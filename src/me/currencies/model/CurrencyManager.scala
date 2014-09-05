/**
 * Created by user on 23/08/2014.
 */

package me.currencies.model

//imports
import java.io._
import java.util.NoSuchElementException

import org.xml.sax.SAXParseException

import scala.collection.Map
import scala.io.Source.fromURL
import scala.xml.XML;

class CurrencyManager(logger:LogHelper, autoSyncData: Boolean, localFilePath: String) extends CurrencyController {
  //defining variables
  private final val url: String = "http://www.boi.org.il/currency.xml"
  private var currencies: Map[String, Currency] = Map[String, Currency]()
  private var lastUpdate: String = ""

  //adding NIS to Currency map
  currencies += (("NIS") -> new Currency("Shekel", 1, "NIS", "ISR", 1, 1));


  //update local currency file
  updateCurrencyXmlFile(url);

  //update local memory from local file
  updateLocalCurrencies();

  //starting new Thread to update local file
  new Thread(new Runnable {
    override def run(): Unit = {
      while (true) {
        Thread.sleep(5000);
        updateCurrencyXmlFile(url);
        updateLocalCurrencies();
      }
    }
  }).start();

  //
  //function Defenition
  //

  //update local currency file
  def updateCurrencyXmlFile(url: String): Unit = {

    if (autoSyncData == false)
      return
    //get request
    try {
      logger.application.info("Sending get request to " + url)
      val result = fromURL(url).mkString;

      //overwrite Currencies file
      val writer = new PrintWriter(new File(localFilePath))
      writer.write(result)
      writer.close()
    } catch {
      case ex: FileNotFoundException => {
        logger.application.warn("External site not found")
      }
      case ex: IOException => {
        logger.application.warn("IO Exception")
      }
      case ex: SAXParseException => {
        logger.application.error("Bad Parsing")
      }
    }
  }

  //convert coins
  def convert(amount: Double, from: String, to: String): Double = {
    try {
      currencies(from).m_unit * currencies(from).m_rate / currencies(to).m_unit / currencies(to).m_rate * amount;
    } catch {
      case ex: NoSuchElementException => {
        logger.application.error("Bad Input")
        throw new BadInputException("Bad Input");
      }
    }
  }

  //loads local xml file to memory (Map collection)
  def updateLocalCurrencies() = {
    logger.application.info("Update local memory for currencies");

    val xml = XML.loadFile("CURRENCIES.XML");
    val currenciesRaw = (xml \ "CURRENCY").toArray

    currencies = Map[String, Currency]()

    lastUpdate = (xml \ "LAST_UPDATE").text;

    for (item <- currenciesRaw) {
      var currency = new Currency(
        ((item) \ "NAME").text,
        ((item) \ "UNIT").text.toInt,
        ((item) \ "CURRENCYCODE").text,
        ((item) \ "COUNTRY").text,
        ((item) \ "RATE").text.toDouble,
        ((item) \ "CHANGE").text.toDouble
      )
      currencies += (((item \ "CURRENCYCODE").text) -> currency);
    }
  }

  def getCurrencies(): Map[String, Currency] = {
    currencies;
  }

  def getCurrenciesNames(): Array[String] = {
    currencies.keys.toArray;
  }

  def getLastUpdate() : String = {
    lastUpdate
  }

}


