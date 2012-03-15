package mobi.ritf.DayToDay;

import java.text.ParseException;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.SherlockFragment;

public class BetweenDatesFragment extends SherlockFragment {
  private static final String TAG = "BetweenDatesFragment";

  private EditText firstDateText;
  private EditText secondDateText;
  private Object daysWeeksSwitch;
  private TextView answer;
  private Button submitButton;
  private String firstDate;
  private String secondDate;
  
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
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.between_dates, container, false);
    
    firstDateText = (EditText)v.findViewById(R.id.first_date_input);
    secondDateText = (EditText)v.findViewById(R.id.second_date_input);
    daysWeeksSwitch = (Object)v.findViewById(R.id.days_weeks_switch);
    answer = (TextView)v.findViewById(R.id.between_dates_answer);
    
    submitButton = (Button)v.findViewById(R.id.between_dates_button);
    submitButton.setOnClickListener(submitListener);
    
    return v;
  }
}
