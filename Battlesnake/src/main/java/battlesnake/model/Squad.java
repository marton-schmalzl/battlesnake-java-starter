package battlesnake.model;

public record Squad(boolean allowBodyCollisions,
             boolean sharedElimination,
             boolean sharedHealth,
             boolean sharedLength) {
}
