import "./RegisterForm.css"
import { useState } from "react";
import { register } from "../api/authApi";
import { useNavigate } from "react-router-dom";
import ROLES from "../../shared/constants/roles"
export default function RegisterForm() {

    const navigate = useNavigate();

    const [form, setForm] = useState
        ({
            username: "",
            email: "",
            password: "",
            role: ROLES.STUDENT
        })
    const handleChange = (e) => {
        setForm(
            {
                ...form,
                [e.target.name]: e.target.value
            })
    }
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await register(form);
            alert("Registered succesfully")
            navigate("/verify-email", { state: { login: form.email ,triggerOtp:true} });
        }
        catch (err) {
            console.error(err);
        }
    }

    return (
        <div className="register-card">
            <h2>Create Account</h2>
            <form onSubmit={handleSubmit}>
                <input type="text" name="username" placeHolder="Enter Name" value={form.username} onChange={handleChange} className="input-field" />
                <input type="email" name="email" placeholder="Enter Email" value={form.email} onChange={handleChange} className="input-field" />
                <input
                    type="password"
                    name="password"
                    placeholder="Enter Password"
                    value={form.password}
                    onChange={handleChange}
                    className="input-field"
                />
                <label>
                    <input
                        type="radio"
                        value={ROLES.STUDENT}
                        checked={form.role === ROLES.STUDENT}
                        onChange={handleChange}
                    />
                    Student
                </label>
                <label>
                    <input
                        type="radio"
                        value={ROLES.TUTOR}
                        checked={form.role === ROLES.TUTOR}
                        onChange={handleChange}
                    />
                    Tutor
                </label>
                <br />
                <button type="submit" className="register-btn">
                    Sign Up →
                </button>
            </form>
        </div>
    )

}