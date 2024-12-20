package model

case class Customer(name: String, order: Order, satisfaction: satisfaction)

enum satisfaction:
  case happy, ok, unhappy
