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
    
    public void createCustomer(Customer customer) throws NotYetException;
    public Customer getCustomerByID(Long ID) throws NotYetException;
    public List<Customer> findAllCustomers() throws NotYetException;
    public void updateCustomer(Customer customer) throws NotYetException;
    public void deleteCustomer(Customer customer) throws NotYetException;

    
}
