package org.xowl.satellites.papyrus;

import java.util.HashMap;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.xowl.satellites.papyrus"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	private HashMap<String, Class> requirements;
	
	private HashMap<String, State> states;
	
	private HashMap<String, Transition> transitions;
	
	/**
	 * The constructor
	 */
	public Activator() {
		this.requirements = new HashMap<String, Class>();
		this.states = new HashMap<String, State>();
		this.transitions = new HashMap<String, Transition>();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	public HashMap<String, Class> getRequirements() {
		return requirements;
	}

	public HashMap<String, State> getStates() {
		return states;
	}

	public HashMap<String, Transition> getTransitions() {
		return transitions;
	}

}
