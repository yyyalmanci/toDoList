package com.falanapp.todolist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.falanapp.todolist.R
import com.falanapp.todolist.database.TaskDb
import com.falanapp.todolist.database.TaskEntry
import com.falanapp.todolist.databinding.FragmentAddTaskBinding
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class FragmentAddTask : Fragment() {

    private lateinit var binding: FragmentAddTaskBinding

    private val compositeDisposable = CompositeDisposable()

    private val PRIORITY_HIGH = 1
    private val PRIORITY_MEDIUM = 2
    private val PRIORITY_LOW = 3

    private lateinit var mDb: TaskDb
    private val taskEntry = MutableLiveData<TaskEntry>()

    private lateinit var mEditText: EditText
    private lateinit var mRadioGroup: RadioGroup
    private lateinit var mButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(layoutInflater, container, false)
        mDb = TaskDb.getInstance(requireContext())
        loadTask(getArgs())
        initViews()
        taskEntry.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            setValues(it)
        })
        return binding.root
    }

    private fun loadTask(itemId: Int) {
        compositeDisposable.add(
            mDb.taskDao().loadTask(itemId).subscribeOn(Schedulers.io())
                .subscribe({ taskEntry.postValue(it) }, {})
        )
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
                onSaveButtonClicked()
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

    private fun onSaveButtonClicked() {
        val description = mEditText.text.toString()
        val priority = getPriorityFromViews()
        val date = Date()

        val taskEntry = TaskEntry(0, description, priority, date)
        compositeDisposable.add(
            mDb.taskDao().insertTask(taskEntry).subscribeOn(Schedulers.io()).subscribe({
            }, {})
        )
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