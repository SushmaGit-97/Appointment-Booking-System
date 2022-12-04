import { Link } from "react-router-dom";
import TextField from "@mui/material/TextField";
import React from "react";
import Alert from "@mui/material/Alert";
import { withRouter } from "react-router";
import axios from "axios";

const baseUrl = "http://localhost:8080/appointmentBooking/Userlogin";
class UserLogin extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
      shouldAlertDisplay: false,
      shouldLoginErrorDisplay: false,
    };
  }
  handleUsernameChange = (e) => {
    this.setState({ username: e.target.value });
  };

  handlePasswordChange = (e) => {
    this.setState({ password: e.target.value });
  };

  handleLogin = () => {
    const { username, password } = this.state;
    console.log("username", username);
    console.log("password", password);
    const {
      history: { push },
    } = this.props;
    if (username === "" || password === "") {
      this.setState({ shouldAlertDisplay: true });
      return;
    }
    this.setState({ shouldAlertDisplay: false });
    this.setState({ shouldLoginErrorDisplay: false });

    if (username === "admin" && password === "admin") {
      push({
        pathname: "/adminDashboard",
      });
      return;
    }
    const reqJson = {
      userName: username,
      password: password,
    };
    axios.post(baseUrl, reqJson).then((res) => {
      if (res.data.isValidUser) {
        localStorage.setItem("userId", res.data.user.id);
        push({
          pathname: "/dashboardLayout",
        });
      }
      if (!res.data.isValidUser) {
        this.setState({ shouldLoginErrorDisplay: true });
      }
    });
  };

  render() {
    const { username, password, shouldAlertDisplay, shouldLoginErrorDisplay } =
      this.state;
    return (
      <div
        className="flex flex-col space-y-5 max-w-md mx-auto my-16 min-w-500"
        style={{
          backgroundColor: "#F5EBE0",
          padding: "30px",
        }}
      >
        <div
          style={{
            color: "darkslategray",
          }}
        >
          <h1 className="text-4xl font-semibold  uppercase">
            Missouri Bank Login
          </h1>
        </div>
        <TextField
          value={username}
          required
          //id="outlined-required"
          label="Username"
          onChange={(e) => this.handleUsernameChange(e)}
        />
        <TextField
          //id="outlined-password-input"
          required
          label="Password"
          type="password"
          value={password}
          onChange={(e) => this.handlePasswordChange(e)}
        />
        <div className="flex">
          <p className="text-lg">New User?</p>
          <Link
            to="/userSignup"
            style={{ color: "darkslategray" }}
            className=" font-semibold text-lg px-1"
          >
            Sign up
          </Link>
        </div>
        <button
          type="button"
          class="text-white bg-gradient-to-r from-gray-500 via-gray-600 to-gray-700 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-gray-300 dark:focus:ring-blue-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
          onClick={this.handleLogin}
        >
          Login
        </button>
        {shouldAlertDisplay && (
          <Alert severity="error">Field cannot be empty</Alert>
        )}
        {shouldLoginErrorDisplay && (
          <Alert severity="error">Invalid username or password</Alert>
        )}
      </div>
    );
  }
}

export default withRouter(UserLogin);
