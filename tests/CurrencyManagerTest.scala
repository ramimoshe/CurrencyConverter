import me.currencies.model._
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by Administrator on 04/09/2014.
 */
class CurrencyManagerTest {
  @Test def convert_UsdToEur_Success {

    val expectedResult = 3.8653660628148776
    val logger: LogHelper = new LogHelper
    val cm: CurrencyController = new CurrencyManager(logger, false, "CURRENCIES_TEST.xml")
    val result: Double = cm.convert(5, "USD", "EUR")

    assertEquals(expectedResult, result, 0)
  }

  @Test(expected = classOf[BadInputException])
  def convert_UsdToXXX_ThrowBadInputException {

    val logger: LogHelper = new LogHelper
    val cm: CurrencyController = new CurrencyManager(logger, false, "CURRENCIES_TEST.xml")
    val result: Double = cm.convert(5, "USD", "XXX")

  }
}