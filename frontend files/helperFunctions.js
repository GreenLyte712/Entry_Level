// this takes in a hotel name to create a span, and will be the start of that Hotel's space on the page.
export function createTopLevelItem(itemFromJSON) {
  let span = document.createElement("span");
  if (itemFromJSON === "Money Maker") {
    span.style.backgroundColor = "green";
  }
  if (itemFromJSON === "Hotel of Horrors") {
    span.style.backgroundColor = "red";
  }
  if (itemFromJSON === "On the Cheap") {
    span.style.backgroundColor = "gold";
  }

  span.className = "topName";
  span.textContent = itemFromJSON;

  return span;
}

//this takes in the totalRevenue and hotel name, and will place Total Rev: $x on the page under the hotel's name.
export function createSecondLevelItemWithDollars(
  itemFromJSON,
  hotelNameAsParam
) {
  let p = document.createElement("p");

  if (hotelNameAsParam === "Money Maker") {
    p.style.color = "green";
  }
  if (hotelNameAsParam === "On the Cheap") {
    p.style.color = "gold";
  }
  if (hotelNameAsParam === "Hotel of Horrors") {
    p.style.color = "red";
  }

  p.innerHTML =
    "Total Revenue: " +
    new Intl.NumberFormat("en-US", {
      style: "currency",
      currency: "USD",
    }).format(itemFromJSON);
  p.style.marginBottom = "1em";
  return p;
}

//this takes in item from array, a description, and the hotel name. This will list the rooms rented details for each hotel.
export function createSecondLevelElement(
  itemFromJSON,
  description,
  hotelNameAsParam
) {
  let p = document.createElement("p");

  if (hotelNameAsParam === "Money Maker") {
    p.style.color = "green";
  }
  if (hotelNameAsParam === "On the Cheap") {
    p.style.color = "gold";
  }
  if (hotelNameAsParam === "Hotel of Horrors") {
    p.style.color = "red";

    //p.style.textShadow = "-1px 0 black, 0 1px black, 1px 0 black, 0 -1px black";
  }

  // description arg determines these
  if (description === "Room") {
    p.style.position = "relative";
    // p.style.left = "1em";
    p.style.textAlign = "left";
    p.innerHTML = description + " : " + itemFromJSON;
  }

  if (description === "Nightly Rent Price") {
    p.style.position = "relative";
    // p.style.left = "1em";
    p.style.textAlign = "left";
    p.innerHTML =
      description +
      " : " +
      new Intl.NumberFormat("en-US", {
        style: "currency",
        currency: "USD",
      }).format(itemFromJSON);
  }
  if (description === "Check In") {
    p.style.position = "relative";
    // p.style.left = "1em";
    p.style.textAlign = "left";
    const [year, month, day] = itemFromJSON.split("-");

    const itemFromJSONDateFormatted = [month, day, year].join("/");
    p.innerHTML = description + " : " + itemFromJSONDateFormatted;
  }
  if (description === "Check Out") {
    p.style.position = "relative";
    // p.style.left = "1em";
    p.style.textAlign = "left";
    const [year, month, day] = itemFromJSON.split("-");

    const itemFromJSONDateFormatted = [month, day, year].join("/");
    p.innerHTML = description + " : " + itemFromJSONDateFormatted;
  }
  if (description === "Total Nights") {
    p.style.position = "relative";
    // p.style.left = "1em";
    p.style.textAlign = "left";
    p.innerHTML = description + " : " + itemFromJSON;
  }

  if (description === "Revenue for the Room") {
    p.style.position = "relative";
    p.style.textAlign = "left";
    p.innerHTML =
      description +
      " : " +
      new Intl.NumberFormat("en-US", {
        style: "currency",
        currency: "USD",
      }).format(itemFromJSON);
  }

  return p;
}

// result = getEveryNth(roomsInfoList, 4, 3);

// console.log(result);

//this function returns an array
export function getEveryNth(arr, numberInGroup, infoPlace) {
  const result = [];
  //console.log(arr);
  for (let i = 0; i < arr.length; i += numberInGroup) {
    result.push(arr[i + infoPlace]);
    // console.log(
    //   "when 3rd arg is " +
    //     infoPlace +
    //     " : " +
    //     "i is " +
    //     i +
    //     " : i + 3rd arg is " +
    //     (i + infoPlace) +
    //     " : element is: " +
    //     arr[i + infoPlace]
    // );
  }

  return result;
}

export function makeLine(hotelName) {
  let line = document.createElement("hr");
  line.style.borderStyle = "none";
  line.style.height = "1px";
  if (hotelName === "Money Maker") line.style.background = "green";
  if (hotelName === "Hotel of Horrors") line.style.background = "red";
  if (hotelName === "On the Cheap") line.style.background = "gold";

  return line;
}

export function makeButton(output, hotelArray) {
  let buttonOne = document.createElement("buttonOne");
  buttonOne.addEventListener("click", () => {
    newList = hotelArray.roomsInfoList.sort(sortAsendingBy("roomNumber"));
  });
  buttonOne.style.blockSize = "5em";
  buttonOne.style.padding = "1em";
  buttonOne.innerText = "Sort";

  return output.appendChild(buttonOne);
}

//example call: hotelArray.roomsInfoList.sort(sortAsendingBy("roomNumber"));
export function sortDescendingBy(prop) {
  return function (a, b) {
    console.log("Sorting desending (-1) by " + prop);
    if (a[prop] > b[prop]) return -1;
  };
}
export function sortAsendingBy(prop) {
  return function (a, b) {
    console.log("Sorting ascending (1) by " + prop);
    if (a[prop] > b[prop]) return 1;
  };
}

export function sortAsendingByDate() {
  return function (a, b) {
    return new Date(a.startDate) - new Date(b.startDate);
  };
}

export function sortDescendingByDate() {
  return function (b, a) {
    return new Date(a.startDate) - new Date(b.startDate);
  };
}

export function makeButtonTest(target) {
  target = target + 1;

  console.log("++++++++++ target inside test function " + target);

  return target;
}
