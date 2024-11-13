import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewPanel extends CustomPanel {
    public static JTextArea textArea = new JTextArea();
    public static JTextField textField = new JTextField();
    public static boolean isInputPanel = false;
    public ViewPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
    public void createTable(Map<String, ArrayList<String>> datas) {
        removeAll();
        List<String> columnNames = new ArrayList<>(datas.keySet());
        List<List<String>> data = new ArrayList<>();

        int numRows = datas.values().iterator().next().size();
        for (int i = 0; i < numRows; i++) {
            List<String> row = new ArrayList<>();
            for (String key : columnNames) {
                row.add(datas.get(key).get(i));
            }
            data.add(row);
        }
        DefaultTableModel model = new DefaultTableModel(data.stream()
                .map(row -> row.toArray(new String[0]))
                .toArray(String[][]::new), columnNames.toArray(new String[0]));
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    public void inputBoard() {
        isInputPanel = true;
        removeAll();

        // 텍스트 필드 설정
        textField.setText("게시글 제목");
        textField.setPreferredSize(new Dimension(800, 40)); // 적절한 크기 설정
        textField.setMaximumSize(new Dimension(800, 40)); // BoxLayout에서 최대 크기 설정
        textField.setAlignmentX(Component.LEFT_ALIGNMENT); // BoxLayout 정렬

        // 텍스트 에어리어 설정
        textArea.setText("게시글 내용");
        textArea.setPreferredSize(new Dimension(800, 400)); // 적절한 크기 설정
        textArea.setMaximumSize(new Dimension(800, 400)); // BoxLayout에서 최대 크기 설정
        textArea.setAlignmentX(Component.LEFT_ALIGNMENT); // BoxLayout 정렬

        // 컴포넌트 추가
        add(Box.createRigidArea(new Dimension(0, 10))); // 상단 여백
        add(textField);
        add(Box.createRigidArea(new Dimension(0, 10))); // 필드와 에어리어 사이 간격
        add(new JScrollPane(textArea)); // 스크롤 추가
        
        new ButtonLabel("입력",this);

        revalidate();
        repaint();
    }

    @Override
    public void clickedEvent(String name) {

    }
}
