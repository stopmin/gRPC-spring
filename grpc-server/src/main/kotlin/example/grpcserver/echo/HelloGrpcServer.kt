package example.grpcserver.echo

import io.grpc.Server
import io.grpc.ServerBuilder

class HelloGrpcServer(
    val port: Int,
    val server: Server = ServerBuilder.forPort(port).addService(EchoService())
        .build(),
) {
    fun start() {
        server.start()
        println("Server started, listening on $port")

        Runtime.getRuntime().addShutdownHook(
            Thread {
                println("*** shutting down gRPC server since JVM is shutting down")
                this@HelloGrpcServer.stop()
                println("*** server shut down")
            },
        )
    }

    fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }
}
