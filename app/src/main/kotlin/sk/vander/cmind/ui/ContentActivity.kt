package sk.vander.cmind.ui

import android.widget.ViewAnimator
import autodagger.AutoInjector
import butterknife.bindView
import sk.vander.cmind.AppComponent
import sk.vander.cmind.R
import sk.vander.cmind.StandardActivity
import sk.vander.library.BaseActivity
import sk.vander.library.DaggerService
import sk.vander.library.annotation.LayoutId
import sk.vander.library.annotation.ScreenLabel

@StandardActivity
@AutoInjector
@ScreenLabel(R.string.label_content)
@LayoutId(R.layout.activity_content)
class ContentActivity : BaseActivity() {
  val animator: ViewAnimator by bindView(R.id.animator)

  override fun onCreateComponent(appComponent: Any): Any {
    return DaggerContentActivityComponent.builder().appComponent(appComponent as AppComponent).build()
  }

  override fun onInject() {
    DaggerService.getDaggerComponent<DaggerContentActivityComponent>(this).inject(this)
  }
}
