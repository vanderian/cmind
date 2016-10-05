package sk.vander.cmind.data

import autodagger.AutoExpose
import rx.Observable
import sk.vander.cmind.App
import sk.vander.library.annotation.ApplicationScope
import java.util.*
import javax.inject.Inject

/**
 * Author vander @ 10/5/16.
 */
@ApplicationScope
@AutoExpose(App::class)
class NumbersProvider @Inject constructor() {

  fun list() : Observable<List<String>> {
    return Observable.just(listOf("one 1 two2", "three 3 four 4", "ten 1 nine 0"))
  }

  fun number() {
    val random = Random()
    val len = random.nextInt(20) + 1
    1.rangeTo(len).map { random.nextInt(10) }.joinToString()
  }

  fun makeString(number: Long) {

  }

}