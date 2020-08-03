package com.inex.servicemanagement.views.dashboard


import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inex.servicemanagement.R
import com.inex.servicemanagement.data.model.ServiceType
import com.inex.servicemanagement.utils.Utils
import com.inex.servicemanagement.utils.inflate
import kotlinx.android.synthetic.main.list_item_service.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class ServiceDataAdapter(val onClickItem: (item: ServiceType) -> Unit, val onLongPressedItem: (item: ServiceType) -> Unit) :

    RecyclerView.Adapter<ServiceDataAdapter.ItemViewHolder>() {

    var format: NumberFormat = Utils.getCurrencyInstance()

    private var list: ArrayList<ServiceType> = ArrayList()

    fun setDataSet(_list: ArrayList<ServiceType>) {
        list.clear()
        list = _list
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(parent.inflate(R.layout.list_item_service)) {
        fun bind(item: ServiceType) = with(itemView) {
            txt_description.text = item.description
            format.currency = Currency.getInstance(item.currency?: Utils.getCurrencyInstance().currency.toString())
            val amount = format.format(item.price)
            txt_price.text = "$amount"
            img_active.setImageResource(if(item.isActive) R.drawable.ic_active else R.drawable.ic_de_active)

            itemView.setOnLongClickListener {
                onLongPressedItem(list[adapterPosition])
                return@setOnLongClickListener true
            }

            itemView.setOnClickListener {
                onClickItem(list[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(parent)

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        holder.bind(list[position])
}