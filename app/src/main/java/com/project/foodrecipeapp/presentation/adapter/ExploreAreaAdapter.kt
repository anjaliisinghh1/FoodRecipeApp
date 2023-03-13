package com.project.foodrecipeapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.foodrecipeapp.R
import com.project.foodrecipeapp.databinding.ItemExploreAreaBinding
import com.project.foodrecipeapp.domain.model.ExploreAreaDetails

class ExploreAreaAdapter: RecyclerView.Adapter<ExploreAreaAdapter.ExploreAreaViewHolder>(){

    private val differCallback = object : DiffUtil.ItemCallback<ExploreAreaDetails>(){
        override fun areItemsTheSame(oldItem: ExploreAreaDetails, newItem: ExploreAreaDetails): Boolean {
            return oldItem.strArea == newItem.strArea
        }

        override fun areContentsTheSame(oldItem: ExploreAreaDetails, newItem: ExploreAreaDetails): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreAreaViewHolder {
        val itemExploreAreaBinding: ItemExploreAreaBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_explore_area,
            parent,
            false
        )

        return ExploreAreaViewHolder(itemExploreAreaBinding)
    }

    override fun onBindViewHolder(holder: ExploreAreaViewHolder, position: Int) {
        val areaListData = differ.currentList[position]
        holder.itemView.apply {
            holder.binding.tvLabelExploreArea.text = areaListData.strArea
            setOnClickListener {
                onItemClickListener?.let {
                    it(areaListData)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ExploreAreaViewHolder(val binding: ItemExploreAreaBinding): RecyclerView.ViewHolder(binding.root)

    private var onItemClickListener: ((ExploreAreaDetails) -> Unit)? = null

    fun setOnItemClickListener(listener: (ExploreAreaDetails) -> Unit) {
        onItemClickListener = listener
    }

}