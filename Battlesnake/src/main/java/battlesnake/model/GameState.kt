package battlesnake.model

data class GameState(
    val game: Game,
    val turn: Int,
    val board: Board,
    val you: Battlesnake
)
