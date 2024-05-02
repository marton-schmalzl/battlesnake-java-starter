package battlesnake.model;

public record Move(
        String move,
        String shout
) {
    public Move(String  move) {
        this(move, null);
    }
}
