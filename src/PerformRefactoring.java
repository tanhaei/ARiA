import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * Created by tanhaei on 15/6/3 AD.
 */
public class PerformRefactoring {
    public PerformRefactoring() {
    }

    public static void createATLRuleFile(Pattern p, String metaModelPath, String metaModelName) {
        String code = p.GenerateATLFinalCode(metaModelPath, metaModelName);
        try {
            FileUtils.writeStringToFile(new File("temporary/" + p.name + ".ATL"), code);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
