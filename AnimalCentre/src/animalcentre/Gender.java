/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animalcentre;

/**
 *
 * @author Barbora
 */
public enum Gender 
{
    MALE("male"),
    FEMALE("female");
    
    private String gender;

    private Gender(String gender) {
        this.gender = gender;
    }
}