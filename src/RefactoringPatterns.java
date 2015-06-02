/**
 * Created by tanhaei on 15/6/1 AD.
 */


import java.net.URL;
import java.util.Collections;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.uml2.uml.*;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.resource.UMLResource;

import javax.management.Attribute;


public class RefactoringPatterns {
    public String name;
    public String generatedrule;
    public Float rank;

    private Model model;
    private Profile profile;
    private String ModelURL;

    private Stereotype ArchRefactoring;

    private static final ResourceSetImpl RESOURCE_SET = new ResourceSetImpl();

    public RefactoringPatterns(String url) {
        ModelURL = url;
        loadProfile("");

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
        String PROFILE_ADD = "./profiles/refactoring.profile.uml";
        PROFILE_NAME = "refactoring";
        URI profileUri = URI.createFileURI(PROFILE_ADD);
        ResourceSet profileSet = new ResourceSetImpl();
        profileSet.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
        profileSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
        profileSet.createResource(profileUri);
        Resource profileResource = profileSet.getResource(profileUri, true);
        profile = (Profile)EcoreUtil.getObjectByType(profileResource.getContents(), UMLPackage.Literals.PROFILE);
        ArchRefactoring = (Stereotype)profile.getOwnedMember("ArchRefactoring");
        EList<Property> temp = ArchRefactoring.getOwnedAttributes();
        loadModels("");
  }

    protected void loadModels(String MODEL_NAME)
    {
        String STEREOTYPE_TRANSITION_EDGE = "refactoring::ArchRefactoring";
        String MODEL_ADD = "./profiles/refactoring.profile.uml";

        URI modelUri = URI.createFileURI(MODEL_ADD);
        ResourceSet modelSet = new ResourceSetImpl();
        modelSet.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
        modelSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
        modelSet.createResource(modelUri);



        Resource modelResource = modelSet.getResource(modelUri, true);

        System.out.println("Root: " + modelResource.getContents().get(2).getClass());
        //System.out.println("Count: " + modelResource.getContents().get(2).toString());

        //Model model2 = (Model)EcoreUtil.getObjectByType(modelResource.getContents(), UMLPackage.Literals.MODEL);

        EList<EObject> elements = modelResource.getContents();//model2.getOwnedElements();


        for(EObject e : elements){
            String ClassName = e.eClass().getName();
            if(ClassName.equals( "ArchRefactoring") || ClassName.equals( "Goal"))
            {
                String id = ((XMLResource)e.eResource()).getID(e);
                Pattern newPatt = new Pattern(id);

                for(FeatureMap.Entry e2 : ((org.eclipse.emf.ecore.xml.type.impl.AnyTypeImpl)e).getAnyAttribute())
                {
                    String name = e2.getEStructuralFeature().getName();
                    String value = e2.getValue().toString();



                    if(name.equals("name"))
                    {
                        newPatt.name = value;
                    }
                    else if(name.equals("rule"))
                    {
                        newPatt.rules = value;
                    }
                    else if(name.equals("helpers"))
                    {
                        newPatt.helpers = value;
                    }
                    else if(name.equals("lazyRules"))
                    {
                        newPatt.lazyRules = value;
                    }



                }
                for(FeatureMap.Entry e2 : ((org.eclipse.emf.ecore.xml.type.impl.AnyTypeImpl)e).getMixed())
                {
                    String name = e2.getEStructuralFeature().getName();
                    if(name.equals("requiredPatterns")) {
                        String value = ((org.eclipse.emf.ecore.xml.type.impl.AnyTypeImpl)e2.getValue()).getMixed().getValue(0).toString();
                        System.out.println(name + "=" + value);
                    }

                }


            }


        }
    }
}
