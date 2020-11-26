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
    private Artifact artifact;

    public void move(int x, int y) {
        oldPosition.setLocation(position.x, position.y);
        position.setLocation(position.x + x, position.y + y);
    }

    public int getNeccesaryExp() {
        return (int) (level * 1000 + Math.pow(level - 1, 2) * 450);
    }

    public void setHP(int hp) {
        if (hp < 0) {
            this.hp = 0;
        } else if (hp > maxHp) {
            this.hp = maxHp;
        } else {
            this.hp = hp;
        }
    }

    public int getFinalAttack() {
        return (artifact != null && artifact.getType().equals("attack")) ? ((attack + artifact.getValue()) << 2) : attack << 2;
    }

    public int getFinalDefense() {
        return (artifact != null && artifact.getType().equals("defense")) ? defense + artifact.getValue() : defense;
    }

    @Override
    public String toString() {
        return "\n Race: ".concat(race)
                .concat("\n Attack: ").concat(String.valueOf(attack))
                .concat("\n Defense: ").concat(String.valueOf(defense))
                .concat("\n HP: ").concat(String.valueOf(hp));
    }
}