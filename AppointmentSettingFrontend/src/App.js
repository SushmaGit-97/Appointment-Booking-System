import "./App.css";
import "./index.css";
import { BrowserRouter, Route } from "react-router-dom";
import UserLogin from "./components/UserLogin";
import DashboardLayout from "./components/DashboardLayout";
import UserSignup from "./components/UserSignup";
import NewAppointmentRequest from "./components/NewAppointmentRequest";
import AdminDashboard from "./components/AdminDashboard";
import NewLocation from "./components/NewLocation";

function App() {
  return (
    <BrowserRouter>
      <div className="flex flex-col min-h-screen">
        <Route path="/" component={UserLogin} exact></Route>
        <Route path="/userSignup" component={UserSignup}></Route>
        <Route path="/dashboardLayout" component={DashboardLayout}></Route>
        <Route
          path="/NewAppointmentRequest"
          component={NewAppointmentRequest}
        ></Route>
        <Route path="/adminDashboard" component={AdminDashboard}></Route>
        <Route path="/newLocation" component={NewLocation}></Route>
      </div>
    </BrowserRouter>
  );
}

export default App;
