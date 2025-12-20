import api from "./axios";

export const adminCreateStaff = async (payload) => {
    const res = await api.post("/admin/users/staff", payload);
    return res.data;
};
