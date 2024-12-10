package com.example.swaramala

import android.content.Context
import android.util.Log
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
    private val context : Context,
    private val values: List<SwaramModel>
) : RecyclerView.Adapter<SelectedSwaramItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val selectedSwaramFragmentItemBinding : SelectedSwaramFragmentItemBinding = SelectedSwaramFragmentItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(selectedSwaramFragmentItemBinding)

    }

    fun getOnClickListener(holder: ViewHolder, position : Int): View.OnClickListener {
        return View.OnClickListener {
            // Remove the swaram from the list based on position.
            onBindViewHolder(holder,position)
            Log.d("SeletedSwaramAdapter:onClick", "Removing swaram ${position} from selected swarams list ...")
            val selectedSwaramsViewModel = (context as MainActivity).selectedSwaramsViewModel;
            selectedSwaramsViewModel.deleteSwaramInPosition(position);
            Log.d("SeletedSwaramAdapter:onClick", "${selectedSwaramsViewModel.getList()}")
            this.notifyItemRemoved(position)
            this.notifyItemChanged(position, false);
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        //holder.idView.text = item.getId()
        holder.idView.text = "${position + 1}. "
        holder.contentView.text = item.getLabel()
        holder.idView.setOnClickListener(getOnClickListener(holder, position))
        holder.contentView.setOnClickListener(getOnClickListener(holder, position))

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