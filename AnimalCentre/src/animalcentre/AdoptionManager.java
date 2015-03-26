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
   
    public void createAdoption(Adoption adoption) throws ServiceFailureException;
    public Adoption getAdoptionByID(Long ID) throws ServiceFailureException;
    public List<Adoption> findAllAdoptions() throws ServiceFailureException;
    public void updateAdoption(Adoption adoption) throws ServiceFailureException;
    public void deleteAdoption(Adoption adoption) throws ServiceFailureException;  
    public List<Adoption> findAllAdoptionsByCustomer(Customer customer) throws ServiceFailureException;
    public List<Adoption> findAllAdoptionsOfAnimal(Animal animal) throws ServiceFailureException;
    
}
