
import { useState, useEffect } from "react";
import PendingRequestFilters
    from "../components/pendingRequests/PendingRequestFilters";

import PendingRequestsTable
    from "../components/pendingRequests/PendingRequestsTable";

import "../styles/PendingAdminRequestsPage.css";

import Pagination from "../components/pagination/Pagination";

import PendingRequestDetailsModal from "../components/pendingRequests/PendingRequestDetailsModal";

import ApproveAdminRequestModal
    from "../components/pendingRequests/ApproveAdminRequestModal";

import RejectAdminRequestModal
    from "../components/pendingRequests/RejectAdminRequestModal";

import { getAdminRequests, approveAdminRequest,rejectAdminRequest  } from "../api/authApi"

function PendingAdminRequestsPage() {
    const [requests, setRequests] =
        useState([]);
    const [loading, setLoading] =
        useState(false);
    const [selectedRequest, setSelectedRequest] =
        useState(null);
    const [isModalOpen, setIsModalOpen] =
        useState(false);
    const [
        approvalRequest,
        setRequestToApprove
    ] = useState(null);
    const [searchTerm, setSearchTerm] =
        useState("");
    const [statusFilter, setStatusFilter] =
        useState("ALL");
    const [sortDirection, setSortDirection] =
        useState("DESC");
    const [page, setPage] =
        useState(1);
    const pageSize = 5;
    const [totalPages, setTotalPages] =
        useState(0);
    const loadRequests = async () => {
        try {
            setLoading(true);
            const response =
                await getAdminRequests(
                    statusFilter,
                    page,
                    pageSize
                );
            setRequests(
                response.content
            );
            setTotalPages(
                response.totalPages
            );

        } catch (error) {
            console.error(
                "Failed to load requests",
                error
            );

        } finally {

            setLoading(false);

        }
    };
    const [
        requestToReject,
        setRequestToReject
    ] = useState(null);
    useEffect(() => {
        console.log(
            "requestToReject changed",
            requestToReject
        );

    }, [requestToReject]);
    const handleReject = async (
        rejectData
    ) => {
        try {
            await rejectAdminRequest(
                requestToReject.id,
                rejectData.reason,
                rejectData.comment
            );
            await loadRequests();
            setRequestToReject(null);

        } catch (error) {
            console.error(
                "Admin rejection failed",
                error
            );

        }
    };
    const handleSearchChange =
        (value) => {
            setSearchTerm(value);
            setPage(1);
        };
    const handleStatusChange =
        (value) => {
            setStatusFilter(value);
            setPage(1);
        };
    const handleSortChange =
        (value) => {
            setSortDirection(value);
            setPage(1);
        };
    const handleApprove = async () => {

        try {

            setRequestToApprove(true);
            await approveAdminRequest(
                approvalRequest?.id
            );
            await loadRequests();
            setRequestToApprove(null);
        } catch (error) {
            console.error(error);
        } finally {

            setRequestToApprove(false);

        }
    };
    const filteredRequests =
        requests.filter(request => {
            const matchesSearch =
                request.username
                    .toLowerCase()
                    .includes(
                        searchTerm.toLowerCase()
                    )

                ||

                request.email
                    .toLowerCase()
                    .includes(
                        searchTerm.toLowerCase()
                    );
            return (
                matchesSearch
            );

        });

    const sortedRequests =
        [...filteredRequests]
            .sort((a, b) => {

                const dateA =
                    new Date(
                        a.requestedAt
                    );

                const dateB =
                    new Date(
                        b.requestedAt
                    );

                return sortDirection === "DESC"

                    ? dateB - dateA

                    : dateA - dateB;

            });

    useEffect(() => {

        loadRequests();

    }, [page, statusFilter]);
    return (
        <div className="pending-requests-page">

            <div className="page-header">

                <h1>
                    Pending Onboarding Requests
                </h1>

                <p>
                    Review and approve
                    admin onboarding requests.
                </p>

            </div>

            {/*  Search Status Filter Sort Dropdown */}
            <PendingRequestFilters

                searchTerm={searchTerm}
                onSearchChange={handleSearchChange}

                statusFilter={statusFilter}
                onStatusChange={handleStatusChange}

                sortDirection={sortDirection}
                onSortChange={handleSortChange}

            />

            <PendingRequestsTable
                requests={sortedRequests}
                onView={(request) => {
                    setSelectedRequest(request);
                    setIsModalOpen(true);
                }}
                onApprove={setRequestToApprove}
                onReject={setRequestToReject}
            />
            <Pagination
                page={page}
                totalPages={totalPages}
                onPageChange={setPage}
            />
            {
                isModalOpen && (
                    <PendingRequestDetailsModal

                        request={selectedRequest}

                        onClose={() => {

                            setIsModalOpen(false);

                            setSelectedRequest(null);
                        }}

                    />
                )
            }
            {
                approvalRequest && (

                    <ApproveAdminRequestModal

                        request={approvalRequest}

                        onClose={() =>
                            setRequestToApprove(null)
                        }

                        onApprove={handleApprove}

                    />

                )
            }

            {
                requestToReject && (
                    <RejectAdminRequestModal
                        request={requestToReject}
                        onClose={() =>
                            setRequestToReject(null)
                        }
                        onReject={handleReject}
                    />

                )
            }
        </div>

    );
}

export default PendingAdminRequestsPage;