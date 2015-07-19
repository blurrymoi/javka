/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animalcentre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.apache.commons.lang.time.DateUtils;
//import org.apache.derby.client.am.DateTime;

/**
 *
 * @author Barbora, blurry
 */
public class AdoptionManagerImplTest {
    
    private AdoptionManager manager;
    private DataSource ds;
    private CustomerManager customerManager;
    private AnimalManager animalManager;

    @Before
    public void setUp() throws SQLException, IOException {

        ds = DBstuff.dataSource2();
        /*
        try (Connection conn = ds.getConnection()){
            try (PreparedStatement pr = conn.prepareStatement(DBstuff.FINAL_ADOPTION)){                
                    pr.executeUpdate();
        } catch (SQLException e) { 
            if (!e.getSQLState().equals("42Y55")) throw e;
            }
            
            try (PreparedStatement pr = conn.prepareStatement(DBstuff.FINAL_ANIMAL)){                
                    pr.executeUpdate();
        } catch (SQLException e) { 
            if (!e.getSQLState().equals("42Y55")) throw e;
            }
            
            try (PreparedStatement pr = conn.prepareStatement(DBstuff.FINAL_CUSTOMER)){                
                    pr.executeUpdate();
        } catch (SQLException e) { 
            if (!e.getSQLState().equals("42Y55")) throw e;
            }
            */
            
        
        try (Connection conn = ds.getConnection()) {            
                      
            try (PreparedStatement pr = conn.prepareStatement(DBstuff.CREATE_TABLE_ANIMAL)){
                pr.executeUpdate();
            }
            
            try (PreparedStatement pr = conn.prepareStatement(DBstuff.CREATE_TABLE_CUSTOMER)){
                pr.executeUpdate();
            }
            
             try (PreparedStatement pr = conn.prepareStatement(DBstuff.CREATE_TABLE_ADOPTION)){
                pr.executeUpdate();
            }
       // } 
        manager = new AdoptionManagerImpl(ds);
        customerManager = new CustomerManagerImpl(ds);
        animalManager = new AnimalManagerImpl(ds);
        }
    }
    /*
    @Before
    public void setUp() throws SQLException, IOException {
               
        ds = DBstuff.dataSource2();
        
        try (Connection conn = ds.getConnection()) {            
            try (PreparedStatement pr = conn.prepareStatement(DBstuff.CREATE_TABLE_ADOPTION)){
                pr.executeUpdate();
            }
        } 
        manager = new AdoptionManagerImpl(ds);
    } */
    
    @After
    public void tearDown() throws SQLException, IOException {
        ds = DBstuff.dataSource2();
        try (Connection conn = ds.getConnection()){
            try (PreparedStatement pr = conn.prepareStatement(DBstuff.FINAL_ADOPTION)){                
                    pr.executeUpdate();
        } catch (SQLException e) { 
            if (!e.getSQLState().equals("42Y55")) throw e;
            }
            
             try (PreparedStatement pr = conn.prepareStatement(DBstuff.FINAL_ANIMAL)){                
                    pr.executeUpdate();
        } catch (SQLException e) { 
            if (!e.getSQLState().equals("42Y55")) throw e;
            }
             
              try (PreparedStatement pr = conn.prepareStatement(DBstuff.FINAL_CUSTOMER)){                
                    pr.executeUpdate();
        } catch (SQLException e) { 
            if (!e.getSQLState().equals("42Y55")) throw e;
            }
    }
}
    
    /**
     * Test of createAdoption method, of class AdoptionManagerImpl.
     * @throws java.text.ParseException
     */
    @Test
    public void testCreateAdoption() throws ParseException {
        Customer customer = newCustomer("X Y", "adresa", "09xx xxx xxx");
        customerManager.createCustomer(customer);
        Animal animal = newAnimal("Lassie", 1994, FEMALE, true);
        animalManager.createAnimal(animal);
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2005");
        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2007");
        
        Adoption adoption = newAdoption(date1, date2, customer, animal);
        manager.createAdoption(adoption);
        Long adoptionID = adoption.getAdoptionID();
        Adoption result = manager.getAdoptionByID(adoptionID); 
        System.out.println(adoption);
        System.out.println(result);
        assertEquals(adoption, result);
        assertNotSame(adoption, result);
        assertDeepEquals(adoption, result);
        
    }

    public void testCreateAdoptionWithWrongAttributes() throws ParseException {

	Customer customer = newCustomer("X Y", "adresa", "09xx xxx xxx");
        Animal animal = newAnimal("Lassie", 1994, FEMALE, true);
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2005");
        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2007");


        try {
            manager.createAdoption(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        Adoption adoption = newAdoption(date1,date2,customer, animal );
        adoption.setAdoptionID(null); 
        try {
            manager.createAdoption(adoption);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        adoption = newAdoption(date1,date2,customer, animal );
        try {
            manager.createAdoption(adoption);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        adoption = newAdoption(null,date2,customer, animal );
        try {
            manager.createAdoption(adoption);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        adoption = newAdoption(date1,null,customer, animal );
        try {
            manager.createAdoption(adoption);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        adoption = newAdoption(date1,date2,null, animal );
        try {
            manager.createAdoption(adoption);
            //OK
        } catch (IllegalArgumentException ex) {
            //fail();
        }

        adoption = newAdoption(date1,date2,customer,null);
        try {
            manager.createAdoption(adoption);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2005");
            date2 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2003");
            
            adoption = newAdoption(date1, date2, customer,animal);
            manager.createAdoption(adoption);
            fail();
        }
        catch (IllegalArgumentException ex) {
            //OK
        }
        
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse("05/16/2005"); //?
            date2 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2007");
            
            adoption = newAdoption(date1, date2, customer,animal);
            manager.createAdoption(adoption);
            fail();
        }
        catch (IllegalArgumentException ex) {
            //OK
        }

} 
    
   
    /**
     * Test of getAdoptionByID method, of class AdoptionManagerImpl.
     */
    @Test
    public void testGetAdoptionByID() throws ParseException {
        Customer customer = newCustomer("X Y", "adresa", "09xx xxx xxx");
        Animal animal = newAnimal("Lassie", 1994, FEMALE, true);
        customerManager.createCustomer(customer);
        animalManager.createAnimal(animal);
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2005");
        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2007");
        Adoption adoption = newAdoption(date1 ,date2,customer, animal);
        
        manager.createAdoption(adoption);
        Long adoptionID = adoption.getAdoptionID();
        Adoption result = manager.getAdoptionByID(adoptionID);
        assertEquals(adoption, result);
        assertDeepEquals(adoption, result);
    } //DONE

    /**
     * Test of findAllAdoptions method, of class AdoptionManagerImpl.
     */
    @Test
    public void testFindAllAdoptions() throws ParseException { //DONE
        assertTrue(manager.findAllAdoptions().isEmpty());

	Customer customer = newCustomer("X Y", "adresa", "09xx xxx xxx");
        customerManager.createCustomer(customer);
        Animal animal = newAnimal("Lassie", 1994, FEMALE, true);
        animalManager.createAnimal(animal);
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2005");
        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2007");
        Adoption adoption1 = newAdoption(date1,date2,customer, animal );
        Adoption adoption2 = newAdoption(date1,date2,customer, animal );
        Adoption adoption3 = newAdoption(date1,date2,customer, animal );

        manager.createAdoption(adoption1);
        manager.createAdoption(adoption2); 
        manager.createAdoption(adoption3); 

        
        List<Adoption> adoptions = new ArrayList<>(); 
        adoptions.add(adoption1);
        adoptions.add(adoption2);
        adoptions.add(adoption3);
        List<Adoption> copy = manager.findAllAdoptions();        
        Collections.sort(adoptions,idComparator);
        Collections.sort(copy,idComparator);
        assertEquals(adoptions, copy);
        assertDeepEquals(adoptions, copy);   
    } //DONE

    /**
     * Test of updateAdoption method, of class AdoptionManagerImpl.
     */
    @Test
    public void testUpdateAdoption() throws ParseException {
        Customer customer = newCustomer("X Y", "adresa", "09xx xxx xxx");
        Animal animal = newAnimal("Lassie", 1994, FEMALE, true);
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2005");
        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2007");
        customerManager.createCustomer(customer);
        animalManager.createAnimal(animal);
        
       	Adoption adoption = newAdoption(date1, date2, customer, animal);
       	Adoption adoption2 = newAdoption(date1, date2, customer, animal);

        manager.createAdoption(adoption);
        manager.createAdoption(adoption2); 
        
        Long cID = adoption.getAdoptionID();
        
        
        adoption = manager.getAdoptionByID(cID); //meni date1
        Date date3 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2000");
        adoption.setDateOfAdoption(date3);
	manager.updateAdoption(adoption);        

        assertEquals(date3, adoption.getDateOfAdoption());
        assertEquals(date2, adoption.getDateOfReturn());
        assertEquals(customer,adoption.getCustomer());
        assertEquals(animal,adoption.getAnimal());
        
	
        adoption = manager.getAdoptionByID(cID); //meni date2
        Date date4 = new Date();
        adoption.setDateOfReturn(date4);
        manager.updateAdoption(adoption);        
        
        assertEquals(date3, adoption.getDateOfAdoption());
        assertEquals(date4, adoption.getDateOfReturn());
        assertEquals(customer,adoption.getCustomer());
        assertEquals(animal,adoption.getAnimal());
        
        adoption = manager.getAdoptionByID(cID); //meni customer
        Customer cus = newCustomer("X Y", "adresa", "09xx xxx xxx");
        customerManager.createCustomer(cus);
        
	adoption.setCustomer(cus);
        manager.updateAdoption(adoption);        
       
        assertEquals(true, DateUtils.isSameDay(date4, adoption.getDateOfReturn()));
        assertEquals(true, DateUtils.isSameDay(date3, adoption.getDateOfAdoption()));
        
        assertEquals(cus,adoption.getCustomer());
        assertEquals(animal,adoption.getAnimal());

        adoption = manager.getAdoptionByID(cID); //meni animal
        Animal ani = newAnimal("Lassie", 1994, FEMALE, true);
        animalManager.createAnimal(ani);
	adoption.setAnimal(ani);
        manager.updateAdoption(adoption);        
        
        assertEquals(true, DateUtils.isSameDay(date4, adoption.getDateOfReturn()));
        assertEquals(true, DateUtils.isSameDay(date3, adoption.getDateOfAdoption()));
        assertEquals(cus,adoption.getCustomer());
        assertEquals(ani,adoption.getAnimal());
        
        assertDeepEquals(adoption2, manager.getAdoptionByID(adoption2.getAdoptionID()));
    }

    @Test
    public void testUpdateAdoptionWithWrongAttributes() throws ParseException { //DONE
        
	Customer customer = newCustomer("X Y", "adresa", "09xx xxx xxx");
        Animal animal = newAnimal("Lassie", 1994, FEMALE, true);
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2005");
        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2007");

        customerManager.createCustomer(customer);
        animalManager.createAnimal(animal);
        Adoption adoption = newAdoption(date1,date2,customer, animal );

        manager.createAdoption(adoption);
        Long adoptionID = adoption.getAdoptionID();
        
        try {
            manager.updateAdoption(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        try {
            adoption = manager.getAdoptionByID(adoptionID);
            adoption.setAdoptionID(null);
            manager.updateAdoption(adoption);        
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            adoption = manager.getAdoptionByID(adoptionID);
            adoption.setAdoptionID(-1L);
            manager.updateAdoption(adoption);        
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            adoption = manager.getAdoptionByID(adoptionID);
            adoption.setDateOfAdoption(null);
            manager.updateAdoption(adoption);        
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            adoption = manager.getAdoptionByID(adoptionID);
            adoption.setDateOfReturn(null);
            manager.updateAdoption(adoption);        
        } catch (IllegalArgumentException ex) {
            fail();
        }

        try {
            System.out.println(adoption);
            adoption = manager.getAdoptionByID(adoptionID);
            adoption.setCustomer(null);
            manager.updateAdoption(adoption);        
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }


        try {
            adoption = manager.getAdoptionByID(adoptionID);
            adoption.setAnimal(null);
            manager.updateAdoption(adoption);        
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
    } //DONE
    /**
     * Test of deleteAdoption method, of class AdoptionManagerImpl.
     */
    @Test
    public void testDeleteAdoption() throws ParseException {
        Customer customer = newCustomer("X Y", "adresa", "09xx xxx xxx");
        customerManager.createCustomer(customer);
        Animal animal = newAnimal("Lassie", 1994, FEMALE, true);
        animalManager.createAnimal(animal);
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2005");
        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2007");
       	Adoption adoption = newAdoption(date1,date2,customer, animal );
        manager.createAdoption(adoption);
        Long ID = adoption.getAdoptionID();
        manager.deleteAdoption(adoption);
        Adoption pom = manager.getAdoptionByID(ID);
        assertNull(pom);
    } //DONE
    
    @Test
    public void testDeleteAdoptionWithWrongAttributes() throws ParseException {
        
        Customer customer = newCustomer("X Y", "adresa", "09xx xxx xxx");
        customerManager.createCustomer(customer);
        Animal animal = newAnimal("Lassie", 1994, FEMALE, true);
        animalManager.createAnimal(animal);
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2005");
        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2007");

       	Adoption adoption = newAdoption(date1,date2,customer, animal );
        manager.createAdoption(adoption);

        try {
            manager.deleteAdoption(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            adoption.setAdoptionID(null);
            manager.deleteAdoption(adoption);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            adoption.setAdoptionID(-1L);
            manager.deleteAdoption(adoption);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            adoption.setDateOfAdoption(null);
            manager.deleteAdoption(adoption);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            adoption.setDateOfReturn(null);
            manager.deleteAdoption(adoption);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            adoption.setCustomer(null);
            manager.deleteAdoption(adoption);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            adoption.setAnimal(null);
            manager.deleteAdoption(adoption);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
    } //DONE

    /**
     * Test of findAllAdoptionsByCustomer method, of class AdoptionManagerImpl.
     * @throws java.text.ParseException
     */
    @Test
    public void testFindAllAdoptionsByCustomer() throws ParseException { 
        
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2005");
        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2007");
        
        Customer customer = newCustomer("Peter", "Brno", "+09234923");
        customerManager.createCustomer(customer);
        assertTrue(manager.findAllAdoptionsByCustomer(customer).isEmpty());
        
        Animal animal = newAnimal("Lassie", 1994, FEMALE, true); 
        animalManager.createAnimal(animal);
        Adoption adoption = newAdoption(date1, date2, customer, animal);  
        manager.createAdoption(adoption);
        
        List<Adoption> adExpected = new ArrayList<>();
        adExpected.add(adoption);
        
        date1 = new SimpleDateFormat("dd/MM/yyyy").parse("02/02/2003");
        date2 = null;
        
        Animal animal2 = newAnimal("Norton", 1994, null, true);  
        animalManager.createAnimal(animal2);
        Adoption adoption2 = newAdoption(date1, date2, customer, animal2);
        manager.createAdoption(adoption2);
        adExpected.add(adoption2);
        
        try {
            manager.findAllAdoptionsByCustomer(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        List<Adoption> adReturned = manager.findAllAdoptionsByCustomer(customer);
        
        Collections.sort(adReturned,idComparator);
        Collections.sort(adExpected,idComparator);
        
        assertEquals(adExpected,adReturned);
        assertDeepEquals(adExpected,adReturned);
        
    
    }

    /**
     * Test of findAllAdoptionsOfAnimal method, of class AdoptionManagerImpl.
     * @throws java.text.ParseException
     */
    @Test
    public void testFindAllAdoptionsOfAnimal() throws ParseException {
        
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2005");
        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2007");
        
        Customer customer = newCustomer("Peter", "Brno", "+09234923");
        Animal animal = newAnimal("Lassie", 1994, FEMALE, true);
        customerManager.createCustomer(customer);
        animalManager.createAnimal(animal);
                
        assertTrue((manager.findAllAdoptionsOfAnimal(animal)).isEmpty());
        
        Adoption adoption = newAdoption(date1, date2, customer, animal);  
        manager.createAdoption(adoption);
        
        List<Adoption> adExpected = new ArrayList<>();
        adExpected.add(adoption);
        
        date1 = new SimpleDateFormat("dd/MM/yyyy").parse("02/02/2008");
        date2 = null;
        
        Adoption adoption2 = newAdoption(date1, date2, customer, animal); 
        adExpected.add(adoption2);
        manager.createAdoption(adoption2);
        
        try {
            manager.findAllAdoptionsOfAnimal(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        List<Adoption> adReturned = manager.findAllAdoptionsOfAnimal(animal);
        
        Collections.sort(adReturned,idComparator);
        Collections.sort(adExpected,idComparator);
        
        assertEquals(adExpected,adReturned);
        assertDeepEquals(adExpected,adReturned);
        
    }
    
    
    /***************************POMOCNE METODY***********************************/
    private static Adoption newAdoption(Date dateOfAdoption, Date dateOfReturn, Customer customer, Animal animal) { //pseudokostruktor, pretoze normalne konstruktory su mainstream
        Adoption adoption = new Adoption();
        adoption.setDateOfAdoption(dateOfAdoption);
        adoption.setDateOfReturn(dateOfReturn);
        adoption.setCustomer(customer);
        adoption.setAnimal(animal);
        return adoption; //done
    }
    
    private static Animal newAnimal(String name, int year, Gender gender, boolean neutered) {

        Animal animal = new Animal();
        animal.setName(name);
        animal.setYearOfBirth(year);
        animal.setGender(gender);
        animal.setNeutered(neutered);
        return animal;
    }
    
        private static Customer newCustomer(String name, String address, String phoneNumber) { //pseudokostruktor, pretoze normalne konstruktory su mainstream
        Customer customer = new Customer();
        customer.setName(name);
        customer.setAddress(address);
        customer.setPhoneNumber(phoneNumber);
        return customer;
    }

    private void assertDeepEquals(Adoption expected, Adoption actual) {
        assertEquals(expected.getAdoptionID(), actual.getAdoptionID());
        assertEquals(expected.getDateOfAdoption(), actual.getDateOfAdoption());
        assertEquals(expected.getDateOfReturn(), actual.getDateOfReturn());
        assertEquals(expected.getCustomer(), actual.getCustomer());
        assertEquals(expected.getAnimal(), actual.getAnimal()); //done
    }    
    
    private void assertDeepEquals(List<Adoption> expectedList, List<Adoption> actualList) { //equals listov
        for (int i = 0; i < expectedList.size(); i++) {
            Adoption expected = expectedList.get(i);
            Adoption actual = actualList.get(i);
            assertDeepEquals(expected, actual); //done
        }
    }
    
  
    private static Comparator<Adoption> idComparator = new Comparator<Adoption>() { 
        @Override
        public int compare(Adoption o1, Adoption o2) {
            return Long.valueOf(o1.getAdoptionID()).compareTo(Long.valueOf(o2.getAdoptionID())); //done
        }
    };
}