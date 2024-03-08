package example.grpcserver

import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.chb.examples.lib.HelloReply
import org.chb.examples.lib.HelloRequest
import org.chb.examples.lib.SimpleGrpc

@GrpcService
class GrpcServerService : SimpleGrpc.SimpleImplBase() {
    override fun sayHello(request: HelloRequest, responseObserver: StreamObserver<HelloReply>) {
        val reply = HelloReply.newBuilder().setMessage(request.name+"이 도착했습니닷").build()
        println(request.name)
        responseObserver.onNext(reply)
        responseObserver.onCompleted()
    }
}

