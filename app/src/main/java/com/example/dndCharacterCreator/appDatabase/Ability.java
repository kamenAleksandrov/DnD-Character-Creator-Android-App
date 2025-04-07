package com.example.dndCharacterCreator.appDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Ability {

    @PrimaryKey(autoGenerate = true)
    private int abilityId;
    @ColumnInfo(name = "abilityName")
    private String abilityName;
    private String level;
    private String actionOrBonusAction;
    private String duration;
    private String range;
    private String castingTime;
    private String damageRoll;
    private String abilityDescription;
    private String abilityOrSpell;
    private String abilityClass;

    public Ability() {
    }


    public Ability(String abilityName, String level, String actionOrBonusAction, String duration,
                   String range, String castingTime, String damageRoll, String abilityDescription,
                   String abilityOrSpell, String abilityClass) {
        this.abilityName = abilityName;
        this.level = level;
        this.actionOrBonusAction = actionOrBonusAction;
        this.duration = duration;
        this.range = range;
        this.castingTime = castingTime;
        this.damageRoll = damageRoll;
        this.abilityDescription = abilityDescription;
        this.abilityOrSpell = abilityOrSpell;
        this.abilityClass = abilityClass;
    }

    public Ability(int abilityId, String abilityName, String level, String actionOrBonusAction,
                   String duration, String range, String castingTime, String damageRoll,
                   String abilityDescription, String abilityOrSpell,  String abilityClass) {
        this.abilityId = abilityId;
        this.abilityName = abilityName;
        this.level = level;
        this.actionOrBonusAction = actionOrBonusAction;
        this.duration = duration;
        this.range = range;
        this.castingTime = castingTime;
        this.damageRoll = damageRoll;
        this.abilityDescription = abilityDescription;
        this.abilityOrSpell = abilityOrSpell;
        this.abilityClass = abilityClass;
    }

    public void setAbilityClass(String abilityClass) {
        this.abilityClass = abilityClass;
    }

    public void setAbilityOrSpell(String abilityOrSpell) {
        this.abilityOrSpell = abilityOrSpell;
    }

    public void setAbilityId(int abilityId) {
        this.abilityId = abilityId;
    }

    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setActionOrBonusAction(String actionOrBonusAction) {
        this.actionOrBonusAction = actionOrBonusAction;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public void setCastingTime(String castingTime) {
        this.castingTime = castingTime;
    }

    public void setDamageRoll(String damageRoll) {
        this.damageRoll = damageRoll;
    }

    public void setAbilityDescription(String abilityDescription) {
        this.abilityDescription = abilityDescription;
    }

    public String getAbilityClass() {
        return abilityClass;
    }

    public String getAbilityOrSpell() {
        return abilityOrSpell;
    }

    public int getAbilityId() {
        return abilityId;
    }

    public String getAbilityName() {
        return abilityName;
    }

    public String getLevel() {
        return level;
    }

    public String getActionOrBonusAction() {
        return actionOrBonusAction;
    }

    public String getDuration() {
        return duration;
    }

    public String getRange() {
        return range;
    }

    public String getCastingTime() {
        return castingTime;
    }

    public String getDamageRoll() {
        return damageRoll;
    }

    public String getAbilityDescription() {
        return abilityDescription;
    }

}