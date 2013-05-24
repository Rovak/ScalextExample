package controllers

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.io.Source

import org.apache.commons.lang3.StringEscapeUtils.escapeHtml4

import com.scalext.annotations.Remotable

import actors.LiveActor
import actors.Session
import actors.SessionList
import actors.UpdateStatus
import akka.actor.actorRef2Scala
import akka.pattern.ask
import akka.util.Timeout
import play.api.libs.concurrent.Execution.Implicits.defaultContext

@Remotable(name = "TerminalCtrl")
class Terminal {

  @Remotable
  def execute(consoleCmd: String): String = {
    val command = escapeHtml4(consoleCmd)
    try {
      val commands = command.split(" ").toList
      commands(0) match {
        case "time" => "today"
        case "replace" => commands.slice(3, commands.size).mkString(" ").replace(commands(1), commands(2))
        case "wget" => Source.fromURL("http://" + commands(1)).mkString
        case "img" => s"<img src='${commands(1)}'>"
        case "broadcast" =>
          LiveActor.actor ! UpdateStatus(commands.slice(1, commands.size).mkString(" "))
          "Broadcast: " + commands.slice(1, commands.size).mkString(" ")
        case "sessions" =>
          implicit val timeout = Timeout(2 second)
          var result = ""
          var req = (LiveActor.actor ? "sessions") map {
            case SessionList(sessions) =>
              result = sessions.foldLeft("") {
                case (str, Session(id, channel, enum)) => {
                  s"$str<br>$id"
                }
              }
          }

          Await.result(req, 10 seconds)
          result.substring(4)
      }
    } catch {
      case e: Exception => e.getMessage
    }
  }
}