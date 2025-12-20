import { useAuth } from "../auth/AuthContext";
import { useNavigate } from "react-router-dom";

const PortalHeader = ({ title }) => {
    const { user, logout } = useAuth();
    const navigate = useNavigate();

    const doLogout = () => {
        logout();
        navigate("/");
    };

    return (
        <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: 16 }}>
            <div>
                <h1 style={{ margin: 0 }}>{title}</h1>
                {user && (
                    <div style={{ fontSize: 12, color: "#666" }}>
                        {user.fullName || user.email} — <b>{user.role}</b>
                    </div>
                )}
            </div>
            <button onClick={doLogout}>Logout</button>
        </div>
    );
};

export default PortalHeader;
