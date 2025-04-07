package com.example.dndCharacterCreator;

import static com.example.dndCharacterCreator.MainActivity.classNameToPass;
import static com.example.dndCharacterCreator.UtilityForFragments.scrollableListView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dndCharacterCreator.databinding.FragmentPreparedSpellbookClassesBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class preparedSpellbookClasses extends Fragment {

    private FragmentPreparedSpellbookClassesBinding binding;

    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    SharedPreferences classesNamesSharedPreferences;
    Map<String, ?> namesMap;

    public preparedSpellbookClasses() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPreparedSpellbookClassesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        listView = binding.classesToPrepareListView;

        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), R.layout.character_creation_list_text, arrayList);
        listView.setAdapter(adapter);

        classNameToPass = null;

        classesNamesSharedPreferences = requireActivity()
                .getSharedPreferences("Classes Names", Context.MODE_PRIVATE);

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

        if (list.isEmpty()){
            Toast.makeText(getContext(), "Please add a class in the spellbook!",Toast.LENGTH_LONG).show();
        }
        scrollableListView(listView, R.id.classesToPrepare_listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = view.findViewById(R.id.character_creation_list_textView);
                classNameToPass = textView.getText().toString();

                NavHostFragment.findNavController(preparedSpellbookClasses.this)
                        .navigate(R.id.action_preparedSpellbookClasses_to_preparedKnownSpellsForClass);
            }
        });

        return view;
    }
}