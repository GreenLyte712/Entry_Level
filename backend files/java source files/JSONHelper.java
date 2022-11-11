package com.greenlyte712;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@SuppressWarnings("unused")
public class JSONHelper
{

	public static void jsonHelperFunction(Set<Hotel> hotelSetForJSON)
	{

		try (FileWriter file = new FileWriter("C:\\xampp\\htdocs\\Random\\Hotel JSON Test\\hotelsJSONForAppWithSet.json"))
		{
			file.write(hotelSetForJSON.toString());
			file.flush();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void jsonHelperFunction(List<Hotel> hotelArrayList)
	{

		try (FileWriter file = new FileWriter(
				"C:\\xampp\\htdocs\\Random\\Hotel JSON Test\\hotelsJSONForAppWithList.json"))
		{
			file.write(hotelArrayList.toString());
			file.flush();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public static void saveData(Set<Hotel> hotelSet)
	{

		JSONArray hotelJSONArray = new JSONArray();

		Iterator<Hotel> itr = hotelSet.iterator();
		while (itr.hasNext())
		{
			hotelJSONArray.add(hotelJSONArray.size(), JSONArrayElementGenerator(itr.next()));

		}

		try (FileWriter file = new FileWriter(
				"C:\\xampp\\htdocs\\Random\\Hotel JSON Test\\forTheSaved\\savedJSONData.json"))
		{
			file.write(hotelJSONArray.toString());
			file.flush();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public static void loadData(Hotel hotel)
	{
		JSONParser parser = new JSONParser();
		Object obj = null;
		try
		{
			obj = parser.parse(
					new FileReader("C:\\xampp\\htdocs\\Random\\Hotel JSON Test\\forTheSaved\\savedJSONData.json"));
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray savedDataJSONArray = (JSONArray) obj;

		for (int i = 0; i < savedDataJSONArray.size(); i++)
		{
			List<?> roomsExtractionList = new ArrayList();
			List<?> nightRentAmntExtractionList = new ArrayList();
			List<?> startDateExtractionList = new ArrayList();
			List<?> endDateExtractionList = new ArrayList();

			roomsExtractionList = getEveryNth((List) ((JSONObject) savedDataJSONArray.get(i)).get("savedRoomsInfoList"),
					4, 0);
			nightRentAmntExtractionList = getEveryNth(
					(List) ((JSONObject) savedDataJSONArray.get(i)).get("savedRoomsInfoList"), 4, 1);
			startDateExtractionList = getEveryNth(
					(List) ((JSONObject) savedDataJSONArray.get(i)).get("savedRoomsInfoList"), 4, 2);
			endDateExtractionList = getEveryNth(
					(List) ((JSONObject) savedDataJSONArray.get(i)).get("savedRoomsInfoList"), 4, 3);

			if ((boolean) ((String) ((JSONObject) savedDataJSONArray.get(i)).get("savedHotelName"))
					.equalsIgnoreCase(hotel.getName()))
			{
				for (int j = 0; j < roomsExtractionList.size(); j++)
				{
					hotel.setRooms(hotel.getRooms(),
							new Room((int) ((Long) roomsExtractionList.get(j)).intValue(),
									new BigDecimal((Long) nightRentAmntExtractionList.get(j)),
									LocalDate.parse((String) startDateExtractionList.get(j),
											DateTimeFormatter.ofPattern("yyyy-MM-dd")),
									LocalDate.parse((String) endDateExtractionList.get(j),
											DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
				}
			}

//		     System.out.println(roomsExtractionList);
//		     System.out.println(nightRentAmntExtractionList);
//		     System.out.println(startDateExtractionList);
//		     System.out.println(endDateExtractionList);

		}

//		List<Object> testing = (List)((JSONObject) savedDataJSONArray.get(0)).get("savedRoomsInfoList");
//		
//		System.out.println("The entire List:\n");
//		System.out.println(testing);// prints the List, seems to be working...
//		System.out.println();
//		
//		System.out.println("Rooms Extraction:\n");
//
//		List<?> roomsExtractionList = getEveryNth(testing, 4, 0);
//		System.out.println(roomsExtractionList);
//		System.out.println();
//		
//		System.out.println("nightly rent amount for each room Extraction:\n");
//
//		List<?> nightRentAmntExtractionList = getEveryNth(testing, 4, 1);
//		System.out.println(nightRentAmntExtractionList);
//		System.out.println();
//		
//		System.out.println("start date for each room Extraction:\n");
//
//		
//		List<?> startDateExtractionList = getEveryNth(testing, 4, 2);
//		System.out.println(startDateExtractionList);
//		System.out.println();
//		
//		System.out.println("end date for each room Extraction:\n");
//
//		
//		List<?> endDateExtractionList = getEveryNth(testing, 4, 3);
//		System.out.println(endDateExtractionList);
//		

//		List<Object> roomzList  = getEveryNth(testing , 4, 0);
//	    System.out.println(roomzList);
//		

		// savedDataJSONArray.stream().forEach(e -> System.out.println(((JSONObject)
		// e).get("savedHotelName")));

		// savedDataJSONArray.stream().forEach(e -> System.out.println(((JSONObject)
		// e).get("savedRoomsInfoList")));

	}

	@SuppressWarnings("unchecked")
	static List<Object> getEveryNth(List<Object> list, int numberInGroup, int infoPlace)
	{

		@SuppressWarnings("rawtypes")
		List result = new ArrayList();

		for (int i = 0; i < list.size(); i += numberInGroup)
		{
			result.add(list.get(i + infoPlace));
		}

		return result;

	}

	@SuppressWarnings("unchecked")
	public static JSONObject JSONArrayElementGenerator(Hotel hotel)
	{

		JSONObject hotelDetails = new JSONObject();

		hotelDetails.put("savedHotelName", hotel.getName());

		JSONArray roomsInfoList = new JSONArray();

		for (Room room : hotel.getRooms())
		{

			roomsInfoList.add(room.getRoomNumber());
			roomsInfoList.add(room.getNightlyRentAmount());
			roomsInfoList.add(room.getStartDate().toString());
			roomsInfoList.add(room.getEndDate().toString());

		}

		hotelDetails.put("savedRoomsInfoList", roomsInfoList);

		return hotelDetails;

	}

	@SuppressWarnings("unchecked")
	public static void loadDataTESTONLY()
	{
		JSONParser parser = new JSONParser();
		Object obj = null;
		try
		{
			obj = parser.parse(
					new FileReader("C:\\xampp\\htdocs\\Random\\Hotel JSON Test\\forTheSaved\\savedJSONData.json"));
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray savedDataJSONArray = (JSONArray) obj;

		for (int i = 0; i < savedDataJSONArray.size(); i++)
		{
//		System.out.println(((JSONObject) savedDataJSONArray.get(i)).get("savedHotelName"));
//		System.out.println(((JSONObject) savedDataJSONArray.get(i)).get("savedRoomsInfoList"));
			@SuppressWarnings("rawtypes")
			List testing = (List) ((JSONObject) savedDataJSONArray.get(i)).get("savedRoomsInfoList");

		}
		List<Object> testing = (List) ((JSONObject) savedDataJSONArray.get(0)).get("savedRoomsInfoList");

		System.out.println("The entire List:\n");
		System.out.println(testing);// prints the List, seems to be working...
		System.out.println();

		System.out.println("Rooms Extraction:\n");

		List<?> roomsExtractionList = getEveryNth(testing, 4, 0);
		System.out.println(roomsExtractionList);
		System.out.println();

		System.out.println("nightly rent amount for each room Extraction:\n");

		List<?> nightRentAmntExtractionList = getEveryNth(testing, 4, 1);
		System.out.println(nightRentAmntExtractionList);
		System.out.println();

		System.out.println("start date for each room Extraction:\n");

		List<?> startDateExtractionList = getEveryNth(testing, 4, 2);
		System.out.println(startDateExtractionList);
		System.out.println();

		System.out.println("end date for each room Extraction:\n");

		List<?> endDateExtractionList = getEveryNth(testing, 4, 3);
		System.out.println(endDateExtractionList);

//	List<Object> roomzList  = getEveryNth(testing , 4, 0);
//    System.out.println(roomzList);
//	

		// savedDataJSONArray.stream().forEach(e -> System.out.println(((JSONObject)
		// e).get("savedHotelName")));

		// savedDataJSONArray.stream().forEach(e -> System.out.println(((JSONObject)
		// e).get("savedRoomsInfoList")));

	}

}

//////////////////////

//JSONArray hotelJSONArray = new JSONArray();
//
//Iterator<Hotel> itr = hotelListForJSON.iterator();
//while (itr.hasNext())
//{
//	hotelJSONArray.add(hotelJSONArray.size(), JSONArrayElementGenerator(itr.next()));
//
//}
