/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Utils.FileTypeFilter;
 
import java.io.File;
import javax.swing.JFileChooser;
import Utils.ICallback;

/**
 *
 * @author jmlucero
 */
public class IOFile extends javax.swing.JFrame {
    public enum TipoDialog {
        SAVE,
        OPEN
    }
 

    ICallback callback;

    public IOFile() {
        initComponents();

     
    }
    
    public IOFile(String type, String description) {
        this();
           FileTypeFilter jsonFilter = new FileTypeFilter( type, description);
        for (javax.swing.filechooser.FileFilter choosableFileFilter : this.jFileChooser1.getChoosableFileFilters()) {
            jFileChooser1.removeChoosableFileFilter(choosableFileFilter);
        }
        this.jFileChooser1.addChoosableFileFilter(jsonFilter);
    }

    public IOFile(String type, String description, ICallback callback,TipoDialog tipoDialog ) {
        this(type,description);
        this.callback = callback;
        if(tipoDialog==TipoDialog.SAVE){
        this.jFileChooser1.setDialogType(JFileChooser.SAVE_DIALOG);
        } else {
            this.jFileChooser1.setDialogType(JFileChooser.OPEN_DIALOG); 
        }
        
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Seleccione un Crucigrama Guardado");

        jFileChooser1.setCurrentDirectory(new java.io.File("C:\\Users\\jmluc\\Documents\\Documents"));
        jFileChooser1.setDialogTitle("Seleccione un crucigrama en Json");
        jFileChooser1.setName(""); // NOI18N
        jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
        if (evt.getActionCommand().equals("ApproveSelection")) {
            File file = this.jFileChooser1.getSelectedFile();
            callback.execute(true, file);
        }
        this.dispose();

    }//GEN-LAST:event_jFileChooser1ActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IOFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IOFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IOFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IOFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
 

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IOFile().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser1;
    // End of variables declaration//GEN-END:variables
}
