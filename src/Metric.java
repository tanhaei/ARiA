/**
 * Created by tanhaei on 15/6/2 AD.
 */
public class Metric {
    public String ID;
    public String name;
    public MetricType type;
    public Float sourceValue;
    public Float targetValue;

    public Metric(String id) {
        this.ID = id;
    }
}
