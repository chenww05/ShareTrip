package com.test.weiweic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class InvitationStatus
 */
@WebServlet("/InvitationStatus")
public class InvitationStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InvitationStatus() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				String receiverId = request.getParameter("receiverId");
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
						String sql = "SELECT * FROM invitation where receiverId='" + receiverId + "'";

						ResultSet rs = connection.createStatement().executeQuery(sql);
						int evenId = 0, senderId=0,status=0,invitationId=0;
						
						String message = null;
						List list = new ArrayList<Map>();
						while (rs.next()) {
							Map<String, String> map = new HashMap<String, String>();
							invitationId = rs.getInt("invitationId");
							map.put("invitationId", Integer.toString(invitationId));
							evenId = rs.getInt("eventId");
							map.put("eventId", Integer.toString((evenId)));
							senderId = rs.getInt("senderId");
							map.put("senderId", Integer.toString((senderId)));
							status = rs.getInt("status");
							map.put("status", Integer.toString((status)));
							message= rs.getString("message");
							map.put("message", message);
							map.put("receiverId", receiverId);
							list.add(map);
							
						}
						PrintWriter out = response.getWriter();
						JSONObject obj = new JSONObject();

						obj.put("invitations", list);
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
