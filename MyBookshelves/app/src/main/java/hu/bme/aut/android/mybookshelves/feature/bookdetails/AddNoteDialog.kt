package hu.bme.aut.android.mybookshelves.feature.bookdetails

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDialogFragment
import hu.bme.aut.android.mybookshelves.R
import hu.bme.aut.android.mybookshelves.databinding.DialogAddNoteBinding

class AddNoteDialog : AppCompatDialogFragment() {

    private lateinit var binding: DialogAddNoteBinding
    private lateinit var listener: AddNoteDialogListener

    companion object {
        private const val PARAM_NOTE = "PARAM_NOTE"

        fun newInstance(note: String?): AddNoteDialog {
            val inst = AddNoteDialog()
            val args = Bundle()
            args.putString(PARAM_NOTE, note)
            inst.arguments = args
            return inst
        }
    }

    interface AddNoteDialogListener {
        fun onNoteAdded(note: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        binding = DialogAddNoteBinding.inflate(LayoutInflater.from(context))

        listener = parentFragment as? AddNoteDialogListener
            ?: throw RuntimeException("Fragment must implement the AddNoteDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        arguments.let {
            binding.AddNoteEditText.setText(it?.getString(PARAM_NOTE))
        }
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.add_note)
            .setView(binding.root)
            .setPositiveButton(R.string.ok) { _, _ ->
                listener.onNoteAdded(
                    binding.AddNoteEditText.text.toString()
                )
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }
}