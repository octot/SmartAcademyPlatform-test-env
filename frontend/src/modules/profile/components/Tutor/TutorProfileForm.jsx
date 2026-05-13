    import { useState } from "react";

    import { toast } from "react-toastify";
    const TutorProfileForm = ({ mode, formData, setFormData, onSubmit, submitting }) => {
        const [locationInput, setLocationInput] = useState("");
        const handleChange = (e) => {
            const { name, value, type, checked } = e.target;

            setFormData((prev) => ({
                ...prev,
                [name]: type === "checkbox" ? checked : value
            }));
        };

        const handlePaymentChange = (e) => {
            const { name, value } = e.target;

            setFormData((prev) => ({
                ...prev,
                payment: {
                    ...prev.payment,
                    [name]: value
                }
            }));
        };

        const handleMultiSelect = (e, field) => {
            const values = Array.from(e.target.selectedOptions, (opt) => opt.value);

            setFormData((prev) => ({
                ...prev,
                [field]: values
            }));
        };

        const handleSubmit = async (e) => {
            e.preventDefault();
            onSubmit();
            toast.success("Profile updated completed successfully");

        };

        return (
            <form onSubmit={handleSubmit}>

                <h2>
                    {mode === "create" ? "Complete your profile" : "Edit Profile"}
                </h2>

                <input
                    type="text"
                    name="aadhaarNumber"
                    placeholder="Aadhaar"
                    value={formData.aadhaarNumber}
                    onChange={handleChange}
                    required
                />

                <label>
                    <input
                        type="checkbox"
                        name="hasVehicle"
                        checked={formData.hasVehicle}
                        onChange={handleChange}
                    />
                    Has Vehicle
                </label>

                {formData.hasVehicle && (
                    <select name="vehicleType" onChange={handleChange}>
                        <option value="">Select Vehicle</option>
                        <option value="Bike">Bike</option>
                        <option value="Car">Car</option>
                    </select>
                )}

                <input
                    type="text"
                    name="qualification"
                    placeholder="Qualification"
                    value={formData.qualification}
                    onChange={handleChange}
                />

                <input
                    type="number"
                    name="experienceYears"
                    placeholder="Experience"
                    value={formData.experienceYears}
                    onChange={handleChange}
                />

                <label>
                    <input
                        type="checkbox"
                        name="entranceCoaching"
                        checked={formData.entranceCoaching}
                        onChange={handleChange}
                    />
                    Entrance Coaching
                </label>

                <h3>Teaching Info</h3>

                <select multiple onChange={(e) => handleMultiSelect(e, "subjects")}>
                    <option value="Maths">Maths</option>
                    <option value="Physics">Physics</option>
                </select>

                <select multiple onChange={(e) => handleMultiSelect(e, "classesHandled")}>
                    <option value="CLASS_10">Class 10</option>
                    <option value="CLASS_12">Class 12</option>
                </select>

                <select multiple onChange={(e) => handleMultiSelect(e, "syllabusHandled")}>
                    <option value="CBSE">CBSE</option>
                    <option value="STATE">State</option>
                </select>

                <h3>Preferences</h3>

                <input
                    type="text"
                    value={locationInput}
                    placeholder="Add location"
                    onChange={(e) => setLocationInput(e.target.value)}
                />

                <button
                    type="button"
                    onClick={() => {
                        if (!locationInput) return;

                        setFormData((prev) => ({
                            ...prev,
                            preferredLocations: [
                                ...prev.preferredLocations,
                                locationInput
                            ]
                        }));

                        setLocationInput("");
                    }}
                >
                    Add
                </button>

                <textarea
                    name="remarks"
                    placeholder="Remarks"
                    value={formData.remarks}
                    onChange={handleChange}
                />

                {mode === "create" && <label>
                    <input
                        type="checkbox"
                        name="guidelinesAccepted"
                        checked={formData.guidelinesAccepted}
                        onChange={handleChange}
                    />
                    Accept Guidelines
                </label>
                }

                <h3>Payment</h3>

                <select
                    name="paymentMethod"
                    value={formData.payment.paymentMethod}
                    onChange={handlePaymentChange}
                >
                    <option value="GPAY">GPay</option>
                    <option value="BANK">Bank</option>
                </select>

                {/* 🔥 GPAY */}
                {formData.payment.paymentMethod === "GPAY" && (
                    <input
                        type="text"
                        name="gpayNumber"
                        placeholder="GPay Number"
                        value={formData.payment.gpayNumber}
                        onChange={handlePaymentChange}
                    />
                )}

                {/* 🔥 BANK */}
                {formData.payment.paymentMethod === "BANK" && (
                    <>
                        <input name="accountHolderName" placeholder="Account Holder" onChange={handlePaymentChange} />
                        <input name="bankName" placeholder="Bank Name" onChange={handlePaymentChange} />
                        <input name="branchName" placeholder="Branch" onChange={handlePaymentChange} />
                        <input name="accountNumber" placeholder="Account Number" onChange={handlePaymentChange} />
                        <input name="ifscCode" placeholder="IFSC" onChange={handlePaymentChange} />
                    </>
                )}

                <button type="submit" disabled={submitting}>
                    {mode === "create" ? "Create Profile" : "Update Profile"}
                </button>
            </form>
        );
    };

    export default TutorProfileForm;