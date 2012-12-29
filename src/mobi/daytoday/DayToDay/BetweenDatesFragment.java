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

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

import java.text.ParseException;

import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * Gather two dates and the form of the response and report the time between the
 * dates
 * 
 * @author Doyle Young
 * 
 */
public class BetweenDatesFragment extends SherlockFragment implements
    OnDateSetListener {
  private static final String TAG = "BetweenDatesFragment";

  private enum AnswerInType {
    DAYS, WEEKS, MONTHS, NATURAL, AGE
  }

  private EditText firstDateInput;
  private EditText secondDateInput;
  private Spinner answerInSpinner;
  private AnswerInType answerType;
  private TextView answer;
  private ImageButton firstDateSelect;
  private ImageButton secondDateSelect;
  private Button resetButton;
  private String firstDate;
  private String secondDate;
  private boolean firstActive;
  private boolean secondActive;
  private Boolean resetVisible;

  /*
   * Handles click on the first date select button
   */
  private OnClickListener firstDateListener = new OnClickListener() {
    public void onClick(View v) {
      FragmentTransaction ft = getFragmentManager().beginTransaction();

      Fragment prev = getFragmentManager().findFragmentByTag(
          DatePickerDialogFragment.DATE_PICKER_ID);
      if (prev != null) {
        ft.remove(prev);
        firstActive = secondActive = false;
      }
      ft.addToBackStack(null);

      firstActive = true;
      DialogFragment frag = new DatePickerDialogFragment(
          BetweenDatesFragment.this);
      Bundle args = new Bundle();
      args.putString(DateWrap.CUR_DATE, firstDateInput.getText().toString());
      frag.setArguments(args);
      frag.show(ft, DatePickerDialogFragment.DATE_PICKER_ID);
    }
  };

  /*
   * Handles click on the second date select button
   */
  private OnClickListener secondDateListener = new OnClickListener() {
    public void onClick(View v) {
      FragmentTransaction ft = getFragmentManager().beginTransaction();

      Fragment prev = getFragmentManager().findFragmentByTag(
          DatePickerDialogFragment.DATE_PICKER_ID);
      if (prev != null) {
        ft.remove(prev);
        firstActive = secondActive = false;
      }
      ft.addToBackStack(null);

      secondActive = true;
      DialogFragment frag = new DatePickerDialogFragment(
          BetweenDatesFragment.this);
      Bundle args = new Bundle();
      args.putString(DateWrap.CUR_DATE, secondDateInput.getText().toString());
      frag.setArguments(args);
      frag.show(ft, DatePickerDialogFragment.DATE_PICKER_ID);
    }
  };

  /*
   * Handles interaction with answer in spinner
   */
  private OnItemSelectedListener answerInSelectedListener = new OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
        long id) {
      answerType = AnswerInType.values()[(int) id];
      Log.v(TAG, "selected " + id + " type: " + answerType);
      findBetween();
      fadeInResetButton();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
      Log.v(TAG, "nothing selected");
      answerType = AnswerInType.DAYS;
      findBetween();
      fadeInResetButton();
    }
  };

  /*
   * Handles click on the reset button
   */
  private OnClickListener resetListener = new OnClickListener() {
    public void onClick(View v) {
      firstDateInput.setText("");
      secondDateInput.setText("");
      answer.setText("");
      
      animate(resetButton).setDuration(800).alphaBy(0.25f).alpha(1).alpha(0);
      resetVisible = false;
    }
  };

  
  private OnEditorActionListener dateEditListener = new OnEditorActionListener() {
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
      findBetween();
      fadeInResetButton();
      return false;
    }
  };

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    firstActive = false;
    secondActive = false;
  }

  /**
   * Create the view, set up our key objects and assign the listeners
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.between_dates, container, false);

    firstDateInput = (EditText) v.findViewById(R.id.first_date_input);
    firstDateInput.setFocusable(true);
    firstDateInput.requestFocus();
    firstDateInput.requestFocusFromTouch();
    firstDateInput.setOnEditorActionListener(dateEditListener);

    secondDateInput = (EditText) v.findViewById(R.id.second_date_input);
    secondDateInput.setOnEditorActionListener(dateEditListener);
    
    answer = (TextView) v.findViewById(R.id.between_dates_answer);

    answerInSpinner = (Spinner) v.findViewById(R.id.answer_in_spinner);
    ArrayAdapter<CharSequence> answerInAdapter = ArrayAdapter
        .createFromResource(getActivity(), R.array.answer_in_array,
            android.R.layout.simple_spinner_item);
    answerInAdapter
        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    answerInSpinner.setAdapter(answerInAdapter);
    answerInSpinner.setOnItemSelectedListener(answerInSelectedListener);

    firstDateSelect = (ImageButton) v.findViewById(R.id.first_date_select);
    firstDateSelect.setOnClickListener(firstDateListener);

    secondDateSelect = (ImageButton) v.findViewById(R.id.second_date_select);
    secondDateSelect.setOnClickListener(secondDateListener);

    resetButton = (Button) v.findViewById(R.id.between_dates_reset_button);
    resetButton.setOnClickListener(resetListener);
    resetButton.setVisibility(View.INVISIBLE);
    resetVisible = false;

    return v;
  }

  /**
   * Set the date based on the selected button
   */
  @Override
  public void onDateSet(DatePicker view, int year, int month, int day) {
    getFragmentManager().popBackStack();
    if (firstActive) {
      firstDateInput.setText(String.format(getString(R.string.date_format), month + 1, day, year));
    } else if (secondActive) {
      secondDateInput.setText(String.format(getString(R.string.date_format), month + 1, day, year));
    }
    
    findBetween();
    
    firstActive = false;
    secondActive = false;
  }
  
  private void fadeInResetButton() {
    if(!resetVisible) {
      resetButton.setVisibility(View.VISIBLE);
      animate(resetButton).setDuration(800).alphaBy(0.25f).alpha(0).alpha(1);
      resetVisible = true;
    }
  }

  private void findBetween() {
    if(!"".equals(firstDateInput.getText().toString()) &&
       !"".equals(secondDateInput.getText().toString())) {
      try {
        firstDate = firstDateInput.getText().toString();
        secondDate = secondDateInput.getText().toString();

        Log.v(TAG, firstDate + " " + secondDate);

        switch (answerType) {
        case DAYS:
          answer.setText(
              String.valueOf(DateWrap.daysBetween(firstDate, secondDate)));
          break;
        case WEEKS:
          answer.setText(
              String.valueOf(DateWrap.weeksBetween(firstDate, secondDate)));
          break;
        case MONTHS:
          answer.setText(
              String.valueOf(DateWrap.monthsBetween(firstDate, secondDate)));
          break;
        case NATURAL:
          answer.setText(DateWrap.naturalInterval(firstDate, secondDate));
          break;
        }

      } catch (ParseException e) {
        ((AndDayToDayActivity) getActivity()).showAlert(getString(R.string.date_error));
        if(firstActive) {
          firstDateInput.setText("");
        }
        
        if(secondActive) {
          secondDateInput.setText("");
        }
      }
    }
  }
}
