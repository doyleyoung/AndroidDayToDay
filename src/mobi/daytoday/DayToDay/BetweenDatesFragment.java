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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

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
  private Button submitButton;
  private String firstDate;
  private String secondDate;
  private boolean firstActive;
  private boolean secondActive;

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

      DialogFragment frag = new DatePickerDialogFragment(
          BetweenDatesFragment.this);
      firstActive = true;
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

      DialogFragment frag = new DatePickerDialogFragment(
          BetweenDatesFragment.this);
      secondActive = true;
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
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
      Log.v(TAG, "nothing selected");
      answerType = AnswerInType.DAYS;
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
    }
  };

  /*
   * Handles click on the submit button
   */
  private OnClickListener submitListener = new OnClickListener() {
    public void onClick(View v) {
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
//        case AGE:
//          answer.setText(DateWrap.getAge(firstDate, secondDate));
//          break;
        }

      } catch (ParseException e) {
        // XXX need to split this up, killing both is not nice
        ((AndDayToDayActivity) getActivity())
            .showAlert(getString(R.string.date_error));
        firstDateInput.setText("");
        secondDateInput.setText("");
      }

    }
  };

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

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

    secondDateInput = (EditText) v.findViewById(R.id.second_date_input);
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

    submitButton = (Button) v.findViewById(R.id.between_dates_submit_button);
    submitButton.setOnClickListener(submitListener);

    return v;
  }

  /**
   * Set the date based on the selected button
   */
  @Override
  public void onDateSet(DatePicker view, int year, int month, int day) {
    getFragmentManager().popBackStack();
    if (firstActive) {
      firstActive = false;
      firstDateInput.setText(month + 1 + "/" + day + "/" + year);
    } else if (secondActive) {
      secondActive = false;
      secondDateInput.setText(month + 1 + "/" + day + "/" + year);
    }
  }
}
