## Tournament table scheduling

Given a number of rounds, list of allowed players at a table and list of players, creates a playing schedule where the amount of different opponents encountered is maximized.

#### Parameters File
See `example-tournament.txt` for an example

#### Running the program
There are several options
- compile the code yourself using sbt
- run the program using sbt: `sbt "run example-tournament.txt"`
- run the provided fat jar from the `bin` directory with java: `java -jar bin/tournament-scheduler-1.1.jar example-tournament.txt`.