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

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.util.Log;

import org.joda.time.DateTime;

/**
 * Date selection and callback dialog fragment
 * @author Doyle Young
 *
 */
public class DatePickerDialogFragment extends DialogFragment {
  private static final String TAG = "DatePickerFragment";
  private Fragment frag;

  public static final String DATE_PICKER_ID = "datepicker";

  /**
   * Constructor - Fragment requires public empty constructor
   */
  public DatePickerDialogFragment() {
    // nothing to see here
  }
  
  /**
   * Set the fragment that will handle the OnDateSetListener callback
   * @param callback - callback fragment
   */  
  public void setCallbackFragment(Fragment callback) {
    frag = callback;
  }
  
  /**
   * Create and return the date picker dialog
   */
  public Dialog onCreateDialog(Bundle arg) {
    String current = getArguments().getString("curDate");
    DateTime dt;
    
    if("".equals(current)) {
      dt = new DateTime();
      
      Log.v(TAG, "year: " + dt.getYear() + " month: " + dt.getMonthOfYear() + " day: " + dt.getDayOfMonth());

    } else {
      try {
        dt = DateWrap.parseDate(current);
      } catch (Exception e) {
        // just ignore it and use now
        dt = new DateTime();
      }
    }
    
    return new DatePickerDialog(getActivity(),
        (OnDateSetListener)frag,
        dt.getYear(),
        dt.getMonthOfYear()-1,
        dt.getDayOfMonth());
  }
}
