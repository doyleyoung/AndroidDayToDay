package mobi.ritf.DayToDay;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class FromDateFragment extends SherlockFragment {
  private static final String TAG = "FromDateFragment";

  private EditText numDaysText;
  private EditText fromDateText;
  private TextView answer;
  private Button submitButton;
  private Integer numDays;
  private String fromDate;
  
  private OnClickListener submitListener = new OnClickListener()
  {
    public void onClick(View v)
    {
      // XXX test month, day and year for sane values java lets them be anything 58/58/2012
//    String[] date = fromDate.split("/");
//    Log.v(TAG, "date: " + date[0] + " " + date[1] + " " + date[2]);
//    GregorianCalendar cal = new GregorianCalendar(Integer.valueOf(date[2]),
//        Integer.valueOf(date[0]),
//        Integer.valueOf(date[1]));
      
      try {
        numDays = Integer.valueOf(numDaysText.getText().toString());
        fromDate = fromDateText.getText().toString();
        Log.v(TAG, numDays + " " + fromDate);
        
        answer.setText(DateWrap.addToDate(fromDate, numDays));
      } catch(NumberFormatException e) {
        // XXX invalid num days
        Log.v(TAG, "invalid num days");
      
      } catch (ParseException e) {
        // XXX invalid num days
        Log.v(TAG, "invalid date");
      }

    }
  };

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    numDays = 0;
    fromDate = "";
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.from_date, container, false);
    
    numDaysText = (EditText)v.findViewById(R.id.num_days_input);
    fromDateText = (EditText)v.findViewById(R.id.from_date_input);
    answer = (TextView)v.findViewById(R.id.from_date_answer);
    
    submitButton = (Button)v.findViewById(R.id.from_date_button);
    submitButton.setOnClickListener(submitListener);
    
    return v;
  }

}
