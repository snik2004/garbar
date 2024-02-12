package com.tinkofflab.garbar.movies.listmovie.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.tinkofflab.garbar.R

class ErrorMessageButton  @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val iconImageView: ImageView
    private val tvTextView: TextView
    private val btnRetry: TextView

    init {
        inflate(context, R.layout.layout_error_message_button, this)
        iconImageView = findViewById(R.id.ic_icon)
        tvTextView = findViewById(R.id.tv_text)
        btnRetry = findViewById(R.id.btn_retry)

        orientation = VERTICAL
        gravity = android.view.Gravity.CENTER_HORIZONTAL

        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ErrorMessageButton)

            val iconResId = typedArray.getResourceId(R.styleable.ErrorMessageButton_embIcon, 0)
            val buttonText = typedArray.getString(R.styleable.ErrorMessageButton_embText)

            if (iconResId != 0) {
                setIcon(iconResId)
            }
            if (!buttonText.isNullOrBlank()) {
                setText(buttonText)
            }

            typedArray.recycle()
        }
    }

    fun setIcon(iconResId: Int) {
        iconImageView.setImageDrawable(ContextCompat.getDrawable(context, iconResId))
    }

    fun setText(text: String) {
        tvTextView.text = text
    }

    fun setRetryListener(callback: () -> Unit ) {
        btnRetry.setOnClickListener {
            callback.invoke()
        }
    }
}