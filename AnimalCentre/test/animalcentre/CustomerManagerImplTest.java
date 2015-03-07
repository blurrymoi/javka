/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animalcentre;

import java.util.Comparator;
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
public class CustomerManagerImplTest {
    
    private CustomerManagerImpl manager;
    
    /*public CustomerManagerImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }*/
    
    @Before
    public void setUp() {
        manager = new CustomerManagerImpl();
    }
    
    /*@After
    public void tearDown() {
    }*/

    /**
     * Test of createCustomer method, of class CustomerManagerImpl.
     */
    @Test
    public void testCreateCustomer() {
        Customer customer = newCustomer(123546, "X Y", "adresa", "09xx xxx xxx"); //vytv. random customera
        manager.createCustomer(customer); //vytv. rovnakeho cust. do manager
        Long customerID = customer.getCustomerID(); 
        assertNotNull(customerID); //nesmie byt null
        Customer result = manager.getCustomerByID(customerID); 
        assertEquals(customer, result);
        //assertNotSame(customer, result); //--what is this
        assertDeepEquals(customer, result);   
    }

    /**
     * Test of getCustomerByID method, of class CustomerManagerImpl.
     */
    @Test
    public void testGetCustomerByID() {
        //assertNull(manager.getGrave(1l)); //-- what
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
        fail("The test case is a prototype.");
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
        
        long cID = customer.getCustomerID();
        
        customer = manager.getCustomerByID(cID); //meni id
        customer.setCustomerID(0);
        manager.updateCustomer(customer);        
        assertEquals(0, customer.getCustomerID());
        assertEquals("X Y", customer.getName());
        assertEquals("adresa", customer.getAddress());
        assertEquals("09xx xxx xxx",customer.getPhoneNumber());

        
        customer = manager.getCustomerByID(cID); //meni meno
        customer.setName("NO NAME");
        manager.updateCustomer(customer);        
        assertEquals(0, customer.getCustomerID());
        assertEquals("NO NAME", customer.getName());
        assertEquals("adresa", customer.getAddress());
        assertEquals("09xx xxx xxx", customer.getPhoneNumber());
        
        customer = manager.getCustomerByID(cID); //meni adresu
        customer.setAddress("NOWHERE");
        manager.updateCustomer(customer);        
        assertEquals(0, customer.getCustomerID());
        assertEquals("NO NAME", customer.getName());
        assertEquals("NOWHERE", customer.getAddress());
        assertEquals("09xx xxx xxx", customer.getPhoneNumber());
        
        customer = manager.getCustomerByID(cID); //meni tel.cislo
        customer.setPhoneNumber("123");
        manager.updateCustomer(customer);        
        assertEquals(0, customer.getCustomerID());
        assertEquals("NO NAME", customer.getName());
        assertEquals("NOWHERE", customer.getAddress());
        assertEquals("123", customer.getPhoneNumber());
        
        
        // Check if updates didn't affected other records
        assertDeepEquals(customer2, manager.getCustomerByID(customer2.getCustomerID()));
        
    }

    /**
     * Test of deleteCustomer method, of class CustomerManagerImpl.
     */
    @Test
    public void testDeleteCustomer() {
        fail("The test case is a prototype.");
    }

        
    /***************************POMOCNE METODY***********************************/
    private static Customer newCustomer(long customerID, String name, String address, String phoneNumber) {
        Customer customer = new Customer();
        customer.setCustomerID(customerID);
        customer.setName(name);
        customer.setAddress(address);
        customer.setPhoneNumber(phoneNumber);
        return customer;
    }

    private void assertDeepEquals(List<Customer> expectedList, List<Customer> actualList) {
        for (int i = 0; i < expectedList.size(); i++) {
            Customer expected = expectedList.get(i);
            Customer actual = actualList.get(i);
            assertDeepEquals(expected, actual);
        }
    }

    private void assertDeepEquals(Customer expected, Customer actual) {
        assertEquals(expected.getCustomerID(), actual.getCustomerID());
        assertEquals(expected.getAddress(), actual.getAddress());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
    }
    
    private static Comparator<Customer> idComparator = new Comparator<Customer>() {
        @Override
        public int compare(Customer o1, Customer o2) {
            return Long.valueOf(o1.getCustomerID()).compareTo(Long.valueOf(o2.getCustomerID()));
        }
    };
}
