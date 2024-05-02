package battlesnake.model;

public record GameState(
        Game game,
        Integer turn,
        Board board,
        Battlesnake you
) {
}
