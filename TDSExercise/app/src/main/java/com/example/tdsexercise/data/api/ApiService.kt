package com.example.tdsexercise.data.api

import com.example.tdsexercise.data.model.EmployeeResponse
import retrofit2.http.GET

interface ApiService {

    @GET("v1/employees")
    suspend fun getEmployees(): EmployeeResponse

}