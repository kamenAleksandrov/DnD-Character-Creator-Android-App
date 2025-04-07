package com.example.dndCharacterCreator;

import static com.example.dndCharacterCreator.FirstFragment.preparedAbilitiesDatabase;
import static com.example.dndCharacterCreator.MainActivity.abilityNameToPass;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dndCharacterCreator.appDatabase.PreparedAbilityDao;
import com.example.dndCharacterCreator.databinding.FragmentCombatBinding;
import com.example.dndCharacterCreator.databinding.FragmentFirstBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Combat extends Fragment {

    private FragmentCombatBinding binding;

    SharedPreferences characterPreferences;
    EditText weaponAttacks, combatNotes;
    ImageButton combatDrawing;
    Spinner preparedAbilitiesSpinner;
    PreparedAbilityDao preparedAbilityDao = preparedAbilitiesDatabase.preparedAbilityDao();

    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentCombatBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //initialise the preferences for the current character
        characterPreferences = requireActivity()
                .getSharedPreferences(characterNameToPass, Context.MODE_PRIVATE);
        SharedPreferences.Editor characterPreferencesEditor = characterPreferences.edit();

        weaponAttacks = binding.weaponAttacksEditText;
        combatNotes = binding.combatNotesEditText;
        combatDrawing = binding.combatDrawingButton;
        preparedAbilitiesSpinner = binding.preparedAbilitiesSpinner;

        editTextGetSet(weaponAttacks, characterPreferences, characterPreferencesEditor, "WeaponAttacks");
        editTextGetSet(combatNotes, characterPreferences, characterPreferencesEditor, "CombatNotes");

        onTouchScroll(binding.weaponAttacksEditText, R.id.weaponAttacks_editText);
        onTouchScroll(binding.combatNotesEditText, R.id.combatNotes_editText);

        // dobavq podgotvenite abilitita v spinnera
        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), R.layout.character_creation_list_text, arrayList);
        //adapter.setDropDownViewResource(R.layout.character_creation_list_text);
        preparedAbilitiesSpinner.setAdapter(adapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> list = preparedAbilityDao.getPreparedNames();
                arrayList.addAll(list);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
                //adapter.addAll(preparedAbilityDao.getPreparedNames());
            }
        }).start();
        adapter.notifyDataSetChanged();

        preparedAbilitiesSpinner.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = view.findViewById(R.id.character_creation_list_textView);
                abilityNameToPass = textView.getText().toString();

                NavHostFragment.findNavController(Combat.this)
                        .navigate(R.id.action_combat_to_abilityFragment);
                return false;
            }
        });


        //slaga izobrajenieto na image butona
        combatDrawing.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int symbolImageButtonWidth,symbolImageButtonHeight;
                symbolImageButtonWidth = combatDrawing.getMeasuredWidthAndState();
                symbolImageButtonHeight = combatDrawing.getMeasuredHeightAndState();
                //opit da sloja risunkata kato kartina
                ContextWrapper cw = new ContextWrapper(getContext());
                File directory = cw.getDir("characterCombat", Context.MODE_PRIVATE);
                if (directory.exists()) {
                    File mypath = new File(directory, characterNameToPass + ".png");
                    if (mypath.exists()) {
                        String filePath = mypath.getPath();
                        Bitmap tempBitmap = BitmapFactory.decodeFile(filePath);
                        if (tempBitmap != null && symbolImageButtonWidth > 0 && symbolImageButtonHeight > 0){
                            Bitmap scaledBitmap = Bitmap.createScaledBitmap(tempBitmap, symbolImageButtonWidth, symbolImageButtonHeight, false);
                            combatDrawing.setImageBitmap(scaledBitmap);
                        }
                    }
                }
            }
        });

        return view;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.combatDrawingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagesFolderToPass = "characterCombat";

                NavHostFragment.findNavController(Combat.this)
                        .navigate(R.id.action_combat_to_userDrawingCanvas);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}