import "./RegisterForm.css"
import { useState } from "react";
import { register } from "../api/authApi";
import { useNavigate } from "react-router-dom";
export default function RegisterForm() {

    const navigate = useNavigate();

    const [form, setForm] = useState
        ({
            name: "",
            email: "",
            password: ""
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
            navigate("/login")
        }
        catch (err) {
            console.error(err);
        }
    }

    return (
        <div className="register-card">
            <h2>Create Account</h2>
            <form onSubmit={handleSubmit}>
                <input type="text" name="name" placeHolder="Enter Name" value={form.name} onChange={handleChange} className="input-field" />
                <input type="email" name="email" placeholder="Enter Email" value={form.email} onChange={handleChange} className="input-field" />
                <input
                    type="password"
                    name="password"
                    placeholder="Enter Password"
                    value={form.password}
                    onChange={handleChange}
                    className="input-field"
                />

                <button type="submit" className="register-btn">
                    Sign Up →
                </button>
            </form>
        </div>
    )

}