package com.falanapp.todolist

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.falanapp.todolist.databinding.ActivityAddTaskBinding


class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    // Extra for the task ID to be received in the intent
    val EXTRA_TASK_ID = "extraTaskId"

    // Extra for the task ID to be received after rotation
    val INSTANCE_TASK_ID = "instanceTaskId"

    // Constants for priority
    val PRIORITY_HIGH = 1
    val PRIORITY_MEDIUM = 2
    val PRIORITY_LOW = 3

    // Constant for default task id to be used when not in update mode
    private val DEFAULT_TASK_ID = -1

    // Constant for logging
    private val TAG = AddTaskActivity::class.java.simpleName

    // Fields for views
    private lateinit var mEditText: EditText
    private lateinit var mRadioGroup: RadioGroup
    private lateinit var mButton: Button

    private var mTaskId = DEFAULT_TASK_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID)
        }
        val intent = intent
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            getString(R.string.update_button)
            if (mTaskId == DEFAULT_TASK_ID) {
                // populate the UI
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putInt(INSTANCE_TASK_ID, mTaskId)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    private fun initViews() {
        binding.apply {
            mEditText = editTextTaskDescription
            mRadioGroup = radioGroup
            mButton = saveButton
            mButton.setOnClickListener {
                onSaveButtonClicked()
            }
        }

    }

    private fun onSaveButtonClicked() {
        TODO("Not yet implemented")
    }

    fun getPriorityFromViews(): Int {
        var priority = 1

        when (binding.radioGroup.checkedRadioButtonId) {
            binding.radButton1.id -> priority = PRIORITY_HIGH
            binding.radButton2.id -> priority = PRIORITY_MEDIUM
            binding.radButton3.id -> priority = PRIORITY_LOW
        }
        return priority
    }
}
