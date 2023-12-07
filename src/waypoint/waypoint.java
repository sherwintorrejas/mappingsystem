
package waypoint;

import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class waypoint extends JButton {
    public waypoint(){
        setContentAreaFilled(false);
        setIcon(new ImageIcon(getClass().getResource("/icon/placeholder.png")));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setSize(new Dimension(24, 24));
    }
    
}
