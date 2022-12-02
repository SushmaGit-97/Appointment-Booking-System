import React from "react";
import { withRouter } from "react-router";
import UserLeftNavigation from "./UserLeftNavigation";

class DashboardLayout extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      userId: localStorage.getItem("userId"),
      userRole: localStorage.getItem("userRole"),
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

  handleNewRequest = () => {
    const {
      history: { push },
    } = this.props;
    push(`/NewAppointmentRequest`);
  };

  render() {
    return (
      <div
        className=" flex flex-col"
        style={{ backgroundColor: "aliceBlueBlue", minHeight: "100vh" }}
      >
        <div
          style={{
            backgroundColor: "blue",
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
          <UserLeftNavigation
            handleNewRequest={this.handleNewRequest}
            handleViewHistory={this.handleViewHistory}
            handleLogout={this.handleLogout}
          />
          <div className="flex flex-col relative w-full">
            <div
              style={{
                display: "flex",
                justifyContent: "center",
                paddingTop: "50px",
                fontSize: "50px",
                color: "black",
              }}
            >
              <h1>Please Proceed to Schedule your Appointment</h1>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default withRouter(DashboardLayout);
