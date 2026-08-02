package taskApp.util;

public class StringUtil {

    public static String cleanJson(String jsnString) {
        String cleanString = jsnString.trim();
        if ((cleanString.startsWith("\"") && cleanString.endsWith("\"")) ||
                (cleanString.startsWith("'") && cleanString.endsWith("'"))) {
            cleanString = cleanString.substring(1, cleanString.length() - 1);

        }
        return cleanString;
    }

}
