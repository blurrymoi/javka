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
import animalcentre.Gender;
import static animalcentre.Gender.FEMALE;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.sql.DataSource;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.UIManager;

/**
 *
 * @author blurry
 */
public class AnimalUI extends javax.swing.JDialog {
    
    private DataSource dataSource;
    private static Logger logger;
    //private FileHandler fh;
    private List<Animal> myAnimals = new ArrayList<>();
    
    /**
     * Creates new form NewJDialog
     */ 
    public AnimalUI(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    AnimalUI(FileHandler fh, DataSource dataSource) {

        logger = Logger.getLogger(AnimCentrUI.class.getName());
        //logger.setUseParentHandlers(false);
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        
        this.dataSource = dataSource;
        initComponents();
        
        comboBoxYear.setModel(new DateModel());
        comboBoxYear.setSelectedIndex(0);
        
        UIManager.put("OptionPane.yesButtonText", LanguageWizard.getString("yes"));
        UIManager.put("OptionPane.noButtonText", LanguageWizard.getString("no"));
        
        try {
            populateJList(jListAnimals, dataSource);
        } catch (SQLException ex) {
            Logger.getLogger(AnimalUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // for logging uncaught exceptions
                Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        logger.log(Level.SEVERE, "Uncaught Exception in thread '" + t.getName() + "'", e);
                        System.exit(1);
                    }
                }); 
    }
    
    
    static class DateModel extends DefaultComboBoxModel {
        
        List<Integer> comboBoxItemList = new ArrayList<Integer>();
        
        public DateModel() {
            int startYear = Calendar.getInstance().get(Calendar.YEAR);
            for (int i = startYear; i > 1850; i--) {
                comboBoxItemList.add(i);
            }
        }
   
        @Override
        public Object getElementAt(int index) {
            return comboBoxItemList.get(index);
        }
        
        @Override
        public int getSize() {
            return comboBoxItemList.size();
        }
    }
    
    
    private void populateJList(JList list, DataSource dataSource) throws SQLException {
        
        DefaultListModel model = new DefaultListModel(); //create a new list model

        AnimalManagerImpl manager = new AnimalManagerImpl(dataSource);
        myAnimals = new ArrayList<>(manager.findAllAnimals());

        for (Animal animal : myAnimals) {
            String itemCode = animal.toString(); //get the element in column "item_code"
            model.addElement(itemCode); //add each item to the model
        }
        list.setModel(model);
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
        labelName = new javax.swing.JLabel();
        textFieldName = new javax.swing.JTextField();
        labelYear = new javax.swing.JLabel();
        labelGender = new javax.swing.JLabel();
        comboBoxGender = new javax.swing.JComboBox();
        comboBoxYear = new javax.swing.JComboBox();
        jCheckBoxNeutered = new javax.swing.JCheckBox();
        labelNeutered = new javax.swing.JLabel();
        buttonAdd = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListAnimals = new javax.swing.JList();
        buttonEdit = new javax.swing.JButton();
        buttonRemove = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, LanguageWizard.getString("Animal"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Consolas", 1, 12))); // NOI18N
        jPanel1.setToolTipText("null");

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("Main/resources/Bundle"); // NOI18N
        labelName.setText(bundle.getString("NAME:")); // NOI18N

        labelYear.setText(bundle.getString("YEAR OF BIRTH:")); // NOI18N

        labelGender.setText(bundle.getString("GENDER:")); // NOI18N

        comboBoxGender.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "FEMALE", "MALE" }));
        comboBoxGender.setSelectedItem(FEMALE);
        comboBoxGender.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", LanguageWizard.getString("FEMALE"), LanguageWizard.getString("MALE")}));

        comboBoxYear.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2015", "2014", "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005", "2004", "2003", "2002", "2001", "2000", "1999", "1998", "1997", "1996", "1995", "1994", "1993", "1992", "1991", "1990", "1989", "1988", "1987", "1986", "1985" }));

        labelNeutered.setText(bundle.getString("NEUTERED:")); // NOI18N

        buttonAdd.setText(bundle.getString("ADD")); // NOI18N
        buttonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddActionPerformed(evt);
            }
        });

        jListAnimals.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jListAnimals);

        buttonEdit.setText(bundle.getString("EDIT")); // NOI18N
        buttonEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditActionPerformed(evt);
            }
        });

        buttonRemove.setText(bundle.getString("REMOVE")); // NOI18N
        buttonRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelYear)
                            .addComponent(labelName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelGender, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelNeutered, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jCheckBoxNeutered)
                            .addComponent(comboBoxYear, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboBoxGender, 0, 165, Short.MAX_VALUE)
                            .addComponent(textFieldName)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonRemove, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelName)
                    .addComponent(textFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelYear)
                    .addComponent(comboBoxYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBoxGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelGender))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxNeutered)
                    .addComponent(labelNeutered))
                .addGap(12, 12, 12)
                .addComponent(buttonAdd)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(buttonEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonRemove)
                        .addContainerGap(172, Short.MAX_VALUE))
                    .addComponent(jScrollPane2)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void buttonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddActionPerformed
        getAnimal();

        //refresh list
        if (evt.getSource() == buttonAdd) {
            DefaultListModel listModel = (DefaultListModel) jListAnimals.getModel();
            listModel.removeAllElements();
            try {
                populateJList(jListAnimals, dataSource);
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }

        textFieldName.setText("");
        comboBoxGender.setSelectedIndex(0);
        comboBoxYear.setSelectedIndex(0);
        //setVisible(false);
    }//GEN-LAST:event_buttonAddActionPerformed

    private void buttonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEditActionPerformed
        if (jListAnimals.getSelectedValue() != null) {

            String selected = jListAnimals.getSelectedValue().toString();
            String subs = selected.substring(selected.indexOf("[") + 1, selected.lastIndexOf("]"));
            Long id = (long) Integer.parseInt(subs);

            AnimalManagerImpl manag = new AnimalManagerImpl(dataSource);
            Animal anim = manag.getAnimalByID(id);

            AnimalEditUI animEdit = new AnimalEditUI(anim, dataSource, logger);
            animEdit.setVisible(true);
            animEdit.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            //will refresh the Animal list once the "Edit animals" window is closed
            animEdit.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosed(WindowEvent e) {

                    DefaultListModel listModel = (DefaultListModel) jListAnimals.getModel();
                    listModel.removeAllElements();
                    try {
                        populateJList(jListAnimals, dataSource);
                    } catch (SQLException ex) {
                        logger.log(Level.SEVERE, "Could not populate Animal list", ex);
                    }
                }
            });
        }
    }//GEN-LAST:event_buttonEditActionPerformed

    private void buttonRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRemoveActionPerformed
        if (jListAnimals.getSelectedValue() != null) {

            String selected = jListAnimals.getSelectedValue().toString();
            String subs = selected.substring(selected.indexOf("[")+1, selected.lastIndexOf("]"));
            Long id = (long) Integer.parseInt(subs);
            

            AnimalManagerImpl manag = new AnimalManagerImpl(dataSource);
            Animal anim = manag.getAnimalByID(id);
            
            if (isCurrentlyAdopted(anim)) {
                JOptionPane.showMessageDialog(new JFrame(LanguageWizard.getString("Message")), LanguageWizard.getString("Currently_adopted"));
            } else {

                int reply = JOptionPane.showConfirmDialog(null, java.util.ResourceBundle.getBundle("Main/resources/Bundle").getString("ARE_YOU_SURE_YOU_WANT_TO_REMOVE") + anim.getName() + "?", LanguageWizard.getString("Confirm"), JOptionPane.YES_NO_OPTION);

                if (reply == JOptionPane.YES_OPTION) {

                    manag.deleteAnimal(anim);
                    logger.log(Level.INFO, "Animal removed: {0}", anim);

                    if (evt.getSource() == buttonRemove) {
                        DefaultListModel listModel = (DefaultListModel) jListAnimals.getModel();
                        listModel.removeAllElements();
                        try {
                            populateJList(jListAnimals, dataSource);
                        } catch (SQLException ex) {
                            logger.log(Level.SEVERE, "Could not populate list in AnimalUI", ex);
                        }
                    }

                }
            }
        }
    }//GEN-LAST:event_buttonRemoveActionPerformed

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
            java.util.logging.Logger.getLogger(AnimalUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AnimalUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AnimalUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnimalUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AnimalUI dialog = new AnimalUI(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    
    
    private boolean isCurrentlyAdopted(Animal animal){
        AdoptionManagerImpl manag = new AdoptionManagerImpl(dataSource);
        List<Adoption> allAdoptions = manag.findAllAdoptionsOfAnimal(animal);
        
        return !allAdoptions.isEmpty();
    }
    
    public Animal getAnimal() {

        AnimalManagerImpl manager = new AnimalManagerImpl(dataSource);
        Animal animal = new Animal();

        Pattern p = Pattern.compile("[A-Za-z]?[a-zěěšřžýáíéťúäôůňóĺŕ ]*\\d*");        
        String name = textFieldName.getText();
        
        if (name.equals("")) {
            JOptionPane.showMessageDialog(new JFrame(LanguageWizard.getString("Message")), LanguageWizard.getString("You_must_set_the_name"));
            //throw new IllegalArgumentException("hello there from AnimalUI"); 
        } else {
            if (!p.matcher(name).matches()) {
                JOptionPane.showMessageDialog(new JFrame(LanguageWizard.getString("Message")), LanguageWizard.getString("Pattern_not_match"));
            } else {
            
            animal.setName(textFieldName.getText());

            if (comboBoxYear.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(new JFrame(LanguageWizard.getString("Message")), LanguageWizard.getString("Select_date"));
            } else {
                String yr = String.valueOf(comboBoxYear.getSelectedItem());
                animal.setYearOfBirth(Integer.parseInt(yr));

                int gender = comboBoxGender.getSelectedIndex();
                switch (gender) {
                    case 0:
                        animal.setGender(null);
                        break;
                    case 1:
                        animal.setGender(Gender.FEMALE);
                        break;
                    case 2:
                        animal.setGender(Gender.MALE);
                        break;
                    default:
                        throw new IllegalArgumentException("Illegal index in Gender");
                }

                animal.setNeutered(jCheckBoxNeutered.isSelected());

                if (animal == null) {
                    JOptionPane.showMessageDialog(new JFrame(LanguageWizard.getString("Message")), LanguageWizard.getString("can_NOT_be_null")); // ??
                    throw new IllegalArgumentException(LanguageWizard.getString("can_NOT_be_null"));
                }

                manager.createAnimal(animal);
                logger.log(Level.INFO, "Animal added: {0}", animal);
                return animal;
            }
            }
        }
        return null;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAdd;
    private javax.swing.JButton buttonEdit;
    private javax.swing.JButton buttonRemove;
    private javax.swing.JComboBox comboBoxGender;
    private javax.swing.JComboBox comboBoxYear;
    private javax.swing.JCheckBox jCheckBoxNeutered;
    private javax.swing.JList jListAnimals;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelGender;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelNeutered;
    private javax.swing.JLabel labelYear;
    private javax.swing.JTextField textFieldName;
    // End of variables declaration//GEN-END:variables
}
