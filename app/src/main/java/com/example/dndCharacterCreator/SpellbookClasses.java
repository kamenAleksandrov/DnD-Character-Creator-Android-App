package com.example.dndCharacterCreator;

import static com.example.dndCharacterCreator.MainActivity.classNameToPass;
import static com.example.dndCharacterCreator.UtilityForFragments.navigationButton;
import static com.example.dndCharacterCreator.UtilityForFragments.scrollableListView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dndCharacterCreator.databinding.FragmentSpellbookClassesBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SpellbookClasses extends Fragment {

    private FragmentSpellbookClassesBinding binding;

    EditText editText;
    AppCompatButton button, allSpellsButton, addAbilityOrSpell;
    ListView listView;

    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    SharedPreferences classesNamesSharedPreferences;
    Map<String, ?> namesMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSpellbookClassesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        editText = binding.classNameCreationEditText;
        button = binding.createClassButton;

        allSpellsButton = binding.allSpellsButton;
        addAbilityOrSpell = binding.addAbilityButton2;

        listView = binding.createdClassesListView;

        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), R.layout.character_creation_list_text, arrayList);
        listView.setAdapter(adapter);

        classNameToPass = null;

        classesNamesSharedPreferences = requireActivity()
                .getSharedPreferences("Classes Names", Context.MODE_PRIVATE);
        SharedPreferences.Editor classesNamesEditor = classesNamesSharedPreferences.edit();

        //adds classes names after restart
        namesMap = classesNamesSharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : namesMap.entrySet()) {
            final String key = entry.getKey();
            final Object value = entry.getValue();
        }

        List<String> list = new ArrayList<String>((Collection<? extends String>) namesMap.values());
        arrayList.addAll(list);
        Collections.sort(arrayList);
        adapter.notifyDataSetChanged();
        //names added

        //checks if char name is a duplicate, if not creates it
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String className = editText.getText().toString();
                //fixes duplicates when class is deleted and then one with the same name is created
                arrayList.clear();
                addClassNames();
                if (!className.equals("")) {
                    if (namesMap.containsValue(className)){
                        Toast.makeText(getContext(), "Name already exists!",Toast.LENGTH_SHORT).show();
                    } else {
                        arrayList.add(0,className);
                        classesNamesEditor.putString(className, className);
                        classesNamesEditor.apply();
                        adapter.notifyDataSetChanged();
                        editText.setText("");
                    }
                } else {
                    Toast.makeText(getContext(), "Field is empty!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //go to abilities corresponding to class name
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = view.findViewById(R.id.character_creation_list_textView);
                classNameToPass = textView.getText().toString();

                NavHostFragment.findNavController(SpellbookClasses.this)
                        .navigate(R.id.action_spellbookClasses_to_knownSpellsForClass);
            }
        });

        // deletes class by name
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView textView = view.findViewById(R.id.character_creation_list_textView);
                String classToDelete = textView.getText().toString();

                new AlertDialog.Builder(getActivity())
                        .setTitle("Delete class?")
                        .setMessage("Are you sure you want to delete " + classToDelete)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                classesNamesEditor.remove(classToDelete);
                                classesNamesEditor.apply();
                                // updates listview names to display change
                                list.remove(classToDelete);
                                arrayList.clear();
                                addClassNames();
                                listView.setAdapter(null);
                                listView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
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

        //makes listview scrollable
        scrollableListView(listView, R.id.createdClasses_listView);

        navigationButton(binding.allSpellsButton,SpellbookClasses.this ,R.id.action_spellbookClasses_to_knownSpellsFragment);
        navigationButton(binding.addAbilityButton2,SpellbookClasses.this ,R.id.action_spellbookClasses_to_abilityFragment);

        return view;
    }

    public void addClassNames () {

        namesMap = classesNamesSharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : namesMap.entrySet()) {
            final String key = entry.getKey();
            final Object value = entry.getValue();
        }

        List<String> list = new ArrayList<String>((Collection<? extends String>) namesMap.values());
        arrayList.addAll(list);
        Collections.sort(arrayList);
        adapter.notifyDataSetChanged();
    }

    public static boolean deleteSharedPreferences(Context context, String name) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.deleteSharedPreferences(name);
        } else {
            context.getSharedPreferences(name, Context.MODE_PRIVATE).edit().clear().apply();
            File dir = new File(context.getApplicationInfo().dataDir, "shared_prefs");
            return new File(dir, name + ".xml").delete();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}