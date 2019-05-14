package app

object Schedule {

  def initialize(nRounds: Int, tableSetup: TableSetup): Schedule = {
    val tableVector = tableSetup
      .zipWithIndex
      .flatMap { case (tableSize, tableNr) => Seq.fill(tableSize)(tableNr) }

    Range.inclusive(1, nRounds).map { _ => tableVector }
  }

  def expandAll: ExpandFun = { round =>
    val indexedRound: Seq[(TableSize, Int)] = round.zipWithIndex

    indexedRound.flatMap {
      case (table, player1) =>
        indexedRound
          .filter { case (t, player2) => t != table && player2 > player1 }
          .map {
            case (_, player2) => round.updated(player2, round(player1)).updated(player1, round(player2))
          }
    }
  }

}
