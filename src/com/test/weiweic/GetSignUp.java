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
import java.util.ArrayList;
import java.util.List;

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
@WebServlet("/GetSignUp")
public class GetSignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetSignUp() {
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

		int id = Integer.parseInt(request.getParameter("id"));
		
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
					"1234qwer!Q");
			String sql = "SELECT userid from signup where eventid=" 
					+ id;

			Statement stat = connection.createStatement();
			ResultSet set = stat.executeQuery(sql);
			List<Integer> list = new ArrayList<Integer>();
			while(set.next()){
				int uid = set.getInt("userid");
				list.add(new Integer(uid));
			}
			JSONObject obj = new JSONObject();
			obj.put("eventid",id);
			obj.put("userids", list);
		
			PrintWriter out = response.getWriter();

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
