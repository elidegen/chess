package model

sealed trait MoveState:
  def isMoveLegal(): Boolean

case object FirstPawnMoveState extends MoveState:
  def isMoveLegal(): Boolean =
    //() yet to be implemented
    true

case object NormalPawnState extends MoveState:
  def isMoveLegal(): Boolean =
    //() yet to be implemented
    true

case object PawnPromotionState extends MoveState:
  def isMoveLegal(): Boolean =
    //() yet to be implemented
    true

//case object PawnCanTakeState extends MoveState:
  //def isMoveLegal(): Boolean =
    //() yet to be implemented
    //true

//case object KingCheckState extends MoveState:
  //def isMoveLegal(): Boolean =
    //() yet to be implemented
    //true
