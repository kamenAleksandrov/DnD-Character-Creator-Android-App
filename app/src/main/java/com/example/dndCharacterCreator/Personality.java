package com.example.dndCharacterCreator;

import static com.example.dndCharacterCreator.MainActivity.characterNameToPass;
import static com.example.dndCharacterCreator.MainActivity.imagesFolderToPass;
import static com.example.dndCharacterCreator.UtilityForFragments.editTextGetSet;
import static com.example.dndCharacterCreator.UtilityForFragments.onTouchScroll;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.dndCharacterCreator.databinding.FragmentPersonalityBinding;

import java.io.File;


public class Personality extends Fragment {

    private FragmentPersonalityBinding binding;
    SharedPreferences characterPreferences;
    ImageButton symbolImageButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPersonalityBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //initialise the preferences for the current character
        characterPreferences = requireActivity()
                .getSharedPreferences(characterNameToPass, Context.MODE_PRIVATE);
        SharedPreferences.Editor characterPreferencesEditor = characterPreferences.edit();

        EditText personality, ideals, bonds, flaws, allies, symbol, backstory;

        personality = binding.personalityTraitsEditText;
        ideals = binding.idealsEditText;
        bonds = binding.bondsEditText;
        flaws = binding.flawsEditText;
        allies = binding.alliesAndOrganizationsEditText;
        symbol = binding.symbolEditText;
        backstory = binding.backstoryEditText;

        symbolImageButton = binding.symbolImageButton;

        binding.symbolImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagesFolderToPass = "characterSymbols";

                NavHostFragment.findNavController(Personality.this)
                        .navigate(R.id.action_personality_to_userDrawingCanvas);
            }
        });

        //puska da se izmeri symvol butona i mu setva bitmapa da matchva razmerite, raboti na run zashtoto
        //inache e mn rano i butona ne e izmeren
        symbolImageButton.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int symbolImageButtonWidth,symbolImageButtonHeight;
                symbolImageButtonWidth = symbolImageButton.getMeasuredWidthAndState();
                symbolImageButtonHeight = symbolImageButton.getMeasuredHeightAndState();
                //opit da sloja symvola kato kartina
                ContextWrapper cw = new ContextWrapper(getContext());
                File directory = cw.getDir("characterSymbols", Context.MODE_PRIVATE);
                if (directory.exists()) {
                    File mypath = new File(directory, characterNameToPass + ".png");
                    if (mypath.exists()) {
                        String filePath = mypath.getPath();
                        Bitmap tempBitmap = BitmapFactory.decodeFile(filePath);
                        if (tempBitmap != null && symbolImageButtonWidth > 0 && symbolImageButtonHeight > 0){
                            Bitmap scaledBitmap = Bitmap.createScaledBitmap(tempBitmap, symbolImageButtonWidth, symbolImageButtonHeight, false);
                            symbolImageButton.setImageBitmap(scaledBitmap);
                        }
                    }
                }
            }
        });


        editTextGetSet(personality, characterPreferences, characterPreferencesEditor, "personality");
        editTextGetSet(ideals, characterPreferences, characterPreferencesEditor, "ideals");
        editTextGetSet(bonds, characterPreferences, characterPreferencesEditor, "bonds");
        editTextGetSet(flaws, characterPreferences, characterPreferencesEditor, "flaws");
        editTextGetSet(allies, characterPreferences, characterPreferencesEditor, "allies");
        editTextGetSet(symbol, characterPreferences, characterPreferencesEditor, "symbol");
        editTextGetSet(backstory, characterPreferences, characterPreferencesEditor, "backstory");

        onTouchScroll(personality, R.id.personalityTraits_editText);
        onTouchScroll(ideals, R.id.ideals_editText);
        onTouchScroll(bonds, R.id.bonds_editText);
        onTouchScroll(flaws, R.id.flaws_editText);
        onTouchScroll(allies, R.id.alliesAndOrganizations_editText);
        onTouchScroll(symbol, R.id.symbol_editText);
        onTouchScroll(backstory, R.id.backstory_editText);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}