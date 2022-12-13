package com.user.smart.models

data class Authorization(
    val ApprovalReferenceCode: String,
    val AuthorizationApprovalDescription: String,
    val AuthorizationDate: String,
    val AuthorizationResponseCode: String,
    val AuthorizationResponseDescription: String,
    val AuthorizationTime: String,
    val AuthorizedChargeAmount: String,
    val AuthorizingTerminalID: String,
    val ElectronicSignature: String,
    val ForceOnLineFlag: String,
    val HostAuthorizedFlag: String,
    val PreAuthorizationFlag: String,
    val ProviderID: String,
    val ReferenceNumber: String,
    val RequestedAmount: String
)