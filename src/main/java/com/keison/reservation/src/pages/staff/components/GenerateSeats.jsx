import { useState } from "react";
import { adminGenerateSeats } from "../../../api/adminFlights.api";

const GenerateSeats = () => {
    const [flightId, setFlightId] = useState("");
    const [travelClass, setTravelClass] = useState("ECONOMY");
    const [count, setCount] = useState(10);
    const [price, setPrice] = useState(150);

    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const submit = async (e) => {
        e.preventDefault();
        setError("");
        setSuccess("");

        try {
            const payload = {
                travelClass,
                count: Number(count),
                price: Number(price),
            };

            const res = await adminGenerateSeats({ flightId: flightId.trim(), payload });
            setSuccess(`Seats generated ✅ count: ${res.length}`);
        } catch (err) {
            setError(err?.response?.data?.message || "Failed to generate seats");
        }
    };

    return (
        <div>
            <h2>Generate Seats</h2>
            {error && <p style={{ color: "red" }}>{error}</p>}
            {success && <p style={{ color: "green" }}>{success}</p>}

            <form onSubmit={submit} style={{ display: "grid", gap: 8, maxWidth: 420 }}>
                <input
                    placeholder="Flight ID (UUID)"
                    value={flightId}
                    onChange={(e) => setFlightId(e.target.value)}
                />

                <select value={travelClass} onChange={(e) => setTravelClass(e.target.value)}>
                    <option value="ECONOMY">ECONOMY</option>
                    <option value="BUSINESS">BUSINESS</option>
                    <option value="FIRST_CLASS">FIRST_CLASS</option>
                </select>

                <input
                    type="number"
                    min="1"
                    placeholder="Count"
                    value={count}
                    onChange={(e) => setCount(e.target.value)}
                />

                <input
                    type="number"
                    min="0"
                    placeholder="Price"
                    value={price}
                    onChange={(e) => setPrice(e.target.value)}
                />

                <button>Generate</button>
            </form>

            <p style={{ marginTop: 10, fontSize: 12, color: "#666" }}>
                Tip: Create flight first, then paste flightId here.
            </p>
        </div>
    );
};

export default GenerateSeats;
