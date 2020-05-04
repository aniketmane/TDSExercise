package com.example.tdsexercise.data.repository

import com.example.tdsexercise.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getEmployees() = apiHelper.getEmployees()
}