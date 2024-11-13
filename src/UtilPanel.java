import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UtilPanel extends CustomPanel {
    private final MainFrame mainFrame;
    JTextField username = new JTextField(20);
    ButtonLabel logIn = new ButtonLabel("LogIn", this);
    ButtonLabel insert = new ButtonLabel("Insert", this);
    ButtonLabel delete = new ButtonLabel("Delete", this);
    ButtonLabel createTable = new ButtonLabel("Create Table", this);
    int empno;

    public UtilPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        // 레이아웃 설정
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setName("UtilPanel");
        setPreferredSize(new Dimension(800, 50)); // 전체 크기 설정
        setBackground(Color.BLUE);

        // 크기 설정
        logIn.setPreferredSize(new Dimension(150, 50));
        username.setPreferredSize(new Dimension(200, 50));

        // 간격 추가
        add(Box.createVerticalStrut(10));
        add(Box.createHorizontalStrut(650)); // 왼쪽 여백
        add(username);
        add(Box.createHorizontalStrut(10)); // 컴포넌트 간 간격
        add(logIn);
        add(Box.createVerticalStrut(10));
//        add(createTable);

        setVisible(true);
    }

    @Override
    public void clickedEvent(String name) {
        switch (name) {
            case "LogIn":
                empno = mainFrame.dao.checkName(username.getText());
                if (empno != 0) {
                    ArrayList<Integer> boardNo = mainFrame.dao.viewBoardTitle(username.getText());
                    Map<String, String>boardNoMap = new HashMap<>();
                    for (Integer integer : boardNo) {
                        boardNoMap.put(integer.toString(), SQL.viewBoardContent(integer));
                    }
                    mainFrame.titlesPanel.setTitlesMap(boardNoMap);
                    remove(username);
                    remove(createTable);
                    logIn.setName("LogOut");
                    logIn.setText("LogOut");
                    add(insert);
                    add(delete);
                }
                else System.out.println("Error Can't find userName");
                break;
            case "LogOut":
                logIn.setName("LogIn");
                logIn.setText("LogIn");
                remove(logIn);
                remove(insert);
                remove(delete);
                username.setText("");
                add(username);
                add(logIn);
                mainFrame.titlesPanel.setTitlesMap(SQL.homeworkOne);
                break;
            case "Delete":
                mainFrame.dao.deleteByEmpName(username.getText());
                break;
            case "Insert":
                if (ViewPanel.isInputPanel) {
                    String boardTitle = ViewPanel.textField.getText();
                    String boardContect = ViewPanel.textArea.getText();
                    if (!boardContect.isEmpty() && !boardTitle.isEmpty()) {
                        mainFrame.dao.insertBorder(boardTitle, boardContect, username.getText(), empno);
                        ViewPanel.textArea.setText("");
                        ViewPanel.textField.setText("");
                        ViewPanel.isInputPanel = false;
                        ArrayList<Integer> boardNo = mainFrame.dao.viewBoardTitle(username.getText());
                        Map<String, String>boardNoMap = new HashMap<>();
                        for (Integer integer : boardNo) {
                            boardNoMap.put(integer.toString(), SQL.viewBoardContent(integer));
                        }
                        mainFrame.titlesPanel.setTitlesMap(boardNoMap);
                    }
                    else System.out.println("Content or title is Empty");
                }
                else mainFrame.viewPanel.inputBoard();
                break;
            case "Create Table":
                for (String sql:SQL.homeworkTwo) {
                    mainFrame.dao.executeUpdateSql(sql);
                }
                remove(createTable);
                break;
        }
    }
}
