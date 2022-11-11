The java backend files basically do what Spring does in terms of they create an API endpoint but without using Spring.

The end result of the "backend files" folder's java and HTML files working together is a JSON array that is structured like this:

[
  {
    "hotelName": "Money Maker",
    "roomsInfoList": [
      {
        "roomNumber": 100,
        "nightlyRent": 125,
        "startDate": "2022-11-01",
        "endDate": "2022-11-30",
        "nightsOfRent": 29,
        "totalRentRevenueForThisRoom": 3625.0
      },
      {
        "roomNumber": 300,
        "nightlyRent": 400,
        "startDate": "2023-01-11",
        "endDate": "2023-02-05",
        "nightsOfRent": 25,
        "totalRentRevenueForThisRoom": 10000.0
      },
      {
        "roomNumber": 200,
        "nightlyRent": 200,
        "startDate": "2022-11-11",
        "endDate": "2022-12-02",
        "nightsOfRent": 21,
        "totalRentRevenueForThisRoom": 4200.0
      },
      {
        "roomNumber": 201,
        "nightlyRent": 205,
        "startDate": "2022-11-11",
        "endDate": "2022-12-02",
        "nightsOfRent": 21,
        "totalRentRevenueForThisRoom": 4305.0
      }
    ],
    "totalRentDollars": 22130.0
  },
  {
    "hotelName": "Hotel of Horrors",
    "roomsInfoList": [
      {
        "roomNumber": 200,
        "nightlyRent": 200,
        "startDate": "2022-11-11",
        "endDate": "2022-12-02",
        "nightsOfRent": 21,
        "totalRentRevenueForThisRoom": 4200.0
      },
      {
        "roomNumber": 201,
        "nightlyRent": 205,
        "startDate": "2022-11-11",
        "endDate": "2022-12-02",
        "nightsOfRent": 21,
        "totalRentRevenueForThisRoom": 4305.0
      }
    ],
    "totalRentDollars": 8505.0
  },
  {
    "hotelName": "On the Cheap",
    "roomsInfoList": [
      {
        "roomNumber": 200,
        "nightlyRent": 200,
        "startDate": "2022-11-11",
        "endDate": "2022-12-02",
        "nightsOfRent": 21,
        "totalRentRevenueForThisRoom": 4200.0
      },
      {
        "roomNumber": 100,
        "nightlyRent": 125,
        "startDate": "2022-11-11",
        "endDate": "2022-12-02",
        "nightsOfRent": 21,
        "totalRentRevenueForThisRoom": 2625.0
      }
    ],
    "totalRentDollars": 6825.0
  }
]

... and this JSON array can be used as an endpoint for the javascrcipt in the "frontend files" folder to consume.
