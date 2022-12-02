import { Link } from "react-router-dom";
import TextField from "@mui/material/TextField";
import React from "react";
import Alert from "@mui/material/Alert";
import { withRouter } from "react-router";
import axios from "axios";

const baseUrl = "http://localhost:8080/appointmentBooking/registerUser";

class UserSignup extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      emailId: "",
      phoneNumber: "",
      password: "",
      address: "",
      shouldAlertDisplay: false,
      shouldErrorMessageDisplay: false,
      signupErrorMessage: "",
      isEmailError: false,
      isNumberError: false,
    };
  }

  handleUsernameChange = (e) => {
    this.setState({ username: e.target.value });
  };

  handleEmailChange = (e) => {
    this.setState({ emailId: e.target.value });
  };

  handlePhoneNumberChange = (e) => {
    this.setState({ phoneNumber: e.target.value });
  };

  handlePasswordChange = (e) => {
    this.setState({ password: e.target.value });
  };

  handleAddressChange = (e) => {
    this.setState({ address: e.target.value });
  };

  handleSubmit = () => {
    const { username, emailId, password, phoneNumber, address } = this.state;
    const {
      history: { push },
    } = this.props;
    if (
      username === "" ||
      emailId === "" ||
      password === "" ||
      phoneNumber === ""
    ) {
      this.setState({ shouldAlertDisplay: true });
      return;
    }
    const isEmailError = this.checkEmailError(emailId);
    const isPhoneError = this.checkPhoneError(phoneNumber);
    if (!isEmailError) {
      this.setState({ isEmailError: true });
    } else {
      this.setState({ isEmailError: false });
    }
    if (!isPhoneError) {
      this.setState({ isNumberError: true });
    } else {
      this.setState({ isNumberError: false });
    }

    if (!isEmailError) {
      return;
    }

    const reqJson = {
      userName: username,
      password: password,
      email: emailId,
      phoneNumber: phoneNumber,
      address: address,
    };

    axios.post(baseUrl, reqJson).then((res) => {
      if (!res.data.isSuccess) {
        this.setState({
          signupErrorMessage: res.data.error,
          shouldErrorMessageDisplay: true,
        });
      } else {
        push("/");
      }
    });
  };

  checkEmailError = (emailId) => {
    const re =
      /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(emailId).toLowerCase());
  };

  checkPhoneError = (number) => {
    const re = /^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$/im;
    return re.test(String(number).toLowerCase());
  };

  render() {
    const {
      username,
      phoneNumber,
      address,
      emailId,
      password,
      shouldAlertDisplay,
      shouldErrorMessageDisplay,
      signupErrorMessage,
      isEmailError,
      isNumberError,
    } = this.state;

    return (
      <div
        className="flex flex-col space-y-5 max-w-md mx-auto my-16 min-w-500"
        style={{ backgroundColor: "white", padding: "30px", borderRadius: 10 }}
      >
        <h2
          style={{
            color: "darkslategray",
            display: "flex",
            justifyContent: "center",
          }}
          className="text-4xl font-semibold uppercase"
        >
          Create Your Account
        </h2>
        <TextField
          required
          id="outlined-username"
          value={username}
          label="Username"
          autoComplete="off"
          onChange={(e) => this.handleUsernameChange(e)}
        />
        <TextField
          value={password}
          required
          id="outlined-password-input"
          label="Password"
          type="password"
          onChange={(e) => this.handlePasswordChange(e)}
        />
        <TextField
          error={isEmailError}
          required
          id="outlined-email"
          value={emailId}
          label="Email ID"
          onChange={(e) => this.handleEmailChange(e)}
          helperText={isEmailError ? "Invalid Email" : ""}
        />
        <TextField
          error={isNumberError}
          required
          id="outlined-phone"
          value={phoneNumber}
          label="Phone Number"
          onChange={(e) => this.handlePhoneNumberChange(e)}
          helperText={isNumberError ? "Invalid Phone Number" : ""}
        />
        <TextField
          required
          id="outlined-phone"
          value={address}
          label="Address"
          onChange={(e) => this.handleAddressChange(e)}
        />

        <div className="flex items-center justify-between">
          <button
            type="button"
            class="text-white bg-gradient-to-r from-gray-500 via-gray-600 to-gray-700 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-gray-300 dark:focus:ring-blue-800 shadow-lg shadow-blue-500/50 dark:shadow-lg dark:shadow-blue-800/80 font-medium rounded-lg text-sm px-5 py-2.5 text-center mr-2 mb-2 "
            onClick={this.handleSubmit}
          >
            Submit
          </button>

          <div className="flex">
            <p className="text-lg">Existing User?</p>
            <Link
              to="/"
              style={{ color: "darkslategray" }}
              className="font-semibold text-lg px-1"
            >
              Login
            </Link>
          </div>
        </div>
        {shouldAlertDisplay && (
          <Alert severity="error">Field cannot be empty</Alert>
        )}
        {shouldErrorMessageDisplay && (
          <Alert severity="error"> {signupErrorMessage} </Alert>
        )}
      </div>
    );
  }
}

export default withRouter(UserSignup);
