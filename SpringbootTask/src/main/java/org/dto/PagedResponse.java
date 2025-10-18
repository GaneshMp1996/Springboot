package org.dto;

import lombok.Data;

@Data
public class PagedResponse {
    private long totalElements;
    private int totalPages;
    private int page;
    private int size;
    private java.util.List<T> content;
}
