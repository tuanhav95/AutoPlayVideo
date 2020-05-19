package com.tuanhav95.auto_play_video.example.utils.extension

import android.animation.Animator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.text.Spannable
import android.text.style.ImageSpan
import android.text.style.StyleSpan
import android.util.TypedValue
import java.util.regex.Matcher
import java.util.regex.Pattern


fun <T> Any?.castList(
    clazz: Class<T>
): ArrayList<T>? {
    if (this == null) {
        return null
    }
    if (this is List<*>) {
        val result = arrayListOf<T>()
        for (o in this) {
            result.add(clazz.cast(o)!!)
        }
        return result
    }
    throw ClassCastException()
}

fun <K, V> Any?.castMap(
    keyType: Class<K>,
    valueType: Class<V>
): Map<K?, V?>? {
    if (this == null) {
        return null
    }
    if (this is Map<*, *>) {
        val result: MutableMap<K?, V?> = HashMap()
        for ((key, value) in this) {
            result[keyType.cast(key)] = valueType.cast(value)
        }
        return result
    }
    throw ClassCastException()
}

fun <K, V> Any?.castMapList(
    keyType: Class<K>,
    valueType: Class<V>
): List<Map<K?, V?>?>? {
    if (this == null) {
        return null
    }
    if (this is List<*>) {
        val result: MutableList<Map<K?, V?>?> =
            ArrayList()
        for (o in this) {
            result.add(o.castMap(keyType, valueType))
        }
        return result
    }
    throw ClassCastException()
}

fun ArrayList<ValuesHolder>.animation(
    duration: Long,
    onUpdate: (HashMap<String, Any>, ValueAnimator) -> Unit,
    onEnd: () -> Unit
): ValueAnimator {
    return animation(duration, 0, onUpdate, onEnd)
}

fun ArrayList<ValuesHolder>.animation(
    duration: Long,
    startDelay: Long,
    onUpdate: (HashMap<String, Any>, ValueAnimator) -> Unit,
    onEnd: () -> Unit
): ValueAnimator {
    val array = arrayOfNulls<ValuesHolder>(size)
    toArray(array)
    return array.animation(duration, startDelay, onUpdate, onEnd)
}

fun Array<ValuesHolder?>.animation(
    duration: Long,
    startDelay: Long,
    onUpdate: (HashMap<String, Any>, ValueAnimator) -> Unit,
    onEnd: () -> Unit
): ValueAnimator {
    val keys = ArrayList<String>()
    val tos = ArrayList<Any>()
    for (valuesHolder in this) {
        keys.add(valuesHolder!!.key)
        tos.add(valuesHolder.to)
    }

    val animator = ValueAnimator.ofPropertyValuesHolder(*this.to())

    animator.addUpdateListener {
        val keyData = HashMap<String, Any>()
        val currentData = ArrayList<Any>()


        for (key in keys) {
            val data = it.getAnimatedValue(key)
            keyData[key] = data
            currentData.add(data)
        }

        onUpdate(keyData, it)

        var equals = true
        for (i in tos.indices) {
            if (tos[i] != currentData[i]) equals = false
        }
        if (equals) {
            it.cancel()
        }
    }

    animator.withEndAction(Runnable {
        onEnd()
    })

    animator.duration = duration
    animator.startDelay = startDelay
    animator.start()

    return animator
}

fun Array<ValuesHolder?>.to(): Array<PropertyValuesHolder> {
    return Array(size) { i -> this[i]!!.to() }
}

class ValuesHolder(val key: String, val from: Any, val to: Any) {

    fun to(): PropertyValuesHolder {
        return if (from is Int && to is Int) {
            PropertyValuesHolder.ofInt(key, from, to)
        } else {
            PropertyValuesHolder.ofFloat(key, (from as Float), (to as Float))
        }
    }
}

fun Animator.withEndAction(runnable: Runnable) {
    addListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {
        }

        override fun onAnimationCancel(animation: Animator?) {
        }

        override fun onAnimationStart(animation: Animator?) {
        }

        override fun onAnimationEnd(animation: Animator?) {
            animation?.removeAllListeners()
            runnable.run()
        }

    })
}

fun Int.toPx(): Int {
    return this.toFloat().toPx()
}

fun Float.toPx(): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    ).toInt()
}

@SuppressLint("DefaultLocale")
fun String.normalize(): String {
    return this.replace(
        "  ",
        " "
    ).trim { it <= ' ' }.toLowerCase().replace(
        "[aáàảãạ]".toRegex(),
        "a"
    ).replace("[ăắằẳẵặ]".toRegex(), "a").replace(
        "[âầấẩẫậ]".toRegex(),
        "a"
    ).replace("[eèéẻẽẹ]".toRegex(), "e").replace(
        "[êềếểễệ]".toRegex(),
        "e"
    ).replace("[iìíỉĩị]".toRegex(), "i").replace(
        "[oòóỏõọ]".toRegex(),
        "o"
    ).replace("[ôồốổỗộ]".toRegex(), "o").replace(
        "[ơờớởỡợ]".toRegex(),
        "o"
    ).replace("[uùúủũụ]".toRegex(), "u").replace(
        "[ưừứửữự]".toRegex(),
        "u"
    ).replace("[yỳýỷỹỵ]".toRegex(), "y").replace(
        "[đ]".toRegex(),
        "d"
    ).replace("[^a-zA-Z0-9\\s+]".toRegex(), "")
}

fun Spannable.replace(context: Context, emoticon: String, drawable: Int): Spannable {
    val emoticons = HashMap<String, Int>()
    emoticons[emoticon] = drawable
    return replace(context, emoticons)
}

fun Spannable.replace(context: Context, emoticons: HashMap<String, Int>): Spannable {
    for ((key, value) in emoticons.entries) {
        replace(key, ImageSpan(context, value))
    }
    return this
}

fun Spannable.replace(text: String, type: Int): Spannable {
    replace(text, StyleSpan(type))
    return this
}

fun Spannable.replace(text: String, obj: Any): Spannable {
    val matcher: Matcher = Pattern.compile(Pattern.quote(text)).matcher(this)
    while (matcher.find()) {
        var set = true
        for (span in getSpans(
            matcher.start(),
            matcher.end(),
            obj::class.java
        )) {
            if (getSpanStart(span) >= matcher.start() && getSpanEnd(span) <= matcher.end()) removeSpan(
                span
            ) else {
                set = false
                break
            }
        }
        if (set) {
            setSpan(
                obj,
                matcher.start(),
                matcher.end(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
    return this
}
