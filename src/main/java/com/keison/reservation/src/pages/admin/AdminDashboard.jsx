import { useState } from "react";
import CreateStaff from "./components/CreateStaff";
import AuditLogs from "./components/AuditLogs";
import PortalHeader from "../../components/PortalHeader";

const AdminDashboard = () => {
    const [tab, setTab] = useState("staff");

    return (
        <div style={{ padding: 16 }}>
            <PortalHeader title="Admin Portal" />
            <h1>Admin Portal</h1>

            <div style={{ display: "flex", gap: 8, marginBottom: 16 }}>
                <button onClick={() => setTab("staff")}>Create Staff</button>
                <button onClick={() => setTab("audit")}>Audit Logs</button>
            </div>

            {tab === "staff" && <CreateStaff />}
            {tab === "audit" && <AuditLogs />}
        </div>
    );
};

export default AdminDashboard;
