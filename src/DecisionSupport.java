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
        for(String goal : p.goals)
        {
            W = W + CalculateSubW(Goal.searchById(goal));
        }
        return W;
    }

    public static void PatternRanking()
    {
        for(Pattern p : Pattern.Patterns)
        {
            p.rank = CalculateW(p);
        }
    }
}
