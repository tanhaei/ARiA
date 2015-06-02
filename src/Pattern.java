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
    public List<Goal> goals;

    public Pattern(String id) {
        ID = id;
    }
}


