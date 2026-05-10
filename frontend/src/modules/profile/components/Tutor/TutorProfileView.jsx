import { useEffect, useState } from "react";
import { getTutorProfile, updateTutorProfile } from "../../api/ProfileApi";
import TutorProfileForm from "./TutorProfileForm";
import BasicInfoSection from "./BasicInfoSection";
import PaymentSection from "./PaymentSection";
import TeachingSection from "./TeachingSection";
import PreferenceSection from "./PreferenceSection";
const TutorProfileView = () => {
    const [data, setData] = useState(null);
    const [editMode, setEditMode] = useState(false);
    const [formData, setFormData] = useState(null);
    const [submitting, setSubmitting] = useState(false);
    useEffect(() => {
        fetchProfile();
    }, []);

    const fetchProfile = async () => {
        try {
            const res = await getTutorProfile();
            setData(res.data);
        } catch (error) {
            console.log(error);
        }
    };

    const handleEdit = () => {
        setFormData(data);   // 🔥 clean assignment
        setEditMode(true);
    };
    const handleSubmit = async () => {
        try {
            setSubmitting(true);
            await updateTutorProfile(formData);

            setEditMode(false);
            fetchProfile();
        }
        catch (error) {
            console.log(error);
        }
        finally {
            setSubmitting(false);
        }
    };

    if (!data) return <div>Loading...</div>;

    if (editMode) {
        return (
            <TutorProfileForm
                mode="edit"
                formData={formData}
                setFormData={setFormData}
                onSubmit={handleSubmit}
                submitting={submitting}
            />
        );
    }
    return (
        <div>
            <h2>Tutor Profile</h2>

            <BasicInfoSection data={data} />
            <TeachingSection data={data} />
            <PreferenceSection data={data} />
            <PaymentSection data={data} />

            <button onClick={handleEdit}>Edit</button>
        </div>
    )

}
export default TutorProfileView;