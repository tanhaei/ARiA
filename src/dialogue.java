import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by tanhaei on 15/6/1 AD.
 */
public class dialogue {
    private JPanel test;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JButton sourceMetaModelButton;
    private JTextPane textPane1;
    private JButton listRefactoringButton;
    private JButton doRefactoringButton;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JTextArea textArea1;
    private JProgressBar progressBar1;
    private JButton button8;
    private JButton button9;

    public  static String IN_METAMODEL = "./metamodels/Composed.ecore";
    public  static String IN_METAMODEL_NAME = "Composed";
    public  static String OUT_METAMODEL = "./metamodels/Simple.ecore";
    public  static String OUT_METAMODEL_NAME = "Simple";

    public  static String IN_MODEL = "./models/composed.xmi";
    public  static String OUT_MODEL = "./models/simple.xmi";

    public  static String TRANSFORMATION_DIR = "./transformations/";
    public  static String TRANSFORMATION_MODULE= "Composed2Simple";

    public dialogue() {
        listRefactoringButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IN_METAMODEL = textField1.getText();
                OUT_METAMODEL = textField2.getText();

                ATLLauncher l = new ATLLauncher();
                l.registerInputMetamodel(IN_METAMODEL);
                l.registerOutputMetamodel(OUT_METAMODEL);
                l.launch(IN_METAMODEL, IN_MODEL, OUT_METAMODEL, OUT_MODEL, TRANSFORMATION_DIR, TRANSFORMATION_MODULE);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("test");
        frame.setContentPane(new dialogue().test);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        RefactoringProfileLoader r = new RefactoringProfileLoader("./profileApplications/RefactoringGoals.profile.uml", "./profileApplications/RefactoringPatterns.profile.uml");

        QualityAttribute.calculateOveralQFactor();

        //DecisionSupport.CalculateW(Pattern.searchInPatterns("HighCoupling"));

        DecisionSupport.PatternRanking();

        //r.loadProfile();
    }
}
