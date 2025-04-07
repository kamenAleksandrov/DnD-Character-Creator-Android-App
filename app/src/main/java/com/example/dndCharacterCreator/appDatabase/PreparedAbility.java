package com.example.dndCharacterCreator.appDatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PreparedAbility {

    @PrimaryKey(autoGenerate = true)
    private int preparedAbilityId;
    private String preparedAbilityName;

    public PreparedAbility() {
    }

    public PreparedAbility(int preparedAbilityId) {
        this.preparedAbilityId = preparedAbilityId;
    }

    public PreparedAbility(int preparedAbilityId, String preparedAbilityName) {
        this.preparedAbilityId = preparedAbilityId;
        this.preparedAbilityName = preparedAbilityName;
    }

    public PreparedAbility(String preparedAbilityName) {
        this.preparedAbilityName = preparedAbilityName;
    }

    public int getPreparedAbilityId() {
        return preparedAbilityId;
    }

    public String getPreparedAbilityName() {
        return preparedAbilityName;
    }

    public void setPreparedAbilityId(int preparedAbilityId) {
        this.preparedAbilityId = preparedAbilityId;
    }

    public void setPreparedAbilityName(String preparedAbilityName) {
        this.preparedAbilityName = preparedAbilityName;
    }
}