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
    public void setUp() throws SQLException {
        manager = new CustomerManagerImpl();
    }

    @Test
    public void testCreateCustomer() {
        Customer customer = newCustomer("X Y", "adresa", "09xx xxx xxx"); //vytv. random customera
        manager.createCustomer(customer); //vytv. rovnakeho cust. do manager
        Long customerID = customer.getCustomerID(); 
        assertNotNull(customerID); //nesmie byt null
        Customer result = manager.getCustomerByID(customerID); 
        assertEquals(customer, result);
        assertNotSame(customer, result);
        assertDeepEquals(customer, result);   
    }

    public void createCustomerWithWrongAttributes() {

        try {
            manager.createCustomer(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        Customer customer = newCustomer("X Y", "adresa", "09xx xxx xxx");
        customer.setCustomerID(1L); //TOTO?
        try {
            manager.createCustomer(customer);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        customer = newCustomer("X Y", "adresa", "09xx xxx xxx"); 
        customer.setCustomerID(-1L);
        try {
            manager.createCustomer(customer);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        customer = newCustomer(null , "adresa", "09xx xxx xxx"); 
        try {
            manager.createCustomer(customer);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        customer = newCustomer("X Y", null, "09xx xxx xxx"); 
        try {
            manager.createCustomer(customer);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        customer = newCustomer("X Y", "adresa", null); 
        try {
            manager.createCustomer(customer);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

    }

    
    /**
     * Test of getCustomerByID method, of class CustomerManagerImpl.
     */
    @Test
    public void testGetCustomerByID() {
        Customer customer = newCustomer("X Y", "adresa", "09xx xxx xxx");
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
        Customer customer1 = newCustomer("X Y", "adresa", "09xx xxx xxx");
        Customer customer2 = newCustomer("A B", "homeless", "09yy yyy yyy");   
        Customer customer3 = newCustomer("C D", "dead", "N/A"); //because why not
        manager.createCustomer(customer1);
        manager.createCustomer(customer2); 
        manager.createCustomer(customer2); 
        //List<Customer> expected = Arrays.asList(customer1,customer2,customer3);
        List<Customer> customers = new ArrayList<Customer>(); //is that good..nobody knows
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        List<Customer> copy = manager.findAllCustomers();        
        Collections.sort(customers,idComparator);
        Collections.sort(copy,idComparator);
        assertEquals(customers, copy);
        assertDeepEquals(customers, copy);       
    }

    /**
     * Test of updateCustomer method, of class CustomerManagerImpl.
     */
    @Test
    public void testUpdateCustomer() {
        //fail("The test case is a prototype.");
        Customer customer = newCustomer("X Y", "adresa", "09xx xxx xxx");
        Customer customer2 = newCustomer("A B", "homeless", "09yy yyy yyy");
        manager.createCustomer(customer);
        manager.createCustomer(customer2); 
        
        Long cID = customer.getCustomerID();
        
        customer = manager.getCustomerByID(cID); //meni id
        customer.setCustomerID(0L);
        manager.updateCustomer(customer); 
        Long zeroID = 0L;
        assertEquals(zeroID, customer.getCustomerID());
        assertEquals("X Y", customer.getName());
        assertEquals("adresa", customer.getAddress());
        assertEquals("09xx xxx xxx",customer.getPhoneNumber());

        customer = manager.getCustomerByID(cID); //meni meno
        customer.setName("A A");
        manager.updateCustomer(customer);        
        assertEquals(zeroID, customer.getCustomerID());
        assertEquals("A A",customer.getName());
        assertEquals("adresa", customer.getAddress());
        assertEquals("09xx xxx xxx", customer.getPhoneNumber());
        
        customer = manager.getCustomerByID(cID); //meni adresu
        customer.setAddress("nowhere");
        manager.updateCustomer(customer);        
        assertEquals(zeroID, customer.getCustomerID());
        assertEquals("A A",customer.getName());
        assertEquals("nowhere", customer.getAddress());
        assertEquals("09xx xxx xxx", customer.getPhoneNumber());
        
        customer = manager.getCustomerByID(cID); //meni tel.cislo
        customer.setPhoneNumber("7yy yyy yyy");
        manager.updateCustomer(customer);        
        assertEquals(zeroID, customer.getCustomerID());
        assertEquals("A A",customer.getName());
        assertEquals("nowhere", customer.getAddress());
        assertEquals("7yy yyy yyy", customer.getPhoneNumber());
        
        assertDeepEquals(customer2, manager.getCustomerByID(customer2.getCustomerID()));
    }

    @Test
    public void testUpdateCustomerWithWrongAttributes() {

        Customer customer = newCustomer("X Y", "adresa", "09xx xxx xxx");
        manager.createCustomer(customer);
        Long customerID = customer.getCustomerID();
        
        try {
            manager.updateCustomer(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        try {
            customer = manager.getCustomerByID(customerID);
            customer.setCustomerID(null);
            manager.updateCustomer(customer);        
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            customer = manager.getCustomerByID(customerID);
            customer.setCustomerID(-1L);
            manager.updateCustomer(customer);        
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            customer = manager.getCustomerByID(customerID);
            customer.setName(null);
            manager.updateCustomer(customer);        
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            customer = manager.getCustomerByID(customerID);
            customer.setAddress(null);
            manager.updateCustomer(customer);        
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            customer = manager.getCustomerByID(customerID);
            customer.setPhoneNumber(null);
            manager.updateCustomer(customer);        
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

}
    
    /**
     * Test of deleteCustomer method, of class CustomerManagerImpl.
     */
    @Test
    public void testDeleteCustomer() {
        Customer customer = newCustomer("X Y", "adresa", "09xx xxx xxx");
        manager.createCustomer(customer);
        Long cID = customer.getCustomerID();
        manager.deleteCustomer(customer);
        Customer pom = manager.getCustomerByID(cID);
        assertNull(pom);
    }
    
    @Test
    public void testDeleteCustomerWithWrongAttributes() {

        Customer customer = newCustomer("X Y", "adresa", "09xx xxx xxx");
        
        try {
            manager.deleteCustomer(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            customer.setCustomerID(null);
            manager.deleteCustomer(customer);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            customer.setCustomerID(-1L);
            manager.deleteCustomer(customer);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        try {
            customer.setName(null);
            manager.deleteCustomer(customer);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        try {
            customer.setAddress(null);
            manager.deleteCustomer(customer);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        try {
            customer.setPhoneNumber(null);
            manager.deleteCustomer(customer);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }
        
    /***************************POMOCNE METODY***********************************/
    private static Customer newCustomer(String name, String address, String phoneNumber) { //pseudokostruktor, pretoze normalne konstruktory su mainstream
        Customer customer = new Customer();
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

    /*class customerBuilder {
    private int id = 0;
    private String name = "aaaa";
    private String add = "adddd";
    private String pn = "sdf";

        public void Id(int id) {
            this.id = id;
        }

        public CustomerBuilder Name(String name) {//takto vsetky
            this.name = name;
            return this;
        }

        public void Add(String add) {
            this.add = add;
        }

        public void Pn(String pn) {
            this.pn = pn;
        }
    
        
    
        
    }*/

}