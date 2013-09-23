package org.kickass.lordofpomelo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "bigint(20)")
    private long id;
    @Column(columnDefinition = "varchar(50)")
    private String name;
    @Column(name = "[from]", columnDefinition = "varchar(50)")
    private String from;
    @Column(columnDefinition = "varchar(50)")
    private String password;

    @Column(columnDefinition = "smallint(6)")
    private int loginCount;
    @Column(columnDefinition = "bigint(20)")
    private long lastLoginTime;

    public User() {
    }

    public User(String name, String password, String from) {
        this.name = name;
        this.password = password;
        this.loginCount = 0;
        this.from = from;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

}
