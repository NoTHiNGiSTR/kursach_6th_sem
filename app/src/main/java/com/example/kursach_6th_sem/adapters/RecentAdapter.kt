package com.example.kursach_6th_sem.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.kursach_6th_sem.databinding.RecentElementBinding

class RecentAdapter(private val onItemClickListener: (String) -> Unit): RecyclerView.Adapter<RecentAdapter.RecentViewHolder>() {

    var recentQuerries: List<Any> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    fun updateData(newData: List<Any>) {
        recentQuerries = newData
    }
    override fun getItemCount(): Int = recentQuerries.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecentElementBinding.inflate(inflater, parent, false)
        return RecentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {

        val querry = recentQuerries[position]
        with(holder.binding){
            name.text=querry.toString()
        }
        holder.itemView.setOnClickListener{
            onItemClickListener(querry.toString())
        }
    }



    class RecentViewHolder(
        val binding: RecentElementBinding
    ) : RecyclerView.ViewHolder(binding.root)
}