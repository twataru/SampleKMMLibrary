import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual class ApiClient actual constructor() {
    actual val client: HttpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(json = kotlinx.serialization.json.Json {
                isLenient = false
                ignoreUnknownKeys = true
                allowSpecialFloatingPointValues = true
                useArrayPolymorphism = false
            })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 1000
        }
    }

    actual  val dispatcher: CoroutineDispatcher = Dispatchers.Default
}