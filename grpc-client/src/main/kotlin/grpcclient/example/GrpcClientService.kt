package grpcclient.example


import io.grpc.ManagedChannelBuilder
import io.grpc.stub.ClientResponseObserver
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Service
import truckerlocation.TruckerLocationRequest
import truckerlocation.TruckerLocationGrpcServiceGrpc
import truckerlocation.TruckerLocationGrpcServiceGrpc.TruckerLocationGrpcServiceStub
import truckerlocation.TruckerLocationGrpcServiceGrpcKt

@Service
class GrpcClientService {
    val channel = ManagedChannelBuilder.forAddress("localhost", 8980).usePlaintext().build()
    val client = TruckerLocationClient(
        channel = ManagedChannelBuilder.forAddress("localhost", 8980).usePlaintext().build()
    )

    @GrpcClient("test")
    private val stub = TruckerLocationGrpcServiceGrpcKt.TruckerLocationGrpcServiceCoroutineStub(channel)

    suspend fun getTruckerLocation(orderId: Long, managerId: Long): String {
        val request = TruckerLocationRequest.newBuilder()
            .setOrderId(orderId)
            .setManagerId(managerId)
            .build()
        client.getTruckerLocation(orderId, managerId)

        stub.getTruckerLocations(request).collect { truckerlocation ->
            println("Result: ${truckerlocation.truckerId}")
            println("longitude: ${truckerlocation.point.longitude}")
            println("latitude: ${truckerlocation.point.latitude}\n")

        }
        return "HI"
    }

//    val streamObserver: StreamObserver<truckerlocation.TruckerLocationReply> =
//        object : StreamObserver<truckerlocation.TruckerLocationReply> {
//            override fun onNext(value: truckerlocation.TruckerLocationReply?) {
//                println("onNext: ${value?.truckerId}")
//                println("onNext: ${value?.point?.longitude}")
//                println("onNext: ${value?.point?.latitude}")
//            }
//
//            override fun onError(t: Throwable?) {
//                println("onError: ${t?.message}")
//            }
//
//            override fun onCompleted() {
//                println("onCompleted")
//            }
//        }
}
