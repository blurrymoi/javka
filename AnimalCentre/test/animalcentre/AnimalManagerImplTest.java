package animalcentre;

import static animalcentre.Gender.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.sql.DataSource;
import java.sql.*;

/**
 *
 * @author blurry
 */
public class AnimalManagerImplTest {

    private AnimalManager manager;
    private DataSource ds;

    @Before
    public void setUp() throws SQLException, IOException {
               
        ds = DBstuff.dataSource2();
        
        try (Connection conn = ds.getConnection()){
            try (PreparedStatement pr = conn.prepareStatement(DBstuff.FINAL_ANIMAL)){                
                    pr.executeUpdate();
        } catch (SQLException e) { 
            if (!e.getSQLState().equals("42Y55")) throw e;
            }
        
        //try (Connection conn = ds.getConnection()) {            
            try (PreparedStatement pr = conn.prepareStatement(DBstuff.CREATE_TABLE_ANIMAL)){
                pr.executeUpdate();
            }
        } 
        manager = new AnimalManagerImpl(ds);
    } 
    
    @After
    public void tearDown() throws SQLException, IOException {
        ds = DBstuff.dataSource2();
        try (Connection conn = ds.getConnection()){
            try (PreparedStatement pr = conn.prepareStatement(DBstuff.FINAL_ANIMAL)){                
                    pr.executeUpdate();
        } catch (SQLException e) { 
            if (!e.getSQLState().equals("42Y55")) throw e;
            }
    } 
}
    
    
    /**
     * Test of createAnimal method, of class AnimalManagerImpl.
     */
    @Test
    public void createAnimal() {
        System.out.println("creating an animal..");
        Animal animal = newAnimal("Arthur", 2009, FEMALE, false);
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
        
        Animal animal = newAnimal("A", 23, MALE, true);
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
            //OK
        } catch (IllegalArgumentException ex) {
           fail();
        }
        
        animal = newAnimal("A", 23, MALE, true);
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
                
        animal = newAnimal("A", 23, MALE, true);
        animal.setYearOfBirth(-14);
        
        try {
            manager.createAnimal(animal);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        animal = newAnimal("A", 23, MALE, true);
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
        
        Animal animal = newAnimal("Gibberish", 2000, MALE, true);
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
        System.out.println("looking for animals (list)..");
        
        assertTrue((manager.findAllAnimals()).isEmpty());
              
        Animal animal = newAnimal("Greg", 2012, MALE, false);
        manager.createAnimal(animal);
        
        List<Animal> listExpected = new ArrayList<Animal>();
        listExpected.add(animal);
        
        Animal animal2 = newAnimal("Tae", 2010, FEMALE, true);
        manager.createAnimal(animal2);
        listExpected.add(animal2);
        
        Animal animal3 = newAnimal("Lexi", 2000, null, true);
        manager.createAnimal(animal3);
        listExpected.add(animal3);
        
        List<Animal> listReturned = manager.findAllAnimals();
        
        Collections.sort(listReturned,idComparator);
        Collections.sort(listExpected,idComparator);
        
        assertEquals(listExpected, listReturned);
        assertDeepEquals(listExpected,listReturned);
        
    }

    /**
     * Test of updateAnimal method, of class AnimalManagerImpl.
     */
    @Test
    public void updateAnimal() {
        System.out.println("updating animals..");
        Animal animal = newAnimal("Fido", 1030, MALE, false);
        manager.createAnimal(animal);
        
        Animal dontAffectAnimal = newAnimal("Castiel", 749, MALE, false);
        manager.createAnimal(dontAffectAnimal);
        
        assertNotNull(animal.getAnimalID());
        
        Long animID = animal.getAnimalID();
        Animal animal2 = manager.getAnimalByID(animID);
        
        animal2.setName("NotFido");
        animal2.setGender(FEMALE);
        animal2.setNeutered(true);
        animal2.setYearOfBirth(1032);
        
        manager.updateAnimal(animal2);
        Animal animal3 = manager.getAnimalByID(animID);
        
        assertEquals("NotFido", animal3.getName());
        assertEquals(FEMALE, animal3.getGender());
        assertEquals(true, animal3.isNeutered());
        assertEquals(1032, animal3.getYearOfBirth());
        
        animal2.setGender(null);
        manager.updateAnimal(animal2);
        animal3 = manager.getAnimalByID(animID);
        assertNull(animal3.getGender());
        
        assertDeepEquals(dontAffectAnimal, manager.getAnimalByID(dontAffectAnimal.getAnimalID()));
               
    }    
    
    /**
     * Test of updateAnimal method with wrong attributes, of class AnimalManagerImpl.
     */
    @Test
    public void updateAnimalWrong() {
        System.out.println("updating animals with wrong attributes..");
        Animal animal = newAnimal("Fido", 1030, MALE, false);
        manager.createAnimal(animal);
        
        Animal dontAffectAnimal = newAnimal("Castiel", 749, MALE, false);
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
            manager.updateAnimal(animal2);
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
        
        Animal animal3 = newAnimal("What", 103, MALE, false);
        animal3.setAnimalID(8L); //not in table?
        try {
            manager.updateAnimal(animal3);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        //System.out.println(manager.findAllAnimals()); //seems to work^^
               
        assertDeepEquals(animal,animal2);
        assertDeepEquals(dontAffectAnimal, manager.getAnimalByID(dontAffectAnimal.getAnimalID()));       
        
    }
    
    
    /**
     * Test of deleteAnimal method, of class AnimalManagerImpl.
     */
    @Test
    public void testDeleteAnimal() {
        System.out.println("deleting animals..");
        
        Animal dontAffectAnimal = newAnimal("Dess", 1999, FEMALE, false);
        manager.createAnimal(dontAffectAnimal);
        
        Animal animal = newAnimal("Lorda", 1998, FEMALE, true);
        manager.createAnimal(animal);
        
        manager.deleteAnimal(manager.getAnimalByID(animal.getAnimalID()));
        assertNull(manager.getAnimalByID(animal.getAnimalID()));
        
        assertNotNull(manager.getAnimalByID(dontAffectAnimal.getAnimalID()));
        assertDeepEquals(dontAffectAnimal, manager.getAnimalByID(dontAffectAnimal.getAnimalID()));
        
    }

    /**
     * Test of deleteAnimal method, of class AnimalManagerImpl.
     */
    @Test
    public void testDeleteAnimalWrong() {
        System.out.println("deleting animals with wrong attributes..");
        
        try {
            manager.deleteAnimal(manager.getAnimalByID(1L)); //no animals present
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        Animal dontAffectAnimal = newAnimal("Dess", 1999, FEMALE, false);
        manager.createAnimal(dontAffectAnimal);
        
        try {
            manager.deleteAnimal(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        Animal animal = newAnimal("Lexi", 2000, null, true);
        
        try {
            animal.setAnimalID(null);
            manager.deleteAnimal(animal);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        assertNotNull(manager.getAnimalByID(dontAffectAnimal.getAnimalID()));
        assertDeepEquals(dontAffectAnimal, manager.getAnimalByID(dontAffectAnimal.getAnimalID()));
        
    }
    

    /**
     * Test of neuterAnimal method, of class AnimalManagerImpl.
     */
    @Test
    public void testNeuterAnimal() {
        System.out.println("neutering animals..");
        
        try {
            manager.neuterAnimal(manager.getAnimalByID(1L));
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        Animal animal = newAnimal("Bad", 2004, MALE, false);
        manager.createAnimal(animal);
        
        Animal dontAffectAnimal = newAnimal("Castiel", 749, MALE, false);
        manager.createAnimal(dontAffectAnimal);
        
        manager.neuterAnimal(animal);
        Animal animal2 = manager.getAnimalByID(animal.getAnimalID());
        
        assertEquals(true, animal2.isNeutered());
        
        animal = newAnimal("Bad", 2004, MALE, true);
        manager.createAnimal(animal);
        manager.neuterAnimal(animal);
        
        animal2 = manager.getAnimalByID(animal.getAnimalID());
        assertEquals(true, animal2.isNeutered());
        
        assertDeepEquals(dontAffectAnimal, manager.getAnimalByID(dontAffectAnimal.getAnimalID()));
        
    }
    
    
    private static Animal newAnimal(String name, int year, Gender gender, boolean neutered) {
        Animal animal = new Animal();
        animal.setName(name);
        animal.setYearOfBirth(year);
        animal.setGender(gender);
        animal.setNeutered(neutered);
        return animal;
    }
    
    
    private void assertDeepEquals(List<Animal> expectedList, List<Animal> actualList) {
        for (int i = 0; i < expectedList.size(); i++) {
            Animal expected = expectedList.get(i);
            Animal actual = actualList.get(i);
            assertDeepEquals(expected, actual);
        }
    }

    private void assertDeepEquals(Animal expected, Animal actual) {
        assertEquals(expected.getAnimalID(), actual.getAnimalID());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getYearOfBirth(), actual.getYearOfBirth());
        assertEquals(expected.getGender(), actual.getGender());
        assertEquals(expected.isNeutered(), actual.isNeutered());
    }
    
    private Comparator<Animal> idComparator = new Comparator<Animal>() { 
        @Override
        public int compare(Animal a1, Animal a2) {
            return Long.valueOf(a1.getAnimalID()).compareTo(Long.valueOf(a2.getAnimalID()));
        }
    };
}

