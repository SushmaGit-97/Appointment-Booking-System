import React from "react";
import { withRouter } from "react-router";
import axios from "axios";
import TextField from "@mui/material/TextField";
import { Alert } from "@mui/material";
import Select from "react-dropdown-select";
import UserLeftNavigation from "./UserLeftNavigation";

const getAllLocationUrl =
  "http://localhost:8080/appointmentBooking/getLocationList";
const newAppointmentBookingUrl =
  "http://localhost:8080/appointmentBooking/AppointmentBooking";
const availableTimeSlotUrl =
  "http://localhost:8080/appointmentBooking/AvailableTimeslot";
class NewAppointmentRequest extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      applicationList: [],
      shouldAlertDisplay: false,
      locationName: "",
      serviceCategory: "",
      selectedLocation: "",
      selectedService: "",
      selectedTimeSlot: "",
      displayTimeslot: false,
      locationList: [],
      serviceList: [],
      timeSlotList: [],
      selectedDate: new Date("2022-12-04T00:00:00"),
      open: false,
      name: "",
      email: "",
      phoneNumber: "",
      address: "",
      locationDetails: [],

      locationId: "",
      serviceId: "",
      userId: localStorage.getItem("userId"),
      reason: "",
      bookingDate: "2022-12-04",
      bookingTime: "",
    };
  }

  componentDidMount() {
    axios.get(getAllLocationUrl).then((res) => {
      console.log("axios", res.data.location);
      const locationList = res.data.locationList;
      const list = [];
      locationList.forEach((loc) => {
        let obj = {
          label: loc.location.name,
          value: loc.location.name,
        };
        list.push(obj);
      });
      this.setState({
        locationList: list,
        locationDetails: locationList,
      });
    });
  }

  handleLogout = () => {
    localStorage.removeItem("userId");
    localStorage.removeItem("userRole");
    const {
      history: { push },
    } = this.props;
    push("/");
  };

  handleDateChange = (date) => {
    const { locationId, serviceId } = this.state;
    const timeList = [];
    const json = {
      locationId: locationId,
      serviceId: serviceId,
      bookingDate: date.target.value,
    };
    axios.post(availableTimeSlotUrl, json).then((res) => {
      let timeslotlist = res.data.availableSlot;
      timeslotlist.forEach((a) => {
        let obj = {};
        obj["label"] =
          a < 12 ? a + " - AM" : a === 12 ? a + " - PM" : a - 12 + " - PM";
        obj["value"] = a;
        timeList.push(obj);
      });
      this.setState({
        bookingDate: date.target.value,
        displayTimeslot: true,
        timeSlotList: timeList,
      });
    });
  };

  handleLocationList = (data) => {
    const { locationDetails } = this.state;
    console.log("location details", locationDetails);
    const list = [];
    let locationId = "";
    locationDetails.forEach((a) => {
      if (a.location.name === data[0]["value"]) {
        let serviceList = a.services;
        locationId = a.location.id;
        serviceList.forEach((a) => {
          let obj = {
            label: a.name,
            value: a.name,
          };
          list.push(obj);
        });
      }
    });
    this.setState({
      selectedLocation: data,
      locationId: locationId,
      serviceList: list,
      shouldAlertDisplay: false,
    });
  };

  handleServiceList = (data) => {
    const { locationDetails, locationId } = this.state;
    let serviceId = "";
    locationDetails.map((loc) => {
      console.log("locationD", loc);
      let serviceList = loc.services;
      serviceList.map((a) => {
        console.log("serviceList", a);
        if (a.name === data[0]["value"]) {
          serviceId = a.id;
        }
      });
    });

    const timeList = [];

    const json = {
      locationId: locationId,
      serviceId: serviceId,
      bookingDate: "2022-11-01",
    };
    axios.post(availableTimeSlotUrl, json).then((res) => {
      let timeslotlist = res.data.availableSlot;
      timeslotlist.forEach((a) => {
        let obj = {};
        obj["label"] =
          a < 12 ? a + " - AM" : a === 12 ? a + " - PM" : a - 12 + " - PM";
        obj["value"] = a;
        timeList.push(obj);
      });
      this.setState({
        displayTimeslot: true,
        timeSlotList: timeList,
      });
    });

    this.setState({
      selectedService: data,
      serviceId: serviceId,
      shouldAlertDisplay: false,
    });
  };

  handleReason = (data) => {
    this.setState({ reason: data.target.value, shouldAlertDisplay: false });
    this.setState({
      shouldAlertDisplay: false,
    });
  };

  handleTimeslots = (data) => {
    let bookingTime = data[0].value;
    this.setState({
      selectedTimeSlot: data,
      shouldAlertDisplay: false,
      bookingTime: bookingTime,
    });
  };

  handleSubmit = () => {
    const {
      history: { push },
    } = this.props;
    const {
      locationId,
      serviceId,
      userId,
      selectedTimeSlot,
      reason,
      bookingDate,
      bookingTime,
    } = this.state;
    const reqJson = {
      locationId: locationId,
      serviceId: serviceId,
      userId: userId,
      reason: reason,
      bookingDate: bookingDate,
      startTime: bookingTime,
    };
    if (
      locationId === "" ||
      serviceId === "" ||
      reason === "" ||
      selectedTimeSlot === ""
    ) {
      this.setState({
        shouldAlertDisplay: true,
      });
      return;
    }
    axios.post(newAppointmentBookingUrl, reqJson).then((res) => {
      console.log(res);
      alert("Your Appointment has been confirmed");

      push("/dashboardLayout");
    });
  };

  render() {
    const {
      timeSlotList,

      locationList,
      serviceList,
      shouldAlertDisplay,
    } = this.state;
    return (
      <div
        className=" flex flex-col"
        style={{ backgroundColor: "aliceBlueBlue", minHeight: "100vh" }}
      >
        <div
          className=" flex flex-col"
          style={{ backgroundColor: "aliceBlueBlue", minHeight: "100vh" }}
        >
          <div
            style={{
              backgroundColor: "#DBA39A",
              display: "flex",
              justifyContent: "center",
              padding: "12px",
              borderBottom: "1px solid white",
              fontSize: "40px",
              color: "white",
            }}
          >
            WELCOME TO MISSOURI BANK
          </div>
          <div style={{ display: "flex" }}>
            <UserLeftNavigation handleLogout={this.handleLogout} />
            <div
              className="flex flex-col relative w-full"
              style={{ alignItems: "center" }}
            >
              {}
              <div
                className="flex flex-col space-y-5 max-w-md mx-auto my-16 "
                style={{
                  minWidth: "70%",
                  backgroundColor: "#F0DBDB",
                  padding: "50px",
                  borderRadius: 20,
                  position: "absolute",
                }}
              >
                <h2
                  style={{
                    color: "black",
                    display: "flex",
                    justifyContent: "center",
                    fontSize: 30,
                  }}
                  className=" font-semibold uppercase"
                >
                  Book an Appointment
                </h2>

                <Select
                  placeholder="Select Location"
                  options={locationList}
                  onChange={(values) => this.handleLocationList(values)}
                />
                <Select
                  placeholder="Select Service"
                  options={serviceList}
                  onChange={(values) => this.handleServiceList(values)}
                />

                <TextField
                  id="outlined-multiline-static"
                  label="Reason"
                  multiline
                  rows={4}
                  onChange={(values) => this.handleReason(values)}
                />

                <TextField
                  id="date"
                  label="Date"
                  type="date"
                  defaultValue="2022-11-01"
                  onChange={this.handleDateChange}
                  sx={{ width: 220 }}
                  InputLabelProps={{
                    shrink: true,
                  }}
                />

                <div>
                  <Select
                    placeholder="Available Time Slots"
                    options={timeSlotList}
                    onChange={(values) => this.handleTimeslots(values)}
                  />
                </div>

                <div className="flex items-center justify-between">
                  <button
                    style={{
                      background: "darkslategray",
                      color: "white",
                      height: 35,
                      width: 80,
                      alignItems: "center",
                      borderRadius: 8,
                      marginRight: 20,
                    }}
                    onClick={this.handleSubmit}
                  >
                    Submit
                  </button>
                </div>
                {shouldAlertDisplay && (
                  <Alert severity="error">Field cannot be empty</Alert>
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default withRouter(NewAppointmentRequest);
