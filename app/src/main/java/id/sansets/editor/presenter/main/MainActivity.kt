package id.sansets.editor.presenter.main

import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.toHtml
import androidx.core.view.children
import androidx.core.widget.doAfterTextChanged
import id.sansets.editor.R
import id.sansets.editor.databinding.ActivityMainBinding
import org.wordpress.aztec.AztecTextFormat

class MainActivity : AppCompatActivity(), MainActionListener, ActionMode.Callback {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    override fun onApplyBoldStyleSpan() {
        binding.etEditor.toggleFormatting(AztecTextFormat.FORMAT_BOLD)

        // Update HTML result in live preview
        onShowTextPreview()
    }

    override fun onApplyItalicStyleSpan() {
        binding.etEditor.toggleFormatting(AztecTextFormat.FORMAT_ITALIC)

        // Update HTML result in live preview
        onShowTextPreview()
    }

    override fun onApplyQuoteSpan() {
        binding.etEditor.toggleFormatting(AztecTextFormat.FORMAT_QUOTE)

        // Update HTML result in live preview
        onShowTextPreview()
    }

    override fun onApplyOrderedListSpan() {
        binding.etEditor.toggleFormatting(AztecTextFormat.FORMAT_ORDERED_LIST)

        // Update HTML result in live preview
        onShowTextPreview()
    }

    override fun onApplyUnorderedListSpan() {
        binding.etEditor.toggleFormatting(AztecTextFormat.FORMAT_UNORDERED_LIST)

        // Update HTML result in live preview
        onShowTextPreview()
    }

    /**
     * Live preview for HTML editor values
     */
    override fun onShowTextPreview() {
        binding.tvPreview.text = binding.etEditor.text.toHtml()
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_format_bold -> {
                onApplyBoldStyleSpan()
                true
            }
            R.id.action_format_italic -> {
                onApplyItalicStyleSpan()
                true
            }
            else -> false
        }
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.menu_actions, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        // Reorder menu item. Make custom action mode menu first
        menu?.children?.filterNot { it.groupId == R.id.group_format }?.forEach {
            menu.removeItem(it.itemId)
            menu.add(it.groupId, it.itemId, it.order, it.title)
        }

        return false
    }

    override fun onDestroyActionMode(mode: ActionMode?) {

    }

    private fun initView() {
        // Set view on click listener
        binding.btnBold.setOnClickListener { onApplyBoldStyleSpan() }
        binding.btnItalic.setOnClickListener { onApplyItalicStyleSpan() }
        binding.btnQuote.setOnClickListener { onApplyQuoteSpan() }
        binding.btnOrderedList.setOnClickListener { onApplyOrderedListSpan() }
        binding.btnUnorderedList.setOnClickListener { onApplyUnorderedListSpan() }

        binding.etEditor.apply {
            setCalypsoMode(false)
            doAfterTextChanged { onShowTextPreview() }
            customSelectionActionModeCallback = this@MainActivity
        }
    }
}
