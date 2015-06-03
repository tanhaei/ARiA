import java.util.ArrayList;
import java.util.Iterator;
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


    private static List<Pattern> Patterns = new ArrayList<Pattern>();

    public static Pattern searchInPatterns(String name) {
        if (Patterns == null) return null;
        Iterator<Pattern> it = Patterns.iterator();
        while (it.hasNext()) {
            Pattern patt = it.next();
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


