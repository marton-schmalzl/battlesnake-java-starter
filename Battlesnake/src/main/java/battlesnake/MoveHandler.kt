package battlesnake

import battlesnake.model.Coord
import battlesnake.model.Direction
import battlesnake.model.GameState
import battlesnake.model.Move
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.IOException
import java.util.Arrays
import java.util.Random

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
            val objectMapper = ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            val state = objectMapper.reader().readValue(input.body, GameState::class.java)
            val head = state.you.head
            val body = state.you.body

            val possibleMoves = mutableListOf(Direction.up, Direction.down, Direction.left, Direction.right)

            avoidMyNeck(head, body, possibleMoves)
            context.logger.log("Moves: $possibleMoves")
            val rand = Random()
            val direction = possibleMoves[rand.nextInt(possibleMoves.size)]
            context.logger.log("Direction: $direction")
            val move = Move(direction.toString())
            return response
                .withBody(objectMapper.writer().writeValueAsString(move))
                .withStatusCode(200)
        } catch (e: IOException) {
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
