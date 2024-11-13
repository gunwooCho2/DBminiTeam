
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SignUpPage {
	String inputedName;
	String inputedJob;
	String[] jobBox = {
			"CLERK",
			"SALESMAN",
			"PRESIDENT",
			"MANAGER",
			"ANALYST"
	};
	Integer[] deptnoBox = {
		10,20,30	
	};
	int inputedDeptno;
	JTextField name = new JTextField();
	JComboBox<String> job = new JComboBox<String>(jobBox);
	JComboBox<Integer> deptNo = new JComboBox<Integer>(deptnoBox);
	JButton submit = new JButton("submit"); 
	JFrame frame = new JFrame();
	JPanel input = new JPanel();
	public SignUpPage() {
		frame.setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));
		input.setLayout(new GridLayout(3,2));
		frame.add(input);
		frame.setSize(300, 200);
		frame.setLocationRelativeTo(null);

		
		input.add(new JLabel("이름 : "));
		input.add(name);
		input.add(new JLabel("직업 : "));
		input.add(job);
		input.add(new JLabel("부서 : "));
		input.add(deptNo);
		frame.add(submit);
		
		submit.addActionListener(e -> submit());
		
		frame.setVisible(true);
	}
	
	public void submit() {
		if (!name.getText().equals("")) {
		inputedName = name.getText();
		inputedJob = (String)job.getSelectedItem();
		inputedDeptno = (int)deptNo.getSelectedItem();
		String sql = "insert into emp(empno,ename,job,deptno) values(empno_seq.nextval,'" + inputedName + "','" + inputedJob + "','" + inputedDeptno + "')"  ;
		new DAO().executeUpdateSql(sql);
		frame.dispose();
		} else {
			JOptionPane.showMessageDialog(null, "이름을 정확히 입력해 주세요");
			name.setText("");
		}
	}
	public static void main(String[] args) {
	new SignUpPage();
	}
}
