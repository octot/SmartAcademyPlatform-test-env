import "./PaymentInfoStep.css";

const PaymentInfoStep = ({
    formData,
    setFormData
}) => {

    const payment = formData.payment || {};

    const handlePaymentChange = (
        field,
        value
    ) => {

        setFormData((prev) => ({
            ...prev,

            payment: {
                ...prev.payment,
                [field]: value
            }
        }));
    };

    return (
        <div className="step-section">

            {/* Header */}
            <div className="step-section-header">

                <h3>Payment Information</h3>

                <p>
                    Add your preferred payment
                    details for payouts.
                </p>

            </div>

            {/* Payment Method */}
            <div className="form-group">

                <label className="form-label">
                    Payment Method
                </label>

                <select
                    className="form-input"
                    value={payment.paymentMethod}
                    onChange={(e) =>
                        handlePaymentChange(
                            "paymentMethod",
                            e.target.value
                        )
                    }
                >
                    <option value="GPAY">
                        GPay
                    </option>

                    <option value="BANK">
                        Bank Transfer
                    </option>

                </select>

            </div>

            {/* GPAY */}
            {payment.paymentMethod === "GPAY" && (

                <div className="form-group">

                    <label className="form-label">
                        GPay Number
                    </label>

                    <input
                        className="form-input"
                        type="text"
                        placeholder="Enter GPay Number"
                        value={
                            payment.gpayNumber || ""
                        }
                        onChange={(e) =>
                            handlePaymentChange(
                                "gpayNumber",
                                e.target.value
                            )
                        }
                    />

                </div>

            )}

            {/* BANK */}
            {payment.paymentMethod === "BANK" && (
                <>

                    <div className="form-group">

                        <label className="form-label">
                            Account Holder Name
                        </label>

                        <input
                            className="form-input"
                            type="text"
                            placeholder="Enter Account Holder Name"
                            value={
                                payment.accountHolderName
                                || ""
                            }
                            onChange={(e) =>
                                handlePaymentChange(
                                    "accountHolderName",
                                    e.target.value
                                )
                            }
                        />

                    </div>

                    <div className="form-group">

                        <label className="form-label">
                            Bank Name
                        </label>

                        <input
                            className="form-input"
                            type="text"
                            placeholder="Enter Bank Name"
                            value={
                                payment.bankName || ""
                            }
                            onChange={(e) =>
                                handlePaymentChange(
                                    "bankName",
                                    e.target.value
                                )
                            }
                        />

                    </div>

                    <div className="form-group">

                        <label className="form-label">
                            IFSC Code
                        </label>

                        <input
                            className="form-input"
                            type="text"
                            placeholder="Enter IFSC Code"
                            value={
                                payment.ifscCode || ""
                            }
                            onChange={(e) =>
                                handlePaymentChange(
                                    "ifscCode",
                                    e.target.value
                                )
                            }
                        />

                    </div>

                </>
            )}

        </div>
    );
};

export default PaymentInfoStep;