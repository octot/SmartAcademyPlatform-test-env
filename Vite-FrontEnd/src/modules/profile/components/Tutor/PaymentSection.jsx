const PaymentSection = ({ data }) => {
    const payment = data.payment || {};

    return (
        <div>
            <h3>Payment</h3>

            <p><b>Method:</b> {payment.paymentMethod}</p>

            {payment.paymentMethod === "GPAY" && (
                <p><b>GPay:</b> {payment.gpayNumber}</p>
            )}

            {payment.paymentMethod === "BANK" && (
                <>
                    <p><b>Account Holder:</b> {payment.accountHolderName}</p>
                    <p><b>Bank:</b> {payment.bankName}</p>
                    <p><b>IFSC:</b> {payment.ifscCode}</p>
                </>
            )}
        </div>
    );
};

export default PaymentSection;