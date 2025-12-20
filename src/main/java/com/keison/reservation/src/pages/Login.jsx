import { useState } from "react";
import { login as loginApi } from "../api/auth.api";
import { useAuth } from "../auth/AuthContext";
import { useNavigate } from "react-router-dom";

const Login = () => {
    const [form, setForm] = useState({ email: "", password: "" });
    const { login } = useAuth();
    const navigate = useNavigate();

    const submit = async (e) => {
        e.preventDefault();
        const res = await loginApi(form);
        login(res);

        if (res.user.role === "PASSENGER") navigate("/passenger");
        if (res.user.role === "STAFF") navigate("/staff");
        if (res.user.role === "ADMIN") navigate("/admin");
    };

    return (
        <form onSubmit={submit}>
            <h2>Login</h2>
            <input placeholder="Email" onChange={e => setForm({ ...form, email: e.target.value })} />
            <input type="password" placeholder="Password" onChange={e => setForm({ ...form, password: e.target.value })} />
            <button>Login</button>
        </form>
    );
};

export default Login;
