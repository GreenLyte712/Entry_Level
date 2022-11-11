package com.greenlyte712;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Hotel implements Comparable<Hotel>
{

	private String name;
	private List<Room> rooms;
	private String location;
	private BigDecimal totalRentDollars;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<Room> getRooms()
	{
		return rooms;
	}

	public void setRooms(List<Room> rooms, Room room)
	{
		rooms.add(room);
		this.rooms = rooms;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public BigDecimal getTotalRentDollars()
	{
		return totalRentDollars;
	}

	public void setTotalRentDollars(BigDecimal totalRentDollars)
	{
		this.totalRentDollars = totalRentDollars;
	}

	public Hotel(String name, String location)
	{

		this.name = name;

		this.location = location;

		this.rooms = new ArrayList<Room>();

	}

	@Override //using this.name as the differentiator
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + name.hashCode();
		return result;
	}

	@Override //using this.name as the differentiator
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hotel other = (Hotel) obj;
		if (name.hashCode() != other.getName().hashCode())
			return false;

		return true;
	}

	@Override // returns a JSON representation of the Hotel.
	public String toString()
	{

		// this Collection will hold 6 things, the room number, nightly rent rate, start
		// date, end date, days of rent, and total rent revenue
		List<Object> roomsInfoArrayList = new ArrayList<>();
		if (this.getRooms() != null)
		{
			for (Room room : this.getRooms())
			{

				roomsInfoArrayList.add("{\"roomNumber\": "+room.getRoomNumber());

				roomsInfoArrayList.add("\"nightlyRent\": "+room.getNightlyRentAmount());
				roomsInfoArrayList.add("\"startDate\": \"" + room.getStartDate() + "\"");

				roomsInfoArrayList.add("\"endDate\": \"" + room.getEndDate() + "\"");

				roomsInfoArrayList.add("\"nightsOfRent\" :"+room.getDaysOfRent());

				roomsInfoArrayList.add("\"totalRentRevenueForThisRoom\": "+room.getTotalRentAmount() + "}");

			}

		}

		BigDecimal sum = new BigDecimal("0.00");

		if (this.getRooms() != null)
		{
			for (Room room : this.getRooms())
			{

				sum = sum.add(room.getTotalRentAmount());

			}
		}
		this.setTotalRentDollars(sum.setScale(2, RoundingMode.HALF_UP));

		String JSONStringOfHotel = "{\"hotelName\":" + "\"" + name + "\"" + "," + "\"roomsInfoList\":"
				+ roomsInfoArrayList + "," + "\"totalRentDollars\":" + this.totalRentDollars + "}";

//		String JSONStringOfHotel = "{\"totalRentDollars\":" + this.totalRentDollars + "," + "\"roomsInfoList\":"
//				+ roomsInfoArrayList + ",\"hotelName\":" + "\"" + name + "\"" + "}";

		return JSONStringOfHotel;

	}

	@Override
	// to make a sorted list have the Hotels sorted by total revenue in descending order.
	public int compareTo(Hotel otherHotel)
	{
		if (otherHotel.getTotalRentDollars().compareTo(this.totalRentDollars) == -1)
			return -1;
		else if (otherHotel.getTotalRentDollars().compareTo(this.totalRentDollars) == 1)
			return 1;
		// last else is returning a random number bc otherwise the Hotel will not get
		// placed into a TreeSet.
		else
			return 2;
	}
}
