/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animalcentre;

import static animalcentre.Gender.*;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Comparator;
import java.util.Date;
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
public class AdoptionManagerImplTest {
    
    private AdoptionManagerImpl manager;
    
    public AdoptionManagerImplTest() {
    }
    
    
    @Before
    public void setUp() {
        
        manager = new AdoptionManagerImpl();
    }
    
    /**
     * Test of createAdoption method, of class AdoptionManagerImpl.
     */
    @Test
    public void testCreateAdoption() throws ParseException {
        Customer customer = newCustomer(123546L, "X Y", "adresa", "09xx xxx xxx");
        Animal animal = newAnimal(34L,"Lassie", 1994, FEMALE, true);
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2005");
        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2007");
        
        Adoption adoption = newAdoption(date1,date2,customer, animal);
        manager.createAdoption(adoption);
        Long adoptionID = adoption.getAdoptionID();
        Adoption result = manager.getAdoptionByID(adoptionID); 
        assertEquals(adoption, result);
        assertNotSame(adoption, result); //--wtf is this
        assertDeepEquals(adoption, result);
        
    } //DONE

    public void testCreateAdoptionWithWrongAttributes() throws ParseException {

	Customer customer = newCustomer(123546L, "X Y", "adresa", "09xx xxx xxx");
        Animal animal = newAnimal(34L,"Lassie", 1994, FEMALE, true);
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2005");
        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2007");


        try {
            manager.createAdoption(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        Adoption adoption = newAdoption(date1,date2,customer, animal );
        adoption.setAdoptionID(null); //TOTO?
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

} //DONE
    
   
    /**
     * Test of getAdoptionByID method, of class AdoptionManagerImpl.
     */
    @Test
    public void testGetAdoptionByID() throws ParseException {
        Customer customer = newCustomer(123546L, "X Y", "adresa", "09xx xxx xxx");
        Animal animal = newAnimal(34L,"Lassie", 1994, FEMALE, true);
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

	Customer customer = newCustomer(123546L, "X Y", "adresa", "09xx xxx xxx");
        Animal animal = newAnimal(34L,"Lassie", 1994, FEMALE, true);
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2005");
        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2007");
        Adoption adoption1 = newAdoption(date1,date2,customer, animal );
        Adoption adoption2 = newAdoption(date1,date2,customer, animal );
        Adoption adoption3 = newAdoption(date1,date2,customer, animal );

        manager.createAdoption(adoption1);
        manager.createAdoption(adoption2); 
        manager.createAdoption(adoption2); 

        //List<Customer> expected = Arrays.asList(customer1,customer2,customer3);
        List<Adoption> adoptions = new ArrayList<>(); //is that good..nobody knows
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
        Customer customer = newCustomer(123546L, "X Y", "adresa", "09xx xxx xxx");
        Animal animal = newAnimal(34L,"Lassie", 1994, FEMALE, true);
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2005");
        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2007");

       	Adoption adoption = newAdoption(date1, date2, customer, animal);
       	Adoption adoption2 = newAdoption(date1, date2, customer, animal);

        manager.createAdoption(adoption);
        manager.createAdoption(adoption2); 
        
        Long cID = adoption.getAdoptionID();
        
        adoption = manager.getAdoptionByID(cID); //meni id
        Long lo = 5L;
        adoption.setAdoptionID(lo);
        manager.updateAdoption(adoption); 
        Long lol = adoption.getAdoptionID();
        assertEquals(lo, lol);
        assertEquals(date1, adoption.getDateOfAdoption());
        assertEquals(date2, adoption.getDateOfReturn());
        assertEquals(customer,adoption.getCustomer());
        assertEquals(animal,adoption.getAnimal());

        adoption = manager.getAdoptionByID(cID); //meni date1
        Date date3 = new Date();
        adoption.setDateOfAdoption(date3);
	manager.updateAdoption(adoption);        
        assertEquals(lo, lol);
        assertEquals(date3, adoption.getDateOfAdoption());
        assertEquals(date2, adoption.getDateOfReturn());
        assertEquals(customer,adoption.getCustomer());
        assertEquals(animal,adoption.getAnimal());
        
	
        adoption = manager.getAdoptionByID(cID); //meni date2
        Date date4 = new Date();
        adoption.setDateOfReturn(date4);
        manager.updateAdoption(adoption);        
        assertEquals(lo, lol);
        assertEquals(date3, adoption.getDateOfAdoption());
        assertEquals(date4, adoption.getDateOfReturn());
        assertEquals(customer,adoption.getCustomer());
        assertEquals(animal,adoption.getAnimal());
        
        adoption = manager.getAdoptionByID(cID); //meni cstoemr
        Customer cus = newCustomer(1L, "X Y", "adresa", "09xx xxx xxx");
	adoption.setCustomer(cus);
        manager.updateAdoption(adoption);        
        assertEquals(lo, lol);
        assertEquals(date3, adoption.getDateOfAdoption());
        assertEquals(date4, adoption.getDateOfReturn());
        assertEquals(cus,adoption.getCustomer());
        assertEquals(animal,adoption.getAnimal());

        adoption = manager.getAdoptionByID(cID); //meni animal
        Animal ani = newAnimal(34L,"Lassie", 1994, FEMALE, true);
	adoption.setAnimal(ani);
        manager.updateAdoption(adoption);        
        assertEquals(lo, lol);
        assertEquals(date3, adoption.getDateOfAdoption());
        assertEquals(date4, adoption.getDateOfReturn());
        assertEquals(cus,adoption.getCustomer());
        assertEquals(ani,adoption.getAnimal());
        
        assertDeepEquals(adoption2, manager.getAdoptionByID(adoption2.getAdoptionID()));
    }

    @Test
    public void testUpdateAdoptionWithWrongAttributes() throws ParseException { //DONE
        
	Customer customer = newCustomer(123546L, "X Y", "adresa", "09xx xxx xxx");
        Animal animal = newAnimal(34L,"Lassie", 1994, FEMALE, true);
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2005");
        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2007");

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
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
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
        Customer customer = newCustomer(123546L, "X Y", "adresa", "09xx xxx xxx");
        Animal animal = newAnimal(34L,"Lassie", 1994, FEMALE, true);
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
        
        Customer customer = newCustomer(123546L, "X Y", "adresa", "09xx xxx xxx");
        Animal animal = newAnimal(34L,"Lassie", 1994, FEMALE, true);
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
        
        Customer customer = newCustomer(221L, "Peter", "Brno", "+09234923");
        assertNull(manager.findAllAdoptionsByCustomer(customer));
        
        Animal animal = newAnimal(34L,"Lassie", 1994, FEMALE, true);        
        Adoption adoption = newAdoption(date1, date2, customer, animal);  
        manager.createAdoption(adoption);
        
        List<Adoption> adExpected = new ArrayList<>();
        adExpected.add(adoption);
        
        date1 = new SimpleDateFormat("dd/MM/yyyy").parse("02/02/2003");
        date2 = null;
        
        Animal animal2 = newAnimal(23L,"Norton", 1994, null, true);        
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
        
        Customer customer = newCustomer(221L, "Peter", "Brno", "+09234923");
        Animal animal = newAnimal(34L,"Lassie", 1994, FEMALE, true);        
        assertNull(manager.findAllAdoptionsOfAnimal(animal));
        
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
    
    private static Animal newAnimal(Long id, String name, int year, Gender gender, boolean neutered) {

        Animal animal = new Animal();
        animal.setAnimalID(id);
        animal.setName(name);
        animal.setYearOfBirth(year);
        animal.setGender(gender);
        animal.setNeutered(neutered);
        return animal;
    }
    
        private static Customer newCustomer(Long customerID, String name, String address, String phoneNumber) { //pseudokostruktor, pretoze normalne konstruktory su mainstream
        Customer customer = new Customer();
        customer.setCustomerID(customerID);
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