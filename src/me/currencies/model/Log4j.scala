/**
 * Created by user on 24/08/2014.
 */

package me.currencies.model

import org.apache.log4j.{ConsoleAppender, Logger, PatternLayout}


class LogHelper {

  val application = Logger.getLogger("CurrencyLogger")
  var pattern = new PatternLayout("%p [%t] %c (%F:%L) - %m%n")
  application.addAppender(new ConsoleAppender(pattern, ConsoleAppender.SYSTEM_OUT))

}