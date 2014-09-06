
package me.currencies.model

import java.io._
import java.util.NoSuchElementException

import org.xml.sax.SAXParseException

import scala.collection.Map
import scala.io.Source.fromURL
import scala.xml.XML

/**
 * * <pre> Currency Manager - manage all currencies data and contains conversions functions <pre/>
 * @param logger logger
 * @param autoSyncData indicates whether to start sync data from server automatically or not
 * @param localFilePath full path or relative path to save currency data
 */
class CurrencyManager(logger: LogHelper, autoSyncData: Boolean, localFilePath: String) extends CurrencyController {
  //defining variables
  private final val url: String = "http://www.boi.org.il/currency.xml"
  private var currencies: Map[String, Currency] = Map[String, Currency]()
  private var lastUpdate: String = ""

  //update local currency file
  updateCurrencyXmlFile(url)

  //update local memory from local file
  updateLocalCurrencies()

  //starting new Thread to update local file
  new Thread(new Runnable {
    override def run(): Unit = {
      while (true) {
        Thread.sleep(5000)
        updateCurrencyXmlFile(url)
        updateLocalCurrencies()
      }
    }
  }).start()

  //---------------------
  //function Definition
  //---------------------

  //update local currency file
  /**
   * <pre> Update the local xml file from the url param <pre/>
   * @param url the url of the xml file
   */
  def updateCurrencyXmlFile(url: String): Unit = {

    if (!autoSyncData)
      return
    //get request
    try {
      logger.application.info("Sending get request to " + url)
      val result = fromURL(url).mkString
      XML.loadString(result)

      //overwrite Currencies file
      val writer = new PrintWriter(new File(localFilePath))
      writer.write(result)
      writer.close()
    } catch {
      case ex: FileNotFoundException =>
        logger.application.warn("External site not found")
      case ex: IOException =>
        logger.application.warn("IO Exception")
      case ex: SAXParseException =>
        logger.application.error("Bad XML recieved from romote server, Using Local XML file")
    }
  }

  /**
   * <pre> convert coins <pre/>
   * @param amount amount
   * @param from from currency name
   * @param to to currency name
   * @return the result of the conversion
   */
  override def convert(amount: Double, from: String, to: String): Double = try
    currencies(from).getUnit() * currencies(from).getRate() / currencies(to).getUnit() / currencies(to).getRate() * amount
  catch {
    case ex: NoSuchElementException => {
      logger.application.error("Bad Input")
      throw new IllegalArgumentException("Bad Input")
    }
  }

  /**
   * <pre> loads local xml file to memory (Map collection) <pre/>
   */
  def updateLocalCurrencies() = {
    logger.application.info("Update local memory for currencies")

    val xml = XML.loadFile("CURRENCIES.XML")
    val currenciesRaw = (xml \ "CURRENCY").toArray

    currencies = Map[String, Currency]()

    //adding NIS to Currency map (used since NIS is not in the map and all rates refer to NIS)
    currencies += ("NIS" -> new Currency("Shekel", 1, "NIS", "Israel", 1, 1))
    lastUpdate = (xml \ "LAST_UPDATE").text
    // loop on all records and insert the data to the currencies map variable
    for (item <- currenciesRaw) {
      val currency = new Currency(
        (item \ "NAME").text,
        (item \ "UNIT").text.toInt,
        (item \ "CURRENCYCODE").text,
        (item \ "COUNTRY").text,
        (item \ "RATE").text.toDouble,
        (item \ "CHANGE").text.toDouble
      )
      currencies += ((item \ "CURRENCYCODE").text -> currency)
    }
  }

  /**
   * get currencies details
   * @return currencies details
   */
  override def getCurrencies(): Map[String, Currency] = {
    currencies
  }

  /**
   * get currencies names list
   * @return list of currencies names
   */
  override def getCurrenciesNames(): Array[String] = {
    currencies.keys.toArray
  }

  /**
   * get the last update date of the model
   * @return last update date
   */
  override def getLastUpdate(): String = {
    lastUpdate
  }
}