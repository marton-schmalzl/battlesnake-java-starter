package battlesnake.model;

public record Battlesnake(String id,
                   String name,
                   long health,
                   Coord[] body,
                   Coord head,
                   long length,
                   String latency,
                   String shout,
                   String squad) {
}
