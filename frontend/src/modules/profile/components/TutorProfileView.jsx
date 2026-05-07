import { useEffect, useState } from "react";
import { getTutorProfile } from "../api/profileApi";
import TutorProfileForm from "./TutorProfileForm";

const TutorProfileView = () => {
    const [data, setData] = useState(null);
    const [editMode, setEditMode] = useState(false);

    useEffect(() => {
        fetchProfile();
    }, []);

    const fetchProfile = async () => {
        const res = await getTutorProfile();
        setData(res.data);
    };

    if (!data) return <div>Loading...</div>;

    if (editMode) {
        return (
            <TutorProfileForm
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
            <p>Qualification: {data.qualification}</p>
            <p>Experience: {data.experienceYears}</p>

            <button onClick={() => setEditMode(true)}>
                Edit Profile
            </button>
        </div>
    );
};

export default TutorProfileView;