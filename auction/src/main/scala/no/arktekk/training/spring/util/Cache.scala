package no.arktekk.training.spring.util
import scala.collection.mutable.Map

/**
 * @author <a href="mailto:kaare.nilsen@arktekk.no">Kaare Nilsen</a>
 */

class Cache[K,T] {
  private val cache:Map[K, T] = Map()
  private var cacheHits: Int = 0
  private var cacheMiss: Int = 0

  def contains(key: K) = cache.contains(key)

  def get(key: K)(f:T): Option[T] = {
    if (!contains(key)) {
      cacheMiss += 1;
      val t = f


      if (t != null) {
        cache.put(key, t)
      }
    }
    else {
      cacheHits += 1;
    }
    cache.get(key)
  }

  def evict(key: K) {
    cache - key
  }

  def clear() {
    cache.clear()
  }


}