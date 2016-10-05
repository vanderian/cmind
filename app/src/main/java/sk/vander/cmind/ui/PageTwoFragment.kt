package sk.vander.cmind.ui

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import autodagger.AutoInjector
import butterknife.bindView
import rx.lang.kotlin.observable
import rx.subscriptions.Subscriptions
import sk.vander.cmind.R
import sk.vander.cmind.data.NumbersProvider
import sk.vander.cmind.ui.adapter.NumberSource
import sk.vander.library.BaseFragment
import sk.vander.library.DaggerService
import sk.vander.library.adapter.ObservableAdapter
import sk.vander.library.annotation.LayoutId
import javax.inject.Inject

/**
 * Author vander @ 10/5/16.
 */
@AutoInjector(ContentActivity::class)
@LayoutId(R.layout.fragment_page_two)
class PageTwoFragment : BaseFragment() {
  val source = NumberSource()
  val adapter = ObservableAdapter<String>(source)

  val refreshLayout by bindView<SwipeRefreshLayout>(R.id.refresh)
  val recyclerView by bindView<RecyclerView>(R.id.list)

  @Inject lateinit var data: NumbersProvider

  override fun onInject() {
    DaggerService.getDaggerComponent<ContentActivityComponent>(context).inject(this)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    adapter.setHasStableIds(true)
    recyclerView.adapter = adapter
    recyclerView.setHasFixedSize(true)
    refreshLayout.setColorSchemeResources(R.color.red_500, R.color.yellow_500, R.color.green_500)
  }

  override fun onResume() {
    super.onResume()

    val sro = observable<Any> {
      refreshLayout.setOnRefreshListener { it.onNext(Any()) }
      it.add(Subscriptions.create { refreshLayout.setOnRefreshListener(null) })
    }

    val o = sro.startWith(Any())
        .flatMap { data.list() }
        .doOnEach { refreshLayout.isRefreshing = false }
        .doOnNext { source.list = it }
        .publish()
    subscription.add(o.subscribe({ adapter.notifyDataSetChanged() }, { it.printStackTrace() }))
    refreshLayout.post({
      refreshLayout.isRefreshing = true
      o.connect()
    })

    subscription.add(adapter.onItemClicked()
        .filter { it.pos == 0 }
        .subscribe { (activity as ContentActivity).animator.showPrevious() })
  }
}