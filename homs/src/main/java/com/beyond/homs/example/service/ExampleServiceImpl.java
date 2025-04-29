package com.beyond.homs.example.service;

import com.beyond.homs.example.entity.Example;
import org.springframework.stereotype.Service;

@Service
public class ExampleServiceImpl implements ExampleService {
    @Override
    public Example findById(Long id) {
        return null;
    }

    @Override
    public Example save(Example example) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
