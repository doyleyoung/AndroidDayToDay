/*
 * Copyright (C) 2012 - 2013 Doyle Young
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

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * Date math functions
 * 
 * @author Doyle Young
 * 
 */
public class DateWrap {
  public static final String DATE_FORMAT = "MM-dd-yyyy";
  public static final String CUR_DATE = "curDate";

  // constants for millisecond time calculation
  private static final int SECOND = 1000;
  private static final int MINUTE = 60 * SECOND;
  private static final int HOUR = 60 * MINUTE;
  private static final int DAY = 24 * HOUR;

  private static final DateFormat dateForm = new SimpleDateFormat(DATE_FORMAT,
      Locale.getDefault());

  private DateWrap() {
  }

  public static Date parseDate(String date) throws ParseException {
    return dateForm.parse(date);
  }
  
  /**
   * Add the number of days to the date and return the result
   * 
   * @param theDate
   *          - date in DATE_FORMAT format
   * @param numDays
   *          - number of days to add (may be negative for subtraction)
   * @return result of the addition in DATE_FORMAT format
   * @throws ParseException
   *           - if there is any error parsing the date
   */
  public static String addToDate(String theDate, int numDays)
      throws ParseException {
    Date date = dateForm.parse(theDate);
    GregorianCalendar cal = new GregorianCalendar(TimeZone.getDefault(),
        Locale.getDefault());
    cal.setTime(date);
    cal.add(Calendar.DATE, numDays);
    return (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE) + "-"
        + cal.get(Calendar.YEAR);
  }

  /**
   * Find the difference in days between the given dates and return the result
   * 
   * @param dateOne
   *          - date in DATE_FORMAT format
   * @param dateTwo
   *          - date in DATE_FORMAT format
   * @return number of days between the two given dates
   * @throws ParseException
   *           - if there is any error parsing a date
   */
  public static long daysBetween(String dateOne, String dateTwo)
      throws ParseException {
    Date firstDate = dateForm.parse(dateOne);
    Date secondDate = dateForm.parse(dateTwo);

    long difference = firstDate.getTime() - secondDate.getTime();

    return (Math.abs(difference) / DAY);
  }

  /**
   * Find the difference in weeks between the given dates and return the result
   * 
   * @param dateOne
   *          - date in DATE_FORMAT format
   * @param dateTwo
   *          - date in DATE_FORMAT format
   * @return number of weeks between the two given dates
   * @throws ParseException
   *           - if there is any error parsing a date
   */
  public static long weeksBetween(String dateOne, String dateTwo)
      throws ParseException {
    return (daysBetween(dateOne, dateTwo) / 7);
  }

  /**
   * Find the difference in months between the given dates and return the result
   * 
   * @param dateOne
   *          - date in DATE_FORMAT format
   * @param dateTwo
   *          - date in DATE_FORMAT format
   * @return number of months between the two given dates
   * @throws ParseException
   *           - if there is any error parsing a date
   */
  public static long monthsBetween(String dateOne, String dateTwo)
      throws ParseException {
    Calendar firstCal = Calendar.getInstance();
    firstCal.setTime(dateForm.parse(dateOne));
    Calendar secondCal = Calendar.getInstance();
    secondCal.setTime(dateForm.parse(dateTwo));

    // years to months
    long months = Math.abs(firstCal.get(Calendar.YEAR)
        - secondCal.get(Calendar.YEAR)) * 12;
    // difference in months
    months -= Math.abs(firstCal.get(Calendar.MONTH)
        - secondCal.get(Calendar.MONTH));

    return Math.abs(months);
  }

  /**
   * Find the difference between the two dates and express it in natural
   * language
   * 
   * @param dateOne
   *          - date in DATE_FORMAT format
   * @param dateTwo
   *          - date in DATE_FORMAT format
   * @return time between the two given dates in natural terms
   * @throws ParseException
   *           - if there is any error parsing a date
   */
  public static String naturalInterval(String dateOne, String dateTwo)
      throws ParseException {
    DateTimeFormatter dtForm = DateTimeFormat.forPattern(DATE_FORMAT);   

    DateTime firstDate = dtForm.parseDateTime(dateOne);
    DateTime secondDate = dtForm.parseDateTime(dateTwo);

    Period period = new Period(firstDate, secondDate);
    
    PeriodFormatter formatter = new PeriodFormatterBuilder()
    .appendYears().appendSuffix(" year ", " years ")
    .appendMonths().appendSuffix(" month ", " months ")
    .appendWeeks().appendSuffix(" week ", " weeks ")
    .appendDays().appendSuffix(" day", " days")
    .printZeroNever()
    .toFormatter();
    
    if(formatter.print(period).matches(".*-.*")) {
      return formatter.print(period.negated());
    }
    
    return formatter.print(period);
  }
}
