package com.project.foodrecipeapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.foodrecipeapp.R
import com.project.foodrecipeapp.common.loadImage
import com.project.foodrecipeapp.databinding.TrendingFoodRecipesBinding
import com.project.foodrecipeapp.domain.model.AllMealList

class TrendingRecipesAdapter: RecyclerView.Adapter<TrendingRecipesAdapter.RecipeViewHolder>() {

    private var mealsList: List<AllMealList> = ArrayList()

    fun setData(mealListData: List<AllMealList>){
        this.mealsList = mealListData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val trendingFoodRecipesBinding: TrendingFoodRecipesBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.trending_food_recipes,
            parent,
            false
        )

        return RecipeViewHolder(trendingFoodRecipesBinding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val mealListData = mealsList[position]
        holder.itemView.apply {
            loadImage(holder.binding.trendingRecipesImg,mealListData.strMealThumb)
            setOnClickListener {
                onItemClickListener?.let {
                    it(mealListData)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    inner class RecipeViewHolder(val binding: TrendingFoodRecipesBinding): RecyclerView.ViewHolder(binding.root)

    private var onItemClickListener: ((AllMealList) -> Unit)? = null

    fun setOnItemClickListener(listener: (AllMealList) -> Unit) {
        onItemClickListener = listener
    }
}