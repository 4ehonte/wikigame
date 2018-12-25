package ua.boberproduction.wikigame.util.views

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.animation.doOnEnd
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.TextViewCompat
import ua.boberproduction.wikigame.util.int
import ua.boberproduction.wikigame.util.randomItem
import java.util.*


/**
 * A view that scrolls given phrases in a randomItem manner
 */
class TextScrollerBackgroundView @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int = 0) : LinearLayout(context, attributeSet, defStyleAttr) {

    var minDuration = 10000
    var maxDuration = 20000
    var fontResource = 0
    var minTextHeight = 24
    var maxTextHeight = 200
    var textColorResource = 0
    val random = Random()
    var phrases: List<String> = emptyList()
        set(value) {
            addTextViews(value)
            field = value
        }

    init {
        orientation = LinearLayout.VERTICAL
    }

    private fun addTextViews(phrases: List<String>) {


        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                var heightLeft = height

                while (heightLeft > maxTextHeight) {
                    val textHeight = random.int(minTextHeight, maxTextHeight)
                    val textView = createTextView(context, textHeight, phrases.randomItem(random))
                    setScrollAnimation(textView, width)
                    addView(textView)
                    heightLeft -= textHeight
                }

                if (heightLeft > 0) {
                    val textView = createTextView(context, heightLeft, phrases.randomItem(random))
                    setScrollAnimation(textView, width)
                    addView(textView)
                }
            }
        })
    }

    fun createTextView(context: Context, height: Int, text: String?): TextView {
        val textView = AppCompatTextView(context)
        textView.height = height

        if (textColorResource != 0) {
            textView.setTextColor(context.resources.getColor(textColorResource))
        }

        if (fontResource != 0) {
            val typeface = ResourcesCompat.getFont(context, fontResource)
            textView.typeface = typeface
        }

        TextViewCompat.setAutoSizeTextTypeWithDefaults(textView, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        textView.text = text

        return textView
    }

    private fun setScrollAnimation(textView: TextView, width: Int) {
        textView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                textView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                val startingPercent = random.nextDouble()
                val duration = random.int(minDuration, maxDuration)

                var startingPoint = width - (width + textView.getTextWidth()) * startingPercent

                val animation1 = ObjectAnimator.ofFloat(textView, "x", startingPoint.toFloat(), width.toFloat())
                animation1.duration = (duration * startingPercent).toLong()

                val animation2 = ObjectAnimator.ofFloat(textView, "x", -textView.getTextWidth().toFloat(), startingPoint.toFloat())
                animation2.duration = duration - (duration * startingPercent).toLong()

                animation1.doOnEnd {
                    textView.text = phrases.randomItem(random)
                    startingPoint = width - (width + textView.getTextWidth()) * startingPercent
                    animation2.setFloatValues(-textView.getTextWidth().toFloat(), startingPoint.toFloat())
                    animation1.setFloatValues(startingPoint.toFloat(), width.toFloat())
                }

                val animatorSet = AnimatorSet()
                animatorSet.interpolator = LinearInterpolator()
                animatorSet.play(animation1).before(animation2)
                animatorSet.doOnEnd { animatorSet.start() }
                animatorSet.start()
            }
        })

    }
}

fun TextView.getTextWidth(): Int {
    val bounds = Rect()
    paint.getTextBounds(this.text.toString(), 0, this.text.length, bounds)
    return bounds.width()
}