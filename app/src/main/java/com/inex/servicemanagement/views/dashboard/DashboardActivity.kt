package com.inex.servicemanagement.views.dashboard


import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.inex.servicemanagement.R
import com.inex.servicemanagement.base.BaseActivity
import com.inex.servicemanagement.data.model.ServiceType
import com.inex.servicemanagement.utils.Constant
import com.inex.servicemanagement.utils.Utils
import com.inex.servicemanagement.views.addnewservice.AddNewServiceActivity
import kotlinx.android.synthetic.main.activity_dashboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardActivity : BaseActivity() {

    private lateinit var transactionAdapter: ServiceDataAdapter
    private val viewModel by viewModel<DashboardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        initUI()
    }

    private fun initAdapter() {
        transactionAdapter =
            ServiceDataAdapter(::onClickItem, ::onLongPressedItem)
        rv_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionAdapter
        }
    }

    private fun initUI() {
        initActionBar()
        initAdapter()
        initObservers()
        loadData()
    }

    private fun initObservers() {
        viewModel.progressStatus.observe(this, androidx.lifecycle.Observer {
            progress_bar.visibility = it
        })

        viewModel.dataServicesResponse.observe(this, Observer { response ->
            txt_no_items.visibility = View.GONE
            if (response.isNullOrEmpty()) {
                transactionAdapter.clear()
                txt_no_items.visibility = View.VISIBLE
            } else {
                transactionAdapter.setDataSet(response)
            }
        })
        viewModel.deleteResponse.observe(this, Observer { response ->
            if (response != null) {
                showMessage(R.string.item_successfully_deleted)
                loadData()
            } else {
                showMessage(R.string.item_deleted_unsuccessful)
            }
        })
    }

    private fun initActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.title_dashboard)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                openViewUpdateOrSave(null)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.dashboard_menu, menu)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Constant.ADD_NEW_VIEW -> {
                    loadData()
                }
                else -> {
                }
            }
        }
    }

    private fun openViewUpdateOrSave(item: ServiceType?) {
        startActivityForResult(AddNewServiceActivity.getIntent(this, item), Constant.ADD_NEW_VIEW)
    }

    private fun loadData() {
        viewModel.getDataServiceList()
    }

    private fun onClickItem(item: ServiceType) {
        openViewUpdateOrSave(item)
    }

    private fun onLongPressedItem(item: ServiceType) {
        Utils.showMessageWithTwoButtons(this,
            R.string.are_you_sure_you_want_to_delete_item,
            R.string.yes,
            R.string.no,
            DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
                item.id?.let { viewModel.deleteService(it) }
            },
            DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
            })
    }
}