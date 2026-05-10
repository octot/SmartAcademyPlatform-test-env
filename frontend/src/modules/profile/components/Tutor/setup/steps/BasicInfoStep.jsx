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
        <div>
            <h3>Basic Info</h3>

            {/* Aadhaar */}
            <div>
                <label>Aadhaar Number</label>

                <input
                    type="text"
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
            <div>
                <label>Qualification</label>

                <input
                    type="text"
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
            <div>
                <label>Experience (Years)</label>

                <input
                    type="number"
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
            <div>
                <label>
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

                    Has Vehicle
                </label>
            </div>

            {/* Vehicle Type */}
            {formData.hasVehicle && (
                <div>
                    <label>Vehicle Type</label>

                    <select
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