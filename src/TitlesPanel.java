import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class TitlesPanel extends CustomPanel {
    private final MainFrame mainFrame;
    private Map<String, String> titlesMap;

    public TitlesPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setName("TitlesPanel");
        setSize(200, 600);
        setBackground(Color.red);
        setTitlesMap(SQL.homeworkOne);
        setVisible(true);
    }

    public void setTitlesMap(Map<String, String> titlesMap) {
        removeAll();
        for (Map.Entry<String, String> entry : titlesMap.entrySet()) {
            add(new ButtonLabel(entry.getKey(), this));
        }
        this.titlesMap = titlesMap;
        revalidate();
        repaint();
    }
    @Override
    public void clickedEvent(String name) {
        mainFrame.viewPanel.createTable(mainFrame.dao.OracleInputSql(titlesMap.get(name)));
    }
}
