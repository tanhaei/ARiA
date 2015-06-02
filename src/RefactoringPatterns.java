/**
 * Created by tanhaei on 15/6/1 AD.
 */


import java.util.Collections;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.*;
import org.eclipse.uml2.uml.resource.UMLResource;


public class RefactoringPatterns {
    public String name;
    public String generatedrule;
    public Float rank;

    private Model model;
    private Profile profile;

    private static final ResourceSetImpl RESOURCE_SET = new ResourceSetImpl();

    public RefactoringPatterns(String url) {


    }

    public String preconditionrule;

    public String getName() {
        return name;
    }

    protected org.eclipse.uml2.uml.Profile load(URI uri) {
        System.out.println("uri = " + uri);
        org.eclipse.uml2.uml.Profile profile_ = null;

        try {
            Resource resource = RESOURCE_SET.getResource(uri, true);

            profile_ = (org.eclipse.uml2.uml.Profile) EcoreUtil.getObjectByType(resource.getContents(), UMLPackage.Literals.PROFILE_APPLICATION);
        } catch (WrappedException we) {
            we.printStackTrace();
            System.exit(1);
        }

        return profile_;
    }

    protected void loadProfile(String PROFILE_NAME)
    {
        URI profileUri = URI.createURI(PROFILE_NAME);
        ResourceSet profileSet = new ResourceSetImpl();
        profileSet.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
        profileSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
        profileSet.createResource(profileUri);
        Resource profileResource = profileSet.getResource(profileUri, true);
        profile = (Profile)EcoreUtil.getObjectByType(profileResource.getContents(), UMLPackage.Literals.PROFILE);
        Profile sopraProfile = (Profile)profile.getOwnedMember(PROFILE_NAME);
      //  Stereotype serviceRequestStereotype = (Stereotype)sopraProfile.getOwnedMember(STEREOTYPE_SERVICE_REQUEST);
      //  Stereotype transitionEdgeStereotype = (Stereotype)sopraProfile.getOwnedMember(STEREOTYPE_TRANSITION_EDGE);
    }

    protected void loadModels(String MODEL_NAME)
    {
        String STEREOTYPE_TRANSITION_EDGE = 'ServiceProfile::TransitionEdge';

        URI modelUri = URI.createURI(MODEL_NAME);
        ResourceSet modelSet = new ResourceSetImpl();
        modelSet.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
        modelSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
        modelSet.createResource(modelUri);
        Resource modelResource = modelSet.getResource(modelUri, true);
        model = (Model)EcoreUtil.getObjectByType(modelResource.getContents(), UMLPackage.Literals.MODEL);

        EList<Element> elements = model.getOwnedElements();
        for(Element e : elements){
            if(e instanceof Activity){
                Activity activity = (Activity)e;
                Stereotype transitionEdgeStereotype =
                        activity.getAppliedStereotype(STEREOTYPE_TRANSITION_EDGE);

                if (transitionEdgeStereotype != null) {
                    System.out.println("hora");
                }
            }
        }
    }
}
