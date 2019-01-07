package org.xowl.satellites.pror;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.internal.resources.File;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.rmf.reqif10.AttributeValue;
import org.eclipse.rmf.reqif10.AttributeValueString;
import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.SpecObject;
import org.eclipse.rmf.reqif10.serialization.ReqIF10ResourceFactoryImpl;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;
import org.xowl.platform.kernel.remote.PlatformApiDeserializerForOSGi;
import org.xowl.platform.kernel.remote.RemotePlatformAccess;

import fr.cenotelie.commons.utils.api.Reply;
import fr.cenotelie.commons.utils.http.HttpConstants;
import fr.cenotelie.commons.utils.http.URIUtils;

@SuppressWarnings("restriction")
public class PushRequirementsHandler extends AbstractHandler {

	@SuppressWarnings("deprecation")
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String u = "https://localhost:8443/api";
		ISelectionService selectionService = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
		ISelection selection = selectionService.getSelection();
		Object o = ((IStructuredSelection)selection).getFirstElement();
		if (!(o instanceof File)) {
			return null;
		}
		ReqIF reqif = loadFromFile((File)o);
		List<Requirement> requirements = generateRequirements(reqif);
		String artifactName, artifactBase, artifactVersion, artifactArchetype;
		artifactName = "ProR Base";
		artifactBase = "http://xowl.org/platform/kernel/Artifact#4b6ebc1f-f6a0-4a07-bb6a-ed42989cabe1";
		artifactVersion = "v1.1";
		artifactArchetype = "com.holonshub.marketplace.domains.syseng.SysEngArchetypeRequirements";
		try(RemotePlatformAccess connection = new RemotePlatformAccess(u, new PlatformApiDeserializerForOSGi())) {
			Reply reply = connection.login("admin", "admin");
			if (!reply.isSuccess()) {
				return null;
			}
			reply = connection.doRequest(
                    "/connectors/specific/reqif" + "?name=" + URIUtils.encodeComponent(artifactName) + "&base="
                            + URIUtils.encodeComponent(artifactBase) + "&version="
                            + URIUtils.encodeComponent(artifactVersion) + "&archetype="
                            + URIUtils.encodeComponent(artifactArchetype),
                    "POST", generateJSONContent(requirements).getBytes(), "application/json", false, HttpConstants.MIME_JSON);
			System.out.println(reply.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private ReqIF loadFromFile(File file) {
		//ReqIF10Package.eINSTANCE.eClass();
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("reqif", new ReqIF10ResourceFactoryImpl());
        ResourceSet resSet = new ResourceSetImpl();
        String path = file.getLocation().toString();
        Resource resource = resSet.getResource(URI.createFileURI(path), true);
		return (ReqIF) resource.getContents().get(0);
	}
	
	private List<Requirement> generateRequirements(ReqIF reqif) {
		EList<SpecObject> reqs = reqif.getCoreContent().getSpecObjects();
		List<Requirement> requirements = new ArrayList<Requirement>();
		for (SpecObject so: reqs) {
			Requirement req = new Requirement();
			req.setName(so.getIdentifier());
			EList<AttributeValue> values = so.getValues();
			req.setDescription(((AttributeValueString)values.get(0)).getTheValue());
			req.setId(((AttributeValueString)values.get(1)).getTheValue());
			requirements.add(req);
		}
		return requirements;
	}
	
	private String generateJSONContent(List<Requirement> requirements) {
		StringBuilder sb = new StringBuilder();
		sb.append("{ \"graph\": \"sys:req\", ");
		sb.append("\"entities\": ");
		sb.append("[");
		Iterator<Requirement> it = requirements.iterator();
		Requirement req;
		while (it.hasNext()) {
			req = it.next();
			sb.append(req.toJSON());
			if (it.hasNext()) {
				sb.append(",");
			}
			sb.append("\n");
		}
		sb.append("]}");
		System.out.println(sb.toString());
		return sb.toString();
	}
	
}