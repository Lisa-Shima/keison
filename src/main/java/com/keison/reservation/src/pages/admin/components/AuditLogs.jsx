import { useEffect, useState } from "react";
import { adminGetAuditLogs } from "../../../api/adminAudit.api";

const AuditLogs = () => {
    const [items, setItems] = useState([]);
    const [error, setError] = useState("");

    const load = async () => {
        setError("");
        try {
            const data = await adminGetAuditLogs();
            // newest first
            setItems([...data].sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt)));
        } catch (err) {
            setError(err?.response?.data?.message || "Failed to load audit logs");
        }
    };

    useEffect(() => {
        load();
    }, []);

    return (
        <div>
            <h2>Audit Logs</h2>
            <button onClick={load} style={{ marginBottom: 10 }}>Refresh</button>
            {error && <p style={{ color: "red" }}>{error}</p>}

            {items.map((l) => (
                <div key={l.id} style={{ border: "1px solid #ddd", padding: 10, marginBottom: 8 }}>
                    <div><b>{l.action}</b></div>
                    <div>Role: {l.actorRole}</div>
                    <div>Actor: {l.actorUserId}</div>
                    <div>Entity: {l.entityType} {l.entityId || ""}</div>
                    <div>Time: {new Date(l.createdAt).toLocaleString()}</div>
                </div>
            ))}

            {items.length === 0 && <p>No logs yet.</p>}
        </div>
    );
};

export default AuditLogs;
