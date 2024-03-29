package grpcclient.example


import net.devh.boot.grpc.client.inject.GrpcClient
import org.chb.examples.lib.HelloRequest
import org.chb.examples.lib.SimpleGrpc
import org.springframework.stereotype.Service

@Service
class GrpcClientService {
    @GrpcClient("test")
    private val simpleStub: SimpleGrpc.SimpleBlockingStub? = null

    fun sendMessage(message: String): String {
        println(message)

        val request = HelloRequest.newBuilder().setName(message).build()
        val response = simpleStub!!.sayHello(request)
        return response.message
    }
}
