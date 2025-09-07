package com.sample.devicemanagement.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class Pagination {

    int page;
    int size;
    int totalPages;
    int totalResults;

}
