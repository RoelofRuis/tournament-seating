package app

object Schedule {

  def initialize(nRounds: Int, tableSetup: TableSetup): Schedule = {
    val tableVector = tableSetup
      .zipWithIndex
      .flatMap { case (tableSize, tableNr) => Seq.fill(tableSize)(tableNr) }

    Range.inclusive(1, nRounds).map { _ => tableVector }
  }

  def swapPlayers(round: Round, player1: Int, player2: Int): Round = {
    var target = round
    val indexedTarget = target.zipWithIndex
    val p1 = indexedTarget(player1)
    val p2 = indexedTarget(player2)
    Seq((p1._1, p2._2), (p2._1, p1._2)).foreach { case (table, pos) => target = target.updated(pos, table) }

    target
  }

  def expandAll: ExpandFun = { round =>
    val indexedRound: Seq[(TableSize, Int)] = round.zipWithIndex
    indexedRound.flatMap { case (table, p1) =>
      indexedRound.filter { case (t, p2) => t != table && p2 > p1 }
        .map { case (_, p2) => swapPlayers(round, p1, p2) }
    }
  }

}
