package com.example.dndCharacterCreator.appDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PreparedAbilityDao {

    @Insert
    void insertPreparedAbility(PreparedAbility preparedAbility);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updatePreparedAbility(PreparedAbility preparedAbility);

    @Delete
    void deletePreparedAbility(PreparedAbility preparedAbility);

    @Query("SELECT preparedAbilityName FROM PreparedAbility")
    List<String> getPreparedNames();

    @Query("select preparedAbilityId from preparedability where preparedAbilityName like :checkedName")
    int findIdByName(String checkedName);

    @Query("DELETE FROM PreparedAbility WHERE preparedAbilityId = :abilityId")
    void deleteByPreparedAbilityId(long abilityId);
}