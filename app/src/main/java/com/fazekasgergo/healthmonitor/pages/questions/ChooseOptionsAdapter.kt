package com.fazekasgergo.healthmonitor.pages.questions

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fazekasgergo.healthmonitor.databinding.ChooseOptionItemViewBinding

class ChooseOptionsAdapter : RecyclerView.Adapter<ChooseOptionsAdapter.ViewHolder>() {

    interface OnItemButtonClickListener {
        fun onItemButtonClick(position: Int)
    }

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

    private var onItemButtonClickListener: OnItemButtonClickListener? = null

    private var previousSelection = -1
    private var currentSelection = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = chooseOptions[position]
        val icon = chooseOptionIcons[position]
        val isChecked = position == currentSelection
        holder.bind(item, icon, isChecked, position)
    }

    override fun getItemCount(): Int {
        return chooseOptions.size
    }

    class ViewHolder private constructor(
        val binding: ChooseOptionItemViewBinding,
        val adapter: ChooseOptionsAdapter
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChooseOption, iconResourceId: Int, isChecked: Boolean, position: Int) {
            binding.chooseOptionButton.text = item.text()
            binding.chooseOptionButton.textOff = item.text()
            binding.chooseOptionButton.textOn = item.text()
            binding.chooseOptionButton.isChecked = isChecked
            binding.chooseOptionButton.isClickable = !isChecked

            binding.chooseOptionButton.setOnCheckedChangeListener { _, newIsChecked ->
                if (newIsChecked) adapter.setSelection(position)
                if (adapter.onItemButtonClickListener != null) adapter.onItemButtonClickListener?.onItemButtonClick(
                    position
                )
            }
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

    fun setOnItemButtonClickListener(listener: OnItemButtonClickListener) {
        this.onItemButtonClickListener = listener
    }

}
