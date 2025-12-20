import { useEffect, useMemo, useState } from "react";
import { getFlightSeats } from "../../../api/flights.api";
import { createBooking } from "../../../api/bookings.api";

const emptyTraveler = () => ({ passengerId: "", seatId: "", note: "" });

const FlightSeats = ({ flight }) => {
    const [seats, setSeats] = useState([]);
    const [loading, setLoading] = useState(true);
    const [travelers, setTravelers] = useState([emptyTraveler()]);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    useEffect(() => {
        let mounted = true;
        setLoading(true);
        setError("");
        setSuccess("");

        getFlightSeats(flight.id)
            .then((data) => {
                if (mounted) setSeats(data);
            })
            .catch((err) => {
                if (mounted) setError(err?.response?.data?.message || "Failed to load seats");
            })
            .finally(() => {
                if (mounted) setLoading(false);
            });

        return () => { mounted = false; };
    }, [flight.id]);

    const availableSeats = useMemo(() => {
        // your backend uses SeatStatus.AVAILABLE
        return seats.filter((s) => s.status === "AVAILABLE");
    }, [seats]);

    const addTraveler = () => setTravelers((t) => [...t, emptyTraveler()]);
    const removeTraveler = (idx) => setTravelers((t) => t.filter((_, i) => i !== idx));

    const updateTraveler = (idx, patch) => {
        setTravelers((t) => t.map((x, i) => (i === idx ? { ...x, ...patch } : x)));
    };

    const submitBooking = async () => {
        setError("");
        setSuccess("");

        // basic validation
        for (let i = 0; i < travelers.length; i++) {
            if (!travelers[i].passengerId.trim()) {
                setError(`Traveler ${i + 1}: passengerId is required`);
                return;
            }
            if (!travelers[i].seatId.trim()) {
                setError(`Traveler ${i + 1}: seatId is required`);
                return;
            }
        }

        // prevent duplicate seats
        const seatIds = travelers.map((t) => t.seatId);
        const unique = new Set(seatIds);
        if (unique.size !== seatIds.length) {
            setError("You selected the same seat for multiple travelers. Choose different seats.");
            return;
        }

        try {
            const payload = {
                flightId: flight.id,
                travelers: travelers.map((t) => ({
                    passengerId: t.passengerId.trim(),
                    seatId: t.seatId.trim(),
                    note: t.note?.trim() || null,
                })),
            };

            const booking = await createBooking(payload);
            setSuccess(`Booking created! Ref: ${booking.bookingRef || booking.id}`);
        } catch (err) {
            setError(err?.response?.data?.message || "Failed to create booking");
        }
    };

    if (loading) return <p>Loading seats...</p>;

    return (
        <div style={{ borderTop: "1px solid #ddd", paddingTop: 12 }}>
            <h3>Seats for {flight.flightNo}</h3>
            {error && <p style={{ color: "red" }}>{error}</p>}
            {success && <p style={{ color: "green" }}>{success}</p>}

            <p>Available seats: {availableSeats.length}</p>

            <div style={{ display: "grid", gap: 12 }}>
                {travelers.map((t, idx) => (
                    <div key={idx} style={{ border: "1px solid #eee", padding: 10 }}>
                        <div style={{ display: "flex", justifyContent: "space-between" }}>
                            <b>Traveler {idx + 1}</b>
                            {travelers.length > 1 && (
                                <button onClick={() => removeTraveler(idx)}>Remove</button>
                            )}
                        </div>

                        <div style={{ display: "grid", gap: 8, marginTop: 8 }}>
                            <input
                                placeholder="Passenger ID (UUID)"
                                value={t.passengerId}
                                onChange={(e) => updateTraveler(idx, { passengerId: e.target.value })}
                            />

                            <select
                                value={t.seatId}
                                onChange={(e) => updateTraveler(idx, { seatId: e.target.value })}
                            >
                                <option value="">Select seat</option>
                                {availableSeats.map((s) => (
                                    <option key={s.id} value={s.id}>
                                        {s.seatNumber} — {s.travelClass} — {s.price}
                                    </option>
                                ))}
                            </select>

                            <input
                                placeholder="Note (optional)"
                                value={t.note}
                                onChange={(e) => updateTraveler(idx, { note: e.target.value })}
                            />
                        </div>
                    </div>
                ))}
            </div>

            <div style={{ display: "flex", gap: 8, marginTop: 12 }}>
                <button onClick={addTraveler}>Add traveler</button>
                <button onClick={submitBooking}>Create booking</button>
            </div>

            <p style={{ marginTop: 10, fontSize: 12, color: "#666" }}>
                For now, enter passenger IDs (UUIDs) manually. Later we can add “Search passenger by passportNo”.
            </p>
        </div>
    );
};

export default FlightSeats;
