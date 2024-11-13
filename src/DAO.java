import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DAO {
    private final String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private final String userid = "scott";
    private final String passwd = "tiger";
    private String sql;
    private Map<String, ArrayList<String>> dataMap = new HashMap<>();

    public DAO() {
        try {
            // Oracle JDBC 드라이버 로드
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // SELECT 쿼리 실행 메서드
    public Map<String, ArrayList<String>> OracleInputSql(String sql) {
        dataMap.clear();
        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String columnValue = rs.getString(i);
                    if (columnValue == null) {
                        columnValue = "NULL"; // null 값을 "NULL" 문자열로 변환
                    }
                    dataMap.computeIfAbsent(columnName, k -> new ArrayList<>()).add(columnValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataMap;
    }

    // 게시물 추가 메서드
    public void insertBorder(String title, String content, String writer, int empno) {
        sql = "INSERT INTO BOARD (title, content, writer, empno) VALUES (?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.setString(3, writer);
            pstmt.setInt(4, empno);

            pstmt.executeUpdate();
            System.out.println("데이터가 성공적으로 저장되었습니다.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 직원 이름으로 게시물 및 직원 삭제 메서드
    public void deleteByEmpName(String empname) {
        String deleteBordersSql = "DELETE FROM board WHERE empno = (SELECT empno FROM EMP WHERE ename = ?)";
        String deleteEmployeeSql = "DELETE FROM EMP WHERE ename = ?";

        try (Connection con = DriverManager.getConnection(url, userid, passwd)) {

            // 게시물 삭제
            try (PreparedStatement pstmt = con.prepareStatement(deleteBordersSql)) {
                pstmt.setString(1, empname);
                int rowsDeleted = pstmt.executeUpdate();
                System.out.println(rowsDeleted + "개의 게시물이 삭제되었습니다.");
            }

            // 직원 삭제
            try (PreparedStatement pstmt = con.prepareStatement(deleteEmployeeSql)) {
                pstmt.setString(1, empname);
                int rowsDeleted = pstmt.executeUpdate();
            }

            System.out.println("데이터 삭제가 성공적으로 완료되었습니다.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int checkName(String empname) {
        sql = "SELECT ENAME, EMPNO FROM EMP WHERE ENAME = ?";
        int empno = 0;
        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, empname);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    empno = rs.getInt("EMPNO");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return empno;
    }

    public ArrayList<Integer> viewBoardTitle(String empname) {
        sql = "SELECT BOARDNO FROM BOARD WHERE WRITER = ?";
        ArrayList<Integer> boardNumbers = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, empname);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    boardNumbers.add(rs.getInt("BOARDNO"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return boardNumbers;
    }

    public Map<String, String> viewBoardContent(String boardNo) {
        sql = "SELECT TITLE, CONTENT, WRITER, REGDATE FROM BOARD WHERE BOARDNO = ?";
        Map<String, String> boardContent = new HashMap<>();
        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, boardNo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    boardContent.put("TITLE", rs.getString("TITLE"));
                    boardContent.put("CONTENT", rs.getString("CONTENT"));
                    boardContent.put("WRITER", rs.getString("WRITER"));
                    boardContent.put("REGDATE", rs.getString("REGDATE"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return boardContent;
    }
    public void executeUpdateSql(String sql) {
        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             Statement stmt = con.createStatement()) {

            stmt.executeUpdate(sql); // SQL 실행
            System.out.println("SQL executed successfully: " + sql);

        } catch (SQLException e) {
            System.err.println("SQL execution failed: " + sql);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DAO myOracleDAO = new DAO();
        System.out.println("KING의 EMPNO: " + myOracleDAO.checkName("KING"));
    }
}
