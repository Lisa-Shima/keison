import api from "./axios";

export const adminGetAuditLogs = async () => {
    const res = await api.get("/admin/audit-logs");
    return res.data;
};
