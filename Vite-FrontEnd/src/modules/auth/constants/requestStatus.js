export const REQUEST_STATUS = {
    PENDING: "PENDING",
    APPROVED: "APPROVED",
    REJECTED: "REJECTED"
};

export const REQUEST_STATUS_OPTIONS = [
    {
        value: "ALL",
        label: "All Statuses"
    },
    {
        value: REQUEST_STATUS.PENDING,
        label: "Pending"
    },
    {
        value: REQUEST_STATUS.APPROVED,
        label: "Approved"
    },
    {
        value: REQUEST_STATUS.REJECTED,
        label: "Rejected"
    }
];