package com.example.dndCharacterCreator;

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

import com.example.dndCharacterCreator.databinding.FragmentCharacterCreationBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static com.example.dndCharacterCreator.MainActivity.characterNameToPass;
import static com.example.dndCharacterCreator.UtilityForFragments.scrollableListView;

public class CharacterCreation extends Fragment {

    private FragmentCharacterCreationBinding binding;

    EditText editText;
    AppCompatButton button;
    ListView listView;

    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    SharedPreferences characterNamesSharedPreferences;
    SharedPreferences characterPreferences;
    Map<String, ?> namesMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCharacterCreationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        editText = binding.characterNameCreationEditText;
        button = binding.createCharacterButton;
        listView = binding.createdCharactersListView;

        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), R.layout.character_creation_list_text, arrayList);
        listView.setAdapter(adapter);

        characterNamesSharedPreferences = requireActivity()
                .getSharedPreferences("Character Names", Context.MODE_PRIVATE);
        SharedPreferences.Editor characterNamesEditor = characterNamesSharedPreferences.edit();

        //adds character names after restart
        namesMap = characterNamesSharedPreferences.getAll();
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
                String characterName = editText.getText().toString();
                //fixes duplicates when character is deleted and than one with the same name is created
                arrayList.clear();
                addCharacterNames();
                if (!characterName.equals("")) {
                    if (namesMap.containsValue(characterName)){
                        Toast.makeText(getContext(), "Name already exists!",Toast.LENGTH_SHORT).show();
                    } else {
                        arrayList.add(0,characterName);
                        characterNamesEditor.putString(characterName, characterName);
                        characterNamesEditor.apply();
                        adapter.notifyDataSetChanged();
                        editText.setText("");
                        //creates a sharedpreference for the character
                        characterPreferences = requireActivity()
                                .getSharedPreferences(characterName, Context.MODE_PRIVATE);
                        SharedPreferences.Editor characterPreferencesEditor = characterPreferences.edit();
                        characterPreferencesEditor.putString("default", null);
                        characterPreferencesEditor.apply();
                    }
                } else {
                    Toast.makeText(getContext(), "Field is empty!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //go to sheet corresponding to character name
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = view.findViewById(R.id.character_creation_list_textView);
                characterNameToPass = textView.getText().toString();

                NavHostFragment.findNavController(CharacterCreation.this)
                        .navigate(R.id.action_characterCreation_to_FirstFragment);
            }
        });

        // deletes character by name
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView textView = view.findViewById(R.id.character_creation_list_textView);
                String characterToDelete = textView.getText().toString();

                new AlertDialog.Builder(getActivity())
                        .setTitle("Delete character?")
                        .setMessage("Are you sure you want to delete " + characterToDelete)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                characterNamesEditor.remove(characterToDelete);
                                characterNamesEditor.apply();
                                // updates listview names to display change
                                list.remove(characterToDelete);
                                arrayList.clear();
                                addCharacterNames();
                                listView.setAdapter(null);
                                listView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                deleteSharedPreferences(getContext(), characterToDelete);
                                getContext().deleteDatabase(characterToDelete + "PreparedAbilities");
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

        return view;
    }

    public void addCharacterNames () {

        namesMap = characterNamesSharedPreferences.getAll();
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