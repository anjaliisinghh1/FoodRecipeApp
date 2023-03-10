package com.project.foodrecipeapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.foodrecipeapp.R
import com.project.foodrecipeapp.common.loadImage
import com.project.foodrecipeapp.databinding.ItemCategoriesBinding
import com.project.foodrecipeapp.domain.model.CategoryList

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private var categoryList: List<CategoryList> = ArrayList()

    fun setData(categoryListData: List<CategoryList>){
        this.categoryList = categoryListData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemCategoriesBinding: ItemCategoriesBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_categories,
            parent,
            false
        )

        return CategoryViewHolder(itemCategoriesBinding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val categories = categoryList[position]
        holder.itemView.apply {
            loadImage(holder.binding.categoryImage,categories.strCategoryThumb)
            holder.binding.tvCategoryName.text = categories.strCategory
            setOnClickListener {
                onItemClickListener?.let {
                    it(categories)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    inner class CategoryViewHolder(val binding: ItemCategoriesBinding): RecyclerView.ViewHolder(binding.root)

    private var onItemClickListener: ((CategoryList) -> Unit)? = null

    fun setOnItemClickListener(listener: (CategoryList) -> Unit) {
        onItemClickListener = listener
    }

}