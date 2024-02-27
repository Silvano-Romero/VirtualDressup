package com.example.virtualdressup2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class CalendarDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_calendar_dialog, container, false)

        val cancelButton: Button = rootView.findViewById(R.id.cancelButton)
        val submitButton: Button = rootView.findViewById(R.id.submitButton)
        val radioGroup: RadioGroup = rootView.findViewById(R.id.radioGroup)

        cancelButton.setOnClickListener {
            dismiss()
        }

        submitButton.setOnClickListener {
            val selectedID = radioGroup.checkedRadioButtonId
            if (selectedID != -1) {
                val radio: RadioButton = rootView.findViewById(selectedID)
                val selectResult = radio.text.toString()
                Toast.makeText(context, "You selected: $selectResult", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Please select an outfit", Toast.LENGTH_SHORT).show()
            }
            dismiss()
        }

        return rootView
    }

}
