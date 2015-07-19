/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animalcentre;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.sql.DataSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Barbora
 */
public class AdoptionManagerImpl implements AdoptionManager {
    
    final static Logger log = Logger.getLogger(AdoptionManagerImpl.class.getName());

    private final DataSource dataSource;
    
    private AnimalManager animalManager;
    private CustomerManager customerManager;
    //private Logger logger;
    //private FileHandler fh;

    public AdoptionManagerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        /*
        try {

        // This block will configure the logger with handler and formatter
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            //get current date time with Date()
            Date date = new Date();
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
        }
                */
    }
    
    public AnimalManager getAnimalManager() {
        return this.animalManager;
    }

    public CustomerManager getCustomerManager() {
        return customerManager;
    }

    public void setAnimalManager(AnimalManager animalManager) {
        this.animalManager = animalManager;
    }

    public void setCustomerManager(CustomerManager customerManager) {
        this.customerManager = customerManager;
    }
    
    
    
    
    @Override
    public void createAdoption(Adoption adoption) throws ServiceFailureException {
        if (adoption == null) throw new IllegalArgumentException("adoption is null");
        if (adoption.getAdoptionID() != null) throw new IllegalArgumentException("adoption id is already set");
        if (adoption.getDateOfAdoption() == null) throw new IllegalArgumentException("adoption date cannot be null");
        if (adoption.getCustomer() == null) throw new IllegalArgumentException("no customer"); 
        if (adoption.getAnimal() == null) throw new IllegalArgumentException("no animal"); 
        
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO ADOPTION (dateOfAdoption,dateOfReturn,customerid,animalid) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                java.sql.Date sqlDate = new java.sql.Date(adoption.getDateOfAdoption().getTime());
                st.setDate(1, sqlDate);
                
                if (adoption.getDateOfReturn() != null) {
                    java.sql.Date sqlDate2 = new java.sql.Date(adoption.getDateOfReturn().getTime());
                    st.setDate(2, sqlDate2);
                
                } else st.setDate(2, null);
                
                Long i = adoption.getCustomer().getCustomerID();
                int ii = new BigDecimal(i).intValueExact();
                st.setInt(3, ii);
                Long j = adoption.getAnimal().getAnimalID();
                int jj = new BigDecimal(j).intValueExact();
                st.setInt(4, jj);
                int addedRows = st.executeUpdate();
                if (addedRows != 1) {
                    throw new ServiceFailureException("Internal Error: More rows inserted when trying to insert adoption " + adoption);
                }
                ResultSet keyRS = st.getGeneratedKeys();
                adoption.setAdoptionID(getKey(keyRS, adoption));
            }
        } catch (SQLException ex) {
            log.logp(Level.SEVERE, "AdoptionManagerImpl", "createAdoption", "db connection problem", ex);
            throw new ServiceFailureException("Could not create adoption:" + adoption, ex);
        }
    }
    
    private Long getKey(ResultSet keyRS, Adoption adoption) throws ServiceFailureException, SQLException {
        if (keyRS.next()) {
            if (keyRS.getMetaData().getColumnCount() != 1) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert adoption " + adoption
                        + " - wrong key fields count: " + keyRS.getMetaData().getColumnCount());
            }
            Long id = keyRS.getLong(1);
            if (keyRS.next()) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert adoption " + adoption
                        + " - more keys found");
            }
            return id;
        } else {
            throw new ServiceFailureException("Internal Error: Generated key"
                    + "retriving failed when trying to insert adoption " + adoption
                    + " - no key found");
        }
    }
    
    
    @Override
    public Adoption getAdoptionByID(Long ID) throws ServiceFailureException {
        Adoption adoption = null;
        
        try (Connection conn = dataSource.getConnection()) {
            if (ID == null) throw new IllegalArgumentException();
            try (PreparedStatement st = conn.prepareStatement("SELECT id, dateOfAdoption, dateOfReturn, customerid, animalid FROM adoption WHERE id = ?")) {
                st.setLong(1, ID);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    adoption = resultSetToAdoption(rs);
                    if (rs.next()) {
                        throw new ServiceFailureException(
                                "Internal error: More entities with the same id found "
                                        + "(source id: " + ID + ", found " + adoption + " and " + resultSetToAdoption(rs));
                    }
                    
                } else {
                    return null;
                }
            } catch (ParseException ex) {
                Logger.getLogger(AdoptionManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            log.logp(Level.SEVERE, "AdoptionManagerImpl", "getAdoptionByID", "db connection problem", ex);
            throw new ServiceFailureException("Error when getting adoption by ID", ex);
        }
        return adoption;
    }
    
    private Adoption resultSetToAdoption(ResultSet rs) throws SQLException, ParseException {
        Adoption adoption = new Adoption();
        adoption.setAdoptionID(rs.getLong("id"));
        
        java.util.Date newDate = new Date(rs.getDate("dateOfAdoption").getTime());
        
        if (rs.getDate("dateOfReturn") == null) adoption.setDateOfReturn(null);
        else {
            java.util.Date newDate2 = new Date(rs.getDate("dateOfReturn").getTime());
            adoption.setDateOfReturn(newDate2);
        }
        adoption.setDateOfAdoption(newDate);
        
        
        CustomerManagerImpl customerManager = new CustomerManagerImpl(dataSource);
        AnimalManagerImpl animalManager = new AnimalManagerImpl(dataSource);
        
        adoption.setCustomer(customerManager.getCustomerByID(rs.getLong("customerid")));
        adoption.setAnimal(animalManager.getAnimalByID(rs.getLong("animalid")));
        
        return adoption;
    }
    
    
    @Override
    public List<Adoption> findAllAdoptions() throws ServiceFailureException {
        List<Adoption> result = null;
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT id,dateOfAdoption, dateOfReturn, customerid, animalid FROM adoption")) {
                ResultSet rs = st.executeQuery();
                result = new ArrayList<>();
                while (rs.next()) {
                    result.add(resultSetToAdoption(rs));
                }
               
            } catch (ParseException ex) {
                Logger.getLogger(AdoptionManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            log.logp(Level.SEVERE, "AdoptionManagerImpl", "findAllAdoptions", "db connection problem", ex);
            throw new ServiceFailureException("Error when retrieving all adoptions", ex);
        }
         return result;
    }
    
    @Override
    public void updateAdoption(Adoption adoption) throws ServiceFailureException {
        if(adoption == null) throw new IllegalArgumentException("adoption pointer is null");
        if(adoption.getAdoptionID() == null) throw new IllegalArgumentException("adoption with null id cannot be updated");
        if (adoption.getAdoptionID() < 0) throw new IllegalArgumentException("adoption ID is negative");
        if (adoption.getCustomer() == null) throw new IllegalArgumentException("adoption customer cannot be null");
        if (adoption.getAnimal() == null) throw new IllegalArgumentException("adoption animal cannot be null");
        if (adoption.getDateOfAdoption() == null) throw new IllegalArgumentException("date of adoption cannot be null");
        

        try (Connection conn = dataSource.getConnection()) {
            try(PreparedStatement st = conn.prepareStatement("UPDATE adoption SET dateOfAdoption=?,dateOfReturn=?,customerid=?,animalid=? WHERE id=?")) {
                                
                java.sql.Date sqlDate = new java.sql.Date(adoption.getDateOfAdoption().getTime());
                st.setDate(1, sqlDate);
                
                if (adoption.getDateOfReturn() != null) {
                    java.sql.Date sqlDate2 = new java.sql.Date(adoption.getDateOfReturn().getTime());
                    st.setDate(2, sqlDate2);
                
                } else st.setDate(2, null);
                
                Long i = adoption.getCustomer().getCustomerID();
                int ii = new BigDecimal(i).intValueExact();
                st.setInt(3, ii);
                Long j = adoption.getAnimal().getAnimalID();
                int jj = new BigDecimal(j).intValueExact();
                st.setInt(4, jj);
                
                Long k = adoption.getAdoptionID();
                int kk = new BigDecimal(k).intValueExact();
                st.setInt(5, kk);
                
                if(st.executeUpdate() != 1) {
                    throw new IllegalArgumentException("cannot update adoption " + adoption);
                }
            }
        } catch (SQLException ex) {
            log.logp(Level.SEVERE, "AdoptionManagerImpl", "updateAdoption", "db connection problem", ex);
            throw new ServiceFailureException("Error when updating adoption: " + adoption, ex);
        }
    }
    
    @Override
    public void deleteAdoption(Adoption adoption) throws ServiceFailureException {
        try (Connection conn = dataSource.getConnection()) {
            if (adoption == null) throw new IllegalArgumentException("adoption cannot be null");
            if (adoption.getAdoptionID() == null) throw new IllegalArgumentException("adoption ID null");
            if (adoption.getAdoptionID() < 0) throw new IllegalArgumentException("adoption ID negative");
            try(PreparedStatement st = conn.prepareStatement("DELETE FROM adoption WHERE id=?")) {
                st.setLong(1, adoption.getAdoptionID());
                if(st.executeUpdate() != 1) {
                    throw new ServiceFailureException("did not delete adoption = " + adoption);
                }
            }
        } catch (SQLException ex) {
            log.logp(Level.SEVERE, "AdoptionManagerImpl", "deleteAdoption", "db connection problem", ex);
            throw new ServiceFailureException("Error when deleting an adoption.", ex);
        }
    }
    
    @Override 
    public List<Adoption> findAllAdoptionsByCustomer(Customer customer) throws ServiceFailureException {
        //log.log(Level.INFO, "retrieving adoptions by customer");
        
        if (customer == null) throw new IllegalArgumentException("customer pointer is null");
        if (customer.getCustomerID() == null) throw new IllegalArgumentException("customer with null id cannot be updated");
        if (customer.getCustomerID() < 0) throw new IllegalArgumentException("customer ID is negative");
        
        Long pom = customer.getCustomerID();
        int po = new BigDecimal(pom).intValueExact();  
        List<Adoption> result = null;
                
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT id, dateOfAdoption, dateOfReturn, customerid, animalid FROM adoption WHERE customerid=" + po)) {
                ResultSet rs = st.executeQuery();
                result = new ArrayList<>();
                while (rs.next()) {
                    result.add(resultSetToAdoption(rs));
                }
                
            } catch (ParseException ex) {
                Logger.getLogger(AdoptionManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            log.logp(Level.SEVERE, "AdoptionManagerImpl", "findAllAdoptions", "db connection problem", ex);
            throw new ServiceFailureException("Error when retrieving all adoptions", ex);
        }
        return result;
    }
    
    @Override
    public List<Adoption> findAllAdoptionsOfAnimal(Animal animal) throws ServiceFailureException {
        //log.log(Level.INFO, "retrieving adoptions by animal");
        
        if (animal == null) throw new IllegalArgumentException("animal pointer is null");
        if (animal.getAnimalID() == null) throw new IllegalArgumentException("animal with null id cannot be updated");
        if (animal.getAnimalID() < 0) throw new IllegalArgumentException("animal ID is negative");
        
        Long pom = animal.getAnimalID();
        int po = new BigDecimal(pom).intValueExact();
        List<Adoption> result = null;
                
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT id, dateOfAdoption, dateOfReturn, customerid, animalid FROM adoption WHERE animalid=" + po)) {
                ResultSet rs = st.executeQuery();
                result = new ArrayList<>();
                while (rs.next()) {
                    result.add(resultSetToAdoption(rs));
                }
                
            } catch (ParseException ex) {
                Logger.getLogger(AdoptionManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            log.logp(Level.SEVERE, "AdoptionManagerImpl", "findAllAdoptions", "db connection problem", ex);
            throw new ServiceFailureException("Error when retrieving all adoptions", ex);
        }
        return result;
    }
}