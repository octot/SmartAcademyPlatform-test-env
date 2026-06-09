import "../styles/PendingRequestDetailsModal.css";

function PendingRequestDetailsModal({

    request,

    onClose

}) {

    if (!request) {

        return null;
    }

    return (

        <div
            className="modal-overlay"
            onClick={onClose}
        >

            <div
                className="request-modal"
                onClick={(e) =>
                    e.stopPropagation()
                }
            >

                <div className="modal-header">

                    <div className="modal-title-section">

                        <h2>
                            Admin Request Details
                        </h2>

                        <span
                            className={`status-badge status-${request.status.toLowerCase()}`}
                        >
                            {request.status}
                        </span>

                    </div>

                    <button
                        className="close-icon"
                        onClick={onClose}
                    >
                        ×
                    </button>

                </div>

                <div className="modal-content">

                    <div className="applicant-section">

                        <div className="applicant-avatar">

                            {
                                request.username
                                    ?.charAt(0)
                                    ?.toUpperCase()
                            }

                        </div>

                        <div>

                            <h3>
                                {request.username}
                            </h3>

                            <p>
                                {request.email}
                            </p>

                        </div>

                    </div>

                    <div className="detail-section">

                        <label>
                            PURPOSE
                        </label>

                        <p>
                            {request.purpose}
                        </p>

                    </div>

                    <div className="detail-section">

                        <label>
                            DESCRIPTION
                        </label>

                        <p>
                            {request.description}
                        </p>

                    </div>

                    <div className="bottom-info">

                        <div>

                            <label>
                                VERIFICATION STATUS
                            </label>

                            <p className="verified">

                                ✓ Email Verified

                            </p>

                        </div>

                        <div>

                            <label>
                                REQUESTED ON
                            </label>

                            <p>
                                {request.requestedAt}
                            </p>

                        </div>

                    </div>

                </div>

                <div className="modal-footer">

                    <button
                        className="close-btn"
                        onClick={onClose}
                    >
                        Close
                    </button>

                </div>

            </div>

        </div>
    );
}

export default PendingRequestDetailsModal;