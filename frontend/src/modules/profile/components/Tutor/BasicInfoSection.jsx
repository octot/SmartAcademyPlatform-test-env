const BasicInfoSection = ({ data }) => {
    return (
        <div>
            <h3>Basic Info</h3>

            <p><b>Aadhaar:</b> {data.aadhaarNumber}</p>
            <p><b>Qualification:</b> {data.qualification}</p>
            <p><b>Experience:</b> {data.experienceYears} years</p>

            <p>
                <b>Vehicle:</b>{" "}
                {data.hasVehicle ? data.vehicleType : "No"}
            </p>
        </div>
    );
};

export default BasicInfoSection;