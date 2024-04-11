package grpcclient.example

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import net.devh.boot.grpc.server.service.GrpcService
import truckerlocation.TruckerLocationGrpcServiceGrpcKt
import truckerlocation.TruckerLocationRequest
import java.io.Closeable
import java.util.concurrent.TimeUnit

class TruckerLocationClient(
    private val channel: ManagedChannel
) : Closeable {
    private val stub = TruckerLocationGrpcServiceGrpcKt.TruckerLocationGrpcServiceCoroutineStub(channel)

    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }

    suspend fun getTruckerLocation(
        orderId: Long, managerId: Long
    ) {
        println("*** GetTruckerLocation: orderId=${orderId}")
        println("*** GetTruckerLocation: managerId=${managerId}")

        val request = TruckerLocationRequest.newBuilder()
            .setOrderId(orderId)
            .setManagerId(managerId)
            .build()

        stub.getTruckerLocations(request).collect { truckerLocation ->
            println("Result: ${truckerLocation.truckerId}")
            println("longitude: ${truckerLocation.point.longitude}")
            println("latitude: ${truckerLocation.point.latitude}\n")
        }
    }
}

suspend fun main() {
    val client = TruckerLocationClient(
        channel =
        ManagedChannelBuilder.forAddress("localhost", 8980).usePlaintext().build()
    )

    client.getTruckerLocation(1L, 1L)
    client.close()
}
