import { useState } from "react";
import CreateFlight from "./components/CreateFlight";
import GenerateSeats from "./components/GenerateSeats";
import ManageBookings from "./components/ManageBookings";
import PortalHeader from "../../components/PortalHeader";

const StaffDashboard = () => {
    const [tab, setTab] = useState("flights");

    return (
        <div style={{ padding: 16 }}>
            <PortalHeader title="Staff Portal" />
            <h1>Staff Portal</h1>

            <div style={{ display: "flex", gap: 8, marginBottom: 16 }}>
                <button onClick={() => setTab("flights")}>Create Flight</button>
                <button onClick={() => setTab("seats")}>Generate Seats</button>
                <button onClick={() => setTab("bookings")}>Bookings</button>
            </div>

            {tab === "flights" && <CreateFlight />}
            {tab === "seats" && <GenerateSeats />}
            {tab === "bookings" && <ManageBookings />}
        </div>
    );
};

export default StaffDashboard;
