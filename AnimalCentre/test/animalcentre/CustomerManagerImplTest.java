/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animalcentre;

import java.util.*;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Barbora
 */
public class CustomerManagerImplTest {
    
    private CustomerManagerImpl manager;

    @Before
    public void setUp() {
        manager = new CustomerManagerImpl();
    }

    @Test
    public void testCreateCustomer() {
        Customer customer = newCustomer(123546, "X Y", "adresa", "09xx xxx xxx"); //vytv. random customera
        manager.createCustomer(customer); //vytv. rovnakeho cust. do manager
        Long customerID = customer.getCustomerID(); 
        assertNotNull(customerID); //nesmie byt null
        Customer result = manager.getCustomerByID(customerID); 
        assertEquals(customer, result);
        assertNotSame(customer, result); //--wtf is this
        assertDeepEquals(customer, result);   
    }

    /*@Test 
    public void createCustomerBadAttrib() { //laaaater. 
    }*/
    
    /**
     * Test of getCustomerByID method, of class CustomerManagerImpl.
     */
    @Test
    public void testGetCustomerByID() {
        Customer customer = newCustomer(123546, "X Y", "adresa", "09xx xxx xxx");
        manager.createCustomer(customer);
        Long customerID = customer.getCustomerID(); 
        Customer result = manager.getCustomerByID(customerID); 
        assertEquals(customer, result);
        assertDeepEquals(customer, result);   
    }

    /**
     * Test of findAllCustomers method, of class CustomerManagerImpl.
     */
    @Test
    public void testFindAllCustomers() {
        //fail("The test case is a prototype.");
        assertTrue(manager.findAllCustomers().isEmpty());
        Customer customer1 = newCustomer(123546, "X Y", "adresa", "09xx xxx xxx");
        Customer customer2 = newCustomer(654321, "A B", "homeless", "09yy yyy yyy");   
        Customer customer3 = newCustomer(000000, "C D", "dead", "N/A"); //because why not
        manager.createCustomer(customer1);
        manager.createCustomer(customer2); 
        manager.createCustomer(customer2); 
        //List<Customer> expected = Arrays.asList(customer1,customer2,customer3);
        List<Customer> zakaznici = new ArrayList<Customer>(); //is that good..nobody knows
        zakaznici.add(customer1);
        zakaznici.add(customer2);
        zakaznici.add(customer3);
        List<Customer> copy = manager.findAllCustomers();        
        Collections.sort(zakaznici,idComparator);
        Collections.sort(copy,idComparator);
        assertEquals(zakaznici, copy);
        assertDeepEquals(zakaznici, copy);       
    }

    /**
     * Test of updateCustomer method, of class CustomerManagerImpl.
     */
    @Test
    public void testUpdateCustomer() {
        //fail("The test case is a prototype.");
        Customer customer = newCustomer(123546, "X Y", "adresa", "09xx xxx xxx");
        Customer customer2 = newCustomer(654321, "A B", "homeless", "09yy yyy yyy");
        manager.createCustomer(customer);
        manager.createCustomer(customer2); 
        
        Long cID = customer.getCustomerID();
        
        customer = manager.getCustomerByID(cID); //meni id
        customer.setCustomerID(0);
        manager.updateCustomer(customer);        
        assertEquals(0, customer.getCustomerID());
        assertEquals("X Y", customer.getName());
        assertEquals("adresa", customer.getAddress());
        assertEquals("09xx xxx xxx",customer.getPhoneNumber());

        customer = manager.getCustomerByID(cID); //meni meno
        customer.setName(null);
        manager.updateCustomer(customer);        
        assertEquals(0, customer.getCustomerID());
        assertNull(customer.getName());
        assertEquals("adresa", customer.getAddress());
        assertEquals("09xx xxx xxx", customer.getPhoneNumber());
        
        customer = manager.getCustomerByID(cID); //meni adresu
        customer.setAddress(null);
        manager.updateCustomer(customer);        
        assertEquals(0, customer.getCustomerID());
        assertNull(customer.getName());
        assertNull(customer.getAddress());
        assertEquals("09xx xxx xxx", customer.getPhoneNumber());
        
        customer = manager.getCustomerByID(cID); //meni tel.cislo
        customer.setPhoneNumber(null);
        manager.updateCustomer(customer);        
        assertEquals(0, customer.getCustomerID());
        assertNull(customer.getName());
        assertNull(customer.getAddress());
        assertNull(customer.getPhoneNumber());//now you have no identity lol
        
        assertDeepEquals(customer2, manager.getCustomerByID(customer2.getCustomerID()));
    }

    /**
     * Test of deleteCustomer method, of class CustomerManagerImpl.
     */
    @Test
    public void testDeleteCustomer() {
        Customer customer = newCustomer(123546, "X Y", "adresa", "09xx xxx xxx");
        manager.createCustomer(customer);
        Long cID = customer.getCustomerID();
        manager.deleteCustomer(customer);
        Customer pom = manager.getCustomerByID(cID);
        assertNull(pom);
    }

        
    /***************************POMOCNE METODY***********************************/
    private static Customer newCustomer(Long customerID, String name, String address, String phoneNumber) { //pseudokostruktor, pretoze normalne konstruktory su mainstream
        Customer customer = new Customer();
        customer.setCustomerID(customerID);
        customer.setName(name);
        customer.setAddress(address);
        customer.setPhoneNumber(phoneNumber);
        return customer;
    }

    private void assertDeepEquals(Customer expected, Customer actual) { //equals customerov
        assertEquals(expected.getCustomerID(), actual.getCustomerID());
        assertEquals(expected.getAddress(), actual.getAddress());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
    }    
    
    private void assertDeepEquals(List<Customer> expectedList, List<Customer> actualList) { //equals listov
        for (int i = 0; i < expectedList.size(); i++) {
            Customer expected = expectedList.get(i);
            Customer actual = actualList.get(i);
            assertDeepEquals(expected, actual);
        }
    }
    
    private static Comparator<Customer> idComparator = new Comparator<Customer>() { 
        @Override
        public int compare(Customer o1, Customer o2) {
            return Long.valueOf(o1.getCustomerID()).compareTo(Long.valueOf(o2.getCustomerID()));
        }
    };
}
