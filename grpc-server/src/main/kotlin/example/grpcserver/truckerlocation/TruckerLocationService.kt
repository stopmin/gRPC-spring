package example.grpcserver.truckerlocation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import net.devh.boot.grpc.server.service.GrpcService
import truckerlocation.TruckerLocationGrpcServiceGrpcKt
import truckerlocation.TruckerLocationReply
import truckerlocation.TruckerLocationRequest
import truckerlocation.point

@GrpcService
class TruckerLocationService : TruckerLocationGrpcServiceGrpcKt.TruckerLocationGrpcServiceCoroutineImplBase() {
    override fun getTruckerLocations(request: TruckerLocationRequest): Flow<TruckerLocationReply> {
        val truckerLocations = listOf(
            TruckerLocationReply.newBuilder()
                .setTruckerId(1)
                .setPoint(
                    point {
                        latitude = 37.7749
                        longitude = 122.4194
                    }
                ).build(),
            TruckerLocationReply.newBuilder()
                .setTruckerId(2)
                .setPoint(
                    point {
                        latitude = 37.7749
                        longitude = 122.234234
                    }
                ).build(),
            TruckerLocationReply.newBuilder()
                .setTruckerId(3)
                .setPoint(
                    point {
                        latitude = 37.7749
                        longitude = 213.4234234194
                    }
                )
                .build(),
        )

        println("Received request for trucker locations: $request")

        return truckerLocations.asFlow()
    }
}


