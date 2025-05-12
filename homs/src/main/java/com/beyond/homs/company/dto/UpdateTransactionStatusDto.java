package com.beyond.homs.company.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateTransactionStatusDto(@NotBlank Boolean isApprovedStatus) { }

