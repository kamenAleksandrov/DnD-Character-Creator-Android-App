package com.example.dndCharacterCreator;

//get the activity variables
//get character name for preferences
import static com.example.dndCharacterCreator.MainActivity.characterNameToPass;
//get the reusable edittext method
import static com.example.dndCharacterCreator.UtilityForFragments.editTextGetSet;
import static com.example.dndCharacterCreator.UtilityForFragments.onTouchScroll;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.dndCharacterCreator.databinding.FragmentCharacterClassAndInfoBinding;

public class CharacterClassAndInfo extends Fragment {

    private FragmentCharacterClassAndInfoBinding binding;
    SharedPreferences characterPreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCharacterClassAndInfoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //initialise the preferences for the current character
        characterPreferences = requireActivity()
                .getSharedPreferences(characterNameToPass, Context.MODE_PRIVATE);
        SharedPreferences.Editor characterPreferencesEditor = characterPreferences.edit();

        EditText level = binding.levelEditText;
        EditText background = binding.backgroundEditText;
        EditText playerName = binding.playerNameEditText;
        EditText race = binding.raceEditText;
        EditText alignment = binding.alignmentEditText;
        EditText experience = binding.experiencePointsEditText;
        EditText age = binding.ageEditText;
        EditText height = binding.heightEditText;
        EditText weight = binding.weightEditText;
        EditText eyes = binding.eyesEditText;
        EditText skin = binding.skinEditText;
        EditText hair = binding.hairEditText;
        EditText otherInfo = binding.otherCharacterInifoEditText;

        editTextGetSet(level, characterPreferences, characterPreferencesEditor, "level");
        editTextGetSet(background, characterPreferences, characterPreferencesEditor, "background");
        editTextGetSet(playerName, characterPreferences, characterPreferencesEditor, "playerName");
        editTextGetSet(race, characterPreferences, characterPreferencesEditor, "race");
        editTextGetSet(alignment, characterPreferences, characterPreferencesEditor, "alignment");
        editTextGetSet(experience, characterPreferences, characterPreferencesEditor, "experience");
        editTextGetSet(age, characterPreferences, characterPreferencesEditor, "age");
        editTextGetSet(height, characterPreferences, characterPreferencesEditor, "height");
        editTextGetSet(weight, characterPreferences, characterPreferencesEditor, "weight");
        editTextGetSet(eyes, characterPreferences, characterPreferencesEditor, "eyes");
        editTextGetSet(skin, characterPreferences, characterPreferencesEditor, "skin");
        editTextGetSet(hair, characterPreferences, characterPreferencesEditor, "hair");
        editTextGetSet(otherInfo, characterPreferences, characterPreferencesEditor, "otherInfo");

        onTouchScroll(level, R.id.level_editText);
        onTouchScroll(background, R.id.background_editText);
        onTouchScroll(playerName, R.id.playerName_editText);
        onTouchScroll(race, R.id.race_editText);
        onTouchScroll(alignment, R.id.alignment_editText);
        onTouchScroll(experience, R.id.experiencePoints_editText);
        onTouchScroll(age, R.id.age_editText);
        onTouchScroll(height, R.id.height_editText);
        onTouchScroll(weight, R.id.weight_editText);
        onTouchScroll(eyes, R.id.eyes_editText);
        onTouchScroll(skin, R.id.skin_editText);
        onTouchScroll(hair, R.id.hair_editText);
        onTouchScroll(otherInfo, R.id.otherCharacterInifo_editText);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}