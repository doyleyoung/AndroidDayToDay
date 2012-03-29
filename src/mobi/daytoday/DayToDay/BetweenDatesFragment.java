package mobi.daytoday.DayToDay;

import java.text.ParseException;

import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.SherlockFragment;

public class BetweenDatesFragment extends SherlockFragment implements OnDateSetListener {
  private static final String TAG = "BetweenDatesFragment";
  
  private EditText firstDateText;
  private EditText secondDateText;
  private Object daysWeeksSwitch;
  private TextView answer;
  private ImageButton firstDateSelect;
  private ImageButton secondDateSelect;
  private Button submitButton;
  private String firstDate;
  private String secondDate;
  private boolean firstActive;
  private boolean secondActive;
    
  private OnClickListener firstDateListener = new OnClickListener() {
    public void onClick(View v) {
      FragmentTransaction ft = getFragmentManager().beginTransaction();
      
      Fragment prev = getFragmentManager().findFragmentByTag(DatePickerDialogFragment.DATE_PICKER_ID);
      if (prev != null) {
        ft.remove(prev);
        firstActive = secondActive = false;
      }
      ft.addToBackStack(null);
      
      DialogFragment frag = new DatePickerDialogFragment(BetweenDatesFragment.this);
      firstActive = true;
      frag.show(ft, DatePickerDialogFragment.DATE_PICKER_ID);
    }
  };
  
  private OnClickListener secondDateListener = new OnClickListener() {
    public void onClick(View v) {
      FragmentTransaction ft = getFragmentManager().beginTransaction();
      
      Fragment prev = getFragmentManager().findFragmentByTag(DatePickerDialogFragment.DATE_PICKER_ID);
      if (prev != null) {
        ft.remove(prev);
        firstActive = secondActive = false;
      }
      ft.addToBackStack(null);
      
      DialogFragment frag = new DatePickerDialogFragment(BetweenDatesFragment.this);
      secondActive = true;
      frag.show(ft, DatePickerDialogFragment.DATE_PICKER_ID);
    }
  };
  
  private OnClickListener submitListener = new OnClickListener()
  {
    public void onClick(View v)
    {
      try {
        firstDate = firstDateText.getText().toString();
        secondDate = secondDateText.getText().toString();
        
        Log.v(TAG, firstDate + " " + secondDate);
                
        int numDays = DateWrap.daysBetween(firstDate, secondDate);
        if(numDays < 0) {
          numDays = numDays * -1;
        }
        
        Log.v(TAG, "numDays: " + numDays);
        
        boolean isWeeks;
        Log.v(TAG, "class: " + daysWeeksSwitch.getClass());
        if(daysWeeksSwitch.getClass().equals(ToggleButton.class)) {
          isWeeks = ((ToggleButton)daysWeeksSwitch).isChecked();
        } else {
          isWeeks = ((Switch)daysWeeksSwitch).isChecked();
        }
        
        if(isWeeks) {
          Log.v(TAG, "weeks");
          answer.setText(String.valueOf(numDays / 7));
        } else {
          Log.v(TAG, "days");
          answer.setText(String.valueOf(numDays));
        }

      } catch (ParseException e) {
        // XXX invalid num days
        Log.v(TAG, "invalid date");
      }
      
    }
  };
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    firstActive = false;
    secondActive = false;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.between_dates, container, false);
    
    firstDateText = (EditText)v.findViewById(R.id.first_date_input);
    secondDateText = (EditText)v.findViewById(R.id.second_date_input);
    daysWeeksSwitch = (Object)v.findViewById(R.id.days_weeks_switch);
    answer = (TextView)v.findViewById(R.id.between_dates_answer);
    
    firstDateSelect = (ImageButton)v.findViewById(R.id.first_date_select);
    firstDateSelect.setOnClickListener(firstDateListener);
    
    secondDateSelect = (ImageButton)v.findViewById(R.id.second_date_select);
    secondDateSelect.setOnClickListener(secondDateListener);
    
    submitButton = (Button)v.findViewById(R.id.between_dates_button);
    submitButton.setOnClickListener(submitListener);
    
    return v;
  }

  @Override
  public void onDateSet(DatePicker view, int year, int month, int day) {
    getFragmentManager().popBackStack();
    if(firstActive) {
      firstActive = false;
      firstDateText.setText(month+1 + "/" + day + "/" + year);
    } else if(secondActive) {
      secondActive = false;
      secondDateText.setText(month+1 + "/" + day + "/" + year);
    }
  }
}