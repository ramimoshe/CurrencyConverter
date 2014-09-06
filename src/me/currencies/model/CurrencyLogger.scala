
package me.currencies.model

import org.apache.log4j.{ConsoleAppender, Logger, PatternLayout}

/**
 * Logger class that uses Log4J functionality
 */
class CurrencyLogger() {

  //initiates the logger
  private val application = Logger.getLogger("CurrencyLogger")

  // create a pattern for the logger
  private val pattern = new PatternLayout("%p [%t] %c (%F:%L) - %m%n")

  //Add appender to logger
  application.addAppender(new ConsoleAppender(pattern, ConsoleAppender.SYSTEM_OUT))


  /**
   * gets the logger instance
   * @return logger instance
   */
  def getCurrencyLogger = {
    application
  }
}