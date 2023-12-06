/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalpage;
import CONFIG.DBCONNECTOR;
import com.sun.javafx.collections.IntegerArraySyncer;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.beans.*;
import javax.swing.*;
import java.text.*; 
import java.awt.print.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import net.proteanit.sql.DbUtils;
public class registerdeceased extends javax.swing.JInternalFrame {
private Connection connection;
    /**
     * Creates new form dashabord
     */
    public registerdeceased() {
        initComponents();
        
gslip();
          this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bi = (BasicInternalFrameUI) this.getUI();
        bi.setNorthPane(null);
    }
  private void gravedet(){
 int bookid= Integer.parseInt(G_ID.getText());
 try{
  connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cemeterymapping", "root", "");
  PreparedStatement pst = connection.prepareStatement("SELECT * FROM grave where GRAVE_ID =?");
  pst.setInt(1, bookid);
  ResultSet rs = pst.executeQuery();
  
  if(rs.next()){
  GRAVE.setText(rs.getString("GRAVE_ID"));
  SECTION.setText(rs.getString("SECTION_NO"));
  QUANT.setText(rs.getString("SPACE"));
   GRAVE_ERROR.setText("");
  }else{
  GRAVE_ERROR.setText("INVALID ID");
  }
 }catch (SQLException ex){
 ex.printStackTrace();
 
 }
 }
 //get student details 
 public void gslip(){

 slip.setText("*****************************************************\n");
 slip.setText(slip.getText()+"-------------------RECIPT--------------------\n");
 slip.setText(slip.getText()+"*****************************************************\n");
 
 Date date = new Date();  // or replace with your own date object

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Replace with your desired format
        String formattedDate = dateFormat.format(date);
 

  slip.setText(slip.getText()+"\nDATE: "+formattedDate+"\n");
 slip.setText(slip.getText()+"\nGRAVE ID: "+G_ID.getText()+"\n");
 slip.setText(slip.getText()+"\nSECTION NO: "+SECTION.getText()+"\n");
 slip.setText(slip.getText()+"\nNAME: "+ NAME.getText()+"\n");
 slip.setText(slip.getText()+"\nLASTNAME: "+ LASTNAME.getText()+"\n");

 
 }
    //print
 public void print(){
 
     try {
         slip.print();
     } catch (Exception e) {
     JOptionPane.showMessageDialog(null, "print failed"+e);
     }
 
 
 
 
 }
         
         
 //issue book
 public boolean issuebook(){
     boolean isissued = false;
 int graveid= Integer.parseInt(GRAVE.getText());
 String nme=NAME.getText();
String lme=LASTNAME.getText();
  java.util.Date uissueddate= BDATE.getDate();
  java.util.Date uduedate=DDATE.getDate();
  
  Long l1= uissueddate.getTime();
  long l2=uduedate.getTime();
  
  java.sql.Date sissuebdate = new java.sql.Date(l1);
  java.sql.Date sddate = new java.sql.Date(l2);
  
     try {
     connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cemeterymapping", "root", "");
     String sql = "insert into registered_deceased( GRAVE_ID,NAME,LASTNAME,BORN_DATE,DEATH_DATE) values(?,?,?,?,?)";
     
     PreparedStatement pst = connection.prepareStatement(sql);
  pst.setInt(1, graveid);
  pst.setString(2, nme);
  pst.setString(3, lme);
  pst.setDate(4, sissuebdate);
  pst.setDate(5, sddate);

  
 
  int rowCount= pst.executeUpdate();
  if(rowCount > 0){
   isissued = true;
  }else{
  isissued = false;
  }

     } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
     
     }
  return isissued;
 }

 //UPDATE BOOK QUANT
 
 public void updatebookquant(){
 int bookid= Integer.parseInt(G_ID.getText());
     try {
          connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cemeterymapping", "root", "");
          String sql = "update grave set SPACE = SPACE - 1 where GRAVE_ID =?";
          PreparedStatement pst = connection.prepareStatement(sql);
          pst.setInt(1, bookid);
          
        int rowcount = pst.executeUpdate();
          
          if(rowcount>0){
          JOptionPane.showMessageDialog(this, "GRAVE QUANTITY UPDATED");
          int initialcount = Integer.parseInt(QUANT.getText());
          QUANT.setText(Integer.toString(initialcount - 1));
          }else{
          JOptionPane.showMessageDialog(this, "GRAVE QUANTITY FAILED TO UPDATED");
          }
     } catch (Exception e) {
        System.out.println(e);
         e.printStackTrace();
         
     }
 }
 
 //CHECK IF BOOK IS ISSUED OR NOT
 public boolean alreadyissued(){
 
 boolean alreadyREGISTERED = false;
  int bookid= Integer.parseInt(G_ID.getText());
  String sid= NAME.getText();
  
     try {
          connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cemeterymapping", "root", "");
          String sql = "select * from registered_deceased where GRAVE_ID = ? and RECORD_ID = ?";
          PreparedStatement pst = connection.prepareStatement(sql);
          
        pst.setInt(1, bookid);
        pst.setString(2, sid);
 
          
            ResultSet rs = pst.executeQuery();
        if(rs.next()){
        alreadyREGISTERED = true;
        }else{
        alreadyREGISTERED = false;
        }
        
        
     } catch (Exception e) {
         System.out.println(e);
         e.printStackTrace();
     }
 return alreadyREGISTERED;
 }
  public boolean validation(){
String isb = G_ID.getText();
String NME = NAME.getText();
String LME = LASTNAME.getText();
 if (isb.equals("")){
 JOptionPane.showMessageDialog(this, "PLEASE ENTER GRAVE ID");
 return false;
 }
 if(NME.equals("")){
 JOptionPane.showMessageDialog(this, "PLEASE ENTER NAME");
 return false;
 }    
if(NME.equals("")){
 JOptionPane.showMessageDialog(this, "PLEASE ENTER LASTNAME");
 return false;
 }   
 if(BDATE.getDate() == null){
 JOptionPane.showMessageDialog(this, "PLEASE ENTER BORN DATE");
 return false;
 }    
 if(DDATE.getDate() == null){
 JOptionPane.showMessageDialog(this, "PLEASE ENTER DEATH DATE");
 return false;
 }  
   return true;  
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
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        GRAVE = new javax.swing.JLabel();
        SECTION = new javax.swing.JLabel();
        QUANT = new javax.swing.JLabel();
        GRAVE_ERROR = new javax.swing.JLabel();
        G_ID = new app.bolivia.swing.JCTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        DDATE = new com.toedter.calendar.JDateChooser();
        jLabel18 = new javax.swing.JLabel();
        BDATE = new com.toedter.calendar.JDateChooser();
        NAME = new app.bolivia.swing.JCTextField();
        rSMaterialButtonCircle1 = new necesario.RSMaterialButtonCircle();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        slip = new javax.swing.JTextArea();
        print = new rojerusan.RSMaterialButtonCircle();
        LASTNAME = new app.bolivia.swing.JCTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(0, 58, 140));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 102, 0));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Sylfaen", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("GRAVE INFO");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 220, 40));

        jLabel9.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("GRAVE ID:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, 110, 30));

        jLabel10.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("SECTION NO.:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, 170, 30));

        jLabel16.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("SPACE:");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, 170, 30));

        GRAVE.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        GRAVE.setForeground(new java.awt.Color(102, 255, 102));
        GRAVE.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel2.add(GRAVE, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, 220, 30));

        SECTION.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        SECTION.setForeground(new java.awt.Color(102, 255, 102));
        SECTION.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel2.add(SECTION, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 260, 220, 30));

        QUANT.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        QUANT.setForeground(new java.awt.Color(102, 255, 102));
        QUANT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel2.add(QUANT, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, 220, 30));

        GRAVE_ERROR.setFont(new java.awt.Font("Yu Gothic UI", 1, 13)); // NOI18N
        GRAVE_ERROR.setForeground(new java.awt.Color(255, 0, 0));
        GRAVE_ERROR.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel2.add(GRAVE_ERROR, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 250, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 370, 540));

        G_ID.setBackground(new java.awt.Color(0, 58, 140));
        G_ID.setBorder(null);
        G_ID.setForeground(new java.awt.Color(153, 255, 153));
        G_ID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        G_ID.setFont(new java.awt.Font("Sylfaen", 1, 15)); // NOI18N
        G_ID.setOpaque(false);
        G_ID.setPlaceholder("ENTER GRAVE ID");
        G_ID.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                G_IDFocusLost(evt);
            }
        });
        G_ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G_IDActionPerformed(evt);
            }
        });
        G_ID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G_IDKeyPressed(evt);
            }
        });
        jPanel1.add(G_ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 120, -1, -1));

        jLabel13.setFont(new java.awt.Font("Sylfaen", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("        REGISTER DECEASED");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, 420, 80));

        jLabel14.setFont(new java.awt.Font("Sylfaen", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("GRAVE ID:");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 90, 90, 30));

        jLabel15.setFont(new java.awt.Font("Sylfaen", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("NAME:");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 110, 40));

        jLabel17.setFont(new java.awt.Font("Sylfaen", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("BORN DATE:");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 180, 140, 30));

        DDATE.setDateFormatString("MM/ dd/ yy");
        DDATE.setFont(new java.awt.Font("Sylfaen", 1, 14)); // NOI18N
        jPanel1.add(DDATE, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 280, 220, 30));

        jLabel18.setFont(new java.awt.Font("Sylfaen", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("DEATH DATE:");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 250, 140, 30));

        BDATE.setDateFormatString("MM/ dd/ yy");
        BDATE.setFont(new java.awt.Font("Sylfaen", 1, 14)); // NOI18N
        jPanel1.add(BDATE, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 210, 220, 30));

        NAME.setBackground(new java.awt.Color(0, 58, 140));
        NAME.setBorder(null);
        NAME.setForeground(new java.awt.Color(153, 255, 153));
        NAME.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        NAME.setFont(new java.awt.Font("Sylfaen", 1, 15)); // NOI18N
        NAME.setOpaque(false);
        NAME.setPlaceholder("ENTER NAME");
        NAME.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                NAMEFocusLost(evt);
            }
        });
        NAME.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NAMEActionPerformed(evt);
            }
        });
        NAME.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NAMEKeyPressed(evt);
            }
        });
        jPanel1.add(NAME, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 120, -1, -1));

        rSMaterialButtonCircle1.setBackground(new java.awt.Color(153, 0, 0));
        rSMaterialButtonCircle1.setText("REGISTER");
        rSMaterialButtonCircle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle1ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonCircle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 320, 230, 40));

        jLabel19.setForeground(new java.awt.Color(25, 20, 20));
        jLabel19.setText("_______________________________");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 130, 210, 40));

        jLabel20.setForeground(new java.awt.Color(25, 20, 20));
        jLabel20.setText("_______________________________");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 130, 210, 40));

        slip.setColumns(20);
        slip.setRows(5);
        jScrollPane1.setViewportView(slip);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 380, 440, 130));

        print.setBackground(new java.awt.Color(153, 0, 0));
        print.setText("print");
        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });
        jPanel1.add(print, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 320, 230, 40));

        LASTNAME.setBackground(new java.awt.Color(0, 58, 140));
        LASTNAME.setBorder(null);
        LASTNAME.setForeground(new java.awt.Color(153, 255, 153));
        LASTNAME.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        LASTNAME.setFont(new java.awt.Font("Sylfaen", 1, 15)); // NOI18N
        LASTNAME.setOpaque(false);
        LASTNAME.setPlaceholder("ENTER LASTNAME");
        LASTNAME.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                LASTNAMEFocusLost(evt);
            }
        });
        LASTNAME.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LASTNAMEKeyPressed(evt);
            }
        });
        jPanel1.add(LASTNAME, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 250, -1, -1));

        jLabel21.setForeground(new java.awt.Color(25, 20, 20));
        jLabel21.setText("_______________________________");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 260, 210, 40));

        jLabel22.setFont(new java.awt.Font("Sylfaen", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("LASTNAME");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 200, 110, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 870, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rSMaterialButtonCircle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle1ActionPerformed
      if(validation()==true){
          if(QUANT.getText().equals("0")){
        JOptionPane.showMessageDialog(this, "GRAVE IS FULL");
    }else{
           issuebook();   
        JOptionPane.showMessageDialog(this, "REGISTERED SUCCESSFULLY");
            gslip();
        updatebookquant();
      }
      }
    }//GEN-LAST:event_rSMaterialButtonCircle1ActionPerformed

    private void G_IDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_G_IDFocusLost
       if(!G_ID.getText().equals("")){
       gravedet();
       }
    }//GEN-LAST:event_G_IDFocusLost

    private void NAMEFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NAMEFocusLost
   
    }//GEN-LAST:event_NAMEFocusLost

    private void G_IDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G_IDKeyPressed
               char c =evt.getKeyChar();
        if(Character.isLetter(c)){
        G_ID.setEditable(false);
        JOptionPane.showMessageDialog(this, "Please enter number only");
        }else{
        G_ID.setEditable(true);
        }
    }//GEN-LAST:event_G_IDKeyPressed

    private void NAMEKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NAMEKeyPressed
             int pos = NAME.getCaretPosition();
        NAME.setText(NAME.getText().toUpperCase());
        NAME.setCaretPosition(pos);
        
    }//GEN-LAST:event_NAMEKeyPressed

    private void printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printActionPerformed
        print();
    }//GEN-LAST:event_printActionPerformed

    private void G_IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G_IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_G_IDActionPerformed

    private void LASTNAMEFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_LASTNAMEFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_LASTNAMEFocusLost

    private void LASTNAMEKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LASTNAMEKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LASTNAMEKeyPressed

    private void NAMEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NAMEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NAMEActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser BDATE;
    private com.toedter.calendar.JDateChooser DDATE;
    private javax.swing.JLabel GRAVE;
    private javax.swing.JLabel GRAVE_ERROR;
    private app.bolivia.swing.JCTextField G_ID;
    private app.bolivia.swing.JCTextField LASTNAME;
    private static app.bolivia.swing.JCTextField NAME;
    private javax.swing.JLabel QUANT;
    private javax.swing.JLabel SECTION;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private rojerusan.RSMaterialButtonCircle print;
    private necesario.RSMaterialButtonCircle rSMaterialButtonCircle1;
    private javax.swing.JTextArea slip;
    // End of variables declaration//GEN-END:variables
}
