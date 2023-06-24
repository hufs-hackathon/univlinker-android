package com.practice.farmate_android.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.practice.farmate_android.R
import com.practice.farmate_android.data.FaqItem
import com.practice.farmate_android.databinding.ActivityWriteMentorBinding
import com.practice.farmate_android.databinding.ItemFaqBinding

class FaqRVAdapter(private val context: Context, private val itemList: ArrayList<FaqItem>)
    : RecyclerView.Adapter<FaqRVAdapter.FaqViewHolder>() {
        inner class FaqViewHolder(private val binding: ItemFaqBinding)
            :RecyclerView.ViewHolder(binding.root) {
                fun bind(context: Context, current: FaqItem) {

                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val binding = DataBindingUtil.inflate<ItemFaqBinding>(LayoutInflater.from(parent.context), R.layout.item_faq, parent, false)
        return FaqViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        holder.bind(context, itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}