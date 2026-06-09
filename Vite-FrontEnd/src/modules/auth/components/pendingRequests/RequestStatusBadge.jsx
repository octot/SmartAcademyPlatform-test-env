function RequestStatusBadge({ status }) {

    return (
        <span
            className={`status-badge status-${status.toLowerCase()}`}
        >
            {status}
        </span>
    );
}

export default RequestStatusBadge;