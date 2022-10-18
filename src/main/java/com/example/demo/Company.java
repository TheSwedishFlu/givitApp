package com.example.demo;

public class Company {

    String name;
    int numberOfmembers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfmembers() {
        return numberOfmembers;
    }

    public void setNumberOfmembers(int numberOfmembers) {
        this.numberOfmembers = numberOfmembers;
    }

    public Company(String name, int numberOfmembers) {
        this.name = name;
        this.numberOfmembers = numberOfmembers;
    }

    public String description() {

        return name + " is a company with " + numberOfmembers + " members.";


    }
}
