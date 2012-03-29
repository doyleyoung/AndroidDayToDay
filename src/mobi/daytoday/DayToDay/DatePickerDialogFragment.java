package mobi.daytoday.DayToDay;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockFragment;

public class DatePickerDialogFragment extends SherlockDialogFragment {
  private static final String TAG = "DatePickerFragment";
  private SherlockFragment frag;

  public static final String DATE_PICKER_ID = "datepicker";

  public DatePickerDialogFragment(SherlockFragment callback) {
    frag = callback;
  }

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
