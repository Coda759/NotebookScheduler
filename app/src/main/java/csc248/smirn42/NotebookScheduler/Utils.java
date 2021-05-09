package csc248.smirn42.NotebookScheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static boolean isValidTimeMillis(String dueDate) {
        String numberCheckRegex = "[0-9]+";
        Pattern p = Pattern.compile(numberCheckRegex);
        Matcher matcher = p.matcher(dueDate);
        return matcher.matches();
    }

    public static long getTimeMillis(String dueDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date date;
        try {
            date = sdf.parse(dueDate);
            return date.getTime();
        } catch (ParseException e) {
            return 0;
        }
    }
}
