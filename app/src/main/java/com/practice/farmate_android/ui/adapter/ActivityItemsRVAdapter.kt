package com.practice.farmate_android.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practice.farmate_android.R
import com.practice.farmate_android.RetrofitClient
import com.practice.farmate_android.SharedPreferencesManager
import com.practice.farmate_android.data.ActivityItem
import com.practice.farmate_android.data.EachActivity
import com.practice.farmate_android.databinding.ItemActivitiesBinding
import com.practice.farmate_android.ui.DetailActivity
import kotlinx.coroutines.NonDisposableHandle.parent
import retrofit2.Callback

class ActivityItemsRVAdapter(private val context: Context, private val itemList: List<EachActivity>, private val userId: Long)
    : RecyclerView.Adapter<ActivityItemsRVAdapter.ActivityItemsViewHolder>() {
        inner class ActivityItemsViewHolder(private val binding: ItemActivitiesBinding)
            : RecyclerView.ViewHolder(binding.root){
                fun bind(context: Context, current: EachActivity) {
                    Glide.with(context)
                        .load(current.imageUrl)
                        .into(binding.ivItem)
                    binding.tvActivityName.text = current.title
                    binding.tvActivityTag.text = "#${current.tag}"

                    itemView.setOnClickListener {
                        val intent = Intent(context, DetailActivity::class.java)
                        intent.putExtra("boardId", current.boardId)
                        context.startActivity(intent)
                    }
                }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityItemsViewHolder {
        val binding = DataBindingUtil.inflate<ItemActivitiesBinding>(LayoutInflater.from(parent.context), R.layout.item_activities, parent, false)
        return ActivityItemsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActivityItemsViewHolder, position: Int) {
        holder.bind(context, itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}