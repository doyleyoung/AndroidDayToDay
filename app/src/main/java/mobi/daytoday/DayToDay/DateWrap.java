/*
 * Copyright (C) 2012 - 2014 Doyle Young
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

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.Months;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import static org.joda.time.Duration.standardDays;

/**
 * Date math functions
 * 
 * @author Doyle Young
 * 
 */
public class DateWrap {
  public static final String DATE_FORMAT = "MM-dd-yyyy";
  public static final String CUR_DATE = "curDate";

  private static final DateTimeFormatter dtForm = DateTimeFormat
      .forPattern(DATE_FORMAT);

  private DateWrap() {
  }

  public static DateTime parseDate(String date) throws Exception {
    return dtForm.parseDateTime(date);
  }

  /**
   * Add the number of days to the date and return the result
   * 
   * @param theDate
   *          - date in DATE_FORMAT format
   * @param numDays
   *          - number of days to add (may be negative for subtraction)
   * @return result of the addition in DATE_FORMAT format
   * @throws Exception
   *           - if there is any error
   */
  public static String addToDate(String theDate, int numDays) throws Exception {
    DateTime date = dtForm.parseDateTime(theDate);
    Duration days = standardDays(numDays);

    DateTime result = date.plus(days);
    DateTimeFormatter MonthDayYearFormat = new DateTimeFormatterBuilder()
        .appendMonthOfYear(1).appendLiteral('-').appendDayOfMonth(1)
        .appendLiteral('-').appendYear(4, 4).toFormatter();

    return MonthDayYearFormat.print(result);
  }

  /**
   * Find the difference in days between the given dates and return the result
   * 
   * @param dateOne
   *          - date in DATE_FORMAT format
   * @param dateTwo
   *          - date in DATE_FORMAT format
   * @return number of days between the two given dates
   * @throws Exception
   *           - if there is any error
   */
  public static long daysBetween(String dateOne, String dateTwo)
      throws Exception {
    DateTime firstDate = dtForm.parseDateTime(dateOne);
    DateTime secondDate = dtForm.parseDateTime(dateTwo);

    Interval difference;
    if (firstDate.isAfter(secondDate)) {
      difference = new Interval(secondDate, firstDate);
    } else {
      difference = new Interval(firstDate, secondDate);
    }

    return difference.toDuration().getStandardDays();
  }

  /**
   * Find the difference in weeks between the given dates and return the result
   * 
   * @param dateOne
   *          - date in DATE_FORMAT format
   * @param dateTwo
   *          - date in DATE_FORMAT format
   * @return number of weeks between the two given dates
   * @throws Exception
   *           - if there is any error
   */
  public static long weeksBetween(String dateOne, String dateTwo)
      throws Exception {
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
   * @throws Exception
   *           - if there is any error
   */
  public static long monthsBetween(String dateOne, String dateTwo)
      throws Exception {
    DateTime firstDate = dtForm.parseDateTime(dateOne);
    DateTime secondDate = dtForm.parseDateTime(dateTwo);

    return Math.abs(Months.monthsBetween(firstDate, secondDate).getMonths());
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
   * @throws Exception
   *           - if there is any error
   */
  public static String naturalInterval(String dateOne, String dateTwo)
      throws Exception {
    DateTime firstDate = dtForm.parseDateTime(dateOne);
    DateTime secondDate = dtForm.parseDateTime(dateTwo);

    Period period = new Period(firstDate, secondDate);

    PeriodFormatter formatter = new PeriodFormatterBuilder().appendYears()
        .appendSuffix(" year ", " years ").appendMonths()
        .appendSuffix(" month ", " months ").appendWeeks()
        .appendSuffix(" week ", " weeks ").appendDays()
        .appendSuffix(" day", " days").printZeroNever().toFormatter();

    if (formatter.print(period).matches(".*-.*")) {
      return formatter.print(period.negated());
    }

    return formatter.print(period);
  }
}
