package com.eletra.dto;

public class CategoryDTO {
    private final Integer id;
    private final String name;
    private final Integer lineId;

    public CategoryDTO(Integer id, String name, Integer lineId) {
        this.id = id;
        this.name = name;
        this.lineId = lineId;
    }

    public Integer getId() { return id; }

    public String getName() { return name; }
}
