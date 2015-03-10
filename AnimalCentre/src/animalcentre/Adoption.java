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

    private Long adoptionID;
    private Date dateOfAdoption;
    private Date dateOfReturn;
    private Customer customer;
    private Animal animal;

    public Long getAdoptionID() {
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

    public void setAdoptionID(Long adoptionID) {
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (int) (this.adoptionID ^ (this.adoptionID >>> 32));
        hash = 59 * hash + Objects.hashCode(this.dateOfAdoption);
        hash = 59 * hash + Objects.hashCode(this.customer);
        hash = 59 * hash + Objects.hashCode(this.animal);
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
        final Adoption other = (Adoption) obj;
        if (this.adoptionID != other.adoptionID) {
            return false;
        }
        if (!Objects.equals(this.dateOfAdoption, other.dateOfAdoption)) {
            return false;
        }
        if (!Objects.equals(this.dateOfReturn, other.dateOfReturn)) {
            return false;
        }
        if (!Objects.equals(this.customer, other.customer)) {
            return false;
        }
        if (!Objects.equals(this.animal, other.animal)) {
            return false;
        }
        return true;
    }
    
    
    
}
