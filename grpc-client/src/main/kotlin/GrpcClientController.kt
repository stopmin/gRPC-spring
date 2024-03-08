package grpcclient.example

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GrpcClientController(
    private val grpcClientService: GrpcClientService
) {
    @GetMapping("/test")
    fun printMessage(@RequestParam message:String): String {
        return grpcClientService.sendMessage(message)
    }
}
