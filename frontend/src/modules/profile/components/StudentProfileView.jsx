import { useEffect, useState } from "react";
import { getStudentProfile } from "../api/ProfileApi";
import StudentProfileForm from "./StudentProfileForm";

const StudentProfileView = () => {
    const [data, setData] = useState(null);
    const [editMode, setEditMode] = useState(false);

    useEffect(() => {
        fetchProfile();
    }, []);

    const fetchProfile = async () => {
        const res = await getStudentProfile();
        setData(res.data);
    };

    if (!data) return <div>Loading...</div>;

    if (editMode) {
        return (
            <StudentProfileForm
                initialData={data}
                onSuccess={() => {
                    setEditMode(false);
                    fetchProfile();
                }}
            />
        );
    }

    return (
        <div>
            <p>Class: {data.studentClass}</p>
            <p>Syllabus: {data.syllabus}</p>

            <button onClick={() => setEditMode(true)}>
                Edit Profile
            </button>
        </div>
    );
};

export default StudentProfileView;