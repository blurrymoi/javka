/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.animalcentre;

import animalcentre.Adoption;
import animalcentre.AdoptionManagerImpl;
import animalcentre.Animal;
import animalcentre.AnimalManagerImpl;
import animalcentre.Customer;
import animalcentre.CustomerManagerImpl;
import animalcentre.DBstuff;
import animalcentre.Gender;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JDialog;
import javax.sql.DataSource;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.derby.impl.tools.sysinfo.Main;

/**
 *
 * @author blurry
 */
public class AnimCentrUI extends javax.swing.JFrame {

    private DataSource dataSource;
    private FileHandler fh;
    private static Logger logger = null;
    
    /**
     * Creates new form AnimCentrUI
     * @throws java.io.IOException
     */
    public AnimCentrUI() throws IOException { 
        initComponents();
        //tableAnimal.addKeyListener(new myKeyListener(tableAnimal)); 
        AbstractTableModel currentModel = new AnimalTableModel();
        
        final Logger logger = Logger.getLogger(AnimCentrUI.class.getName());
        
        try {

        // This block will configure the logger with handler and formatter
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
            //get current date time with Date()
            Date date = new Date();
            String path = Paths.get(".").toAbsolutePath().normalize().toString();
            path = path.replace("\\","\\\\");
            String filename = path + "\\\\logs" + String.valueOf(dateFormat.format(date));
            System.out.println(filename);
            
            fh = new FileHandler(filename);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            logger.info("Application started");

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
        
        setDataSource();
        
        
        jTabbedPane3.addChangeListener(new ChangeListener() {
            // This method is called whenever the selected tab changes
            @Override
            public void stateChanged(ChangeEvent evt) {
                JTabbedPane pane = (JTabbedPane) evt.getSource();
                
                int sel = pane.getSelectedIndex(); // get current tab
                fireTable(sel);
            }
        });
        
    }
    
    public void fireTable(int index){
                //boolean result = javax.swing.SwingUtilities.isEventDispatchThread();
                //System.out.println(result);

                switch (index) {
                    case 0: {                        
                        tableAnimal.setModel(new AnimalTableModel());
                        ((AnimalTableModel) tableAnimal.getModel()).addTableModelListener(tableAnimal);
                        ((AnimalTableModel) tableAnimal.getModel()).fillTable();
                    }
                    break;

                    case 1: {
                        tableCustomer.setModel(new CustomerTableModel());
                        ((CustomerTableModel) tableCustomer.getModel()).addTableModelListener(tableCustomer);
                        ((CustomerTableModel) tableCustomer.getModel()).fillTable();
                    }
                    break;
                        
                    case 2: {
                        tableAdoption.setModel(new AdoptionTableModel());
                        ((AdoptionTableModel) tableAdoption.getModel()).addTableModelListener(tableAdoption);
                        ((AdoptionTableModel) tableAdoption.getModel()).fillTable();
                    }
                    break;
                        
                    default: {
                        throw new IllegalArgumentException("Invalid panel number");
                    }
                }
    }
    
    private void removeHandlers() {
        logger = Logger.getLogger(AnimCentrUI.class.getName());
        Handler[] handlers = logger.getHandlers();
        for (Handler handler : handlers) {
            logger.removeHandler(handler);
        }
    }

    
    class AnimalTableModel extends AbstractTableModel {
    
    List<Animal> myAnimals = new ArrayList<>();
    private DataSource dataSource;

    @Override
    public void addTableModelListener(TableModelListener l) {
        super.addTableModelListener(l);
    }   

    @Override
    public int getRowCount() {
        return myAnimals.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(rowIndex >= myAnimals.size()) {
            throw new IllegalArgumentException(LanguageWizard.getString("Invalid_row_index"));
        }
        Animal animal = myAnimals.get(rowIndex);
        
        switch(columnIndex) {
            case 0: {
                return animal.getAnimalID();
            }
            case 1: {
                return animal.getName();
            }
            case 2: {
                return animal.getYearOfBirth();
            }
            case 3: {
                return LanguageWizard.getString(String.valueOf(animal.getGender()));
            }
            case 4: {
                return LanguageWizard.getString(String.valueOf(animal.isNeutered()));
            }
        }
        throw new IllegalArgumentException(LanguageWizard.getString("Invalid_column_index"));
    }
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if(rowIndex > myAnimals.size()) {
            throw new IllegalArgumentException();
        }
        Animal animal = myAnimals.get(rowIndex);
        
        switch(columnIndex) {
            /*case 0: {
                animal.setAnimalID((Long)value); 
                break; //this should not be editable D:
            }*/
            case 1: {
                animal.setName((String)value);
                break;
            }
            case 2: {
                animal.setYearOfBirth((Integer)value);
                break;
            }
            case 3: {
                if (String.valueOf(value).isEmpty()) animal.setGender(null);
                else animal.setGender(Gender.valueOf((String)value));
                break;
            }
            case 4: {
                animal.setNeutered((boolean)value);
                break;
            }
            default:
                throw new IllegalArgumentException("Tried to update ID cell or out of range");
        }
        
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    
    @Override
    public Class getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0: return Integer.class;
            case 1: return String.class;
            case 2: return Integer.class;
            case 3: return String.class; 
            case 4: return boolean.class; 
            default: throw new IllegalArgumentException(LanguageWizard.getString("Invalid_column1"));
        }
        //return getValueAt(0, columnIndex).getClass();
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        switch(columnIndex){
            case 0: return LanguageWizard.getString("ID");
            case 1: return LanguageWizard.getString("Name");
            case 2: return LanguageWizard.getString("Year_of_birth");
            case 3: return LanguageWizard.getString("Gender"); 
            case 4: return LanguageWizard.getString("Neutered"); 
            default: throw new IllegalArgumentException(LanguageWizard.getString("Invalid_column"));
        }
    } 
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; //!((columnIndex == 0) || (columnIndex > 4));
    }

    public void addAnimal(Animal animal) {
        myAnimals.add(animal);
        int lastRow = myAnimals.size()-1;
        
        fireTableRowsInserted(lastRow, lastRow); //added row; table update
    }
     
    public void fillTable() {
        
        SwingWorker mySwingWorker = new fillingWorker();
        mySwingWorker.execute();
        
    }
    
    public void removeAnimal(int ID, int row, javax.swing.JTable myTable) {
           
        AnimalManagerImpl manager = new AnimalManagerImpl(dataSource);
        Animal animal = manager.getAnimalByID(new Long(ID));
        
        if(animal == null) {
            throw new UnsupportedOperationException(LanguageWizard.getString("This_animal_is_not_present"));
        }
        
        manager.deleteAnimal(animal);
        myAnimals.remove(animal);           

    }
    
    
    void refreshTable(JTable myTable) {
        myTable.setModel(new AnimalTableModel());
    }

    private class fillingWorker extends SwingWorker<List<Animal>, Void> {
        
        private List<Animal> allAnimals;
        
        @Override
        protected List<Animal> doInBackground() throws Exception {
            dataSource = DBstuff.dataSource2();
            AnimalManagerImpl manager = new AnimalManagerImpl(dataSource);
            allAnimals = new ArrayList<>(manager.findAllAnimals());
            return allAnimals;
        }

        @Override
        protected void done() {
            for (Animal allAnimal : allAnimals) {
                addAnimal(allAnimal);
            }
        }

    }
}
    
    
class CustomerTableModel extends AbstractTableModel {
    
    List<Customer> myCustomers = new ArrayList<>();
    private DataSource dataSource;

    @Override
    public void addTableModelListener(TableModelListener l) {
        super.addTableModelListener(l);
    }   

    @Override
    public int getRowCount() {
        return myCustomers.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(rowIndex >= myCustomers.size()) {
            throw new IllegalArgumentException(LanguageWizard.getString("Invalid_row_index"));
        }
        Customer customer = myCustomers.get(rowIndex);
        
        switch(columnIndex) {
            case 0: {
                return customer.getCustomerID();
            }
            case 1: {
                return customer.getName();
            }
            case 2: {
                return customer.getAddress();
            }
            case 3: {
                return customer.getPhoneNumber();
            }
        }
        throw new IllegalArgumentException(LanguageWizard.getString("Invalid_column_index"));
    }
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if(rowIndex > myCustomers.size()) {
            throw new IllegalArgumentException();
        }
        Customer customer = myCustomers.get(rowIndex);
        
        switch(columnIndex) {
            case 0: {
                customer.setCustomerID((Long)value);
                break;
            }
            case 1: {
                customer.setName((String)value);
                break;
            }
            case 2: {
                customer.setAddress((String)value);
                break;
            }
            case 3: {
                customer.setPhoneNumber((String)value);
                break;
            }

        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    @Override
    public Class getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0: return Integer.class;
            case 1: return String.class;
            case 2: return String.class;
            case 3: return String.class; 
            default: throw new IllegalArgumentException(LanguageWizard.getString("Invalid_column1"));
        }
        //return getValueAt(0, columnIndex).getClass();
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        switch(columnIndex){
            case 0: return LanguageWizard.getString("ID");
            case 1: return LanguageWizard.getString("Name");
            case 2: return LanguageWizard.getString("Address");
            case 3: return LanguageWizard.getString("Phone_number"); 
            default: throw new IllegalArgumentException(LanguageWizard.getString("Invalid_column"));
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; //!((columnIndex == 0) || (columnIndex > 3));
    }

    public void addCustomer(Customer customer) {
        myCustomers.add(customer);
        int lastRow = myCustomers.size()-1;
        
        fireTableRowsInserted(lastRow, lastRow); //added row; table update
    }
    
    public void fillTable() {
        
        SwingWorker mySwingWorker = new fillingWorker();
        mySwingWorker.execute();
        
    }
    
    public void removeCustomer(int ID, int row, javax.swing.JTable myTable) {
        
        CustomerManagerImpl manager = new CustomerManagerImpl(dataSource);
        Customer customer = manager.getCustomerByID(new Long(ID));
        
        if(customer == null) {
            throw new UnsupportedOperationException(LanguageWizard.getString("This_customer_is_not_present"));
        }
        
        manager.deleteCustomer(customer);
        myCustomers.remove(customer);           

    }
    
    void refreshTable(JTable myTable) {
        myTable.setModel(new CustomerTableModel());
    }

    private class fillingWorker extends SwingWorker <List<Customer>, Void> {
        
        private List<Customer> allCustomers;

        @Override
        protected List<Customer> doInBackground() throws Exception {
            dataSource = DBstuff.dataSource2();
            CustomerManagerImpl manager = new CustomerManagerImpl(dataSource);
            allCustomers = new ArrayList<>(manager.findAllCustomers());
            return allCustomers;
        }

        @Override
        protected void done() {   
            for (Customer allCustomer : allCustomers) {
                addCustomer(allCustomer);
            }
        }        
    }
}    



class AdoptionTableModel extends AbstractTableModel {
    
    List<Adoption> myAdoptions = new ArrayList<>();
    private DataSource dataSource;

    @Override
    public void addTableModelListener(TableModelListener l) {
        super.addTableModelListener(l);
    }   

    @Override
    public int getRowCount() {
        return myAdoptions.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(rowIndex >= myAdoptions.size()) {
            throw new IllegalArgumentException(LanguageWizard.getString("Invalid_row_index"));
        }
        Adoption adoption = myAdoptions.get(rowIndex);
        
        switch(columnIndex) {
            case 0: {
                return adoption.getAdoptionID();
            }
            case 1: {
                return adoption.getDateOfAdoption();
            }
            case 2: {
                return adoption.getDateOfReturn();
            }
            case 3: {
                return adoption.getCustomer().getCustomerID();
            }
            case 4: {
                return adoption.getAnimal().getAnimalID();
            }
        }
        throw new IllegalArgumentException(LanguageWizard.getString("Invalid_column_index"));
    }
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if(rowIndex > myAdoptions.size()) {
            throw new IllegalArgumentException();
        }
        Adoption adoption = myAdoptions.get(rowIndex);
        
        switch(columnIndex) {
            case 0: {
                adoption.setAdoptionID((Long)value);
                break;
            }
            case 1: {
                java.sql.Date sqlDate = new java.sql.Date((Long)value);
                adoption.setDateOfAdoption(sqlDate);
                break;
            }
            case 2: {
                if (value != null) {
                    java.sql.Date sqlDateR = new java.sql.Date((Long)value);
                    adoption.setDateOfReturn(sqlDateR);
                } else adoption.setDateOfReturn(null);
                
                break;
            }
            case 3: {
                CustomerManagerImpl custMan = new CustomerManagerImpl(dataSource);
                adoption.setCustomer(custMan.getCustomerByID((Long)value));
                break;
            }
            case 4: {
                AnimalManagerImpl animMan = new AnimalManagerImpl(dataSource);
                adoption.setAnimal(animMan.getAnimalByID((Long)value));
                break;
            }
                
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    @Override
    public Class getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0: return Integer.class;
            case 1: return Date.class;
            case 2: return Date.class;
            case 3: return Integer.class; 
            case 4: return Integer.class; 
            default: throw new IllegalArgumentException(LanguageWizard.getString("Invalid_column1"));
        }
        //return getValueAt(0, columnIndex).getClass();
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        switch(columnIndex){
            case 0: return LanguageWizard.getString("ID");
            case 1: return LanguageWizard.getString("Date_of_adoption");
            case 2: return LanguageWizard.getString("Date_of_return");
            case 3: return LanguageWizard.getString("CustomerID"); 
            case 4: return LanguageWizard.getString("AnimalID"); 
            default: throw new IllegalArgumentException(LanguageWizard.getString("Invalid_column"));
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void addAdoption(Adoption adoption) {
        myAdoptions.add(adoption);
        int lastRow = myAdoptions.size()-1;
        
        fireTableRowsInserted(lastRow, lastRow); //added row; table update
    }
    
    public void fillTable() {
        
        SwingWorker mySwingWorker = new fillingWorker();
        mySwingWorker.execute();
        
    }
    
    public void removeAdoption(int ID, int row, javax.swing.JTable myTable) {
           
        AdoptionManagerImpl manager = new AdoptionManagerImpl(dataSource);
        Adoption adoption = manager.getAdoptionByID(new Long(ID));
        
        if(adoption == null) {
            throw new UnsupportedOperationException(LanguageWizard.getString("This_adoption_is_not_present"));
        }
        
        manager.deleteAdoption(adoption);
        myAdoptions.remove(adoption);           

    }
    
    
    void refreshTable(JTable myTable) {
        myTable.setModel(new AnimalTableModel());
    }

    private class fillingWorker extends SwingWorker<List<Adoption>, Void> {
        
        private List<Adoption> allAdoptions;
        
        @Override
        protected List<Adoption> doInBackground() throws Exception {
            dataSource = DBstuff.dataSource2();
            AdoptionManagerImpl manager = new AdoptionManagerImpl(dataSource);
            allAdoptions = new ArrayList<>(manager.findAllAdoptions());
            return allAdoptions;
        }

        @Override
        protected void done() {
            for(int i = 0; i < allAdoptions.size(); i++) {
            addAdoption(allAdoptions.get(i));
        }
        }

    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        fileChooser = new javax.swing.JFileChooser();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableAnimal = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableCustomer = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableAdoption = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("Main/resources/Bundle"); // NOI18N
        jMenu1.setText(bundle.getString("AnimCentrUI.jMenu1.text")); // NOI18N

        jMenu2.setText(bundle.getString("AnimCentrUI.jMenu2.text")); // NOI18N

        jMenuItem6.setText(bundle.getString("AnimCentrUI.jMenuItem6.text")); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tableAnimal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableAnimal);
        tableAnimal.setModel(new AnimalTableModel());
        ((AnimalTableModel) tableAnimal.getModel()).addTableModelListener(tableAnimal);
        ((AnimalTableModel) tableAnimal.getModel()).fillTable();

        jTabbedPane3.addTab(bundle.getString("AnimCentrUI.jScrollPane1.TabConstraints.tabTitle"), jScrollPane1); // NOI18N

        tableCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tableCustomer);

        jTabbedPane3.addTab(bundle.getString("AnimCentrUI.jScrollPane3.TabConstraints.tabTitle"), jScrollPane3); // NOI18N

        tableAdoption.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tableAdoption);

        jTabbedPane3.addTab(bundle.getString("AnimCentrUI.jScrollPane2.TabConstraints.tabTitle"), jScrollPane2); // NOI18N

        jMenu6.setText(bundle.getString("AnimCentrUI.jMenu6.text")); // NOI18N

        jMenuItem5.setText(bundle.getString("AnimCentrUI.jMenuItem5.text")); // NOI18N
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem5);

        jMenuItem7.setText(bundle.getString("AnimCentrUI.jMenuItem7.text")); // NOI18N
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem7);

        jMenuItem8.setText(bundle.getString("AnimCentrUI.jMenuItem8.text")); // NOI18N
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem8);

        jMenuItem1.setText(bundle.getString("AnimCentrUI.jMenuItem1.text")); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem1);

        jMenuBar1.add(jMenu6);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
       JDialog anim = new AnimalUI(fh, dataSource);
       anim.setVisible(true);
       
       //will refresh the Animal table once the "Handle animals" window is closed
        anim.addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosed(WindowEvent e) {
                int sel = jTabbedPane3.getSelectedIndex();
                fireTable(sel);
                
                removeHandlers();  
                //logger.addHandler(fh); //
                
                //throw new IllegalArgumentException("pourquoi pas");
            }
        });
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
       JDialog custom = new CustomerUI(fh, dataSource);
       custom.setVisible(true);
       
       custom.addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosed(WindowEvent e) {
                int sel = jTabbedPane3.getSelectedIndex();
                fireTable(sel);
                
                removeHandlers();
            }
        });
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        JDialog adopt = new AdoptionUI(fh, dataSource);
        adopt.setVisible(true);
        
        adopt.addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosed(WindowEvent e) {
                int sel = jTabbedPane3.getSelectedIndex();
                fireTable(sel);
                
                removeHandlers();
            }
        });
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws Exception {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (java.util.ResourceBundle.getBundle("Main/resources/Bundle").getString("NIMBUS").equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AnimCentrUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AnimCentrUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AnimCentrUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnimCentrUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AnimCentrUI().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(AnimCentrUI.class.getName()).log(Level.SEVERE, "IO Exception in AnimCentrUI", ex);
                }
                
                /*
                // for logging uncaught exceptions
                Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    public void uncaughtException(Thread t, Throwable e) {
                        logger.log(Level.SEVERE, "Uncaught Exception in thread '" + t.getName() + "'", e);
                        logger.info("wtf");
                        System.exit(1);
                    }
                }); */
  
                }
        });
    }

    /*
    private class myKeyListener implements KeyListener {
        
        private DataSource dataSource;
        private JTable table; 
        private TableModel tableModel; 

        public myKeyListener(javax.swing.JTable myTable) {
        }

        @Override
        public void keyTyped(KeyEvent evt) {
        }
    
        @Override
        public void keyPressed(KeyEvent evt) {
            
            switch (evt.getKeyCode()) {
                case KeyEvent.VK_DELETE: {

                    if("AnimalTableModel".equals(String.valueOf(tableAnimal.getModel()))) {
                        Animal animal = new Animal();
                        int animID;
                        int row = tableAnimal.getSelectedRow();
                        animID = (int) ((AnimalTableModel)tableAnimal.getModel()).getValueAt(row, 0);
                
                        if(animID == -1) {
                            break;
                        }
                        ((AnimalTableModel)tableAnimal.getModel()).removeAnimal(animID, row, tableAnimal);
                        ((AnimalTableModel)tableAnimal.getModel()).refreshTable(tableAnimal);
                        
                        try { 
                            dataSource = DBstuff.dataSource2();
                        } catch (IOException ex) {
                            Logger.getLogger(AnimCentrUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        ((AnimalTableModel)tableAnimal.getModel()).fillTable();
                        break;
                    }
                    
                    //if(((CustomerTableModel)jTable3.getModel()).getColumnName(2).equals(LanguageWizard.getString("Address"))) {
                    if("CustomerTableModel".equals(String.valueOf(tableCustomer.getModel()))) {
                        Customer customer = new Customer();
                        int customID;
                        int row = tableCustomer.getSelectedRow();
                        customID = (int) ((CustomerTableModel)tableCustomer.getModel()).getValueAt(row, 0);
                
                        if(customID == -1) {
                            break;
                        }
                        ((CustomerTableModel)tableCustomer.getModel()).removeCustomer(customID, row, tableCustomer);
                        ((CustomerTableModel)tableCustomer.getModel()).refreshTable(tableCustomer);
                        
                        try {
                            dataSource = DBstuff.dataSource2();
                        } catch (IOException ex) {
                            Logger.getLogger(AnimCentrUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        ((CustomerTableModel)tableCustomer.getModel()).fillTable();
                        break;
                    }     
            }    
            case KeyEvent.VK_ESCAPE: {
                System.exit(0);
                break; 
            }    
            
            case KeyEvent.VK_RIGHT: {
                try {
                    dataSource = DBstuff.dataSource2();
                } catch (IOException ex) {
                    Logger.getLogger(AnimCentrUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                AdoptionManagerImpl manager = new AdoptionManagerImpl(dataSource);
                
                javax.swing.JTable suitable = new JTable();
                JScrollPane scrollPane = new JScrollPane(suitable);
                javax.swing.JFrame additional = new JFrame();
                additional.add(suitable);
                additional.add(scrollPane);
                additional.setDefaultCloseOperation(EXIT_ON_CLOSE);
                additional.setVisible(true);
                suitable.setSize(500,400);
                suitable.setVisible(true);
                additional.pack();
                additional.setSize(500,400);  
                suitable.setFillsViewportHeight(true);
    
    */
                
                /*
                if(((AnimalTableModel)jTable1.getModel()).getColumnName(2).equals(LanguageWizard.getString("Year of birth"))) {
                    suitable.setModel(new AnimalTableModel());
                    
                    Animal selectedOne = new Animal();
                    int animId = ((int)((AnimalTableModel)jTable1.getModel()).getValueAt(jTable1.getSelectedRow(), 0));
                    
                    AnimalManagerImpl animManager = new AnimalManagerImpl(dataSource);
                    selectedOne = animManager.getAnimalByID(new Long(animId));
                    ArrayList<Adoption> allAnimals = new ArrayList<>(manager.findAllAdoptionsOfAnimal(selectedOne));
        
                    for(int i = 0; i < allAnimals.size(); i++) {
                        ((AdoptionTableModel)suitable.getModel()).addAdoption(allAdoptions.get(i));
                    }                    
                    break;
                }
                    
                if(((CustomerTableModel)jTable2.getModel()).getColumnName(2).equals(LanguageWizard.getString("Address"))) {
                    suitable.setModel(new CustomerTableModel());
                    
                    Customer selectedOne = new Customer();
                    int customId = ((int)((CustomerTableModel)jTable2.getModel()).getValueAt(jTable2.getSelectedRow(), 0));
                    
                    CustomerManagerImpl offerManager = new CustomerManagerImpl(dataSource);
                    selectedOne = offerManager.getCustomerByID(new Long(customId));
                    ArrayList<Adoption> allCustomers = new ArrayList<>(manager.findAllAdoptionsByCustomer(selectedOne));
        
                    for(int i = 0; i < allCustomers.size(); i++) {
                        ((CustomerTableModel)suitable.getModel()).addAdoption(allCustomers.get(i));
                    }
                    break;
                }  */    
                
                
                /*
                break; 
            }     
                        
            default: {
                break; 
            } 
        } 
    }
    
    @Override
    public void keyReleased(KeyEvent evt) {
    }
}
    */
    
    /**
     * Will set dataSource attribute according to url, username and password from myconf properties.
     */
    private void setDataSource() {
        
        Properties myconf = new Properties();
        try {
            myconf.load(Main.class.getResourceAsStream(LanguageWizard.getString("Main_resources_myconf_properties")));
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Could not set database info", ex);
        }
        
        BasicDataSource bds = new BasicDataSource(); //Apache DBCP connection pooling DataSource
        
        bds.setUrl(myconf.getProperty(LanguageWizard.getString("jdbc_url")));
        bds.setUsername(myconf.getProperty(LanguageWizard.getString("jdbc_user")));
        bds.setPassword(myconf.getProperty(LanguageWizard.getString("jdbc_password")));
        this.dataSource = bds;
    }
    
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTable tableAdoption;
    private javax.swing.JTable tableAnimal;
    private javax.swing.JTable tableCustomer;
    // End of variables declaration//GEN-END:variables
}
