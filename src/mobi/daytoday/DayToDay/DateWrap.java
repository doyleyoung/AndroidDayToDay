/*
* Copyright (C) 2012 Doyle Young
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package mobi.daytoday.DayToDay;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Date math functions
 * @author Doyle Young
 *
 */
public class DateWrap {
  private DateWrap() {}
  
  // constants for millisecond time calculation
  private static final int SECOND = 1000;
  private static final int MINUTE = 60 * SECOND;
  private static final int HOUR = 60 * MINUTE;
  private static final int DAY = 24 * HOUR;
  
  public static final String DATE_FORMAT = "MM/dd/yyyy";
  
  /**
   * Add the number of days to the date and return the result
   * @param theDate - date in DATE_FORMAT format
   * @param numDays - number of days to add (may be negative for subtraction)
   * @return result of the addition in DATE_FORMAT format
   * @throws ParseException - if there is any error parsing the date
   */
  public static String addToDate(String theDate, int numDays) throws ParseException {
    DateFormat dateForm = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    Date date = dateForm.parse(theDate);
    GregorianCalendar cal = new GregorianCalendar(TimeZone.getDefault(), Locale.getDefault());
    cal.setTime(date);
    cal.add(Calendar.DATE, numDays);
    return (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE) + "/" + cal.get(Calendar.YEAR);
  }
  
  /**
   * Find the difference in days between the given dates and return the result
   * @param dateOne - date in DATE_FORMAT format
   * @param dateTwo - date in DATE_FORMAT format
   * @return number of days between the two given dates
   * @throws ParseException - if there is any error parsing the date
   */
  public static int daysBetween(String dateOne, String dateTwo) throws ParseException {
    DateFormat dateForm = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    Date firstDate = dateForm.parse(dateOne);
    Date secondDate = dateForm.parse(dateTwo);
    
    long difference = firstDate.getTime() - secondDate.getTime();
    
    return (int)(difference / DAY);
  }
}
