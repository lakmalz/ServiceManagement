package com.inex.servicemanagement.base

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.inex.servicemanagement.R

open class BaseActivity : AppCompatActivity() {

    protected fun showMessage(message: String = getString(R.string.oops_something_went_wrong)) {
        val alert = AlertDialog.Builder(this)
        alert.setMessage(message)
        alert.show()
    }

    protected fun showMessage(message: Int = R.string.oops_something_went_wrong) {
        val alert = AlertDialog.Builder(this)
        alert.setMessage(message)
        alert.show()
    }

}