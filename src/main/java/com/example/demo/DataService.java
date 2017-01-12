package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;

public class DataService {

	private String datebaseIp = "";
	private String datebasePort = "";
	private String datebaseName = "";
	private String datebaseUsername = "";
	private String datebasePassword = "";

	public DataService() {
		this.setDatebaseIp(System.getenv("MYSQL_SERVICE_HOST").replaceAll("\"", ""));
		this.setDatebasePort(System.getenv("MYSQL_SERVICE_PORT").replaceAll("\"", ""));
		this.setDatebaseName(System.getenv("MYSQL_DATABASE").replaceAll("\"", ""));
		this.setDatebaseUsername(System.getenv("MYSQL_USER").replaceAll("\"", ""));
		this.setDatebasePassword(System.getenv("MYSQL_PASSWORD").replaceAll("\"", ""));

		this.setupDatabase();
	}

	public String getDatebaseIp() {
		return datebaseIp;
	}

	public void setDatebaseIp(String datebaseIp) {
		this.datebaseIp = datebaseIp;
	}

	public String getDatebasePort() {
		return datebasePort;
	}

	public void setDatebasePort(String datebasePort) {
		this.datebasePort = datebasePort;
	}

	public String getDatebaseName() {
		return datebaseName;
	}

	public void setDatebaseName(String datebaseName) {
		this.datebaseName = datebaseName;
	}

	public String getDatebaseUsername() {
		return datebaseUsername;
	}

	public void setDatebaseUsername(String datebaseUsername) {
		this.datebaseUsername = datebaseUsername;
	}

	public String getDatebasePassword() {
		return datebasePassword;
	}

	public void setDatebasePassword(String datebasePassword) {
		this.datebasePassword = datebasePassword;
	}

	private Connection generateConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		String url = "jdbc:mysql://{0}:{1}/{2}?user={3}&password={4}";
		String connurl = MessageFormat.format(url, getDatebaseIp(), getDatebasePort(), getDatebaseName(),
				getDatebaseUsername(), getDatebasePassword());
		return DriverManager.getConnection(connurl);
	}

	public void executeSql(Connection conn, String sql) throws Exception {
		Statement stmt = conn.createStatement();
		stmt.execute(sql);
		stmt.close();
	}

	public void setupDatabase() {
		try {
			Connection conn = generateConnection();
			executeSql(conn,
					"create table office (id int(2) primary key auto_increment, country char(25), city char(25), contact char(25), mobile char(25) )");

			executeSql(conn, "insert into office (country, city, contact, mobile) values ('China','Shenzhen','Zhang Shan','13800138000');");
			executeSql(conn, "insert into office (country, city, contact, mobile) values ('India','Armrisar','Li Si','13500135000');");
			executeSql(conn, "insert into office (country, city, contact, mobile) values ('Malaysia','Pengang','Wang Wu','13900139000');");
			executeSql(conn, "insert into office (country, city, contact, mobile) values ('Sir Lanka','Galle','Chen Liu','13600136000');");
			executeSql(conn, "insert into office (country, city, contact, mobile) values ('Japan','Matsusaka','Wang Er Ma zi','13700137000');");
			//executeSql(conn, "insert into office (country, city) values ('Austria','Salzburg');");
			//executeSql(conn, "insert into office (country, city) values ('Belgium','Antpur');");

			//Bad practice, demo only
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Office> queryOffice() throws Exception {
		ArrayList<Office> list = new ArrayList<Office>();
		Connection conn = generateConnection();
		Statement stmt = conn.createStatement();

		String sql = "select * from office";

		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			Office office = new Office();
			office.setCountry(rs.getString("country"));
			office.setCity(rs.getString("city"));
			office.setContact(rs.getString("contact"));
			office.setContact(rs.getString("mobile"));
			list.add(office);
		}
		
		//Bad practice, demo only
		rs.close();
		stmt.close();
		conn.close();
		return list;

	}
}
