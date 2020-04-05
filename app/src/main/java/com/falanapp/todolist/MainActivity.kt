package com.falanapp.todolist

import android.content.Intent
import android.graphics.drawable.ClipDrawable.VERTICAL
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.falanapp.todolist.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), TaskAdapter.ItemClickListener {

    private lateinit var binding: ActivityMainBinding

    // Constant for logging
    private val TAG = MainActivity::class.java.simpleName

    // Member variables for the adapter and RecyclerView
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mRecyclerView = binding.recyclerViewTasks
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = TaskAdapter(this, this)
        mRecyclerView.adapter = mAdapter

        val decoration =
            DividerItemDecoration(applicationContext, VERTICAL)
        mRecyclerView.addItemDecoration(decoration)

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            // Called when a user swipes left or right on a ViewHolder
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                // Here is where you'll implement swipe to delete
            }
        }).attachToRecyclerView(mRecyclerView)
        val fabButton = binding.fab
        fabButton.setOnClickListener {
            val addTaskIntent = Intent(this@MainActivity, AddTaskActivity::class.java)
            startActivity(addTaskIntent)
        }

    }

    override fun onItemClickListener(itemId: Int) {
        TODO("Not yet implemented")
    }
}
