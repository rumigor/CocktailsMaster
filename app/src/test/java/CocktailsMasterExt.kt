import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import kotlin.math.roundToInt

fun View.click(click: () -> Unit) = setOnClickListener { click() }

fun Fragment.arguments(vararg arguments: Pair<String, Any>): Fragment {
    this.arguments = bundleOf(*arguments)
    return this
}

fun getAuth(): LazyHeaders {
    var user = ""
    var password = ""
    AppPreferences.username?.let {
        user = it
    }
    AppPreferences.password?.let {
        password = it
    }
    return LazyHeaders.Builder()
        .addHeader("Authorization", BasicAuthorization(user, password))
        .build()
}

@Suppress("IMPLICIT_CAST_TO_ANY")
fun TextView.setStartDrawableCircleImageFromUri(uri: String, placeholder: Int = R.drawable.ic_baseline_block_24) {

    val glideUrl = if (uri.isEmpty()) placeholder else GlideUrl(uri, getAuth())

    Glide.with(context)
        .load(glideUrl)
        .placeholder(placeholder)
        .apply(
            RequestOptions
                .circleCropTransform()
                .override(80.dp(this.context))
        )
        .into(object : CustomViewTarget<TextView, Drawable>(this) {

            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                view.setCompoundDrawable(resource, null, null, null)
            }

            override fun onResourceCleared(placeholder: Drawable?) {
                view.setCompoundDrawable(placeholder, null, null, null)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                view.setCompoundDrawable(errorDrawable, null, null, null)
            }

        })

}

fun TextView.setCompoundDrawable(
    left: Drawable? = null,
    top: Drawable? = null,
    right: Drawable? = null,
    bottom: Drawable? = null
) {
    setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
}

fun Int.dp(context: Context): Int =
    TypedValue
        .applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            context.resources.displayMetrics
        )
        .roundToInt()