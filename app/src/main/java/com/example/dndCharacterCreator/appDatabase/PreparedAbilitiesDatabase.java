package com.example.dndCharacterCreator.appDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {PreparedAbility.class}, version = 1)
public abstract class PreparedAbilitiesDatabase extends RoomDatabase {

    public abstract PreparedAbilityDao preparedAbilityDao();

}