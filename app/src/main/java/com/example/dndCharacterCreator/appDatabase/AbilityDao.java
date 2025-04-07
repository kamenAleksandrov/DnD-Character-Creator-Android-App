package com.example.dndCharacterCreator.appDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AbilityDao {

    @Insert
    void insertAbility(Ability ability);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAbility(Ability ability);

    @Delete
    void deleteAbility(Ability ability);

    @Query("SELECT * FROM ability WHERE abilityName LIKE :name")
    Ability findByName(String name);

    @Query("SELECT abilityName FROM ability")
    List<String> getNames();

    @Query("SELECT abilityName FROM ability where abilityOrSpell like :abilityOrSpell")
    List<String> getNamesByAbilityOrSpell(String abilityOrSpell);

    /* staro
    @Query("SELECT abilityName FROM ability where preparedOrNot like :abilityOrSpell")
    List<String> getNamesByPreparedOrNot(String abilityOrSpell);
     */

    @Query("select abilityId from ability where abilityName like :checkedName")
    int findIdByName(String checkedName);

    @Query("select abilityName from ability where abilityId like :id")
    String getNameById(int id);

    @Query("select level from ability where abilityId like :id")
    String getLevelById(int id);

    @Query("select actionOrBonusAction from ability where abilityId like :id")
    String getActionOrBonusActionById(int id);

    @Query("select duration from ability where abilityId like :id")
    String getDurationById(int id);

    @Query("select range from ability where abilityId like :id")
    String getRangeById(int id);

    @Query("select castingTime from ability where abilityId like :id")
    String getCastingTimeById(int id);

    @Query("select damageRoll from ability where abilityId like :id")
    String getDamageRollById(int id);

    @Query("select abilityDescription from ability where abilityId like :id")
    String getAbilityDescriptionById(int id);

    @Query("select abilityOrSpell from ability where abilityId like :id")
    String getIfAbilityOrSpellById(int id);

    /* staro
    @Query("select preparedOrNot from ability where abilityId like :id")
    String getIfPreparedOrNotById(int id);
     */

    @Query("select abilityClass from ability where abilityId like :id")
    String getAbilityClass(int id);

    @Query("SELECT abilityName FROM ability where abilityOrSpell like :abilityOrSpell and abilityClass like :abilityClass")
    List<String> getNamesByAbilityOrSpellForClass(String abilityOrSpell, String abilityClass);

    @Query("DELETE FROM Ability WHERE abilityId = :abilityId")
    void deleteByAbilityId(long abilityId);

}