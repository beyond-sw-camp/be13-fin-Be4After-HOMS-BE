package com.beyond.homs.example.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.example.service.ExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExampleControllerImpl implements ExampleController {
    private final ExampleService exampleService;


    @Override
    @GetMapping("/example")
    public ResponseEntity<ResponseDto<String>> example() {
        return ResponseEntity.ok(
                new ResponseDto<>(
                        200,
                        "성공",
                        "Example"
                )
        );
    }
}
