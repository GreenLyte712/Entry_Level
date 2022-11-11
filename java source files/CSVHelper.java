package com.greenlyte712;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class CSVHelper
{

	public static List<Room> parse()
	{
		try
		{
			CSVReader reader = new CSVReaderBuilder(new FileReader(
					"C:\\Greenlyte\\712JavaWorkSpaces\\712AllRandom\\HotelWithCalendar\\src\\main\\java\\com\\greenlyte712\\hotelData1.csv"))
							.withSkipLines(1).build();

			List<Room> loadedRoomsList = reader.readAll().stream().map(data -> {
				Room roomObj = new Room(Integer.parseInt(data[0]), new BigDecimal(data[1]));

				return roomObj;
			}).collect(Collectors.toList());

			return loadedRoomsList;

		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;

	}
}
