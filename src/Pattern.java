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
    public double rank;
    public List<String> goals = new ArrayList<String>();
    public List<String> requiredPatterns = new ArrayList<String>();

    public Pattern(String id) {
        ID = id;
    }


    public static List<Pattern> Patterns = new ArrayList<Pattern>();

    public static Pattern searchInPatterns(String name) {
        if (Patterns == null) return null;
        for (Pattern patt : Patterns) {
            if (patt.name.equals(name)) {
                return patt;
            }
        }
        return null;
    }

    public static void addToPatterns(Pattern p) {
        Patterns.add(p);
    }

}


