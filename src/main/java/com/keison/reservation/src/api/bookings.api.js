import api from "./axios";

export const createBooking = async (payload) => {
    const res = await api.post("/bookings", payload);
    return res.data;
};

export const myBookings = async () => {
    const res = await api.get("/bookings");
    return res.data;
};

export const cancelBooking = async (bookingId) => {
    const res = await api.post(`/bookings/${bookingId}/cancel`);
    return res.data;
};
