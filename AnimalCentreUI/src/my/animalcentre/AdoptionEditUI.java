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
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.joda.time.DateTime;

/**
 *
 * @author blurry
 */
public class AdoptionEditUI extends javax.swing.JFrame {
    private Adoption adoption;
    private DataSource dataSource;
    private static Logger logger;
    private List<Animal> myAnimals;
    private List<Customer> myCustomers;

    /**
     * Creates new form AdoptionEditUI
     */
    public AdoptionEditUI() {
        initComponents();
    }

    AdoptionEditUI(Adoption adopt, DataSource dataSource, final Logger logger) {
        initComponents();
        
        this.dataSource = dataSource;
        this.adoption = adopt;
        this.logger = logger;
        setCombos();
        
        try {
            populateAnimalCustomer(dataSource);
        } catch (SQLException ex) {
            Logger.getLogger(AdoptionEditUI.class.getName()).log(Level.SEVERE, "Could not populate animal customer lists", ex);
        }
        
        setToAdopt(adoption);
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //labelDay.setText(LanguageWizard.getString("Name"));
                //labelYear.setText(LanguageWizard.getString("Year_of_birth"));
                //labelGender.setText(LanguageWizard.getString("Gender"));
                //labelNeutered.setText(LanguageWizard.getString("Neutered"));
            }
        });
        
        
         // for logging uncaught exceptions
                Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    public void uncaughtException(Thread t, Throwable e) {
                        logger.log(Level.SEVERE, "Uncaught Exception in thread '" + t.getName() + "'", e);
                        System.exit(1);
                    }
                });

    }
    
    private void populateAnimalCustomer(DataSource dataSource) throws SQLException {
        DefaultListModel<String> modelAnim = new DefaultListModel<String>(); //create a new list model
        DefaultListModel<String> modelCustom = new DefaultListModel<String>();

        AnimalManagerImpl managerAnim = new AnimalManagerImpl(dataSource);
        myAnimals = new ArrayList<>(managerAnim.findAllAnimals());

        CustomerManagerImpl managerCustom = new CustomerManagerImpl(dataSource);
        myCustomers = new ArrayList<>(managerCustom.findAllCustomers());

        for (Animal animal : myAnimals) {
            String itemCode = animal.toString(); 
            modelAnim.addElement(itemCode); 
        }
        listAnimal.setModel(modelAnim);

        for (Customer customer : myCustomers) {
            String itemCode = customer.toString(); 
            modelCustom.addElement(itemCode); 
        }
        listCustomer.setModel(modelCustom);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelDateAdoption = new javax.swing.JLabel();
        labelDateReturn = new javax.swing.JLabel();
        labelDay = new javax.swing.JLabel();
        labelMonth = new javax.swing.JLabel();
        labelYear = new javax.swing.JLabel();
        buttonClear = new javax.swing.JButton();
        comboDateAdoptionDay = new javax.swing.JComboBox();
        comboDateAdoptionMonth = new javax.swing.JComboBox();
        comboDateReturnDay = new javax.swing.JComboBox();
        comboDateAdoptionYear = new javax.swing.JComboBox();
        comboDateReturnMonth = new javax.swing.JComboBox();
        comboDateReturnYear = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listAnimal = new javax.swing.JList();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listCustomer = new javax.swing.JList();
        buttonOK = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, LanguageWizard.getString("Dates")
            , javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Consolas", 1, 12))); // NOI18N
    jPanel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("Main/resources/Bundle"); // NOI18N
    labelDateAdoption.setText(bundle.getString("DATE OF ADOPTION:")); // NOI18N

    labelDateReturn.setText(bundle.getString("DATE OF RETURN:")); // NOI18N

    labelDay.setText(bundle.getString("DAY:")); // NOI18N

    labelMonth.setText(bundle.getString("MONTH:")); // NOI18N

    labelYear.setText(bundle.getString("YEAR:")); // NOI18N

    buttonClear.setLabel(bundle.getString("CLEAR")); // NOI18N
    buttonClear.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            buttonClearActionPerformed(evt);
        }
    });

    comboDateAdoptionDay.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

    comboDateAdoptionMonth.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

    comboDateReturnDay.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

    comboDateAdoptionYear.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

    comboDateReturnMonth.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

    comboDateReturnYear.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(28, 28, 28)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(labelDateAdoption)
                        .addComponent(labelDateReturn))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(27, 27, 27)
                            .addComponent(labelDay)
                            .addGap(34, 34, 34)
                            .addComponent(labelMonth))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(comboDateReturnDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(comboDateAdoptionDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(comboDateReturnMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(comboDateAdoptionMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(comboDateReturnYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(comboDateAdoptionYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(buttonClear))))
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(275, 275, 275)
                    .addComponent(labelYear)))
            .addContainerGap(17, Short.MAX_VALUE))
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(labelDay)
                .addComponent(labelMonth)
                .addComponent(labelYear))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comboDateAdoptionDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelDateAdoption))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelDateReturn)
                        .addComponent(comboDateReturnDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(comboDateAdoptionYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comboDateReturnYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonClear)))
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(comboDateAdoptionMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(comboDateReturnMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(21, Short.MAX_VALUE))
    );

    jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, LanguageWizard.getString("Animal"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Consolas", 1, 12))); // NOI18N

    listAnimal.setModel(new javax.swing.AbstractListModel() {
        String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
        public int getSize() { return strings.length; }
        public Object getElementAt(int i) { return strings[i]; }
    });
    jScrollPane1.setViewportView(listAnimal);

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
            .addGap(0, 0, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, LanguageWizard.getString("Customer")
        , javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Consolas", 1, 12))); // NOI18N

listCustomer.setModel(new javax.swing.AbstractListModel() {
    String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
    public int getSize() { return strings.length; }
    public Object getElementAt(int i) { return strings[i]; }
    });
    jScrollPane2.setViewportView(listCustomer);

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
    );
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
            .addGap(0, 0, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    buttonOK.setLabel(bundle.getString("OK")); // NOI18N
    buttonOK.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            buttonOKActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(buttonOK, javax.swing.GroupLayout.Alignment.TRAILING))
            .addContainerGap())
    );

    layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jPanel1, jPanel2, jPanel3});

    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(buttonOK)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jPanel2, jPanel3});

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonClearActionPerformed
        resetAndUnselectCombos();
    }//GEN-LAST:event_buttonClearActionPerformed

    private void buttonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOKActionPerformed
        
        Adoption adoption = this.adoption;
        String formerAdoption = this.adoption.toString();
        adoption = getAdoption(adoption);
        if (adoption != null) {
            AdoptionManagerImpl manager = new AdoptionManagerImpl(dataSource);
            manager.updateAdoption(adoption);
            logger.log(Level.INFO, "Adoption [{0}] updated to: {1}; formerly was {2}", new Object[]{String.valueOf(adoption.getAdoptionID()), adoption, formerAdoption});
            this.dispose();
        }
    }//GEN-LAST:event_buttonOKActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(AdoptionEditUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdoptionEditUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdoptionEditUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdoptionEditUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdoptionEditUI().setVisible(true);
            }
        });
    }
    
    private void setToAdopt(Adoption adopt) {
        
        Date dateA = adopt.getDateOfAdoption();
        DateTime dateTimeA; //jodaTime ^^
        dateTimeA = new DateTime(dateA);
        int currentYear = Integer.parseInt(String.valueOf(comboDateAdoptionYear.getItemAt(0)));
        
        comboDateAdoptionDay.setSelectedIndex(dateTimeA.getDayOfMonth()-1);
        comboDateAdoptionMonth.setSelectedIndex(dateTimeA.getMonthOfYear()-1);
        comboDateAdoptionYear.setSelectedIndex(currentYear - dateTimeA.getYear());
        
        Date dateR = adopt.getDateOfReturn();
        if (dateR == null) {
            comboDateReturnDay.setSelectedIndex(-1);
            comboDateReturnMonth.setSelectedIndex(-1);
            comboDateReturnYear.setSelectedIndex(-1);
        } else {
            DateTime dateTimeR; //jodaTime ^^
            dateTimeR = new DateTime(dateR);

            comboDateReturnDay.setSelectedIndex(dateTimeR.getDayOfMonth()-1);
            comboDateReturnMonth.setSelectedIndex(dateTimeR.getMonthOfYear()-1);
            comboDateReturnYear.setSelectedIndex(currentYear - dateTimeR.getYear());
        }
        
        boolean scrollIntoView = true;
        
        String animal = adopt.getAnimal().toString();
        listAnimal.setSelectedValue(animal, scrollIntoView);
        
        String customer = adopt.getCustomer().toString();
        listCustomer.setSelectedValue(customer, scrollIntoView);
    }
    
    private void resetAndUnselectCombos() {
        comboDateReturnYear.setSelectedIndex(-1);
        comboDateReturnMonth.setSelectedIndex(-1);
        comboDateReturnDay.setSelectedIndex(-1);

        comboDateAdoptionYear.setSelectedIndex(0);
        comboDateAdoptionMonth.setSelectedIndex(0);
        comboDateAdoptionDay.setSelectedIndex(0);
    }
    
    private void setCombos() {
        comboDateAdoptionYear.setModel(new AnimalUI.DateModel());
        comboDateAdoptionYear.setSelectedIndex(0);

        comboDateReturnYear.setModel(new AnimalUI.DateModel());
        comboDateReturnYear.setSelectedIndex(-1);

        comboDateAdoptionMonth.setModel(new AdoptionUI.MonthModel());
        comboDateAdoptionMonth.setSelectedIndex(0);

        comboDateReturnMonth.setModel(new AdoptionUI.MonthModel());
        comboDateReturnMonth.setSelectedIndex(-1);

        comboDateAdoptionDay.setModel(new AdoptionUI.DayModel());
        comboDateAdoptionDay.setSelectedIndex(0);

        comboDateReturnDay.setModel(new AdoptionUI.DayModel());
        comboDateReturnDay.setSelectedIndex(-1);
    }
    
    
    
    
    private long getID(String string) {

        String stringID = string.substring(string.indexOf("[")+1, string.lastIndexOf("]"));
        Long id = (long) Integer.parseInt(stringID);

        return id;
    }
    
    private boolean isCurrentlyAdopted(Animal animal){
        AdoptionManagerImpl manag = new AdoptionManagerImpl(dataSource);
        List<Adoption> allAdoptions = manag.findAllAdoptionsOfAnimal(animal);
        
        for(Adoption adoption : allAdoptions){
            if (adoption.getDateOfReturn() == null)
                return true;
        }
        return false;
    }

    private boolean isDateValid(Date date) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(date);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

     public final boolean isInvalidFormat(final String date) {
        String[] formatStrings = {"dd/MM/yyyy"};
        boolean isInvalidFormat = false;
        Date dateObj;
        for (String formatString : formatStrings) {
            try {
                SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getDateInstance();
                sdf.applyPattern(formatString);
                sdf.setLenient(false);
                dateObj = sdf.parse(date);

                if (date.equals(sdf.format(dateObj))) {
                    isInvalidFormat = false;
                    break;
                }
            } catch (ParseException e) {
                isInvalidFormat = true;
            }
        }
        return isInvalidFormat;
    }
    

    private Date formDate(String day, String month, String year) {
        String date;
        Date result = null;
        if (Integer.parseInt(day) < 10) {
            date = "0" + day;
        } else {
            date = day;
        }
        if (Integer.parseInt(month) < 10) {
            date += "/0" + month;
        } else {
            date += "/" + month;
        }
        date += "/" + year;

        if (!isInvalidFormat(date)) {
            try {
                result = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            } catch (ParseException ex) {
                return null;
            }
        }
        return result;
    }


    public Adoption getAdoption(Adoption adoption) {

        if (listAnimal.getSelectedValue() == null || listCustomer.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(new JFrame(LanguageWizard.getString("Message")), LanguageWizard.getString("Please_select_an_animal_and_customer"));
        } else {

            AnimalManagerImpl managerAnim = new AnimalManagerImpl(dataSource);
            CustomerManagerImpl managerCustom = new CustomerManagerImpl(dataSource);
            AdoptionManagerImpl managerAdopt = new AdoptionManagerImpl(dataSource);

            Animal animal;
            Animal formerAnimal = adoption.getAnimal();
            Customer customer;

            String selectedA = listAnimal.getSelectedValue().toString();
            animal = managerAnim.getAnimalByID(getID(selectedA));
            adoption.setAnimal(animal);
            
            if (!formerAnimal.equals(animal) && isCurrentlyAdopted(animal)) {
                JOptionPane.showMessageDialog(new JFrame(LanguageWizard.getString("Message")), LanguageWizard.getString("Animal_currently_adopted"));
                return null;
            }

            String selectedC = listCustomer.getSelectedValue().toString();
            customer = managerCustom.getCustomerByID(getID(selectedC));
            adoption.setCustomer(customer);

            String day = String.valueOf(comboDateAdoptionDay.getSelectedItem());
            String month = String.valueOf(comboDateAdoptionMonth.getSelectedItem());
            String year = String.valueOf(comboDateAdoptionYear.getSelectedItem());

            Date date1 = formDate(day, month, year);

            if (date1 == null) {
                JOptionPane.showMessageDialog(new JFrame(LanguageWizard.getString("Message")), LanguageWizard.getString("Invalid_date_adoption"));
            } else {
                if (isDateValid(date1)) {
                    adoption.setDateOfAdoption(date1);

                    if (comboDateReturnDay.getSelectedItem() == null && comboDateReturnMonth.getSelectedItem() == null && comboDateReturnYear.getSelectedItem() == null) {
                        adoption.setDateOfReturn(null);
                    } else {
                        if (comboDateReturnDay.getSelectedItem() == null || comboDateReturnMonth.getSelectedItem() == null || comboDateReturnYear.getSelectedItem() == null) {
                            JOptionPane.showMessageDialog(new JFrame(LanguageWizard.getString("Message")), LanguageWizard.getString("Date_return_null_set"));
                            return null;
                        } else {

                            String dayR = String.valueOf(comboDateReturnDay.getSelectedItem());
                            String monthR = String.valueOf(comboDateReturnMonth.getSelectedItem());
                            String yearR = String.valueOf(comboDateReturnYear.getSelectedItem());

                            Date date2 = formDate(dayR, monthR, yearR);
                            if (date2 == null) {
                                JOptionPane.showMessageDialog(new JFrame(LanguageWizard.getString("Message")), LanguageWizard.getString("Invalid_date_return"));
                                return null;
                            } else {
                                if (isDateValid(date2)) {
                                    adoption.setDateOfReturn(date2);
                                } else {
                                    JOptionPane.showMessageDialog(new JFrame(LanguageWizard.getString("Message")), LanguageWizard.getString("Invalid_date_return"));
                                    return null;
                                }
                                
                                if (date2.before(date1)) {
                                    JOptionPane.showMessageDialog(new JFrame(LanguageWizard.getString("Message")), LanguageWizard.getString("Invalid_date_before"));
                                    return null;
                                }

                                if (adoption == null) {
                                    JOptionPane.showMessageDialog(new JFrame(LanguageWizard.getString("Message")), LanguageWizard.getString("can_NOT_be_null")); // ??
                                    throw new IllegalArgumentException(LanguageWizard.getString("can_NOT_be_null"));
                                }
                            }
                        }
                    }

                    managerAdopt.updateAdoption(adoption);
                    return adoption;

                } else {
                    JOptionPane.showMessageDialog(new JFrame(LanguageWizard.getString("Message")), LanguageWizard.getString("Invalid_date_adoption"));
                }

            }
        }

        return null;
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonClear;
    private javax.swing.JButton buttonOK;
    private javax.swing.JComboBox comboDateAdoptionDay;
    private javax.swing.JComboBox comboDateAdoptionMonth;
    private javax.swing.JComboBox comboDateAdoptionYear;
    private javax.swing.JComboBox comboDateReturnDay;
    private javax.swing.JComboBox comboDateReturnMonth;
    private javax.swing.JComboBox comboDateReturnYear;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelDateAdoption;
    private javax.swing.JLabel labelDateReturn;
    private javax.swing.JLabel labelDay;
    private javax.swing.JLabel labelMonth;
    private javax.swing.JLabel labelYear;
    private javax.swing.JList listAnimal;
    private javax.swing.JList listCustomer;
    // End of variables declaration//GEN-END:variables
}
