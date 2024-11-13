import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public DAO dao = new DAO();
    public ViewPanel viewPanel = new ViewPanel();
    public TitlesPanel titlesPanel = new TitlesPanel(this);
    public UtilPanel utilPanel = new UtilPanel(this);
    public MainFrame() {
        setTitle("미니실습");
        setSize(1000, 600); // 창 크기 조정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 중앙에 위치
        setLayout(new BorderLayout());
        add(titlesPanel, BorderLayout.WEST);
        add(viewPanel, BorderLayout.CENTER);
        add(utilPanel, BorderLayout.NORTH);

        setVisible(true);
    }
}
