import controller.controllerComponent.ControllerInterface
import model.rulesComponent.RulesInterface
import model.dataComponent.DataInterface

import controller.controllerComponent.controllerBaseImpl.Controller as BaseController
import model.rulesComponent.rulesBaseImpl.Rules as BaseRules
import model.dataComponent.dataBaseImpl.Data as BaseData // Beispielname

object ChessModule:
  given data: DataInterface = BaseData()

  given rules(using d: DataInterface): RulesInterface =
    BaseRules() // oder BaseRules()(using d) je nach Konstruktor

  given controller(using r: RulesInterface, d: DataInterface): ControllerInterface =
    BaseController() // oder BaseController(...)(using r,d)
