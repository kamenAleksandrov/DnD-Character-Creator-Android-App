package com.example.dndCharacterCreator;

//get the activity variables
//get character name for preferences
import static com.example.dndCharacterCreator.MainActivity.characterNameToPass;
//get the reusable edittext method
import static com.example.dndCharacterCreator.UtilityForFragments.editTextGetSet;
//scrollable textViews
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

import com.example.dndCharacterCreator.databinding.FragmentOtherProficienciesAndLanguagesBinding;


public class OtherProficienciesAndLanguages extends Fragment {

    private FragmentOtherProficienciesAndLanguagesBinding binding;
    SharedPreferences characterPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOtherProficienciesAndLanguagesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        characterPreferences = requireActivity()
                .getSharedPreferences(characterNameToPass, Context.MODE_PRIVATE);
        SharedPreferences.Editor characterPreferencesEditor = characterPreferences.edit();

        EditText armor, weapons, languages, other;

        armor = binding.armorEditText;
        weapons = binding.weaponsEditText;
        languages = binding.languagesEditText;
        other = binding.otherEditText;

        editTextGetSet(armor,characterPreferences ,characterPreferencesEditor, "armor");
        editTextGetSet(weapons,characterPreferences ,characterPreferencesEditor, "weapons");
        editTextGetSet(languages,characterPreferences ,characterPreferencesEditor, "languages");
        editTextGetSet(other,characterPreferences ,characterPreferencesEditor, "other");

        onTouchScroll(armor, R.id.armor_editText);
        onTouchScroll(weapons, R.id.weapons_editText);
        onTouchScroll(languages, R.id.languages_editText);
        onTouchScroll(other, R.id.other_editText);

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}