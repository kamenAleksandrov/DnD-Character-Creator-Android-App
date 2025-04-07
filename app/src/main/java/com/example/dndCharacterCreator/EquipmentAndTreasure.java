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

import com.example.dndCharacterCreator.databinding.FragmentEquipmentAndTreasureBinding;

public class EquipmentAndTreasure extends Fragment {

    private FragmentEquipmentAndTreasureBinding binding;
    SharedPreferences characterPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentEquipmentAndTreasureBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //initialise the preferences for the current character
        characterPreferences = requireActivity()
                .getSharedPreferences(characterNameToPass, Context.MODE_PRIVATE);
        SharedPreferences.Editor characterPreferencesEditor = characterPreferences.edit();

        EditText copper, silver, electrum, gold, platinum, equipment, treasure;

        copper = binding.copperEditTxtNum;
        silver = binding.silverEditTxtNum;
        electrum = binding.electrumEditTxtNum;
        gold = binding.goldEditTxtNum;
        platinum = binding.platinumEditTxtNum;
        equipment = binding.equipmentEditText;
        treasure = binding.treasureEditText;

        editTextGetSet(copper, characterPreferences, characterPreferencesEditor, "copper");
        editTextGetSet(silver, characterPreferences, characterPreferencesEditor, "silver");
        editTextGetSet(electrum, characterPreferences, characterPreferencesEditor, "electrum");
        editTextGetSet(gold, characterPreferences, characterPreferencesEditor, "gold");
        editTextGetSet(platinum, characterPreferences, characterPreferencesEditor, "platinum");
        editTextGetSet(equipment, characterPreferences, characterPreferencesEditor, "equipment");
        editTextGetSet(treasure, characterPreferences, characterPreferencesEditor, "treasure");


        onTouchScroll(equipment, R.id.equipment_editText);
        onTouchScroll(treasure, R.id.treasure_editText);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}