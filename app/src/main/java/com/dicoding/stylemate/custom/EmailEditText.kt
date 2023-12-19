package com.dicoding.stylemate.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText


class EmailEditText: AppCompatEditText {
    private var isValid = false

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No action needed
            }

            override fun afterTextChanged(s: Editable?) {
                // Perform email validation
                isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()
                if (isValid) {
                    // Valid email format
                    setError(null)
                } else {
                    // Invalid email format
                    setError("Invalid email address")
                }
            }
        })

        fun isValidEmail(): Boolean {
            return isValid
        }
    }

}