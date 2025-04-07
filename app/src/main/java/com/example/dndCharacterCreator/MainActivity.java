package com.example.dndCharacterCreator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.view.MotionEvent;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.example.dndCharacterCreator.appDatabase.AbilitiesDatabase;
import com.example.dndCharacterCreator.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    //4376
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    SharedPreferences characterNamesSharedPreferences, classesNamesSharedPreferences, lastDiceRollsSharedPreferences;
    static String characterNameToPass;
    static String classNameToPass;
    static String abilityNameToPass;
    static String imagesFolderToPass;
    static AbilitiesDatabase abilitiesDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /* code for migration but not finished
        static final Migration MIGRATION_1_2 = new Migration(1, 2) {
            @Override
            public void migrate(SupportSQLiteDatabase database) {
                database.execSQL("ALTER TABLE users "
                        +"ADD COLUMN address TEXT");
            }
        };
         */
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                //build abilities database
                abilitiesDatabase = Room.databaseBuilder(getApplicationContext(),
                                AbilitiesDatabase.class, "Abilities Database")
                        //.fallbackToDestructiveMigration() //add this to clear database (no need for migration but deletes everything)
                        .build();
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {

                //create prefs on start
                characterNamesSharedPreferences = getApplicationContext()
                        .getSharedPreferences("Character Names", Context.MODE_PRIVATE);
                SharedPreferences.Editor characterNamesEditor = characterNamesSharedPreferences.edit();
                characterNamesEditor.putString("default", null);
                characterNamesEditor.apply();

                classesNamesSharedPreferences = getApplicationContext()
                        .getSharedPreferences("Classes Names", Context.MODE_PRIVATE);
                SharedPreferences.Editor classesNamesEditor = classesNamesSharedPreferences.edit();
                classesNamesEditor.putString("default", null);
                classesNamesEditor.apply();

                lastDiceRollsSharedPreferences = getApplicationContext()
                        .getSharedPreferences("Last Dice Rolls", Context.MODE_PRIVATE);
                SharedPreferences.Editor lastDiceRollsEditor = lastDiceRollsSharedPreferences.edit();
                lastDiceRollsEditor.putString("default", null);
                lastDiceRollsEditor.apply();
            }
        }).start();

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        Context context = getApplicationContext();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               DiceRollerDialogFragment newFragment = new DiceRollerDialogFragment();
               newFragment.show(getSupportFragmentManager(),"dice");
            }
        });

        binding.fab.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                v.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        switch (event.getActionMasked()) {
                            case MotionEvent.ACTION_MOVE:
                                view.setX(event.getRawX() - 150);
                                view.setY(event.getRawY() - 200);
                                break;
                            case MotionEvent.ACTION_UP:
                                view.setOnTouchListener(null);
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

}