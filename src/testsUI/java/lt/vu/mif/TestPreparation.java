package lt.vu.mif;

import java.util.HashMap;
import java.util.Map;

public class TestPreparation {

    private static Map<String, Boolean> alreadySetupMap = new HashMap<>();

    public static void setSetuped(String identifier) {
        alreadySetupMap.put(identifier, true);
    }

    public static boolean getSetuped(String identifier) {
        if (alreadySetupMap.containsKey(identifier)) {
            return alreadySetupMap.get(identifier);
        }
        return false;
    }
}
