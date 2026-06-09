import "../styles/ApproveAdminRequestModal.css";

function ApproveAdminRequestModal({

    request,

    onClose,

    onApprove

}) {

    if (!request) {

        return null;
    }

    return (

        <div
            className="approve-modal-overlay"
            onClick={onClose}
        >

            <div
                className="approve-modal"
                onClick={(e) =>
                    e.stopPropagation()
                }
            >

                <div className="approve-modal-header">

                    <h2>
                        Approve Admin Request
                    </h2>

                    <button
                        className="approve-modal-close"
                        onClick={onClose}
                    >
                        ×
                    </button>

                </div>

                <div className="approve-modal-content">

                    <p className="approve-message">

                        You are about to approve this
                        admin onboarding request.

                    </p>

                    <div className="approve-summary-card">

                        <div className="summary-row">

                            <span>
                                USERNAME
                            </span>

                            <strong>
                                {request.username}
                            </strong>

                        </div>

                        <div className="summary-row">

                            <span>
                                EMAIL
                            </span>

                            <strong>
                                {request.email}
                            </strong>

                        </div>

                        <div className="summary-row">

                            <span>
                                PURPOSE
                            </span>

                            <strong>
                                {request.purpose}
                            </strong>

                        </div>

                    </div>

                    <div className="approval-effects">

                        <div>
                            ✓ Admin account will be activated
                        </div>

                        <div>
                            ✓ User can sign in immediately
                        </div>

                        <div>
                            ✓ Approval notification email will be sent
                        </div>

                    </div>

                </div>

                <div className="approve-modal-footer">

                    <button
                        className="cancel-btn"
                        onClick={onClose}
                    >
                        Cancel
                    </button>

                    <button
                        className="approve-request-btn"
                        onClick={() => {
                            console.log("thisapprovalclicked")
                            onApprove(request)
                        }

                        }

                    >
                        Approve Request
                    </button>

                </div>

            </div>

        </div>
    );
}

export default ApproveAdminRequestModal;