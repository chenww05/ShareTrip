package com.test.weiweic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
 * Servlet implementation class UserFetch
 */
@WebServlet("/UserFetch")
public class UserFetch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserFetch() {
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
		String id = request.getParameter("userId");
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
				String sql = "SELECT * FROM username where id='" + id + "'";

				ResultSet rs = connection.createStatement().executeQuery(sql);
				String username = null, gender=null, password=null, facebookid=null, linkedinid=null;
				while (rs.next()) {
					username = rs.getString("username");
					gender = rs.getString("gender");
					password = rs.getString("password");
					facebookid = rs.getString("facebookid");
					linkedinid = rs.getString("linkedinid");
					System.out.println(username + "\t" + gender + "\t" + password
							+ "\t" + facebookid + "\t" + linkedinid);
					break;
				}
				PrintWriter out = response.getWriter();
				JSONObject obj = new JSONObject();

				obj.put("username", username);
				obj.put("userid", id);
				obj.put("gender", gender);
				obj.put("password", password);
				obj.put("facebookid", facebookid);
				obj.put("linkedinid", linkedinid);
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
