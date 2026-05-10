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
        <div>


            {/* Preferred Locations */}
            <div>
                <label>Preferred Locations</label>

                <input
                    type="text"
                    placeholder="Perinthalmanna, Manjeri"
                    value={
                        formData.preferredLocations?.join(", ") || ""
                    }
                    onChange={(e) =>
                        handleArrayChange(
                            "preferredLocations",
                            e.target.value
                        )
                    }
                />
            </div>

            {/* Remarks */}
            <div>
                <label>Remarks</label>

                <textarea
                    rows={4}
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
            <div>
                <label>
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

                    I accept the guidelines
                </label>
            </div>
        </div>
    );
};

export default PreferencesStep;