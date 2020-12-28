package com.guilhermemagro.myhabits.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.guilhermemagro.myhabits.data.Habit
import com.guilhermemagro.myhabits.databinding.ItemHabitBinding
import com.guilhermemagro.myhabits.viewmodels.HabitViewModel

class HabitAdapter(
    lifecycle: LifecycleOwner,
    private val allHabits: LiveData<List<Habit>>,
    private val viewModel: HabitViewModel,
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    init {
        allHabits.observe(lifecycle, Observer {
            notifyDataSetChanged()
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemHabitBinding.inflate(layoutInflater, parent, false)
        return HabitViewHolder(itemBinding, viewModel)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = allHabits.value?.get(position)
            ?: throw IllegalStateException("Null HabitList not expected")
        holder.bind(habit)
    }

    override fun getItemCount(): Int {
        return allHabits.value?.size ?: 0
    }

    class HabitViewHolder(private var binding: ItemHabitBinding, private val viewModel: HabitViewModel)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(habit: Habit) {
            binding.habit = habit
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }
}