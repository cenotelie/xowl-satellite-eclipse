package org.xowl.satellites.papyrus;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

public class ProfileUtils {
	
	public static final String SYSML_PROFILES_PATHMAP = "pathmap://SysML14_PROFILES/"; //$NON-NLS-1$
	public static final String SYSML_PROFILE_PATH = "SysML.profile.uml"; //$NON-NLS-1$
	
	public static Profile loadProfile(String pathmap, String path) {
		URI sysmlProfilesURI = URI.createURI(pathmap, true);
		URI sysmlProfileURI = sysmlProfilesURI.appendSegment(path);
		ResourceSet rs = new ResourceSetImpl();
		Resource resource = rs.getResource(sysmlProfileURI, true);
		Profile profile = (Profile) resource.getContents().get(0);
		return profile;
	}

}
