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

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockFragment;

/**
 * Date selection and callback dialog fragment
 * @author Doyle Young
 *
 */
public class DatePickerDialogFragment extends SherlockDialogFragment {
  private static final String TAG = "DatePickerFragment";
  private SherlockFragment frag;

  public static final String DATE_PICKER_ID = "datepicker";

  /**
   * Constructor
   * @param callback - callback fragment (must implement OnDateSetListener)
   */
  public DatePickerDialogFragment(SherlockFragment callback) {
    frag = callback;
  }

  /**
   * Create and return the date picker dialog
   */
  public Dialog onCreateDialog(Bundle arg) {
    String current = getArguments().getString("curDate");
    Calendar cal;
    
    if(current == "") {
      cal = GregorianCalendar.getInstance(TimeZone.getDefault(),
          Locale.getDefault());

      Log.v(TAG, "year: " + cal.get(Calendar.YEAR) + " month: " + cal.get(Calendar.MONTH) + " day: " + cal.get(Calendar.DATE));

    } else {
      try {
        Date date = DateWrap.parseDate(current);
        cal = new GregorianCalendar(TimeZone.getDefault(),
            Locale.getDefault());
        cal.setTime(date);

      } catch (ParseException e) {
        // just ignore it and use now
        cal = GregorianCalendar.getInstance(TimeZone.getDefault(),
            Locale.getDefault());
      }
    }
    
    return new DatePickerDialog(getActivity(),
        (OnDateSetListener)frag,
        cal.get(Calendar.YEAR),
        cal.get(Calendar.MONTH),
        cal.get(Calendar.DATE));
  }
}
