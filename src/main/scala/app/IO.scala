package app

import java.io.{BufferedWriter, File, FileWriter}

import scala.io.Source
import scala.util.Try

object IO {

  def parseInputFile(path: String): Either[InvalidArguments, Arguments] = {
    Try(Source.fromFile(path).getLines.toList.filterNot(s => s.startsWith("#") || s.isEmpty))
      .toEither
      .left
      .map { e: Throwable => InvalidArguments(s"Unable to read arguments: [${e.getMessage}]") }
      .flatMap { lines: Seq[String] =>
        if (lines.length < 3) Left(InvalidArguments("Input file should contain at least 3 lines"))
        else {
          for {
            rounds <- Try(lines.head.toInt)
              .toEither
              .left
              .map(_ => InvalidArguments(s"[${lines.head}] must be an integer indicating the number of rounds"))

            tableSizes <- Try(lines(1)
              .split(" ")
              .map(_.toInt))
              .toEither
              .left
              .map(_ => InvalidArguments(s"[${lines(1)}] must be a space separated list of available table sizes"))

            players <- Right(lines.drop(2))
          } yield Arguments(rounds, tableSizes, players)
        }
      }
  }

  def writeOutputFile(path: String, contents: String): Unit = {
    val writer = new BufferedWriter(new FileWriter(new File(path)))
    writer.write(contents)
    writer.close()
  }

}
