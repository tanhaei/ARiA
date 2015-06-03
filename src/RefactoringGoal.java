/**
 * Created by tanhaei on 15/6/1 AD.
 */

import java.util.ArrayList;
import java.util.List;

public class RefactoringGoal {

    public String ID;
    public List<String> qualities = new ArrayList<String>();


    public RefactoringGoal(String id) {
        this.ID = id;
    }


    public static List<RefactoringGoal> RefactoringGoals = new ArrayList<RefactoringGoal>();

    public static RefactoringGoal searchById(String id) {
        if (RefactoringGoals == null) return null;
        for (RefactoringGoal refgoal : RefactoringGoals) {
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
