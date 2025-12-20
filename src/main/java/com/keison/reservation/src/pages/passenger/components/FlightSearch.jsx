import { useState } from "react";
import { searchFlights } from "../../../api/flights.api";
import FlightSeats from "./FlightSeats";

const FlightSearch = () => {
    const [origin, setOrigin] = useState("");
    const [dest, setDest] = useState("");
    const [flights, setFlights] = useState([]);
    const [selectedFlight, setSelectedFlight] = useState(null);
    const [error, setError] = useState("");

    const submit = async (e) => {
        e.preventDefault();
        setError("");
        setSelectedFlight(null);

        try {
            const data = await searchFlights({
                origin: origin.trim().toUpperCase(),
                dest: dest.trim().toUpperCase(),
            });
            setFlights(data);
        } catch (err) {
            setError(err?.response?.data?.message || "Failed to search flights");
        }
    };

    return (
        <div>
            <h2>Search Flights</h2>

            <form onSubmit={submit} style={{ display: "flex", gap: 8, marginBottom: 12 }}>
                <input
                    placeholder="Origin code (e.g. KGL)"
                    value={origin}
                    onChange={(e) => setOrigin(e.target.value)}
                />
                <input
                    placeholder="Destination code (e.g. NBO)"
                    value={dest}
                    onChange={(e) => setDest(e.target.value)}
                />
                <button type="submit">Search</button>
            </form>

            {error && <p style={{ color: "red" }}>{error}</p>}

            {flights.length > 0 && (
                <div style={{ marginBottom: 16 }}>
                    <h3>Results</h3>
                    {flights.map((f) => (
                        <div
                            key={f.id}
                            style={{
                                border: "1px solid #ddd",
                                padding: 10,
                                marginBottom: 8,
                                cursor: "pointer",
                            }}
                            onClick={() => setSelectedFlight(f)}
                        >
                            <div><b>{f.flightNo}</b> — {f.airline}</div>
                            <div>
                                {f.originAirportCode} → {f.destAirportCode}
                            </div>
                            <div>
                                Depart: {new Date(f.departureTime).toLocaleString()}
                            </div>
                        </div>
                    ))}
                </div>
            )}

            {selectedFlight && (
                <FlightSeats flight={selectedFlight} />
            )}
        </div>
    );
};

export default FlightSearch;
