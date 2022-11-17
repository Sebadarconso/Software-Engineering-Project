package sample;

import java.util.HashSet;
import java.util.Set;

public enum CategorieCittadini {
    PRIMO_ANNO, SECONDO_ANNO, BAMBINI_5_6_ANNI, ADOLESCENTI_11_18_ANNI, ADULTI_19_64_ANNI,
    OVER_65, DONNE_FERTILI_GRAVIDANZA, A_RISCHIO_PATOLOGIA, A_RISCHIO_PROFESSIONALE, VIAGGIATORI_INTERNAZIONALI;

    public static String toString(CategorieCittadini category) {
        switch(category.ordinal()) {
            case 0:
                return "Primo anno";
            case 1:
                return "Secondo anno";
            case 2:
                return "5-6 Anni";
            case 3:
                return "11-18 Anni";
            case 4:
                return "19-64 Anni";
            case 5:
                return "Over 65";
            case 6:
                return "Donna Fertile-Gravidanza";
            case 7:
                return "A rischio per patologia";
            case 8:
                return "A rischio per professione";
            case 9:
                return "Viaggiatori internazionali";
            default:
                return null;

        }

    }
    public static CategorieCittadini toCategory(String categoryString) {
        if (categoryString.equals("Primo anno"))
            return CategorieCittadini.PRIMO_ANNO;
        else if(categoryString.equals("Secondo anno"))
            return CategorieCittadini.SECONDO_ANNO;
        else if(categoryString.equals("5-6 Anni"))
            return CategorieCittadini.BAMBINI_5_6_ANNI;
        else if(categoryString.equals("11-18 Anni"))
            return CategorieCittadini.ADOLESCENTI_11_18_ANNI;
        else if(categoryString.equals("19-64 Anni"))
            return CategorieCittadini.ADULTI_19_64_ANNI;
        else if(categoryString.equals("Over 65"))
            return CategorieCittadini.OVER_65;
        else if(categoryString.equals("Donna Fertile-Gravidanza"))
            return CategorieCittadini.DONNE_FERTILI_GRAVIDANZA;
        else if(categoryString.equals("A rischio per patologia"))
            return CategorieCittadini.A_RISCHIO_PATOLOGIA;
        else if(categoryString.equals("A rischio per professione"))
            return CategorieCittadini.A_RISCHIO_PROFESSIONALE;
        else if(categoryString.equals("Viaggiatori internazionali"))
            return CategorieCittadini.VIAGGIATORI_INTERNAZIONALI;
        else
            return null;
    }

    public static Set<CategorieCittadini> parseCategories(String categories) {
        Set<CategorieCittadini> retSet = new HashSet<>();
        if (!(categories.equals("null"))) {
            String ctg[] = categories.split("/");
            for (String s : ctg) {
                retSet.add(CategorieCittadini.valueOf(s));
            }
        }
        return retSet;
    }
    public static String categoriesToString(Set<CategorieCittadini> categories) {
        String retString = "";
        for (CategorieCittadini ctg:categories) {
            retString = retString + ctg + "/";
        }
        if (categories.isEmpty())
            return "null";
        return retString.substring(0, retString.length() - 1);
    }
}
