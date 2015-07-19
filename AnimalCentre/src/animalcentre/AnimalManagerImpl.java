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
 * @author blurry
 *
 */

public class AnimalManagerImpl implements AnimalManager{
    
    final static Logger log = Logger.getLogger(AnimalManagerImpl.class.getName());

    private final DataSource dataSource;
    //private Logger logger;
    //private FileHandler fh;

    public AnimalManagerImpl(DataSource dataSource) {
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
        }  */
    }
    
    
    @Override
    public void createAnimal(Animal animal) throws ServiceFailureException {
        if (animal == null) throw new IllegalArgumentException("animal is null");
        if (animal.getAnimalID() != null) throw new IllegalArgumentException("animal id is already set");
        if (animal.getName() == null) throw new IllegalArgumentException("animal name cannot be null");
        if (animal.getName().isEmpty()) throw new IllegalArgumentException("empty name of animal"); 
        if (animal.getYearOfBirth() < 0) throw new IllegalArgumentException("year of birth is negative"); 
        
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO ANIMAL (name,birthyear,gender,neutered) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                st.setString(1, animal.getName());
                st.setInt(2, animal.getYearOfBirth());
                if (animal.getGender() != null) st.setString(3, animal.getGender().name()); else st.setString(3, null);
                st.setBoolean(4, animal.isNeutered());
                int addedRows = st.executeUpdate();
                if (addedRows != 1) {
                    throw new ServiceFailureException("Internal Error: More rows inserted when trying to insert animal " + animal);
                }
                ResultSet keyRS = st.getGeneratedKeys();
                animal.setAnimalID(getKey(keyRS, animal));
            }
        } catch (SQLException ex) {
            log.logp(Level.SEVERE, "AnimalManagerImpl", "createAnimal", "db connection problem", ex);
            throw new ServiceFailureException("Could not create animal:" + animal, ex);
        }
    }
    
    private Long getKey(ResultSet keyRS, Animal animal) throws ServiceFailureException, SQLException {
        if (keyRS.next()) {
            if (keyRS.getMetaData().getColumnCount() != 1) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert animal " + animal
                        + " - wrong key fields count: " + keyRS.getMetaData().getColumnCount());
            }
            Long id = keyRS.getLong(1);
            if (keyRS.next()) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert animal " + animal
                        + " - more keys found");
            }
            return id;
        } else {
            throw new ServiceFailureException("Internal Error: Generated key"
                    + "retriving failed when trying to insert animal " + animal
                    + " - no key found");
        }
    }
    
    
    @Override
    public Animal getAnimalByID(Long ID) throws ServiceFailureException {
        try (Connection conn = dataSource.getConnection()) {
            if (ID == null) throw new IllegalArgumentException();
            try (PreparedStatement st = conn.prepareStatement("SELECT id,name,birthyear,gender,neutered FROM animal WHERE id = ?")) {
                st.setLong(1, ID);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    Animal animal = resultSetToAnimal(rs);
                    if (rs.next()) {
                        throw new ServiceFailureException(
                                "Internal error: More entities with the same id found "
                                        + "(source id: " + ID + ", found " + animal + " and " + resultSetToAnimal(rs));
                    }
                    return animal;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            log.logp(Level.SEVERE, "AnimalManagerImpl", "getAnimalByID", "db connection problem", ex);
            throw new ServiceFailureException("Error when getting animal by ID", ex);
        }
    }
    
    private Animal resultSetToAnimal(ResultSet rs) throws SQLException {
        Animal animal = new Animal();
        animal.setAnimalID(rs.getLong("id"));
        animal.setName(rs.getString("name"));
        animal.setYearOfBirth(rs.getInt("birthyear"));
        if (rs.getString("gender") != null) animal.setGender(Gender.valueOf(rs.getString("gender")));
        animal.setNeutered(rs.getBoolean("neutered"));
        return animal;
    }
    
    
    @Override   
    public List<Animal> findAllAnimals() throws ServiceFailureException {
        //log.log(Level.INFO, "retrieving animals");
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT id, name, birthyear, gender, neutered FROM animal")) {
                ResultSet rs = st.executeQuery();
                List<Animal> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(resultSetToAnimal(rs));
                }
                return result;
            }
        } catch (SQLException ex) {
            log.logp(Level.SEVERE, "AnimalManagerImpl", "findAllAnimals", "db connection problem", ex);
            throw new ServiceFailureException("Error when retrieving all animals", ex);
        }
    }
    
    @Override
    public void updateAnimal(Animal animal) throws ServiceFailureException {
        if(animal == null) throw new IllegalArgumentException("animal pointer is null");
        if(animal.getAnimalID() == null) throw new IllegalArgumentException("animal with null id cannot be updated");
        if (animal.getAnimalID() < 0) throw new IllegalArgumentException("animal ID is negative");
        if(animal.getYearOfBirth() < 0) throw new IllegalArgumentException("year of birth is negative");
        if (animal.getName() == null) throw new IllegalArgumentException("animal name cannot be null");
        if(animal.getName().isEmpty()) throw new IllegalArgumentException("empty name of animal");

        try (Connection conn = dataSource.getConnection()) {
            try(PreparedStatement st = conn.prepareStatement("UPDATE animal SET name=?,birthyear=?,gender=?,neutered=? WHERE id=?")) {
                st.setString(1, animal.getName());
                st.setInt(2, animal.getYearOfBirth());
                if (animal.getGender() != null) st.setString(3, animal.getGender().name()); else st.setString(3, null);
                st.setBoolean(4, animal.isNeutered());
                st.setLong(5, animal.getAnimalID());
                if(st.executeUpdate() != 1) {
                    throw new IllegalArgumentException("cannot update animal " + animal);
                }
            }
        } catch (SQLException ex) {
            log.logp(Level.SEVERE, "AnimalManagerImpl", "updateAnimal", "db connection problem", ex);
            throw new ServiceFailureException("Error when updating animal: " + animal, ex);
        }
    }
    
    @Override
    public void deleteAnimal(Animal animal) throws ServiceFailureException {
        try (Connection conn = dataSource.getConnection()) {
            if (animal == null) throw new IllegalArgumentException();
            if (animal.getAnimalID() == null) throw new IllegalArgumentException();
            try(PreparedStatement st = conn.prepareStatement("DELETE FROM animal WHERE id=?")) {
                st.setLong(1, animal.getAnimalID());
                if(st.executeUpdate() != 1) {
                    throw new ServiceFailureException("did not delete animal = " + animal);
                }
            }
        } catch (SQLException ex) {
            log.logp(Level.SEVERE, "AnimalManagerImpl", "deleteAnimal", "db connection problem", ex);
            throw new ServiceFailureException("Error when deleting an animal.", ex);
        }
    }
    
    @Override
    public void neuterAnimal(Animal animal) throws ServiceFailureException {
        if(animal == null) throw new IllegalArgumentException("animal pointer is null");
        if(animal.getAnimalID() == null) throw new IllegalArgumentException("animal with null id cannot be neutered");
        if (animal.getAnimalID() < 0) throw new IllegalArgumentException("animal ID is negative");
        if(animal.getYearOfBirth() < 0) throw new IllegalArgumentException("year of birth is negative");
        if (animal.getName() == null) throw new IllegalArgumentException("animal name cannot be null");
        if(animal.getName().isEmpty()) throw new IllegalArgumentException("empty name of animal");

        try (Connection conn = dataSource.getConnection()) {
            try(PreparedStatement st = conn.prepareStatement("UPDATE animal SET neutered=? WHERE id=?")) {
                st.setBoolean(1, true);
                st.setLong(2, animal.getAnimalID());
                if(st.executeUpdate() != 1) {
                    throw new IllegalArgumentException("cannot neuter animal " + animal);
                }
            }
        } catch (SQLException ex) {
            log.logp(Level.SEVERE, "AnimalManagerImpl", "neuterAnimal", "db connection problem", ex);
            throw new ServiceFailureException("Error when neutering animal: " + animal, ex);
        }
    }
    
}
