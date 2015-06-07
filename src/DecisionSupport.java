import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by tanhaei on 15/6/3 AD.
 */
public class DecisionSupport {
    public DecisionSupport() {
    }

    public static double CalculateSubW(Goal goal) {
        double subW = 0;
        double metricSum = 0;
        for (String metricSTR : goal.metrics) {
            Metric m = Metric.searchById(metricSTR);
            metricSum = metricSum + (m.targetValue - m.sourceValue);
        }
        metricSum = metricSum / goal.metrics.size();
        subW = metricSum * QualityAttribute.OveralQFactor(QualityAttribute.searchByNode(goal.node).ID);

        for (String subgoal : goal.subGoals) {
            subW = subW + CalculateSubW(Goal.searchById(subgoal));
        }

        return subW;
    }

    public static double CalculateW(Pattern p) {
        double W = 0;
        for (String goal : p.goals) {
            W = W + CalculateSubW(Goal.searchById(goal));
        }
        return W;
    }

    public static void PatternRanking() {
        for (Pattern p : Pattern.Patterns) {
            p.rank = CalculateW(p);
        }

        Collections.sort(Pattern.Patterns, new CustomComparator());

    }

    public static int CompareTwoFile(String fILE_ONE2, String fILE_TWO2) {
        File f1 = new File(fILE_ONE2); //OUTFILE
        File f2 = new File(fILE_TWO2); //INPUT

        try {
            FileReader fR1 = new FileReader(f1);
            FileReader fR2 = new FileReader(f2);

            BufferedReader reader1 = new BufferedReader(fR1);
            BufferedReader reader2 = new BufferedReader(fR2);

            String line1 = null;
            String line2 = null;

            while (true) // Continue while there are equal lines
            {
                line1 = reader1.readLine();
                line2 = reader2.readLine();

                if (line1 == null) // End of file 1
                {
                    return (line2 == null ? 1 : 0); // Equal only if file 2 also ended
                } else if (line2 == null) {
                    return 0; // File 2 ended before file 1, so not equal
                } else if (!line1.equalsIgnoreCase(line2)) // Non-null and different lines
                {
                    return 0;
                }

                // Non-null and equal lines, continue until the input is exhausted
            }
        }
        catch (Exception e)
        {
            return 0;
        }
    }
}


