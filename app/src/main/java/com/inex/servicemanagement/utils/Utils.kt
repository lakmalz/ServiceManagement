package com.inex.servicemanagement.utils

import android.content.Context
import android.content.DialogInterface
import android.view.Window
import androidx.appcompat.app.AlertDialog
import com.inex.servicemanagement.R
import java.text.NumberFormat
import java.util.*

class Utils {
    companion object {

        fun getCurrencySymbol(): String = Currency.getInstance(getLocaleCurrencyCode()).symbol

        fun getCurrencyInstance(): NumberFormat = NumberFormat.getCurrencyInstance()

        fun showMessage(
            context: Context?,
            msg: String?,
            onClickListener: DialogInterface.OnClickListener?
        ) {
            val dialog =
                AlertDialog.Builder(context!!)
            dialog.setMessage(msg)
                .setPositiveButton(context.getString(R.string.ok), onClickListener)
            val alert = dialog.create()
            alert.setCancelable(false)
            alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alert.show()
        }

        open fun showMessageWithTwoButtons(
            context: Context?,
            msg: Int,
            positiveText: Int,
            negativeText: Int,
            onYesClickListener: DialogInterface.OnClickListener?,
            onNoClickListener: DialogInterface.OnClickListener?
        ) {
            val dialog =
                androidx.appcompat.app.AlertDialog.Builder(context!!)
            dialog.setMessage(msg)
                .setPositiveButton(positiveText, onYesClickListener)
                .setNegativeButton(negativeText, onNoClickListener)
            val alert = dialog.create()
            alert.setCancelable(true)
            alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alert.show()
        }

        fun getLocaleCurrencyCode(): String = Currency.getInstance(Locale.getDefault()).currencyCode
    }


}