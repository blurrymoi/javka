/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animalcentre;

import java.util.List;

/**
 *
 * @author Barbora
 */
public class AdoptionManagerImpl implements AdoptionManager {
    
    @Override
    public void createAdoption(Adoption adoption) throws NotYetException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Adoption getAdoptionByID(Long ID) throws NotYetException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public List<Adoption> findAllAdoptions() throws NotYetException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void updateAdoption(Adoption adoption) throws NotYetException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void deleteAdoption(Adoption adoption) throws NotYetException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public List<Adoption> findAllAdoptionsByCustomer(Customer customer) throws NotYetException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public List<Adoption> findAllAdoptionsOfAnimal(Animal animal) throws NotYetException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
