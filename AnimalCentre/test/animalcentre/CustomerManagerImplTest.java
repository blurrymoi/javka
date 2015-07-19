/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author Barbora
 */
public class CustomerManagerImplTest {
    
    private CustomerManager manager;
    private DataSource ds;

    
    
    @Before
    public void setUp() throws SQLException, IOException {
               
        ds = DBstuff.dataSource2();
        
        try (Connection conn = ds.getConnection()){
            try (PreparedStatement pr = conn.prepareStatement(DBstuff.FINAL_CUSTOMER)){                
                    pr.executeUpdate();
        } catch (SQLException e) { 
            if (!e.getSQLState().equals("42Y55")) throw e;
            }
        
        //try (Connection conn = ds.getConnection()) {            
            try (PreparedStatement pr = conn.prepareStatement(DBstuff.CREATE_TABLE_CUSTOMER)){
                pr.executeUpdate();
            }
        }  //}
        manager = new CustomerManagerImpl(ds); 
    } 
    
    /*
    @Before
    public void setUp() throws SQLException, IOException {
               
        ds = DBstuff.dataSource2();
        
        try (Connection conn = ds.getConnection()) {            
            try (PreparedStatement pr = conn.prepareStatement(DBstuff.CREATE_TABLE_CUSTOMER)){
                pr.executeUpdate();
            }
        } 
        manager = new CustomerManagerImpl(ds);
    } */
    
    @After
    public void tearDown() throws SQLException, IOException {
        ds = DBstuff.dataSource2();
        try (Connection conn = ds.getConnection()){
            try (PreparedStatement pr = conn.prepareStatement(DBstuff.FINAL_CUSTOMER)){                
                    pr.executeUpdate();
        } catch (SQLException e) { 
            if (!e.getSQLState().equals("42Y55")) throw e;
            }
    } 
}
    
    @Test
    public void testCreateCustomer() {
               
        System.out.println("creating a customer..");
        Customer customer = newCustomer("X Y", "adresa", "09xx xxx xxx");
        manager.createCustomer(customer);
        
        Long custID = customer.getCustomerID();
        assertNotNull(custID);
        
        Customer comp = manager.getCustomerByID(custID);
        assertEquals(customer, comp);
        assertNotSame(customer, comp);
        assertDeepEquals(customer, comp);
    }

    public void createCustomerWithWrongAttributes() {
            System.out.println("creating customers with wrong attributes..");
            
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
        System.out.println("getting customer by ID");
        assertNull(manager.getCustomerByID(1L)); 

        try {
            manager.getCustomerByID(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }        
        
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
        
        System.out.println("looking for customers (list)..");
        
        assertTrue(manager.findAllCustomers().isEmpty());
        
        Customer customer1 = newCustomer("X Y", "adresa", "09xx xxx xxx");
        Customer customer2 = newCustomer("A B", "homeless", "09yy yyy yyy");   
        Customer customer3 = newCustomer("C D", "dead", "N/A"); 
        manager.createCustomer(customer1);
        manager.createCustomer(customer2); 
        manager.createCustomer(customer3); 
        
        List<Customer> customers = new ArrayList<Customer>(); 
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
                
        Customer customer = newCustomer("X Y", "adresa", "09xx xxx xxx");
        Customer customer2 = newCustomer("A B", "homeless", "09yy yyy yyy");
        manager.createCustomer(customer);
        manager.createCustomer(customer2); 
        
        Long cID = customer.getCustomerID();
        Long zeroID = 1L;
                
        customer = manager.getCustomerByID(cID); //meni id
        customer.setCustomerID(1L);
                
        manager.updateCustomer(customer); 
        
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
        System.out.println("updating customer with wrong attributes..");
        Customer customer = newCustomer("X Y", "adresa", "09xx xxx xxx");
        manager.createCustomer(customer);
        Long customerID = customer.getCustomerID();
        
        Customer dontAffect = newCustomer("X", "ad", "09");
        manager.createCustomer(dontAffect);
        
 
        Customer customer2 = manager.getCustomerByID(customerID);
        
        customer = manager.getCustomerByID(customerID); //meni id
        customer.setCustomerID(0L);
        
        try {
            manager.updateCustomer(customer); 
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }  
        
        try {
            manager.updateCustomer(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        customer2.setCustomerID(-100L);
        
        try {
            manager.updateCustomer(customer2);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        customer2.setCustomerID(customer.getCustomerID());
        customer2.setName("");
        
        try {
            manager.updateCustomer(customer2);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        customer2.setName(customer.getName());
        customer2.setAddress("");
        
        try {
            manager.updateCustomer(customer2);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        customer2.setAddress(customer.getAddress());
        customer2.setPhoneNumber("");
        
        try {
            manager.updateCustomer(customer2);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        customer2.setPhoneNumber(customer.getPhoneNumber());
        
        Customer customer3 = newCustomer("Who", "where", "123");
        customer3.setCustomerID(8L); //not in table?
        try {
            manager.updateCustomer(customer3);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
                       
        assertDeepEquals(customer,customer2);
        assertDeepEquals(dontAffect, manager.getCustomerByID(dontAffect.getCustomerID()));       

}
    
    /**
     * Test of deleteCustomer method, of class CustomerManagerImpl.
     */
    @Test
    public void testDeleteCustomer() {
        System.out.println("deleting customers..");        
        Customer dontAffectCustomer = newCustomer("X Y", "adresa", "09xx xxx xxx");
        manager.createCustomer(dontAffectCustomer);
        
        Customer customer= newCustomer("Who", "where", "123");
        manager.createCustomer(customer);
        
        manager.deleteCustomer(manager.getCustomerByID(customer.getCustomerID()));
        assertNull(manager.getCustomerByID(customer.getCustomerID()));
        
        assertNotNull(manager.getCustomerByID(dontAffectCustomer.getCustomerID()));
        assertDeepEquals(dontAffectCustomer, manager.getCustomerByID(dontAffectCustomer.getCustomerID()));
    }
    
    @Test
    public void testDeleteCustomerWithWrongAttributes() {
        System.out.println("deleting customers with wrong attributes..");

        try {
            manager.deleteCustomer(manager.getCustomerByID(1L)); //no animals present
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        Customer dontAffectCustomer = newCustomer("X Y", "adresa", "09xx xxx xxx");
        manager.createCustomer(dontAffectCustomer);
        
        
        try {
            manager.deleteCustomer(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        Customer customer= newCustomer("Who", "where", "123");
        
        try {
            customer.setCustomerID(null);
            manager.deleteCustomer(customer);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        assertNotNull(manager.getCustomerByID(dontAffectCustomer.getCustomerID()));
        assertDeepEquals(dontAffectCustomer, manager.getCustomerByID(dontAffectCustomer.getCustomerID()));
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

    

}