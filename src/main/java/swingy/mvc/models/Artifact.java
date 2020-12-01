package swingy.mvc.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Artifact {
    private final String type;
    private final int value;
}
