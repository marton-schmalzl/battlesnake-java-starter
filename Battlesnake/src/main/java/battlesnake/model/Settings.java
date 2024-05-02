package battlesnake.model;

public record Settings(
        long foodSpawnChance,
        long minimumFood,
        long hazardDamagePerTurn,
        Royale royale,
        Squad squad
) {
}
