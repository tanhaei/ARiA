/**
 * Created by tanhaei on 15/6/1 AD.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class RefactoringGoal {

    public String ID;
    public List<String> qualities = new ArrayList<String>();


    public RefactoringGoal(String id) {
        this.ID = id;
    }


    private static List<RefactoringGoal> RefactoringGoals = new ArrayList<RefactoringGoal>();

    public static RefactoringGoal searchById(String id) {
        if (RefactoringGoals == null) return null;
        Iterator<RefactoringGoal> it = RefactoringGoals.iterator();
        while (it.hasNext()) {
            RefactoringGoal refgoal = it.next();
            if (refgoal.ID.equals(id)) {
                return refgoal;
            }
        }
        return null;
    }

    public static void addToRefactoringGoals(RefactoringGoal rg) {
        RefactoringGoals.add(rg);
    }
}
