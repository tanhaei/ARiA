import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
    private JButton TargetMetamodel;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JTextArea textArea1;
    private JProgressBar progressBar1;
    private JButton Source2ModelATL;
    private JButton Model2SourceATL;
    private JButton RefactoringPatterns;
    private JButton ModelInstance;
    private JButton RefactoringGoals;
    private JList list1;
    private JButton button8;
    private JButton button9;

    public static String IN_METAMODEL = "./metamodels/Composed.ecore";
    public static String IN_METAMODEL_NAME = "Composed";
    public static String OUT_METAMODEL = "./metamodels/Simple.ecore";
    public static String OUT_METAMODEL_NAME = "Simple";

    public static String IN_MODEL = "./models/composed.xmi";
    public static String OUT_MODEL = "./models/simple.xmi";

    public static String TRANSFORMATION_DIR = "./transformations/";
    public static String TRANSFORMATION_MODULE = "ACME2ACMEProfile";

    public dialogue() {

        listRefactoringButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (PerformRefactoring.refversion == 0) {
                        PerformRefactoring.model2TargetTransformation(textField1.getText(), textField2.getText(), textField6.getText(), textField3.getText());
                    }
                    PerformRefactoring.listRefactorings(textField2.getText(), textField5.getText(), textField7.getText(), list1, progressBar1);
                } catch (Exception excp) {

                }
                doRefactoringButton.setEnabled(true);

            }
        });

        doRefactoringButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) list1.getSelectedValue();
                selected = selected.replace(" Pattern is matched!", "");
                if (selected != null)
                    PerformRefactoring.doRefactoring(textField2.getText(), selected);

                if (PerformRefactoring.refversion != 0) {

                }

            }
        });

        sourceMetaModelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                int result = fileChooser.showOpenDialog(textPane1);
                if (result == JFileChooser.APPROVE_OPTION) {
                    textField1.setText(fileChooser.getSelectedFile().getPath());
                }

            }
        });
        TargetMetamodel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                int result = fileChooser.showOpenDialog(textPane1);
                if (result == JFileChooser.APPROVE_OPTION) {
                    textField2.setText(fileChooser.getSelectedFile().getPath());
                }
            }
        });
        Source2ModelATL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                int result = fileChooser.showOpenDialog(textPane1);
                if (result == JFileChooser.APPROVE_OPTION) {
                    textField3.setText(fileChooser.getSelectedFile().getPath());
                }
            }
        });
        Model2SourceATL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                int result = fileChooser.showOpenDialog(textPane1);
                if (result == JFileChooser.APPROVE_OPTION) {
                    textField4.setText(fileChooser.getSelectedFile().getPath());
                }
            }
        });
        RefactoringPatterns.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                int result = fileChooser.showOpenDialog(textPane1);
                if (result == JFileChooser.APPROVE_OPTION) {
                    textField5.setText(fileChooser.getSelectedFile().getPath());
                }
            }
        });
        ModelInstance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                int result = fileChooser.showOpenDialog(textPane1);
                if (result == JFileChooser.APPROVE_OPTION) {
                    textField6.setText(fileChooser.getSelectedFile().getPath());
                }
            }
        });
        RefactoringGoals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                int result = fileChooser.showOpenDialog(textPane1);
                if (result == JFileChooser.APPROVE_OPTION) {
                    textField7.setText(fileChooser.getSelectedFile().getPath());
                }
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
        PerformRefactoring.createATLRuleFile(Pattern.searchInPatterns("HighCoupling"), "./metamodel/ACMEProfile.ATL", "ACMEprofile");

        //r.loadProfile();
    }

}
