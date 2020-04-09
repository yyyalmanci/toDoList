package com.falanapp.todolist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.falanapp.todolist.R
import com.falanapp.todolist.database.TaskEntry
import com.falanapp.todolist.databinding.FragmentAddTaskBinding
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class FragmentAddTask : Fragment() {

    private lateinit var binding: FragmentAddTaskBinding

    private val PRIORITY_HIGH = 1
    private val PRIORITY_MEDIUM = 2
    private val PRIORITY_LOW = 3

    private lateinit var mEditText: EditText
    private lateinit var mRadioGroup: RadioGroup
    private lateinit var mButton: Button

    private val viewModel: MainFragmentViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(layoutInflater, container, false)
        viewModel.loadTasksWithId(getArgs())
        initViews()
        viewModel.taskEntry.observe(viewLifecycleOwner, Observer {
            setValues(it[0])
        })
        return binding.root
    }

    private fun getArgs(): Int {
        return FragmentAddTaskArgs.fromBundle(requireArguments()).taskId
    }

    private fun initViews() {
        binding.apply {
            mEditText = editTextTaskDescription
            mRadioGroup = radioGroup
            mButton = saveButton
            mButton.setOnClickListener {
                onButtonClicked()
                findNavController().navigateUp()
            }
        }
    }

    private fun setValues(taskEntry: TaskEntry) {
        mButton.text = getString(R.string.update_button)
        mEditText.setText(taskEntry.getDescription())
        setRadioButtons(taskEntry.getPriority())
    }

    private fun setRadioButtons(priority: Int) {
        when (priority) {
            1 -> binding.radButton1.isChecked = true
            2 -> binding.radButton2.isChecked = true
            3 -> binding.radButton3.isChecked = true
        }
    }

    private fun onButtonClicked() {
        val description = mEditText.text.toString()
        val priority = getPriorityFromViews()
        val date = Date()
        setButtonWork(description, priority, date)
    }

    private fun setButtonWork(description: String, priority: Int, date: Date) {
        if (mButton.text == getString(R.string.update_button)) {
            viewModel.updateTask(TaskEntry(getArgs(), description, priority, date))
        } else {
            viewModel.addTask(TaskEntry(0, description, priority, date))
        }
    }


    private fun getPriorityFromViews(): Int {
        var priority = 1

        when (binding.radioGroup.checkedRadioButtonId) {
            binding.radButton1.id -> priority = PRIORITY_HIGH
            binding.radButton2.id -> priority = PRIORITY_MEDIUM
            binding.radButton3.id -> priority = PRIORITY_LOW
        }
        return priority
    }

}