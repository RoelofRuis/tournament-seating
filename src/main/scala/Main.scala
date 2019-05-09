import java.io.File

import app._

object Main extends App {

  if (args.length != 1) {
    println("The name of the parameters file should be given")
    System.exit(1)
  }

  val inputFile = new File(args(0))

  val arguments: Either[InvalidArguments, Arguments] = IO.parseInputFile(inputFile.getCanonicalPath)

  if (arguments.isLeft) {
    println(arguments.left.get.message)
    System.exit(1)
  }

  arguments.map { arguments =>
    // Find best table setup
    val setups = Tables.findPossibleSetups(arguments.numPlayers, arguments.tableSizes)

    if (setups.isEmpty) {
      println(s"Cannot seat [${arguments.numPlayers}] players at tables with sizes ${arguments.tableSizes.mkString("[",",","]")}")
      System.exit(1)
    }

    val bestSetup = Tables.pickBestSetup(setups)

    // Optimize tournament schedule
    val initialSchedule = Schedule.initialize(arguments.nRounds, bestSetup)
    val (bestSchedule, bestScore) = Optimizer.findBest(initialSchedule, Score.rootPlaySum, Schedule.expandAll)

    // Format output data
    val descriptionString = Range.inclusive(1, arguments.nRounds).map { round =>
      val playersDescription = bestSchedule(round - 1)
        .zip(arguments.players)
        .groupBy { case (table, _) => table }
        .toList
        .sortBy { case (table, _) => table }
        .map { case (table, players) => s"At table [${table + 1}]: " + players.map { case (_, name) => name }.mkString(", ") }
        .mkString("\n")

      s"Round [$round]:\n$playersDescription"
    }
        .mkString("\n\n")

    val resultString = f"Score: [$bestScore]\nNumber of players: [${arguments.numPlayers}]\nTables Required: ${bestSetup.mkString("[",",","]")}\n\n$descriptionString"

    // Write data to file
    IO.writeOutputFile(new File("scheduled_" + inputFile.getName).getCanonicalPath, resultString)
  }

}
