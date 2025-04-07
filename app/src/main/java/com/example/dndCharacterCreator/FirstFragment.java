package com.example.dndCharacterCreator;

//get the activity variables
//get character name for preferences
import static com.example.dndCharacterCreator.MainActivity.characterNameToPass;
//get the reusable edittext method
import static com.example.dndCharacterCreator.MainActivity.imagesFolderToPass;
import static com.example.dndCharacterCreator.UtilityForFragments.editTextGetSet;
import static com.example.dndCharacterCreator.UtilityForFragments.onTouchScroll;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.room.Room;

import com.example.dndCharacterCreator.appDatabase.PreparedAbilitiesDatabase;
import com.example.dndCharacterCreator.databinding.FragmentFirstBinding;
import com.example.dndCharacterCreator.databinding.FragmentStartScreenBinding;

import java.io.File;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    SharedPreferences characterPreferences;
    static PreparedAbilitiesDatabase preparedAbilitiesDatabase;
    ImageButton characterProfileButton;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        if (characterNameToPass == null){
            NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_startScreen);
        }
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //this allows me to set custom behaviour to each fragment
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //set the character name textView to the current character name
        binding.characterNameTxtview.setText(characterNameToPass);

        //build abilities database
        preparedAbilitiesDatabase = Room.databaseBuilder(getContext(),
                PreparedAbilitiesDatabase.class, characterNameToPass + "PreparedAbilities")
                //.fallbackToDestructiveMigration() //add this to clear database (no need for migration but deletes everything)
                .build();

        //initialise the preferences for the current character
        characterPreferences = requireActivity()
                .getSharedPreferences(characterNameToPass, Context.MODE_PRIVATE);
        SharedPreferences.Editor characterPreferencesEditor = characterPreferences.edit();

        EditText str, strMod, dex, dexMod, constVariable, constVariableMod, intVariable, intVariableMod,
                wis, wisMod, charVariable, charVariableMod, hp, tempHp, armorClass, initiative, speed,
                inspiration, proficiency, perception, spelcastingAbility, spellSaveDc, spellAttackBonus;

        //bind the editText
        str = binding.strEditTxtNum;
        //get value, store it, update it, display it
        editTextGetSet(str,characterPreferences ,characterPreferencesEditor, "str");
        //do the same for every editText
        strMod = binding.strModEditTxtNum;
        dex = binding.dexEditTxtNum;
        dexMod = binding.dexModEditTxtNum;
        constVariable = binding.constEditTxtNum;
        constVariableMod = binding.constModEditTxtNum;
        intVariable = binding.intEditTxtNum;
        intVariableMod = binding.intModEditTxtNum;
        wis = binding.wisEditTxtNum;
        wisMod = binding.wisModEditTxtNum;
        charVariable = binding.charEditTxtNum;
        hp = binding.hpEditTxtNum;
        tempHp = binding.tempHpEditTxtNum;
        armorClass = binding.armorClassEditTxtNum;
        initiative = binding.initiativeEditTxtNum;
        speed = binding.speedEditTxtNum;
        inspiration = binding.inspEditTxtNum;
        proficiency = binding.profEditTxtNum;
        charVariableMod = binding.charModEditTxtNum;
        perception = binding.persEditTxtNum;
        spelcastingAbility = binding.spellcastingAbilityEditText;
        spellSaveDc = binding.spellSaveDcEditTxtNum;
        spellAttackBonus = binding.spellAttackBonusEditTxtNum;

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                editTextGetSet(strMod, characterPreferences, characterPreferencesEditor, "strMod");
                editTextGetSet(dex, characterPreferences, characterPreferencesEditor, "dex");
                editTextGetSet(dexMod, characterPreferences, characterPreferencesEditor, "dexMod");
                editTextGetSet(constVariable, characterPreferences, characterPreferencesEditor, "constVariable");
                editTextGetSet(constVariableMod, characterPreferences, characterPreferencesEditor, "constVariableMod");
                editTextGetSet(intVariable, characterPreferences, characterPreferencesEditor, "intVariable");
                editTextGetSet(intVariableMod, characterPreferences, characterPreferencesEditor, "intVariableMod");
                editTextGetSet(wis, characterPreferences, characterPreferencesEditor, "wis");
                editTextGetSet(wisMod, characterPreferences, characterPreferencesEditor, "wisMod");
                editTextGetSet(charVariable, characterPreferences, characterPreferencesEditor, "charVariable");
                editTextGetSet(charVariableMod, characterPreferences, characterPreferencesEditor, "charVariableMod");
                editTextGetSet(hp, characterPreferences, characterPreferencesEditor, "hp");
                editTextGetSet(tempHp, characterPreferences, characterPreferencesEditor, "tempHp");
                editTextGetSet(armorClass, characterPreferences, characterPreferencesEditor, "armorClass");
                editTextGetSet(initiative, characterPreferences, characterPreferencesEditor, "initiative");
                editTextGetSet(speed, characterPreferences, characterPreferencesEditor, "speed");
                editTextGetSet(inspiration, characterPreferences, characterPreferencesEditor, "inspiration");
                editTextGetSet(proficiency, characterPreferences, characterPreferencesEditor, "proficiency");
                editTextGetSet(perception, characterPreferences, characterPreferencesEditor, "perception");
                editTextGetSet(spelcastingAbility, characterPreferences, characterPreferencesEditor, "spelcastingAbility");
                editTextGetSet(spellSaveDc, characterPreferences, characterPreferencesEditor, "spellSaveDc");
                editTextGetSet(spellAttackBonus, characterPreferences, characterPreferencesEditor, "spellAttackBonus");

            }
        }).start();

        onTouchScroll(str,R.id.str_editTxtNum);
        onTouchScroll(strMod,R.id.strMod_editTxtNum);
        onTouchScroll(dex,R.id.dex_editTxtNum);
        onTouchScroll(dexMod,R.id.dexMod_editTxtNum);
        onTouchScroll(constVariable,R.id.const_editTxtNum);
        onTouchScroll(constVariableMod,R.id.constMod_editTxtNum);
        onTouchScroll(intVariable,R.id.int_editTxtNum);
        onTouchScroll(intVariableMod,R.id.intMod_editTxtNum);
        onTouchScroll(wis,R.id.wis_editTxtNum);
        onTouchScroll(wisMod,R.id.wisMod_editTxtNum);
        onTouchScroll(charVariable,R.id.char_editTxtNum);
        onTouchScroll(charVariableMod,R.id.charMod_editTxtNum);
        onTouchScroll(hp,R.id.hp_editTxtNum);
        onTouchScroll(tempHp,R.id.tempHp_editTxtNum);
        onTouchScroll(armorClass,R.id.armorClass_editTxtNum);
        onTouchScroll(initiative,R.id.initiative_editTxtNum);
        onTouchScroll(speed,R.id.speed_editTxtNum);
        onTouchScroll(inspiration,R.id.insp_editTxtNum);
        onTouchScroll(proficiency,R.id.prof_editTxtNum);
        onTouchScroll(perception,R.id.pers_editTxtNum);
        onTouchScroll(spelcastingAbility,R.id.spellcastingAbility_editText);
        onTouchScroll(spellSaveDc,R.id.spellSaveDc_editTxtNum);
        onTouchScroll(spellAttackBonus,R.id.spellAttackBonus_editTxtNum);

        characterProfileButton = binding.userDrawingButton;

        //slaga izobrajenieto na image butona
        characterProfileButton.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int symbolImageButtonWidth,symbolImageButtonHeight;
                symbolImageButtonWidth = characterProfileButton.getMeasuredWidthAndState();
                symbolImageButtonHeight = characterProfileButton.getMeasuredHeightAndState();
                //opit da sloja risunkata kato kartina
                ContextWrapper cw = new ContextWrapper(getContext());
                File directory = cw.getDir("characterPortraits", Context.MODE_PRIVATE);
                if (directory.exists()) {
                    File mypath = new File(directory, characterNameToPass + ".png");
                    if (mypath.exists()) {
                        String filePath = mypath.getPath();
                        Bitmap tempBitmap = BitmapFactory.decodeFile(filePath);
                        if (tempBitmap != null && symbolImageButtonWidth > 0 && symbolImageButtonHeight > 0){
                            Bitmap scaledBitmap = Bitmap.createScaledBitmap(tempBitmap, symbolImageButtonWidth, symbolImageButtonHeight, false);
                            characterProfileButton.setImageBitmap(scaledBitmap);
                        }
                    }
                }
            }
        });

        return view;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new Thread(new Runnable() {
            @Override
            public void run() {
                navigationButton(binding.combatButton,FirstFragment.this ,R.id.action_FirstFragment_to_combat);
                navigationButton(binding.equipmentAndTreasureButton,FirstFragment.this ,R.id.action_FirstFragment_to_equipmentAndTreasure);
                navigationButton(binding.otherProficienciesAndLanguagesButton,FirstFragment.this , R.id.action_FirstFragment_to_otherProficienciesAndLanguages);
                navigationButton(binding.featuresAndTraitsButton,FirstFragment.this ,R.id.action_FirstFragment_to_featuresAndTraits);
                navigationButton(binding.personalityButton,FirstFragment.this , R.id.action_FirstFragment_to_personality);
                navigationButton(binding.hpInfoButton,FirstFragment.this , R.id.action_FirstFragment_to_healthPointsInfo);
                navigationButton(binding.skillsButton,FirstFragment.this , R.id.action_FirstFragment_to_skills);
                navigationButton(binding.charClassButton,FirstFragment.this , R.id.action_FirstFragment_to_characterClassAndInfo);
                navigationButton(binding.knownSpellsButton,FirstFragment.this , R.id.action_FirstFragment_to_spellbookClasses);
                navigationButton(binding.preparedAbilitiesAndSpellcastingButton,FirstFragment.this , R.id.action_FirstFragment_to_preparedAbilitiesAndSpellcasting);
            }
        }).start();


        binding.userDrawingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagesFolderToPass = "characterPortraits";

                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_userDrawingCanvas);
            }
        });

    }

    static void navigationButton(AppCompatButton appCompatButton, Fragment fragment, int id) {
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(fragment)
                        .navigate(id);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}