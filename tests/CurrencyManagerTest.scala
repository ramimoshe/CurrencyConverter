import me.currencies.model._
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Currency Manager Tests
 */
class CurrencyManagerTest {

  @Test
  def convert_UsdToEur_Success() {

    val expectedResult = 3.8653660628148776
    val logger: CurrencyLogger = new CurrencyLogger
    val cm: CurrencyController = new CurrencyManager(logger, false, "CURRENCIES_TEST.xml")
    val result: Double = cm.convert(5, "USD", "EUR")

    assertEquals(expectedResult, result, 0)
  }

  @Test(expected = classOf[IllegalArgumentException])
  def convert_UsdToXXX_ThrowBadInputException() {

    val logger: CurrencyLogger = new CurrencyLogger
    val cm: CurrencyController = new CurrencyManager(logger, false, "CURRENCIES_TEST.xml")
    val result: Double = cm.convert(5, "USD", "XXX")

  }
}