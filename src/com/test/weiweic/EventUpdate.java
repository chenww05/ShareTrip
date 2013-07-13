package com.test.weiweic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EventCreate
 */
@WebServlet("/EventCreate")
public class EventUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EventUpdate() {
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

		String param = null;
		Map<String, String> attrMap = new HashMap<String, String>();
		int eventId = Integer.parseInt(request.getParameter("id"));
		
		if((param = request.getParameter("price")) != null)
			attrMap.put("price", param);
		
		if((param = request.getParameter("starttime")) != null)
			attrMap.put("starttime", param);
		
		if((param = request.getParameter("endtime")) != null)
			attrMap.put("endtime", param);
		
		if((param = request.getParameter("location")) != null)
			attrMap.put("location", param);
		
		if((param = request.getParameter("latitude")) != null)
			attrMap.put("latitude", param);
		
		if((param = request.getParameter("longitude")) != null)
			attrMap.put("longitude", param);
				
		//start = "2007-10-10 12:12:12";
		//end = "2007-10-10 13:13:13";
		DateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		// java.sql.Date sqltDate= new java.sql.Date(parsedUtilDate.getTime());
		//SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");

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
					"postgres");
			
			List<String> keys = new ArrayList<String>(attrMap.keySet());
			StringBuilder sql = new StringBuilder("UPDATE event SET ");
			for(int i = 0; i < keys.size(); i++) {
				sql.append(keys.get(i)); 
				sql.append("=? ");
				if(i != keys.size()-1) {
					sql.append(", ");
				}
			}
			sql.append(" where id=?");

			PreparedStatement stmt = connection.prepareStatement(sql.toString());

			for(int i = 0; i < keys.size(); i++) {
				String str = keys.get(i);
				if(str.equals("price"))
					stmt.setDouble(i+1, Double.parseDouble(attrMap.get(str)));
				else if(str.equals("location"))
					stmt.setString(i+1, str);
				else if(str.equals("latitude"))
					stmt.setDouble(i+1, Double.parseDouble(str));
				else if(str.equals("longitude"))
					stmt.setDouble(i+1, Double.parseDouble(str));
				else if(str.equals("starttime") || str.equals("endtime")) {
					try {
						stmt.setDouble(i+1, formater.parse(str).getTime());  						
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

			stmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
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
