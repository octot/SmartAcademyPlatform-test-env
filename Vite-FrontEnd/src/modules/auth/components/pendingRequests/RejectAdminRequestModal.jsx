import { useState } from "react";

import "../styles/RejectAdminRequestModal.css";

function RejectAdminRequestModal({

    request,

    onClose,

    onReject

}) {


    const [reason, setReason] =
        useState("");

    const [comment, setComment] =
        useState("");

    const [error, setError] =
        useState("");

    if (!request) {

        return null;
    }

    const handleReject = () => {
        console.log("Reject Request");
        if (!reason) {

            setError(
                "Please select a reason"
            );

            return;
        }

        onReject({
            requestId: request.id,
            reason,
            comment
        });
    };

    return (

        <div
            className="reject-modal-overlay"
            onClick={onClose}
        >

            <div
                className="reject-modal"
                onClick={(e) =>
                    e.stopPropagation()
                }
            >

                <div className="reject-modal-header">

                    <h2>
                        Reject Admin Request
                    </h2>

                    <button
                        className="reject-modal-close"
                        onClick={onClose}
                    >
                        ×
                    </button>

                </div>

                <div className="reject-modal-content">

                    <div className="warning-banner">

                        ⚠ You are about to reject
                        this admin onboarding request.

                    </div>

                    <div className="reject-summary-card">

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

                    <div className="form-group">

                        <label>
                            Reason for Rejection *
                        </label>

                        <select
                            value={reason}
                            onChange={(e) =>
                                setReason(
                                    e.target.value
                                )
                            }
                        >

                            <option value="">
                                Select a reason...
                            </option>

                            <option
                                value="INSUFFICIENT_BUSINESS_JUSTIFICATION"
                            >
                                Insufficient Business Justification
                            </option>

                            <option
                                value="INVALID_INFORMATION"
                            >
                                Invalid Information
                            </option>

                            <option
                                value="DUPLICATE_REQUEST"
                            >
                                Duplicate Request
                            </option>

                            <option
                                value="SECURITY_CONCERN"
                            >
                                Security Concern
                            </option>

                            <option
                                value="OTHER"
                            >
                                Other
                            </option>

                        </select>

                    </div>

                    <div className="form-group">

                        <label>
                            Additional Comments
                            (Optional)
                        </label>

                        <textarea
                            rows="4"
                            value={comment}
                            onChange={(e) =>
                                setComment(
                                    e.target.value
                                )
                            }
                            placeholder="
Provide context for this rejection..."
                        />

                    </div>

                    {
                        error && (

                            <small
                                className="reject-error"
                            >
                                {error}
                            </small>

                        )
                    }

                </div>

                <div className="reject-modal-footer">

                    <button
                        className="cancel-btn"
                        onClick={onClose}
                    >
                        Cancel
                    </button>

                    <button
                        className="reject-btn"
                        onClick={handleReject}
                    >
                        Reject Request
                    </button>

                </div>

            </div>

        </div>
    );
}

export default RejectAdminRequestModal;