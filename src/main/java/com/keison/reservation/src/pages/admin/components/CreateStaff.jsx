import { useState } from "react";
import { adminCreateStaff } from "../../../api/adminUsers.api";

const CreateStaff = () => {
    const [form, setForm] = useState({
        email: "",
        password: "",
        fullName: "",
        phoneNumber: "",
        department: "",
        position: "",
    });

    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const submit = async (e) => {
        e.preventDefault();
        setError("");
        setSuccess("");

        try {
            const payload = {
                email: form.email.trim(),
                password: form.password,
                fullName: form.fullName.trim(),
                phoneNumber: form.phoneNumber.trim() || null,
                department: form.department.trim() || null,
                position: form.position.trim() || null,
            };

            const created = await adminCreateStaff(payload);
            setSuccess(`Staff created ✅ ${created.email} (${created.role})`);
            setForm({
                email: "",
                password: "",
                fullName: "",
                phoneNumber: "",
                department: "",
                position: "",
            });
        } catch (err) {
            setError(err?.response?.data?.message || "Failed to create staff");
        }
    };

    return (
        <div>
            <h2>Create Staff Account</h2>
            {error && <p style={{ color: "red" }}>{error}</p>}
            {success && <p style={{ color: "green" }}>{success}</p>}

            <form onSubmit={submit} style={{ display: "grid", gap: 8, maxWidth: 420 }}>
                <input
                    placeholder="Full name"
                    value={form.fullName}
                    onChange={(e) => setForm({ ...form, fullName: e.target.value })}
                />
                <input
                    placeholder="Email"
                    value={form.email}
                    onChange={(e) => setForm({ ...form, email: e.target.value })}
                />
                <input
                    type="password"
                    placeholder="Temporary password"
                    value={form.password}
                    onChange={(e) => setForm({ ...form, password: e.target.value })}
                />
                <input
                    placeholder="Phone number (optional)"
                    value={form.phoneNumber}
                    onChange={(e) => setForm({ ...form, phoneNumber: e.target.value })}
                />

                <input
                    placeholder="Department (optional)"
                    value={form.department}
                    onChange={(e) => setForm({ ...form, department: e.target.value })}
                />
                <input
                    placeholder="Position (optional)"
                    value={form.position}
                    onChange={(e) => setForm({ ...form, position: e.target.value })}
                />

                <button>Create Staff</button>
            </form>

            <p style={{ marginTop: 10, fontSize: 12, color: "#666" }}>
                Staff can login using the email + password you set here.
            </p>
        </div>
    );
};

export default CreateStaff;
