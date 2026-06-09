import RequestStatusBadge from "./RequestStatusBadge";
function PendingRequestRow({
    request, onView, onApprove, onReject
}) {

    return (

        <tr>
            <td>
                <div className="user-info">

                    <div className="user-avatar">

                        {request.username.charAt(0)}
                    </div>

                    <div>

                        <strong>
                            {request.username}
                        </strong>

                        <div className="user-email">
                            {request.email}
                        </div>

                    </div>

                </div>

            </td>

            <td>
                {request.purpose}
            </td>

            <td>
                {request.requestedAt}
            </td>

            <td>
                <span
                    className={
                        request.emailVerified
                            ? "status-badge verified"
                            : "status-badge pending"
                    }
                >
                    {
                        request.emailVerified
                            ? "Verified"
                            : "Pending"
                    }
                </span>

            </td>

            <td>

                <div className="action-buttons">

                    <button className="view-btn"
                        onClick={() => onView(request)}>
                        View
                    </button>

                    <button className="approve-btn"
                        onClick={() => onApprove(request)}>
                        Approve
                    </button>

                    <button
                        className="reject-btn"
                        onClick={() => {
                            onReject(request);
                        }}
                    >
                        Reject
                    </button>

                </div>

            </td>
            <td>

                <RequestStatusBadge
                    status={request.status}
                />

            </td>

        </tr>
    );
}

export default PendingRequestRow;