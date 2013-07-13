package com.test.weiweic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class EventCreate
 */
@WebServlet("/EventCreate")
public class EventCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EventCreate() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String name = request.getParameter("name");
		String description = request.getParameter("description");
		double price = Double.parseDouble(request.getParameter("price"));
		String start = request.getParameter("starttime");
		String end = request.getParameter("endtime");
		int number  = Integer.parseInt(request.getParameter("number"));
		String type = request.getParameter("type");
		//start = "2007-10-10 12:12:12";
		//end = "2007-10-10 13:13:13";
		DateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		// java.sql.Date sqltDate= new java.sql.Date(parsedUtilDate.getTime());
		//SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");

		long starttime = 0;
		long endtime = 0;
		try {
			starttime = formater.parse(start).getTime();  
			endtime = formater.parse(end).getTime();
			
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String location = request.getParameter("location");
		double latitude = Double.parseDouble(request.getParameter("latitude"));
		double longitude =  Double.parseDouble(request.getParameter("longitude"));
		response.setContentType("application/json");

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return;
		}

		System.out.println("PostgreSQL JDBC Driver Registered!");
		Connection connection = null;

		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/shareit", "postgres",
					"root");
			String sql = "INSERT INTO event (name,description,starttime,endtime,location,latitude, longitude, price,number,type) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, name);
			stmt.setString(2, description);
			stmt.setDouble(3, starttime);
			
			stmt.setDouble(4, endtime);
			stmt.setString(5, location);
			stmt.setDouble(6, latitude);
			stmt.setDouble(7, longitude);
			stmt.setDouble(8, price);
			stmt.setInt(9, number);
			stmt.setString(10,type);
			stmt.executeUpdate();
			
			sql = "SELECT eventid FROM event ORDER BY eventid DESC LIMIT 1";
			Statement stat = connection.createStatement();
			ResultSet set = stat.executeQuery(sql);
			PrintWriter out = response.getWriter();
			JSONObject obj = new JSONObject();
			set.next();
			int eventid = set.getInt("eventid");
			obj.put("eventId", eventid);

			out.print(obj);
			out.flush();
			out.close();

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
