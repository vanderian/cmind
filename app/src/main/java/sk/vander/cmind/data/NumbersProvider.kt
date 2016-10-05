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
  val names = mapOf(
      Pair(1, "one"),
      Pair(2, "two"),
      Pair(3, "three"),
      Pair(4, "four"),
      Pair(5, "five"),
      Pair(6, "six"),
      Pair(7, "seven"),
      Pair(8, "eight"),
      Pair(9, "nine")
  )

  fun list() : Observable<List<String>> {
    val res = 0.rangeTo(100).map { number() }.map { makeString(it) }
//    return Observable.just(listOf("one 1 two2", "three 3 four 4", "ten 1 nine 0"))
    return Observable.just(res)
  }

  fun number(): List<Int> {
    val random = Random()
    val len = random.nextInt(20) + 1
    val num = 1.rangeTo(len).map { random.nextInt(10) }
    return num
  }

  fun makeString(number: List<Int>): String {
    tailrec fun loop(n: List<Int>, acc: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
      return if (n.isEmpty()) acc
      else {
        val h = n.first()
        val i = n.takeWhile { it == h }.count()
        loop(n.drop(i), listOf(*acc.toTypedArray(), (Pair(i, h))))
      }
    }
    val l = loop(number, listOf())
    return l.map { "${names[it.first]} ${it.second}" }.joinToString(" ")
  }

//  for testing...
  fun makeString(number:String): String = makeString(number.toList().map(Char::toString).map(String::toInt))

}