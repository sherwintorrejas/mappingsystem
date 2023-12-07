/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalpage;
import CONFIG.DBCONNECTOR;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.*;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.text.*; 
import java.awt.print.*;
import java.nio.file.*;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.*;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import net.proteanit.sql.DbUtils;

public class deceasedinfoadmin extends javax.swing.JInternalFrame {
DefaultTableModel model;
private Connection con;


    /**
     * Creates new form dashabord
     */
    public deceasedinfoadmin() {
        initComponents();
         displayData();
          this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bi = (BasicInternalFrameUI) this.getUI();
        bi.setNorthPane(null);
        
    }
    
        public static int getHeightFromWidth(String imagePath, int desiredWidth) {
        try {
    
            File imageFile = new File(imagePath);
            BufferedImage image = ImageIO.read(imageFile);
          
            int originalWidth = image.getWidth();
            int originalHeight = image.getHeight();
            
    
            int newHeight = (int) ((double) desiredWidth / originalWidth * originalHeight);
            
            return newHeight;
        } catch (IOException ex) {
            System.out.println("No image found!");
        }
        
        return -1;
    }
    
    
public  ImageIcon ResizeImage(String ImagePath, byte[] pic, JLabel label) {
    ImageIcon MyImage = null;
        if(ImagePath !=null){
            MyImage = new ImageIcon(ImagePath);
        }else{
            MyImage = new ImageIcon(pic);
        }
        
    int newHeight = getHeightFromWidth(ImagePath, label.getWidth());

    Image img = MyImage.getImage();
    Image newImg = img.getScaledInstance(label.getWidth(), newHeight, Image.SCALE_SMOOTH);
    ImageIcon image = new ImageIcon(newImg);
    return image;
}

public int FileChecker(String path){
        File file = new File(path);
        String fileName = file.getName();
        
        Path filePath = Paths.get("src/images", fileName);
        boolean fileExists = Files.exists(filePath);
        
        if (fileExists) {
            return 1;
        } else {
            return 0;
        }
    
    }
    
  public void reset(){
      EN.setText("");
   ELN.setText("");

   }
    public void search(String str){
    model = (DefaultTableModel) DECEASEDDET.getModel();
    TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
    DECEASEDDET.setRowSorter(trs);
    trs.setRowFilter(RowFilter.regexFilter(str));
    }
    
      public void displayData(){
        try{
       
            DBCONNECTOR dbc = new DBCONNECTOR();
            ResultSet rs = dbc.getData("SELECT * FROM registered_deceased");
            DECEASEDDET.setModel(DbUtils.resultSetToTableModel(rs));
       DefaultTableModel model = (DefaultTableModel) DECEASEDDET.getModel();
  String[] columnIdentifiers = {"RECORD ID", "LOCATION", "NAME", "LASTNAME","BORN DATE","DEATH DATE"};
    model.setColumnIdentifiers(columnIdentifiers);
    
        }catch(SQLException ex){
            System.out.println("Error Message: "+ex);
       
        }
    
     }
    public void filltable () throws SQLException{
        try{
            DBCONNECTOR dbc = new DBCONNECTOR();
            ResultSet rs = dbc.getData("SELECT * FROM registered_deceased");
            DECEASEDDET.setModel(DbUtils.resultSetToTableModel(rs));
            
        }catch(SQLException ex){
         System.out.println("Error Message: "+ex);
       
        
        }
    
    }
    
     public boolean validation(){
  String name= EN.getText();
String lastname= ELN.getText();
 String BORN= DB.getText();
String DEATH= DD.getText();
String LOCATION= GL.getText();
 if (name.equals("")){
 JOptionPane.showMessageDialog(this, "PLEASE ENTER NAME");
 return false;
 }
 if(lastname.equals("")){
 JOptionPane.showMessageDialog(this, "PLEASE ENTER LASTNAME");
 return false;
 }       
   if(BORN.equals("")){
 JOptionPane.showMessageDialog(this, "PLEASE ENTER DATE BORN");
 return false;
 } 
     if(DEATH.equals("")){
 JOptionPane.showMessageDialog(this, "PLEASE ENTER DEATH DATE");
 return false;
 } 
       if(LOCATION.equals("")){
 JOptionPane.showMessageDialog(this, "PLEASE ENTER LOCATION");
 return false;
 } 
  
   return true;  
 }
     public void update(){
         int result=0;
         try {
         con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cemeterymapping", "root", "");
         int row = DECEASEDDET.getSelectedRow();
         String value = (DECEASEDDET.getModel().getValueAt(row, 0).toString());
         String sql = "UPDATE registered_deceased SET NAME=?, LASTNAME=?, BORN_DATE=?, DEATH_DATE=?, GRAVE_ID =? where RECORD_ID="+value;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, EN.getText());
            ps.setString(2, ELN.getText());
            ps.setString(3, DB.getText());
            ps.setString(4, DD.getText());
            ps.setString(5, GL.getText());
          
         
            ps.execute();
          
            JOptionPane.showMessageDialog(this, "UPDATED SUCCESSFULLY");
            }catch(Exception e){
                System.err.println("Cannot connect to database: " + e.getMessage());
     
     
     }
         
     }
     
  
     /*public void table(){
     int row = DECEASEDDET.getSelectedRow();
     int cc = DECEASEDDET.getSelectedColumn();
     String tc = DECEASEDDET.getModel().getValueAt(row, 0).toString();
             try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/cemeterymapping", "root", "");
             String sql = "select * from registered_deceased where RECORD_ID="+tc+"";
             PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
            int id=rs.getInt("RECORD_ID");
             int Gid=rs.getInt("GRAVE_ID");
            String name=rs.getString("NAME");
            String lname=rs.getString("LASTNAME");
            String BDTE=rs.getString("BORN_DATE");
            String DDTE=rs.getString("DEATH_DATE");
           
            }
             ps.close();
             rs.close();
         } catch (Exception e) {
         JOptionPane.showMessageDialog(null, e);
         }
        }*/
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        EN = new app.bolivia.swing.JCTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        ELN = new app.bolivia.swing.JCTextField();
        jLabel5 = new javax.swing.JLabel();
        DELETE = new necesario.RSMaterialButtonCircle();
        UPDATE = new necesario.RSMaterialButtonCircle();
        jLabel20 = new javax.swing.JLabel();
        line2 = new javax.swing.JLabel();
        line3 = new javax.swing.JLabel();
        line4 = new javax.swing.JLabel();
        DB = new app.bolivia.swing.JCTextField();
        jLabel8 = new javax.swing.JLabel();
        line5 = new javax.swing.JLabel();
        GL = new app.bolivia.swing.JCTextField();
        jLabel9 = new javax.swing.JLabel();
        line6 = new javax.swing.JLabel();
        DD = new app.bolivia.swing.JCTextField();
        jLabel2 = new javax.swing.JLabel();
        search = new app.bolivia.swing.JCTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        DECEASEDDET = new rojeru_san.complementos.RSTableMetro();
        rSMaterialButtonCircle2 = new necesario.RSMaterialButtonCircle();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 58, 140));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBackground(new java.awt.Color(204, 0, 0));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EN.setBackground(new java.awt.Color(204, 0, 0));
        EN.setBorder(null);
        EN.setForeground(new java.awt.Color(153, 255, 153));
        EN.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        EN.setFont(new java.awt.Font("Sylfaen", 1, 15)); // NOI18N
        EN.setOpaque(false);
        EN.setPlaceholder("ENTER NAME");
        EN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ENKeyReleased(evt);
            }
        });
        jPanel10.add(EN, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 180, -1));

        jLabel3.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("NAME:");
        jPanel10.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 120, 32));

        jLabel4.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("LASTNAME:");
        jPanel10.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 120, 32));

        ELN.setBackground(new java.awt.Color(204, 0, 0));
        ELN.setBorder(null);
        ELN.setForeground(new java.awt.Color(153, 255, 153));
        ELN.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ELN.setFont(new java.awt.Font("Sylfaen", 1, 15)); // NOI18N
        ELN.setOpaque(false);
        ELN.setPlaceholder("ENTER LASTNAME");
        ELN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ELNKeyReleased(evt);
            }
        });
        jPanel10.add(ELN, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 180, -1));

        jLabel5.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("GRAVE ID:");
        jPanel10.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 210, 32));

        DELETE.setText("DELETE");
        DELETE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DELETEActionPerformed(evt);
            }
        });
        jPanel10.add(DELETE, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 460, 100, 50));

        UPDATE.setText("UPDATE");
        UPDATE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UPDATEActionPerformed(evt);
            }
        });
        jPanel10.add(UPDATE, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 400, 100, 50));

        jLabel20.setForeground(new java.awt.Color(25, 20, 20));
        jLabel20.setText("_______________________________");
        jPanel10.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 60, 210, 140));

        line2.setForeground(new java.awt.Color(25, 20, 20));
        line2.setText("__________________________");
        line2.setToolTipText("");
        jPanel10.add(line2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 200, 30));

        line3.setForeground(new java.awt.Color(25, 20, 20));
        line3.setText("__________________________");
        line3.setToolTipText("");
        jPanel10.add(line3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 200, 30));

        line4.setForeground(new java.awt.Color(25, 20, 20));
        line4.setText("__________________________");
        line4.setToolTipText("");
        jPanel10.add(line4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 200, 20));

        DB.setBackground(new java.awt.Color(204, 0, 0));
        DB.setBorder(null);
        DB.setForeground(new java.awt.Color(153, 255, 153));
        DB.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        DB.setFont(new java.awt.Font("Sylfaen", 1, 15)); // NOI18N
        DB.setOpaque(false);
        DB.setPlaceholder("ENTER DATE BORN");
        DB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DBActionPerformed(evt);
            }
        });
        DB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                DBKeyReleased(evt);
            }
        });
        jPanel10.add(DB, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 180, -1));

        jLabel8.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("DATE BORN:");
        jPanel10.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, 120, 32));

        line5.setForeground(new java.awt.Color(25, 20, 20));
        line5.setText("__________________________");
        line5.setToolTipText("");
        jPanel10.add(line5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, 200, 30));

        GL.setBackground(new java.awt.Color(204, 0, 0));
        GL.setBorder(null);
        GL.setForeground(new java.awt.Color(153, 255, 153));
        GL.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        GL.setFont(new java.awt.Font("Sylfaen", 1, 15)); // NOI18N
        GL.setOpaque(false);
        GL.setPlaceholder("ENTER LOCATION");
        GL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                GLKeyReleased(evt);
            }
        });
        jPanel10.add(GL, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 180, -1));

        jLabel9.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("DEATH DATE:");
        jPanel10.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 160, 32));

        line6.setForeground(new java.awt.Color(25, 20, 20));
        line6.setText("__________________________");
        line6.setToolTipText("");
        jPanel10.add(line6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 200, 20));

        DD.setBackground(new java.awt.Color(204, 0, 0));
        DD.setBorder(null);
        DD.setForeground(new java.awt.Color(153, 255, 153));
        DD.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        DD.setFont(new java.awt.Font("Sylfaen", 1, 15)); // NOI18N
        DD.setOpaque(false);
        DD.setPlaceholder("ENTER DEATH DATE");
        DD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                DDKeyReleased(evt);
            }
        });
        jPanel10.add(DD, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 180, -1));

        jPanel1.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 540));

        jLabel2.setFont(new java.awt.Font("Sylfaen", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("DECEASED INFO");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 500, 60));

        search.setBorder(null);
        search.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        search.setFont(new java.awt.Font("Sylfaen", 1, 12)); // NOI18N
        search.setPlaceholder("SEARCH");
        search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchKeyReleased(evt);
            }
        });
        jPanel1.add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 340, 20));

        DECEASEDDET.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME", "LASTNAME", "DATE BORN", "DEATH DATE", "LOCATION"
            }
        ));
        DECEASEDDET.setFont(new java.awt.Font("Yu Gothic UI", 1, 10)); // NOI18N
        DECEASEDDET.setFuenteFilas(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        DECEASEDDET.setFuenteFilasSelect(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        DECEASEDDET.setFuenteHead(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        DECEASEDDET.setGrosorBordeFilas(0);
        DECEASEDDET.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DECEASEDDETMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(DECEASEDDET);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, 650, 420));

        rSMaterialButtonCircle2.setBackground(new java.awt.Color(204, 0, 0));
        rSMaterialButtonCircle2.setText("PRINT");
        rSMaterialButtonCircle2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle2ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonCircle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 70, 110, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 540));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void UPDATEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UPDATEActionPerformed
      if(validation()== true){
     update();
        }
      
    }//GEN-LAST:event_UPDATEActionPerformed

    private void DELETEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DELETEActionPerformed
     int rowIndex = DECEASEDDET.getSelectedRow();
       if(rowIndex < 0){
           JOptionPane.showMessageDialog(null, "Please select a data first");
       }else{
            TableModel model = DECEASEDDET.getModel();
            Object value = model.getValueAt(rowIndex, 0);
            String ID = value.toString();
             int a=JOptionPane.showConfirmDialog(null,"Are you sure?");  
                    if(a==JOptionPane.YES_OPTION){  
                            DBCONNECTOR dbc = new DBCONNECTOR();
                            dbc.deleteData(Integer.parseInt(ID));
                            displayData();
                            reset();
                            JOptionPane.showMessageDialog(this, "DELETED SUCCESSFULL");
                    }    
       }
    }//GEN-LAST:event_DELETEActionPerformed

    private void searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyReleased
      String seachst = search.getText();
        search(seachst);
    }//GEN-LAST:event_searchKeyReleased

    private void DECEASEDDETMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DECEASEDDETMouseClicked
            int rowIndex = DECEASEDDET.getSelectedRow();
        if(rowIndex <0){
        
        }else{
        TableModel model = DECEASEDDET.getModel();

        EN.setText(""+model.getValueAt(rowIndex, 2));
        ELN.setText(""+model.getValueAt(rowIndex, 3));
        DB.setText(""+model.getValueAt(rowIndex, 4));
        DD.setText(""+model.getValueAt(rowIndex, 5));
        GL.setText(""+model.getValueAt(rowIndex, 1));
        }
//table();
    }//GEN-LAST:event_DECEASEDDETMouseClicked

    private void rSMaterialButtonCircle2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle2ActionPerformed
        MessageFormat head = new MessageFormat("DECEASED");
        MessageFormat FOOT = new MessageFormat("Page{0, number , integer}");

        try {
            DECEASEDDET.print(JTable.PrintMode.NORMAL, head, FOOT);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, "cannot print");
        }
    }//GEN-LAST:event_rSMaterialButtonCircle2ActionPerformed

    private void ELNKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ELNKeyReleased
       int pos =  ELN.getCaretPosition();
         ELN.setText( ELN.getText().toUpperCase());
         ELN.setCaretPosition(pos);
        
    }//GEN-LAST:event_ELNKeyReleased

    private void ENKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ENKeyReleased
        int pos =  EN.getCaretPosition();
         EN.setText( EN.getText().toUpperCase());
         EN.setCaretPosition(pos);
        
    }//GEN-LAST:event_ENKeyReleased

    private void DBKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DBKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_DBKeyReleased

    private void GLKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GLKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_GLKeyReleased

    private void DDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DDKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_DDKeyReleased

    private void DBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DBActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.bolivia.swing.JCTextField DB;
    private app.bolivia.swing.JCTextField DD;
    private rojeru_san.complementos.RSTableMetro DECEASEDDET;
    private necesario.RSMaterialButtonCircle DELETE;
    private app.bolivia.swing.JCTextField ELN;
    private app.bolivia.swing.JCTextField EN;
    private app.bolivia.swing.JCTextField GL;
    private necesario.RSMaterialButtonCircle UPDATE;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel line2;
    private javax.swing.JLabel line3;
    private javax.swing.JLabel line4;
    private javax.swing.JLabel line5;
    private javax.swing.JLabel line6;
    private necesario.RSMaterialButtonCircle rSMaterialButtonCircle2;
    private app.bolivia.swing.JCTextField search;
    // End of variables declaration//GEN-END:variables

}
