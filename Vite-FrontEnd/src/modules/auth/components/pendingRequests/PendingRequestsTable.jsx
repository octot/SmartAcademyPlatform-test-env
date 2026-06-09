import PendingRequestRow
    from "./PendingRequestRow";

// PendingRequestsTable
import "../styles/PendingRequestsTable.css"
function PendingRequestsTable({ requests, onView,onApprove,onReject }) {



    return (

        <table className="pending-requests-table">

            <thead>

                <tr>

                    <th>User</th>

                    <th>Purpose</th>

                    <th>Requested</th>

                    <th>Email Verified</th>

                    <th>Actions</th>

                    <th>Status</th>

                </tr>

            </thead>

            <tbody>

                {
                    requests.map(request => (

                        // User Info Purpose, Requested Date Status, Badge Action Buttons
                        <PendingRequestRow
                            key={request.id}
                            request={request}
                            onView={onView}
                            onApprove={onApprove}
                            onReject={onReject}
                        />
                    ))
                }

            </tbody>

        </table>
    );
}

export default PendingRequestsTable;