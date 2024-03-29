/*
 * Copyright (C) 2012 - 2022 Doyle Young
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
package mobi.daytoday.DayToDay2;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * Gather two dates and the form of the response and report the time between the
 * dates
 *
 * @author Doyle Young
 */
public class BetweenDatesFragment extends Fragment implements OnDateSetListener {
    private static final String TAG = "BetweenDatesFragment";

    private enum AnswerInType {
        DAYS, WEEKS, MONTHS, NATURAL
    }

    private EditText firstDateInput;
    private EditText secondDateInput;
    private AnswerInType answerType;
    private TextView answer;
    private Button resetButton;
    private boolean firstActive;
    private boolean secondActive;
    private Boolean resetVisible;

    /**
     * Constructor - Fragment requires public empty constructor
     */
    public BetweenDatesFragment() {
        // nothing to see here
    }

    /*
     * Handles click on the first date select button
     */
    private OnClickListener firstDateListener = new OnClickListener() {
        public void onClick(View v) {
            firstActive = true;
            showDatePickerDialogWith(firstDateInput.getText().toString());
        }
    };

    /*
     * Handles click on the second date select button
     */
    private OnClickListener secondDateListener = new OnClickListener() {
        public void onClick(View v) {
            secondActive = true;
            showDatePickerDialogWith(secondDateInput.getText().toString());
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

            calculateIfPossible();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Log.v(TAG, "nothing selected");
            answerType = AnswerInType.DAYS;

            calculateIfPossible();
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

    /*
     * Handles Enter key or Alt finished key press
     */
    private OnEditorActionListener dateEditListener = new OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                calculateIfPossible();
            }
            return false;
        }
    };

    /*
     * Actively update the result
     */
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            calculateIfPossible();
        }

        @Override
        public void afterTextChanged(Editable s) {
            //nothing
        }
    };

    /*
     * Keeps focus on EditText and off tab when using physical keyboard
     */
    private OnTouchListener handlePhysicalKeyboardListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.requestFocusFromTouch();
            return false;
        }
    };

    /*
     * Handles EditText losing focus for any reason
     */
    private OnFocusChangeListener editTextLosesFocusListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                calculateIfPossible();
            }
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
        firstDateInput.addTextChangedListener(textWatcher);
        firstDateInput.setOnTouchListener(handlePhysicalKeyboardListener);
        firstDateInput.setOnFocusChangeListener(editTextLosesFocusListener);

        secondDateInput = (EditText) v.findViewById(R.id.second_date_input);
        secondDateInput.setOnEditorActionListener(dateEditListener);
        secondDateInput.addTextChangedListener(textWatcher);
        secondDateInput.setOnTouchListener(handlePhysicalKeyboardListener);
        secondDateInput.setOnFocusChangeListener(editTextLosesFocusListener);

        answer = (TextView) v.findViewById(R.id.between_dates_answer);

        Spinner answerInSpinner = (Spinner) v.findViewById(R.id.answer_in_spinner);
        ArrayAdapter<CharSequence> answerInAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.answer_in_array,
                        android.R.layout.simple_spinner_item);
        answerInAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        answerInSpinner.setAdapter(answerInAdapter);
        answerInSpinner.setOnItemSelectedListener(answerInSelectedListener);

        ImageButton firstDateSelect = (ImageButton) v.findViewById(R.id.first_date_select);
        firstDateSelect.setOnClickListener(firstDateListener);

        ImageButton secondDateSelect = (ImageButton) v.findViewById(R.id.second_date_select);
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
            firstDateInput.setText(String.format(getString(R.string.date_format),
                    month + 1, day, year));
        } else if (secondActive) {
            secondDateInput.setText(String.format(getString(R.string.date_format),
                    month + 1, day, year));
        }

        findBetween();
        fadeInResetButton();

        firstActive = false;
        secondActive = false;
    }

    private void showDatePickerDialogWith(String date) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        Fragment prev = getFragmentManager().findFragmentByTag(
                DatePickerDialogFragment.DATE_PICKER_ID);
        if (prev != null) {
            ft.remove(prev);
            firstActive = secondActive = false;
        }
        ft.addToBackStack(null);

        DialogFragment frag = new DatePickerDialogFragment();
        ((DatePickerDialogFragment) frag).setCallbackFragment(BetweenDatesFragment.this);
        Bundle args = new Bundle();
        args.putString(DateWrap.CUR_DATE, date);
        frag.setArguments(args);
        frag.show(ft, DatePickerDialogFragment.DATE_PICKER_ID);
    }

    private void calculateIfPossible() {
        if (hasDates() && datesFollowFormat()) {
            findBetween();
            fadeInResetButton();
        }
    }

    private boolean datesFollowFormat() {
        return firstDateInput.getText().toString().matches("\\d{1,2}-\\d{1,2}-\\d{1,5}")
        && secondDateInput.getText().toString().matches("\\d{1,2}-\\d{1,2}-\\d{1,5}");
    }

    private boolean hasDates() {
        return firstDateIsSet() && secondDateIsSet();
    }

    private boolean firstDateIsSet() {
        return !"".equals(firstDateInput.getText().toString());
    }

    private boolean secondDateIsSet() {
        return !"".equals(secondDateInput.getText().toString());
    }

    private void fadeInResetButton() {
        if (!resetVisible) {
            resetButton.setVisibility(View.VISIBLE);
            animate(resetButton).setDuration(800).alphaBy(0.25f).alpha(0).alpha(1);
            resetVisible = true;
        }
    }

    private void findBetween() {
        if (hasDates()) {
            try {
                String firstDate = firstDateInput.getText().toString();
                String secondDate = secondDateInput.getText().toString();

                Log.v(TAG, firstDate + " " + secondDate);

                switch (answerType) {
                    case DAYS:
                        answer.setText(String.valueOf(DateWrap.daysBetween(firstDate,
                                secondDate)));
                        break;
                    case WEEKS:
                        answer.setText(String.valueOf(DateWrap.weeksBetween(firstDate,
                                secondDate)));
                        break;
                    case MONTHS:
                        answer.setText(String.valueOf(DateWrap.monthsBetween(firstDate,
                                secondDate)));
                        break;
                    case NATURAL:
                        answer.setText(DateWrap.naturalInterval(firstDate, secondDate));
                        break;
                }

            } catch (Exception e) {
                ((AndDayToDayActivity) getActivity()).showAlert(getString(R.string.date_error));
                firstDateInput.setText("");
                secondDateInput.setText("");
                answer.setText("");
                resetButton.setVisibility(View.INVISIBLE);
                resetVisible = false;
            }
        }
    }
}
