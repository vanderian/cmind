package sk.vander.library

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import butterknife.bindView
import sk.vander.library.annotation.HideToolbar
import sk.vander.library.annotation.LayoutId
import sk.vander.library.annotation.ScreenLabel
import sk.vander.library.annotation.ShowUp
import sk.vander.library.misc.Utils
import sk.vander.library.navigation.activity.ActivityScreenSwitcher
import sk.vander.library.navigation.activity.ActivityUriScreen
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.util.*
import javax.inject.Inject

/**
 * Created by arashid on 26/06/16.
 */
abstract class BaseActivity : AppCompatActivity() {

  @Inject lateinit var screenSwitcher: ActivityScreenSwitcher
  @Inject lateinit var appContainer: AppContainer

  private var component: Any? = null

  val contentFrame by bindView<ViewGroup>(R.id.frame_content)
  val appBar by bindView<AppBarLayout>(R.id.appBar)
  val toolbar by bindView<Toolbar>(R.id.toolbar)
  val progress by bindView<View>(R.id.progress)

  override fun attachBaseContext(newBase: Context) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
  }

  override fun getSystemService(name: String): Any {
    return if (DaggerService.SERVICE_NAME == name) component!! else super.getSystemService(name)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    component = onCreateComponent(DaggerService.getDaggerComponent<Any>(applicationContext))
    onInject()

    if (appContainer == null) {
      throw IllegalStateException("No injection happened. Add DaggerService.getDaggerComponent(this).inject(this) in onInject() implementation.")
    }

    initViewContainer()
    setupAppBar()
  }

  override fun setTitle(title: CharSequence) {
    toolbar.title = title
  }

  override fun setTitle(titleId: Int) {
    toolbar.setTitle(titleId)
  }

  protected fun initViewContainer() {
    val layoutInflater = layoutInflater
    val container = appContainer!!.get(this)
    layoutInflater.inflate(R.layout.activity_base, container, true)

    if (layoutId() != 0) {
      layoutInflater.inflate(layoutId(), contentFrame, true)
      //      content = (ViewGroup) layoutInflater.inflate(layoutId(), container, false);
      //      contentFrame.addView(content);
    }

//    ButterKnife.bind(this)
    //    consume clicks
    progress.setOnClickListener { v -> }
  }

  protected fun setupAppBar() {
    appBar.visibility = if (javaClass.isAnnotationPresent(HideToolbar::class.java)) View.GONE else View.VISIBLE
    val titleRes = Utils.getAnnotationValue(javaClass, INT_CACHE, ScreenLabel::class.java)
    if (titleRes != 0) toolbar.setTitle(titleRes)
    if (javaClass.isAnnotationPresent(ShowUp::class.java)) {
      val resId = Utils.getAnnotationValue(javaClass, INT_CACHE, ShowUp::class.java)
      toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
      toolbar.setNavigationOnClickListener { v ->
        if (resId != 0) {
          screenSwitcher!!.open(ActivityUriScreen.withUri(Uri.parse(getString(resId))), true)
        } else {
          onBackPressed()
        }
      }
    }
  }

  @LayoutRes protected fun layoutId(): Int {
    return Utils.getAnnotationValue(javaClass, INT_CACHE, LayoutId::class.java)
  }

  fun showProgress(show: Boolean) {
    progress.visibility = if (show) View.VISIBLE else View.GONE
  }

  /**
   *
   *
   * Must be implemented by derived activities. Injection must be performed here.
   * Otherwise IllegalStateException will be thrown. Derived activity is
   * responsible to create and store it's component.
   *

   * @param appComponent application level component
   */
  protected abstract fun onCreateComponent(appComponent: Any): Any

  protected abstract fun onInject()

  companion object {
    private val INT_CACHE = LinkedHashMap<String, Int>()
  }
}
