package battlesnake.model;

public record Board(int height,
             int width,
             Coord[] food,
             Battlesnake[] snakes,
             Coord[] hazards) {
}
