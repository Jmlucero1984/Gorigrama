/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import com.jml.gorigrama.GorigramaEntity;
import com.jml.gorigrama.RepositorioPalabras;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;

/**
 *
 * @author jmlucero
 */
public class WordTool extends javax.swing.JFrame {

    /**
     * Creates new form WordTool
     */
    GorigramaEntity ce;
    int indexOfList = 0;

    public WordTool() {
        initComponents();
        Image image = Toolkit.getDefaultToolkit().getImage("src/main/resources/imgs/icon_small.png");
        ImageIcon icon = new ImageIcon(image);
        setIconImage(image);
    }
    List<Integer> indexes;
    String defaultString = "Ingrese la palabra...";

    public WordTool(GorigramaEntity ce) {
        this();
        this.ce = ce;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    // @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        indicesDeRestriccionesField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        searchWord = new javax.swing.JButton();
        agregarWord = new javax.swing.JButton();
        searchedWordCondition = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        searchWordField = new javax.swing.JTextField();
        spacedLettersLabel = new javax.swing.JLabel();
        spacedWord = new javax.swing.JLabel();
        palabraConRestriccionesField = new javax.swing.JTextField();
        buscarPosiblesRestringidas = new javax.swing.JButton();
        resultadoBusquedaRestringida = new javax.swing.JLabel();
        removeWordBtn1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Word Tools");
        setBounds(new java.awt.Rectangle(0, 0, 400, 400));
        setMinimumSize(new java.awt.Dimension(400, 500));

        indicesDeRestriccionesField.setText("Ingrese los indices: 1,5,8, etc");

        jLabel1.setText("  Buscar:");

        searchWord.setText("Buscar");
        searchWord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchWordActionPerformed(evt);
            }
        });

        agregarWord.setText("Agregar");
        agregarWord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarWordActionPerformed(evt);
            }
        });

        searchedWordCondition.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        searchedWordCondition.setText("OPERATION STATUS");

        jLabel3.setText("Búsqueda con restricciones:");

        searchWordField.setText("Ingrese la palabra...");

        spacedLettersLabel.setFont(new java.awt.Font("JetBrains Mono NL SemiBold", 0, 13)); // NOI18N
        spacedLettersLabel.setText("0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 ");
        spacedLettersLabel.setToolTipText("");

        spacedWord.setFont(new java.awt.Font("JetBrains Mono NL SemiBold", 0, 20)); // NOI18N
        spacedWord.setText("l d f s d f s d f s d f s f s d f s d f s a b c d e f g h i j ");

        palabraConRestriccionesField.setText("Ingrese la palabra...");
        palabraConRestriccionesField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                palabraConRestriccionesFieldCaretUpdate(evt);
            }
        });
        palabraConRestriccionesField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                palabraConRestriccionesFieldMouseClicked(evt);
            }
        });
        palabraConRestriccionesField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                palabraConRestriccionesFieldActionPerformed(evt);
            }
        });

        buscarPosiblesRestringidas.setText("BUSCAR");
        buscarPosiblesRestringidas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarPosiblesRestringidasActionPerformed(evt);
            }
        });

        resultadoBusquedaRestringida.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        resultadoBusquedaRestringida.setText("posibles coincidencias...");

        removeWordBtn1.setText("Quitar");
        removeWordBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeWordBtn1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(spacedWord, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(spacedLettersLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(resultadoBusquedaRestringida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(searchWord, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(searchWordField, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(searchedWordCondition, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(agregarWord, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(removeWordBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(palabraConRestriccionesField, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(35, 35, 35)
                                    .addComponent(indicesDeRestriccionesField, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                                    .addComponent(buscarPosiblesRestringidas))))))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchedWordCondition, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchWordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchWord, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(removeWordBtn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(agregarWord, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(53, 53, 53)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buscarPosiblesRestringidas)
                    .addComponent(indicesDeRestriccionesField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(palabraConRestriccionesField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(spacedWord, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spacedLettersLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(resultadoBusquedaRestringida, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void palabraConRestriccionesFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_palabraConRestriccionesFieldCaretUpdate

        String[] chars = palabraConRestriccionesField.getText().trim().split("");
        String monoespaced = "";
        for (String char1 : chars) {
            monoespaced += char1 + " ";
        }

        spacedWord.setText(monoespaced.trim());
    }//GEN-LAST:event_palabraConRestriccionesFieldCaretUpdate

    private void palabraConRestriccionesFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_palabraConRestriccionesFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_palabraConRestriccionesFieldActionPerformed

    private void palabraConRestriccionesFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_palabraConRestriccionesFieldMouseClicked
        if (palabraConRestriccionesField.getText().equals(defaultString)) {
            palabraConRestriccionesField.setText("");
        }
    }//GEN-LAST:event_palabraConRestriccionesFieldMouseClicked

    private void buscarPosiblesRestringidasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarPosiblesRestringidasActionPerformed
        boolean founded = false;
        String restrictedWord = palabraConRestriccionesField.getText().trim();
        String[] cuts = indicesDeRestriccionesField.getText().split(",");
        if (!restrictedWord.equals("")) {
            indexes = new ArrayList();
            for (String cut : cuts) {
                try {
                    int value = Integer.parseInt(cut);
                    if (value < 30 && value < restrictedWord.length()) {
                        indexes.add(value);
                    } else {
                        System.out.println("SE OMITIO EL VALOR: " + value);
                    }

                } catch (Exception e) {
                    System.out.println("Exception e: " + e);
                }
            }
        } else {
            System.out.println("NO HAY NADA VALIDO EN EL CUADRO DE TEXTO PARA BUSCAR");
        }
        while (!founded && indexOfList < RepositorioPalabras.getPalabras().size()) {
            String actual = RepositorioPalabras.getPalabras().get(indexOfList);
            boolean goodFit = true;
            if (actual.length() % 2 == 1 && actual.length() >= restrictedWord.length()) {
                for (int i = 0; i < indexes.size(); i++) {
                    int ii = indexes.get(i);
                    if (!actual.substring(ii, ii + 1).equalsIgnoreCase(restrictedWord.substring(ii, ii + 1))) {
                        goodFit = false;
                    }
                }
                if (goodFit) {
                    founded = true;
                    System.out.println("SE ENCONTRO: " + actual);
                    resultadoBusquedaRestringida.setText(actual);
                } else {
                    System.out.println("NO SE ENCONTRO NINGUNA COINCIDENCIA");
                }
            }
            indexOfList++;
        }
    }//GEN-LAST:event_buscarPosiblesRestringidasActionPerformed

    private void agregarWordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarWordActionPerformed
        String word = searchWordField.getText().trim();
        if (!RepositorioPalabras.getPalabras().contains(word)) {
            RepositorioPalabras.getPalabras().add(word);
            try {
                RepositorioPalabras.addNewWord(word);
                RepositorioPalabras.load();
            } catch (IOException ex) {
                searchedWordCondition.setText("OCURRIÓ UN FALLO AL AGREGAR LA PALABRA AL POSTADDED");
            }
            searchedWordCondition.setText("POST ADDED SUCCESSFUL");
            Executor completableFuture = CompletableFuture.delayedExecutor(3, TimeUnit.SECONDS);

            completableFuture.execute(() -> searchedWordCondition.setText("OPERATION STATUS"));

        }
    }//GEN-LAST:event_agregarWordActionPerformed

    private void removeWordBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeWordBtn1ActionPerformed
        String word = searchWordField.getText().trim();
        if (RepositorioPalabras.getPalabras().contains(word)) {
            RepositorioPalabras.getPalabras().remove(word);
        }
        try {
            RepositorioPalabras.excludeNewBanned(word);

        } catch (IOException ex) {
            searchedWordCondition.setText("OCURRIÓ UN FALLO AL BANNEAR");
        }
        try {
            RepositorioPalabras.load();
        } catch (IOException ex) {
            searchedWordCondition.setText("OCURRIÓ UN FALLO AL RECARGAR LUEGO DEL BAN");
        }

        // CUIDADO, COMO HACEMOS CON LO QUE ESTÁ EN ALREADYTOKEN?
        searchedWordCondition.setText("BAN SUCCESSFUL");
        Executor completableFuture = CompletableFuture.delayedExecutor(3, TimeUnit.SECONDS);

        completableFuture.execute(() -> searchedWordCondition.setText("OPERATION STATUS"));
    }//GEN-LAST:event_removeWordBtn1ActionPerformed

    private void searchWordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchWordActionPerformed
        String word = searchWordField.getText().trim();
        String msg = "";
        if (RepositorioPalabras.getPalabras().contains(word)) {
            msg = "PRESENTE EN LA LISTA DE PALABRAS";
            if (ce.getAlreadyTokenWords().contains(word)) {

                msg += ", Y EN ALREADY TOKEN!!!!";
            }
        } else if (ce.getAlreadyTokenWords().contains(word)) {
            msg = "PRESENTE EN ALREADY TOKEN";
        } else {
            msg = "ESA PALABRA NO ESTA EN NINGUNA LISTA";
        }
        searchedWordCondition.setText(msg);
        Executor completableFuture = CompletableFuture.delayedExecutor(3, TimeUnit.SECONDS);

        completableFuture.execute(() -> searchedWordCondition.setText("OPERATION STATUS"));


    }//GEN-LAST:event_searchWordActionPerformed

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
            java.util.logging.Logger.getLogger(WordTool.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WordTool.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WordTool.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WordTool.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WordTool().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton agregarWord;
    private javax.swing.JButton buscarPosiblesRestringidas;
    private javax.swing.JTextField indicesDeRestriccionesField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField palabraConRestriccionesField;
    private javax.swing.JButton removeWordBtn1;
    private javax.swing.JLabel resultadoBusquedaRestringida;
    private javax.swing.JButton searchWord;
    private javax.swing.JTextField searchWordField;
    private javax.swing.JLabel searchedWordCondition;
    private javax.swing.JLabel spacedLettersLabel;
    private javax.swing.JLabel spacedWord;
    // End of variables declaration//GEN-END:variables
}
