package battlesnake.model;

public record Game(String id,
            Ruleset ruleset,
            long timeout) {
}
