/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.jml.gorigrama;

import GUI.Main;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;

import java.io.FileNotFoundException;

import java.io.IOException;

import javax.swing.UIManager;

public class Gorigrama {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        FlatLightLaf.setup(); // Dependency added to POM
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
            for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
                System.out.println(laf.getName());
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }
        Main main = new Main();
        main.setVisible(true);
    }

}
