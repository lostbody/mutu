package gr.aueb.cf.mutu.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InterestDto {
    private final long id;
    private final String name;

    public InterestDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "InterestDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
