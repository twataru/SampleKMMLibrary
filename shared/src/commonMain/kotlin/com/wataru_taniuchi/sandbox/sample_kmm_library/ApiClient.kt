import io.ktor.client.*
import kotlinx.coroutines.CoroutineDispatcher

expect class ApiClient() {
    val client: HttpClient
    val dispatcher: CoroutineDispatcher
}