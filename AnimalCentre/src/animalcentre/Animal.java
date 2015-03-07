/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animalcentre;
//import java.util.*;
/**
 *
 * @author Barbora
 */

public class Animal {
    
    private long animalID; 
    private String name;
    private int yearOfBirth;
    private Gender gender;
    private boolean neutered;

    public long getAnimalID() {
        return animalID;
    }

    public void setAnimalID(long animalID) {
        this.animalID = animalID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isNeutered() {
        return neutered;
    }

    public void setNeutered(boolean neutered) {
        this.neutered = neutered;
    }
    
    
}
