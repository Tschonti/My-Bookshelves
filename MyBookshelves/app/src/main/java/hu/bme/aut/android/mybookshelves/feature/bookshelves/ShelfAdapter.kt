package hu.bme.aut.android.mybookshelves.feature.bookshelves

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.mybookshelves.databinding.ItemShelfBinding
import hu.bme.aut.android.mybookshelves.model.db.ShelfWithBooks
import hu.bme.aut.android.mybookshelves.sqlite.AppDatabase
import kotlin.concurrent.thread

class ShelfAdapter (private val listener: OnShelfSelectedListener) : RecyclerView.Adapter<ShelfAdapter.ShelfViewHolder>() {
    private var shelves: MutableList<ShelfWithBooks> = mutableListOf()

    interface OnShelfSelectedListener {
        fun onShelfSelected(shelf: ShelfWithBooks)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShelfViewHolder = ShelfViewHolder(
        ItemShelfBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ShelfViewHolder, position: Int) {
        val item = shelves[position]
        holder.binding.ShelfItemTitleTextView.text = item.shelf.name
        holder.binding.ShelfItemCountTextView.text = item.books.size.toString()
        holder.binding.root.setOnClickListener {
            listener.onShelfSelected(item)
        }
        holder.binding.deleteBtn.setOnClickListener {
            thread {
                AppDatabase.getInstance(holder.binding.root.context).shelfDao().delete(item.shelf)
                 (holder.binding.root.context as Activity).runOnUiThread {
                     removeShelf(item)
                 }
            }
        }
    }

    override fun getItemCount(): Int = shelves.size

    fun addShelf(newShelf: ShelfWithBooks) {
        shelves.add(newShelf)
        notifyItemInserted(shelves.lastIndex)
    }

    fun removeShelf(shelf: ShelfWithBooks) {
        val idx = shelves.indexOf(shelf)
        shelves.remove(shelf)
        notifyItemRemoved(idx)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun replaceShelves(newShelves: List<ShelfWithBooks>) {
        shelves.clear()
        shelves.addAll(newShelves)
        notifyDataSetChanged()
    }

    inner class ShelfViewHolder(val binding: ItemShelfBinding) : RecyclerView.ViewHolder(binding.root)
}