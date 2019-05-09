package app

object Score {

  def rootPlaySum: ScoreFun = { schedule =>
    var plays = Map[Int, List[Int]]()
    schedule.foreach { round =>
      val playerSeats = round.zipWithIndex

      playerSeats
        .foreach { case (table, player) =>
          playerSeats
            .filter { case (t, p) => player != p && t == table }
            .foreach { case (_, p) => plays = plays.updated(player, plays.getOrElse(player, List()) :+ p) }
        }
    }

    plays
      .values
      .map { played =>
        played.foldLeft(Map[Int, Int]()){ case (acc, p) => acc.updated(p, acc.getOrElse(p, 0) + 1)}
      }
      .map { _.values.map(i => Math.sqrt(i.toDouble)).sum }.sum
  }

}
