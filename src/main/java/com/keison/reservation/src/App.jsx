import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import PassengerDashboard from "./pages/passenger/PassengerDashboard";
import StaffDashboard from "./pages/staff/StaffDashboard";
import AdminDashboard from "./pages/admin/AdminDashboard";
import ProtectedRoute from "./auth/ProtectedRoute";
import { AuthProvider } from "./auth/AuthContext";

function App() {
    return (
        <AuthProvider>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<Login />} />

                    <Route path="/passenger" element={
                        <ProtectedRoute role="PASSENGER">
                            <PassengerDashboard />
                        </ProtectedRoute>
                    } />

                    <Route path="/staff" element={
                        <ProtectedRoute role="STAFF">
                            <StaffDashboard />
                        </ProtectedRoute>
                    } />

                    <Route path="/admin" element={
                        <ProtectedRoute role="ADMIN">
                            <AdminDashboard />
                        </ProtectedRoute>
                    } />
                </Routes>
            </BrowserRouter>
        </AuthProvider>
    );
}

export default App;
