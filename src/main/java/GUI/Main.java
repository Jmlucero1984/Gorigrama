/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import GUI.Bundles.BundleMng;
import Utils.SerializedNormal;
import com.jml.gorigrama.GorigramaEntity;
import com.jml.gorigrama.RepositorioPalabras;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import Utils.Pair;

import Utils.WordItem;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.github.weisj.jsvg.util.ImageUtil;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Component;

import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import java.awt.image.ColorModel;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JMenuItem;

import javax.swing.JOptionPane;

import javax.swing.JPopupMenu;

/**
 *
 * @author jmlucero
 */
public class Main extends javax.swing.JFrame {

    Graphics2D gr;

    private final int ANCHO_CELDA = 32, ALTO_CELDA = 32;
    private int CELDAS_H = 25, CELDAS_V = 15;   // 48 x 27 
    private int OFFSET_Y = 12, OFFSET_X = 12;
    private final int NOM_WIDTH = 1032, NOM_HEIGHT = 668;
    private int FONT_SIZE_NUM = 10, FONT_SIZE_LETTER = 11;
    private int CHAR_LEFT_MARGIN = 10;
    private int CHAR_TOP_MARGIN = 10;
    private int NUM_LEFT_MARGIN = 10;
    private int NUM_TOP_MARGIN = 10;
    GorigramaEntity crucigramaInstance;
    JPopupMenu jpm = new JPopupMenu("ContexMenu");
    ResourceBundle bundle;
    private Integer[][] nums;

    boolean banWord = false;

    String title = " GORIGRAMAS - Desde 2023 (prácticamente desde ayer :-P) creando crucigramas para suegras       ";

    List<Pair<Integer, String>> horizPairsList, vertiPairList;

    BufferedImage bufferedImg;
    Executor resetStatusText = CompletableFuture.delayedExecutor(15, TimeUnit.MILLISECONDS);
    Method method;
    Class<?> scraperClass;

    // Now you can interact with the loaded class and its methods
    public Main() throws IOException {
        try {

            //  bundle= BundleMng.getBundle();
            // bundle = BundleMng.getBundle();
            initComponents();

            RepositorioPalabras.load();

            FONT_SIZE_NUM = (int) Math.round(ALTO_CELDA * 0.32);
            FONT_SIZE_LETTER = (int) Math.round(ALTO_CELDA * 0.40);
            NUM_LEFT_MARGIN = (int) (ANCHO_CELDA * 0.1);
            NUM_TOP_MARGIN = (int) (ALTO_CELDA * 0.05);
            CHAR_LEFT_MARGIN = (int) Math.round(ANCHO_CELDA - FONT_SIZE_LETTER) / 2;
            CHAR_TOP_MARGIN = (int) Math.round(ALTO_CELDA - FONT_SIZE_LETTER) / 2;
            crearBufferAndGraphics();
            Image image = Toolkit.getDefaultToolkit().getImage("src/main/resources/imgs/icon_small.png");
            ImageIcon icon = new ImageIcon(image);
            setIconImage(image);
            TimerTask tasknew = new TimerTask() {
                @Override
                public void run() {
                    setTitle();
                }
            };
            Timer timer = new Timer(true);
            timer.scheduleAtFixedRate(tasknew, 0, 200);
            File file = new File("src/DefinitionScrapper.jar");
            URL[] urls = {file.toURI().toURL()};
            URLClassLoader childClassLoader = new URLClassLoader(urls, ClassLoader.getSystemClassLoader());
            scraperClass = Class.forName("com.jmlucero.definitionscrapper.DefinitionScrapper", true, childClassLoader);
            // Get the protected addURL method from the parent URLClassLoader class
            method = scraperClass.getDeclaredMethod("getDefinition", String.class);
            jpm.setLightWeightPopupEnabled(false);
            //mainPanel.setComponentPopupMenu(jpm);

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        // loadInternatStrings();
        loadLocaleStrings();
    }

    public void loadLocaleStrings() {
        JButton[] buttons = {this.defsBtn, this.drawGridBtn, this.generateBtn, this.loadFromRemoteBtn,
            this.loadLocalBtn, this.saveImageBtn, this.saveToRemoteBtn, this.setDefsBtn, this.showLettersBtn,
            this.showNumbersBtn, this.updateBtn, this.wordToolsBtn, this.saveLocalBtn};
        BundleMng.getStrings(Arrays.asList(buttons));

    }

    public void setCeldas_V(int celdas) {
        CELDAS_V = celdas;
    }

    public void setCeldas_H(int celdas) {
        CELDAS_H = celdas;
    }

    public void setTitle() {
        char letter;
        letter = title.charAt(0);
        title = title.substring(1).concat(String.valueOf(letter));
        this.setTitle(title);
    }

    private void crearBufferAndGraphics() {
        bufferedImg = (BufferedImage) mainPanel.createImage(mainPanel.getWidth(), mainPanel.getHeight());
        gr = (Graphics2D) bufferedImg.getGraphics(); //mainPanel.getGraphics();
    }

    private void inicializar() throws IOException {
        OFFSET_Y = (NOM_HEIGHT - CELDAS_V * ALTO_CELDA) / 2;
        OFFSET_X = (NOM_WIDTH - CELDAS_H * ANCHO_CELDA) / 2;
        crucigramaInstance = new GorigramaEntity(CELDAS_V, CELDAS_H, OFFSET_Y, OFFSET_X);
        crucigramaInstance.generar();
        crearBufferAndGraphics();
        drawGridBW();
        placeNumbers();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        mainContainer = new javax.swing.JPanel();
        leftPanel = new javax.swing.JPanel();
        generateBtn = new javax.swing.JButton();
        drawGridBtn = new javax.swing.JButton();
        showNumbersBtn = new javax.swing.JButton();
        showLettersBtn = new javax.swing.JButton();
        setDefsBtn = new javax.swing.JButton();
        defsBtn = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        saveImageBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        loadLocalBtn = new javax.swing.JButton();
        saveLocalBtn = new javax.swing.JButton();
        saveToRemoteBtn = new javax.swing.JButton();
        loadFromRemoteBtn = new javax.swing.JButton();
        wordToolsBtn = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        rightPanel = new javax.swing.JPanel();
        mainPanel = new javax.swing.JPanel();

        jPopupMenu1.setDoubleBuffered(true);
        jPopupMenu1.setLightWeightPopupEnabled(false);

        jMenu1.setText("jMenu1");

        jMenuItem1.setText("jMenuItem1");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("jMenuItem2");
        jMenu1.add(jMenuItem2);

        jPopupMenu1.add(jMenu1);

        jMenuItem3.setText("jMenuItem3");
        jPopupMenu1.add(jMenuItem3);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(java.awt.Color.white);
        setMaximumSize(new java.awt.Dimension(1250, 750));
        setMinimumSize(new java.awt.Dimension(1250, 750));
        setPreferredSize(new java.awt.Dimension(1060, 725));
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        mainContainer.setLayout(new javax.swing.BoxLayout(mainContainer, javax.swing.BoxLayout.LINE_AXIS));

        leftPanel.setMaximumSize(new java.awt.Dimension(150, 32767));
        leftPanel.setMinimumSize(new java.awt.Dimension(150, 800));
        leftPanel.setPreferredSize(new java.awt.Dimension(130, 700));
        leftPanel.setLayout(new java.awt.GridLayout(0, 1));

        generateBtn.setText("Generate");
        generateBtn.setMinimumSize(new java.awt.Dimension(150, 20));
        generateBtn.setPreferredSize(new java.awt.Dimension(150, 15));
        generateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateBtnWords(evt);
            }
        });
        leftPanel.add(generateBtn);

        drawGridBtn.setText("Draw Grid");
        drawGridBtn.setMinimumSize(new java.awt.Dimension(150, 20));
        drawGridBtn.setPreferredSize(new java.awt.Dimension(150, 15));
        drawGridBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drawGrid(evt);
            }
        });
        leftPanel.add(drawGridBtn);

        showNumbersBtn.setText("Place Numbers");
        showNumbersBtn.setMinimumSize(new java.awt.Dimension(150, 20));
        showNumbersBtn.setPreferredSize(new java.awt.Dimension(150, 15));
        showNumbersBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drawNumbers(evt);
            }
        });
        leftPanel.add(showNumbersBtn);

        showLettersBtn.setText("Show Letters");
        showLettersBtn.setMinimumSize(new java.awt.Dimension(150, 20));
        showLettersBtn.setPreferredSize(new java.awt.Dimension(150, 15));
        showLettersBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drawLetters(evt);
            }
        });
        leftPanel.add(showLettersBtn);

        setDefsBtn.setText("Set Defs");
        setDefsBtn.setMinimumSize(new java.awt.Dimension(150, 20));
        setDefsBtn.setPreferredSize(new java.awt.Dimension(150, 15));
        setDefsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generarDefs(evt);
            }
        });
        leftPanel.add(setDefsBtn);

        defsBtn.setText("Edit Defs");
        defsBtn.setMinimumSize(new java.awt.Dimension(150, 20));
        defsBtn.setPreferredSize(new java.awt.Dimension(150, 15));
        defsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editDefs(evt);
            }
        });
        leftPanel.add(defsBtn);

        jSeparator1.setPreferredSize(new java.awt.Dimension(150, 15));
        leftPanel.add(jSeparator1);

        saveImageBtn.setText("Save jpg");
        saveImageBtn.setMinimumSize(new java.awt.Dimension(150, 20));
        saveImageBtn.setPreferredSize(new java.awt.Dimension(150, 15));
        saveImageBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveImage(evt);
            }
        });
        leftPanel.add(saveImageBtn);

        updateBtn.setText("Print PDF");
        updateBtn.setMinimumSize(new java.awt.Dimension(150, 20));
        updateBtn.setPreferredSize(new java.awt.Dimension(150, 15));
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update(evt);
            }
        });
        leftPanel.add(updateBtn);

        jSeparator2.setMaximumSize(new java.awt.Dimension(130, 10));
        jSeparator2.setPreferredSize(new java.awt.Dimension(150, 15));
        leftPanel.add(jSeparator2);

        loadLocalBtn.setText("Load Local");
        loadLocalBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadLocalBtnActionPerformed(evt);
            }
        });
        leftPanel.add(loadLocalBtn);

        saveLocalBtn.setText("Save Local");
        saveLocalBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveLocalBtnActionPerformed(evt);
            }
        });
        leftPanel.add(saveLocalBtn);

        saveToRemoteBtn.setText("Save DB");
        saveToRemoteBtn.setMinimumSize(new java.awt.Dimension(150, 20));
        saveToRemoteBtn.setPreferredSize(new java.awt.Dimension(150, 15));
        saveToRemoteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveToRemoteBtn(evt);
            }
        });
        leftPanel.add(saveToRemoteBtn);

        loadFromRemoteBtn.setText("Load DB");
        loadFromRemoteBtn.setMinimumSize(new java.awt.Dimension(150, 20));
        loadFromRemoteBtn.setPreferredSize(new java.awt.Dimension(150, 15));
        loadFromRemoteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadFromRemoteBtn(evt);
            }
        });
        leftPanel.add(loadFromRemoteBtn);

        wordToolsBtn.setText("Word Tool");
        wordToolsBtn.setMinimumSize(new java.awt.Dimension(150, 20));
        wordToolsBtn.setPreferredSize(new java.awt.Dimension(150, 15));
        wordToolsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wordToolsBtnsaveToJson(evt);
            }
        });
        leftPanel.add(wordToolsBtn);

        jSeparator3.setPreferredSize(new java.awt.Dimension(150, 15));
        leftPanel.add(jSeparator3);

        mainContainer.add(leftPanel);

        rightPanel.setPreferredSize(new java.awt.Dimension(1120, 674));
        rightPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                rightPanelMouseReleased(evt);
            }
        });

        mainPanel.setForeground(new java.awt.Color(153, 51, 0));
        mainPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mainPanel.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        mainPanel.setInheritsPopupMenu(true);
        mainPanel.setMaximumSize(new java.awt.Dimension(1100, 750));
        mainPanel.setMinimumSize(new java.awt.Dimension(1008, 650));
        mainPanel.setPreferredSize(new java.awt.Dimension(1032, 668));
        mainPanel.setRequestFocusEnabled(false);
        mainPanel.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                mainPanelAncestorMoved(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        mainPanel.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
                mainPanelAncestorResized(evt);
            }
        });
        mainPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mainPanelMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                mainPanelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1032, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 668, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout rightPanelLayout = new javax.swing.GroupLayout(rightPanel);
        rightPanel.setLayout(rightPanelLayout);
        rightPanelLayout.setHorizontalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        rightPanelLayout.setVerticalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainContainer.add(rightPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(mainContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(mainContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void drawGrid(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drawGrid
        drawGridBW();
    }//GEN-LAST:event_drawGrid
    public void drawGridBW() {
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, mainPanel.getWidth(), mainPanel.getHeight());
        gr.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(2f);
        int ancho = CELDAS_H * ANCHO_CELDA;
        int alto = CELDAS_V * ALTO_CELDA;
        gr.setStroke(stroke);
        for (int j = 0; j <= CELDAS_H; j++) {
            gr.drawLine(j * ANCHO_CELDA + OFFSET_X, OFFSET_Y, j * ANCHO_CELDA + OFFSET_X, alto + OFFSET_Y);
        }
        for (int j = 0; j <= CELDAS_V; j++) {
            gr.drawLine(0 + OFFSET_X, j * ALTO_CELDA + OFFSET_Y, ancho + OFFSET_X, j * ALTO_CELDA + OFFSET_Y);
        }
        mainPanel.getGraphics().drawImage(bufferedImg, 0, 0, null);
    }
    private void drawNumbers(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drawNumbers
        placeNumbers();

    }//GEN-LAST:event_drawNumbers
    public void placeNumbers() {
        try {
            if (crucigramaInstance == null) {
                JOptionPane.showMessageDialog(mainPanel, "No se ha inicialiado ningun crucigrama", "Atención", JOptionPane.WARNING_MESSAGE);
            } else {
                nums = crucigramaInstance.generarNumeros();
                String[][] lettersH = crucigramaInstance.getHorizontales();
                String[][] lettersV = crucigramaInstance.getVerticales();

                System.out.println("ALTO : " + lettersH.length);
                System.out.println("ANCHO : " + lettersH[0].length);

                gr.setFont(new Font("TimesRoman", Font.BOLD, FONT_SIZE_NUM));
                for (int i = 0; i < nums[0].length; i++) {
                    for (int j = 0; j < nums.length; j++) {
                        if (lettersH[j][i].equals(" ") && lettersV[j][i].equals(" ")) {
                            //  &&lettersV[i][j].equals(" ")
                            gr.fillRect(i * ANCHO_CELDA + OFFSET_X, j * ALTO_CELDA + OFFSET_Y, ANCHO_CELDA, ALTO_CELDA);
                        }
                        if (nums[j][i] != 0) {
                            gr.setFont(new Font("TimesRoman", Font.BOLD, FONT_SIZE_NUM));
                            gr.drawString(String.valueOf(nums[j][i]), i * ANCHO_CELDA + OFFSET_X + NUM_LEFT_MARGIN, j * ALTO_CELDA + OFFSET_Y + FONT_SIZE_NUM + NUM_TOP_MARGIN);
                        }
                    }
                }
                mainPanel.getGraphics().drawImage(bufferedImg, 0, 0, null);
            }
        } catch (HeadlessException e) {

        }
    }

    public byte[] convertImageToByteArray(BufferedImage image, String format) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            // Write the BufferedImage to the ByteArrayOutputStream in the specified format
            ImageIO.write(image, format, byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get the byte array from the ByteArrayOutputStream
        return byteArrayOutputStream.toByteArray();
    }
    private void drawLetters(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drawLetters
        gr.setFont(new Font("TimesRoman", Font.BOLD, FONT_SIZE_LETTER));
        String[][] lettersH = crucigramaInstance.getHorizontales();
        String[][] lettersV = crucigramaInstance.getVerticales();
        for (int i = 0; i < lettersH[0].length; i++) {
            for (int j = 0; j < lettersH.length; j++) {
                if (lettersH[j][i].equals(" ") && lettersV[j][i].equals(" ")) {
                    gr.fillRect(i * ANCHO_CELDA + OFFSET_X, j * ALTO_CELDA + OFFSET_Y, ANCHO_CELDA, ALTO_CELDA);
                } else {
                    gr.drawString(lettersH[j][i], i * ANCHO_CELDA + OFFSET_X + CHAR_LEFT_MARGIN, j * ALTO_CELDA + OFFSET_Y + FONT_SIZE_LETTER + CHAR_TOP_MARGIN);
                    gr.drawString(lettersV[j][i], i * ANCHO_CELDA + OFFSET_X + CHAR_LEFT_MARGIN, j * ALTO_CELDA + OFFSET_Y + FONT_SIZE_LETTER + CHAR_TOP_MARGIN);
                }
            }
        }
        mainPanel.getGraphics().drawImage(bufferedImg, 0, 0, null);
    }//GEN-LAST:event_drawLetters

    private void editDefs(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editDefs
        Definitions def = new Definitions(crucigramaInstance);
        def.setLocationRelativeTo(this);
        def.setVisible(true);
    }//GEN-LAST:event_editDefs

    private void saveImage(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveImage

        if (crucigramaInstance != null) {
            IOFile saveFile = new IOFile(".jpeg", "Imagen del crucigrama", (boolean success, File file) -> {
                System.out.println(success);
                if (success) {
                    try {
                        System.out.println("FILE.ABS.PATH: " + file.getAbsolutePath());
                        ImageIO.write(bufferedImg, "jpg", new File(file.getParent() + "\\" + Utils.FixFileName.fixFileName(file, "jpg")));
                    } catch (IOException ex) {
                        System.out.println("EXCEPTION E: " + ex);
                    }
                }
            }, IOFile.TipoDialog.SAVE);
            saveFile.setLocationRelativeTo(this);
            saveFile.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(mainPanel, "No hay ningun crucigrama para guardar", "Atención", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_saveImage

    private void generateBtnWords(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateBtnWords
        /* try {
            inicializar();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        SetDimsPopUp setDims = new SetDimsPopUp(this, CELDAS_H, CELDAS_V);
        setDims.setLocationRelativeTo(this);
        setDims.setVisible(true);
    }//GEN-LAST:event_generateBtnWords
    public void generar() {
        try {
            inicializar();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void update(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update
        //update();
        /* BufferedImage bfToPrint;
        bfToPrint = bufferedImg.getSubimage(OFFSET_X, OFFSET_Y, ANCHO_CELDA * CELDAS_H, ALTO_CELDA * CELDAS_V);
        TextWrapper tw = new TextWrapper(bfToPrint, crucigramaInstance);

        tw.setLocationRelativeTo(this);
        tw.setVisible(true);*/
        saveToPDF();
    }//GEN-LAST:event_update

    private void update() {
        horizPairsList = new ArrayList();
        vertiPairList = new ArrayList();
        crucigramaInstance.refillVerticalWords(false);
        drawGrid(null);
        drawNumbers(null);
        drawLetters(null);
    }
    private void saveToRemoteBtn(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveToRemoteBtn
        try {
            SerializedNormal.saveSerializedNormal(crucigramaInstance);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_saveToRemoteBtn

    private void generarDefs(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generarDefs
        crucigramaInstance.generarDefiniciones();
    }//GEN-LAST:event_generarDefs

    private void loadFromRemoteBtn(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadFromRemoteBtn

        IOFile loadFile = new IOFile(".slzd", "Crucigrama Serializado", (boolean success, File file) -> {
            System.out.println(success);
            if (success) {
                try {
                    System.out.println("FILE.ABS.PATH: " + file.getAbsolutePath());
                    crucigramaInstance = SerializedNormal.loadSerialized(file);
                } catch (IOException ex) {
                    System.out.println("EXCEPTION E: " + ex);
                }
            }
        }, IOFile.TipoDialog.OPEN);
        loadFile.setLocationRelativeTo(this);
        loadFile.setVisible(true);
    }//GEN-LAST:event_loadFromRemoteBtn

    private void wordToolsBtnsaveToJson(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wordToolsBtnsaveToJson
        WordTool wt = new WordTool(crucigramaInstance);
        wt.setLocationRelativeTo(this);
        wt.setVisible(true);
    }//GEN-LAST:event_wordToolsBtnsaveToJson

    public void drawCross(Graphics2D gr, int xx, int yy) {
        Stroke stroke = new BasicStroke(5f);
        gr.setColor(Color.RED);
        gr.drawLine(xx - 10, yy - 10, xx + 10, yy + 10);
        gr.drawLine(xx - 10, yy + 10, xx + 10, yy - 10);
    }

    public void drawCircle(Graphics2D gr, int xx, int yy) {
        Stroke stroke = new BasicStroke(5f);

        gr.setStroke(stroke);
        gr.setColor(Color.GREEN);
        gr.drawOval(xx - 10, yy - 10, 20, 20);
    }

    private void mainPanelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainPanelMouseReleased

        int xx = evt.getX();
        int yy = evt.getY();
        if (evt.getButton() == MouseEvent.BUTTON3) {
            System.out.println("MOUSE CLICKED BUTTON 3");
            // drawCross((Graphics2D) mainPanel.getGraphics(), evt.getX(), evt.getY());
            jpm.removeAll();
            // jpm.setOpaque(false);
            Pair<WordItem, WordItem> foundedWords = crucigramaInstance.locateWords((int) ((xx - OFFSET_X) / (ANCHO_CELDA)), (int) (yy - OFFSET_Y) / (ALTO_CELDA));
            if (foundedWords != null && foundedWords.first != null) {
                JMenuItem h = new JMenuItem(foundedWords.first.getWord());
                h.addActionListener((ActionEvent ae) -> {
                    System.out.println(foundedWords.first.getWord());
                    crucigramaInstance.removeWord(foundedWords.first);
                    update();
                });
                jpm.add(h);
            }
            if (foundedWords != null && foundedWords.second != null) {
                JMenuItem v = new JMenuItem(foundedWords.second.getWord());
                v.addActionListener((ActionEvent ae) -> {
                    System.out.println(foundedWords.second.getWord());
                    crucigramaInstance.removeWord(foundedWords.second);
                    update();
                });
                jpm.add(v);
            }
            jpm.show(mainPanel, evt.getX(), evt.getY());
        }

        if (evt.getButton() == MouseEvent.BUTTON1) {
            System.out.println("MOUSE CLICKED BUTTON 1");
            // drawCircle((Graphics2D) mainPanel.getGraphics(), evt.getX(), evt.getY());

        }

        if (evt.getButton() == 2) {
            mainPanel.getGraphics().drawImage(bufferedImg, 0, 0, null);
            crucigramaInstance.cleanStack();
        }

    }//GEN-LAST:event_mainPanelMouseReleased

    private void mainPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainPanelMouseClicked

    }//GEN-LAST:event_mainPanelMouseClicked

    private void mainPanelAncestorResized(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_mainPanelAncestorResized
        mainPanel.getGraphics().drawImage(bufferedImg, 0, 0, null);
    }//GEN-LAST:event_mainPanelAncestorResized

    private void mainPanelAncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_mainPanelAncestorMoved
        mainPanel.getGraphics().drawImage(bufferedImg, 0, 0, null);      // TODO add your handling code here:
    }//GEN-LAST:event_mainPanelAncestorMoved

    private void loadLocalBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadLocalBtnActionPerformed
        IOFile loadFile = new IOFile(".slzd", "Crucigrama Serializado", (boolean success, File file) -> {
            System.out.println(success);
            if (success) {
                try {
                    System.out.println("FILE.ABS.PATH: " + file.getAbsolutePath());
                    crucigramaInstance = SerializedNormal.loadSerialized(file);
                    this.OFFSET_X = crucigramaInstance.getOffsetx();
                    this.OFFSET_Y = crucigramaInstance.getOffsety();
                    mainPanel.getGraphics().dispose();
                    this.CELDAS_H = crucigramaInstance.getNumCeldasH();
                    this.CELDAS_V = crucigramaInstance.getNumCeldasV();

                } catch (IOException ex) {
                    System.out.println("EXCEPTION E: " + ex);
                }
            }
        }, IOFile.TipoDialog.OPEN);
        loadFile.setLocationRelativeTo(this);
        loadFile.setVisible(true);
    }//GEN-LAST:event_loadLocalBtnActionPerformed

    private void saveToPDF() {

        if (crucigramaInstance != null) {
            IOFile saveFile = new IOFile(".pdf", "PDF del crucigrama", (boolean success, File file) -> {
                System.out.println(success);
                if (success) {
                    try {
                        String definiciones = "";
                        com.itextpdf.text.Document pdfDoc = new com.itextpdf.text.Document(PageSize.A4);
                        System.out.println("FILE.ABS.PATH: " + file.getAbsolutePath());
                        File destination = new File(file.getParent() + "\\" + Utils.FixFileName.fixFileName(file, "pdf"));
                        PdfWriter.getInstance(pdfDoc, new FileOutputStream(destination))
                                .setPdfVersion(PdfWriter.PDF_VERSION_1_7);
                        pdfDoc.open();
                        com.itextpdf.text.Font myfo;

                        //FontFactory.register("src\\main\\resources\\fonts\\JetBrainsMono-Medium.ttf", "JetBrains");
                        FontFactory.register("src\\main\\resources\\fonts\\RotisSemiSerifStd.otf", "RotisSS");
                       
                        FontFactory.registerDirectory("src\\main\\resources\\fonts");
                        myfo = FontFactory.getFont("rotisss", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                        System.out.println("REGISTERED FONTS: " + FontFactory.getRegisteredFonts());
                        System.out.println("REGISTERED FAMILIES: " + FontFactory.getRegisteredFamilies());

                        //myfo.setStyle(com.itextpdf.text.Font.NORMAL);
                        
                        
                        Image titleImg =  Toolkit.getDefaultToolkit().getImage("src\\main\\resources\\imgs\\titulo.png");
                        com.itextpdf.text.Image titleImgElement =com.itextpdf.text.Image.getInstance(titleImg, null);
                         titleImgElement.setDpi(150, 150);
                        titleImgElement.scaleToFit(150, 500);
                        pdfDoc.add(titleImgElement);
                        
                        
                        
                        myfo.setSize(10);
                        
                        BufferedImage buffForPdf = bufferedImg.getSubimage(OFFSET_X, OFFSET_Y, ANCHO_CELDA * CELDAS_H, ALTO_CELDA * CELDAS_V);
                        com.itextpdf.text.Image img = new com.itextpdf.text.Jpeg(convertImageToByteArray(buffForPdf, "JPG"));
                        img.setDpi(150, 150);
                        img.scaleToFit(520, 500);
                        pdfDoc.add(img);
                        pdfDoc.add(new Paragraph("\n"));
                        definiciones += "HORIZONTALES\n ";
                        for (Pair<Integer, String> pair : crucigramaInstance.getHorizPairsList()) {
                            String def = crucigramaInstance.getDefiniciones().get(pair.second).split("\n")[0]; //Supposed to have multiple defs
                            definiciones += String.valueOf(pair.first) + "." + def + ". ";
                        }
                        definiciones += "\nVERTICALES\n ";
                        for (Pair<Integer, String> pair : crucigramaInstance.getVertiPairList()) {
                            String def = crucigramaInstance.getDefiniciones().get(pair.second).split("\n")[0];
                            definiciones += String.valueOf(pair.first) + "." + def + ". ";
                        }
                        Paragraph para = new Paragraph(definiciones, myfo);
                        para.setAlignment(com.itextpdf.text.Element.ALIGN_JUSTIFIED);
                        pdfDoc.add(para);

                        pdfDoc.close();

                    } catch (Exception ex) {
                        System.out.println("EXCEPTION E: " + ex);
                    }
                }
            }, IOFile.TipoDialog.SAVE);
            saveFile.setLocationRelativeTo(this);
            saveFile.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(mainPanel, "No hay ningun crucigrama para guardar", "Atención", JOptionPane.WARNING_MESSAGE);
        }

    }
    private void saveLocalBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveLocalBtnActionPerformed
        if (crucigramaInstance != null) {
            IOFile saveFile = new IOFile(".slzd", "Crucigrama Serializado", (boolean success, File file) -> {
                System.out.println(success);
                if (success) {
                    try {
                        System.out.println("FILE.ABS.PATH: " + file.getAbsolutePath());
                        SerializedNormal.saveSerializedNormal(crucigramaInstance, file);
                    } catch (IOException ex) {
                        System.out.println("EXCEPTION E: " + ex);
                    }
                }
            }, IOFile.TipoDialog.SAVE);
            saveFile.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(mainPanel, "No hay ningun crucigrama para guardar", "Atención", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_saveLocalBtnActionPerformed

    private void rightPanelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rightPanelMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_rightPanelMouseReleased

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased

    }//GEN-LAST:event_formMouseReleased

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
                    javax.swing.UIManager.setLookAndFeel(new FlatDarculaLaf());
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Exception " + e);
        };
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Main().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton defsBtn;
    private javax.swing.JButton drawGridBtn;
    private javax.swing.JButton generateBtn;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JButton loadFromRemoteBtn;
    private javax.swing.JButton loadLocalBtn;
    private javax.swing.JPanel mainContainer;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JButton saveImageBtn;
    private javax.swing.JButton saveLocalBtn;
    private javax.swing.JButton saveToRemoteBtn;
    private javax.swing.JButton setDefsBtn;
    private javax.swing.JButton showLettersBtn;
    private javax.swing.JButton showNumbersBtn;
    private javax.swing.JButton updateBtn;
    private javax.swing.JButton wordToolsBtn;
    // End of variables declaration//GEN-END:variables
}
