package com.edu.web.spacecatsmarket.web.exception;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Jacksonized
public record ParamsViolationDetails(
        String fieldName,
        String reason
) {}