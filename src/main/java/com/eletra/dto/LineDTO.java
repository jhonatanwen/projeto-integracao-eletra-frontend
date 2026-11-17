package com.eletra.dto;

public class LineDTO {
    private final Integer id;
    private final String name;

    public LineDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() { return id; }

    public String getName() { return name; }
}
