import "./TeachingInfoStep.css";

const CLASS_OPTIONS = [
    { value: "LKG", label: "LKG" },
    { value: "UKG", label: "UKG" },
    { value: "CLASS_1", label: "Class 1" },
    { value: "CLASS_2", label: "Class 2" },
    { value: "CLASS_3", label: "Class 3" },
    { value: "CLASS_4", label: "Class 4" },
    { value: "CLASS_5", label: "Class 5" },
    { value: "CLASS_6", label: "Class 6" },
    { value: "CLASS_7", label: "Class 7" },
    { value: "CLASS_8", label: "Class 8" },
    { value: "CLASS_9", label: "Class 9" },
    { value: "CLASS_10", label: "Class 10" },
    { value: "CLASS_11", label: "Class 11" },
    { value: "CLASS_12", label: "Class 12" }
];
const SYLLABUS_OPTIONS = [
    "CBSE",
    "ICSE",
    "STATE"
];
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
    const handleMultiSelect = (field, selectedValue) => {
        setFormData((prev) => {
            const currentValues = prev[field] || [];
            const isSelected = currentValues.includes(selectedValue);

            return {
                ...prev,
                [field]: isSelected
                    ? currentValues.filter((v) => v !== selectedValue)
                    : [...currentValues, selectedValue]
            };
        });
    };

    return (
        <div className="step-section">

            {/* Header */}
            <div className="step-section-header">

                <h3>
                    Teaching Information
                </h3>

                <p>
                    Add subjects, syllabus and
                    classes you can handle.
                </p>

            </div>

            {/* Entrance Coaching */}
            <div className="checkbox-group">

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

                <label>
                    Provides Entrance Coaching
                </label>

            </div>

            {/* Subjects */}
            <div className="form-group">

                <label className="form-label">
                    Subjects
                </label>

                <input
                    className="form-input"
                    type="text"
                    placeholder="Maths, Physics"
                    value={
                        formData.subjects?.join(", ")
                        || ""
                    }
                    onChange={(e) =>
                        handleArrayChange(
                            "subjects",
                            e.target.value
                        )
                    }
                />

                <small className="field-hint">
                    Separate values using commas
                </small>

            </div>

            {/* Classes */}
            <div className="form-group">

                <label className="form-label">
                    Classes Handled
                </label>

                <div className="checkbox-group-scroll">
                    {CLASS_OPTIONS.map((classOption) => (
                        <div key={classOption.value} className="checkbox-item">
                            <input
                                type="checkbox"
                                id={`class-${classOption.value}`}
                                checked={(formData.classesHandled || []).includes(classOption.value)}
                                onChange={(e) =>
                                    handleMultiSelect("classesHandled", classOption.value)
                                }
                            />
                            <label htmlFor={`class-${classOption.value}`}>
                                {classOption.label}
                            </label>
                        </div>
                    ))}
                </div>

            </div>

            {/* Syllabus */}
            <div className="form-group">

                <label className="form-label">
                    Syllabus Handled
                </label>

                <div className="checkbox-group-vertical">
                    {SYLLABUS_OPTIONS.map((syllabusOption) => (
                        <div key={syllabusOption} className="checkbox-item">
                            <input
                                type="checkbox"
                                id={`syllabus-${syllabusOption}`}
                                checked={(formData.syllabusHandled || []).includes(syllabusOption)}
                                onChange={(e) =>
                                    handleMultiSelect("syllabusHandled", syllabusOption)
                                }
                            />
                            <label htmlFor={`syllabus-${syllabusOption}`}>
                                {syllabusOption}
                            </label>
                        </div>
                    ))}
                </div>

            </div>

        </div>
    );
};

export default TeachingInfoStep;