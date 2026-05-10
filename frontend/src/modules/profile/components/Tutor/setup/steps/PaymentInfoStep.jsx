const PaymentStep = ({
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
        <div>
            <h3>Payment</h3>

            {/* Payment Method */}
            <div>
                <label>Payment Method</label>

                <select
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
                        Bank
                    </option>
                </select>
            </div>

            {/* GPAY */}
            {payment.paymentMethod === "GPAY" && (
                <div>
                    <label>GPay Number</label>

                    <input
                        type="text"
                        value={payment.gpayNumber || ""}
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
                    <div>
                        <label>
                            Account Holder Name
                        </label>

                        <input
                            type="text"
                            value={
                                payment.accountHolderName || ""
                            }
                            onChange={(e) =>
                                handlePaymentChange(
                                    "accountHolderName",
                                    e.target.value
                                )
                            }
                        />
                    </div>

                    <div>
                        <label>Bank Name</label>

                        <input
                            type="text"
                            value={payment.bankName || ""}
                            onChange={(e) =>
                                handlePaymentChange(
                                    "bankName",
                                    e.target.value
                                )
                            }
                        />
                    </div>

                    <div>
                        <label>IFSC Code</label>

                        <input
                            type="text"
                            value={payment.ifscCode || ""}
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

export default PaymentStep;