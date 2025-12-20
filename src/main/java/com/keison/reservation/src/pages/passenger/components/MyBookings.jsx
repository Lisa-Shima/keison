import { useEffect, useState } from "react";
import { cancelBooking, myBookings } from "../../../api/bookings.api";
import { payBooking } from "../../../api/payments.api";

const MyBookings = () => {
    const [items, setItems] = useState([]);
    const [error, setError] = useState("");
    const [busyId, setBusyId] = useState(null);

    const load = async () => {
        setError("");
        try {
            const data = await myBookings();
            setItems(data);
        } catch (err) {
            setError(err?.response?.data?.message || "Failed to load bookings");
        }
    };

    useEffect(() => {
        load();
    }, []);

    const cancel = async (id) => {
        setBusyId(id);
        setError("");
        try {
            await cancelBooking(id);
            await load();
        } catch (err) {
            setError(err?.response?.data?.message || "Failed to cancel booking");
        } finally {
            setBusyId(null);
        }
    };

    const pay = async (id) => {
        setBusyId(id);
        setError("");
        try {
            await payBooking({ bookingId: id });
            await load();
            alert("Payment successful! Your ticket(s) should now appear in My Tickets.");
        } catch (err) {
            setError(err?.response?.data?.message || "Payment failed");
        } finally {
            setBusyId(null);
        }
    };

    return (
        <div>
            <h2>My Bookings</h2>
            {error && <p style={{ color: "red" }}>{error}</p>}

            {items.map((b) => (
                <div key={b.id} style={{ border: "1px solid #ddd", padding: 10, marginBottom: 8 }}>
                    <div><b>{b.bookingRef || b.id}</b></div>
                    <div>Status: {b.status}</div>
                    <div>Total: {b.totalAmount}</div>

                    <div style={{ display: "flex", gap: 8, marginTop: 8 }}>
                        {b.status === "PENDING" && (
                            <>
                                <button disabled={busyId === b.id} onClick={() => pay(b.id)}>
                                    {busyId === b.id ? "Paying..." : "Pay"}
                                </button>

                                <button disabled={busyId === b.id} onClick={() => cancel(b.id)}>
                                    {busyId === b.id ? "Cancelling..." : "Cancel"}
                                </button>
                            </>
                        )}

                        {b.status === "CONFIRMED" && (
                            <span style={{ color: "green" }}>Confirmed ✅ (see My Tickets)</span>
                        )}

                        {b.status === "CANCELLED" && (
                            <span style={{ color: "#999" }}>Cancelled</span>
                        )}
                    </div>
                </div>
            ))}

            {items.length === 0 && <p>No bookings yet.</p>}
        </div>
    );
};

export default MyBookings;
