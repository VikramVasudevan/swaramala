package com.example.swaramala

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.swaramala.databinding.SelectedSwaramFragmentItemBinding

/**
 * [RecyclerView.Adapter] that can display a [SwaramModel].
 * TODO: Replace the implementation with code for your data type.
 */
class SelectedSwaramItemRecyclerViewAdapter(
    private val values: List<SwaramModel>
) : RecyclerView.Adapter<SelectedSwaramItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            SelectedSwaramFragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        //holder.idView.text = item.getId()
        holder.idView.text = "${position + 1}. "
        holder.contentView.text = item.getLabel()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: SelectedSwaramFragmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}