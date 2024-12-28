package cafe.model

case class Ingredient(name:String, image:String)

val ingredients: List[Ingredient] = List(
  Ingredient("espresso", "cafe.media/espresso.png"),
  Ingredient("milk", "cafe.media/milk.png"),
  Ingredient("cup", "cafe.media/cupone.png"),
  Ingredient("caramelsyrup", "cafe.media/caramelsyrup.png"),
  Ingredient("chocolatesyrup", "cafe.media/chocolatesyrup.png"),
  Ingredient("croissant", "cafe.media/croissant.png"),
  Ingredient("glass", "cafe.media/glass.png"),
  Ingredient("ice", "cafe.media/ice.png"),
  Ingredient("matchapowder", "cafe.media/matchapowder.png"),
  Ingredient("pistachiocake", "cafe.media/pistachiocake.png"),
  Ingredient("plate", "cafe.media/plateone.png"),
  Ingredient("strawberryshortcake", "cafe.media/strawberryshortcake.png"),
  Ingredient("tiramisu", "cafe.media/tiramisu.png"),
  Ingredient("whippedcream", "cafe.media/whippedcream.png"),
)