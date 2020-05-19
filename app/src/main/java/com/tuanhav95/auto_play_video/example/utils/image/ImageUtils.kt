package com.bee.utils.utils.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.request.target.CustomTarget

class ImageUtils {
    companion object {

        fun showGif(
            imageView: ImageView,
            source: Any
        ) {
            Glide.with(imageView)
                .asGif()
                .load(source)
                .into(imageView)
        }

        fun showImage(
            imageView: ImageView,
            source: Any,
            width: Int = -1,
            height: Int = -1,
            transformations: ArrayList<Transformation<Bitmap>>?
        ) {
            if (!transformations.isNullOrEmpty()) {
                val array = arrayOfNulls<Transformation<Bitmap>>(transformations.size)
                transformations.toArray(array)
                showImage(imageView, source, width, height, *array)
            } else {
                showImage(imageView, source, width, height)
            }
        }

        fun showImage(
            imageView: ImageView,
            source: Any,
            width: Int = -1,
            height: Int = -1,
            vararg transformations: Transformation<Bitmap>?
        ) {
            Glide.with(imageView)
                .asBitmap()
                .transform(
                    if (transformations.isNullOrEmpty()) CenterCrop() else MultiTransformation<Bitmap>(
                        *transformations
                    )
                )
//                .transition(
//                    com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade(
//                        500
//                    )
//                )
                .load(source)
                .override(width, height)
                .into(imageView)
        }

        fun getBitmapBy(
            context: Context,
            path: String,
            size: Int,
            transformations: ArrayList<Transformation<Bitmap>>?
        ): Bitmap {
            return if (!transformations.isNullOrEmpty()) {
                val array = arrayOfNulls<Transformation<Bitmap>>(transformations.size)
                transformations.toArray(array)
                getBitmapBy(context, path, size, *array)
            } else {
                getBitmapBy(context, path, size)
            }
        }

        fun getBitmapBy(
            context: Context,
            path: String,
            size: Int,
            vararg transformations: Transformation<Bitmap>?
        ): Bitmap {
            return Glide.with(context)
                .asBitmap()
                .transform(
                    if (transformations.isNullOrEmpty()) FitCenter() else MultiTransformation<Bitmap>(
                        *transformations
                    )
                )
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load(path)
                .submit(size, size)
                .get()
        }

        fun getBitmapBy(
            context: Context,
            path: String,
            size: Int,
            transformations: ArrayList<Transformation<Bitmap>>?,
            onBitmap: (Bitmap) -> Unit
        ) {
            if (!transformations.isNullOrEmpty()) {
                val array = arrayOfNulls<Transformation<Bitmap>>(transformations.size)
                transformations.toArray(array)
                getBitmapBy(context, path, size, *array) {
                    onBitmap(it)
                }
            } else {
                val array = arrayOfNulls<Transformation<Bitmap>>(0)
                getBitmapBy(context, path, size, *array) {
                    onBitmap(it)
                }
            }
        }

        fun getBitmapBy(
            context: Context,
            path: String,
            size: Int,
            vararg transformations: Transformation<Bitmap>?,
            onBitmap: (Bitmap) -> Unit
        ) {
            Glide.with(context)
                .asBitmap()
                .transform(
                    if (transformations.isNullOrEmpty()) FitCenter() else MultiTransformation<Bitmap>(
                        *transformations
                    )
                )
                .load(path)
                .override(size, size)
                .into(object : CustomTarget<Bitmap>() {

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }

                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                    ) {
                        onBitmap(resource)
                    }
                })
        }

        fun getBitmapBy(
            context: Context,
            bitmap: Bitmap,
            vararg transformations: Transformation<Bitmap>?
        ): Bitmap {
            return Glide.with(context)
                .asBitmap()
                .transform(
                    if (transformations.isNullOrEmpty()) FitCenter() else MultiTransformation<Bitmap>(
                        *transformations
                    )
                )
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .load(bitmap)
                .submit(bitmap.width, bitmap.height)
                .get()
        }

        fun clearDiskCache(context: Context) {
            Glide.get(context).clearDiskCache()
        }

        fun clearMemory(context: Context) {
            Glide.get(context).clearMemory()
        }

        fun clear(imageView: ImageView) {
            Glide.with(imageView).clear(imageView)
        }
    }

}