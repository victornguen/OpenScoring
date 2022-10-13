package com.scalalazy.mainservice.model.database

import java.util.Date

case class Person(
    id: String,
    firstName: String,
    middleName: String,
    lastName: String,
    mobileNumber: String,
    email: String,
    dateOfBirth: Date
)
