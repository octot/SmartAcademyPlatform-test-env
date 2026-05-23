import { useEffect, useState } from "react";
import { getStudentProfile, updateStudentProfile } from "../../../api/ProfileApi";
import StudentProfileForm from "./StudentProfileForm";
import { toast } from "react-toastify";
const StudentProfileView = () => {
    const [data, setData] = useState(null);
    const [formData, setFormData] = useState(null);
    const [editMode, setEditMode] = useState(false);
    const [submitting, setSubmitting] = useState(false);

    useEffect(() => {
        fetchProfile();
    }, []);

    const fetchProfile = async () => {
        const res = await getStudentProfile();
        setData(res.data);
    };

    const handleEdit = () => {
        setFormData(data);
        setEditMode(true);
    };
    const handleUpdate = async () => {
        try {
            setSubmitting(true);

            await updateStudentProfile(formData);

            setEditMode(false);
            fetchProfile();
            toast.success("Profile updated completed successfully");

        }
        catch (err) { console.log(err); }
        finally {
            setSubmitting(false);
        }
    };


    if (!data) return <div>Loading...</div>;

    if (editMode) {
        return (
            <StudentProfileForm
                mode="edit"
                formData={formData}
                setFormData={setFormData}
                onSubmit={handleUpdate}
                submitting={submitting}
            />
        );
    }

    return (
        <div>
            <p>Class: {data.studentClass}</p>
            <p>Syllabus: {data.syllabus}</p>

            <button onClick={handleEdit}>
                Edit Profile
            </button>
        </div>
    );
};

export default StudentProfileView;