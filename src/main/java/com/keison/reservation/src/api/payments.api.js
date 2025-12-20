import api from "./axios";

// Try the common endpoint you created earlier
export const payBooking = async ({ bookingId }) => {
    try {
        const res = await api.post("/payments/pay", { bookingId });
        return res.data;
    } catch (e) {
        // fallback if your backend uses /payments with body
        const res2 = await api.post("/payments", { bookingId });
        return res2.data;
    }
};
