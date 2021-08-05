
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
		super("14010448 이경후");
		layInit();
		conDB();
		setVisible(true);
		setBounds(0, 0, 1400, 500); // 가로위치,세로위치,가로길이,세로길이
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void layInit() {
		// 입력
		inputBox = new JTextField(15);
		inputBox.setText("");

		// Buttons
		btnDoctor = new JButton("Doctors");
		btnNurse = new JButton("Nurses");
		btnPatient = new JButton("Patients");
		btnTreatment = new JButton("Treatment");
		btnChart = new JButton("Charts");
		btnCheck = new JButton("확인");
		btnCancel = new JButton("취소");
		btnSearch1 = new JButton("Q1");
		btnSearch2 = new JButton("Q2");
		btnSearch3 = new JButton("Q3");
		btnResetDB = new JButton("RESET Database");

		// 출력
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
			// System.out.println("드라이버 로드 성공");
			txtStatus.append("드라이버 로드 성공...\n");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try { /* 데이터베이스를 연결하는 과정 */
			// System.out.println("데이터베이스 연결 준비...");
			txtStatus.append("데이터베이스 연결 준비...\n");
			con = DriverManager.getConnection(url, userid, pwd);
			// System.out.println("데이터베이스 연결 성공");
			txtStatus.append("데이터베이스 연결 성공...\n");
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
			// executeUpdate를 위한 변수
			int ires = 0;
			// 입력 버튼
			if (e.getSource() == btnDoctor) {
				checkBtn = 1; inputBox.setText("의사 테이블 삽입/삭제/변경이 가능합니다.");
				txtResult.setText("삽입 : insert into Doctors values();\n삭제: WHERE절 입력\n변경: SET/WHERE절 순차 입력('/'로 구분)");
			}
			else if(e.getSource() == btnNurse){
				checkBtn = 2;inputBox.setText("간호 테이블 삽입/삭제/변경이 가능합니다.");
				txtResult.setText("삽입 : insert into Nurses values();\n삭제: WHERE절 입력\n변경: SET/WHERE절 순차 입력('/'로 구분)");
			}
			else if(e.getSource() == btnPatient ){
				checkBtn = 3;inputBox.setText("환자 테이블 삽입/삭제/변경이 가능합니다.");
				txtResult.setText("삽입 : insert into Patients values();\n삭제: WHERE절 입력\n변경: SET/WHERE절 순차 입력('/'로 구분)");
			}
			else if(e.getSource() == btnTreatment){
				checkBtn = 4;inputBox.setText("진료 테이블 삽입/삭제/변경이 가능합니다.");
				txtResult.setText("삽입 : insert into Treatments values();\n삭제: WHERE절 입력\n변경: SET/WHERE절 순차 입력('/'로 구분)");
			}
			else if(e.getSource() == btnChart){
				checkBtn = 5;inputBox.setText("차트 테이블 삽입/삭제/변경이 가능합니다.");
				txtResult.setText("삽입 : insert into Charts values();\n삭제: WHERE절 입력\n변경: SET/WHERE절 순차 입력('/'로 구분)");
			}
			if (e.getSource() == btnCheck) {
				//981512,'소아과','이태정','M','010-333-1320','ltj@hanb2.com','과장'
	
				if (checkBtn == 1) {
					try {
						String info = inputBox.getText();
						String checkInfo = info.substring(0,1);
						int idx = 0;
						txtResult.setText("");
						//삽입 
						if (checkInfo.equals("i") || checkInfo.equals("I")) {
							query = info;
							ires = stmt.executeUpdate(query);
							txtResult.setText("삽입 성공했습니다.\n");
						}
						else {
							if (info.contains("/")) {checkInfo = "s";}
							else {checkInfo = "w";}
							//삭제 
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
								txtResult.setText("삭제에 성공했습니다.\n");
							}
							//변경
							else if (checkInfo.equals("s")) {
								String set = info.substring(0,info.indexOf("/"));
								String where = info.substring(info.indexOf("/")+1,info.length());
								query = "UPDATE Doctors set " + set +" where "+ where;
								ires = stmt.executeUpdate(query);
								txtResult.setText("변경 완료 했습니다.\n");
							}
							//오류
							else {
								txtResult.setText("잘못 입력했습니다. 테이블부터 다시 선택해 주세요.\n");
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

						//삽입 
						if (checkInfo.equals("i") || checkInfo.equals("I")) {
							query = info;
							ires = stmt.executeUpdate(query);
							txtResult.setText("삽입 성공했습니다.\n");
						}
						else {
							if (info.contains("/")) {checkInfo = "s";}
							else {checkInfo = "w";}
							//삭제 
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
								txtResult.setText("삭제에 성공했습니다.\n");
							}
							//변경
							else if (checkInfo.equals("s")) {
								//nur_name = '이상득'/nur_id = 130211
								String set = info.substring(0,info.indexOf("/"));
								String where = info.substring(info.indexOf("/")+1,info.length());
								query = "UPDATE nurses set " + set +" where "+ where;
								System.out.println(query);
								ires = stmt.executeUpdate(query);
								txtResult.setText("변경 완료 했습니다.\n");
							}
							//오류
							else {
								txtResult.setText("잘못 입력했습니다. 테이블부터 다시 선택해 주세요.\n");
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

						//삽입 
						if (checkInfo.equals("i") || checkInfo.equals("I")) {
							query = info;
							ires = stmt.executeUpdate(query);
							txtResult.setText("삽입 성공했습니다.\n");
						}
						else {
							if (info.contains("/")) {checkInfo = "s";}
							else {checkInfo = "w";}
							//삭제 
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
								txtResult.setText("삭제에 성공했습니다.\n");
							}
							//변경
							else if (checkInfo.equals("s")) {
								String set = info.substring(0,info.indexOf("/"));
								String where = info.substring(info.indexOf("/")+1,info.length());
								query = "UPDATE patients set " + set +" where "+ where;
								ires = stmt.executeUpdate(query);
								txtResult.setText("변경 완료 했습니다.\n");
							}
							//오류
							else {
								txtResult.setText("잘못 입력했습니다. 테이블부터 다시 선택해 주세요.\n");
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

						//삽입 
						if (checkInfo.equals("i") || checkInfo.equals("I")) {
							query = info;
							ires = stmt.executeUpdate(query);
							txtResult.setText("삽입 성공했습니다.\n");
						}
						else {
							if (info.contains("/")) {checkInfo = "s";}
							else {checkInfo = "w";}
							//삭제 
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
								txtResult.setText("삭제에 성공했습니다.\n");
							}
							//변경
							else if (checkInfo.equals("s")) {
								String set = info.substring(0,info.indexOf("/"));
								String where = info.substring(info.indexOf("/")+1,info.length());
								query = "UPDATE treatments set " + set +" where "+ where;
								ires = stmt.executeUpdate(query);
								txtResult.setText("변경 완료 했습니다.\n");
							}
							//오류
							else {
								txtResult.setText("잘못 입력했습니다. 테이블부터 다시 선택해 주세요.\n");
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

						//삽입 
						if (checkInfo.equals("i") || checkInfo.equals("I")) {
							query = info;
							ires = stmt.executeUpdate(query);
							txtResult.setText("삽입 성공했습니다.\n");
						}
						else {
							if (info.contains("/")) {checkInfo = "s";}
							else {checkInfo = "w";}
							//삭제 
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
								txtResult.setText("삭제에 성공했습니다.\n");
							}
							//변경
							else if (checkInfo.equals("s")) {
								String set = info.substring(0,info.indexOf("/"));
								String where = info.substring(info.indexOf("/")+1,info.length());
								query = "UPDATE charts set " + set +" where "+ where;
								ires = stmt.executeUpdate(query);
								txtResult.setText("변경 완료 했습니다.\n");
							}
							//오류
							else {
								txtResult.setText("잘못 입력했습니다. 테이블부터 다시 선택해 주세요.\n");
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
					txtResult.setText("롤백(취소) 완료했습니다.\n");
				}catch(SQLException ex) {
					txtResult.setText("ERROR : "+ex.getMessage());
				}

			} else if (e.getSource() == btnSearch1) {
				try {
					txtResult.setText("");
					if (checkBtn == 1) {
						query = "select * from Doctors;";
						rs = stmt.executeQuery(query);
						txtResult.setText("의사 정보\n");
						while (rs.next()) {
							String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4)
							+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+rs.getString(7)+"\n";
							txtResult.append(str);
						}
					}else if(checkBtn ==2) {
						query = "select * from Nurses;";
						rs = stmt.executeQuery(query);
						txtResult.setText("간호사 정보\n");
						while (rs.next()) {
							String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4)
							+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+rs.getString(7)+"\n";
							txtResult.append(str);
						}
					}else if(checkBtn == 3) {
						query = "select * from Patients;";
						rs = stmt.executeQuery(query);
						txtResult.setText("환자 정보\n");
						while (rs.next()) {
							String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4)
							+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+rs.getString(7)+"\t"+rs.getString(8)+"\t"+rs.getString(9)+"\t"+rs.getString(10)+"\n";
							txtResult.append(str);
						}
					}else if(checkBtn == 4) {
						query = "select * from Treatments;";
						rs = stmt.executeQuery(query);
						txtResult.setText("진료 정보\n");
						while (rs.next()) {
							String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4)
							+"\t"+rs.getString(5)+"\n";
							txtResult.append(str);
						}
					}else if (checkBtn ==5) {
						query = "select * from Charts;";
						rs = stmt.executeQuery(query);
						txtResult.setText("차트 정보\n");
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
			// Q1 - 박지성이 구매하지 않은 도서의 이름을 검색하라.
			else if (e.getSource() == btnSearch2) {
				txtResult.setText("");
				query = "select pat_gen,count(*)\n"
						+ "from patients\n"
						+ "where pat_id in(select pat_id from treatments where treat_date > '2014-01-01')\n"
						+ "group by pat_gen;";
				txtResult.setText("2014년도 이전에 진료기록이 있는 사람들 중 여자와 남자의 수를 구하는 질의\n\nselect pat_gen,count(*)\n"
						+ "from patients\n"
						+ "where pat_id in(select pat_id from treatments where treat_date > '2014-01-01')\n"
						+ "group by pat_gen; \n\n성별\t수\n");
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					String str = rs.getString(1) + "\t" + rs.getInt(2)+"\n";
					txtResult.append(str);
				}
			}
			// Q2 - 박지성이 구매한 도서의 출판수를 검색하라.
			else if (e.getSource() == btnSearch3) {
				txtResult.setText("");
				query = "select pat_gen,count(*)\n"
						+ "from patients \n"
						+ "where pat_id in (select distinct c.pat_id\n"
						+ "				from charts c,treatments t\n"
						+ "				where ( t.treat_date > '2014-01-01' and c.charts_contents like '%처방%' and c.pat_id = t.pat_id )\n"
						+ "				)\n"
						+ "group by pat_gen;";
				txtResult.setText("차트에 '처방'을 받은 환자들 중에서 2014-01-01 이전에 진료기록을 가진 사람들의 남녀비율을 구하는 질의\n\nselect pat_gen,count(*)\n"
						+ "from patients \n"
						+ "where pat_id in (\n"
						+ " 	select distinct c.pat_id\n"
						+ " 	from charts c,treatments t\n"
						+ " 	where ( t.treat_date > '2014-01-01' and c.charts_contents like '%처방%' and c.pat_id = t.pat_id )\n"
						+ "	)\n"
						+ "group by pat_gen;\n\n성별\t수\n");
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					String str = rs.getString(1) + "\t" + rs.getInt(2)+"\n";
					txtResult.append(str);
				}
			}
			else if (e.getSource() == btnResetDB) {
				try {
					// excuteUpdate를 위한 변수
					int reset = 0;
					txtResult.setText("");
					// 여러개의 쿼리를 작성하고 실행하기위한 문자 배열 선언
					String[] querySet = new String[80];
					// DROP을 통한 테이블 전체 삭
					query = "drop schema if exists `madang`";
					reset = stmt.executeUpdate(query);

					query = "CREATE SCHEMA IF NOT EXISTS `madang` DEFAULT CHARACTER SET utf8 ;";
					reset = stmt.executeUpdate(query);

					query = "USE `madang` ;";
					reset = stmt.executeUpdate(query);

					query = "DROP TABLE IF EXISTS Doctors,Nurses,Patients,Treatments,Charts;";
					reset = stmt.executeUpdate(query);

					// 초기 테이블을 다시 생성하기 위한 쿼리
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


					querySet[16] = "insert into Doctors values(980312,'소아과','이태정','M','010-333-1340','ltj@hanbh.com','과장');";
					querySet[17] = "insert into Doctors values(000601,'내과','안성기','M','011-222-0987','ask@hanbh.com','과장');";
					querySet[18] = "insert into Doctors values(001208,'외과','김민종','M','010-333-8743','kmj@hanbh.com','과장');";
					querySet[19] = "insert into Doctors values(020403,'피부과','이태서','M','019-777-3764','lts@hanbh.com','과장');";
					querySet[20] = "insert into Doctors values(050900,'소아과','김연아','F','010-555-3746','kya@hanbh.com','전문의');";
					querySet[21] = "insert into Doctors values(050101,'내과','차태현','M','011-222-7643','cth@hanbh.com','전문의');";
					querySet[22] = "insert into Doctors values(062019,'소아과','전지현','F','010-999-1265','jjh@hanbh.com','전문의');";
					querySet[23] = "insert into Doctors values(070576,'피부과','홍길동','M','016-333-7263','hgd@hanbh.com','전문의');";
					querySet[24] = "insert into Doctors values(080543,'방사선과','유재석','M','010-222-1263','yjs@hanbh.com','과장'); ";
					querySet[25] = "insert into Doctors values(091001,'외과','김병만','M','010-555-3542','kbm@hanbh.com','전문의');";

					querySet[26] = "insert into Nurses values(050302,'소아과','김은영','F','010-555-8751','key@hanbh.com','수간호사');";
					querySet[27] = "insert into Nurses values(050021,'내과','윤성애','F','016-333-8745','ysa@hanbh.com','수간호사');";
					querySet[28] = "insert into Nurses values(040089,'피부과','신지원','M','010-666-7646','sjw@hanbh.com','주임');";
					querySet[29] = "insert into Nurses values(070605,'방사선과','유정화','F','010-333-4588','yjh@hanbh.com','주임');";
					querySet[30] = "insert into Nurses values(070804,'내과','라하나','F','010-222-1340','nhn@hanbh.com','주임');";
					querySet[31] = "insert into Nurses values(071018,'소아과','김화경','F','019-888-4116','khk@hanbh.com','주임');";
					querySet[32] = "insert into Nurses values(100356,'소아과','이선용','M','010-777-1234','lsy@hanbh.com','간호사');";
					querySet[33] = "insert into Nurses values(104145,'외과','김현','M','010-999-8520','kh@hanbh.com','간호사');";
					querySet[34] = "insert into Nurses values(120309,'피부과','박성완','M','010-777-4996','psw@hanbh.com','간호사');";
					querySet[35] = "insert into Nurses values(130211,'외과','이서연','F','010-222-3214','lsy2@hanbh.com','간호사');";

					querySet[36] = "insert into Patients values(2345,050302,980312,'안상건','M','232345','서울','010-555-7845','ask@ab.com','회사원');";
					querySet[37] = "insert into Patients values(3545,040089,020403,'김성룡','M','543545','서울','010-333-7812','ksr@bb.com','자영업');";
					querySet[38] = "insert into Patients values(3424,070605,080543,'이종진','M','433424','부산','019-888-4859','ljjk@ab.com','회사원');";
					querySet[39] = "insert into Patients values(7675,100356,050900,'최강석','M','677675','당진','010-222-4847','cks@cc.com','회사원');";
					querySet[40] = "insert into Patients values(4533,070804,000601,'정한경','M','744533','강릉','010-777-9630','jhk@ab.com','교수');";
					querySet[41] = "insert into Patients values(5546,120309,070576,'유원현','M','765546','대구','016-777-0214','ywh@cc.com','자영업');";
					querySet[42] = "insert into Patients values(4543,070804,050101,'최재정','M','454543','부산','010-555-4187','cjj@bb.com','회사원');";
					querySet[43] = "insert into Patients values(9768,130211,091001,'이진희','F','119768','서울','010-888-3675','ljh@ab.com','교수');";
					querySet[44] = "insert into Patients values(4234,130211,091001,'오나미','F','234234','속초','010-999-6541','onm@cc.com','학생');";
					querySet[45] = "insert into Patients values(7643,071018,062019,'송성묵','M','987643','서울','010-222-5874','ssm@bb.com','학생');";

					querySet[46] = "insert into Treatments values(130516023,2345,980312,'감기, 몸살','2013-05-16');";
					querySet[47] = "insert into Treatments values(130628100,3545,020403,'피부 트러블 치료','2013-06-28');";
					querySet[48] = "insert into Treatments values(131205056,3424,080543,'목 디스크로 MRI 촬영','2013-12-05');";
					querySet[49] = "insert into Treatments values(131218024,7675,050900,'중이염','2013-12-18');";
					querySet[50] = "insert into Treatments values(131224012,4533,000601,'장염','2013-12-24');";
					querySet[51] = "insert into Treatments values(140103001,5546,070576,'여드름 치료','2014-01-03');";
					querySet[52] = "insert into Treatments values(140109026,4543,050101,'위염','2014-01-09');";
					querySet[53] = "insert into Treatments values(140226102,9768,091001,'화상치료','2014-02-26');";
					querySet[54] = "insert into Treatments values(140303003,4234,091001,'교통사고 외상치료','2014-03-03');";
					querySet[55] = "insert into Treatments values(140308087,7643,062019,'장염','2014-03-08');";

					querySet[56] = "insert into Charts values(1,130516023,980312,2345,050302,'해열제 처방');";
					querySet[57] = "insert into Charts values(2,130628100,020403,3545,040089,'박피');";
					querySet[58] = "insert into Charts values(3,131205056,080543,3424,070605,'MRI 상태 확인');";
					querySet[59] = "insert into Charts values(4,131218024,050900,7675,070804,'중이염약 처방');";
					querySet[60] = "insert into Charts values(5,131224012,000601,4533,070804,'장염약 처방');";
					querySet[61] = "insert into Charts values(6,140103001,070576,5546,040089,'여드름 레이저 치료');";
					querySet[62] = "insert into Charts values(7,140109026,050101,4543,050021,'위염약 처방');";
					querySet[63] = "insert into Charts values(8,140226102,091001,9768,050302,'부분마취와 피부이식');";
					querySet[64] = "insert into Charts values(9,140303003,091001,4234,104145,'3번 척추 수술');";
					querySet[65] = "insert into Charts values(10,140308087,062019,7643,070804,'장염약 처방');";
					
					querySet[66] = "insert into Charts values(11,130516023,980312,2345,050302,'타이레놀 추가 처방');";
					querySet[67] = "insert into Charts values(12,130628100,020403,3545,040089,'트러블 완화제 추가 처방');";
					querySet[68] = "insert into Charts values(13,131205056,080543,3424,070605,'MRI 결과 : 폐암 2단계');";
					querySet[69] = "insert into Charts values(14,140103001,070576,5546,040089,'여드름 크림 처방');";
					querySet[70] = "insert into Charts values(15,140303003,091001,4234,104145,'수술결과 확인 및 무통주사 처방');";
					querySet[71] = "commit;";

					// 각각의 쿼리들을 실
					for (int i = 0; i < 72; i++) {
						ires = stmt.executeUpdate(querySet[i]);
					}
					txtResult.setText("DB초기화 완료!");
				} catch (Exception e3) {
					txtResult.setText("Error : " + e3.getMessage());
					System.out.println("SQL Error :" + e3.getMessage());
				}

			}
		} catch (Exception e2) {
			System.out.println("쿼리 읽기 실패 :" + e2);
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
				System.out.println("프로그램 완전 종료!");
				System.exit(0);
			}
		});
	}
}
