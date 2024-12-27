package cafe.model

case class Ingredient(name:String, image:String)

val ingredients: List[Ingredient] = List(
  Ingredient("espresso", "cafe.media/espresso.png"),
  Ingredient("milk", "cafe.media/milk.png")
)