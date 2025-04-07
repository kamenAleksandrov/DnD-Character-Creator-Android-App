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

import com.example.dndCharacterCreator.databinding.FragmentFeaturesAndTraitsBinding;

public class FeaturesAndTraits extends Fragment {

    private FragmentFeaturesAndTraitsBinding binding;
    SharedPreferences characterPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFeaturesAndTraitsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //initialise the preferences for the current character
        characterPreferences = requireActivity()
                .getSharedPreferences(characterNameToPass, Context.MODE_PRIVATE);
        SharedPreferences.Editor characterPreferencesEditor = characterPreferences.edit();

        EditText features, traits;

        features = binding.featuresEditText;
        traits = binding.traitsEditText;

        editTextGetSet(features, characterPreferences, characterPreferencesEditor, "features");
        editTextGetSet(traits, characterPreferences, characterPreferencesEditor, "traits");

        onTouchScroll(binding.featuresEditText, R.id.features_editText);
        onTouchScroll(binding.traitsEditText, R.id.traits_editText);

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}