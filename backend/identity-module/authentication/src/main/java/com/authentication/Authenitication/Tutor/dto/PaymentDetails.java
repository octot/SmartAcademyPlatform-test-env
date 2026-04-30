package com.authentication.Authenitication.Tutor.dto;

import com.authentication.Authenitication.Tutor.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDetails {

    @NotNull
    private PaymentMethod paymentMethod;

    private String gpayNumber;

    private String accountHolderName;
    private String bankName;
    private String branchName;
    private String accountNumber;
    private String ifscCode;



}
