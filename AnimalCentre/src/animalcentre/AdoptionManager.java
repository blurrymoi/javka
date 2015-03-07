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
   
    public void createAdoption(Adoption adoption) throws NoServiceException;
    public Adoption getAdoptionByID(long ID) throws NoServiceException;
    public List<Adoption> findAllAdoptions() throws NoServiceException;
    public void updateAdoption(Adoption adoption) throws NoServiceException;
    public void deleteAdoption(Adoption adoption) throws NoServiceException;  
    public List<Adoption> findAllAdoptionsByCustomer(Customer customer) throws NoServiceException;
    public List<Adoption> findAllAdoptionsOfAnimal(Animal animal) throws NoServiceException;
    
}
