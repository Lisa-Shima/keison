import api from "./axios";

export const adminGetBookings = async (status) => {
    const res = await api.get("/admin/bookings", { params: status ? { status } : {} });
    return res.data;
};

export const adminCancelBooking = async (bookingId) => {
    const res = await api.post(`/admin/bookings/${bookingId}/cancel`);
    return res.data;
};
