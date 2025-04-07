package com.example.dndCharacterCreator;

import static com.example.dndCharacterCreator.FirstFragment.preparedAbilitiesDatabase;
import static com.example.dndCharacterCreator.MainActivity.abilitiesDatabase;
import static com.example.dndCharacterCreator.MainActivity.abilityNameToPass;
import static com.example.dndCharacterCreator.MainActivity.classNameToPass;
import static com.example.dndCharacterCreator.UtilityForFragments.scrollableListView;

import android.os.Bundle;

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
import android.widget.Toast;

import com.example.dndCharacterCreator.appDatabase.AbilityDao;
import com.example.dndCharacterCreator.appDatabase.PreparedAbility;
import com.example.dndCharacterCreator.appDatabase.PreparedAbilityDao;
import com.example.dndCharacterCreator.databinding.FragmentKnownSpellsForClassBinding;
import com.example.dndCharacterCreator.databinding.FragmentPreparedKnownSpellsForClassBinding;

import java.util.ArrayList;
import java.util.List;


public class preparedKnownSpellsForClass extends Fragment {

    private FragmentPreparedKnownSpellsForClassBinding binding;

    AbilityDao abilityDao = abilitiesDatabase.abilityDao();
    PreparedAbilityDao preparedAbilityDao = preparedAbilitiesDatabase.preparedAbilityDao();
    ListView abilitiesListView, spellsListView;
    ArrayList<String> abilitiesArrayList, spellsArrayList;
    ArrayAdapter<String> abilitiesAdapter, spellsAdapter;
    TextView classNameTextView;

    public preparedKnownSpellsForClass() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPreparedKnownSpellsForClassBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        abilityNameToPass = null;

        abilitiesListView = binding.preparedAbilitiesListView;
        spellsListView = binding.preparedSpellsListView;

        classNameTextView = binding.preparedClassNameTxtview;
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
        abilitiesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = view.findViewById(R.id.character_creation_list_textView);
                abilityNameToPass = textView.getText().toString();

                NavHostFragment.findNavController(preparedKnownSpellsForClass.this)
                        .navigate(R.id.action_preparedKnownSpellsForClass_to_abilityFragment);
                return true;
            }
        });

        spellsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = view.findViewById(R.id.character_creation_list_textView);
                abilityNameToPass = textView.getText().toString();

                NavHostFragment.findNavController(preparedKnownSpellsForClass.this)
                        .navigate(R.id.action_preparedKnownSpellsForClass_to_abilityFragment);
                return true;
            }
        });

        // checks prepared abilities and adds or removes ability when clicked
        abilitiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
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
                                    Toast.makeText(getContext(), "Removed: " + preparedAbility + "!",Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            preparedAbilityDao.insertPreparedAbility(new PreparedAbility(preparedAbility));
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), "Added: " + preparedAbility + "!",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });

        spellsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
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
                                    Toast.makeText(getContext(), "Removed: " + preparedAbility + "!",Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            preparedAbilityDao.insertPreparedAbility(new PreparedAbility(preparedAbility));
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), "Added: " + preparedAbility + "!",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });

        return view;
    }
}