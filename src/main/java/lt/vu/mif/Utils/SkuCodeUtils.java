package lt.vu.mif.Utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SkuCodeUtils {

    // TODO: šitie požymiai bus naudojami vėliau, implementavus papildomus reikalavimus, susijusius su kategorijomis, spalvomis, dydžiais ir t.t. ir tikriausiai bus pakeisti į ENUM
    private final String COLOR_BLACK = "bk";
    private final String COLOR_BLUE = "bl";
    private final String COLOR_GREEN = "gn";
    private final String COLOR_RED = "rd";
    private final String COLOR_YELLOW = "yl";
    private final String COLOR_WHITE = "wt";

    private final String SIZE_SMALL = "S";
    private final String SIZE_MEDIUM = "M";
    private final String SIZE_LARGE = "L";

    public static String generateSkuCode(String itemName){
        String sku = "";

        itemName = itemName.replaceAll(",", " ");           // Replaces commas with whitespaces
        itemName = itemName.replaceAll("-", " ");           // Replaces dashes with whitespaces
        itemName = itemName.trim().replaceAll(" +", " ");   // Replaces more than one whitespaces with only one

        for(String s : itemName.split(" "))
        {
            if(s.length() >= 5){
                sku += s.substring(0, 4) + "_";
            }
            else if(s.length() >= 3){
                sku += s.substring(0, 3) + "_";
            }
            else if(s.length() == 2){
                sku += s.substring(0, 2) + "_";
            }
            else sku += s.charAt(0) + "_";
        }
        sku = sku.substring(0, sku.length() - 1);

        return sku;
    }
}
