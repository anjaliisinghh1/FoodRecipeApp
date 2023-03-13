package com.project.foodrecipeapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.foodrecipeapp.R
import com.project.foodrecipeapp.databinding.ItemExploreIngredientsBinding
import com.project.foodrecipeapp.domain.model.ExploreIngredientsDetail

class ExploreIngredientsAdapter: RecyclerView.Adapter<ExploreIngredientsAdapter.ExploreIngredientsViewHolder>(){

    private val differCallback = object : DiffUtil.ItemCallback<ExploreIngredientsDetail>(){
        override fun areItemsTheSame(oldItem: ExploreIngredientsDetail, newItem: ExploreIngredientsDetail): Boolean {
            return oldItem.idIngredient == newItem.idIngredient
        }

        override fun areContentsTheSame(oldItem: ExploreIngredientsDetail, newItem: ExploreIngredientsDetail): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExploreIngredientsViewHolder {
        val itemExploreIngredientsBinding: ItemExploreIngredientsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_explore_ingredients,
            parent,
            false
        )

        return ExploreIngredientsViewHolder(itemExploreIngredientsBinding)
    }

    override fun onBindViewHolder(holder: ExploreIngredientsViewHolder, position: Int) {
        val ingredientListData = differ.currentList[position]
        holder.itemView.apply {
            holder.binding.tvLabelExploreIngredients.text = ingredientListData.strIngredient
            setOnClickListener {
                onItemClickListener?.let {
                    it(ingredientListData)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ExploreIngredientsViewHolder(val binding: ItemExploreIngredientsBinding): RecyclerView.ViewHolder(binding.root)

    private var onItemClickListener: ((ExploreIngredientsDetail) -> Unit)? = null

    fun setOnItemClickListener(listener: (ExploreIngredientsDetail) -> Unit) {
        onItemClickListener = listener
    }
}