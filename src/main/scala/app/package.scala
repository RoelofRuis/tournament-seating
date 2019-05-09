package object app {

  type TableSize = Int
  type TableSetup = Seq[TableSize]
  type Round = Seq[Int]
  type Schedule = Seq[Round]
  type Score = Double

  type ScoreFun = Schedule => Score
  type ExpandFun = Round => Seq[Round]

  case class InvalidArguments(message: String)

  case class Arguments(nRounds: Int, tableSizes: Seq[TableSize], players: Seq[String]) {
    def numPlayers: Int = players.size
  }

}
