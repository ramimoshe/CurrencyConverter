
package me.currencies.model

import org.apache.log4j.{ConsoleAppender, Logger, PatternLayout}

/**
 * Logger class that uses Log4J functionality
 */
class CurrencyLogger() {

  val application = Logger.getLogger("CurrencyLogger")
  var pattern = new PatternLayout("%p [%t] %c (%F:%L) - %m%n")
  application.addAppender(new ConsoleAppender(pattern, ConsoleAppender.SYSTEM_OUT))

}