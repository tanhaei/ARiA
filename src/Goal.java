import java.util.List;

/**
 * Created by tanhaei on 15/6/2 AD.
 */
public class Goal {
    public String ID;
    public QualityNode node;
    public List<Goal> subGoals;
    public List<Metric> metrics;
}