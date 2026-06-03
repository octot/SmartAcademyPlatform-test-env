// pages/AdminPendingApprovalPage.jsx

import {
    ShieldCheck,
    Clock3
} from "lucide-react";

import "./AdminPendingApprovalPage.css";

export default function AdminPendingApprovalPage() {

    return (

        <div className="admin-pending-page">

            <div className="admin-pending-card">

                <div className="admin-pending-icon">

                    <ShieldCheck size={56} />

                </div>

                <div className="admin-pending-content">

                    <h1>
                        Admin Request Submitted
                    </h1>

                    <p className="primary-message">

                        Your email has been verified
                        successfully and your admin
                        onboarding request is now
                        under review.

                    </p>

                    <div className="review-status-box">

                        <Clock3 size={18} />

                        <span>
                            Awaiting Super Admin Approval
                        </span>

                    </div>

                    <p className="secondary-message">

                        Once approved, your
                        administrative account will
                        be activated and you’ll be
                        able to access the admin
                        portal.

                    </p>

                </div>

                <div className="admin-pending-footer">

                    <a href="/login">
                        Back to Sign In
                    </a>

                </div>

            </div>

        </div>
    );
}