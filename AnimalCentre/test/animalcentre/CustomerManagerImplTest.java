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
        /*System.out.println("createCustomer");
        Customer customer = null;
        CustomerManagerImpl instance = new CustomerManagerImpl();
        instance.createCustomer(customer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
        Customer customer = newCustomer(123546, "X Y", "adresa", "09xx xxx xxx");
        manager.createCustomer(customer);
        
        Long customerID = customer.getCustomerID();
        assertNotNull(customerID);
        Customer result = manager.getCustomerByID(customerID);
        assertEquals(customer, result);
        //assertNotSame(customer, result); --what is this
        assertDeepEquals(customer, result);
        
        
    }

    /**
     * Test of getCustomerByID method, of class CustomerManagerImpl.
     */
    @Test
    public void testGetCustomerByID() {
        System.out.println("getCustomerByID");
        long ID = 0L;
        CustomerManagerImpl instance = new CustomerManagerImpl();
        Customer expResult = null;
        Customer result = instance.getCustomerByID(ID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAllCustomers method, of class CustomerManagerImpl.
     */
    @Test
    public void testFindAllCustomers() {
        System.out.println("findAllCustomers");
        CustomerManagerImpl instance = new CustomerManagerImpl();
        List<Customer> expResult = null;
        List<Customer> result = instance.findAllCustomers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateCustomer method, of class CustomerManagerImpl.
     */
    @Test
    public void testUpdateCustomer() {
        System.out.println("updateCustomer");
        Customer customer = null;
        CustomerManagerImpl instance = new CustomerManagerImpl();
        instance.updateCustomer(customer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteCustomer method, of class CustomerManagerImpl.
     */
    @Test
    public void testDeleteCustomer() {
        System.out.println("deleteCustomer");
        Customer customer = null;
        CustomerManagerImpl instance = new CustomerManagerImpl();
        instance.deleteCustomer(customer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
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
