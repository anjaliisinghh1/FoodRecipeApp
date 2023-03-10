package com.project.foodrecipeapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.foodrecipeapp.R
import com.project.foodrecipeapp.common.loadImage
import com.project.foodrecipeapp.databinding.ItemMealListBinding
import com.project.foodrecipeapp.domain.model.AllMealList

class MealListAdapter : RecyclerView.Adapter<MealListAdapter.MealListViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<AllMealList>(){
        override fun areItemsTheSame(oldItem: AllMealList, newItem: AllMealList): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: AllMealList, newItem: AllMealList): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealListViewHolder {
        val itemMealListBinding: ItemMealListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_meal_list,
            parent,
            false
        )
        return MealListViewHolder(itemMealListBinding)
    }

    override fun onBindViewHolder(holder: MealListViewHolder, position: Int) {
        val mealListData = differ.currentList[position]
        holder.itemView.apply {
            loadImage(holder.binding.mealImage, mealListData.strMealThumb)
            holder.binding.tvmealName.text = mealListData.strMeal
            setOnClickListener {
                onItemClickListener?.let {
                    it(mealListData)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class MealListViewHolder(val binding: ItemMealListBinding): RecyclerView.ViewHolder(binding.root)

    private var onItemClickListener: ((AllMealList) -> Unit)? = null

    fun setOnClickListener(listener: ((AllMealList) -> Unit)){
        onItemClickListener = listener
    }
}