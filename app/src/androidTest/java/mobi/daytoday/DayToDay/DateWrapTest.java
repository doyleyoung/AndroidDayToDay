package mobi.daytoday.DayToDay;

import junit.framework.TestCase;

import org.joda.time.DateTime;

public class DateWrapTest extends TestCase {
  private static final String NEW_YEARS_DAY = "01-01-2014";
  private static final String DATE_ONE = "11-26-2013";
  private static final String DATE_TWO = "01-19-2038";

  public void testParseDate() throws Exception {
    // Given
    String toParse = DATE_ONE;
    
    // When
    DateTime date = DateWrap.parseDate(toParse);
    
    // Then
    assertEquals(26, date.getDayOfMonth());
    assertEquals(11, date.getMonthOfYear());
    assertEquals(2013, date.getYear());
  }

  public void testShouldThrowException() {
    try {
      DateWrap.parseDate("abcdefgh");
      fail();
    } catch (Exception e) {
      assertEquals("class java.lang.IllegalArgumentException", e.getClass().toString());
    }
  }

  public void testAddToDate() throws Exception {
    // When
    String result = DateWrap.addToDate(DATE_ONE, 30);
    
    // Then
    assertEquals(result, "12-26-2013");
  }

  public void testDaysBetween() throws Exception {
    // When
    long result = DateWrap.daysBetween(DATE_ONE, DATE_TWO);
    
    // Then
    assertEquals(8820, result);
  }

  public void testWeeksBetween() throws Exception {
    // When
    long result = DateWrap.weeksBetween(DATE_ONE, DATE_TWO);
    
    // Then
    assertEquals(1260, result);
  }

  public void testMonthsBetween() throws Exception {
    // When
    long result = DateWrap.monthsBetween(DATE_ONE, DATE_TWO);
    
    // Then
    assertEquals(289, result);
  }

  public void testSameMonthMonthsBetween() throws Exception {
    // When
    long result = DateWrap.monthsBetween(NEW_YEARS_DAY, "01-31-2014");
    
    // Then
    assertEquals(0, result);
  }

  public void testNegativeOrderMonthsBetween() throws Exception {
    // When
    long result = DateWrap.monthsBetween(DATE_TWO, DATE_ONE);
    
    // Then
    assertEquals(289, result);
  }

  public void testSameYearMonthsBetween() throws Exception {
    // When
    long result = DateWrap.monthsBetween(NEW_YEARS_DAY, "07-04-2014");
    
    // Then
    assertEquals(6, result);
  }

  public void testOneYearMonthsBetween() throws Exception {
    // When
    long result = DateWrap.monthsBetween(NEW_YEARS_DAY, "01-01-2015");
    
    // Then
    assertEquals(12, result);
  }

  public void testNaturalLanguage() throws Exception {
    // When
    String result = DateWrap.naturalInterval(DATE_ONE, DATE_TWO);
    
    // Then
    assertEquals("24 years 1 month 3 weeks 3 days", result);
  }

  public void testNaturalLanguageDay() throws Exception {
    // When
    String result = DateWrap.naturalInterval(NEW_YEARS_DAY, "01-02-2014");
        
    // Then
    assertEquals("1 day", result);
  }

  public void testNaturalLanguageDays() throws Exception {
    // When
    String result = DateWrap.naturalInterval(NEW_YEARS_DAY, "01-05-2014");
        
    // Then
    assertEquals("4 days", result);
  }

  public void testNaturalLanguageWeek() throws Exception {
    // When
    String result = DateWrap.naturalInterval(NEW_YEARS_DAY, "01-08-2014");
        
    // Then
    assertEquals("1 week ", result);
  }

  public void testNaturalLanguageWeeks() throws Exception {
    // When
    String result = DateWrap.naturalInterval(NEW_YEARS_DAY, "01-15-2014");
        
    // Then
    assertEquals("2 weeks ", result);
  }

  public void testNaturalLanguageMonth() throws Exception {
    // When
    String result = DateWrap.naturalInterval(NEW_YEARS_DAY, "02-01-2014");
        
    // Then
    assertEquals("1 month ", result);
  }

  public void testNaturalLanguageMonths() throws Exception {
    // When
    String result = DateWrap.naturalInterval(NEW_YEARS_DAY, "05-01-2014");
        
    // Then
    assertEquals("4 months ", result);
  }

  public void testNaturalLanguageYear() throws Exception {
    // When
    String result = DateWrap.naturalInterval(NEW_YEARS_DAY, "01-01-2015");
        
    // Then
    assertEquals("1 year ", result);
  }

  public void testNaturalLanguageYears() throws Exception {
    // When
    String result = DateWrap.naturalInterval(NEW_YEARS_DAY, "01-01-2019");
        
    // Then
    assertEquals("5 years ", result);
  }
}
