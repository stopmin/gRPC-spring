package example.grpcserver

import example.grpcserver.echo.EchoService
import example.grpcserver.echo.HelloGrpcServer
import io.grpc.Server
import io.grpc.ServerBuilder
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GrpcServerApplication

fun main(args: Array<String>) {
    runApplication<GrpcServerApplication>(*args)
    val server: Server = ServerBuilder.forPort(8980).addService(EchoService())
        .build()
    val truckerLocationServer = HelloGrpcServer(8980, server)
    truckerLocationServer.start()
    truckerLocationServer.blockUntilShutdown()
    truckerLocationServer.stop()
}
