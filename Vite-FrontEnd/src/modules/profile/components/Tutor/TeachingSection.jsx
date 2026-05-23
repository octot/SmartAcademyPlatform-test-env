const TeachingSection = ({ data }) => {
    return (
        <div>
            <h3>Teaching</h3>

            <p><b>Subjects:</b> {data.subjects?.join(", ")}</p>
            <p><b>Classes:</b> {data.classesHandled?.join(", ")}</p>
            <p><b>Syllabus:</b> {data.syllabusHandled?.join(", ")}</p>
        </div>
    );
};

export default TeachingSection;