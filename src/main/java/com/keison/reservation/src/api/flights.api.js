import api from "./axios";

export const searchFlights = async ({ origin, dest }) => {
    const res = await api.get("/flights/search", {
        params: { originAirportCode: origin, destAirportCode: dest },
    });
    return res.data;
};

export const getFlightSeats = async (flightId) => {
    const res = await api.get(`/flights/${flightId}/seats`);
    return res.data;
};
