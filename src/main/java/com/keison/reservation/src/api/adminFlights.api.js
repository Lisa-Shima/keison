import api from "./axios";

export const adminCreateFlight = async (payload) => {
    const res = await api.post("/admin/flights", payload);
    return res.data;
};

export const adminGenerateSeats = async ({ flightId, payload }) => {
    const res = await api.post(`/admin/flights/${flightId}/seats/generate`, payload);
    return res.data;
};
