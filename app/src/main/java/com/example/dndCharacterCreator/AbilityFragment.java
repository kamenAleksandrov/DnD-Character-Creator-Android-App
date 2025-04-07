package com.example.dndCharacterCreator;

import static com.example.dndCharacterCreator.FirstFragment.preparedAbilitiesDatabase;
import static com.example.dndCharacterCreator.MainActivity.abilitiesDatabase;
import static com.example.dndCharacterCreator.MainActivity.abilityNameToPass;
import static com.example.dndCharacterCreator.MainActivity.classNameToPass;
import static com.example.dndCharacterCreator.UtilityForFragments.onTouchScroll;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dndCharacterCreator.appDatabase.Ability;
import com.example.dndCharacterCreator.appDatabase.AbilityDao;
import com.example.dndCharacterCreator.appDatabase.PreparedAbilityDao;
import com.example.dndCharacterCreator.databinding.FragmentAbilityBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class  AbilityFragment extends Fragment {

    private FragmentAbilityBinding binding;
    AppCompatButton button;
    EditText name, level, range, damageRoll, castingTime,
            duration, actionOrBonusAction, description;
    RadioGroup abilityOrSpellRadioButtonGroup;
    RadioButton abilityRb, spellRb;
    //tova e staroto prepearvane na magii ama mai go izmaistorih po-dobre
    //ToggleButton preparedOrNot;
    AbilityDao abilityDao = abilitiesDatabase.abilityDao();
    PreparedAbilityDao preparedAbilityDao = preparedAbilitiesDatabase.preparedAbilityDao();
    Spinner spinner;
    private String abilityOrSpellString;
    // staro
    //private String preparedOrNotString;

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
        binding = FragmentAbilityBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        button = binding.saveAbilityButton;

        name = binding.abilityNameEditText;
        level = binding.abilityLevelEditText;
        range = binding.abilityRangeEditText;
        damageRoll = binding.abilityDamageRollEditText;
        castingTime = binding.abilityCasttingTimeEditText;
        duration = binding.abilityDurationEditText;
        actionOrBonusAction = binding.actionOrBonusActionEditText;
        description = binding.abilityDescriptionEditText;
        abilityOrSpellRadioButtonGroup = binding.abilityOrSpellRadioGroup;
        abilityRb = binding.abilityRadioButton;
        spellRb = binding.spellRadioButton;
        //staro
        //preparedOrNot = binding.preparedOrNotToggleButton;
        spinner = binding.chooseClassSpinner;

        //adds classes to spinner
        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), R.layout.character_creation_list_text, arrayList);
        //adapter.setDropDownViewResource(R.layout.character_creation_list_text);
        spinner.setAdapter(adapter);

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

        //checks if you are trying to edit ability or create one
        if (abilityNameToPass == null){
            if (classNameToPass != null){
                int setSpinnerValue = adapter.getPosition(classNameToPass);
                spinner.setSelection(setSpinnerValue);
            }
        }else {
            new Thread(new Runnable(){
                @Override
                public void run(){
                    int id;
                    id = abilityDao.findIdByName(abilityNameToPass);
                    name.setText(abilityDao.getNameById(id));
                    level.setText(abilityDao.getLevelById(id));
                    range.setText(abilityDao.getRangeById(id));
                    damageRoll.setText(abilityDao.getDamageRollById(id));
                    castingTime.setText(abilityDao.getCastingTimeById(id));
                    duration.setText(abilityDao.getDurationById(id));
                    actionOrBonusAction.setText(abilityDao.getActionOrBonusActionById(id));
                    description.setText(abilityDao.getAbilityDescriptionById(id));
                    if (abilityDao.getIfAbilityOrSpellById(id).equals("ability")){
                        abilityRb.setChecked(true);
                        abilityRb.setSelected(true);
                    }else {
                        spellRb.setChecked(true);
                        spellRb.setSelected(true);
                    }
                    /* staro
                    if (abilityDao.getIfPreparedOrNotById(id).equals("prepared")){
                        preparedOrNot.setChecked(true);
                    }

                     */
                    int setSpinnerValue = adapter.getPosition(abilityDao.getAbilityClass(id));
                    spinner.setSelection(setSpinnerValue);
                }
            }).start();
        }

        onTouchScroll(level, R.id.abilityLevel_editText);
        onTouchScroll(actionOrBonusAction, R.id.actionOrBonusAction_editText);
        onTouchScroll(duration, R.id.abilityDuration_editText);
        onTouchScroll(range, R.id.abilityRange_editText);
        onTouchScroll(castingTime, R.id.abilityCasttingTime_editText);
        onTouchScroll(damageRoll, R.id.abilityDamageRoll_editText);
        onTouchScroll(description,R.id.abilityDescription_editText);

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        abilityOrSpellRadioButtonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                abilityOrSpellRadioButtonGroup.getCheckedRadioButtonId();
                if (abilityOrSpellRadioButtonGroup.getCheckedRadioButtonId() == R.id.ability_radioButton){
                    abilityOrSpellString = "ability";
                }else {
                    abilityOrSpellString = "spell";
                }
            }
        });

        /* staro
        preparedOrNotString = "notPrepared";
        preparedOrNot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (preparedOrNot.isChecked()){
                    preparedOrNotString = "prepared";
                }else {
                    preparedOrNotString = "notPrepared";
                }
            }
        });

         */

        //save or update button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable(){
                    @Override
                    public void run(){

                        List<String> list = abilityDao.getNames();
                        // staro
                        //List<String> preparedList = preparedAbilityDao.getPreparedNames();

                        String nameST, levelST, rangeST, damageRollST, castingTimeST,
                                durationST, actionOrBonusActionST, descriptionST, spinnerValue = null;

                        nameST = name.getText().toString();
                        levelST = level.getText().toString();
                        rangeST = range.getText().toString();
                        damageRollST = damageRoll.getText().toString();
                        castingTimeST = castingTime.getText().toString();
                        durationST = duration.getText().toString();
                        actionOrBonusActionST = actionOrBonusAction.getText().toString();
                        descriptionST = description.getText().toString();
                        if (spinner.getSelectedItem() != null){
                            spinnerValue =  spinner.getSelectedItem().toString();
                        }

                        if (nameST.equals("") || abilityOrSpellString == null || spinnerValue == null) {

                            if (nameST.equals("")){
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(), "Please enter name!",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            if (abilityOrSpellString == null){
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(), "Please choose ability or spell!",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            if (spinnerValue == null){
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(), "Please add a class!",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }else {
                            if (levelST.equals("")){
                                levelST = "~";
                            }
                            if (rangeST.equals("")){
                                rangeST = "~";
                            }
                            if (damageRollST.equals("")){
                                damageRollST = "~";
                            }
                            if (castingTimeST.equals("")){
                                castingTimeST = "~";
                            }
                            if (durationST.equals("")){
                                durationST = "~";
                            }
                            if (actionOrBonusActionST.equals("")){
                                actionOrBonusActionST = "~";
                            }
                            if (descriptionST.equals("")){
                                descriptionST = "~";
                            }
                            if (list.contains(nameST)) {
                                int id, preparedId;
                                id = abilityDao.findIdByName(nameST);
                                // staro
                                //preparedId = preparedAbilityDao.findIdByName(nameST);
                                abilityDao.updateAbility(new Ability(id, nameST, levelST, actionOrBonusActionST, durationST, rangeST,
                                        castingTimeST, damageRollST, descriptionST, abilityOrSpellString, spinnerValue));
                                /* staro
                                if (preparedOrNot.isChecked()){
                                    if (preparedList.contains(nameST)){
                                        preparedAbilityDao.updatePreparedAbility(new PreparedAbility(preparedId, nameST));
                                    }else {
                                        preparedAbilityDao.insertPreparedAbility(new PreparedAbility(nameST));
                                    }
                                }else {
                                    preparedAbilityDao.deleteByPreparedAbilityId(preparedId);
                                }

                                 */
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(), "Updated!",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                abilityDao.insertAbility(new Ability(nameST, levelST, actionOrBonusActionST, durationST, rangeST,
                                        castingTimeST, damageRollST, descriptionST, abilityOrSpellString, spinnerValue));
                                /* staro
                                if (preparedOrNot.isChecked()){
                                    preparedAbilityDao.insertPreparedAbility(new PreparedAbility(nameST));
                                }

                                 */
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(), "Created!",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}