/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animalcentre;
//import java.util.*;

/**
 *
 * @author Barbora
 */
public class Customer {
    private String name; 
    private String address;
    private long customerID;
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public long getCustomerID() {
        return customerID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCustomerID(long customerID) {
        this.customerID = customerID;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    
}
