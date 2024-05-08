package battlesnake

import battlesnake.model.Coord
import battlesnake.model.Direction
import battlesnake.model.GameState
import battlesnake.model.Move
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.beust.klaxon.Klaxon
import kotlin.random.Random

/**
 * Handler for requests to Lambda function.
 */
class MoveHandler : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    override fun handleRequest(input: APIGatewayProxyRequestEvent, context: Context): APIGatewayProxyResponseEvent {
        val headers: MutableMap<String, String> = HashMap()
        headers["Content-Type"] = "application/json"
        headers["X-Custom-Header"] = "application/json"
        val response = APIGatewayProxyResponseEvent()
            .withHeaders(headers)
        try {
            val state = Klaxon().parse<GameState>(input.body) ?: throw RuntimeException("parse error")

            val possibleMoves = mutableListOf(Direction.up, Direction.down, Direction.left, Direction.right)

            avoidMyNeck(state.you.head, state.you.body, possibleMoves)
            context.logger.log("Moves: $possibleMoves")
            val direction = possibleMoves[Random.nextInt(possibleMoves.size)]
            context.logger.log("Direction: $direction")
            val move = Move(direction.toString())
            return response
                .withBody(Klaxon().toJsonString(move))
                .withStatusCode(200)
        } catch (e: Exception) {
            context.logger.log(e.message)
            return response
                .withBody("{}")
                .withStatusCode(500)
        }
    }

    fun avoidMyNeck(head: Coord, body: Array<Coord>, possibleMoves: MutableList<Direction>) {
        val neck = body[1]

        if (neck.x < head.x) {
            possibleMoves.remove(Direction.left)
        } else if (neck.x > head.x) {
            possibleMoves.remove(Direction.right)
        } else if (neck.y < head.y) {
            possibleMoves.remove(Direction.down)
        } else if (neck.y > head.y) {
            possibleMoves.remove(Direction.up)
        }
    }
}
