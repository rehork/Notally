package com.omgodse.notally.recyclerview.viewholders

import android.view.MotionEvent
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.omgodse.notally.databinding.RecyclerListItemBinding
import com.omgodse.notally.miscellaneous.setOnNextAction
import com.omgodse.notally.recyclerview.ListItemListener
import com.omgodse.notally.room.ListItem

class MakeListVH(val binding: RecyclerListItemBinding, listener: ListItemListener, touchHelper: ItemTouchHelper) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.EditText.setOnNextAction {
            listener.moveToNext(adapterPosition)
        }

        binding.EditText.doAfterTextChanged { text ->
            listener.textChanged(adapterPosition, requireNotNull(text).trim().toString())
        }

        binding.Delete.setOnClickListener {
            listener.delete(adapterPosition)
        }

        binding.CheckBox.setOnCheckedChangeListener { _, isChecked ->
            binding.EditText.isEnabled = !isChecked
            listener.checkedChanged(adapterPosition, isChecked)
        }

        binding.DragHandle.setOnTouchListener { _, event ->
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                touchHelper.startDrag(this)
            }
            false
        }
    }

    fun bind(item: ListItem) {
        binding.root.reset()
        binding.EditText.setText(item.body)
        binding.CheckBox.isChecked = item.checked
    }
}