package sk.vander.library

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import rx.Observable
import rx.subscriptions.CompositeSubscription
import sk.vander.library.annotation.LayoutId
import sk.vander.library.misc.Utils
import sk.vander.library.navigation.activity.ActivityScreenSwitcher
import sk.vander.library.retrofit.error.RetrofitException
import timber.log.Timber
import java.util.*
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    protected val subscription = CompositeSubscription()
    @Inject lateinit var screenSwitcher: ActivityScreenSwitcher

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        onInject()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(layoutId(), container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    protected fun onError(throwable: Throwable): Observable<String> {
        return Observable.just(throwable)
                .doOnNext { t -> Timber.d(t, "BaseFragment: Api Error") }
                .ofType(RetrofitException::class.java)
                .flatMap { ex ->
                    if (RetrofitException.Kind.NETWORK == ex.kind) {
                        Observable.just<String>(ex.message)
                    }
                    if (RetrofitException.Kind.HTTP == ex.kind) {
                        ex.asErrorResponse.map { it.error().message() }.onErrorResumeNext(Observable.just<String>(ex.message))
                    }
//          Unexpected error -> fail
                    Observable.error<String>(ex)
                }
                .doOnError { t -> Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show() }
                //        .doOnNext(er -> Snackbar.make(getView(), er.error().message(), Snackbar.LENGTH_SHORT).show())
                .doOnNext { er -> Toast.makeText(context, er, Toast.LENGTH_SHORT).show() }
    }

    override fun onPause() {
        super.onPause()
        subscription.clear()
    }

    @LayoutRes protected fun layoutId(): Int {
        return Utils.getAnnotationValue(javaClass, INT_CACHE, LayoutId::class.java)
    }

    val baseActivity: BaseActivity
        get() = activity as BaseActivity

    protected abstract fun onInject()

    companion object {
        private val INT_CACHE = LinkedHashMap<String, Int>()
    }
}
