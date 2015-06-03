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


    public String GenerateATLFinalCode(String metaModelPath, String metaModelName) {
        String code = "";

        if (helpers != null)
            code = code + helpers + '\n';
        if (lazyRules != null)
            code = code + lazyRules + '\n';
        if (rules != null)
            code = code + rules + '\n';

        for (String reqPatt : requiredPatterns) {
            Pattern related = Pattern.searchInPatterns(reqPatt);
            if (related != null)
                code = related.GenerateATLFinalCode(metaModelPath, metaModelName) + '\n' + code;
        }

        String header = "-- @path " + metaModelName + "=" + metaModelPath + "\n" +
                "-- @atlcompiler emftvm\n" +
                "\n" +
                "module " + metaModelName + "2" + metaModelName + ";\n" +
                "create OUT : " + metaModelName + " refining IN : " + metaModelName + ";\n";

        return header + code;
    }

}


