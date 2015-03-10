/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animalcentre;

import static animalcentre.Gender.*;
import java.sql.SQLException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Zuzana
 */
public class AnimalManagerImplTest {
    
    private AnimalManager manager;
    
    @Before
    public void setUp() throws SQLException{
        manager = new AnimalManagerImpl();
    }
    
    /**
     * Test of createAnimal method, of class AnimalManagerImpl.
     */
    @Test
    public void createAnimal() {
        System.out.println("creating an animal..");
        Animal animal = newAnimal("Arthur",2009,FEMALE,false);
        manager.createAnimal(animal);
        
        Long animID = animal.getAnimalID();
        assertNotNull(animID);
        
        Animal comp = manager.getAnimalByID(animID);
        assertEquals(animal, comp);
        assertNotSame(animal, comp);
        assertDeepEquals(animal, comp);     
        
    }
    
    /**
     * Test of createAnimal method with wrong attributes, of class AnimalManagerImpl.
     */
    @Test
    public void createAnimalWrong(){
        System.out.println("creating animals with wrong attributes..");
        
        try {
            manager.createAnimal(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        Animal animal = newAnimal("A",23,MALE,true);
        animal.setAnimalID(-100L);
        
        try {
            manager.createAnimal(animal);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        animal.setAnimalID(null);
        
        try {
            manager.createAnimal(animal);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        animal.setAnimalID(100L);
        animal.setName("");
        
        try {
            manager.createAnimal(animal);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        animal.setName(null);
        
        try {
            manager.createAnimal(animal);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
                
        animal.setName("A");
        animal.setYearOfBirth(-14);
        
        try {
            manager.createAnimal(animal);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        animal.setYearOfBirth(14);
        animal.setGender(null);
        
        // ?
        try {
            manager.createAnimal(animal);
            //OK
        } catch (IllegalArgumentException ex) {
            fail();
        }
        
    }
    
    /**
     * Test of getAnimalByID method, of class AnimalManagerImpl.
     */
    @Test
    public void getAnimalByID() {
        System.out.println("getting animal by ID");
        assertNull(manager.getAnimalByID(1L)); //no animals present
        
        try {
            manager.getAnimalByID(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        Animal animal = newAnimal("Gibberish",2000,MALE,true);
        manager.createAnimal(animal);
        Long ID = animal.getAnimalID();
            
        Animal expResult = manager.getAnimalByID(ID);
        assertEquals(animal, expResult);
        assertDeepEquals(animal, expResult);
    }
    
    /**
     * Test of findAllAnimals method, of class AnimalManagerImpl.
     */
    @Test
    public void testFindAllAnimals() {
        // nothing to see here
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateAnimal method, of class AnimalManagerImpl.
     */
    @Test
    public void updateAnimal() {
        System.out.println("updating animals..");
        Animal animal = newAnimal("Fido",1030,MALE,false);
        manager.createAnimal(animal);
        
        Animal dontAffectAnimal = newAnimal("Castiel",749,MALE,false);
        manager.createAnimal(dontAffectAnimal);
        
        assertNotNull(animal.getAnimalID()); //?
        
        Long animID = animal.getAnimalID();
        Animal animal2 = manager.getAnimalByID(animID);
        
        animal2.setName("NotFido");
        animal2.setGender(FEMALE);
        animal2.setNeutered(true);
        animal2.setYearOfBirth(1032);
        
        manager.updateAnimal(animal2);
                
        assertEquals("NotFido", animal.getName());
        assertEquals(FEMALE, animal.getGender());
        assertEquals(true, animal.isNeutered());
        assertEquals(1032, animal.getYearOfBirth());
        
        animal2.setGender(null);
        manager.updateAnimal(animal2);
        assertNull(animal.getGender());
        
        assertDeepEquals(dontAffectAnimal, manager.getAnimalByID(dontAffectAnimal.getAnimalID()));
               
    }    
    
    /**
     * Test of updateAnimal method with wrong attributes, of class AnimalManagerImpl.
     */
    @Test
    public void updateAnimalWrong() {
        System.out.println("updating animals with wrong attributes..");
        Animal animal = newAnimal("Fido",1030,MALE,false);
        manager.createAnimal(animal);
        
        Animal dontAffectAnimal = newAnimal("Castiel",749,MALE,false);
        manager.createAnimal(dontAffectAnimal);
        
        Long animID = animal.getAnimalID();
        Animal animal2 = manager.getAnimalByID(animID);
        
        try {
            manager.updateAnimal(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        animal2.setAnimalID(-100L);
        
        try {
            manager.updateAnimal(animal);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        animal2.setAnimalID(animal.getAnimalID());
        animal2.setName("");
        
        try {
            manager.updateAnimal(animal2);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        animal2.setName(animal.getName());
        animal2.setYearOfBirth(-14);
        
        try {
            manager.updateAnimal(animal2);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        animal2.setYearOfBirth(animal.getYearOfBirth());
               
        assertDeepEquals(animal,animal2);
        assertDeepEquals(dontAffectAnimal, manager.getAnimalByID(dontAffectAnimal.getAnimalID()));       
        
    }
    
    
    
    /**
     * Test of deleteAnimal method, of class AnimalManagerImpl.
     */
    @Test
    public void testDeleteAnimal() {
        // nothing to see here
        fail("The test case is a prototype.");
    }

    /**
     * Test of neuterAnimal method, of class AnimalManagerImpl.
     */
    @Test
    public void testNeuterAnimal() {
        // nothing to see here
        fail("The test case is a prototype.");
    }
    
    
    private static Animal newAnimal(String name, int year, Gender gender, boolean neutered) {
        Animal animal = new Animal();
        animal.setName(name);
        animal.setYearOfBirth(year);
        animal.setGender(gender);
        animal.setNeutered(neutered);
        return animal;
    }
    
    /*
    private void assertDeepEquals(List<Animal> expectedList, List<Animal> actualList) {
        for (int i = 0; i < expectedList.size(); i++) {
            Animal expected = expectedList.get(i);
            Animal actual = actualList.get(i);
            assertDeepEquals(expected, actual);
        }
    } */

    private void assertDeepEquals(Animal expected, Animal actual) {
        assertEquals(expected.getAnimalID(), actual.getAnimalID());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getYearOfBirth(), actual.getYearOfBirth());
        assertEquals(expected.getGender(), actual.getGender());
        assertEquals(expected.isNeutered(), actual.isNeutered());
    }
}
