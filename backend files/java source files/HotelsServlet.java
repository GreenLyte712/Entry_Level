package com.greenlyte712;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


@WebServlet("/Hotels")
public class HotelsServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	
	public HotelsServlet()
	{
		super();
		
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	//loadedRoomsList is a list of rooms coming from a .csv file.
	List<Room> loadedRoomsList = CSVHelper.parse();
	//hotelHashMap is a HashMap to store Hotel objects so that there is no need create a reference like hotelOne, hotemTwo, etc.
	Map<String, Hotel> hotelHashMap = new HashMap<String, Hotel>();
	//HashSet of Hotels so that only unique Hotel objs are placed there, just in case.
	Set<Hotel> hotelSetForJSON = new HashSet<Hotel>();

	// initializer block just to create three Hotels so that later you can get by using hotelHashMap.get("<name of the hotel>"). 
	{
		hotelHashMap.put("Money Maker", new Hotel("Money Maker", "City A"));
		hotelHashMap.put("On the Cheap", new Hotel("On the Cheap", "City B"));
		hotelHashMap.put("Hotel of Horrors", new Hotel("Hotel of Horrors", "City C"));
		
		try
		{
			JSONHelper.loadData(hotelHashMap.get("Money Maker"));
			JSONHelper.loadData(hotelHashMap.get("On the Cheap"));
			JSONHelper.loadData(hotelHashMap.get("Hotel of Horrors"));
		} catch (Exception e)
		{
			System.out.println(" ");
		}
		
		hotelSetForJSON.add(hotelHashMap.get("Money Maker"));
		hotelSetForJSON.add(hotelHashMap.get("On the Cheap"));
		hotelSetForJSON.add(hotelHashMap.get("Hotel of Horrors"));


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		PrintWriter out = response.getWriter();

		// hotelListForJSON.forEach((n) -> System.out.println(n.getName()));

		// This is how to get JSON Request Object from the frontend
		StringBuilder jsonBuff = new StringBuilder();
		String line = null;
		try
		{
			BufferedReader reader = request.getReader();// <<<look here for the request...
			while ((line = reader.readLine()) != null)
				jsonBuff.append(line);
		} catch (Exception e)
		{
			System.out.println("error...");
		}
		System.out.println();
		System.out.println("Request's JSON string :" + jsonBuff.toString());
		System.out.println();   

		JSONObject jsonObject = (JSONObject) JSONValue.parse(jsonBuff.toString());
		// (above) This is how to get JSON Request Object from the frontend

		// getting all the data necessary for a Hotel from the JSONObject.
		String dateStartStr = (String) jsonObject.get("dateStart");
		String dateEndStr = (String) jsonObject.get("dateEnd");
		int roomNumber = Integer.parseInt((String) jsonObject.get("roomNumber"));
		String hotelName = (String) jsonObject.get("hotelName");
		LocalDate dateStartLD = LocalDate.parse(dateStartStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate dateEndLD = LocalDate.parse(dateEndStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		System.out.println("For " + hotelHashMap.get(hotelName).getName() + ":");
		// this is the main part (i.e. "meat") of the program.
		if (hotelName.equalsIgnoreCase("Money Maker"))
		{
			meat(hotelName, roomNumber, dateStartLD, dateEndLD, out);
		}
		if (hotelName.equalsIgnoreCase("On the Cheap"))   
		{
			meat(hotelName, roomNumber, dateStartLD, dateEndLD, out);
		}
		if (hotelName.equalsIgnoreCase("Hotel of Horrors"))
		{
			meat(hotelName, roomNumber, dateStartLD, dateEndLD, out);
		}

		


		//printing HashSet of Hotels to see what is in there. The Hotel class' toString method is a JSON representation of the Hotel. The HashSet is not sorted in any way.
		System.out.println("*** hotelListForJSON as HashSet ***");
		System.out.println(hotelSetForJSON);
		System.out.println();
		// Copying Hotel HashSet into a Hotel TreeSet so that it is sorted, this is possible by the fact that Hotel class implements the Comparable interface.
		Set<Hotel> treeSet = new TreeSet<Hotel>(hotelSetForJSON);
		System.out.println("*** hotelListForJSON as TreeSet ***");

		System.out.println(treeSet);
		// jsonHelperFunction() takes in either a Set or a List.
		JSONHelper.jsonHelperFunction(treeSet);
		JSONHelper.saveData(treeSet);
		/////////////////////////////////////////////////////////////////////////

	}
	// the main part of the program's method definition
	void meat(String hotelName, int roomNumber, LocalDate dateStartLD, LocalDate dateEndLD, PrintWriter out)
	{
		//roomNumber is an int type member of the Room class.
		List<Integer> tempRoomsNumberListToCheckIfTheyAreAvailable = new ArrayList<Integer>();
		// getRooms() returns a List of rooms that the hotel has (those that are rented), so the forEach adds each room number from that List into the tempRoomsNumberListToCheckIfTheyAreAvailable.
		hotelHashMap.get(hotelName).getRooms()
				.forEach((room) -> {
					System.out.println(room.getEndDate());
					System.out.println(dateStartLD);
					System.out.println(room.getEndDate().isAfter(dateStartLD));
				
				if(room.getEndDate().isAfter(dateStartLD))
				
				tempRoomsNumberListToCheckIfTheyAreAvailable.add(room.getRoomNumber());
				
				});

		if (!tempRoomsNumberListToCheckIfTheyAreAvailable.contains(roomNumber))
		{
			//to get price from the loadedRoomsList based on the room number selected, the stream().filter() function should return one Room object, and so .get(0) is the only option.
			BigDecimal price = loadedRoomsList.stream().filter(room -> room.getRoomNumber() == roomNumber)
					.collect(Collectors.toList()).get(0).getNightlyRentAmount();
			//setting the new room number to get placed into the Hotel's Rooms List.
			hotelHashMap.get(hotelName).setRooms(hotelHashMap.get(hotelName).getRooms(),
					new Room(roomNumber, price, dateStartLD, dateEndLD));
		}
		
		//setting the sum of all rented rooms
		BigDecimal sum = new BigDecimal("0.00");
		for (Room room : hotelHashMap.get(hotelName).getRooms())
		{

			sum = sum.add(room.getTotalRentAmount());

		}

		hotelHashMap.get(hotelName).setTotalRentDollars(sum.setScale(2, RoundingMode.HALF_UP));

		System.out.println("\nList of Rooms Rented: \n");

		for (Room room : hotelHashMap.get(hotelName).getRooms())
		{

			System.out.println("Room Number: " + room.getRoomNumber());
			System.out.println("rent nights: " + room.getDaysOfRent());
			System.out.println("total rent for this room: " + room.getTotalRentAmount());
			System.out.println("-----------------------------------------");

		}

		System.out.println("Total for this hotel: " + hotelHashMap.get(hotelName).getTotalRentDollars());
		System.out.println("-----------------------------------------");

		//printing things to the webpage... the webpage has a div for the Servlet response.
		if (tempRoomsNumberListToCheckIfTheyAreAvailable.contains(roomNumber))
			out.println("<p class='unavailable'> Room " + roomNumber + " is unavilable </p>");
		else
			out.println("<p class='success'> Thank you for renting Room " + roomNumber + "! </p>");

	}

}
