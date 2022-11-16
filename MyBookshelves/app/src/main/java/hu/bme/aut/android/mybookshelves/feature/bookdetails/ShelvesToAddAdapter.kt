package hu.bme.aut.android.mybookshelves.feature.bookdetails

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.mybookshelves.databinding.ItemShelfInDialogBinding
import hu.bme.aut.android.mybookshelves.model.db.ShelfWithBooks

class ShelvesToAddAdapter(private val listener: OnShelfCheckedListener) :
    RecyclerView.Adapter<ShelvesToAddAdapter.ShelfToAddViewHolder>() {
    private var shelves: MutableList<Pair<ShelfWithBooks, Boolean>> = mutableListOf()

    interface OnShelfCheckedListener {
        fun onShelfSelected(shelf: ShelfWithBooks)
        fun onShelfDeSelected(shelf: ShelfWithBooks)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShelfToAddViewHolder =
        ShelfToAddViewHolder(
            ItemShelfInDialogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ShelfToAddViewHolder, position: Int) {
        val item = shelves[position]
        holder.binding.ShelfItemTitleTextView.text = item.first.shelf.name
        holder.binding.shelfItemCheckbox.isChecked = item.second
        holder.binding.shelfItemCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) listener.onShelfSelected(item.first) else listener.onShelfDeSelected(item.first)
        }
        holder.binding.root.setOnClickListener {
            if (holder.binding.shelfItemCheckbox.isChecked) listener.onShelfSelected(item.first) else listener.onShelfDeSelected(
                item.first
            )
            holder.binding.shelfItemCheckbox.isChecked = !holder.binding.shelfItemCheckbox.isChecked
        }
    }

    override fun getItemCount(): Int = shelves.size

    @SuppressLint("NotifyDataSetChanged")
    fun replaceShelves(newShelves: List<Pair<ShelfWithBooks, Boolean>>) {
        shelves.clear()
        shelves.addAll(newShelves)
        notifyDataSetChanged()
    }

    inner class ShelfToAddViewHolder(val binding: ItemShelfInDialogBinding) :
        RecyclerView.ViewHolder(binding.root)
}