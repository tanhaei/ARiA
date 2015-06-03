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
    public Float importance;
    public List<String> subQualities = new ArrayList<String>();

    public QualityAttribute(String id) {
        this.ID = id;
    }

    private static List<QualityAttribute> QualityAttributes = new ArrayList<QualityAttribute>();

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

    public static void addToQualityAttribute(QualityAttribute qa) {
        QualityAttributes.add(qa);
    }
}
