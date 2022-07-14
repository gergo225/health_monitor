package com.fazekasgergo.healthmonitor.pages.questions

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fazekasgergo.healthmonitor.R
import com.fazekasgergo.healthmonitor.databinding.ChooseOptionItemViewBinding

class ChooseOptionsAdapter : RecyclerView.Adapter<ChooseOptionsAdapter.ViewHolder>() {

    var chooseOptions = listOf<ChooseOption>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var chooseOptionIcons = listOf<Int>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var context: Context? = null

    private var previousSelection = -1
    private var currentSelection = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = chooseOptions[position]
        val icon = chooseOptionIcons[position]
        val isChecked = position == currentSelection
        holder.bind(item, icon, isChecked, position, context!!)
    }

    override fun getItemCount(): Int {
        return chooseOptions.size
    }

    class ViewHolder private constructor(
        val binding: ChooseOptionItemViewBinding,
        val adapter: ChooseOptionsAdapter
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: ChooseOption, iconResourceId: Int, isChecked: Boolean,
            position: Int, context: Context
        ) {
            binding.chooseOptionButton.text = item.text()

            val backgroundResource =
                if (isChecked) R.drawable.choose_option_button_selected else R.drawable.choose_option_button_not_selected
            val textColor =
                if (isChecked) ContextCompat.getColor(context, R.color.white)
                else ContextCompat.getColor(context, R.color.dark_blue)
            binding.chooseOptionButton.setBackgroundResource(backgroundResource)
            binding.chooseOptionButton.setTextColor(textColor)

            binding.chooseOptionButton.setOnClickListener { adapter.setSelection(position) }
            binding.chooseOptionIcon.setImageResource(iconResourceId)
        }

        companion object {
            fun from(parent: ViewGroup, adapter: ChooseOptionsAdapter): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ChooseOptionItemViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, adapter)
            }
        }
    }

    fun setSelection(position: Int) {
        if (position != currentSelection) {
            currentSelection = position

            if (previousSelection >= 0)
                notifyItemChanged(previousSelection)

            notifyItemChanged(currentSelection)
            previousSelection = position
        }
    }

}
