package com.example.dndCharacterCreator;

import static com.example.dndCharacterCreator.FirstFragment.preparedAbilitiesDatabase;
import static com.example.dndCharacterCreator.MainActivity.abilitiesDatabase;
import static com.example.dndCharacterCreator.MainActivity.classNameToPass;
import static com.example.dndCharacterCreator.MainActivity.abilityNameToPass;
import static com.example.dndCharacterCreator.UtilityForFragments.navigationButton;
import static com.example.dndCharacterCreator.UtilityForFragments.scrollableListView;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dndCharacterCreator.appDatabase.AbilityDao;
import com.example.dndCharacterCreator.appDatabase.PreparedAbilityDao;
import com.example.dndCharacterCreator.databinding.FragmentKnownSpellsForClassBinding;

import java.util.ArrayList;
import java.util.List;

public class knownSpellsForClass extends Fragment {

    private FragmentKnownSpellsForClassBinding binding;

    AbilityDao abilityDao = abilitiesDatabase.abilityDao();
    PreparedAbilityDao preparedAbilityDao = preparedAbilitiesDatabase.preparedAbilityDao();
    ListView abilitiesListView, spellsListView;
    ArrayList<String> abilitiesArrayList, spellsArrayList;
    ArrayAdapter<String> abilitiesAdapter, spellsAdapter;
    TextView classNameTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentKnownSpellsForClassBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        abilityNameToPass = null;

        abilitiesListView = binding.abilitiesListView;
        spellsListView = binding.spellsListView;

        classNameTextView = binding.characterNameTxtview2;
        classNameTextView.setText(classNameToPass);

        //populates abilities listView
        abilitiesArrayList = new ArrayList<>();
        abilitiesAdapter = new ArrayAdapter<>(getContext(), R.layout.character_creation_list_text, abilitiesArrayList);
        abilitiesListView.setAdapter(abilitiesAdapter);

        new Thread(new Runnable(){
            @Override
            public void run(){
                List<String> list = abilityDao.getNamesByAbilityOrSpellForClass("ability", classNameToPass);
                abilitiesArrayList.addAll(list);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        abilitiesAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();

        abilitiesAdapter.notifyDataSetChanged();

        spellsArrayList = new ArrayList<>();
        spellsAdapter = new ArrayAdapter<>(getContext(), R.layout.character_creation_list_text, spellsArrayList);
        spellsListView.setAdapter(spellsAdapter);

        new Thread(new Runnable(){
            @Override
            public void run(){
                List<String> list = abilityDao.getNamesByAbilityOrSpellForClass("spell", classNameToPass);
                spellsArrayList.addAll(list);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        spellsAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();

        spellsAdapter.notifyDataSetChanged();

        scrollableListView(abilitiesListView, R.id.abilities_listView);
        scrollableListView(spellsListView, R.id.spells_listView);


        //go to ability corresponding to name
        abilitiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = view.findViewById(R.id.character_creation_list_textView);
                abilityNameToPass = textView.getText().toString();

                NavHostFragment.findNavController(knownSpellsForClass.this)
                        .navigate(R.id.action_knownSpellsForClass_to_abilityFragment);
            }
        });

        spellsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = view.findViewById(R.id.character_creation_list_textView);
                abilityNameToPass = textView.getText().toString();

                NavHostFragment.findNavController(knownSpellsForClass.this)
                        .navigate(R.id.action_knownSpellsForClass_to_abilityFragment);
            }
        });

        // deletes ability by name and updates listView
        onLongClickDeleteAbilityFromDatabaseAndRefresh();

        // deletes spells by name and updates listView
        onLongClickDeleteSpellFromDatabaseAndRefresh();

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navigationButton(binding.addAbilityButton,knownSpellsForClass.this,
                R.id.action_knownSpellsForClass_to_abilityFragment);
    }

    private void onLongClickDeleteAbilityFromDatabaseAndRefresh() {
        abilitiesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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

                                abilitiesArrayList.clear();
                                abilitiesAdapter.clear();
                                abilitiesAdapter.notifyDataSetChanged();
                                abilitiesListView.setAdapter(null);

                                new Thread(new Runnable(){
                                    @Override
                                    public void run(){
                                        int id, preparedId;
                                        id = abilityDao.findIdByName(abilityToDelete);
                                        preparedId = preparedAbilityDao.findIdByName(abilityToDelete);
                                        abilityDao.deleteByAbilityId(id);
                                        preparedAbilityDao.deleteByPreparedAbilityId(preparedId);

                                        abilitiesArrayList = new ArrayList<>();
                                        abilitiesAdapter = new ArrayAdapter<>(getContext(),
                                                R.layout.character_creation_list_text, abilitiesArrayList);
                                        List<String> list = abilityDao.getNamesByAbilityOrSpellForClass("ability", classNameToPass);
                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                abilitiesListView.setAdapter(abilitiesAdapter);
                                                abilitiesArrayList.addAll(list);
                                                abilitiesAdapter.notifyDataSetChanged();
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
                return true;
            }
        });
    }

    private void onLongClickDeleteSpellFromDatabaseAndRefresh() {
        spellsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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

                                spellsArrayList.clear();
                                spellsAdapter.clear();
                                spellsAdapter.notifyDataSetChanged();
                                spellsListView.setAdapter(null);

                                new Thread(new Runnable(){
                                    @Override
                                    public void run(){
                                        int id, preparedId;
                                        id = abilityDao.findIdByName(abilityToDelete);
                                        preparedId = preparedAbilityDao.findIdByName(abilityToDelete);
                                        abilityDao.deleteByAbilityId(id);
                                        preparedAbilityDao.deleteByPreparedAbilityId(preparedId);

                                        spellsArrayList = new ArrayList<>();
                                        spellsAdapter = new ArrayAdapter<>(getContext(),
                                                R.layout.character_creation_list_text, spellsArrayList);
                                        List<String> list = abilityDao.getNamesByAbilityOrSpellForClass("spell", classNameToPass);

                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                spellsListView.setAdapter(spellsAdapter);
                                                spellsArrayList.addAll(list);
                                                spellsAdapter.notifyDataSetChanged();
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
                return true;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}