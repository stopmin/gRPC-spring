# gRPC SpringBoot Test
> 해당 레포는 gRPC를 Spring 프레임워크 상에서 활용해보기 위해서 테스트 목적으로 작성한 레포이니,
>  application.yml은 본인의 fit에 맞추어 테스트해보시고 참고만 하시길

[gRPC-echosystem 라이브러리](https://github.com/grpc-ecosystem/grpc-spring)를 활용한 테스트입니다.
더 자세한 예제는 아래 레포의 document를 참고하세요.

```kotlin
dependencies {
  compile 'net.devh:grpc-client-spring-boot-starter:3.0.0.RELEASE'
}
```

## gRPC
- gRPC는 Google이 개발한 고성능, 범용 RPC(원격 프로시저 호출) 프레임워크이다.
- gRPC는 클라이언트와 서버 사이의 통신을 위해 **HTTP/2**를 기반으로 하며, 프로토콜 버퍼(Protocol Buffers)라는 효율적인 인터페이스 정의 언어(IDL)를 사용하여 API를 정의한다.
- 이를 통해 언어 독립적인 방식으로 서비스를 정의하고, 데이터를 직렬화하여 네트워크를 통해 전송할 수 있다.
- gRPC는 멀티플렉싱, 흐름 제어, 스트림 우선 순위 설정 등 HTTP/2의 주요 기능을 활용해 높은 성능을 제공한다.

## Websocket과는 무엇이 다른가?
- 기반 프로토콜: gRPC는 HTTP/2를 기반으로 하며, WebSocket은 HTTP를 업그레이드한 독립적인 프로토콜이다.
- 용도
  - WebSocket은 양방향, 지속적인 연결을 위해 설계되었으며, 주로 실시간 통신에 사용된다.
  - gRPC는 원격 프로시저 호출에 초점을 맞추고 있으며, 클라이언트-서버 간의 구조화된 데이터를 효율적으로 전송하는 데 유용하다.
- 스트리밍: gRPC는 서버 스트리밍, 클라이언트 스트리밍, 양방향 스트리밍을 지원하여 다양한 통신 패턴을 한다. WebSocket은 기본적으로 양방향 통신을 지원한다.
- 메시지 포맷: gRPC는 프로토콜 버퍼를 사용하여 데이터를 직렬화한다. 이는 매우 효율적이지만 사용을 위해서는 스키마 정의가 필요하다. 반면, WebSocket은 텍스트(예: JSON) 또는 바이너리 데이터를 전송할 수 있으며, 형식에 대한 제한이 없다.

***

### ⭐️ 부록) 서버 스트리밍과 코루틴
**서버 스트리밍**
- 서버 스트리밍은 클라이언트가 서버에 요청을 보낸 후, 서버로부터 여러 개의 메시지를 시간 경과에 따라 받는 통신 방식이다.
- 이는 데이터를 배치로 빠르게 전송하거나, 데이터가 준비되는 대로 점진적으로 클라이언트에게 제공할 때 유용하다.
**코루틴**
- 코루틴은 비동기 프로그래밍을 단순화하는 프로그래밍 구조로, 특히 Kotlin과 같은 언어에서 많이 사용된다.
- 서버 스트리밍과 코루틴을 함께 사용하면, 서버로부터 메시지 스트림을 비동기적으로 처리하면서도 코드를 마치 동기 코드처럼 간결하고 이해하기 쉽게 작성할 수 있다.
- 예를 들어, 코루틴을 사용하면 서버로부터 메시지 스트림을 받는 동안 다른 작업을 수행하거나, 스트림 처리가 완료될 때까지 기다림 없이 즉시 다음 코드로 넘어갈 수 있다.
- 이는 I/O 바운드 작업, 특히 네트워크를 통한 데이터 통신에서 코드의 비동기 실행을 용이하게 하며, 서버 스트리밍을 통한 효율적인 데이터 처리에 큰 장점을 제공한다.

이번 프로젝트의 경우 위치를 연결이 수립되면 [data stream](https://stopmin.tistory.com/entry/%EC%8A%A4%ED%8A%B8%EB%A6%AC%EB%B0%8D-%EB%8D%B0%EC%9D%B4%ED%84%B0Data-Stream)으로 받아서 처리해야하기 때문에 서버스트리밍 방식을 사용하였으며, kotlin에서는 앞서말한 coroutine이라는 것이 존재하여 이를 편하게 다룰 수 있다.

***

### 👀 어느 목적인가?
- 이번 테스트의 목적은 Kinesis Stream 데이터를 수신하기 위한 기반을 다지기 위한 학습이었다.
- 이후 Stream 데이터를 수집될 때마다 비동기적으로 처리하며 수집된 데이터를 gRPC 연결이 수립된 client로 전송하는 코드를 작성하였다.
- 이에 관련된 코드도 [지민의 tistory 게시물](https://stopmin.tistory.com/)에서 간단하게 확인할 수 있다.
