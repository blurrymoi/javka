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
public interface AnimalManager {
 
    public void createAnimal(Animal animal) throws ServiceFailureException;
    public Animal getAnimalByID(Long ID) throws ServiceFailureException;
    public List<Animal> findAllAnimals() throws ServiceFailureException;
    public void updateAnimal(Animal animal) throws ServiceFailureException;
    public void deleteAnimal(Animal animal) throws ServiceFailureException;
    public void neuterAnimal(Animal animal) throws ServiceFailureException;
    
}
