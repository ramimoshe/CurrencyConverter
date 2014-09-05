package me.currencies.model

case class BadInputException(msg: String) extends Exception(msg)