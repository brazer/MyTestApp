package com.salanevich.testapp.ui.view

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.salanevich.testapp.R
import com.salanevich.testapp.utils.dateFormatter
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class DateTextWatcher(private val editText: EditText): TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        //do nothing
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val context = editText.context
        if (s != null && s.length > 10) {
            editText.error = context.getString(R.string.too_long)
        } else if (s != null && s.length == 10) {
            val regex = "\\d{4}/\\d{2}/\\d{2}".toRegex()
            if (!s.matches(regex)) {
                editText.error = context.getString(R.string.incorrect_date)
            } else {
                dateFormatter.isLenient = false
                try {
                    dateFormatter.parse(s.toString())
                } catch (e: Exception) {
                    Timber.w(e)
                    editText.error = context.getString(R.string.incorrect_date)
                }
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {
        //do nothing
    }
}