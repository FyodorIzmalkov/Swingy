package swingy.mvc.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Artifact {
    private final ArtifactType artifactType;
    private final int value;
}
