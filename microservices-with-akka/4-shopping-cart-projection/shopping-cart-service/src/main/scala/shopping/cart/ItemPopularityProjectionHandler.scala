
package shopping.cart

import akka.actor.typed.ActorSystem
import akka.projection.eventsourced.EventEnvelope
import akka.projection.jdbc.scaladsl.JdbcHandler
import org.slf4j.LoggerFactory
import shopping.cart.repository.ItemPopularityRepository
import shopping.cart.repository.ScalikeJdbcSession

class ItemPopularityProjectionHandler(
    tag: String,
    system: ActorSystem[_],
    repo: ItemPopularityRepository)
    extends JdbcHandler[
      EventEnvelope[ShoppingCart.Event],
      ScalikeJdbcSession]() {

  private val log = LoggerFactory.getLogger(getClass)

  override def process(
      session: ScalikeJdbcSession,
      envelope: EventEnvelope[ShoppingCart.Event]): Unit = {
    envelope.event match {
      case ShoppingCart.ItemAdded(_, itemId, quantity) =>
        repo.update(session, itemId, quantity)
        logItemCount(session, itemId)
      case ShoppingCart.ItemQuantityAdjusted(
            _,
            itemId,
            newQuantity,
            oldQuantity) =>
        repo.update(session, itemId, newQuantity - oldQuantity)
        logItemCount(session, itemId)
      case ShoppingCart.ItemRemoved(_, itemId, oldQuantity) =>
        repo.update(session, itemId, 0 - oldQuantity)
        logItemCount(session, itemId)
      case _: ShoppingCart.CheckedOut =>
    }
  }

  private def logItemCount(
      session: ScalikeJdbcSession,
      itemId: String): Unit = {
    log.info(
      "ItemPopularityProjectionHandler({}) item popularity for '{}': [{}]",
      tag,
      itemId,
      repo.getItem(session, itemId).getOrElse(0))
  }

}
