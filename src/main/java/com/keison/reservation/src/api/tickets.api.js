import api from "./axios";

export const myTickets = async () => {
    const res = await api.get("/tickets/my");
    return res.data;
};
