/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animalcentre;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.sql.DataSource;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Barbora
 */
public class CustomerManagerImpl implements CustomerManager {
    
    
    final static Logger log = Logger.getLogger(CustomerManagerImpl.class.getName());

    private final DataSource dataSource;
    //private FileHandler fh;

    public CustomerManagerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        
        /*
        try {

        // This block will configure the logger with handler and formatter
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            //get current date time with Date()
            java.util.Date date = new java.util.Date();
            String path = Paths.get(".").toAbsolutePath().normalize().toString();
            path = path.replace("\\","\\\\");
            String filename = path + "\\logs" + String.valueOf(dateFormat.format(date));
            
            //String filename = "C:\\Users\\blurry\\Documents\\documentos\\UNI\\predmety\\Java\\AnimalCentre\\logs" + String.valueOf(dateFormat.format(date));

            fh = new FileHandler(filename);
            log.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        } */
    }
    
    ////////////
    
    @Override
    public void createCustomer(Customer customer) throws ServiceFailureException {
               
        if (customer == null) throw new IllegalArgumentException("customer is null");
        if (customer.getCustomerID() != null) throw new IllegalArgumentException("customer id is already set");
        if (customer.getName() == null) throw new IllegalArgumentException("customer name cannot be null");
        if (customer.getName().isEmpty()) throw new IllegalArgumentException("empty name of customer"); 
        if (customer.getAddress() == null) throw new IllegalArgumentException("customer address cannot be null");
        if (customer.getAddress().isEmpty()) throw new IllegalArgumentException("empty address of customer"); 
        if (customer.getPhoneNumber() == null) throw new IllegalArgumentException("customer phone number cannot be null");
        if (customer.getPhoneNumber().isEmpty()) throw new IllegalArgumentException("empty phone number of customer"); 
        
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO CUSTOMER (name,address,phone) VALUES (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                st.setString(1, customer.getName());
                st.setString(2, customer.getAddress());
                st.setString(3, customer.getPhoneNumber());
                int addedRows = st.executeUpdate();
                if (addedRows != 1) {
                    throw new ServiceFailureException("Internal Error: More rows inserted when trying to insert animal " + customer);
                }
                ResultSet keyRS = st.getGeneratedKeys();
                customer.setCustomerID(getKey(keyRS, customer));
            }
        } catch (SQLException ex) {
            log.logp(Level.SEVERE, "CustomerManagerImpl", "createCustomer", "db connection problem", ex);
            throw new ServiceFailureException("Could not create customer:" + customer, ex);
        }
    }
    
        private Long getKey(ResultSet keyRS, Customer customer) throws ServiceFailureException, SQLException {
        if (keyRS.next()) {
            if (keyRS.getMetaData().getColumnCount() != 1) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert animal " + customer
                        + " - wrong key fields count: " + keyRS.getMetaData().getColumnCount());
            }
            Long id = keyRS.getLong(1);
            if (keyRS.next()) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert animal " + customer
                        + " - more keys found");
            }
            return id;
        } else {
            throw new ServiceFailureException("Internal Error: Generated key"
                    + "retriving failed when trying to insert animal " + customer
                    + " - no key found");
        }
    }
    
    @Override
    public Customer getCustomerByID(Long ID) throws ServiceFailureException {
        try (Connection conn = dataSource.getConnection()) {
            if (ID == null) throw new IllegalArgumentException();
            try (PreparedStatement st = conn.prepareStatement("SELECT id,name,address,phone FROM customer WHERE id = ?")) {
                st.setLong(1, ID);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    Customer customer = resultSetToCustomer(rs);
                    if (rs.next()) {
                        throw new ServiceFailureException(
                                "Internal error: More entities with the same id found "
                                        + "(source id: " + ID + ", found " + customer + " and " + resultSetToCustomer(rs));
                    }
                    return customer;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            log.logp(Level.SEVERE, "CustomerManagerImpl", "getAnimalByID", "db connection problem", ex);
            throw new ServiceFailureException("Error when getting customer by ID", ex);
        }
    }
    
    private Customer resultSetToCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerID(rs.getLong("id"));
        customer.setName(rs.getString("name"));
        customer.setAddress(rs.getString("address"));
        customer.setPhoneNumber(rs.getString("phone"));
        return customer;
    }
    
    @Override
    public List<Customer> findAllCustomers() throws ServiceFailureException {
        //log.log(Level.INFO, "retrieving customer");
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT id, name, address, phone FROM customer")) {
                ResultSet rs = st.executeQuery();
                List<Customer> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(resultSetToCustomer(rs));
                }
                return result;
            }
        } catch (SQLException ex) {
            log.logp(Level.SEVERE, "CustomerManagerImpl", "findAllCustomer", "db connection problem", ex);
            throw new ServiceFailureException("Error when retrieving all customers", ex);
        }
    }
    
    @Override
    public void updateCustomer(Customer customer) throws ServiceFailureException {
        if (customer == null) throw new IllegalArgumentException("customer is null");
        if (customer.getCustomerID() == null) throw new IllegalArgumentException("customer with null id cannot be updated");
        if (customer.getName() == null) throw new IllegalArgumentException("customer name cannot be null");
        if (customer.getName().isEmpty()) throw new IllegalArgumentException("empty name of customer"); 
        if (customer.getAddress() == null) throw new IllegalArgumentException("customer address cannot be null");
        if (customer.getAddress().isEmpty()) throw new IllegalArgumentException("empty address of customer"); 
        if (customer.getPhoneNumber() == null) throw new IllegalArgumentException("customer phone number cannot be null");
        if (customer.getPhoneNumber().isEmpty()) throw new IllegalArgumentException("empty phone number of customer"); 

        try (Connection conn = dataSource.getConnection()) {
            try(PreparedStatement st = conn.prepareStatement("UPDATE customer SET name=?,address=?,phone=? WHERE id=?")) {
                st.setString(1, customer.getName());
                st.setString(2, customer.getAddress());
                st.setString(3, customer.getPhoneNumber());
                st.setLong(4, customer.getCustomerID());
                if(st.executeUpdate() != 1) {
                    throw new IllegalArgumentException("cannot update customer " + customer);
                }
            }
        } catch (SQLException ex) {
            log.logp(Level.SEVERE, "CustomerManagerImpl", "updateCustomer", "db connection problem", ex);
            throw new ServiceFailureException("Error when updating customer: " + customer, ex);
        }
    }
    
    @Override
    public void deleteCustomer(Customer customer) throws ServiceFailureException {
        try (Connection conn = dataSource.getConnection()) {
            if (customer == null) throw new IllegalArgumentException();
            if (customer.getCustomerID() == null) throw new IllegalArgumentException();
            try(PreparedStatement st = conn.prepareStatement("DELETE FROM customer WHERE id=?")) {
                st.setLong(1, customer.getCustomerID());
                if(st.executeUpdate() != 1) {
                    throw new ServiceFailureException("did not delete customer = " + customer);
                }
            }
        } catch (SQLException ex) {
            log.logp(Level.SEVERE, "CustomerManagerImpl", "deleteCustomer", "db connection problem", ex);
            throw new ServiceFailureException("Error when deleting a customer.", ex);
        }
    }
    
    

    
}