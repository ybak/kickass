package org.kickass.lordofpomelo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Equipments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "bigint(20)")
    private long id;
    @Column(columnDefinition = "bigint(20)")
    private long playerId;
    @Column(columnDefinition = "smallint(6)")
    private int weapon;
    @Column(columnDefinition = "smallint(6)")
    private int armor;
    @Column(columnDefinition = "smallint(6)")
    private int helmet;
    @Column(columnDefinition = "smallint(6)")
    private int necklace;
    @Column(columnDefinition = "smallint(6)")
    private int ring;
    @Column(columnDefinition = "smallint(6)")
    private int belt;
    @Column(columnDefinition = "smallint(6)")
    private int shoes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getWeapon() {
        return weapon;
    }

    public void setWeapon(int weapon) {
        this.weapon = weapon;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getHelmet() {
        return helmet;
    }

    public void setHelmet(int helmet) {
        this.helmet = helmet;
    }

    public int getNecklace() {
        return necklace;
    }

    public void setNecklace(int necklace) {
        this.necklace = necklace;
    }

    public int getRing() {
        return ring;
    }

    public void setRing(int ring) {
        this.ring = ring;
    }

    public int getBelt() {
        return belt;
    }

    public void setBelt(int belt) {
        this.belt = belt;
    }

    public int getShoes() {
        return shoes;
    }

    public void setShoes(int shoes) {
        this.shoes = shoes;
    }

}
