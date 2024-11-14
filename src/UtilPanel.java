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
    ButtonLabel rename = new ButtonLabel("Rename", this);
    ButtonLabel signUp = new ButtonLabel("SignUp", this);
    String userName;
    int empno;

    public UtilPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setName("UtilPanel");
        setPreferredSize(new Dimension(800, 50));
        setBackground(Color.BLUE);

        logIn.setPreferredSize(new Dimension(150, 50));
        username.setPreferredSize(new Dimension(200, 50));

        add(Box.createVerticalStrut(10));
        add(Box.createHorizontalStrut(550));
        add(username);
        add(Box.createHorizontalStrut(10));
        add(logIn);
        add(signUp);
        add(Box.createVerticalStrut(10));

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
                    userName = username.getText();
                    username.setText("");
                    logIn.setName("LogOut");
                    logIn.setText("LogOut");
                    add(insert);
                    add(delete);
                    add(rename);
                    remove(signUp);
                    repaint();
                    revalidate();
                }
                else System.out.println("Error Can't find userName");
                break;
            case "LogOut":
                logIn.setName("LogIn");
                logIn.setText("LogIn");
                remove(logIn);
                remove(insert);
                remove(delete);
                remove(rename);
                add(logIn);
                add(signUp);
                mainFrame.titlesPanel.setTitlesMap(SQL.homeworkOne);
                repaint();
                revalidate();
                break;
            case "Delete":
                mainFrame.dao.deleteByEmpName(userName);
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
            case "Rename":
                int empno2 = mainFrame.dao.checkName(username.getText());
                if (empno2 == 0) {
                    String sql = "UPDATE EMP SET ENAME = '" + username.getText() + "' WHERE EMPNO = " + empno;
                    mainFrame.dao.executeUpdateSql(sql);
                    sql = "UPDATE BOARD SET WRITER = '" + username.getText() + "' WHERE EMPNO = " + empno;
                    mainFrame.dao.executeUpdateSql(sql);
                    username.setText("");
                    System.out.println("Renamed");
                }
                else System.out.println("Username already exists in the database.");
            case "SignUp":
                new SignUpPage();
        }
    }
}
