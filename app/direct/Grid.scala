package direct

import com.scalext.annotations.Remotable

case class GridRow(name: String, info: String)

@Remotable(name = "Scalext.example.Grid")
class Grid {

  @Remotable
  def getGrid(): List[GridRow] = {
    List(GridRow("Name 1", "Info 1"),
      GridRow("Name 2", "Info 2"),
      GridRow("Name 3", "Info 3"),
      GridRow("Name 4", "Info 4"))
  }

}