package com.scalalazy.mainservice.model.dto

case class CreateCreditApplicationDTO(
    sum: Long,
    currency: String,
    periodInMonth: Int,
    firstName: String,
    middleName: String,
    lastName: String,
    mobileNumber: String,
    email: String,
    dateOfBirth: String
)
