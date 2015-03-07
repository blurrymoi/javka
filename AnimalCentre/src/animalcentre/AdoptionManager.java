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
public interface AdoptionManager {
   
    public void createAdoption(Adoption adoption) throws NotYetException;
    public Adoption getAdoptionByID(long ID) throws NotYetException;
    public List<Adoption> findAllAdoptions() throws NotYetException;
    public void updateAdoption(Adoption adoption) throws NotYetException;
    public void deleteAdoption(Adoption adoption) throws NotYetException;  
    public List<Adoption> findAllAdoptionsByCustomer(Customer customer) throws NotYetException;
    public List<Adoption> findAllAdoptionsOfAnimal(Animal animal) throws NotYetException;
    
}
