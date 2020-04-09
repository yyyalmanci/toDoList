package com.falanapp.todolist.ui

import android.graphics.drawable.ClipDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.falanapp.todolist.TaskAdapter
import com.falanapp.todolist.database.TaskEntry
import com.falanapp.todolist.databinding.FragmentMainBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment(), TaskAdapter.ItemClickListener {
    private lateinit var binding: FragmentMainBinding

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: TaskAdapter
    private val viewModel: MainFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        initUi()
        viewModel.loadTasks()

        viewModel.taskEntry.observe(viewLifecycleOwner, Observer {
            mAdapter.setTasks(it)
        })

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val taskEntry = mAdapter.getTasks()[viewHolder.adapterPosition]
                deleteTask(taskEntry)
            }
        }).attachToRecyclerView(mRecyclerView)

        val fabButton = binding.fab
        fabButton.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToFragmentAddTask(
                    -1
                )
            )
        }

        return binding.root
    }

    private fun initUi() {
        mRecyclerView = binding.recyclerViewTasks
        mRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        mAdapter = TaskAdapter(this, requireContext())
        mRecyclerView.adapter = mAdapter
        val decoration =
            DividerItemDecoration(context, ClipDrawable.HORIZONTAL)
        mRecyclerView.addItemDecoration(decoration)
    }

    private fun deleteTask(taskEntry: TaskEntry) {
        viewModel.deleteTask(taskEntry)
    }

    override fun onItemClickListener(itemId: Int) {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToFragmentAddTask(
                itemId
            )
        )
    }

}