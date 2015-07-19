/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animalcentre;

import java.io.IOException;
import java.sql.SQLException;
//import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author blurry
 */
public class DBstuff {
         
     public static DataSource dataSource2() throws IOException {
        
        BasicDataSource bds = new BasicDataSource(); //Apache DBCP connection pooling DataSource
        
        bds.setUrl("jdbc:derby://localhost:1527/animal");
        bds.setUsername("blurry");
        bds.setPassword("a");
        return bds;
    }
    
     public static final String CREATE_TABLE_ANIMAL = "CREATE TABLE ANIMAL " +
                   "(id BIGINT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                   " name VARCHAR(255), " + 
                   " birthyear INTEGER, " + 
                   " gender VARCHAR(31), " + 
                   " neutered BOOLEAN) ";
     
     public static final String FINAL_ANIMAL = "DROP TABLE " + "ANIMAL";
    
     public static final String CREATE_TABLE_CUSTOMER = "CREATE TABLE CUSTOMER " +
                   "(id BIGINT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                   " name VARCHAR(255), " + 
                   " address VARCHAR(255), " + 
                   " phone VARCHAR(255)) ";
     
     public static final String FINAL_CUSTOMER = "DROP TABLE " + "CUSTOMER";

      public static final String CREATE_TABLE_ADOPTION = "CREATE TABLE ADOPTION " +
                   "(id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                   " dateOfAdoption DATE, " + 
                   " dateOfReturn DATE, " +
                   " customerid BIGINT REFERENCES CUSTOMER (ID), " + 
                   " animalid BIGINT REFERENCES ANIMAL (ID)) ";
     
     public static final String FINAL_ADOPTION = "DROP TABLE " + "ADOPTION";

}