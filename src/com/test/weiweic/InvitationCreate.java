package com.test.weiweic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet implementation class UserCreate
 */
@WebServlet("/InvitationCreate")
public class InvitationCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InvitationCreate() {
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
		int eventId = Integer.parseInt(request.getParameter("eventId"));
		int senderId = Integer.parseInt(request.getParameter("senderId"));
		int receiverId = Integer.parseInt(request.getParameter("receiverId"));
		String message = request.getParameter("message");
		int status = 0;
		
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin", "*");
		
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
					"1234qwer!Q");
			String sql = "INSERT INTO invitation (eventid, senderid, receiverid," +
					"message,status) "
					+ "VALUES (?,?,?,?,?)";

			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setInt(1, eventId);
			stmt.setInt(2, senderId);
			stmt.setInt(3, receiverId);
			stmt.setString(4, message);
			stmt.setInt(5, status);
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
