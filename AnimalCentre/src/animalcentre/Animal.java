/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animalcentre;
//import java.util.*;

import java.util.Objects;

/**
 *
 * @author Barbora
 */

public class Animal {
    
    private Long animalID; 
    private String name;
    private int yearOfBirth;
    private Gender gender;
    private boolean neutered;

    public long getAnimalID() {
        return animalID;
    }

    public void setAnimalID(Long animalID) {
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.animalID ^ (this.animalID >>> 32));
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + this.yearOfBirth;
        hash = 97 * hash + Objects.hashCode(this.gender);
        hash = 97 * hash + (this.neutered ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Animal other = (Animal) obj;
        if (this.animalID != other.animalID) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.yearOfBirth != other.yearOfBirth) {
            return false;
        }
        if (this.gender != other.gender) {
            return false;
        }
        if (this.neutered != other.neutered) {
            return false;
        }
        return true;
    }
    
    
}
