
package waypoint;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

public class mywaypoint extends DefaultWaypoint {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JButton getButton() {
        return button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }
    
    public mywaypoint(String name, GeoPosition coord){
        super(coord);
        this.name = name;
    }
    
    public mywaypoint(){
    
    }
    private String name;
    private JButton button;
    private void initButton(){
        button = new waypoint();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("click: "+ name);
            }
        });
        
    }
}
