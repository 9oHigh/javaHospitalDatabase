
import java.awt.event.*;
//import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;

public class HospitalDatabase extends JFrame implements ActionListener {

	JButton btnDoctor,btnNurse,btnPatient,btnTreatment,btnChart;
	JButton btnCheck,btnCancel, btnSearch1,btnSearch2,btnSearch3;
	JButton btnResetDB;
	int checkBtn;

	JTextArea txtResult, txtStatus;
	JTextField inputBox;
	JPanel mainPanel;

	static Connection con;
	Statement stmt;
	ResultSet rs;
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";
	;
	
	public HospitalDatabase() {
		// TITLE
		super("14010448 �̰���");
		layInit();
		conDB();
		setVisible(true);
		setBounds(0, 0, 1400, 500); // ������ġ,������ġ,���α���,���α���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void layInit() {
		// �Է�
		inputBox = new JTextField(15);
		inputBox.setText("");

		// Buttons
		btnDoctor = new JButton("Doctors");
		btnNurse = new JButton("Nurses");
		btnPatient = new JButton("Patients");
		btnTreatment = new JButton("Treatment");
		btnChart = new JButton("Charts");
		btnCheck = new JButton("Ȯ��");
		btnCancel = new JButton("���");
		btnSearch1 = new JButton("Q1");
		btnSearch2 = new JButton("Q2");
		btnSearch3 = new JButton("Q3");
		btnResetDB = new JButton("RESET Database");

		// ���
		txtResult = new JTextArea();
		txtStatus = new JTextArea(5, 30);

		inputBox.setSize(25, 5);

		mainPanel = new JPanel();
		mainPanel.add(inputBox);
		mainPanel.add(btnCheck);
		mainPanel.add(btnCancel);
		mainPanel.add(btnDoctor);
		mainPanel.add(btnNurse);
		mainPanel.add(btnPatient);
		mainPanel.add(btnTreatment);
		mainPanel.add(btnChart);
		mainPanel.add(btnSearch1);
		mainPanel.add(btnSearch2);
		mainPanel.add(btnSearch3);
		mainPanel.add(btnResetDB);

		txtResult.setEditable(false);
		txtStatus.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(txtResult);
		JScrollPane scrollPane2 = new JScrollPane(txtStatus);

		add("North", mainPanel);
		add("Center", scrollPane);
		add("South", scrollPane2);

		btnDoctor.addActionListener(this);
		btnNurse.addActionListener(this);
		btnPatient.addActionListener(this);
		btnTreatment.addActionListener(this);
		btnChart.addActionListener(this);
		btnCheck.addActionListener(this);
		btnCancel.addActionListener(this);
		btnSearch1.addActionListener(this);
		btnSearch2.addActionListener(this);
		btnSearch3.addActionListener(this);
		btnResetDB.addActionListener(this);
	}

	public void conDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// System.out.println("����̹� �ε� ����");
			txtStatus.append("����̹� �ε� ����...\n");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try { /* �����ͺ��̽��� �����ϴ� ���� */
			// System.out.println("�����ͺ��̽� ���� �غ�...");
			txtStatus.append("�����ͺ��̽� ���� �غ�...\n");
			con = DriverManager.getConnection(url, userid, pwd);
			// System.out.println("�����ͺ��̽� ���� ����");
			txtStatus.append("�����ͺ��̽� ���� ����...\n");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			stmt = con.createStatement();
			String query = "";
			String sql = "";
			// executeUpdate�� ���� ����
			int ires = 0;
			// �Է� ��ư
			if (e.getSource() == btnDoctor) {
				checkBtn = 1; inputBox.setText("�ǻ� ���̺� ����/����/������ �����մϴ�.");
				txtResult.setText("���� : insert into Doctors values();\n����: WHERE�� �Է�\n����: SET/WHERE�� ���� �Է�('/'�� ����)");
			}
			else if(e.getSource() == btnNurse){
				checkBtn = 2;inputBox.setText("��ȣ ���̺� ����/����/������ �����մϴ�.");
				txtResult.setText("���� : insert into Nurses values();\n����: WHERE�� �Է�\n����: SET/WHERE�� ���� �Է�('/'�� ����)");
			}
			else if(e.getSource() == btnPatient ){
				checkBtn = 3;inputBox.setText("ȯ�� ���̺� ����/����/������ �����մϴ�.");
				txtResult.setText("���� : insert into Patients values();\n����: WHERE�� �Է�\n����: SET/WHERE�� ���� �Է�('/'�� ����)");
			}
			else if(e.getSource() == btnTreatment){
				checkBtn = 4;inputBox.setText("���� ���̺� ����/����/������ �����մϴ�.");
				txtResult.setText("���� : insert into Treatments values();\n����: WHERE�� �Է�\n����: SET/WHERE�� ���� �Է�('/'�� ����)");
			}
			else if(e.getSource() == btnChart){
				checkBtn = 5;inputBox.setText("��Ʈ ���̺� ����/����/������ �����մϴ�.");
				txtResult.setText("���� : insert into Charts values();\n����: WHERE�� �Է�\n����: SET/WHERE�� ���� �Է�('/'�� ����)");
			}
			if (e.getSource() == btnCheck) {
				//981512,'�Ҿư�','������','M','010-333-1320','ltj@hanb2.com','����'
	
				if (checkBtn == 1) {
					try {
						String info = inputBox.getText();
						String checkInfo = info.substring(0,1);
						int idx = 0;
						txtResult.setText("");
						//���� 
						if (checkInfo.equals("i") || checkInfo.equals("I")) {
							query = info;
							ires = stmt.executeUpdate(query);
							txtResult.setText("���� �����߽��ϴ�.\n");
						}
						else {
							if (info.contains("/")) {checkInfo = "s";}
							else {checkInfo = "w";}
							//���� 
							if(checkInfo.equals("w")) {
								sql = "SET foreign_key_checks = 0;";
								stmt.execute(sql);
								query = "delete "
										+	"from Doctors "
										+ 	"where "
										+	info;
								ires = stmt.executeUpdate(query);
								sql = "SET foreign_key_checks = 1;";
								stmt.execute(sql);
								txtResult.setText("������ �����߽��ϴ�.\n");
							}
							//����
							else if (checkInfo.equals("s")) {
								String set = info.substring(0,info.indexOf("/"));
								String where = info.substring(info.indexOf("/")+1,info.length());
								query = "UPDATE Doctors set " + set +" where "+ where;
								ires = stmt.executeUpdate(query);
								txtResult.setText("���� �Ϸ� �߽��ϴ�.\n");
							}
							//����
							else {
								txtResult.setText("�߸� �Է��߽��ϴ�. ���̺���� �ٽ� ������ �ּ���.\n");
							}
						}
					}catch(Exception e1) {
						txtResult.setText("Error : " + e1.getMessage());
						System.out.println("SQL Error :" + e1.getMessage());
					}
				}

				else if( checkBtn == 2) {
					try {
						String info = inputBox.getText();
						String checkInfo = info.substring(0,1);
						int idx = 0;
						txtResult.setText("");

						//���� 
						if (checkInfo.equals("i") || checkInfo.equals("I")) {
							query = info;
							ires = stmt.executeUpdate(query);
							txtResult.setText("���� �����߽��ϴ�.\n");
						}
						else {
							if (info.contains("/")) {checkInfo = "s";}
							else {checkInfo = "w";}
							//���� 
							if(checkInfo.equals("w")) {
								sql = "SET foreign_key_checks = 0;";
								stmt.execute(sql);
								query = "delete "
										+	"from nurses "
										+ 	"where "
										+	info;
								ires = stmt.executeUpdate(query);
								sql = "SET foreign_key_checks = 1;";
								stmt.execute(sql);
								txtResult.setText("������ �����߽��ϴ�.\n");
							}
							//����
							else if (checkInfo.equals("s")) {
								//nur_name = '�̻��'/nur_id = 130211
								String set = info.substring(0,info.indexOf("/"));
								String where = info.substring(info.indexOf("/")+1,info.length());
								query = "UPDATE nurses set " + set +" where "+ where;
								System.out.println(query);
								ires = stmt.executeUpdate(query);
								txtResult.setText("���� �Ϸ� �߽��ϴ�.\n");
							}
							//����
							else {
								txtResult.setText("�߸� �Է��߽��ϴ�. ���̺���� �ٽ� ������ �ּ���.\n");
							}
						}
					}catch(Exception e1) {
						txtResult.setText("Error : " + e1.getMessage());
						System.out.println("SQL Error :" + e1.getMessage());
					}

				}else if( checkBtn == 3) {
					try {
						String info = inputBox.getText();
						String checkInfo = info.substring(0,1);
						int idx = 0;
						txtResult.setText("");

						//���� 
						if (checkInfo.equals("i") || checkInfo.equals("I")) {
							query = info;
							ires = stmt.executeUpdate(query);
							txtResult.setText("���� �����߽��ϴ�.\n");
						}
						else {
							if (info.contains("/")) {checkInfo = "s";}
							else {checkInfo = "w";}
							//���� 
							if(checkInfo.equals("w")) {
								sql = "SET foreign_key_checks = 0;";
								stmt.execute(sql);
								query = "delete "
										+	"from patients "
										+ 	"where "
										+	info;
								ires = stmt.executeUpdate(query);
								sql = "SET foreign_key_checks = 1;";
								stmt.execute(sql);
								txtResult.setText("������ �����߽��ϴ�.\n");
							}
							//����
							else if (checkInfo.equals("s")) {
								String set = info.substring(0,info.indexOf("/"));
								String where = info.substring(info.indexOf("/")+1,info.length());
								query = "UPDATE patients set " + set +" where "+ where;
								ires = stmt.executeUpdate(query);
								txtResult.setText("���� �Ϸ� �߽��ϴ�.\n");
							}
							//����
							else {
								txtResult.setText("�߸� �Է��߽��ϴ�. ���̺���� �ٽ� ������ �ּ���.\n");
							}
						}
					}catch(Exception e1) {
						txtResult.setText("Error : " + e1.getMessage());
						System.out.println("SQL Error :" + e1.getMessage());
					}

				}else if( checkBtn == 4) {
					try {
						String info = inputBox.getText();
						String checkInfo = info.substring(0,1);
						int idx = 0;
						txtResult.setText("");

						//���� 
						if (checkInfo.equals("i") || checkInfo.equals("I")) {
							query = info;
							ires = stmt.executeUpdate(query);
							txtResult.setText("���� �����߽��ϴ�.\n");
						}
						else {
							if (info.contains("/")) {checkInfo = "s";}
							else {checkInfo = "w";}
							//���� 
							if(checkInfo.equals("w")) {
								sql = "SET foreign_key_checks = 0;";
								stmt.execute(sql);
								query = "delete "
										+	"from treatments "
										+ 	"where "
										+	info;
								ires = stmt.executeUpdate(query);
								sql = "SET foreign_key_checks = 1;";
								stmt.execute(sql);
								txtResult.setText("������ �����߽��ϴ�.\n");
							}
							//����
							else if (checkInfo.equals("s")) {
								String set = info.substring(0,info.indexOf("/"));
								String where = info.substring(info.indexOf("/")+1,info.length());
								query = "UPDATE treatments set " + set +" where "+ where;
								ires = stmt.executeUpdate(query);
								txtResult.setText("���� �Ϸ� �߽��ϴ�.\n");
							}
							//����
							else {
								txtResult.setText("�߸� �Է��߽��ϴ�. ���̺���� �ٽ� ������ �ּ���.\n");
							}
						}
					}catch(Exception e1) {
						txtResult.setText("Error : " + e1.getMessage());
						System.out.println("SQL Error :" + e1.getMessage());
					}

				}else if( checkBtn == 5) {
					try {
						String info = inputBox.getText();
						String checkInfo = info.substring(0,1);
						int idx = 0;
						txtResult.setText("");

						//���� 
						if (checkInfo.equals("i") || checkInfo.equals("I")) {
							query = info;
							ires = stmt.executeUpdate(query);
							txtResult.setText("���� �����߽��ϴ�.\n");
						}
						else {
							if (info.contains("/")) {checkInfo = "s";}
							else {checkInfo = "w";}
							//���� 
							if(checkInfo.equals("w")) {
								sql = "SET foreign_key_checks = 0;";
								stmt.execute(sql);
								query = "delete "
										+	"from Charts "
										+ 	"where "
										+	info;
								ires = stmt.executeUpdate(query);
								sql = "SET foreign_key_checks = 1;";
								stmt.execute(sql);
								txtResult.setText("������ �����߽��ϴ�.\n");
							}
							//����
							else if (checkInfo.equals("s")) {
								String set = info.substring(0,info.indexOf("/"));
								String where = info.substring(info.indexOf("/")+1,info.length());
								query = "UPDATE charts set " + set +" where "+ where;
								ires = stmt.executeUpdate(query);
								txtResult.setText("���� �Ϸ� �߽��ϴ�.\n");
							}
							//����
							else {
								txtResult.setText("�߸� �Է��߽��ϴ�. ���̺���� �ٽ� ������ �ּ���.\n");
							}
						}
					}catch(Exception e1) {
						txtResult.setText("Error : " + e1.getMessage());
						System.out.println("SQL Error :" + e1.getMessage());
					}

				}

			} else if (e.getSource() == btnCancel) {
				try {
					con.rollback();
					txtResult.setText("�ѹ�(���) �Ϸ��߽��ϴ�.\n");
				}catch(SQLException ex) {
					txtResult.setText("ERROR : "+ex.getMessage());
				}

			} else if (e.getSource() == btnSearch1) {
				try {
					txtResult.setText("");
					if (checkBtn == 1) {
						query = "select * from Doctors;";
						rs = stmt.executeQuery(query);
						txtResult.setText("�ǻ� ����\n");
						while (rs.next()) {
							String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4)
							+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+rs.getString(7)+"\n";
							txtResult.append(str);
						}
					}else if(checkBtn ==2) {
						query = "select * from Nurses;";
						rs = stmt.executeQuery(query);
						txtResult.setText("��ȣ�� ����\n");
						while (rs.next()) {
							String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4)
							+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+rs.getString(7)+"\n";
							txtResult.append(str);
						}
					}else if(checkBtn == 3) {
						query = "select * from Patients;";
						rs = stmt.executeQuery(query);
						txtResult.setText("ȯ�� ����\n");
						while (rs.next()) {
							String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4)
							+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+rs.getString(7)+"\t"+rs.getString(8)+"\t"+rs.getString(9)+"\t"+rs.getString(10)+"\n";
							txtResult.append(str);
						}
					}else if(checkBtn == 4) {
						query = "select * from Treatments;";
						rs = stmt.executeQuery(query);
						txtResult.setText("���� ����\n");
						while (rs.next()) {
							String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4)
							+"\t"+rs.getString(5)+"\n";
							txtResult.append(str);
						}
					}else if (checkBtn ==5) {
						query = "select * from Charts;";
						rs = stmt.executeQuery(query);
						txtResult.setText("��Ʈ ����\n");
						while (rs.next()) {
							String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4)
							+"\t"+rs.getInt(5)+"\t"+rs.getString(6)+"\n";
							txtResult.append(str);
						}
					}
				}catch(Exception e1) {
					txtResult.setText("Error : " + e1.getMessage());
				}
			}
			// Q1 - �������� �������� ���� ������ �̸��� �˻��϶�.
			else if (e.getSource() == btnSearch2) {
				txtResult.setText("");
				query = "select pat_gen,count(*)\n"
						+ "from patients\n"
						+ "where pat_id in(select pat_id from treatments where treat_date > '2014-01-01')\n"
						+ "group by pat_gen;";
				txtResult.setText("2014�⵵ ������ �������� �ִ� ����� �� ���ڿ� ������ ���� ���ϴ� ����\n\nselect pat_gen,count(*)\n"
						+ "from patients\n"
						+ "where pat_id in(select pat_id from treatments where treat_date > '2014-01-01')\n"
						+ "group by pat_gen; \n\n����\t��\n");
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					String str = rs.getString(1) + "\t" + rs.getInt(2)+"\n";
					txtResult.append(str);
				}
			}
			// Q2 - �������� ������ ������ ���Ǽ��� �˻��϶�.
			else if (e.getSource() == btnSearch3) {
				txtResult.setText("");
				query = "select pat_gen,count(*)\n"
						+ "from patients \n"
						+ "where pat_id in (select distinct c.pat_id\n"
						+ "				from charts c,treatments t\n"
						+ "				where ( t.treat_date > '2014-01-01' and c.charts_contents like '%ó��%' and c.pat_id = t.pat_id )\n"
						+ "				)\n"
						+ "group by pat_gen;";
				txtResult.setText("��Ʈ�� 'ó��'�� ���� ȯ�ڵ� �߿��� 2014-01-01 ������ �������� ���� ������� ��������� ���ϴ� ����\n\nselect pat_gen,count(*)\n"
						+ "from patients \n"
						+ "where pat_id in (\n"
						+ " 	select distinct c.pat_id\n"
						+ " 	from charts c,treatments t\n"
						+ " 	where ( t.treat_date > '2014-01-01' and c.charts_contents like '%ó��%' and c.pat_id = t.pat_id )\n"
						+ "	)\n"
						+ "group by pat_gen;\n\n����\t��\n");
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					String str = rs.getString(1) + "\t" + rs.getInt(2)+"\n";
					txtResult.append(str);
				}
			}
			else if (e.getSource() == btnResetDB) {
				try {
					// excuteUpdate�� ���� ����
					int reset = 0;
					txtResult.setText("");
					// �������� ������ �ۼ��ϰ� �����ϱ����� ���� �迭 ����
					String[] querySet = new String[80];
					// DROP�� ���� ���̺� ��ü ��
					query = "drop schema if exists `madang`";
					reset = stmt.executeUpdate(query);

					query = "CREATE SCHEMA IF NOT EXISTS `madang` DEFAULT CHARACTER SET utf8 ;";
					reset = stmt.executeUpdate(query);

					query = "USE `madang` ;";
					reset = stmt.executeUpdate(query);

					query = "DROP TABLE IF EXISTS Doctors,Nurses,Patients,Treatments,Charts;";
					reset = stmt.executeUpdate(query);

					// �ʱ� ���̺��� �ٽ� �����ϱ� ���� ����
					querySet[0] = "CREATE TABLE IF NOT EXISTS `madang`.`Doctors` (\n"
							+ "	   doc_id  integer not null,\n"
							+ "	   major_treat varchar(25) not null,\n"
							+ "	   doc_name varchar(20) not null,\n"
							+ "	   doc_gen char(1) not null,\n"
							+ "    doc_phone varchar(15) null,\n"
							+ "    doc_email varchar(50) unique,\n"
							+ "    doc_position varchar(20) not null\n"
							+ ");";
					querySet[1] = "alter table Doctors\n"
							+ "	ADD constraint doc_id_pk primary key(doc_id);";

					querySet[2] = "CREATE TABLE IF NOT EXISTS `madang`.`Nurses` (\n"
							+ "	   nur_id  integer not null,\n"
							+ "	   major_job varchar(25) not null,\n"
							+ "	   nur_name varchar(20) not null,\n"
							+ "	   nur_gen char(1) not null,\n"
							+ "    nur_phone varchar(15) null,\n"
							+ "    nur_email varchar(50) unique,\n"
							+ "    nur_position varchar(20) not null\n"
							+ ");";
					querySet[3] = "alter table Nurses\n"
							+ "	ADD constraint nur_id_pk primary key (nur_id);";

					querySet[4] = "CREATE TABLE IF NOT EXISTS `madang`.`Patients` (\n"
							+ "	   pat_id  integer not null,\n"
							+ "    nur_id  integer not null,\n"
							+ "    doc_id  integer not null,\n"
							+ "	   pat_name varchar(20) not null,\n"
							+ "	   pat_gen char(1) not null,\n"
							+ "    pat_jumin varchar(14) not null,\n"
							+ "    pat_addr varchar(100) not null,\n"
							+ "    pat_phone varchar(15) null,\n"
							+ "    pat_email varchar(50) unique,\n"
							+ "    pat_position varchar(20) not null\n"
							+ ");";
					querySet[5] = "alter table Patients\n"
							+ "	     ADD constraint pat_id_pk primary key (pat_id);";
					querySet[6] = "alter table Patients\n"
							+ "	     ADD (constraint R_2 foreign key(doc_id) references Doctors(doc_id));";

					querySet[7] = "CREATE TABLE IF NOT EXISTS `madang`.`Treatments` (\n"
							+ "		treat_id integer not null,\n"
							+ "		pat_id  integer not null,\n"
							+ "     doc_id  integer not null,\n"
							+ "		treat_contents varchar(1000) not null,\n"
							+ "     treat_date DATE not null\n"
							+ ");";
					querySet[8] = "alter table Treatments\n"
							+ "		  ADD constraint treat_pat_doc_id_pk primary key (treat_id,pat_id,doc_id);";
					querySet[9] = "alter table Treatments\n"
							+ "		  ADD (constraint R_5 foreign key(pat_id) references Patients (pat_id));";
					querySet[10] = "alter table Treatments\n"
							+ "		  ADD (constraint R_6 foreign key(doc_id) references Doctors (doc_id));";

					querySet[11] = "CREATE TABLE IF NOT EXISTS `madang`.`Charts` (\n"
							+ "		charts_id integer not null,\n"
							+ "		treat_id integer not null,\n"
							+ "     doc_id  integer not null,\n"
							+ "		pat_id  integer not null,\n"
							+ "     nur_id  integer not null,\n"
							+ "		charts_contents varchar(1000) not null\n"
							+ ");";
					querySet[12] = "alter table charts\n"
							+ "		  ADD constraint chart_treat_doc_pat_id_pk primary key (charts_id,treat_id,pat_id,doc_id);";
					querySet[13] = "alter table charts\n"
							+ "		  ADD (constraint R_4 foreign key(nur_id) references Nurses (nur_id));";
					querySet[14] = "alter table charts\n"
							+ "		  ADD (constraint R_7 foreign key(treat_id,pat_id,doc_id) references Treatments (treat_id,pat_id,doc_id));";

					querySet[15] = "alter table patients\n"
							+ "	      ADD (constraint R_3 foreign key(nur_id) references nurses(nur_id));";


					querySet[16] = "insert into Doctors values(980312,'�Ҿư�','������','M','010-333-1340','ltj@hanbh.com','����');";
					querySet[17] = "insert into Doctors values(000601,'����','�ȼ���','M','011-222-0987','ask@hanbh.com','����');";
					querySet[18] = "insert into Doctors values(001208,'�ܰ�','�����','M','010-333-8743','kmj@hanbh.com','����');";
					querySet[19] = "insert into Doctors values(020403,'�Ǻΰ�','���¼�','M','019-777-3764','lts@hanbh.com','����');";
					querySet[20] = "insert into Doctors values(050900,'�Ҿư�','�迬��','F','010-555-3746','kya@hanbh.com','������');";
					querySet[21] = "insert into Doctors values(050101,'����','������','M','011-222-7643','cth@hanbh.com','������');";
					querySet[22] = "insert into Doctors values(062019,'�Ҿư�','������','F','010-999-1265','jjh@hanbh.com','������');";
					querySet[23] = "insert into Doctors values(070576,'�Ǻΰ�','ȫ�浿','M','016-333-7263','hgd@hanbh.com','������');";
					querySet[24] = "insert into Doctors values(080543,'��缱��','���缮','M','010-222-1263','yjs@hanbh.com','����'); ";
					querySet[25] = "insert into Doctors values(091001,'�ܰ�','�躴��','M','010-555-3542','kbm@hanbh.com','������');";

					querySet[26] = "insert into Nurses values(050302,'�Ҿư�','������','F','010-555-8751','key@hanbh.com','����ȣ��');";
					querySet[27] = "insert into Nurses values(050021,'����','������','F','016-333-8745','ysa@hanbh.com','����ȣ��');";
					querySet[28] = "insert into Nurses values(040089,'�Ǻΰ�','������','M','010-666-7646','sjw@hanbh.com','����');";
					querySet[29] = "insert into Nurses values(070605,'��缱��','����ȭ','F','010-333-4588','yjh@hanbh.com','����');";
					querySet[30] = "insert into Nurses values(070804,'����','���ϳ�','F','010-222-1340','nhn@hanbh.com','����');";
					querySet[31] = "insert into Nurses values(071018,'�Ҿư�','��ȭ��','F','019-888-4116','khk@hanbh.com','����');";
					querySet[32] = "insert into Nurses values(100356,'�Ҿư�','�̼���','M','010-777-1234','lsy@hanbh.com','��ȣ��');";
					querySet[33] = "insert into Nurses values(104145,'�ܰ�','����','M','010-999-8520','kh@hanbh.com','��ȣ��');";
					querySet[34] = "insert into Nurses values(120309,'�Ǻΰ�','�ڼ���','M','010-777-4996','psw@hanbh.com','��ȣ��');";
					querySet[35] = "insert into Nurses values(130211,'�ܰ�','�̼���','F','010-222-3214','lsy2@hanbh.com','��ȣ��');";

					querySet[36] = "insert into Patients values(2345,050302,980312,'�Ȼ��','M','232345','����','010-555-7845','ask@ab.com','ȸ���');";
					querySet[37] = "insert into Patients values(3545,040089,020403,'�輺��','M','543545','����','010-333-7812','ksr@bb.com','�ڿ���');";
					querySet[38] = "insert into Patients values(3424,070605,080543,'������','M','433424','�λ�','019-888-4859','ljjk@ab.com','ȸ���');";
					querySet[39] = "insert into Patients values(7675,100356,050900,'�ְ���','M','677675','����','010-222-4847','cks@cc.com','ȸ���');";
					querySet[40] = "insert into Patients values(4533,070804,000601,'���Ѱ�','M','744533','����','010-777-9630','jhk@ab.com','����');";
					querySet[41] = "insert into Patients values(5546,120309,070576,'������','M','765546','�뱸','016-777-0214','ywh@cc.com','�ڿ���');";
					querySet[42] = "insert into Patients values(4543,070804,050101,'������','M','454543','�λ�','010-555-4187','cjj@bb.com','ȸ���');";
					querySet[43] = "insert into Patients values(9768,130211,091001,'������','F','119768','����','010-888-3675','ljh@ab.com','����');";
					querySet[44] = "insert into Patients values(4234,130211,091001,'������','F','234234','����','010-999-6541','onm@cc.com','�л�');";
					querySet[45] = "insert into Patients values(7643,071018,062019,'�ۼ���','M','987643','����','010-222-5874','ssm@bb.com','�л�');";

					querySet[46] = "insert into Treatments values(130516023,2345,980312,'����, ����','2013-05-16');";
					querySet[47] = "insert into Treatments values(130628100,3545,020403,'�Ǻ� Ʈ���� ġ��','2013-06-28');";
					querySet[48] = "insert into Treatments values(131205056,3424,080543,'�� ��ũ�� MRI �Կ�','2013-12-05');";
					querySet[49] = "insert into Treatments values(131218024,7675,050900,'���̿�','2013-12-18');";
					querySet[50] = "insert into Treatments values(131224012,4533,000601,'�忰','2013-12-24');";
					querySet[51] = "insert into Treatments values(140103001,5546,070576,'���帧 ġ��','2014-01-03');";
					querySet[52] = "insert into Treatments values(140109026,4543,050101,'����','2014-01-09');";
					querySet[53] = "insert into Treatments values(140226102,9768,091001,'ȭ��ġ��','2014-02-26');";
					querySet[54] = "insert into Treatments values(140303003,4234,091001,'������ �ܻ�ġ��','2014-03-03');";
					querySet[55] = "insert into Treatments values(140308087,7643,062019,'�忰','2014-03-08');";

					querySet[56] = "insert into Charts values(1,130516023,980312,2345,050302,'�ؿ��� ó��');";
					querySet[57] = "insert into Charts values(2,130628100,020403,3545,040089,'����');";
					querySet[58] = "insert into Charts values(3,131205056,080543,3424,070605,'MRI ���� Ȯ��');";
					querySet[59] = "insert into Charts values(4,131218024,050900,7675,070804,'���̿��� ó��');";
					querySet[60] = "insert into Charts values(5,131224012,000601,4533,070804,'�忰�� ó��');";
					querySet[61] = "insert into Charts values(6,140103001,070576,5546,040089,'���帧 ������ ġ��');";
					querySet[62] = "insert into Charts values(7,140109026,050101,4543,050021,'������ ó��');";
					querySet[63] = "insert into Charts values(8,140226102,091001,9768,050302,'�κи���� �Ǻ��̽�');";
					querySet[64] = "insert into Charts values(9,140303003,091001,4234,104145,'3�� ô�� ����');";
					querySet[65] = "insert into Charts values(10,140308087,062019,7643,070804,'�忰�� ó��');";
					
					querySet[66] = "insert into Charts values(11,130516023,980312,2345,050302,'Ÿ�̷��� �߰� ó��');";
					querySet[67] = "insert into Charts values(12,130628100,020403,3545,040089,'Ʈ���� ��ȭ�� �߰� ó��');";
					querySet[68] = "insert into Charts values(13,131205056,080543,3424,070605,'MRI ��� : ��� 2�ܰ�');";
					querySet[69] = "insert into Charts values(14,140103001,070576,5546,040089,'���帧 ũ�� ó��');";
					querySet[70] = "insert into Charts values(15,140303003,091001,4234,104145,'������� Ȯ�� �� �����ֻ� ó��');";
					querySet[71] = "commit;";

					// ������ �������� ��
					for (int i = 0; i < 72; i++) {
						ires = stmt.executeUpdate(querySet[i]);
					}
					txtResult.setText("DB�ʱ�ȭ �Ϸ�!");
				} catch (Exception e3) {
					txtResult.setText("Error : " + e3.getMessage());
					System.out.println("SQL Error :" + e3.getMessage());
				}

			}
		} catch (Exception e2) {
			System.out.println("���� �б� ���� :" + e2);
			/*
			 * } finally { try { if (rs != null) rs.close(); if (stmt != null) stmt.close();
			 * if (con != null) con.close(); } catch (Exception e3) { // TODO: handle
			 * exception }
			 */
		}

	}
	public static void main(String[] args) {
		HospitalDatabase BLS = new HospitalDatabase();

		// BLS.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// BLS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		BLS.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				try {
					con.close();
				} catch (Exception e4) {
				}
				System.out.println("���α׷� ���� ����!");
				System.exit(0);
			}
		});
	}
}
