/**
 * Created by tanhaei on 15/6/1 AD.
 */


import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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


    private Model model;
    private Profile profile;
    private String ModelURL;

    private Stereotype ArchRefactoring;

    private static final ResourceSetImpl RESOURCE_SET = new ResourceSetImpl();


    public RefactoringPatterns(String url) {
        ModelURL = url;

        loadRefactorngGoalsProfileApplication("./profiles/RefactoringGoals.profile.uml");
        loadRefactorngPatternsProfileApplication("./profiles/RefactoringPatterns.profile.uml");


    }


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

    protected void loadRefactorngPatternsProfile(String ProfileAddress) {
        URI profileUri = URI.createFileURI(ProfileAddress);
        ResourceSet profileSet = new ResourceSetImpl();
        profileSet.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
        profileSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
        profileSet.createResource(profileUri);
        Resource profileResource = profileSet.getResource(profileUri, true);
        profile = (Profile) EcoreUtil.getObjectByType(profileResource.getContents(), UMLPackage.Literals.PROFILE);
        ArchRefactoring = (Stereotype) profile.getOwnedMember("ArchRefactoring");
        EList<Property> temp = ArchRefactoring.getOwnedAttributes();
    }

    protected void loadRefactorngGoalsProfileApplication(String ProfileApplicationAddress) {

        URI modelUri = URI.createFileURI(ProfileApplicationAddress);
        ResourceSet modelSet = new ResourceSetImpl();
        modelSet.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
        modelSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
        modelSet.createResource(modelUri);


        Resource modelResource = modelSet.getResource(modelUri, true);

        //System.out.println("Root: " + modelResource.getContents().get(2).getClass());

        EList<EObject> elements = modelResource.getContents();


        for (EObject e : elements) {
            String ClassName = e.eClass().getName();

            // QualityNode
            if (ClassName.equals("QualityNode")) {
                String id = ((XMLResource) e.eResource()).getID(e);
                QualityNode newnode = QualityNode.searchById(id);
                if (newnode == null) {
                    newnode = new QualityNode(id);
                }

                for (FeatureMap.Entry e2 : ((org.eclipse.emf.ecore.xml.type.impl.AnyTypeImpl) e).getAnyAttribute()) {
                    String name = e2.getEStructuralFeature().getName();
                    String value = e2.getValue().toString();

                    if (name.equals("name")) {
                        newnode.name = value;
                    }
                }

                if (QualityNode.searchById(newnode.ID) == null)
                    QualityNode.addToQualityNodes(newnode);
            }

            // RefactoringGoal
            else if (ClassName.equals("RefactoringGoal")) {
                String id = ((XMLResource) e.eResource()).getID(e);
                RefactoringGoal newrefgoal = RefactoringGoal.searchById(id);
                if (newrefgoal == null) {
                    newrefgoal = new RefactoringGoal(id);
                }

                for (FeatureMap.Entry e2 : ((org.eclipse.emf.ecore.xml.type.impl.AnyTypeImpl) e).getAnyAttribute()) {
                    String name = e2.getEStructuralFeature().getName();
                    String value = e2.getValue().toString();

                    if (name.equals("qualities")) {
                        String[] subqualities = value.split(" ");
                        for (String subqualitySTR : subqualities) {
                            QualityAttribute qa = QualityAttribute.searchById(subqualitySTR);
                            if (qa == null) {
                                qa = new QualityAttribute(subqualitySTR);
                                QualityAttribute.addToQualityAttribute(qa);
                            }
                            newrefgoal.qualities.add(qa.ID);
                        }
                    }
                }

                if (RefactoringGoal.searchById(newrefgoal.ID) == null)
                    RefactoringGoal.addToRefactoringGoals(newrefgoal);
            }

            // QualityAttribute
            else if (ClassName.equals("QualityAttribute")) {
                String id = ((XMLResource) e.eResource()).getID(e);
                QualityAttribute newq = QualityAttribute.searchById(id);
                if (newq == null) {
                    newq = new QualityAttribute(id);
                }

                for (FeatureMap.Entry e2 : ((org.eclipse.emf.ecore.xml.type.impl.AnyTypeImpl) e).getAnyAttribute()) {
                    String name = e2.getEStructuralFeature().getName();
                    String value = e2.getValue().toString();

                    if (name.equals("node")) {
                        newq.node = value;
                    } else if (name.equals("importanceFactor")) {
                        newq.importance = Float.valueOf(value);
                    } else if (name.equals("subQualities")) {
                        String[] subQualities = value.split(" ");
                        for (String subqualitySTR : subQualities) {
                            QualityAttribute qa = QualityAttribute.searchById(subqualitySTR);
                            if (qa == null) {
                                qa = new QualityAttribute(subqualitySTR);
                                QualityAttribute.addToQualityAttribute(qa);
                            }
                            qa.parent = newq.ID;
                            newq.subQualities.add(qa.ID);
                        }
                    }

                }
            }


        }
    }

    protected void loadRefactorngPatternsProfileApplication(String ProfileApplicationAddress) {

        URI modelUri = URI.createFileURI(ProfileApplicationAddress);
        ResourceSet modelSet = new ResourceSetImpl();
        modelSet.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
        modelSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
        modelSet.createResource(modelUri);


        Resource modelResource = modelSet.getResource(modelUri, true);

        //System.out.println("Root: " + modelResource.getContents().get(2).getClass());

        EList<EObject> elements = modelResource.getContents();


        for (EObject e : elements) {
            String ClassName = e.eClass().getName();

            // ArchRefactoring
            if (ClassName.equals("ArchRefactoring")) {
                String id = ((XMLResource) e.eResource()).getID(e);
                Pattern newPatt = new Pattern(id);

                for (FeatureMap.Entry e2 : ((org.eclipse.emf.ecore.xml.type.impl.AnyTypeImpl) e).getAnyAttribute()) {
                    String name = e2.getEStructuralFeature().getName();
                    String value = e2.getValue().toString();

                    if (name.equals("name")) {
                        newPatt.name = value;
                    } else if (name.equals("rule")) {
                        newPatt.rules = value;
                    } else if (name.equals("helpers")) {
                        newPatt.helpers = value;
                    } else if (name.equals("lazyRules")) {
                        newPatt.lazyRules = value;
                    } else if (name.equals("goals")) {
                        String[] goals = value.split(" ");
                        for (String goalSTR : goals) {
                            Goal g = Goal.searchById(goalSTR);
                            if (g == null) {
                                g = new Goal(goalSTR);
                                Goal.addToGoals(g);
                            }
                            newPatt.goals.add(g.ID);
                        }
                    }
                }
                for (FeatureMap.Entry e2 : ((org.eclipse.emf.ecore.xml.type.impl.AnyTypeImpl) e).getMixed()) {
                    String name = e2.getEStructuralFeature().getName();
                    if (name.equals("requiredPatterns")) {

                        String value = ((org.eclipse.emf.ecore.xml.type.impl.AnyTypeImpl) e2.getValue()).getMixed().getValue(0).toString();
                        newPatt.requiredPatterns.add(value);
                    }

                }
                Pattern.addToPatterns(newPatt);
            }
            // Goal
            else if (ClassName.equals("Goal")) {
                String id = ((XMLResource) e.eResource()).getID(e);
                Goal newgoal = Goal.searchById(id);
                if (newgoal == null) {
                    newgoal = new Goal(id);
                }

                for (FeatureMap.Entry e2 : ((org.eclipse.emf.ecore.xml.type.impl.AnyTypeImpl) e).getAnyAttribute()) {
                    String name = e2.getEStructuralFeature().getName();
                    String value = e2.getValue().toString();

                    if (name.equals("node")) {
                        QualityNode q = QualityNode.searchById(value);
                        if (q == null) {
                            q = new QualityNode(value);
                        }
                        newgoal.node = q.ID;
                    } else if (name.equals("subGoals")) {
                        String[] subgoals = value.split(" ");
                        for (String subgoalSTR : subgoals) {
                            Goal g = Goal.searchById(subgoalSTR);
                            if (g == null) {
                                g = new Goal(subgoalSTR);
                                Goal.addToGoals(g);
                            }
                            g.parent = newgoal.ID;
                            newgoal.subGoals.add(g.ID);
                        }
                    } else if (name.equals("metrics")) {
                        String[] metrics = value.split(" ");
                        for (String metricSTR : metrics) {
                            Metric m = Metric.searchById(metricSTR);
                            if (m == null) {
                                m = new Metric(metricSTR);
                                Metric.addToMetrics(m);
                            }
                            newgoal.metrics.add(m.ID);
                        }
                    }
                }

                if (Goal.searchById(newgoal.ID) == null)
                    Goal.addToGoals(newgoal);
            }
            // QualityNode
            else if (ClassName.equals("QualityNode")) {
                String id = ((XMLResource) e.eResource()).getID(e);


                for (FeatureMap.Entry e2 : ((org.eclipse.emf.ecore.xml.type.impl.AnyTypeImpl) e).getAnyAttribute()) {
                    String name = e2.getEStructuralFeature().getName();
                    String value = e2.getValue().toString();

                    if (name.equals("name")) {
                        QualityNode newnode = QualityNode.searchByName(value);
                        if (newnode == null) {
                            newnode = new QualityNode(id);
                            newnode.name = value;
                        }
                        newnode.setID2(id);

                        if (QualityNode.searchById(newnode.ID) == null)
                            QualityNode.addToQualityNodes(newnode);
                    }
                }


            }
            // QualityMetric
            else if (ClassName.equals("QualityMetric")) {
                String id = ((XMLResource) e.eResource()).getID(e);
                Metric newmetric = Metric.searchById(id);
                if (newmetric == null) {
                    newmetric = new Metric(id);
                }

                for (FeatureMap.Entry e2 : ((org.eclipse.emf.ecore.xml.type.impl.AnyTypeImpl) e).getAnyAttribute()) {
                    String name = e2.getEStructuralFeature().getName();
                    String value = e2.getValue().toString();

                    if (name.equals("name")) {
                        newmetric.name = value;
                    } else if (name.equals("sourceValue")) {
                        newmetric.sourceValue = Float.valueOf(value);
                    } else if (name.equals("targetValue")) {
                        newmetric.targetValue = Float.valueOf(value);
                    }
                }

                if (Metric.searchById(newmetric.ID) == null)
                    Metric.addToMetrics(newmetric);
            }

        }
    }


}
