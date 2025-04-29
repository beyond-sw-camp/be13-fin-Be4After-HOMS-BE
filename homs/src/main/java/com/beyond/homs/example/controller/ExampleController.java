package com.beyond.homs.example.controller;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Example API", description = "Example API 목록")
public interface ExampleController {
}
/**
 * create database homs;
 * CREATE USER `beyond`@`%` IDENTIFIED BY 'beyond';
 * GRANT ALL PRIVILEGES ON homs.* TO `beyond`@`%`;
 * FLUSH PRIVILEGES;
 */