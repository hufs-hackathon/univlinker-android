package com.practice.farmate_android.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practice.farmate_android.R
import com.practice.farmate_android.data.MentorItem
import com.practice.farmate_android.databinding.ItemMentorBinding

class MentorItemRVAdapter(private val context: Context, private val itemList: List<MentorItem>)
    : RecyclerView.Adapter<MentorItemRVAdapter.MentorItemViewHolder>() {
        inner class MentorItemViewHolder(private val binding: ItemMentorBinding)
            : RecyclerView.ViewHolder(binding.root) {
                fun bind(context: Context, current: MentorItem) {
                    Glide.with(context)
                        .load(current.profile)
                        .into(binding.ivProfile)
                    binding.tvTagMentor.text = current.mentor
                    binding.tvTitle.text = current.title
                    binding.tvName.text = current.name
                    binding.tvDepartment.text = current.department
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MentorItemViewHolder {
        val binding = DataBindingUtil.inflate<ItemMentorBinding>(LayoutInflater.from(parent.context), R.layout.item_mentor, parent, false)
        return MentorItemViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MentorItemViewHolder, position: Int) {
        holder.bind(context, itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}