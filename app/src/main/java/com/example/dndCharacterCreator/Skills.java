package com.example.dndCharacterCreator;

import static com.example.dndCharacterCreator.MainActivity.characterNameToPass;
import static com.example.dndCharacterCreator.UtilityForFragments.checkUncheck;
import static com.example.dndCharacterCreator.UtilityForFragments.editTextGetSet;
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

import com.example.dndCharacterCreator.databinding.FragmentSkillsBinding;

public class Skills extends Fragment {

    private FragmentSkillsBinding binding;
    SharedPreferences characterPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSkillsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        characterPreferences = requireActivity()
                .getSharedPreferences(characterNameToPass, Context.MODE_PRIVATE);
        SharedPreferences.Editor characterPreferencesEditor = characterPreferences.edit();

        EditText acrobaticsET, animalHandlingET, arcanaET, athleticsET, deceptionET, historyET,
                insightET, intimidationET, investigationET, medicineET, natureET, perceptionET,
                performanceET, persuasionET, religionET, sleightOfHandET, stealthET, survivalET;

        RadioButton acrobaticsRB, animalHandlingRB, arcanaRB, athleticsRB, deceptionRB, historyRB,
                insightRB, intimidationRB, investigationRB, medicineRB, natureRB, perceptionRB,
                performanceRB, persuasionRB, religionRB, sleightOfHandRB, stealthRB, survivalRB;

        acrobaticsET = binding.acrobaticsSTEditTextNumber;
        animalHandlingET= binding.animalHandlingSTEditTextNumber;
        arcanaET= binding.arcanaSTEditTextNumber;
        athleticsET = binding.athleticsSTEditTextNumber;
        deceptionET= binding.deceptionSTEditTextNumber;
        historyET = binding.historySTEditTextNumber;
        insightET= binding.insightSTEditTextNumber;
        intimidationET = binding.intimidationSTEditTextNumber;
        investigationET= binding.investigationSTEditTextNumber;
        medicineET= binding.medicineSTEditTextNumber;
        natureET= binding.natureSTEditTextNumber;
        perceptionET = binding.perceptionSTEditTextNumber;
        performanceET= binding.performanceSTEditTextNumber;
        persuasionET= binding.persuasionSTEditTextNumber;
        religionET= binding.religionSTEditTextNumber;
        sleightOfHandET = binding.sleightOfHandSTEditTextNumber;
        stealthET= binding.stealthSTEditTextNumber;
        survivalET= binding.survivalSTEditTextNumber;

        acrobaticsRB = binding.acrobaticsRadioButton;
        animalHandlingRB= binding.animalHandlingRadioButton;
        arcanaRB= binding.arcanaRadioButton;
        athleticsRB = binding.athleticsRadioButton;
        deceptionRB= binding.deceptionRadioButton;
        historyRB = binding.historyRadioButton;
        insightRB= binding.insightRadioButton;
        intimidationRB = binding.intimidationRadioButton;
        investigationRB= binding.investigationRadioButton;
        medicineRB= binding.medicineRadioButton;
        natureRB= binding.natureRadioButton;
        perceptionRB = binding.perceptionRadioButton;
        performanceRB= binding.performanceRadioButton;
        persuasionRB= binding.persuasionRadioButton;
        religionRB= binding.religionRadioButton;
        sleightOfHandRB = binding.sleightOfHandRadioButton;
        stealthRB= binding.stealthRadioButton;
        survivalRB= binding.survivalRadioButton;

        editTextGetSet(acrobaticsET, characterPreferences, characterPreferencesEditor, "acrobaticsET");
        editTextGetSet(animalHandlingET, characterPreferences, characterPreferencesEditor, "animalHandlingET");
        editTextGetSet(arcanaET, characterPreferences, characterPreferencesEditor, "arcanaET");
        editTextGetSet(athleticsET, characterPreferences, characterPreferencesEditor, "athleticsET");
        editTextGetSet(deceptionET, characterPreferences, characterPreferencesEditor, "deceptionET");
        editTextGetSet(historyET, characterPreferences, characterPreferencesEditor, "historyET");
        editTextGetSet(insightET, characterPreferences, characterPreferencesEditor, "insightET");
        editTextGetSet(intimidationET, characterPreferences, characterPreferencesEditor, "intimidationET");
        editTextGetSet(investigationET, characterPreferences, characterPreferencesEditor, "investigationET");
        editTextGetSet(medicineET, characterPreferences, characterPreferencesEditor, "medicineET");
        editTextGetSet(natureET, characterPreferences, characterPreferencesEditor, "natureET");
        editTextGetSet(perceptionET, characterPreferences, characterPreferencesEditor, "perceptionET");
        editTextGetSet(performanceET, characterPreferences, characterPreferencesEditor, "performanceET");
        editTextGetSet(persuasionET, characterPreferences, characterPreferencesEditor, "persuasionET");
        editTextGetSet(religionET, characterPreferences, characterPreferencesEditor, "religionET");
        editTextGetSet(sleightOfHandET, characterPreferences, characterPreferencesEditor, "sleightOfHandET");
        editTextGetSet(stealthET, characterPreferences, characterPreferencesEditor, "stealthET");
        editTextGetSet(survivalET, characterPreferences, characterPreferencesEditor, "survivalET");

        //checks saved radio button state and sets it

        setRadioState(acrobaticsRB,characterPreferences, "acrobaticsRB");
        setRadioState(animalHandlingRB,characterPreferences, "animalHandlingRB");
        setRadioState(arcanaRB,characterPreferences, "arcanaRB");
        setRadioState(athleticsRB,characterPreferences, "athleticsRB");
        setRadioState(deceptionRB,characterPreferences, "deceptionRB");
        setRadioState(historyRB,characterPreferences, "historyRB");
        setRadioState(insightRB,characterPreferences, "insightRB");
        setRadioState(intimidationRB,characterPreferences, "intimidationRB");
        setRadioState(investigationRB,characterPreferences, "investigationRB");
        setRadioState(medicineRB,characterPreferences, "medicineRB");
        setRadioState(natureRB,characterPreferences, "natureRB");
        setRadioState(perceptionRB,characterPreferences, "perceptionRB");
        setRadioState(performanceRB,characterPreferences, "performanceRB");
        setRadioState(persuasionRB,characterPreferences, "persuasionRB");
        setRadioState(religionRB,characterPreferences, "religionRB");
        setRadioState(sleightOfHandRB,characterPreferences, "sleightOfHandRB");
        setRadioState(stealthRB,characterPreferences, "stealthRB");
        setRadioState(survivalRB,characterPreferences, "survivalRB");

        //allows to check and uncheck radio buttons + saves their state

        checkUncheck(acrobaticsRB,characterPreferencesEditor, "acrobaticsRB");
        checkUncheck(animalHandlingRB,characterPreferencesEditor, "animalHandlingRB");
        checkUncheck(arcanaRB,characterPreferencesEditor, "arcanaRB");
        checkUncheck(athleticsRB,characterPreferencesEditor, "athleticsRB");
        checkUncheck(deceptionRB,characterPreferencesEditor, "deceptionRB");
        checkUncheck(historyRB,characterPreferencesEditor, "historyRB");
        checkUncheck(insightRB,characterPreferencesEditor, "insightRB");
        checkUncheck(intimidationRB,characterPreferencesEditor, "intimidationRB");
        checkUncheck(investigationRB,characterPreferencesEditor, "investigationRB");
        checkUncheck(medicineRB,characterPreferencesEditor, "medicineRB");
        checkUncheck(natureRB,characterPreferencesEditor, "natureRB");
        checkUncheck(perceptionRB,characterPreferencesEditor, "perceptionRB");
        checkUncheck(performanceRB,characterPreferencesEditor, "performanceRB");
        checkUncheck(persuasionRB,characterPreferencesEditor, "persuasionRB");
        checkUncheck(religionRB,characterPreferencesEditor, "religionRB");
        checkUncheck(sleightOfHandRB,characterPreferencesEditor, "sleightOfHandRB");
        checkUncheck(stealthRB,characterPreferencesEditor, "stealthRB");
        checkUncheck(survivalRB,characterPreferencesEditor, "survivalRB");

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}