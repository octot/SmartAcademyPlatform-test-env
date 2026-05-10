const TeachingInfoStep = ({
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
            <h3>Teaching Information</h3>

            {/* Entrance Coaching */}
            <div>
                <label>
                    <input
                        type="checkbox"
                        checked={formData.entranceCoaching}
                        onChange={(e) =>
                            handleChange(
                                "entranceCoaching",
                                e.target.checked
                            )
                        }
                    />

                    Provides Entrance Coaching
                </label>
            </div>

            {/* Subjects */}
            <div>
                <label>Subjects</label>

                <input
                    type="text"
                    placeholder="Maths, Physics"
                    value={
                        formData.subjects?.join(", ") || ""
                    }
                    onChange={(e) =>
                        handleArrayChange(
                            "subjects",
                            e.target.value
                        )
                    }
                />
            </div>

            {/* Classes Handled */}
            <div>
                <label>Classes Handled</label>

                <input
                    type="text"
                    placeholder="8, 9, 10"
                    value={
                        formData.classesHandled?.join(", ") || ""
                    }
                    onChange={(e) =>
                        handleArrayChange(
                            "classesHandled",
                            e.target.value
                        )
                    }
                />
            </div>

            {/* Syllabus */}
            <div>
                <label>Syllabus Handled</label>

                <input
                    type="text"
                    placeholder="CBSE, ICSE"
                    value={
                        formData.syllabusHandled?.join(", ") || ""
                    }
                    onChange={(e) =>
                        handleArrayChange(
                            "syllabusHandled",
                            e.target.value
                        )
                    }
                />
            </div>
        </div>
    );
};

export default TeachingInfoStep;