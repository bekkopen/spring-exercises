package no.arktekk.training.spring.repository

/**
 * @author <a href="mailto:kaare.nilsen@arktekk.no">Kaare Nilsen</a>
 */

trait CrudRepository[K,T] {

  def find(id: K): Option[T]

  def list: List[T]

  def addNew(domainObject: T)

  def update(domainObject: T)

  def delete(domainObject: T)

}