import { useEffect, useState } from "react";
import { myTickets } from "../../../api/tickets.api";

const MyTickets = () => {
    const [items, setItems] = useState([]);
    const [error, setError] = useState("");

    useEffect(() => {
        myTickets()
            .then(setItems)
            .catch((err) => setError(err?.response?.data?.message || "Failed to load tickets"));
    }, []);

    return (
        <div>
            <h2>My Tickets</h2>
            {error && <p style={{ color: "red" }}>{error}</p>}

            {items.map((t) => (
                <div key={t.id} style={{ border: "1px solid #ddd", padding: 10, marginBottom: 8 }}>
                    <div><b>{t.ticketNumber}</b> ({t.status})</div>
                    <div>Flight: {t.flightNo}</div>
                    <div>{t.origin} → {t.destination}</div>
                    <div>Seat: {t.seatNumber} ({t.travelClass})</div>
                    <div>Issued: {new Date(t.issuedAt).toLocaleString()}</div>
                    <div>Price: {t.price}</div>
                </div>
            ))}

            {items.length === 0 && <p>No tickets yet.</p>}
        </div>
    );
};

export default MyTickets;
