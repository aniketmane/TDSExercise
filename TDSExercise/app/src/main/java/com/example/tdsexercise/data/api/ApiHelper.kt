package com.example.tdsexercise.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getEmployees() = apiService.getEmployees()
}