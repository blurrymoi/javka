/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animalcentre;
import java.util.*;
/**
 *
 * @author Barbora
 */
public class Adoption {

    private long adoptionID;
    private Date dateOfAdoption;
    private Date dateOfReturn;
    private Customer customer;
    private Animal animal;

    public long getAdoptionID() {
        return adoptionID;
    }

    public Date getDateOfAdoption() {
        return dateOfAdoption;
    }

    public Date getDateOfReturn() {
        return dateOfReturn;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAdoptionID(long adoptionID) {
        this.adoptionID = adoptionID;
    }

    public void setDateOfAdoption(Date dateOfAdoption) {
        this.dateOfAdoption = dateOfAdoption;
    }

    public void setDateOfReturn(Date dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
    
    
}
