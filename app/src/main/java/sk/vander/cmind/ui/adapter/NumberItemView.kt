package sk.vander.cmind.ui.adapter

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.bindView
import rx.Observable
import sk.vander.cmind.R
import sk.vander.library.view.BindableView

/**
 * Author vander @ 10/5/16.
 */
class NumberItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RelativeLayout(context, attrs, defStyle), BindableView<String> {
  val text by bindView<TextView>(R.id.number)

  override fun onSelected(selected: Boolean) {
  }

  override fun bindTo(item: String?) {
    text.text = item
  }

  override fun getView(): View = this

  override fun getObjectObservable(): Observable<Any> = Observable.empty()
}