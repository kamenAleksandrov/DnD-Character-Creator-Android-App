package com.example.dndCharacterCreator;

import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Random;

public interface UtilityForFragments {

    static void navigationButton(AppCompatButton appCompatButton, Fragment fragment, int id) {
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(fragment)
                        .navigate(id);
            }
        });
    }

    static void editTextGetSet(EditText editText, SharedPreferences preferences , SharedPreferences.Editor editor, String key){

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String value = editText.getText().toString();
                editor.putString(key, value);
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        editText.setText(preferences.getString(key, null));
    }

    static void scrollableListView(ListView listView, int id){
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (view.getId() == id) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }

                return false;
            }
        });
    }

    static void scrollableTextView(TextView textView, int id){
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (view.getId() == id) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }

                return false;
            }
        });
    }

    static void onTouchScroll(EditText p, int p2) {
        p.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {

                if (view.getId() == p2) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });
    }

    static void checkUncheck(RadioButton p, SharedPreferences.Editor editor, String key) {
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!p.isSelected()) {
                    p.setChecked(true);
                    p.setSelected(true);
                    editor.putString(key, "true");

                } else {
                    p.setChecked(false);
                    p.setSelected(false);
                    editor.putString(key, "false");

                }
                editor.apply();
            }
        });
    }

    static void setRadioState(RadioButton radioButton,SharedPreferences preferences, String key){
        if (preferences.contains(key)){
            if (preferences.getString(key, null).equals("true")){
                radioButton.setChecked(true);
                radioButton.setSelected(true);
            }else {
                radioButton.setChecked(false);
                radioButton.setSelected(false);
            }
        }else {
            radioButton.setChecked(false);
            radioButton.setSelected(false);
        }
    }

    static void presetDiceRoller(AppCompatButton appCompatButton, int valueInput, Spinner spinner,
                                 TextView rollValueTxtView, TextView rollValuesTxtView){

        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random random = new Random();
                int rand = 0;
                String rollsSpinnerString;
                rollsSpinnerString = spinner.getSelectedItem().toString();
                int rolls = 0;
                rolls = Integer.parseInt(rollsSpinnerString);

                StringBuilder rollValuesString = new StringBuilder();
                int totalValue = 0;

                for (int a = 1; a <= rolls; a++){
                    rand = random.nextInt(valueInput) + 1;
                    totalValue += rand;
                    if (a == rolls){
                        rollValuesString.append(rand);
                    } else {
                        rollValuesString.append(rand + ", ");
                    }
                }
                rollValueTxtView.setText(Integer.toString(totalValue));
                rollValuesTxtView.setText(rollValuesString);
            }
        });
    }

    static void textViewGetSet(TextView textView, SharedPreferences preferences , SharedPreferences.Editor editor, String key){

        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String value = textView.getText().toString();
                editor.putString(key, value);
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        textView.setText(preferences.getString(key, null));
    }

    static void saveSpinnerPosition(int position, SharedPreferences.Editor editor){
        editor.putInt("spinnerValue",position);
        editor.apply();
    }
    static void loadSpinnerPosition(SharedPreferences sharedPreferences, Spinner spinner){
        int position = sharedPreferences.getInt("spinnerValue",-1);
        // set the value of the spinner
        spinner.setSelection(position);
    }

}