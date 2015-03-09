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
 
    public void createAnimal(Animal animal) throws NotYetException;
    public Animal getAnimalByID(long ID) throws NotYetException;
    public List<Animal> findAllAnimals() throws NotYetException;
    public void updateAnimal(Animal animal) throws NotYetException;
    public void deleteAnimal(Animal animal) throws NotYetException;
    public void neuterAnimal(Animal animal) throws NotYetException;
    
}
