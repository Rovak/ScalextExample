package controllers

import com.scalext.frontend.grid.Column
import com.scalext.frontend.grid.{ Panel => GridPanel }
import actors.LiveActor
import actors.UpdateStatus
import akka.actor.actorRef2Scala
import play.api.libs.json.JsValue
import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.mvc.WebSocket
import com.google.gson.GsonBuilder
import com.google.gson.FieldNamingPolicy

object Live extends Controller {

  val gson = new GsonBuilder()
    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    .enableComplexMapKeySerialization()
    .create()

  def dashboard = Action {

    LiveActor.actor ! UpdateStatus("User joined..")

    Ok(views.html.dashboard())
  }

  def live = WebSocket.async[JsValue] { request =>
    actors.LiveActor.join()
  }
}