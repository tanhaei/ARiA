import java.util.Comparator;

/**
 * Created by tanhaei on 15/6/3 AD.
 */
public class CustomComparator implements Comparator<Pattern> {
    @Override
    public int compare(Pattern o1, Pattern o2) {
        if(o1.rank == o2.rank) return 0;
        else return o1.rank < o2.rank ? 1 : -1;
    }
}