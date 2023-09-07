package com.root14.mstock.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.root14.mstock.R
import com.root14.mstock.data.model.ProductModel

class MainRecyclerViewAdapter(private val productItemList: List<ProductModel>) :
    RecyclerView.Adapter<MainRecyclerViewAdapter.ModelViewHolder>() {

    class ModelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val productName: TextView = view.findViewById(R.id.text_view_product_name)
        val ean: TextView = view.findViewById(R.id.text_view_ean)
        val quantity: TextView = view.findViewById(R.id.text_view_quantity)

        fun bindItems(item: ProductModel) {
            productName.text = item.name
            ean.text = item.ean
            quantity.text = item.quantity.toString()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ModelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_screen_list_item_layout, parent, false)
        return ModelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        holder.bindItems(productItemList[position])
    }

    override fun getItemCount(): Int {
        return productItemList.size
    }

}