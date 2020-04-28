package com.avira.iot.interview.common.controller;

import com.avira.iot.interview.common.types.PingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class RestStatusController {

    @GetMapping("/ping")
    public Mono<PingResponse> ping() {
        return Mono.just(new PingResponse("OK"));
    }
}
