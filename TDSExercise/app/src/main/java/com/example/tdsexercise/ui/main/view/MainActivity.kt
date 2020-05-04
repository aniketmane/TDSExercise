package com.example.tdsexercise.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tdsexercise.R
import com.example.tdsexercise.data.api.ApiHelper
import com.example.tdsexercise.data.api.RetrofitBuilder
import com.example.tdsexercise.data.model.Employee
import com.example.tdsexercise.ui.base.ViewModelFactory
import com.example.tdsexercise.ui.main.viewmodel.MainViewModel
import com.example.tdsexercise.util.Status
import kotlinx.android.synthetic.main.activity_main.*
import rx.Observable
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private var timer: Timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
            lessThan18 = lessThan18.toString(),
            between18to60 = lessBet18And60.toString(),
            moreThan60 = moreThan60.toString(),
            total = total.toString()
        )
    }

    private fun setCountUI(
        lessThan18: String,
        between18to60: String,
        moreThan60: String,
        total: String
    ) {
        tvTotalCount.text = total
        tvLessThanEighteen.text = lessThan18
        tvBetweenEighteenToSixty.text = between18to60
        tvMoreThanSixty.text = moreThan60
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
        updateUI(false)
    }

    private fun showEmergency() {
        updateUI(true)
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    Log.d("Test", "Fetch Employees")
                    fetchEmployees()
                }
            }
        }, 0L, 5000L)
    }

    private fun updateUI(isEmergency: Boolean) {
        if (isEmergency) {
            tvTitle.text = getString(R.string.emergency)
            tvTitle.setTextColor(getColor(R.color.red))
            tvTotalCount.visibility = View.VISIBLE
            tvTotal.visibility = View.VISIBLE
            clLayoutBottom.visibility = View.VISIBLE
        } else {
            tvTitle.text = getString(R.string.no_emergency)
            tvTitle.setTextColor(getColor(R.color.green))
            tvTotalCount.visibility = View.GONE
            tvTotal.visibility = View.GONE
            clLayoutBottom.visibility = View.GONE
        }
    }

    private fun getRandomInterval(): Long {
        return (30..90).random().toLong()
    }
}
