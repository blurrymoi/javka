/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animalcentre;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Barbora
 */
public class AnimalManagerImplTest {
    
    public AnimalManagerImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createAnimal method, of class AnimalManagerImpl.
     */
    @Test
    public void testCreateAnimal() {
        System.out.println("createAnimal");
        Animal animal = null;
        AnimalManagerImpl instance = new AnimalManagerImpl();
        instance.createAnimal(animal);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAnimalByID method, of class AnimalManagerImpl.
     */
    @Test
    public void testGetAnimalByID() {
        System.out.println("getAnimalByID");
        long ID = 0L;
        AnimalManagerImpl instance = new AnimalManagerImpl();
        Animal expResult = null;
        Animal result = instance.getAnimalByID(ID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAllAnimals method, of class AnimalManagerImpl.
     */
    @Test
    public void testFindAllAnimals() {
        System.out.println("findAllAnimals");
        AnimalManagerImpl instance = new AnimalManagerImpl();
        List<Animal> expResult = null;
        List<Animal> result = instance.findAllAnimals();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateAnimal method, of class AnimalManagerImpl.
     */
    @Test
    public void testUpdateAnimal() {
        System.out.println("updateAnimal");
        Animal animal = null;
        AnimalManagerImpl instance = new AnimalManagerImpl();
        instance.updateAnimal(animal);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteAnimal method, of class AnimalManagerImpl.
     */
    @Test
    public void testDeleteAnimal() {
        System.out.println("deleteAnimal");
        Animal animal = null;
        AnimalManagerImpl instance = new AnimalManagerImpl();
        instance.deleteAnimal(animal);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of neuterAnimal method, of class AnimalManagerImpl.
     */
    @Test
    public void testNeuterAnimal() {
        System.out.println("neuterAnimal");
        Animal animal = null;
        AnimalManagerImpl instance = new AnimalManagerImpl();
        instance.neuterAnimal(animal);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
