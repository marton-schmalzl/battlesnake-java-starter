package battlesnake;

import battlesnake.model.Coord;
import battlesnake.model.Direction;
import battlesnake.model.GameState;
import battlesnake.model.Move;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

import static battlesnake.model.Direction.*;


/**
 * Handler for requests to Lambda function.
 */
public class MoveHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
        try {
            ObjectMapper objectMapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            GameState state = objectMapper.reader().readValue(input.getBody(), GameState.class);
            var head = state.you().head();
            var body = state.you().body();

            var possibleMoves = new ArrayList<>(Arrays.asList(up, down, left, right));

            avoidMyNeck(head, body, possibleMoves);
            context.getLogger().log("Moves: " + possibleMoves);
            Random rand = new Random();
            Direction direction = possibleMoves.get(rand.nextInt(possibleMoves.size()));
            context.getLogger().log("Direction: " + direction.toString());
            Move move = new Move(direction.toString());
            return response
                    .withBody(objectMapper.writer().writeValueAsString(move))
                    .withStatusCode(200);
        } catch (IOException e) {
            context.getLogger().log(e.getMessage());
            return response
                    .withBody("{}")
                    .withStatusCode(500);
        }
    }

    public void avoidMyNeck(Coord head, Coord[] body, List<Direction> possibleMoves) {
        var neck = body[1];

        if (neck.x() < head.x()) {
            possibleMoves.remove(left);
        } else if (neck.x() > head.x()) {
            possibleMoves.remove(right);
        } else if (neck.y() < head.y()) {
            possibleMoves.remove(down);
        } else if (neck.y() > head.y()) {
            possibleMoves.remove(up);
        }
    }
}
