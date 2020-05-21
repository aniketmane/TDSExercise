package com.example.tdsexercise.ui.main.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tdsexercise.R
import com.example.tdsexercise.data.api.ApiHelper
import com.example.tdsexercise.data.api.RetrofitBuilder
import com.example.tdsexercise.data.model.Employee
import com.example.tdsexercise.databinding.ActivityMainBinding
import com.example.tdsexercise.ui.base.ViewModelFactory
import com.example.tdsexercise.ui.main.viewmodel.EmployeeDataObservable
import com.example.tdsexercise.ui.main.viewmodel.MainViewModel
import com.example.tdsexercise.util.Status
import rx.Observable
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private var timer: Timer = Timer()
    private lateinit var binding: ActivityMainBinding
    private var dataObservable = EmployeeDataObservable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.employeeDataObservable = dataObservable
        dataObservable.notifyChange()
        initialiseViewModel()
        startTimer()
    }

    private fun initialiseViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

    private fun fetchEmployees() {
        viewModel.getEmployees().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Log.d("Response", it.data.toString());
                        resource.data?.let { it -> retrieveListCounts(it.data) }
                    }
                    Status.ERROR -> {
                        Log.d("Response", it.data.toString());
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        //show progress
                    }
                }
            }
        })
    }

    private fun retrieveListCounts(users: List<Employee>) {
        val lessThan18 = users.filter { it.employee_age.toInt() < 18 }.count()
        val lessBet18And60 =
            users.filter { it.employee_age.toInt() in 19..59 }.count()
        val moreThan60 = users.filter { it.employee_age.toInt() > 60 }.count()
        val total = users.size
        Log.d("Test", "$lessThan18-$lessBet18And60-$moreThan60-$total")
        setCountUI(
            lessThan18 = lessThan18,
            between18to60 = lessBet18And60,
            moreThan60 = moreThan60,
            total = total
        )
    }

    private fun setCountUI(
        lessThan18: Int,
        between18to60: Int,
        moreThan60: Int,
        total: Int
    ) {
        dataObservable.setLessThan18(lessThan18)
        dataObservable.setBetween18to60(between18to60)
        dataObservable.setMoreThan60(moreThan60)
        dataObservable.setTotal(total)
        dataObservable.setEmergency(true)
        dataObservable.notifyChange()
    }

    private fun startTimer() {
        Log.d("Test", "Timer starts")
        Observable.timer(getRandomInterval(), TimeUnit.SECONDS).map {
            it % 2 == 0L
        }.subscribe {
            Log.d("Test", it.toString())
            runOnUiThread {
                if (it) showEmergency()
                else showNoEmergency()
            }
        }
    }

    private fun showNoEmergency() {
        Log.d("Test", "NoEmergency")
        timer.cancel()
    }

    private fun showEmergency() {
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    Log.d("Test", "Fetch Employees")
                    fetchEmployees()
                }
            }
        }, 0L, 5000L)
    }

    private fun getRandomInterval(): Long {
        return (30..90).random().toLong()
    }
}
