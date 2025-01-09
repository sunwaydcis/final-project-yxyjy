package cafe.model

/**
 *
 * @param name name of the item
 * @param price price of the item
 * @param ingredients list of ingredients in the item
 * @param finalPic the image filepath of the final image of the item
 */
case class Item(name: String, price: Double, ingredients: List[String], finalPic: String)