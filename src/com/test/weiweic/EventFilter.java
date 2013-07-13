package com.test.weiweic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class EventFilter
 */
@WebServlet("/EventFilter")
public class EventFilter extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EventFilter() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Double priceUp = Double.parseDouble(request.getParameter("priceUp"));
		Double priceDown = Double
				.parseDouble(request.getParameter("priceDown"));
		Double radius = Double.parseDouble(request.getParameter("radius"));
		Double latitude = Double.parseDouble(request.getParameter("latitude"));
		Double longtitude = Double.parseDouble(request
				.getParameter("longitude"));
		int numberOfPersons = Integer.parseInt(request.getParameter("number"));


		String start = request.getParameter("startTime");
		String end = request.getParameter("endTime");
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
			//selct event.evenid from signup left join event on price<500 and signup.eventid=event.eventid
			String sql = "SELECT event.eventid, event.latitude, event.longitude FROM signup left join event on price<=" + priceUp + 
					" AND price>="+ priceDown + " AND starttime>=" + starttime +
					" AND endtime<=" + endtime + " AND signup.eventid=event.eventid";

			ResultSet rs = connection.createStatement().executeQuery(sql);
			PrintWriter out = response.getWriter();
			@SuppressWarnings("rawtypes")
			ArrayList list = new ArrayList();
			Map<String, Integer> map = new HashMap<String, Integer>();
			while (rs.next()) {
				String id = rs.getString("eventid");
				double lat = Double.parseDouble(rs.getString("latitude"));
				double lon =Double.parseDouble(rs.getString("longitude")); 
				double dist = distFrom(lat, lon, latitude, longtitude);
				if(dist > radius){
					continue;
				}
				list.add(id);
				if (! map.containsKey(id)){
					int num = 1;
					map.put(id, num);
				}else{
					int num = (Integer)map.get(id);
					num ++;
					map.put(id, num);
				}
			}
			ArrayList<String> newList = new ArrayList<String>();
			for(String id: map.keySet()){
				int num = map.get(id);
				if (num <= numberOfPersons){
					newList.add(id);
				}
			}
			
			JSONObject obj = new JSONObject();
			obj.put("eventId", newList);

			out.println(obj);
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

	private double distFrom(double lat1, double lng1, double lat2, double lng2) {
		   double earthRadius = 3958.75;
		   double dLat = Math.toRadians(lat2-lat1);
		   double dLng = Math.toRadians(lng2-lng1);
		   double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		              Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
		              Math.sin(dLng/2) * Math.sin(dLng/2);
		   double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		   double dist = earthRadius * c;
		   return dist;
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
