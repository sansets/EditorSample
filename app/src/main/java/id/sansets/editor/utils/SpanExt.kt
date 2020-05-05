package id.sansets.editor.utils

import android.text.Spannable
import android.text.style.QuoteSpan
import android.text.style.StyleSpan
import android.widget.EditText
import android.widget.Toast

/**
 * Created by Sandi on 05/05/2020.
 */

fun EditText.applyStyleSpan(styleSpan: StyleSpan) {
    val context = this.context
    val selectionStart = this.selectionStart
    val selectionEnd = this.selectionEnd

    if (selectionStart == selectionEnd) {
        Toast.makeText(context, "No text selected", Toast.LENGTH_SHORT).show()
        return
    }

    val spannableString = this.text as Spannable
    val styleSpans =
        spannableString.getSpans(selectionStart, selectionEnd, StyleSpan::class.java)

    styleSpans.forEach {
        if (it.style == styleSpan.style) {
            spannableString.removeSpan(it)
        }
    }

    if (!styleSpans.any { it.style == styleSpan.style }) {
        spannableString.setSpan(
            styleSpan,
            selectionStart,
            selectionEnd,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}

fun EditText.applyQuoteSpan(quoteSpan: QuoteSpan) {
    val spannableString = this.text as Spannable
    val styleSpans =
        spannableString.getSpans(0, text.length, QuoteSpan::class.java)

    styleSpans.forEach {
        if (it.spanTypeId == quoteSpan.spanTypeId) {
            spannableString.removeSpan(it)
        }
    }

    if (!styleSpans.any { it.spanTypeId == quoteSpan.spanTypeId }) {
        spannableString.setSpan(
            quoteSpan,
            0,
            text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}