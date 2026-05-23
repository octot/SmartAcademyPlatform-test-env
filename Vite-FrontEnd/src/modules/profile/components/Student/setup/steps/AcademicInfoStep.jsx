
import "./AcademicInfoStep.css";

const AcademicInfoStep = ({
    formData,
    setFormData
}) => {

    const handleChange = (
        field,
        value
    ) => {

        setFormData((prev) => ({
            ...prev,
            [field]: value
        }));
    };

    return (
        <div className="step-section">

            {/* Header */}
            <div className="step-section-header">

                <h3>
                    Academic Information
                </h3>

                <p>
                    Add your class and syllabus
                    details.
                </p>

            </div>

            {/* Student Class */}
            <div className="form-group">

                <label className="form-label">
                    Student Class
                </label>

                <select
                    className="form-input"
                    value={formData.studentClass}
                    onChange={(e) =>
                        handleChange(
                            "studentClass",
                            e.target.value
                        )
                    }
                >
                    <option value="">
                        Select Class
                    </option>

                    <option value="LKG">
                        LKG
                    </option>

                    <option value="UKG">
                        UKG
                    </option>

                    <option value="CLASS_1">
                        Class 1
                    </option>

                    <option value="CLASS_2">
                        Class 2
                    </option>

                    <option value="CLASS_3">
                        Class 3
                    </option>

                    <option value="CLASS_4">
                        Class 4
                    </option>

                    <option value="CLASS_5">
                        Class 5
                    </option>

                    <option value="CLASS_6">
                        Class 6
                    </option>

                    <option value="CLASS_7">
                        Class 7
                    </option>

                    <option value="CLASS_8">
                        Class 8
                    </option>

                    <option value="CLASS_9">
                        Class 9
                    </option>

                    <option value="CLASS_10">
                        Class 10
                    </option>

                    <option value="CLASS_11">
                        Class 11
                    </option>

                    <option value="CLASS_12">
                        Class 12
                    </option>

                </select>

            </div>

            {/* Syllabus */}
            <div className="form-group">

                <label className="form-label">
                    Syllabus
                </label>

                <select
                    className="form-input"
                    value={formData.syllabus}
                    onChange={(e) =>
                        handleChange(
                            "syllabus",
                            e.target.value
                        )
                    }
                >
                    <option value="">
                        Select Syllabus
                    </option>

                    <option value="CBSE">
                        CBSE
                    </option>

                    <option value="ICSE">
                        ICSE
                    </option>

                    <option value="STATE">
                        STATE
                    </option>

                </select>

            </div>

        </div>
    );
};

export default AcademicInfoStep;