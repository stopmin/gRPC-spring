package grpcclient.example

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GrpcClientController(
    private val grpcClientService: GrpcClientService
) {
    @PostMapping("/test")
    suspend fun printMessage(): ResponseEntity<String> {
        val truckerLocation = grpcClientService.getTruckerLocation(1, 1)
        return ResponseEntity.ok(truckerLocation)
    }

    @GetMapping("/test")
    fun test(): ResponseEntity<String> {
        return ResponseEntity.ok("Hello")
    }
}
