
package internalpage;
import CONFIG.DBCONNECTOR;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.text.*; 
import java.awt.print.*;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import net.proteanit.sql.DbUtils;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;
import waypoint.mywaypoint;
import waypoint.waypointrender;
public class map extends javax.swing.JInternalFrame {

    private final Set<mywaypoint> waypoint = new HashSet<>();

    public map() {
        initComponents();
        init();
          this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bi = (BasicInternalFrameUI) this.getUI();
        bi.setNorthPane(null);
    }
  private void init(){
     TileFactoryInfo info= new OSMTileFactoryInfo();
     DefaultTileFactory tilefactory = new DefaultTileFactory(info);
     mapv.setTileFactory(tilefactory);
     GeoPosition geo = new GeoPosition(10.2508458,123.7965834);
     mapv.setAddressLocation(geo);
     mapv.setZoom(1);
     
     //mouse move
      MouseInputListener mm= new PanMouseInputListener(mapv);
      mapv.addMouseListener(mm);
      mapv.addMouseMotionListener(mm);
      mapv.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapv));
  }
   private void initwaypoint(){
       WaypointPainter<mywaypoint> wp = new waypointrender();
       wp.setWaypoints(waypoint);
       mapv.setOverlayPainter(wp);
       for(mywaypoint d: waypoint){
           mapv.add(d.getButton()); 
       } 
   }
   
   private void waypointclear(){
        for(mywaypoint d: waypoint){
           mapv.remove(d.getButton()); 
       } 
        waypoint.clear();
        initwaypoint();
        
   }
   
   
   
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jXMapViewer1 = new org.jxmapviewer.JXMapViewer();
        mapv = new org.jxmapviewer.JXMapViewer();
        mapcombo = new javax.swing.JComboBox<>();
        cmdadd = new javax.swing.JButton();
        cmdclear = new javax.swing.JButton();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mapv.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mapcombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Open Street", "Virtual Earth", "Hybrid", "Satellite", " " }));
        mapcombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mapcomboActionPerformed(evt);
            }
        });
        mapv.add(mapcombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 50, 110, -1));

        cmdadd.setText("Add Waypoints");
        cmdadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdaddActionPerformed(evt);
            }
        });
        mapv.add(cmdadd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        cmdclear.setText("Clear");
        cmdclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdclearActionPerformed(evt);
            }
        });
        mapv.add(cmdclear, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, -1, -1));

        getContentPane().add(mapv, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 540));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mapcomboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mapcomboActionPerformed
      TileFactoryInfo info;
      int index = mapcombo.getSelectedIndex();
      if(index == 0){
          info = new OSMTileFactoryInfo();
      }else if(index == 1){
          info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
      }else if(index==2){
          info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
      }else{
          info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE);
      }
      DefaultTileFactory tilefactory = new DefaultTileFactory(info);
     mapv.setTileFactory(tilefactory);
      
    }//GEN-LAST:event_mapcomboActionPerformed

    private void cmdaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdaddActionPerformed
        JOptionPane.showMessageDialog(null,"under development");
       // waypoint.add(new mywaypoint("Area 1", new GeoPosition(10.251492302563756, 123.79580465932587)));
        //initwaypoint();
    }//GEN-LAST:event_cmdaddActionPerformed

    private void cmdclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdclearActionPerformed
         JOptionPane.showMessageDialog(null,"under development");
    }//GEN-LAST:event_cmdclearActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdadd;
    private javax.swing.JButton cmdclear;
    private org.jxmapviewer.JXMapViewer jXMapViewer1;
    private javax.swing.JComboBox<String> mapcombo;
    private org.jxmapviewer.JXMapViewer mapv;
    // End of variables declaration//GEN-END:variables
}
