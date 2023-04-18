import com.wataru_taniuchi.sandbox.sample_kmm_library.Prefecture
import com.wataru_taniuchi.sandbox.sample_kmm_library.WeatherClient
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertNotNull

class WeatherClientTest {
    @Test
    fun testGetWeather() {
        val client = WeatherClient()
        runBlocking {
            val response = client.get(Prefecture.TOKYO)
            assertNotNull(response)
        }
    }
}