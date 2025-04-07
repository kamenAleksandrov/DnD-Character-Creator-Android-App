package com.example.dndCharacterCreator;

import static com.example.dndCharacterCreator.UtilityForFragments.editTextGetSet;
import static com.example.dndCharacterCreator.UtilityForFragments.loadSpinnerPosition;
import static com.example.dndCharacterCreator.UtilityForFragments.presetDiceRoller;
import static com.example.dndCharacterCreator.UtilityForFragments.saveSpinnerPosition;
import static com.example.dndCharacterCreator.UtilityForFragments.textViewGetSet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class DiceRollerDialogFragment extends DialogFragment {

    SharedPreferences lastDiceRollsSharedPreferences;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //initialise the preferences
        lastDiceRollsSharedPreferences = requireActivity()
                .getSharedPreferences("Last Dice Rolls", Context.MODE_PRIVATE);
        SharedPreferences.Editor lastDiceRollsEditor = lastDiceRollsSharedPreferences.edit();

        View customLayout = getLayoutInflater().inflate(R.layout.fragment_dice_roller_dialog_layout, null);

        EditText diceValue = customLayout.findViewById(R.id.diceValue_editTxtNum);
        Button diceRoller = customLayout.findViewById(R.id.diceRoller_button);

        TextView rollValue = customLayout.findViewById(R.id.diceRollValue_textView);
        TextView rollValues = customLayout.findViewById(R.id.rollValues_txtView);

        Spinner numberOfRollsSpinner = customLayout.findViewById(R.id.numberOfRolls_spinner);
        ArrayList spinnerValues = new ArrayList<>();
        spinnerValues.add("1");
        spinnerValues.add("2");
        spinnerValues.add("3");
        spinnerValues.add("4");
        spinnerValues.add("5");
        spinnerValues.add("6");
        spinnerValues.add("7");
        spinnerValues.add("8");
        spinnerValues.add("9");
        spinnerValues.add("10");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.character_creation_list_text, spinnerValues);
        arrayAdapter.setDropDownViewResource(R.layout.character_creation_list_text);
        numberOfRollsSpinner.setAdapter(arrayAdapter);

        AppCompatButton d4, d6, d8, d10, d12, d20, d100;

        d4 = customLayout.findViewById(R.id.d4_button);
        d6 = customLayout.findViewById(R.id.d6_button);
        d8 = customLayout.findViewById(R.id.d8_button);
        d10 = customLayout.findViewById(R.id.d10_button);
        d12 = customLayout.findViewById(R.id.d12_button);
        d20 = customLayout.findViewById(R.id.d20_button);
        d100 = customLayout.findViewById(R.id.d100_button);

        builder.setView(customLayout).create();
        builder.setCancelable(false);

        builder.setNegativeButton(R.string.dice_roller_dialog_fragment_done_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Objects.requireNonNull(DiceRollerDialogFragment.this.getDialog()).cancel();

            }
        });

        diceRoller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rollValues.setText("");

                Random random = new Random();
                String string = diceValue.getText().toString();
                String rollsSpinnerString;
                StringBuilder rollValuesString = new StringBuilder();

                int value = 0;
                int rolls = 0;
                rollsSpinnerString = numberOfRollsSpinner.getSelectedItem().toString();
                rolls = Integer.parseInt(rollsSpinnerString);

                long totalValue = 0;

                if (string.equals("0")){
                    rollValue.setText("0");
                }else if (!string.equals("")){
                    for (int a = 1; a <= rolls; a++){
                        value = Integer.parseInt(string);
                        value = random.nextInt(value) + 1;
                        totalValue += value;
                        if (a == rolls){
                            rollValuesString.append(value);
                        } else {
                            rollValuesString.append(value + ", ");
                        }
                    }
                    rollValue.setText(Long.toString(totalValue));
                    rollValues.setText(rollValuesString);
                } else {
                    Toast.makeText(getContext(), "Invalid Input",Toast.LENGTH_SHORT).show();
                }
            }
        });

        presetDiceRoller(d4, 4,numberOfRollsSpinner, rollValue, rollValues);
        presetDiceRoller(d6, 6,numberOfRollsSpinner, rollValue, rollValues);
        presetDiceRoller(d8, 8,numberOfRollsSpinner, rollValue, rollValues);
        presetDiceRoller(d10, 10,numberOfRollsSpinner, rollValue, rollValues);
        presetDiceRoller(d12, 12,numberOfRollsSpinner, rollValue, rollValues);
        presetDiceRoller(d20, 20,numberOfRollsSpinner, rollValue, rollValues);
        presetDiceRoller(d100, 100,numberOfRollsSpinner, rollValue, rollValues);

        //scrollableTextView(rollValues,R.id.rollValues_txtView);
        rollValues.setMovementMethod(new ScrollingMovementMethod());

        editTextGetSet(diceValue, lastDiceRollsSharedPreferences, lastDiceRollsEditor, "customDiceValue");
        textViewGetSet(rollValue, lastDiceRollsSharedPreferences, lastDiceRollsEditor, "totalRoll");
        textViewGetSet(rollValues, lastDiceRollsSharedPreferences, lastDiceRollsEditor, "allRolls");

        //load the last spinner selection
        loadSpinnerPosition(lastDiceRollsSharedPreferences,numberOfRollsSpinner);
        //when spinner selection is chosen, save it
        numberOfRollsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int spinnerPosition = numberOfRollsSpinner.getSelectedItemPosition();
                saveSpinnerPosition(spinnerPosition,lastDiceRollsEditor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        return builder.create();
    }

}