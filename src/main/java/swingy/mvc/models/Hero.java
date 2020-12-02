package swingy.mvc.models;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.awt.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hero {
    @Pattern(regexp = "^[0-9A-Za-z]+", message = "Name should consist only of digits and letters")
    @Size(min = 3, max = 15, message = "Name must be from 3 to 15 symbols length")
    private String name;
    private String race;
    private int level;
    private int exp;
    private int attack;
    private int defense;
    private int maxHp;
    private int hp;
    @NotNull(message = "Position cant be null")
    private Point position = new Point(0, 0);
    @NotNull(message = "Position cant be null")
    private Point oldPosition = new Point(0, 0);
    private Artifact artifact = new Artifact(ArtifactType.NO_ARTIFACT, 0);

    public void move(int x, int y) {
        oldPosition.setLocation(position.x, position.y);
        position.setLocation(position.x + x, position.y + y);
    }

    public int getExpForLevelUp() {
        return (int) (level * 1000 + Math.pow(level - 1, 2) * 450);
    }

    public void setHP(int hp) {
        if (hp < 0) {
            this.hp = 0;
        } else {
            this.hp = Math.min(hp, getMaxHp());
        }
    }

    public int getMaxHp() {
        return ArtifactType.HELM == artifact.getArtifactType() ? this.maxHp + artifact.getValue() : this.maxHp;
    }

    public int getMaxHpWithoutArtifact() {
        return this.maxHp;
    }

    public int getTotalAttack() {
        return (artifact != null && ArtifactType.WEAPON == artifact.getArtifactType()) ? attack + artifact.getValue() : attack;
    }

    public int getTotalDefense() {
        return (artifact != null && ArtifactType.ARMOR == artifact.getArtifactType()) ? defense + artifact.getValue() : defense;
    }

    @Override
    public String toString() {
        return "\n Race: ".concat(race)
                .concat("\n Attack: ").concat(String.valueOf(getTotalAttack()))
                .concat("\n Defense: ").concat(String.valueOf(getTotalDefense()))
                .concat("\n HP: ").concat(String.valueOf(getHp()));
    }
}