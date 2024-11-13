import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SQL {
    // 정적 맵 정의
    public static final Map<String, String> homeworkOne = new HashMap<>();
    public static final ArrayList<String> homeworkTwo = new ArrayList<>();

    // 정적 초기화 블록으로 맵 초기화
    static {
        homeworkOne.put("GROUP BY + HAVING", "SELECT JOB, AVG(SAL) FROM EMP GROUP BY JOB HAVING AVG(SAL) >= 1000");
        homeworkOne.put("JOIN1", "SELECT d.deptno, d.dname, e.empno, e.ename, e.job, e.sal FROM emp e, dept d WHERE e.deptno(+) = d.deptno ORDER BY d.deptno, e.empno");
        homeworkOne.put("JOIN2", """
                SELECT d.deptno, d.dname, e.empno, e.ename, e.mgr, e.sal, e.deptno AS deptno_1, 
                       s.losal, s.hisal, s.grade, e2.empno mgr_empno, e2.ename mgr_ename
                FROM emp e 
                RIGHT OUTER JOIN dept d ON (e.deptno = d.deptno)
                LEFT OUTER JOIN salgrade s ON (e.sal BETWEEN s.losal AND s.hisal)
                LEFT OUTER JOIN emp e2 ON (e.mgr = e2.empno)
                ORDER BY deptno, empno
                """);
        homeworkOne.put("서브쿼리", "SELECT * FROM emp WHERE sal > ANY (SELECT MIN(sal) FROM emp WHERE job = 'SALESMAN')");
        homeworkOne.put("급여 랭킹", "SELECT ENAME, SAL FROM EMP ORDER BY SAL DESC");

        homeworkTwo.add("""
                CREATE TABLE board (
                    boardNo NUMBER PRIMARY KEY,
                    title VARCHAR2(100) NOT NULL,
                    content VARCHAR2(1000) NOT NULL,
                    writer VARCHAR2(50) NOT NULL,
                    empno NUMBER,
                    regdate DATE DEFAULT SYSDATE,
                    CONSTRAINT fk_emp FOREIGN KEY (empno) REFERENCES emp(empno) ON DELETE CASCADE
                )
                """);
        homeworkTwo.add("""
                CREATE SEQUENCE board_seq
                    START WITH 1
                    INCREMENT BY 1
                    NOCYCLE
                    MAXVALUE 9999999999
                    MINVALUE 1
                    CACHE 20
                """);
        homeworkTwo.add("""
                CREATE OR REPLACE TRIGGER trg_board_no
                BEFORE INSERT ON board
                FOR EACH ROW
                BEGIN
                    IF :NEW.boardNo IS NULL THEN
                        SELECT board_seq.NEXTVAL INTO :NEW.boardNo FROM dual;
                    END IF;
                END;
                /
                """);
    }

    public static String viewBoardContent(int boardNo) {
        String sql = "SELECT TITLE, CONTENT, WRITER, REGDATE FROM BOARD WHERE BOARDNO = " + boardNo;
        return sql;
    }

    private SQL() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }
}