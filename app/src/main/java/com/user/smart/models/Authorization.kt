package com.user.smart.models

data class Authorization(
    val ApprovalReferenceCode: Any,
    val AuthorizationApprovalDescription: Any,
    val AuthorizationDate: String,
    val AuthorizationResponseCode: Any,
    val AuthorizationResponseDescription: Any,
    val AuthorizationTime: String,
    val AuthorizedChargeAmount: String,
    val AuthorizingTerminalID: Any,
    val ElectronicSignature: Any,
    val ForceOnLineFlag: Any,
    val HostAuthorizedFlag: Any,
    val PreAuthorizationFlag: Any,
    val ProviderID: String,
    val ReferenceNumber: Any,
    val RequestedAmount: Any
)