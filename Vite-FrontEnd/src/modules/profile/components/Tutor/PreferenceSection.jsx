const PreferenceSection = ({ data }) => {
    return (
        <div>
            <h3>Preferences</h3>

            <p><b>Locations:</b> {data.preferredLocations?.join(", ")}</p>
            <p><b>Remarks:</b> {data.remarks}</p>
        </div>
    );
};

export default PreferenceSection;