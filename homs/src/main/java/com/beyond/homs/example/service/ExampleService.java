package com.beyond.homs.example.service;

import com.beyond.homs.example.entity.Example;

public interface ExampleService {
    Example findById(Long id);

    Example save(Example example);

    void delete(Long id);
}
