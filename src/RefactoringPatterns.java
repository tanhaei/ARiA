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


    private static List<Pattern> Patterns = new ArrayList<Pattern>();
    private static List<Goal> Goals = new ArrayList<Goal>();
    private static List<Metric> Metrics = new ArrayList<Metric>();
    private static List<QualityNode> QualityNodes = new ArrayList<QualityNode>();


    public RefactoringPatterns(String url) {
        ModelURL = url;
        loadModels("");

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

    protected void loadProfile(String PROFILE_NAME) {
        String PROFILE_ADD = "./profiles/refactoring.profile.uml";
        PROFILE_NAME = "refactoring";
        URI profileUri = URI.createFileURI(PROFILE_ADD);
        ResourceSet profileSet = new ResourceSetImpl();
        profileSet.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
        profileSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
        profileSet.createResource(profileUri);
        Resource profileResource = profileSet.getResource(profileUri, true);
        profile = (Profile) EcoreUtil.getObjectByType(profileResource.getContents(), UMLPackage.Literals.PROFILE);
        ArchRefactoring = (Stereotype) profile.getOwnedMember("ArchRefactoring");
        EList<Property> temp = ArchRefactoring.getOwnedAttributes();
    }

    protected void loadModels(String MODEL_NAME) {
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
                            Goal g = searchInGoals(goalSTR);
                            if (g == null) {
                                g = new Goal(goalSTR);
                                Goals.add(g);
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

                Patterns.add(newPatt);
            }
            // Goal
            else if (ClassName.equals("Goal")) {
                String id = ((XMLResource) e.eResource()).getID(e);
                Goal newgoal = searchInGoals(id);
                if (newgoal == null) {
                    newgoal = new Goal(id);
                }

                for (FeatureMap.Entry e2 : ((org.eclipse.emf.ecore.xml.type.impl.AnyTypeImpl) e).getAnyAttribute()) {
                    String name = e2.getEStructuralFeature().getName();
                    String value = e2.getValue().toString();

                    if (name.equals("node")) {
                        QualityNode q = searchInQualityNodes(value);
                        if (q == null) {
                            q = new QualityNode(value);
                        }
                        newgoal.node = q.ID;
                    } else if (name.equals("subGoals")) {
                        String[] subgoals = value.split(" ");
                        for (String subgoalSTR : subgoals) {
                            Goal g = searchInGoals(subgoalSTR);
                            if (g == null) {
                                g = new Goal(subgoalSTR);
                                Goals.add(g);
                            }
                            g.parent = newgoal.ID;
                            newgoal.subGoals.add(g.ID);
                        }
                    } else if (name.equals("metrics")) {
                        String[] metrics = value.split(" ");
                        for (String metricSTR : metrics) {
                            Metric m = searchInMetrics(metricSTR);
                            if (m == null) {
                                m = new Metric(metricSTR);
                                Metrics.add(m);
                            }
                            newgoal.metrics.add(m.ID);
                        }
                    }
                }

                if (searchInGoals(newgoal.ID) == null)
                    Goals.add(newgoal);
            }
            // QualityNode
            else if (ClassName.equals("QualityNode")) {
                String id = ((XMLResource) e.eResource()).getID(e);
                QualityNode newnode = searchInQualityNodes(id);
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

                if (searchInQualityNodes(newnode.ID) == null)
                    QualityNodes.add(newnode);
            }
            // QualityMetric
            else if (ClassName.equals("QualityMetric")) {
                String id = ((XMLResource) e.eResource()).getID(e);
                Metric newmetric = searchInMetrics(id);
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

                if (searchInMetrics(newmetric.ID) == null)
                    Metrics.add(newmetric);
            }

        }
    }

    public static Goal searchInGoals(String id) {
        if (Goals == null) return null;
        Iterator<Goal> it = Goals.iterator();
        while (it.hasNext()) {
            Goal goal = it.next();
            if (goal.ID.equals(id)) {
                return goal;
            }
        }
        return null;
    }

    public static Pattern searchInPatterns(String name) {
        if (Patterns == null) return null;
        Iterator<Pattern> it = Patterns.iterator();
        while (it.hasNext()) {
            Pattern patt = it.next();
            if (patt.name.equals(name)) {
                return patt;
            }
        }
        return null;
    }

    public static QualityNode searchInQualityNodes(String id) {
        if (QualityNodes == null) return null;
        Iterator<QualityNode> it = QualityNodes.iterator();
        while (it.hasNext()) {
            QualityNode node = it.next();
            if (node.ID.equals(id)) {
                return node;
            }
        }
        return null;
    }

    public static Metric searchInMetrics(String id) {
        if (Metrics == null) return null;
        Iterator<Metric> it = Metrics.iterator();
        while (it.hasNext()) {
            Metric metric = it.next();
            if (metric.ID.equals(id)) {
                return metric;
            }
        }
        return null;
    }
}
