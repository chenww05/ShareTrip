package com.test.weiweic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class EventFetch
 */
@WebServlet("/EventFetch")
public class EventFetch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventFetch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("eventId");
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
				String sql = "SELECT * FROM event where eventid='" + id + "'";

				ResultSet rs = connection.createStatement().executeQuery(sql);
				String name = null, description=null, location=null, type=null;
				double latitude = 0.0, longitude = 0.0, start= 0.0, end = 0.0,price =0.0;
				int number =0;
				while (rs.next()) {
					name = rs.getString("name");
					description = rs.getString("description");
					location = rs.getString("location");
					latitude = rs.getDouble("latitude");
					longitude= rs.getDouble("longitude");
					start = rs.getDouble("starttime");
					end = rs.getDouble("endtime");
					price = rs.getDouble("price");
					number=rs.getInt("number");
					type=rs.getString("type");
					System.out.println(name + "\t" + location + "\t" + description
							+ "\t" + latitude + "\t" + longitude);
					break;
				}
				PrintWriter out = response.getWriter();
				JSONObject obj = new JSONObject();

				obj.put("name", name);
				obj.put("eventId", id);
				obj.put("description", description);
				obj.put("location", location);
				obj.put("latitude", latitude);
				obj.put("longitude", longitude);
				obj.put("starttime", start);
				obj.put("endtime", end);
				obj.put("price",price);
				obj.put("number", number);
				obj.put("type", type);
				out.print(obj);
				out.flush();
				out.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
