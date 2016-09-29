package sk.vander.cmind.ui.debug

import android.app.Activity
import android.content.Context.POWER_SERVICE
import android.os.PowerManager
import android.os.PowerManager.*
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import com.jakewharton.processphoenix.ProcessPhoenix
import com.squareup.picasso.Picasso
import io.palaima.debugdrawer.DebugDrawer
import io.palaima.debugdrawer.actions.Action
import io.palaima.debugdrawer.actions.ActionsModule
import io.palaima.debugdrawer.actions.SwitchAction
import io.palaima.debugdrawer.commons.BuildModule
import io.palaima.debugdrawer.commons.DeviceModule
import io.palaima.debugdrawer.okhttp3.OkHttp3Module
import io.palaima.debugdrawer.picasso.PicassoModule
import io.palaima.debugdrawer.scalpel.ScalpelModule
import io.palaima.debugdrawer.timber.TimberModule
import okhttp3.OkHttpClient
import sk.vander.cmind.R
import sk.vander.cmind.data.api.MockMode
import sk.vander.library.AppContainer
import sk.vander.library.annotation.ApplicationScope
import sk.vander.library.prefs.BooleanPreference
import javax.inject.Inject

@ApplicationScope
class DebugAppContainer
@Inject internal constructor(@MockMode val mockPref: BooleanPreference,
                             val okHttpClient: OkHttpClient,
                             val picasso: Picasso) : AppContainer {

  override fun get(activity: Activity): ViewGroup {
    activity.setContentView(R.layout.container_debug)

    val mock = SwitchPref("Mock mode", mockPref, SwitchAction.Listener { ProcessPhoenix.triggerRebirth(activity) })

    DebugDrawer.Builder(activity).modules(ActionsModule(mock),
        ScalpelModule(activity),
        TimberModule(),
        OkHttp3Module(okHttpClient),
        PicassoModule(picasso),
        DeviceModule(activity),
        BuildModule(activity)).build()

    riseAndShine(activity)
    return activity.findViewById(R.id.container_debug) as ViewGroup
  }

  private class SwitchPref(private val name: String, private val pref: BooleanPreference, private val listener: SwitchAction.Listener?) : Action {
    private var switchButton: Switch? = null

    override fun getView(linearLayout: LinearLayout): View {
      val context = linearLayout.context
      val resources = context.resources
      val viewGroupLayoutParams = LinearLayout.LayoutParams(-1, -1)
      viewGroupLayoutParams.topMargin = resources.getDimensionPixelOffset(io.palaima.debugdrawer.actions.R.dimen.dd_padding_small)
      val viewGroup = LinearLayout(context)
      viewGroup.layoutParams = viewGroupLayoutParams
      viewGroup.orientation = LinearLayout.HORIZONTAL
      val textViewLayoutParams = LinearLayout.LayoutParams(-2, -2)
      textViewLayoutParams.rightMargin = resources.getDimensionPixelSize(io.palaima.debugdrawer.actions.R.dimen.dd_spacing_big)
      val textView = TextView(context)
      textView.layoutParams = textViewLayoutParams
      textView.text = this.name
      textView.setTextSize(0, resources.getDimension(io.palaima.debugdrawer.actions.R.dimen.dd_font_normal))
      textView.gravity = 16
      this.switchButton = Switch(context)
      onStart()
      viewGroup.addView(textView)
      viewGroup.addView(this.switchButton)
      return viewGroup
    }

    override fun onOpened() {
    }

    override fun onClosed() {
    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStart() {
      this.switchButton!!.setOnCheckedChangeListener(null)
      this.switchButton!!.isChecked = pref.get()
      this.switchButton!!.setOnCheckedChangeListener { buttonView, isChecked ->
        pref.set(isChecked)
        listener?.onCheckedChanged(isChecked)
      }
    }

    override fun onStop() {
    }
  }

  companion object {

    /**
     * Show the activity over the lock-screen and wake up the device. If you launched the app manually
     * both of these conditions are already true. If you deployed from the IDE, however, this will
     * save you from hundreds of power button presses and pattern swiping per day!
     */
    fun riseAndShine(activity: Activity) {
      activity.window.addFlags(FLAG_SHOW_WHEN_LOCKED)

      val power = activity.getSystemService(POWER_SERVICE) as PowerManager
      val lock = power.newWakeLock(FULL_WAKE_LOCK or ACQUIRE_CAUSES_WAKEUP or ON_AFTER_RELEASE, "wakeup!")
      lock.acquire()
      lock.release()
    }
  }
}
