import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tanhaei on 15/6/2 AD.
 */
public class QualityNode {
    public String name;
    public String ID;
    public String ID2;

    public QualityNode(String id) {
        this.ID = id;
        this.ID2 = "";
    }

    public void setID2(String id2) {
        this.ID2 = id2;
    }

    private static List<QualityNode> QualityNodes = new ArrayList<QualityNode>();

    public static QualityNode searchById(String id) {
        if (QualityNodes == null) return null;
        Iterator<QualityNode> it = QualityNodes.iterator();
        while (it.hasNext()) {
            QualityNode node = it.next();
            if (node.ID.equals(id) || node.ID2.equals(id)) {
                return node;
            }
        }
        return null;
    }

    public static QualityNode searchByName(String name) {
        if (QualityNodes == null) return null;
        Iterator<QualityNode> it = QualityNodes.iterator();
        while (it.hasNext()) {
            QualityNode node = it.next();
            if (node.name.equals(name)) {
                return node;
            }
        }
        return null;
    }

    public static void addToQualityNodes(QualityNode g) {
        QualityNodes.add(g);
    }


}
