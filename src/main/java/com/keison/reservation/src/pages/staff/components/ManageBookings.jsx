import { useEffect, useState } from "react";
import { adminCancelBooking, adminGetBookings } from "../../../api/adminBookings.api";

const ManageBookings = () => {
    const [status, setStatus] = useState("");
    const [items, setItems] = useState([]);
    const [error, setError] = useState("");
    const [busyId, setBusyId] = useState(null);

    const load = async () => {
        setError("");
        try {
            const data = await adminGetBookings(status || undefined);
            setItems(data);
        } catch (err) {
            setError(err?.response?.data?.message || "Failed to load bookings");
        }
    };

    useEffect(() => {
        load();
    }, [status]);

    const cancel = async (id) => {
        setBusyId(id);
        setError("");
        try {
            await adminCancelBooking(id);
            await load();
        } catch (err) {
            setError(err?.response?.data?.message || "Failed to cancel booking");
        } finally {
            setBusyId(null);
        }
    };

    return (
        <div>
            <h2>Bookings</h2>

            <div style={{ display: "flex", gap: 8, alignItems: "center", marginBottom: 10 }}>
                <label>Filter:</label>
                <select value={status} onChange={(e) => setStatus(e.target.value)}>
                    <option value="">All</option>
                    <option value="PENDING">PENDING</option>
                    <option value="CONFIRMED">CONFIRMED</option>
                    <option value="CANCELLED">CANCELLED</option>
                </select>
                <button onClick={load}>Refresh</button>
            </div>

            {error && <p style={{ color: "red" }}>{error}</p>}

            {items.map((b) => (
                <div key={b.id} style={{ border: "1px solid #ddd", padding: 10, marginBottom: 8 }}>
                    <div><b>{b.bookingRef || b.id}</b></div>
                    <div>Status: {b.status}</div>
                    <div>Total: {b.totalAmount}</div>

                    {b.status === "PENDING" && (
                        <button disabled={busyId === b.id} onClick={() => cancel(b.id)} style={{ marginTop: 8 }}>
                            {busyId === b.id ? "Cancelling..." : "Cancel booking"}
                        </button>
                    )}
                </div>
            ))}

            {items.length === 0 && <p>No bookings found.</p>}
        </div>
    );
};

export default ManageBookings;
