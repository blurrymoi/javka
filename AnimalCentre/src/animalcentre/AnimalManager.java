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
 
    public void createAnimal(Animal animal) throws NoServiceException;
    public Animal getAnimalByID(long ID) throws NoServiceException;
    public List<Animal> findAllAnimals() throws NoServiceException;
    public void updateAnimal(Animal animal) throws NoServiceException;
    public void deleteAnimal(Animal animal) throws NoServiceException;
    public void neuterAnimal(Animal animal) throws NoServiceException;
    
}
