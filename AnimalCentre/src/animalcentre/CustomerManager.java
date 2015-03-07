/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animalcentre;
import java.util.*;

/**
 *
 * @author Barbora
 */
public interface CustomerManager {
    
    public void createCustomer(Customer customer) throws NoServiceException;
    public Customer getCustomerByID(long ID) throws NoServiceException;
    public List<Customer> findAllCustomers() throws NoServiceException;
    public void updateCustomer(Customer customer) throws NoServiceException;
    public void deleteCustomer(Customer customer) throws NoServiceException;

    
}
