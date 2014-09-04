import me.currencies.model.{CurrencyManager, CurrencyController, LogHelper}
import org.junit.Assert._
import org.junit.Assert.assertEquals
import me.currencies.model._
import scala.collection.JavaConversions
import org.junit.Test

/**
 * Created by Administrator on 04/09/2014.
 */
class CurrencyManagerTest {
  @Test def convert_UsdToEur_Success {

    val expectedResult = 3.8345430021195965;
    val logger: LogHelper = new LogHelper
    val cm: CurrencyController = new CurrencyManager(logger, false)
    val result: Double = cm.convert(5, "USD", "EUR")

    assertEquals(expectedResult, result, 0)
  }
}