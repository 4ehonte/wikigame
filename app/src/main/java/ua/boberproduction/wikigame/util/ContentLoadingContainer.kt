package ua.boberproduction.wikigame.util

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.content_loading_container.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import ua.boberproduction.wikigame.R

/**
 * Custom container encapsulating a recyclerView holding content, a progressbar, an empty view,
 * and an error message which launches user defined behaviour on click.
 */
class ContentLoadingContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) :
    FrameLayout(context, attrs, defStyle, defStyleRes), AnkoLogger {

    var contentView: View? = null
        set(view) {
            field = view
            view?.visibility = View.GONE
        }
    private var retryCallback: () -> Unit = {}

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.content_loading_container, this, true)

        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        layoutParams = params

        error_tv.setOnClickListener {
            showLoading()
            retryCallback.invoke()
            debug(retryCallback)
        }

        // setup progressbar's color
        progressbar.indeterminateDrawable
            .setColorFilter(resources.getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN)
    }

    fun showContentView() {
        progressbar.visibility = View.GONE
        empty_tv.visibility = View.GONE
        error_tv.visibility = View.GONE
        contentView?.visibility = View.VISIBLE
    }

    fun showLoading() {
        progressbar.visibility = View.VISIBLE
        empty_tv.visibility = View.GONE
        error_tv.visibility = View.GONE
        contentView?.visibility = View.GONE
    }

    fun showEmptyMessage() {
        progressbar.visibility = View.GONE
        empty_tv.visibility = View.VISIBLE
        error_tv.visibility = View.GONE
        contentView?.visibility = View.GONE
    }

    fun showError() {
        progressbar.visibility = View.GONE
        empty_tv.visibility = View.GONE
        error_tv.visibility = View.VISIBLE
        contentView?.visibility = View.GONE
    }

    /**
     * Set callback to run when user selected retry option.
     */
    fun doOnRetry(callback: () -> Unit) {
        this.retryCallback = callback
    }


    interface OnRetryClickedCallback {
        fun onRetryClicked()
    }
}
