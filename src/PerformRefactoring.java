import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import javax.swing.*;

/**
 * Created by tanhaei on 15/6/3 AD.
 */
public class PerformRefactoring {
    public static String IN_METAMODEL = "./metamodels/Composed.ecore";
    public static String IN_METAMODEL_NAME = "Composed";
    public static String OUT_METAMODEL = "./metamodels/Simple.ecore";
    public static String OUT_METAMODEL_NAME = "Simple";

    public static String IN_MODEL = "./models/composed.xmi";
    public static String OUT_MODEL = "./models/simple.xmi";

    public static String TRANSFORMATION_DIR = "./transformations/";
    public static String TRANSFORMATION_MODULE = "ACME2ACMEProfile";

    public static int refversion = 0;

    public PerformRefactoring() {
    }

    public static String createATLRuleFile(Pattern p, String metaModelPath, String metaModelName) {
        String code = p.GenerateATLFinalCode(metaModelPath, metaModelName);

        try {
            FileUtils.writeStringToFile(new File("temporary/" + p.name + ".ATL"), code);
            return "temporary/" + p.name + ".ATL";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void model2TargetTransformation(String srouceMetaModel, String targetMetaModel, String modelPath, String S2TRules) {
        refversion = 0;

        IN_METAMODEL = srouceMetaModel;
        Path p = Paths.get(IN_METAMODEL);
        IN_METAMODEL_NAME = p.getFileName().toString();
        IN_METAMODEL_NAME = IN_METAMODEL_NAME.substring(0, IN_METAMODEL_NAME.length() - IN_METAMODEL_NAME.lastIndexOf("."));

        OUT_METAMODEL = targetMetaModel;
        Path p2 = Paths.get(OUT_METAMODEL);
        OUT_METAMODEL_NAME = p2.getFileName().toString();
        OUT_METAMODEL_NAME = OUT_METAMODEL_NAME.substring(0, OUT_METAMODEL_NAME.length() - OUT_METAMODEL_NAME.lastIndexOf("."));

        IN_MODEL = modelPath;
        OUT_MODEL = "./temporary/middlemodel_" + refversion + ".uml";


        Path p3 = Paths.get(S2TRules);
        String TRANSFORMATION = p2.getFileName().toString();
        TRANSFORMATION_MODULE = TRANSFORMATION.substring(0, TRANSFORMATION.length() - TRANSFORMATION.lastIndexOf("."));
        TRANSFORMATION_DIR = p3.getParent().toString();

        ATLLauncher l = new ATLLauncher();
        l.launch(IN_METAMODEL, IN_METAMODEL_NAME, IN_MODEL, OUT_METAMODEL, OUT_METAMODEL_NAME, OUT_MODEL, TRANSFORMATION_DIR, TRANSFORMATION_MODULE);
    }

    public static void target2ModelTransformation(String srouceMetaModel, String targetMetaModel, String modelPath, String S2TRules) {
        refversion = 0;

        IN_METAMODEL = srouceMetaModel;
        Path p = Paths.get(IN_METAMODEL);
        IN_METAMODEL_NAME = p.getFileName().toString();
        IN_METAMODEL_NAME = IN_METAMODEL_NAME.substring(0, IN_METAMODEL_NAME.length() - IN_METAMODEL_NAME.lastIndexOf("."));

        OUT_METAMODEL = targetMetaModel;
        Path p2 = Paths.get(OUT_METAMODEL);
        OUT_METAMODEL_NAME = p2.getFileName().toString();
        OUT_METAMODEL_NAME = OUT_METAMODEL_NAME.substring(0, OUT_METAMODEL_NAME.length() - OUT_METAMODEL_NAME.lastIndexOf("."));

        OUT_METAMODEL = modelPath;
        IN_MODEL = "./temporary/middlemodel_" + refversion + ".uml";


        Path p3 = Paths.get(S2TRules);
        String TRANSFORMATION = p2.getFileName().toString();
        TRANSFORMATION_MODULE = TRANSFORMATION.substring(0, TRANSFORMATION.length() - TRANSFORMATION.lastIndexOf("."));
        TRANSFORMATION_DIR = p3.getParent().toString();

        ATLLauncher l = new ATLLauncher();
        l.launch(IN_METAMODEL, IN_METAMODEL_NAME, IN_MODEL, OUT_METAMODEL, OUT_METAMODEL_NAME, OUT_MODEL, TRANSFORMATION_DIR, TRANSFORMATION_MODULE);
    }

    public static void listRefactorings(String targetMetaModel, String patternsPath, String goalsPath, JList list, JProgressBar progg) {

        ATLLauncher l = new ATLLauncher();
        progg.setValue(0);

        OUT_METAMODEL = targetMetaModel;
        Path p2 = Paths.get(OUT_METAMODEL);
        OUT_METAMODEL_NAME = p2.getFileName().toString();
        OUT_METAMODEL_NAME = OUT_METAMODEL_NAME.substring(0, OUT_METAMODEL_NAME.length() - OUT_METAMODEL_NAME.lastIndexOf("."));

        IN_METAMODEL = OUT_METAMODEL;
        IN_METAMODEL_NAME = OUT_METAMODEL_NAME;


        //Import Refactoring Goals and Refactoring Pattern Profile
        RefactoringProfileLoader r = new RefactoringProfileLoader(goalsPath, patternsPath);

        //Calculation of the OveralQFactor
        QualityAttribute.calculateOveralQFactor();

        //Ranking pattern based on the calculated OveralQFactor
        DecisionSupport.PatternRanking();

        //Check each pattern on the middle model
        IN_MODEL = "./temporary/middlemodel_" + refversion + ".uml";
        OUT_MODEL = "./temporary/middlemodel_" + refversion + ".patt.uml";


        DefaultListModel listModel = new DefaultListModel();


        for (Pattern patt : Pattern.Patterns) {

            String mmatl = PerformRefactoring.createATLRuleFile(patt, IN_METAMODEL, IN_METAMODEL_NAME);
            Path p4 = Paths.get(mmatl);
            String Pattern_TRANSFORMATION = p4.getFileName().toString();
            TRANSFORMATION_MODULE = Pattern_TRANSFORMATION.substring(0, Pattern_TRANSFORMATION.length() - Pattern_TRANSFORMATION.lastIndexOf("."));
            TRANSFORMATION_DIR = p4.getParent().toString();
            l.launch(IN_METAMODEL, IN_METAMODEL_NAME, IN_MODEL, OUT_METAMODEL, OUT_METAMODEL_NAME, OUT_MODEL, TRANSFORMATION_DIR, TRANSFORMATION_MODULE);
            if (DecisionSupport.CompareTwoFile(IN_MODEL, OUT_MODEL) == 0) {
                listModel.addElement(patt.name + " Pattern is matched!");
                list.setListData(listModel.toArray());
            }
            progg.setValue(progg.getValue() + (int) 100 / (Pattern.Patterns.size()));
        }
    }

    public static void doRefactoring(String targetMetaModel, String patternName) {
        ATLLauncher l = new ATLLauncher();

        OUT_METAMODEL = targetMetaModel;
        Path p2 = Paths.get(OUT_METAMODEL);
        OUT_METAMODEL_NAME = p2.getFileName().toString();
        OUT_METAMODEL_NAME = OUT_METAMODEL_NAME.substring(0, OUT_METAMODEL_NAME.length() - OUT_METAMODEL_NAME.lastIndexOf("."));

        IN_METAMODEL = OUT_METAMODEL;
        IN_METAMODEL_NAME = OUT_METAMODEL_NAME;

        IN_MODEL = "./temporary/middlemodel_" + refversion + ".uml";
        //refversion = refversion + 1;
        OUT_MODEL = "./temporary/middlemodel_" + (refversion + 1) + ".uml";

        Pattern patt = Pattern.searchInPatterns(patternName);
        if (patt != null) {
            String mmatl = PerformRefactoring.createATLRuleFile(patt, IN_METAMODEL, IN_METAMODEL_NAME);
            Path p4 = Paths.get(mmatl);
            String Pattern_TRANSFORMATION = p4.getFileName().toString();
            TRANSFORMATION_MODULE = Pattern_TRANSFORMATION.substring(0, Pattern_TRANSFORMATION.length() - Pattern_TRANSFORMATION.lastIndexOf("."));
            TRANSFORMATION_DIR = p4.getParent().toString();
            l.launch(IN_METAMODEL, IN_METAMODEL_NAME, IN_MODEL, OUT_METAMODEL, OUT_METAMODEL_NAME, OUT_MODEL, TRANSFORMATION_DIR, TRANSFORMATION_MODULE);
            if (DecisionSupport.CompareTwoFile(IN_MODEL, OUT_MODEL) == 0) {
                refversion = refversion + 1;
            }
        }

    }
}
