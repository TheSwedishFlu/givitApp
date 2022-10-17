package com.example.demo;

import javax.persistence.*;
import java.util.Set;

@Entity
//@Table (name="account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(unique = true)
    private int orgnr;
    private String city;
    private String email;
    private String password;

   /* @OneToMany(mappedBy = "account", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Item> items;*/

    public Account() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrgnr() {
        return orgnr;
    }

    public void setOrgnr(int orgnr) {
        this.orgnr = orgnr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account(int id, String name, int orgnr, String city, String email, String password) {
        this.id = id;
        this.name = name;
        this.orgnr = orgnr;
        this.city = city;
        this.email = email;
        this.password = password;


    }

}

