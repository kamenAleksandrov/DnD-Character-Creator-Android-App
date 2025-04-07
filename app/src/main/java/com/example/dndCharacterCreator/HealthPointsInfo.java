package com.example.dndCharacterCreator;

import static com.example.dndCharacterCreator.MainActivity.characterNameToPass;
import static com.example.dndCharacterCreator.UtilityForFragments.checkUncheck;
import static com.example.dndCharacterCreator.UtilityForFragments.editTextGetSet;
import static com.example.dndCharacterCreator.UtilityForFragments.onTouchScroll;
import static com.example.dndCharacterCreator.UtilityForFragments.setRadioState;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.dndCharacterCreator.databinding.FragmentHealthPointsInfoBinding;

public class HealthPointsInfo extends Fragment {

    private FragmentHealthPointsInfoBinding binding;
    SharedPreferences characterPreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHealthPointsInfoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        characterPreferences = requireActivity()
                .getSharedPreferences(characterNameToPass, Context.MODE_PRIVATE);
        SharedPreferences.Editor characterPreferencesEditor = characterPreferences.edit();

        EditText totalThrows, hitDie, throwsHistory, strStEt, dexStEt, constStEt, intStEt, wisStEt,
                charStEt, hitPointMaximumEt;

        RadioButton successesStRb, successesStRb2, successesStRb3, failuresStRb, failuresStRb2,
                failuresStRb3, strStRb, dexStRb, constStRb, intStRb, wisStRb, charStRb;

        totalThrows = binding.totalThrowsEditTextNumber;
        hitDie = binding.hitDieEditText;
        throwsHistory = binding.throwsHistoryEditText;
        strStEt = binding.strSTEditTextNumber;
        dexStEt = binding.dexSTEditTextNumber;
        constStEt = binding.constSTEditTextNumber;
        intStEt = binding.intSTEditTextNumber;
        wisStEt = binding.wisSTEditTextNumber;
        charStEt = binding.charSTEditTextNumber;
        hitPointMaximumEt = binding.hitPointMaximumEditText;

        successesStRb=binding.successesRadioButton;
        successesStRb2=binding.successesRadioButton2;
        successesStRb3=binding.successesRadioButton3;
        failuresStRb=binding.failuresRadioButton;
        failuresStRb2=binding.failuresRadioButton2;
        failuresStRb3=binding.failuresRadioButton3;
        strStRb=binding.strRadioButton;
        dexStRb=binding.dexRadioButton;
        constStRb=binding.constRadioButton;
        intStRb=binding.intRadioButton;
        wisStRb=binding.wisRadioButton;
        charStRb=binding.charRadioButton;

        onTouchScroll(throwsHistory, R.id.throwsHistory_editText);

        editTextGetSet(totalThrows, characterPreferences, characterPreferencesEditor, "totalThrows");
        editTextGetSet(hitDie, characterPreferences, characterPreferencesEditor, "hitDie");
        editTextGetSet(throwsHistory, characterPreferences, characterPreferencesEditor, "throwsHistory");
        editTextGetSet(strStEt, characterPreferences, characterPreferencesEditor, "strStEt");
        editTextGetSet(dexStEt, characterPreferences, characterPreferencesEditor, "dexStEt");
        editTextGetSet(constStEt, characterPreferences, characterPreferencesEditor, "constStEt");
        editTextGetSet(intStEt, characterPreferences, characterPreferencesEditor, "intStEt");
        editTextGetSet(wisStEt, characterPreferences, characterPreferencesEditor, "wisStEt");
        editTextGetSet(charStEt, characterPreferences, characterPreferencesEditor, "charStEt");
        editTextGetSet(hitPointMaximumEt, characterPreferences, characterPreferencesEditor, "hitPointMaximumEt");

        setRadioState(successesStRb,characterPreferences, "successesStRb");
        setRadioState(successesStRb2,characterPreferences, "successesStRb2");
        setRadioState(successesStRb3,characterPreferences, "successesStRb3");
        setRadioState(failuresStRb,characterPreferences, "failuresStRb");
        setRadioState(failuresStRb2,characterPreferences, "failuresStRb2");
        setRadioState(failuresStRb3,characterPreferences, "failuresStRb3");
        setRadioState(strStRb,characterPreferences, "strStRb");
        setRadioState(dexStRb,characterPreferences, "dexStRb");
        setRadioState(constStRb,characterPreferences, "constStRb");
        setRadioState(intStRb,characterPreferences, "intStRb");
        setRadioState(wisStRb,characterPreferences, "wisStRb");
        setRadioState(charStRb,characterPreferences, "charStRb");

        checkUncheck(successesStRb,characterPreferencesEditor, "successesStRb");
        checkUncheck(successesStRb2,characterPreferencesEditor, "successesStRb2");
        checkUncheck(successesStRb3,characterPreferencesEditor, "successesStRb3");
        checkUncheck(failuresStRb,characterPreferencesEditor, "failuresStRb");
        checkUncheck(failuresStRb2,characterPreferencesEditor, "failuresStRb2");
        checkUncheck(failuresStRb3,characterPreferencesEditor, "failuresStRb3");
        checkUncheck(strStRb,characterPreferencesEditor, "strStRb");
        checkUncheck(dexStRb,characterPreferencesEditor, "dexStRb");
        checkUncheck(constStRb,characterPreferencesEditor, "constStRb");
        checkUncheck(intStRb,characterPreferencesEditor, "intStRb");
        checkUncheck(wisStRb,characterPreferencesEditor, "wisStRb");
        checkUncheck(charStRb,characterPreferencesEditor, "charStRb");

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}