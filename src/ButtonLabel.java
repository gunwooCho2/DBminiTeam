import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonLabel extends JLabel {
    private CustomPanel parent;
    public ButtonLabel(String name, CustomPanel parent) {
        super(name);
        setName(name);
        this.parent = parent;
        setHorizontalAlignment(SwingConstants.CENTER);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(200, 40));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(Color.DARK_GRAY);
                setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.LIGHT_GRAY);
                setForeground(Color.BLACK);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                parent.clickedEvent(getName());
            }
        });
    }
}
