package controllers

import com.scalext.frontend.grid.Column
import com.scalext.frontend.grid.{Panel => GridPanel}

import actors.LiveActor
import actors.UpdateStatus
import akka.actor.actorRef2Scala
import play.api.libs.json.JsValue
import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.mvc.WebSocket

object Application extends Controller {

  def dashboard = Action {

    LiveActor.actor ! UpdateStatus("User joined..")

    Ok(views.html.dashboard())
  }

  def index = Action {

    var gridPanel = new GridPanel {
      title = "Test Titel"
      columns ++= List(
        Column("Id"),
        Column("Artikel"))
    }

    Ok(gridPanel.toJson)
  }

  def live = WebSocket.async[JsValue] { request =>
    actors.LiveActor.join()
  }
}