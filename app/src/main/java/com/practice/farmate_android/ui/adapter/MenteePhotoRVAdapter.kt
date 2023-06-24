package com.practice.farmate_android.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.practice.farmate_android.R
import com.practice.farmate_android.data.MenteePhotoItem
import com.practice.farmate_android.databinding.ItemMenteePhotoBinding

class MenteePhotoRVAdapter(private val context: Context, private val itemList: List<MenteePhotoItem>)
    : RecyclerView.Adapter<MenteePhotoRVAdapter.MenteePhotoViewHolder>(){
        inner class MenteePhotoViewHolder(private val binding: ItemMenteePhotoBinding)
            : RecyclerView.ViewHolder(binding.root) {
                fun bind(context: Context, current: MenteePhotoItem) {

                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenteePhotoViewHolder {
        val binding = DataBindingUtil.inflate<ItemMenteePhotoBinding>(LayoutInflater.from(parent.context), R.layout.item_mentee_photo, parent, false)
        return MenteePhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenteePhotoViewHolder, position: Int) {
        holder.bind(context, itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}