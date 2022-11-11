import {
  createTopLevelItem,
  createSecondLevelItemWithDollars,
  createSecondLevelElement,
  makeLine,
  sortDescendingBy,
  sortAsendingBy,
  sortAsendingByDate,
  sortDescendingByDate,
} from "./helperFunctions.js";
/*



*/

//Getting the DOM elements of the html page:
const output = document.getElementById("output");
//this will be passed into the json.sort() built-in function in order to sort json array if it's not sorted already.

//Default view
fetch("hotelsJSONForAppWithSet.json")
  .then(function (response) {
    return response.json();
  })
  .then(function (obj) {
    // OPTIONAL IF NOT ALREADY SORTED IN THE BACKEND: obj.sort(GetSortOrder("totalRentDollars"));

    doLoop(obj);
  });

//Definition of the custom sort function which will be tied to buttons
function customSort(sortOrder, byWhatAttribute) {
  output.innerHTML = "";
  fetch("hotelsJSONForAppWithSet.json")
    .then(function (response) {
      return response.json();
    })
    .then(function (obj) {
      for (let i = 0; i < obj.length; i++) {
        obj[i].roomsInfoList.sort(sortOrder(byWhatAttribute));
        console.log(obj[i].roomsInfoList);
      }

      doLoop(obj);
    });
}
//Definition of the custom sort function for dates which will be tied to buttons

function customSortForDates(sortOrder) {
  output.innerHTML = "";
  fetch("hotelsJSONForAppWithSet.json")
    .then(function (response) {
      return response.json();
    })
    .then(function (obj) {
      for (let i = 0; i < obj.length; i++) {
        obj[i].roomsInfoList.sort(sortOrder());
        console.log(obj[i].roomsInfoList);
      }

      doLoop(obj);
    });
}

//Getting buttons from the DOM
let sortByRoomNumberAscendingButton = document.getElementById(
  "sortByRoomNumber-Ascending"
);
let sortByRoomNumberDescendingButton = document.getElementById(
  "sortByRoomNumber-Descending"
);
let sortByStartDateAscendingButton = document.getElementById(
  "sortByStartDate-Ascending"
);
let sortByStartDateDescendingButton = document.getElementById(
  "sortByStartDate-Descending"
);

//button color effects

let buttonsGroupArray = Array.from(
  document.getElementsByClassName("sortButtons")
);

buttonsGroupArray.forEach(function (mov) {
  mov.addEventListener("click", handleClick);
});

function handleClick(event) {
  buttonsGroupArray.forEach(function (val) {
    if (val == event.target) {
      val.classList.add("active-button");
    } else {
      val.classList.remove("active-button");
    }
  });
}

//Sorted views based on what button is clicked:
sortByRoomNumberAscendingButton.addEventListener("click", () => {
  customSort(sortAsendingBy, "roomNumber");
});
sortByRoomNumberDescendingButton.addEventListener("click", () => {
  customSort(sortDescendingBy, "roomNumber");
});
sortByStartDateAscendingButton.addEventListener("click", () => {
  customSortForDates(sortAsendingByDate);
});
sortByStartDateDescendingButton.addEventListener("click", () => {
  customSortForDates(sortDescendingByDate);
});

//Main fucntion:
function doLoop(thing) {
  console.log("first line of doLoop()");

  for (let i = 0; i < thing.length; i++) {
    // generates a span, gets the hotel name and puts it in a span, the span's background color and "span.textContent" font depends on what the hotel name is.
    output.appendChild(createTopLevelItem(thing[i].hotelName));
    // if (i == 0) {
    //   makeButton(output, thing[0], thing[0].roomsInfoList);
    // }
    //generates a p element, gets the total revenue for the hotel and formats the number value as dollars, the hotel name is needed as the 2nd arg bc that dictates what font will be applied to this .
    output.appendChild(
      createSecondLevelItemWithDollars(
        thing[i].totalRentDollars,
        thing[i].hotelName
      )
    );

    for (let j = 0; j < thing[i].roomsInfoList.length; j++) {
      output.appendChild(makeLine(thing[i].hotelName));

      output.appendChild(
        createSecondLevelElement(
          thing[i].roomsInfoList[j].roomNumber,
          "Room",
          thing[i].hotelName
        )
      );

      output.appendChild(
        createSecondLevelElement(
          thing[i].roomsInfoList[j].nightlyRent,
          "Nightly Rent Price",
          thing[i].hotelName
        )
      );

      output.appendChild(
        createSecondLevelElement(
          thing[i].roomsInfoList[j].startDate,
          "Check In",
          thing[i].hotelName
        )
      );
      output.appendChild(
        createSecondLevelElement(
          thing[i].roomsInfoList[j].endDate,
          "Check Out",
          thing[i].hotelName
        )
      );
      output.appendChild(
        createSecondLevelElement(
          thing[i].roomsInfoList[j].nightsOfRent,
          "Total Nights",
          thing[i].hotelName
        )
      );
      output.appendChild(
        createSecondLevelElement(
          thing[i].roomsInfoList[j].totalRentRevenueForThisRoom,
          "Revenue for the Room",
          thing[i].hotelName
        )
      );
    }
    output.appendChild(makeLine(thing[i].hotelName));
  }
}
