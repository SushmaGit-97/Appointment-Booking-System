import React from "react";
import { withRouter } from "react-router";
import AdminLeftNavigation from "./AdminLeftNavigation";
import axios from "axios";
import { DataGrid, GridValueGetterParams } from "@mui/x-data-grid";

const url = "http://localhost:8080/appointmentBooking/getBookingDetails";
class AdminDashboard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      userId: localStorage.getItem("userId"),
      userRole: localStorage.getItem("userRole"),
      appointmentList: [],
    };
  }

  componentWillMount() {
    axios.get(url).then((res) => {
      this.setState({
        appointmentList: res.data.history,
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

  handleNewLocation = () => {
    const {
      history: { push },
    } = this.props;
    push(`/newLocation`);
  };
  render() {
    const columns = [
      {
        field: "id",
        type: "string",
        width: 150,
        renderHeader: () => (
          <strong>
            <span
              style={{
                fontSize: 16,
              }}
            >
              APPOINTMENT ID
            </span>
          </strong>
        ),
      },
      {
        field: "bookingUserName",
        type: "string",
        width: 115,
        renderHeader: () => (
          <strong>
            <span
              style={{
                fontSize: 16,
              }}
            >
              USER NAME
            </span>
          </strong>
        ),
      },

      {
        field: "bookingPhoneNumber",
        type: "string",
        width: 150,
        renderHeader: () => (
          <strong>
            <span
              style={{
                fontSize: 16,
              }}
            >
              PHONE NUMBER
            </span>
          </strong>
        ),
      },
      {
        field: "bookingEmail",
        type: "string",
        width: 200,
        renderHeader: () => (
          <strong>
            <span
              style={{
                fontSize: 16,
              }}
            >
              EMAIL
            </span>
          </strong>
        ),
      },
      {
        field: "locationName",
        type: "string",
        width: 120,
        renderHeader: () => (
          <strong>
            <span
              style={{
                fontSize: 16,
              }}
            >
              LOCATION
            </span>
          </strong>
        ),
      },
      {
        field: "serviceName",
        type: "string",
        width: 100,
        renderHeader: () => (
          <strong>
            <span
              style={{
                fontSize: 16,
              }}
            >
              SERVICE
            </span>
          </strong>
        ),
      },
      {
        field: "bookingDate",
        type: "string",
        width: 250,
        renderHeader: () => (
          <strong>
            <span
              style={{
                fontSize: 16,
              }}
            >
              BOOKING DATE AND TIME
            </span>
          </strong>
        ),
        valueGetter: (params: GridValueGetterParams) =>
          `${params.row.bookingDate}` +
          `(` +
          `${
            params.row.startTime < 12
              ? params.row.startTime + " - AM"
              : params.row.startTime === 12
              ? params.row.startTime + " - PM"
              : params.row.startTime - 12 + " - PM"
          }` +
          `)`,
      },
    ];
    return (
      <div
        className=" flex flex-col"
        style={{
          backgroundColor: "aliceBlueBlue",
          minHeight: "100vh",
          position: "absolute",
        }}
      >
        <div
          style={{
            backgroundColor: "#285430",
            width: 1630,
            display: "flex",
            justifyContent: "center",
            padding: "15px",
            borderBottom: "5px solid white",
            fontSize: "40px",
            color: "White",
          }}
        >
          SCHEDULED APPOINTMENTS
        </div>
        <div style={{ display: "flex" }}>
          <AdminLeftNavigation
            handleNewLocation={this.handleNewLocation}
            handleAppointmentsTracking={this.handleAppointmentsTracking}
            handleLogout={this.handleLogout}
          />
          <div className="flex flex-col relative w-full">
            <DataGrid
              style={{
                backgroundColor: "Cornsilk",
                width: 1350,
                height: 1000,
                position: "absolute",
              }}
              rows={this.state.appointmentList}
              columns={columns}
              experimentalFeatures={{ newEditingApi: true }}
              autoPageSize={true}
            />
          </div>
        </div>
      </div>
    );
  }
}

export default withRouter(AdminDashboard);
