import React from "react";
import { withRouter } from "react-router";
import axios from "axios";

import TextField from "@mui/material/TextField";
import dayjs from "dayjs";
import Stack from "@mui/material/Stack";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { TimePicker } from "@mui/x-date-pickers/TimePicker";
import { TagsInput } from "react-tag-input-component";
import AdminLeftNavigation from "./AdminLeftNavigation";

const addLocationUrl = "http://localhost:8080/appointmentBooking/addLocation";
class NewLocation extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      applicationList: [],
      locationName: "",
      serviceCategory: "",
      weekDayStartTime: dayjs("2022-01-01 9.00"),
      weekDayEndTime: dayjs("2022-01-01 18:00"),
      weekendStartTime: dayjs("2022-01-01 10:00"),
      weekendEndTime: dayjs("2022-01-01 16:00"),
      weekdayStartNum: 9,
      weekdayEndNum: 18,
      weekendEndNum: 18,
      weekendStartNum: 9,
      serviceList: [],
    };
  }

  handleLogout = () => {
    localStorage.removeItem("userId");
    localStorage.removeItem("userRole");
    const {
      history: { push },
    } = this.props;
    push("/");
  };

  handleWeekendStartTime = (newTime) => {
    this.setState({
      weekendStartTime: newTime,
      weekdayStartNum: newTime.$H,
    });
  };

  handleWeekendEndTime = (newTime) => {
    this.setState({
      weekendEndTime: newTime,
      weekendEndNum: newTime.$H,
    });
  };

  handleWeekDayStartTime = (newTime) => {
    this.setState({
      weekDayStartTime: newTime,
      weekdayStartNum: newTime.$H,
    });
  };

  handleWeekDayEndTime = (newTime) => {
    this.setState({
      weekDayEndTime: newTime,
      weekdayEndNum: newTime.$H,
    });
  };

  handleService = (value) => {
    this.setState({
      serviceList: value,
    });
  };

  handleLocationNameChange = (e) => {
    this.setState({ locationName: e.target.value });
  };

  handleSubmit = () => {
    const {
      locationName,
      weekdayEndNum,
      weekdayStartNum,
      weekendStartNum,
      weekendEndNum,
      serviceList,
    } = this.state;
    const location = {
      name: locationName,
      weekdayStartTime: weekdayStartNum,
      weekdayEndTime: weekdayEndNum,
      weekendStartTime: weekendStartNum,
      weekendEndTime: weekendEndNum,
    };
    const services = [];
    serviceList.forEach((service) => {
      let obj = {};
      obj["name"] = service;
      services.push(obj);
    });

    const reqJson = {
      location: location,
      services: services,
    };
    axios.post(addLocationUrl, reqJson).then((res) => {
      alert("Location added successfully!");
    });

    const {
      history: { push },
    } = this.props;
    push("/adminDashboard");
  };

  render() {
    const {
      locationName,
      weekendStartTime,
      weekendEndTime,
      weekDayStartTime,
      weekDayEndTime,
      serviceList,
    } = this.state;
    return (
      <div
        className=" flex flex-col"
        style={{ backgroundColor: "aliceBlueBlue", minHeight: "100vh" }}
      >
        <div
          className=" flex flex-col"
          style={{ backgroundColor: "Cornsilk", minHeight: "100vh" }}
        >
          <div
            style={{
              backgroundColor: "Tomato",
              display: "flex",
              justifyContent: "center",
              padding: "15px",
              borderBottom: "5px solid white",
              fontSize: "40px",
              color: "black",
            }}
          >
            LOCATION MANAGEMENT
          </div>
          <div style={{ display: "flex" }}>
            <AdminLeftNavigation
              handleNewLocation={this.handleNewLocation}
              handleAppointmentsTracking={this.handleAppointmentsTracking}
              handleLogout={this.handleLogout}
            />
            <div
              className="flex flex-col relative w-full"
              style={{ alignItems: "center" }}
            >
              <div
                className="flex flex-col space-y-5 max-w-md mx-auto my-16 "
                style={{
                  minWidth: "50%",
                  padding: "30px",
                  borderRadius: 30,
                  borderColor: "black",
                  position: "absolute",
                }}
              >
                <h2
                  style={{
                    color: "black",
                    display: "flex",
                    justifyContent: "center",
                    fontSize: 35,
                  }}
                  className=" font-semibold uppercase"
                >
                  Location Details
                </h2>

                <TextField
                  required
                  id="outlined-username"
                  value={locationName}
                  label="Location name"
                  autoComplete="off"
                  onChange={(e) => this.handleLocationNameChange(e)}
                />

                <TagsInput
                  value={serviceList}
                  onChange={this.handleService}
                  placeHolder="Services"
                />
                <i>Click Enter after adding new service</i>

                <div
                  style={{
                    display: "flex",
                    justifyContent: "space-between",
                    marginTop: "40px",
                  }}
                >
                  <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <Stack spacing={3}>
                      <TimePicker
                        renderInput={(params) => <TextField {...params} />}
                        value={weekDayStartTime}
                        label="Weekday start time"
                        onChange={(newValue) => {
                          this.handleWeekDayStartTime(newValue);
                        }}
                        views={["hours"]}
                      />
                    </Stack>
                  </LocalizationProvider>
                  <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <Stack spacing={3}>
                      <TimePicker
                        renderInput={(params) => <TextField {...params} />}
                        value={weekDayEndTime}
                        label="Weekday end time"
                        onChange={(newValue) => {
                          this.handleWeekDayEndTime(newValue);
                        }}
                        views={["hours"]}
                      />
                    </Stack>
                  </LocalizationProvider>
                </div>

                <div
                  style={{
                    display: "flex",
                    justifyContent: "space-between",
                    marginTop: "40px",
                  }}
                >
                  <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <Stack spacing={3}>
                      <TimePicker
                        renderInput={(params) => <TextField {...params} />}
                        value={weekendStartTime}
                        label="Weekend start time"
                        onChange={(newValue) => {
                          this.handleWeekendStartTime(newValue);
                        }}
                        views={["hours"]}
                      />
                    </Stack>
                  </LocalizationProvider>
                  <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <Stack spacing={3}>
                      <TimePicker
                        renderInput={(params) => <TextField {...params} />}
                        value={weekendEndTime}
                        label="Weekend end time"
                        onChange={(newValue) => {
                          this.handleWeekendEndTime(newValue);
                        }}
                        views={["hours"]}
                      />
                    </Stack>
                  </LocalizationProvider>
                </div>
                <div className="flex items-center justify-between">
                  <button
                    type="button"
                    class="text-white bg-gradient-to-r from-gray-500 via-gray-600 to-gray-700 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-gray-300 dark:focus:ring-gray-800 shadow-lg shadow-gray-500/50 dark:shadow-lg dark:shadow-gray-800/80 font-medium rounded-lg text-sm px-5 py-2.5 text-center mr-2 mb-2 "
                    onClick={this.handleSubmit}
                  >
                    Add
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default withRouter(NewLocation);
