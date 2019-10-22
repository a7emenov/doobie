// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.postgres

import cats.Order
import cats.instances.option._
import cats.syntax.order._

object PGDiscreteRange {

  //todo either create order instance or move [A: Order] to PGRange

  def empty[A: Order]: PGDiscreteRange[A] =
    new PGDiscreteRange(
      left = None,
      leftInclusive = false,
      right = None,
      rightInclusive = false
    )

  def apply[A: Order](left: Option[A],
                      leftInclusive: Boolean,
                      right: Option[A],
                      rightInclusive: Boolean): PGDiscreteRange[A] =

   // todo adjust left/right depending on leftInclusive/rightInclusive (cats-collections Discrete?)
    new PGDiscreteRange[A](
      left = left,
      leftInclusive = left.isDefined && leftInclusive,
      right = right,
      rightInclusive = right.isDefined && rightInclusive
    )
}

final case class PGDiscreteRange[A: Order] private(left: Option[A],
                                                   leftInclusive: Boolean,
                                                   right: Option[A],
                                                   rightInclusive: Boolean) extends PGRange[A] {

  // todo need analogue of Discrete from cats-collections for equals? (equality depends on leftInclusive/rightInclusive)


  // todo need analogue of Discrete as well due to the cases like (1, 2)
  def isEmpty: Boolean =
    (left.isDefined && right.isDefined) &&
    (
      (left > right) ||
      (!leftInclusive || !rightInclusive) && (left === right)
    )

  //todo prevent copy?
}
