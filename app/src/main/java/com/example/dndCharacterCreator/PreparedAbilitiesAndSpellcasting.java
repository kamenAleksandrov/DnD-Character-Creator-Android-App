package com.example.dndCharacterCreator;

import static com.example.dndCharacterCreator.FirstFragment.preparedAbilitiesDatabase;
import static com.example.dndCharacterCreator.MainActivity.abilityNameToPass;
import static com.example.dndCharacterCreator.MainActivity.characterNameToPass;
import static com.example.dndCharacterCreator.UtilityForFragments.editTextGetSet;
import static com.example.dndCharacterCreator.UtilityForFragments.navigationButton;
import static com.example.dndCharacterCreator.UtilityForFragments.onTouchScroll;
import static com.example.dndCharacterCreator.UtilityForFragments.scrollableListView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dndCharacterCreator.appDatabase.PreparedAbilityDao;
import com.example.dndCharacterCreator.databinding.FragmentPreparedAbilitiesAndSpellcastingBinding;

import java.util.ArrayList;
import java.util.List;

public class PreparedAbilitiesAndSpellcasting extends Fragment {

    private FragmentPreparedAbilitiesAndSpellcastingBinding binding;

    PreparedAbilityDao preparedAbilityDao = preparedAbilitiesDatabase.preparedAbilityDao();
    ListView preparedAbilitiesAndSpellsListView;
    ArrayList<String> preparedAbilitiesAndSpellsArrayList;
    ArrayAdapter<String> preparedAbilitiesAndSpellsAdapter;
    EditText preparedSpellsPerLevel;
    SharedPreferences characterPreferences;
    AppCompatButton addPreparedButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPreparedAbilitiesAndSpellcastingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        abilityNameToPass = null;

        preparedAbilitiesAndSpellsListView = binding.preparedAbilitiesAndSpellsListView;
        preparedSpellsPerLevel = binding.preparedSpellsPerLevelEditText;
        addPreparedButton = binding.addPreparedButton;
        //adds info to edittext for number of spells
        characterPreferences = requireActivity()
                .getSharedPreferences(characterNameToPass, Context.MODE_PRIVATE);
        SharedPreferences.Editor characterPreferencesEditor = characterPreferences.edit();

        editTextGetSet(preparedSpellsPerLevel, characterPreferences, characterPreferencesEditor, "preparedSpellsPerLevel");
        
        onTouchScroll(binding.preparedSpellsPerLevelEditText, R.id.preparedSpellsPerLevel_editText);

        //populates abilities listView
        preparedAbilitiesAndSpellsArrayList = new ArrayList<>();
        preparedAbilitiesAndSpellsAdapter = new ArrayAdapter<>(getContext(),
                R.layout.character_creation_list_text, preparedAbilitiesAndSpellsArrayList);

        new Thread(new Runnable(){
            @Override
            public void run(){
                List<String> list = preparedAbilityDao.getPreparedNames();
                preparedAbilitiesAndSpellsArrayList.addAll(list);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        preparedAbilitiesAndSpellsAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();

        preparedAbilitiesAndSpellsListView.setAdapter(preparedAbilitiesAndSpellsAdapter);
        preparedAbilitiesAndSpellsAdapter.notifyDataSetChanged();

        scrollableListView(preparedAbilitiesAndSpellsListView, R.id.preparedAbilitiesAndSpells_listView);

        //navigate to clicked ability
        preparedAbilitiesAndSpellsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = view.findViewById(R.id.character_creation_list_textView);
                abilityNameToPass = textView.getText().toString();

                NavHostFragment.findNavController(PreparedAbilitiesAndSpellcasting.this)
                        .navigate(R.id.action_preparedAbilitiesAndSpellcasting_to_abilityFragment);
            }
        });

        navigationButton(binding.addPreparedButton,PreparedAbilitiesAndSpellcasting.this,
                R.id.action_preparedAbilitiesAndSpellcasting_to_preparedSpellbookClasses);

        preparedAbilitiesAndSpellsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                TextView textView = view.findViewById(R.id.character_creation_list_textView);
                String abilityToDelete = textView.getText().toString();

                new AlertDialog.Builder(getActivity())
                        .setTitle("Remove?")
                        .setMessage("Are you sure you want to remove " + abilityToDelete)
                        .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                preparedAbilitiesAndSpellsArrayList.clear();
                                preparedAbilitiesAndSpellsAdapter.clear();
                                preparedAbilitiesAndSpellsAdapter.notifyDataSetChanged();
                                preparedAbilitiesAndSpellsListView.setAdapter(null);

                                new Thread(new Runnable(){
                                    @Override
                                    public void run(){
                                        int preparedId;

                                        TextView textView = view.findViewById(R.id.character_creation_list_textView);
                                        String preparedAbility = textView.getText().toString();

                                        preparedId = preparedAbilityDao.findIdByName(preparedAbility);

                                        List<String> preparedList = preparedAbilityDao.getPreparedNames();

                                        if (preparedList.contains(preparedAbility)){
                                            preparedAbilityDao.deleteByPreparedAbilityId(preparedId);
                                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getContext(), "Removed!",Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }

                                        preparedAbilitiesAndSpellsArrayList = new ArrayList<>();
                                        preparedAbilitiesAndSpellsAdapter = new ArrayAdapter<>(getContext(),
                                                R.layout.character_creation_list_text, preparedAbilitiesAndSpellsArrayList);
                                        preparedAbilitiesAndSpellsListView.setAdapter(preparedAbilitiesAndSpellsAdapter);
                                        List<String> list = preparedAbilityDao.getPreparedNames();
                                        preparedAbilitiesAndSpellsArrayList.addAll(list);

                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                preparedAbilitiesAndSpellsAdapter.notifyDataSetChanged();
                                            }
                                        });
                                    }
                                }).start();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();

                /*
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        int preparedId;

                        TextView textView = view.findViewById(R.id.character_creation_list_textView);
                        String preparedAbility = textView.getText().toString();

                        preparedId = preparedAbilityDao.findIdByName(preparedAbility);

                        List<String> preparedList = preparedAbilityDao.getPreparedNames();

                        if (preparedList.contains(preparedAbility)){
                            preparedAbilityDao.deleteByPreparedAbilityId(preparedId);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), "Removed!",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
                 */

                return true;
            }
        });


        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    /*
    private void onLongClickDeleteAbilityFromDatabaseAndRefresh() {
        preparedAbilitiesAndSpellsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView textView = view.findViewById(R.id.character_creation_list_textView);
                String abilityToDelete = textView.getText().toString();

                new AlertDialog.Builder(getActivity())
                        .setTitle("Delete?")
                        .setMessage("Are you sure you want to delete " + abilityToDelete)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new Thread(new Runnable(){
                                    @Override
                                    public void run(){
                                        int id;
                                        id = abilityDao.findIdByName(abilityToDelete);
                                        abilityDao.deleteByAbilityId(id);
                                    }
                                }).start();

                                preparedAbilitiesAndSpellsArrayList.clear();
                                preparedAbilitiesAndSpellsAdapter.clear();
                                preparedAbilitiesAndSpellsAdapter.notifyDataSetChanged();
                                preparedAbilitiesAndSpellsListView.setAdapter(null);

                                preparedAbilitiesAndSpellsArrayList = new ArrayList<>();
                                preparedAbilitiesAndSpellsAdapter = new ArrayAdapter<>(getContext(),
                                        R.layout.character_creation_list_text, preparedAbilitiesAndSpellsArrayList);
                                preparedAbilitiesAndSpellsListView.setAdapter(preparedAbilitiesAndSpellsAdapter);

                                new Thread(new Runnable(){
                                    @Override
                                    public void run(){
                                        List<String> list = abilityDao.getNamesByPreparedOrNot("prepared");
                                        preparedAbilitiesAndSpellsArrayList.addAll(list);
                                    }
                                }).start();

                                preparedAbilitiesAndSpellsAdapter.notifyDataSetChanged();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
                return true;
            }
        });
    }

     */
}