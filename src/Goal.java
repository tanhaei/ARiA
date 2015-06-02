import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanhaei on 15/6/2 AD.
 */
public class Goal {
    public String ID;
    public String node;
    public String parent;
    public List<String> subGoals = new ArrayList<String>();
    public List<String> metrics = new ArrayList<String>();


    public Goal(String id) {
        this.ID = id;
    }
}