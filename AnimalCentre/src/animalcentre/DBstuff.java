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
import org.apache.derby.jdbc.ClientDataSource;
import org.apache.derby.jdbc.ClientDriver;

/**
 *
 * @author blurry
 */
public class DBstuff {
    /*
     public static DataSource dataSource() throws IOException {
        Properties p =  new Properties();
        p.load(this.getClass().getResourceAsStream("/jdbc.properties"));
 
        BasicDataSource bds = new BasicDataSource(); //Apache DBCP connection pooling DataSource
        bds.setDriverClassName(p.getProperty("jdbc.driver"));
        bds.setUrl(p.getProperty("jdbc.url"));
        bds.setUsername(p.getProperty("jdbc.user"));
        bds.setPassword(p.getProperty("jdbc.password"));
        return bds;
    }*/
     
     public static DataSource dataSource2() throws IOException {
        
        BasicDataSource bds = new BasicDataSource(); //Apache DBCP connection pooling DataSource
        
        //bds.setDriverClassName("org.apache.derby.jdbc.ClientDriver"); //?
        bds.setUrl("jdbc:derby://localhost:1527/animal");
        bds.setUsername("blurry");
        bds.setPassword("a");
        return bds;
    }
    
     public static final String CREATE_TABLE = "CREATE TABLE ANIMAL " +
                   "(id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                   " name VARCHAR(255), " + 
                   " birthyear INTEGER, " + 
                   " gender VARCHAR(31), " + 
                   " neutered BOOLEAN) ";
                   //" PRIMARY KEY ( id ))";
                   //"(id BIGINT NOT NULL AUTO INCREMENT PRIMARY KEY, " +
     
     public static final String FINAL = "DROP TABLE " + "ANIMAL";
     
}