import {
    REQUEST_STATUS_OPTIONS
} from "../../constants/requestStatus";

function PendingRequestFilters({

    searchTerm,

    onSearchChange,

    statusFilter,

    onStatusChange,

    sortDirection,

    onSortChange

}) {
    console.log({
        searchTerm,
        statusFilter,
        sortDirection
    });

    return (

        <div className="request-filters">

            <input
                type="text"
                value={searchTerm}
                placeholder="Search by email or username..."
                onChange={(e) =>
                    onSearchChange(
                        e.target.value
                    )
                }
            />

            <select
                value={statusFilter}
                onChange={(e) =>
                    onStatusChange(
                        e.target.value
                    )
                }
            >

                {
                    REQUEST_STATUS_OPTIONS.map(option => (

                        <option
                            key={option.value}
                            value={option.value}
                        >
                            {option.label}
                        </option>

                    ))
                }

            </select>

            <select
                value={sortDirection}
                onChange={(e) =>
                    onSortChange(
                        e.target.value
                    )
                }
            >

                <option value="DESC">
                    Newest First
                </option>

                <option value="ASC">
                    Oldest First
                </option>

            </select>

        </div>

    );
}

export default PendingRequestFilters;