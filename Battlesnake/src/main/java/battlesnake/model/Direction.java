package battlesnake.model;

public enum Direction {
    up("up"),
    down("down"),
    left("left"),
    right("right");
    private final String text;

    Direction(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
