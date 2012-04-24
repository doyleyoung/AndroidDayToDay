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
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * Gather number of days and date and report date that is the result of adding
 * days to date
 * @author Doyle Young
 *
 */
public class FromDateFragment extends SherlockFragment implements OnDateSetListener {
  private static final String TAG = "FromDateFragment";

  private EditText numDaysText;
  private EditText fromDateText;
  private TextView answer;
  private ImageButton fromDateSelect;
  private Button resetButton;
  private Button submitButton;
  private Integer numDays;
  private String fromDate;
  
  private OnClickListener firstDateListener = new OnClickListener() {
    public void onClick(View v) {
      FragmentTransaction ft = getFragmentManager().beginTransaction();
      
      Fragment prev = getFragmentManager().findFragmentByTag(DatePickerDialogFragment.DATE_PICKER_ID);
      if (prev != null) {
        ft.remove(prev);
      }
      ft.addToBackStack(null);
      
      DialogFragment frag = new DatePickerDialogFragment(FromDateFragment.this);
      frag.show(ft, DatePickerDialogFragment.DATE_PICKER_ID);
    }
  };
  
  /*
   * Handles click on the reset button
   */
  private OnClickListener resetListener = new OnClickListener()
  {
    public void onClick(View v)
    {
    	fromDateText.setText("");
    	numDaysText.setText("");
    	answer.setText("");
    }
  };
  
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
    numDaysText.setFocusable(true);
    numDaysText.requestFocus();
    numDaysText.requestFocusFromTouch();
    fromDateText = (EditText)v.findViewById(R.id.from_date_input);
    answer = (TextView)v.findViewById(R.id.from_date_answer);
    
    fromDateSelect = (ImageButton)v.findViewById(R.id.from_date_select);
    fromDateSelect.setOnClickListener(firstDateListener);
    
    resetButton = (Button)v.findViewById(R.id.from_date_reset_button);
    resetButton.setOnClickListener(resetListener);
    
    submitButton = (Button)v.findViewById(R.id.from_date_submit_button);
    submitButton.setOnClickListener(submitListener);
    
    return v;
  }

  @Override
  public void onDateSet(DatePicker view, int year, int month, int day) {
    getFragmentManager().popBackStack();
    fromDateText.setText(month+1 + "/" + day + "/" + year);    
  }

}
