package gui

import scala.swing.{BoxPanel, Label, MainFrame, Orientation, Swing, TextArea, TextField}

class AppFrame(version: String) extends MainFrame {
  title = s"Tournament Scheduler $version"

  val numberOfRoundsField = new TextField("3")
  val availableTablesField = new TextField("3 4", 32)
  val availablePlayersField = new TextArea(16, 5)
  availablePlayersField.text = """Alice
                                 |Bob
                                 |Charlie
                                 |David
                                 |Eve
                                 |Ferguson
                                 |Gemma
                                 |Hilbert
                                 |Ivy
                                 |Jenny
                                 |Kimberly
                                 |Laurel
                                 |Miles""".stripMargin

  contents = new BoxPanel(Orientation.Vertical) {
    contents += new BoxPanel(Orientation.Horizontal) {
      contents += new Label("nr of rounds")
      contents += Swing.HStrut(5)
      contents += numberOfRoundsField
    }

    contents += new BoxPanel(Orientation.Horizontal) {
      contents += new Label("table sizes")
      contents += Swing.HStrut(5)
      contents += availableTablesField
    }

    contents += Swing.VStrut(5)
    contents += Swing.VGlue

    contents += new BoxPanel(Orientation.Horizontal) {
      contents += new Label("Players")
      contents += Swing.HStrut(5)
      contents += availablePlayersField
    }
  }
}
