package com.falanapp.todolist.ui

import android.graphics.drawable.ClipDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.falanapp.todolist.TaskAdapter
import com.falanapp.todolist.database.TaskDb
import com.falanapp.todolist.database.TaskEntry
import com.falanapp.todolist.databinding.FragmentMainBinding
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainFragment : Fragment(), TaskAdapter.ItemClickListener {
    private lateinit var binding: FragmentMainBinding

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: TaskAdapter
    private lateinit var taskDb: TaskDb

    private val taskEntry = MutableLiveData<List<TaskEntry>>()

    private val composite = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        taskDb = TaskDb.getInstance(requireContext())
        initUi()
        taskEntry.observe(viewLifecycleOwner, Observer {
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

            // Called when a user swipes left or right on a ViewHolder
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                deleteTask(viewHolder)
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

    override fun onDestroy() {
        super.onDestroy()
        composite.dispose()
    }

    override fun onResume() {
        super.onResume()
        loadTasks()
    }

    private fun deleteTask(viewHolder: RecyclerView.ViewHolder) {
        composite.add(
            taskDb.taskDao().deleteTask(mAdapter.getTasks()[viewHolder.adapterPosition])
                .subscribeOn(Schedulers.io()).doOnComplete {
                    taskDb.taskDao().loadAllTasks().subscribe({
                        taskEntry.postValue(it)
                    }, {})
                }.subscribe({}, {})
        )
    }

    private fun loadTasks() {
        composite.add(
            taskDb.taskDao().loadAllTasks().subscribeOn(Schedulers.io()).subscribe({
                taskEntry.postValue(it)
            }, {})
        )
    }

    override fun onItemClickListener(itemId: Int) {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToFragmentAddTask(
                itemId
            )
        )
    }

}