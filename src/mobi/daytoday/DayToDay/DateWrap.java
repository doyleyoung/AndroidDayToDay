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
  
  public static String addToDate(String theDate, int numDays) throws ParseException {
    DateFormat dateForm = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
    Date date = dateForm.parse(theDate);
    GregorianCalendar cal = new GregorianCalendar(TimeZone.getDefault(), Locale.getDefault());
    cal.setTime(date);
    cal.add(Calendar.DATE, numDays);
    return (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE) + "/" + cal.get(Calendar.YEAR);
  }
  
  public static int daysBetween(String dateOne, String dateTwo) throws ParseException {
    DateFormat dateForm = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
    Date firstDate = dateForm.parse(dateOne);
    Date secondDate = dateForm.parse(dateTwo);
    
    long difference = firstDate.getTime() - secondDate.getTime();
    
    return (int)(difference / DAY);
  }
}
