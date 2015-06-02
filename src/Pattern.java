import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanhaei on 15/6/2 AD.
 */
public class Pattern {
    public String ID;
    public String name;
    public String rules;
    public String lazyRules;
    public String helpers;
    public List<String> goals = new ArrayList<String>();
    public List<String> requiredPatterns = new ArrayList<String>();

    public Pattern(String id) {
        ID = id;
    }
}


