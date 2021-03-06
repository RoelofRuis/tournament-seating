package app

import scala.collection.mutable

object Optimizer {

  def findBest(initialSchedule: Schedule, score: ScoreFun, expand: ExpandFun): (Schedule, Score) = {
    val queue = mutable.Queue[Schedule]()
    var roundIndex = 1
    var bestSchedule = initialSchedule
    var bestScore: Score = score(initialSchedule)

    while (roundIndex < initialSchedule.size) {
      println(s"Optimizing round [${roundIndex + 1}]...")

      val seenRounds = mutable.HashSet[Round]()
      queue.enqueue(bestSchedule)

      while (queue.nonEmpty) {
        println(s"Queue size [${queue.size}]")
        val currentSchedule = queue.dequeue()
        val currentRound = currentSchedule(roundIndex)

        expand(currentRound)
          .flatMap { newRound =>
            if (seenRounds.contains(newRound)) List()
            else {
              seenRounds.add(newRound)
              List(currentSchedule.updated(roundIndex, newRound))
            }
          }
          .foreach { schedule =>
            val scored = score(schedule)
            if (scored > bestScore) {
              bestScore = scored
              bestSchedule = schedule
              queue.enqueue(schedule)
            }
          }
      }
      roundIndex += 1
    }

    (bestSchedule, bestScore)
  }

}
