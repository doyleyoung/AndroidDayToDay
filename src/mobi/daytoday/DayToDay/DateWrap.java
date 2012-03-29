package mobi.daytoday.DayToDay;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateWrap {
  private DateWrap() {}
  
  // constants for millisecond time calculation
  private static final int SECOND = 1000;
  private static final int MINUTE = 60 * SECOND;
  private static final int HOUR = 60 * MINUTE;
  private static final int DAY = 24 * HOUR;
  
  public static String addToDate(String theDate, int value) throws ParseException {
    DateFormat dateForm = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
    Date date = dateForm.parse(theDate);
    GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("EST"), Locale.US);
    cal.setTime(date);
    cal.add(Calendar.DATE, value);
    return cal.get(Calendar.MONTH)+1 + "/" + cal.get(Calendar.DATE) + "/" + cal.get(Calendar.YEAR);
  }
  
  // XXX 17+ days math is wrong... WTF 
  public static int daysBetween(String dateOne, String dateTwo) throws ParseException {
    DateFormat dateForm = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
    Date firstDate = dateForm.parse(dateOne);
    Date secondDate = dateForm.parse(dateTwo);
    
    long difference = firstDate.getTime() - secondDate.getTime();
    
    return (int)(difference / DAY);
  }
}
