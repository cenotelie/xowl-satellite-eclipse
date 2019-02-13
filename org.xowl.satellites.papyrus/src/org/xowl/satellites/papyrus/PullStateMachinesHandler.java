package org.xowl.satellites.papyrus;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.emf.common.command.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.EObjectTreeElement;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLFactory;
import org.xowl.platform.kernel.remote.PlatformApiDeserializerForOSGi;
import org.xowl.platform.kernel.remote.RemotePlatformAccess;

import fr.cenotelie.commons.utils.api.Reply;
import fr.cenotelie.commons.utils.api.ReplyResultCollection;
import fr.cenotelie.commons.utils.json.SerializedUnknown;

public class PullStateMachinesHandler extends AbstractHandler {
	
	private HashMap<String, State> states = Activator.getDefault().getStates();
	private HashMap<String, Transition> transitions = Activator.getDefault().getTransitions();

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		states.clear();
		transitions.clear();
		ISelectionService selectionService = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
		ISelection selection = selectionService.getSelection();
		Object o = ((IStructuredSelection)selection).getFirstElement();
		Object p = ((EObjectTreeElement)o).getEObject();
		if (!(p instanceof Package)) {
			return null;
		}
		Package pack = (Package)p;
		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(p);
		String u = "https://localhost:8443/api";
		try(RemotePlatformAccess connection = new RemotePlatformAccess(u, new PlatformApiDeserializerForOSGi())) {
			Reply reply = connection.login("admin", "admin");
			if (!reply.isSuccess()) {
				return null;
			}
			reply = connection.doRequest("/connectors/specific/uppaal", "GET");
			if (!reply.isSuccess()) {
				return null;
			}
			@SuppressWarnings("unchecked")
			Collection<SerializedUnknown> res = ((ReplyResultCollection<SerializedUnknown>)reply).getData();
			Iterator<SerializedUnknown> it = res.iterator();
			Command cmd = new RecordingCommand(ted) {
				
				@Override
				protected void doExecute() {
					while(it.hasNext()) {
						SerializedUnknown s = it.next();
						createFsm(pack, s);
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

	protected void createFsm(Package pack, SerializedUnknown s) {
		StateMachine sm = UMLFactory.eINSTANCE.createStateMachine();
		pack.getPackagedElements().add(sm);
		sm.setName("" + s.getValueFor("name"));
		Region mainRegion = UMLFactory.eINSTANCE.createRegion();
		sm.getRegions().add(mainRegion);
		mainRegion.setName("main");
		Iterator<SerializedUnknown> it = ((Collection)s.getValueFor("states")).iterator();
		while (it.hasNext()) {
			SerializedUnknown su = it.next();
			createState(mainRegion, su);
		}
		it = ((Collection)s.getValueFor("transitions")).iterator();
		while (it.hasNext()) {
			SerializedUnknown su = it.next();
			createTransition(mainRegion, su);
		}
		State init = states.get("" + s.getValueFor("initialStateId"));
		createInitialState(mainRegion, init);
	}
	
	protected void createState(Region region, SerializedUnknown s) {
		State state = UMLFactory.eINSTANCE.createState();
		region.getSubvertices().add(state);
		state.setName("" + s.getValueFor("name"));
		states.put("" + s.getValueFor("identifier"), state);
	}
	
	protected void createTransition(Region region, SerializedUnknown s) {
		State source = states.get("" + s.getValueFor("sourceId"));
		State target = states.get("" + s.getValueFor("targetId"));
		if (source == null || target == null) return;
		Transition t = UMLFactory.eINSTANCE.createTransition();
		region.getTransitions().add(t);
		t.setName(source.getName().toLowerCase() + "_" + target.getName().toLowerCase());
		t.setSource(source);
		t.setTarget(target);
		transitions.put(s.getValueFor("sourceId") + "_" + s.getValueFor("targetId"), t);
	}
	
	protected void createInitialState(Region region, State init) {
		Pseudostate ps = UMLFactory.eINSTANCE.createPseudostate();
		ps.setKind(PseudostateKind.INITIAL_LITERAL);
		region.getSubvertices().add(ps);
		Transition t = UMLFactory.eINSTANCE.createTransition();
		region.getTransitions().add(t);
		t.setSource(ps);
		t.setTarget(init);
		
	}

}
