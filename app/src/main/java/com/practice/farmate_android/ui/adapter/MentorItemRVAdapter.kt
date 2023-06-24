package com.practice.farmate_android.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practice.farmate_android.R
import com.practice.farmate_android.data.MentorAllItem
import com.practice.farmate_android.data.MentorItem
import com.practice.farmate_android.data.MentorServices
import com.practice.farmate_android.databinding.ItemMentorBinding

class MentorItemRVAdapter(private val context: Context, private val itemList: List<MentorAllItem>?)
    : RecyclerView.Adapter<MentorItemRVAdapter.MentorItemViewHolder>() {
        inner class MentorItemViewHolder(private val binding: ItemMentorBinding)
            : RecyclerView.ViewHolder(binding.root) {
                fun bind(context: Context, current: MentorAllItem) {
                    binding.tvTagMentor.text = current.nickname
                    binding.tvTitle.text = current.title
                    binding.tvName.text = current.title
                    binding.tvDepartment.text = current.major
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MentorItemViewHolder {
        val binding = DataBindingUtil.inflate<ItemMentorBinding>(LayoutInflater.from(parent.context), R.layout.item_mentor, parent, false)
        return MentorItemViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MentorItemViewHolder, position: Int) {
        holder.bind(context, itemList!![position])
    }

    override fun getItemCount(): Int {
        return itemList!!.size
    }

}