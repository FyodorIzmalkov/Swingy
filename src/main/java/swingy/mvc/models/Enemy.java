package swingy.mvc.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Enemy {
    private int hp;
    private int attack;
    private int defense;
    private int imageNumber;
    private Point position;
}