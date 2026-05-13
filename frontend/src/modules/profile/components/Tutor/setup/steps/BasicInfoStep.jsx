import "./BasicInfoStep.css";
import "../../setup/form-common.css"
const BasicInfoStep = ({
    formData,
    setFormData
}) => {

    const handleChange = (field, value) => {
        setFormData((prev) => ({
            ...prev,
            [field]: value
        }));
    };

    return (
        <div className="step-section">

            <div className="step-section-header">
                <h3>Basic Information</h3>

                <p>
                    Add your qualification and
                    personal details.
                </p>
            </div>

            {/* Aadhaar */}
            <div className="form-group">

                <label className="form-label">
                    Aadhaar Number
                </label>

                <input
                    className="form-input"
                    type="text"
                    placeholder="Enter Aadhaar Number"
                    value={formData.aadhaarNumber}
                    onChange={(e) =>
                        handleChange(
                            "aadhaarNumber",
                            e.target.value
                        )
                    }
                />

            </div>

            {/* Qualification */}
            <div className="form-group">

                <label className="form-label">
                    Qualification
                </label>

                <input
                    className="form-input"
                    type="text"
                    placeholder="Enter Qualification"
                    value={formData.qualification}
                    onChange={(e) =>
                        handleChange(
                            "qualification",
                            e.target.value
                        )
                    }
                />

            </div>

            {/* Experience */}
            <div className="form-group">

                <label className="form-label">
                    Experience (Years)
                </label>

                <input
                    className="form-input"
                    type="number"
                    placeholder="Years of Experience"
                    value={formData.experienceYears}
                    onChange={(e) =>
                        handleChange(
                            "experienceYears",
                            e.target.value
                        )
                    }
                />

            </div>

            {/* Vehicle Checkbox */}
            <div className="checkbox-group">

                <input
                    type="checkbox"
                    checked={formData.hasVehicle}
                    onChange={(e) =>
                        handleChange(
                            "hasVehicle",
                            e.target.checked
                        )
                    }
                />

                <label>
                    I have a vehicle
                </label>

            </div>

            {/* Vehicle Type */}
            {formData.hasVehicle && (

                <div className="form-group">

                    <label className="form-label">
                        Vehicle Type
                    </label>

                    <select
                        className="form-input"
                        value={formData.vehicleType}
                        onChange={(e) =>
                            handleChange(
                                "vehicleType",
                                e.target.value
                            )
                        }
                    >
                        <option value="">
                            Select Vehicle
                        </option>

                        <option value="BIKE">
                            Bike
                        </option>

                        <option value="CAR">
                            Car
                        </option>

                    </select>

                </div>

            )}

        </div>
    );
};

export default BasicInfoStep;