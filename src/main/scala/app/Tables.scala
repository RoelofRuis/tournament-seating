package app

object Tables {

  def findPossibleSetups(numPlayers: Int, permittedSizes: Seq[TableSize]): List[TableSetup] = {

    def seatNextPlayer(numPlayers: Int, permittedSizes: Seq[TableSize], foundTables: TableSetup): Set[TableSetup] = {
      if (numPlayers == 0) Set(foundTables.sorted.reverse)
      else permittedSizes.flatMap(seats =>
        if (numPlayers - seats >= 0) seatNextPlayer(numPlayers - seats, permittedSizes, foundTables :+ seats)
        else Seq()
      ).toSet
    }

    seatNextPlayer(numPlayers, permittedSizes, Seq()).toList
  }

  def pickBestSetup(setups: List[TableSetup]): TableSetup = setups.maxBy { setup => setup.sum.toDouble / setup.size }

}
