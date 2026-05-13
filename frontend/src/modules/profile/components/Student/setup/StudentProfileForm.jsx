const StudentProfileForm = ({ mode, formData, setFormData, onSubmit, submitting }) => {


    console.log("formData", formData)
    const handleChange = (e) => {
        const { name, value } = e.target;

        setFormData((prev) => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        onSubmit();
    };

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>Student Class</label>
                <input
                    type="text"
                    name="studentClass"
                    value={formData.studentClass}
                    onChange={handleChange}
                    required
                />
            </div>

            <div>
                <label>Syllabus</label>
                <textarea
                    name="syllabus"
                    value={formData.syllabus}
                    onChange={handleChange}
                />
            </div>

            <button type="submit" disabled={submitting}>
                {submitting ? "Saving..." : "Save Profile"}
            </button>
        </form>
    );
};

export default StudentProfileForm;