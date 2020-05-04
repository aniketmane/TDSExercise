package com.example.tdsexercise.data.model


data class Employee(
    val id: String,
    val employee_name: String,
    val employee_salary: String,
    val employee_age: String,
    val profile_image: String
)

data class EmployeeResponse(
    val status: String,
    val data: List<Employee>
)