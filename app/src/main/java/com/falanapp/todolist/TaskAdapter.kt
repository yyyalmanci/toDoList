package com.falanapp.todolist

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.falanapp.todolist.database.TaskEntry
import com.falanapp.todolist.databinding.TaskLayoutBinding
import java.text.SimpleDateFormat
import java.util.*


class TaskAdapter(private val listener: ItemClickListener, private val context: Context) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val DATE_FORMAT = "dd/MM/yyy"
    private lateinit var mTaskEntries: List<TaskEntry>
    private val dateFormat: SimpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

    interface ItemClickListener {
        fun onItemClickListener(itemId: Int)
    }

    class TaskViewHolder(private val binding: TaskLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val taskDescriptionView = binding.taskDescription
        val updatedAtView = binding.taskUpdatedAt
        val priorityView = binding.priorityTextView

        fun bind(itemId: Int, listener: ItemClickListener) {
            binding.root.setOnClickListener {
                listener.onItemClickListener(itemId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)

    }

    override fun getItemCount(): Int {
        if (::mTaskEntries.isInitialized) {
            return mTaskEntries.size
        }
        return 0
    }

    fun getTasks(): List<TaskEntry> {
        return mTaskEntries
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val taskEntry = mTaskEntries[position]
        val description = taskEntry.getDescription()
        val priority = taskEntry.getPriority()
        val updateDate = dateFormat.format(taskEntry.getUpdatedAt())
        holder.bind(taskEntry.getId(), listener)

        holder.taskDescriptionView.text = description
        holder.updatedAtView.text = updateDate
        holder.priorityView.text = priority.toString()

        val priorityCircle =
            holder.priorityView.background as GradientDrawable
        val priorityColor = getPriorityColor(priority)
        priorityCircle.setColor(priorityColor)
    }

    fun setTasks(taskEntries: List<TaskEntry>) {
        mTaskEntries = taskEntries
        notifyDataSetChanged()
    }

    private fun getPriorityColor(priority: Int): Int {
        var priorityColor = 0
        when (priority) {
            1 -> priorityColor = ContextCompat.getColor(context, R.color.materialRed)
            2 -> priorityColor = ContextCompat.getColor(context, R.color.materialOrange)
            3 -> priorityColor = ContextCompat.getColor(context, R.color.materialYellow)
            else -> {
            }
        }
        return priorityColor
    }
}