package org.xowl.satellites.papyrus;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.EObjectTreeElement;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.xowl.platform.kernel.remote.PlatformApiDeserializerForOSGi;
import org.xowl.platform.kernel.remote.RemotePlatformAccess;

import fr.cenotelie.commons.utils.api.Reply;
import fr.cenotelie.commons.utils.api.ReplyResultCollection;
import fr.cenotelie.commons.utils.json.SerializedUnknown;

@SuppressWarnings("restriction")
public class PullRequirementsHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelectionService selectionService = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
		ISelection selection = selectionService.getSelection();
		Object o = ((IStructuredSelection)selection).getFirstElement();
		Object p = ((EObjectTreeElement)o).getEObject();
		if (!(p instanceof Package)) {
			return null;
		}
		Package pack = (Package)p;
		Profile sysml = loadSysmlProfile();
		Profile requirements = (Profile) EcoreUtil.getEObject(sysml, "Requirements");
		Stereotype requirement = (Stereotype) EcoreUtil.getEObject(sysml, "Requirements/Requirement");
		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(p);
		String u = "https://localhost:8443/api";
		try(RemotePlatformAccess connection = new RemotePlatformAccess(u, new PlatformApiDeserializerForOSGi())) {
			Reply reply = connection.login("admin", "admin");
			if (!reply.isSuccess()) {
				return null;
			}
			reply = connection.doRequest("/connectors/specific/reqif", "GET");
			if (!reply.isSuccess()) {
				return null;
			}
			@SuppressWarnings("unchecked")
			Collection<SerializedUnknown> res = ((ReplyResultCollection<SerializedUnknown>)reply).getData();
			Iterator<SerializedUnknown> it = res.iterator();
			Command cmd = new RecordingCommand(ted) {
				
				@Override
				protected void doExecute() {
					if (pack.getAllAppliedProfiles().indexOf(requirements) < 0) {
						pack.applyProfile(requirements);
					}
					while(it.hasNext()) {
						SerializedUnknown s = it.next();
						createRequirement(pack, s, requirement);
					}					
				}
			};
			try {
				ted.getCommandStack().execute(cmd);
			} catch(Exception e) {
				e.printStackTrace();
			}
			return null;
		} catch(Exception ex) {
			return null;
		}
	}
	
	private void createRequirement(Package parent, SerializedUnknown serialized, Stereotype stereo) {
		Class req = UMLFactory.eINSTANCE.createClass();
		parent.getPackagedElements().add(req);
		req.setName("REQ_" + serialized.getValueFor("id"));
		req.applyStereotype(stereo);
		Stereotype appliedStereotype = req.getAppliedStereotypes().get(0);
		req.setValue(appliedStereotype, "id", serialized.getValueFor("name"));
		req.setValue(appliedStereotype, "text", serialized.getValueFor("description"));
	}
	
	private Profile loadSysmlProfile() {
		return ProfileUtils.loadProfile(ProfileUtils.SYSML_PROFILES_PATHMAP, ProfileUtils.SYSML_PROFILE_PATH);
	}
	
}