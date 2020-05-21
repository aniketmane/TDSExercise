package com.example.tdsexercise.ui.main.viewmodel

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.tdsexercise.BR

class EmployeeDataObservable : BaseObservable() {
    private var lessThan18: Int = 0
    private var between18to60: Int = 0
    private var moreThan60: Int = 0
    private var total: Int = 0
    private var emergency: Boolean = false

    @Bindable
    fun getLessThan18(): String {
        return lessThan18.toString()
    }

    fun setLessThan18(value: Int) {
        if (lessThan18 != value) {
            lessThan18 = value
            notifyPropertyChanged(BR.lessThan18)
        }
    }

    @Bindable
    fun getBetween18to60(): String {
        return between18to60.toString()
    }

    fun setBetween18to60(value: Int) {
        if (between18to60 != value) {
            between18to60 = value
            notifyPropertyChanged(BR.between18to60)
        }
    }

    @Bindable
    fun getMoreThan60(): String {
        return moreThan60.toString()
    }

    fun setMoreThan60(value: Int) {
        if (moreThan60 != value) {
            moreThan60 = value
            notifyPropertyChanged(BR.moreThan60)
        }
    }

    @Bindable
    fun getTotal(): String {
        return total.toString()
    }

    fun setTotal(value: Int) {
        if (total != value) {
            total = value
            notifyPropertyChanged(BR.total)
        }
    }

    @Bindable
    fun getEmergency(): Boolean {
        return emergency
    }

    fun setEmergency(value: Boolean) {
        if (emergency != value) {
            emergency = value
            notifyPropertyChanged(BR.emergency)
        }

    }
    
}
