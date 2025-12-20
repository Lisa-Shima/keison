import { useState } from "react";
import { adminCreateFlight } from "../../../api/adminFlights.api";

const CreateFlight = () => {
    const [form, setForm] = useState({
        airline: "Keison Airline",
        flightNo: "",
        originAirportCode: "KGL",
        destAirportCode: "",
        departureTime: "",
        arrivalTime: "",
    });

    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const submit = async (e) => {
        e.preventDefault();
        setError("");
        setSuccess("");

        try {
            const payload = {
                airline: form.airline.trim(),
                flightNo: form.flightNo.trim(),
                originAirportCode: form.originAirportCode.trim().toUpperCase(),
                destAirportCode: form.destAirportCode.trim().toUpperCase(),
                departureTime: new Date(form.departureTime).toISOString(),
                arrivalTime: new Date(form.arrivalTime).toISOString(),
            };

            const created = await adminCreateFlight(payload);
            setSuccess(`Flight created ✅ ID: ${created.id}`);
            setForm((f) => ({ ...f, flightNo: "", destAirportCode: "", departureTime: "", arrivalTime: "" }));
        } catch (err) {
            setError(err?.response?.data?.message || "Failed to create flight");
        }
    };

    return (
        <div>
            <h2>Create Flight</h2>
            {error && <p style={{ color: "red" }}>{error}</p>}
            {success && <p style={{ color: "green" }}>{success}</p>}

            <form onSubmit={submit} style={{ display: "grid", gap: 8, maxWidth: 420 }}>
                <input
                    placeholder="Airline"
                    value={form.airline}
                    onChange={(e) => setForm({ ...form, airline: e.target.value })}
                />
                <input
                    placeholder="Flight No (e.g. KA101)"
                    value={form.flightNo}
                    onChange={(e) => setForm({ ...form, flightNo: e.target.value })}
                />
                <input
                    placeholder="Origin code (e.g. KGL)"
                    value={form.originAirportCode}
                    onChange={(e) => setForm({ ...form, originAirportCode: e.target.value })}
                />
                <input
                    placeholder="Destination code (e.g. NBO)"
                    value={form.destAirportCode}
                    onChange={(e) => setForm({ ...form, destAirportCode: e.target.value })}
                />

                <label>
                    Departure Time
                    <input
                        type="datetime-local"
                        value={form.departureTime}
                        onChange={(e) => setForm({ ...form, departureTime: e.target.value })}
                    />
                </label>

                <label>
                    Arrival Time
                    <input
                        type="datetime-local"
                        value={form.arrivalTime}
                        onChange={(e) => setForm({ ...form, arrivalTime: e.target.value })}
                    />
                </label>

                <button>Create</button>
            </form>
        </div>
    );
};

export default CreateFlight;
