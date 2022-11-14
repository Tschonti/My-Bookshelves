package hu.bme.aut.android.mybookshelves.feature.bookdetails

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.mybookshelves.databinding.ItemShelfInDialogBinding
import hu.bme.aut.android.mybookshelves.model.db.Bookshelf

class ShelvesToAddAdapter(private val listener: OnShelfCheckedListener) : RecyclerView.Adapter<ShelvesToAddAdapter.ShelfToAddViewHolder>() {
    private var shelves: MutableList<Bookshelf> = mutableListOf()

    interface OnShelfCheckedListener {
        fun onShelfSelected(shelf: Bookshelf)
        fun onShelfDeSelected(shelf: Bookshelf)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShelfToAddViewHolder = ShelfToAddViewHolder(
        ItemShelfInDialogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ShelfToAddViewHolder, position: Int) {
        val item = shelves[position]
        Log.d("adapter", item.name)
        holder.binding.ShelfItemTitleTextView.text = item.name
        holder.binding.shelfItemCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) listener.onShelfSelected(item) else listener.onShelfDeSelected(item)
        }
    }

    override fun getItemCount(): Int = shelves.size

    @SuppressLint("NotifyDataSetChanged")
    fun replaceShelves(newShelves: List<Bookshelf>) {
        shelves.clear()
        shelves.addAll(newShelves)
        notifyDataSetChanged()
    }

    inner class ShelfToAddViewHolder(val binding: ItemShelfInDialogBinding) : RecyclerView.ViewHolder(binding.root)
}