import "./PreferencesInfoStep.css";

const PreferencesStep = ({
    formData,
    setFormData
}) => {

    const handleArrayChange = (
        field,
        value
    ) => {

        const values = value
            .split(",")
            .map((item) => item.trim())
            .filter(Boolean);

        setFormData((prev) => ({
            ...prev,
            [field]: values
        }));
    };

    const handleChange = (field, value) => {
        setFormData((prev) => ({
            ...prev,
            [field]: value
        }));
    };

    return (
        <div className="step-section">

            {/* Header */}
            <div className="step-section-header">

                <h3>Preferences</h3>

                <p>
                    Add your preferred teaching
                    locations and additional remarks.
                </p>

            </div>

            {/* Preferred Locations */}
            <div className="form-group">

                <label className="form-label">
                    Preferred Locations
                </label>

                <input
                    className="form-input"
                    type="text"
                    placeholder="Perinthalmanna, Manjeri"
                    value={
                        formData.preferredLocations?.join(", ")
                        || ""
                    }
                    onChange={(e) =>
                        handleArrayChange(
                            "preferredLocations",
                            e.target.value
                        )
                    }
                />

                <small className="field-hint">
                    Separate locations using commas
                </small>

            </div>

            {/* Remarks */}
            <div className="form-group">

                <label className="form-label">
                    Remarks
                </label>

                <textarea
                    className="form-input"
                    rows={4}
                    placeholder="Additional information..."
                    value={formData.remarks || ""}
                    onChange={(e) =>
                        handleChange(
                            "remarks",
                            e.target.value
                        )
                    }
                />

            </div>

            {/* Guidelines */}
            <div className="checkbox-group">

                <input
                    type="checkbox"
                    checked={
                        formData.guidelinesAccepted
                    }
                    onChange={(e) =>
                        handleChange(
                            "guidelinesAccepted",
                            e.target.checked
                        )
                    }
                />

                <label>
                    I accept the platform guidelines
                </label>

            </div>

        </div>
    );
};

export default PreferencesStep;