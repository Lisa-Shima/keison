import { useState } from "react";
import FlightSearch from "./components/FlightSearch";
import MyBookings from "./components/MyBookings";
import MyTickets from "./components/MyTickets";
import PortalHeader from "../../components/PortalHeader";

const PassengerDashboard = () => {
    const [tab, setTab] = useState("search");

    return (
        <div style={{ padding: 16 }}>
            <PortalHeader title="Passenger Portal" />
            <h1>Passenger Portal</h1>

            <div style={{ display: "flex", gap: 8, marginBottom: 16 }}>
                <button onClick={() => setTab("search")}>Search Flights</button>
                <button onClick={() => setTab("bookings")}>My Bookings</button>
                <button onClick={() => setTab("tickets")}>My Tickets</button>
            </div>

            {tab === "search" && <FlightSearch />}
            {tab === "bookings" && <MyBookings />}
            {tab === "tickets" && <MyTickets />}
        </div>
    );
};

export default PassengerDashboard;
