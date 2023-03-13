package com.project.foodrecipeapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.foodrecipeapp.R
import com.project.foodrecipeapp.databinding.ItemExploreCategoriesBinding
import com.project.foodrecipeapp.domain.model.ExploreCategoriesDetail

class ExploreCategoriesAdapter: RecyclerView.Adapter<ExploreCategoriesAdapter.ExploreCategoriesViewHolder>(){

    private val differCallback = object : DiffUtil.ItemCallback<ExploreCategoriesDetail>(){
        override fun areItemsTheSame(oldItem: ExploreCategoriesDetail, newItem: ExploreCategoriesDetail): Boolean {
            return oldItem.strCategory == newItem.strCategory
        }

        override fun areContentsTheSame(oldItem: ExploreCategoriesDetail, newItem: ExploreCategoriesDetail): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreCategoriesViewHolder {
        val itemExploreCategoriesBinding: ItemExploreCategoriesBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_explore_categories,
            parent,
            false
        )
        return ExploreCategoriesViewHolder(itemExploreCategoriesBinding)
    }

    override fun onBindViewHolder(holder: ExploreCategoriesViewHolder, position: Int) {
        val categoriesData = differ.currentList[position]
        holder.itemView.apply {
            holder.binding.tvLabelExploreCategories.text = categoriesData.strCategory
            setOnClickListener {
                onItemClickListener?.let {
                    it(categoriesData)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ExploreCategoriesViewHolder(val binding: ItemExploreCategoriesBinding): RecyclerView.ViewHolder(binding.root)

    private var onItemClickListener: ((ExploreCategoriesDetail) -> Unit)? = null

    fun setOnItemClickListener(listener: (ExploreCategoriesDetail) -> Unit) {
        onItemClickListener = listener
    }
}