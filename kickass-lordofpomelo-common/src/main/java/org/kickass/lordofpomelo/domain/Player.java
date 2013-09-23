package org.kickass.lordofpomelo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "bigint(20)")
    private long id;
    @Column(columnDefinition = "bigint(20)")
    private long userId;
    @Column(columnDefinition = "varchar(10)")
    private String kindId;
    @Column(columnDefinition = "varchar(50)")
    private String name;
    @Column(columnDefinition = "smallint(6)")
    private int country;
    @Column(columnDefinition = "smallint(6)")
    private int rank;
    @Column(columnDefinition = "smallint(6)")
    private int level;
    @Column(columnDefinition = "smallint(6)")
    private int experience;
    @Column(columnDefinition = "smallint(6)")
    private int attackValue;
    @Column(columnDefinition = "smallint(6)")
    private int defenceValue;
    @Column(columnDefinition = "smallint(6)")
    private int hitRate;
    @Column(columnDefinition = "smallint(6)")
    private int dodgeRate;
    @Column(columnDefinition = "smallint(6)")
    private int walkSpeed;
    @Column(columnDefinition = "smallint(6)")
    private int attackSpeed;
    @Column(columnDefinition = "smallint(6)")
    private int hp;
    @Column(columnDefinition = "smallint(6)")
    private int mp;
    @Column(columnDefinition = "smallint(6)")
    private int maxHp;
    @Column(columnDefinition = "smallint(6)")
    private int maxMp;
    @Column(columnDefinition = "bigint(20)")
    private long areaId;
    @Column(columnDefinition = "int(10)")
    private int x;
    @Column(columnDefinition = "int(10)")
    private int y;
    @Column(columnDefinition = "varchar(30)")
    private String kindName;
    @Column(columnDefinition = "int(10)")
    private int skillPoint;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getKindId() {
        return kindId;
    }

    public void setKindId(String kindId) {
        this.kindId = kindId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getAttackValue() {
        return attackValue;
    }

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }

    public int getDefenceValue() {
        return defenceValue;
    }

    public void setDefenceValue(int defenceValue) {
        this.defenceValue = defenceValue;
    }

    public int getHitRate() {
        return hitRate;
    }

    public void setHitRate(int hitRate) {
        this.hitRate = hitRate;
    }

    public int getDodgeRate() {
        return dodgeRate;
    }

    public void setDodgeRate(int dodgeRate) {
        this.dodgeRate = dodgeRate;
    }

    public int getWalkSpeed() {
        return walkSpeed;
    }

    public void setWalkSpeed(int walkSpeed) {
        this.walkSpeed = walkSpeed;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public void setMaxMp(int maxMp) {
        this.maxMp = maxMp;
    }

    public long getAreaId() {
        return areaId;
    }

    public void setAreaId(long areaId) {
        this.areaId = areaId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public int getSkillPoint() {
        return skillPoint;
    }

    public void setSkillPoint(int skillPoint) {
        this.skillPoint = skillPoint;
    }

}
