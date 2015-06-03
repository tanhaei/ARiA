import java.util.ArrayList;
import java.util.Iterator;
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


    private static List<Goal> Goals = new ArrayList<Goal>();

    public static Goal searchById(String id) {
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

    public static void addToGoals(Goal g) {
        Goals.add(g);
    }
}