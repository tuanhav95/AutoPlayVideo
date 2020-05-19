package com.tuanhav95.auto_play_video.example.utils.extension

import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.LayoutRes
import com.bumptech.glide.load.Transformation
import com.bee.utils.utils.image.ImageUtils

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.isGone() = visibility == View.GONE

fun View.isVisible() = visibility == View.VISIBLE

fun View.isInVisible() = visibility == View.INVISIBLE

fun View.setMargins(
    leftMarginDp: Int? = null,
    topMarginDp: Int? = null,
    rightMarginDp: Int? = null,
    bottomMarginDp: Int? = null
) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val params = layoutParams as ViewGroup.MarginLayoutParams
        leftMarginDp?.run { params.leftMargin = this.toPx() }
        topMarginDp?.run { params.topMargin = this.toPx() }
        rightMarginDp?.run { params.rightMargin = this.toPx() }
        bottomMarginDp?.run { params.bottomMargin = this.toPx() }
        requestLayout()
    }
}

fun RelativeLayout.add(view: View) {
    val layoutParams = RelativeLayout.LayoutParams(view.width, view.height)
    layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
    add(view, layoutParams)
}

fun ViewGroup.add(view: View, layoutParams: ViewGroup.LayoutParams) {
    view.parent?.let {
        (it as ViewGroup).removeView(view)
    }
    addView(view, layoutParams)
}

fun View.alphaAnimation(
    toAlpha: Float,
    duration: Long = 350
) {
    alphaAnimation(toAlpha, duration, 0) {}
}

fun View.alphaAnimation(
    toAlpha: Float,
    startDelay: Long,
    duration: Long = 350
) {
    alphaAnimation(toAlpha, duration, startDelay) {}
}

fun View.alphaAnimation(
    toAlpha: Float,
    duration: Long,
    onEnd: () -> Unit
) {
    alphaAnimation(toAlpha, duration, 0, onEnd)
}

fun View.alphaAnimation(
    toAlpha: Float,
    duration: Long,
    startDelay: Long,
    onEnd: () -> Unit
) {

    if (alpha == toAlpha) {
        onEnd()
        return
    }

    if (tag is ValueAnimator && (tag as ValueAnimator).isRunning) {
        (tag as ValueAnimator).cancel()
    }

    visible()

    val pointList = ArrayList<ValuesHolder>()
    pointList.add(ValuesHolder("alpha", this.alpha, toAlpha))

    tag = pointList.animation(duration, startDelay, { keyData, _ ->
        alpha = keyData["alpha"] as Float
    }, {

        if (toAlpha == 0f) {
            gone()
        }

        onEnd()
    })
}

fun View.translationYAnim(
    value: Float,
    duration: Long
) {
    translationYAnim(value, duration) {}
}

fun View.translationYAnim(
    toTranslationY: Float,
    duration: Long,
    onEnd: () -> Unit
) {

    if (translationY == toTranslationY) {
        onEnd()
        return
    }

    if (tag is ValueAnimator && (tag as ValueAnimator).isRunning) {
        (tag as ValueAnimator).cancel()
    }

    val pointList = ArrayList<ValuesHolder>()
    pointList.add(ValuesHolder("translationY", translationY, toTranslationY))

    tag = pointList.animation(duration, 0, { keyData, _ ->
        translationY = keyData.get("translationY") as Float
    }, {
        onEnd()
    })
}

fun View.resizeAnimation(
    width: Int,
    height: Int,
    duration: Long = 350
) {
    resizeAnimation(width, height, duration) {}
}

fun View.resizeAnimation(
    width: Int,
    height: Int,
    duration: Long = 350,
    onEnd: () -> Unit
) {

    val pointList = ArrayList<ValuesHolder>()
    pointList.add(
        ValuesHolder(
            "width",
            if (width >= 0) this.width else layoutParams.width,
            if (width >= 0) width else layoutParams.width
        )
    )
    pointList.add(
        ValuesHolder(
            "height",
            if (height >= 0) this.height else layoutParams.height,
            if (height >= 0) height else layoutParams.height
        )
    )

    pointList.animation(duration, 0, { keyData, _ ->
        resize(keyData["width"] as Int, keyData["height"] as Int)
    }, {
        onEnd()
    })
}

fun View.paddingAnimation(
    leftPadding: Int,
    topPadding: Int,
    rightPadding: Int,
    bottomPadding: Int,
    duration: Long
) {
    marginAnimation(leftPadding, topPadding, rightPadding, bottomPadding, duration) {}
}

fun View.paddingAnimation(
    leftPadding: Int,
    topPadding: Int,
    rightPadding: Int,
    bottomPadding: Int,
    duration: Long,
    onEnd: () -> Unit
) {
    val pointList = ArrayList<ValuesHolder>()
    pointList.add(
        ValuesHolder("topPadding", paddingTop, topPadding)
    )
    pointList.add(
        ValuesHolder("bottomPadding", paddingBottom, bottomPadding)
    )
    pointList.add(
        ValuesHolder("leftPadding", paddingLeft, leftPadding)
    )
    pointList.add(
        ValuesHolder("rightPadding", paddingRight, rightPadding)
    )

    pointList.animation(duration, 0, { keyData, _ ->
        val paddingTop = keyData["topPadding"] as Int
        val paddingBottom = keyData["bottomPadding"] as Int
        val paddingLeft = keyData["leftPadding"] as Int
        val paddingRight = keyData["rightPadding"] as Int

        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
    }, {
        onEnd()
    })
}

fun View.marginAnimation(
    leftMargin: Int,
    topMargin: Int,
    rightMargin: Int,
    bottomMargin: Int,
    duration: Long
) {
    marginAnimation(leftMargin, topMargin, rightMargin, bottomMargin, duration) {}
}

fun View.marginAnimation(
    leftMargin: Int,
    topMargin: Int,
    rightMargin: Int,
    bottomMargin: Int,
    duration: Long,
    onEnd: () -> Unit
) {
    val pointList = ArrayList<ValuesHolder>()
    val layoutParams = layoutParams as RelativeLayout.LayoutParams
    pointList.add(ValuesHolder("topMargin", layoutParams.topMargin, topMargin))
    pointList.add(ValuesHolder("bottomMargin", layoutParams.bottomMargin, bottomMargin))
    pointList.add(ValuesHolder("leftMargin", layoutParams.leftMargin, leftMargin))
    pointList.add(ValuesHolder("rightMargin", layoutParams.rightMargin, rightMargin))

    pointList.animation(duration, 0, { keyData, _ ->
        layoutParams.topMargin = keyData["topMargin"] as Int
        layoutParams.bottomMargin = keyData["bottomMargin"] as Int
        layoutParams.leftMargin = keyData["leftMargin"] as Int
        layoutParams.rightMargin = keyData["rightMargin"] as Int
        this.layoutParams = layoutParams
    }, {
        onEnd()
    })
}

fun View.resize(width: Int, height: Int) {
    var update = false
    if (layoutParams.width != width && width >= -2) {
        layoutParams.width = width
        update = true
    }

    if (layoutParams.height != height && height >= -2) {
        layoutParams.height = height
        update = true
    }
    if (update) {
        layoutParams = layoutParams
    }
}

fun EditText.setTextChangedListener(
    onlyWhenFocused: Boolean = false,
    onChanged: (EditText) -> Unit
): TextWatcher {
    val textWatcher = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (onlyWhenFocused && !isFocusable) return
            onChanged(this@setTextChangedListener)
        }
    }
    addTextChangedListener(textWatcher)
    return textWatcher
}

fun EditText.setOnDoneClickedListener(onDoneClicked: (EditText) -> Unit) {
    this.setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onDoneClicked(this)
            return@setOnEditorActionListener true
        } else {
            return@setOnEditorActionListener false
        }
    }
}

fun View.attachedTo(viewGroup: ViewGroup?) {
    if (viewGroup != null && parent != viewGroup) {
        detachedTo(parent)
        viewGroup.addView(this)
    }
}

fun View.detachedTo(viewGroup: ViewParent?) {
    if (viewGroup != null && parent == viewGroup && viewGroup is ViewGroup) {
        viewGroup.removeView(this)
    }
}

fun ViewGroup.inflate(@LayoutRes l: Int): View {
    return LayoutInflater.from(context).inflate(l, this, false)
}

fun ImageView.showImage(source: Any, transformations: ArrayList<Transformation<Bitmap>>) {
    val array = arrayOfNulls<Transformation<Bitmap>>(transformations.size)
    transformations.toArray(array)
    showImage(source, -1, -1, *array)
}

fun ImageView.showImage(
    source: Any,
    width: Int = -1,
    height: Int = -1,
    transformations: ArrayList<Transformation<Bitmap>>
) {
    val array = arrayOfNulls<Transformation<Bitmap>>(transformations.size)
    transformations.toArray(array)
    showImage(source, width, height, *array)
}

fun ImageView.showImage(
    source: Any,
    vararg transformations: Transformation<Bitmap>?
) {
    ImageUtils.showImage(this, source, -1, -1, *transformations)
}

fun ImageView.showImage(
    source: Any,
    width: Int = -1,
    height: Int = -1,
    vararg transformations: Transformation<Bitmap>?
) {
    ImageUtils.showImage(this, source, width, height, *transformations)
}

fun ImageView.showGif(
    source: Any
) {
    ImageUtils.showGif(this, source)
}
