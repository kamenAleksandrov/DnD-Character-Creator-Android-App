package com.example.dndCharacterCreator.appDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Ability.class}, version = 5)
public abstract class AbilitiesDatabase extends RoomDatabase {

    public abstract AbilityDao abilityDao();
}