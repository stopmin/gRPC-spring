package example.grpcserver.echo

import echo.EchoRequest
import echo.EchoResponse
import echo.EchoServiceGrpcKt
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class EchoService : EchoServiceGrpcKt.EchoServiceCoroutineImplBase() {
    override suspend fun echo(request: EchoRequest): EchoResponse {
        val response = EchoResponse.newBuilder().setMessage(request.message + "가 도착했지롱").build()
        println(request.message)
        return response
    }
}


