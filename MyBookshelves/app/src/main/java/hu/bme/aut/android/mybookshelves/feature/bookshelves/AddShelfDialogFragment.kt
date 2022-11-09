package hu.bme.aut.android.mybookshelves.feature.bookshelves

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDialogFragment
import hu.bme.aut.android.mybookshelves.R
import hu.bme.aut.android.mybookshelves.databinding.DialogNewShelfBinding

class AddShelfDialogFragment : AppCompatDialogFragment() {

    private lateinit var binding: DialogNewShelfBinding
    private lateinit var listener: AddShelfDialogListener

    interface AddShelfDialogListener {
        fun onShelfAdded(shelfName: String?)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        binding = DialogNewShelfBinding.inflate(LayoutInflater.from(context))

        listener = parentFragment as? AddShelfDialogListener
            ?: throw RuntimeException("Fragment must implement the AddShelfDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_shelf)
            .setView(binding.root)
            .setPositiveButton(R.string.ok) { _, _ ->
                listener.onShelfAdded(
                    binding.NewShelfDialogEditText.text.toString()
                )
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }
}