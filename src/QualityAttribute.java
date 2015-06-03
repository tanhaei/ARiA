import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tanhaei on 15/6/3 AD.
 */
public class QualityAttribute {
    public String ID;
    public String node;
    public String parent;
    public double importance;
    public List<String> subQualities = new ArrayList<String>();
    public double overalQFactor = -1;

    public QualityAttribute(String id) {
        this.ID = id;
    }

    public static List<QualityAttribute> QualityAttributes = new ArrayList<QualityAttribute>();

    public static QualityAttribute searchById(String id) {
        if (QualityAttributes == null) return null;
        Iterator<QualityAttribute> it = QualityAttributes.iterator();
        while (it.hasNext()) {
            QualityAttribute quality = it.next();
            if (quality.ID.equals(id)) {
                return quality;
            }
        }
        return null;
    }

    public static QualityAttribute searchByNode(String nodeid) {
        if (QualityAttributes == null) return null;
        QualityNode node = QualityNode.searchById(nodeid);
        Iterator<QualityAttribute> it = QualityAttributes.iterator();
        while (it.hasNext()) {
            QualityAttribute quality = it.next();
            if (quality.node.equals(node.ID) || quality.node.equals(node.ID2)) {
                return quality;
            }
        }
        return null;
    }

    public static void addToQualityAttribute(QualityAttribute qa) {
        QualityAttributes.add(qa);
    }

    public static void calculateOveralQFactor() {
        if (QualityAttributes == null) return;

        Iterator<QualityAttribute> it = QualityAttributes.iterator();

        while (it.hasNext()) {
            QualityAttribute node = it.next();
            QualityAttribute.OverQFactor(node.ID);
        }
        return;
    }

    public static double OverQFactor(String qualityid) {
        double factor = 0;
        QualityAttribute QQ = QualityAttribute.searchById(qualityid);

        if (QQ == null) {
            factor = 1.0;
        } else {
            if (QQ.overalQFactor == -1) {
                double child_factor = 0;
                List<String> sibilings;
                if (QQ.parent == "root")
                    sibilings = RefactoringGoal.RefactoringGoals.get(0).qualities;
                else
                    sibilings = QualityAttribute.searchById(QQ.parent).subQualities;

                for (String sibiling : sibilings) {
                    child_factor = child_factor + QualityAttribute.searchById(sibiling).importance;
                }
                double relative_importance = QQ.importance / child_factor;
                factor = relative_importance * OverQFactor(QQ.parent);

                QQ.overalQFactor = factor;
            } else {
                factor = QQ.overalQFactor;
            }
        }

        return factor;
    }
}
