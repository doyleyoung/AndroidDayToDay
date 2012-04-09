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

import java.util.Calendar;

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
    Calendar now = Calendar.getInstance();

    Log.v(TAG, "year: " + now.get(Calendar.YEAR) + " month: " + now.get(Calendar.MONTH) + " day: " + now.get(Calendar.DATE));

    return new DatePickerDialog(getActivity(),
        (OnDateSetListener)frag,
        now.get(Calendar.YEAR),
        now.get(Calendar.MONTH),
        now.get(Calendar.DATE));
  }
}
