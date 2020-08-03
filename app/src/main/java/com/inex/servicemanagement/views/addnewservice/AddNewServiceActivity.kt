package com.inex.servicemanagement.views.addnewservice


import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.inex.servicemanagement.R
import com.inex.servicemanagement.base.BaseActivity
import com.inex.servicemanagement.data.model.ServiceType
import com.inex.servicemanagement.utils.Constant
import com.inex.servicemanagement.utils.Utils
import kotlinx.android.synthetic.main.activity_add_new_service.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddNewServiceActivity : BaseActivity() {

    private var selectedServiceItem: ServiceType? = null
    private val viewModel by viewModel<AddNewServiceViewModel>()

    companion object {
        fun getIntent(context: Context, dataService: ServiceType? = null): Intent {
            val intent = Intent(context, AddNewServiceActivity::class.java)
            intent.putExtra(Constant.EXTRAS_SERVICE_ITEM, dataService)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_service)
        getExtras()
        liveObserveData()
        initUI()
    }

    private fun liveObserveData() {
        viewModel.progressStatus.observe(this, androidx.lifecycle.Observer {
            progress_bar.visibility = it
        })

        viewModel.serviceTypeResponse.observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                Utils.showMessage(
                    this,
                    getString(R.string.service_added_successful),
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.dismiss()
                        setResult(Activity.RESULT_OK)
                        onBackPressed()
                    })
            } else {
                showMessage(getString(R.string.fail_msg_item_insert))
            }
        })
    }

    private fun initUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.title_add_new_service)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white)
        txt_symbol.text = Utils.getCurrencySymbol()
    }

    private fun getExtras() {
        if (intent.hasExtra(Constant.EXTRAS_SERVICE_ITEM)) {
            selectedServiceItem =
                intent.getParcelableExtra<ServiceType>(Constant.EXTRAS_SERVICE_ITEM)

            edt_description.setText(selectedServiceItem?.description ?: "")

            if (selectedServiceItem?.price == null) {
                edt_price.setText("")
            } else {
                edt_price.setText(selectedServiceItem?.price.toString())
            }

            switch_active.isChecked = selectedServiceItem?.isActive ?: false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_done -> {
                if (validation()) {
                    val request = ServiceType(
                        selectedServiceItem?.id ?: null,
                        switch_active.isChecked,
                        edt_description.text.toString().trim(),
                        edt_price.text.toString().trim().toDouble(),
                        selectedServiceItem?.currency ?: Utils.getCurrencyInstance().currency.toString()
                    )

                    viewModel.saveOrUpdateService(request)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    /**
     * Input fields validate
     */
    private fun validation(): Boolean {
        return when {
            edt_description.text.isEmpty() -> {
                showMessage(getString(R.string.description_is_required))
                false
            }
            edt_price.text.isEmpty() -> {
                showMessage(getString(R.string.price_is_required))
                false
            }
            else -> true
        }
    }
}
