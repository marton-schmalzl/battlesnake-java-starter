package battlesnake;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class GetInfoTest {
  @Test
  public void successfulResponse() {
    /*GetInfoHandler getInfo = new GetInfoHandler();
    APIGatewayProxyResponseEvent result = getInfo.handleRequest(null, null);
    assertEquals(200, result.getStatusCode().intValue());
    assertEquals("application/json", result.getHeaders().get("Content-Type"));
    String content = result.getBody();
    assertNotNull(content);
    assertTrue(content.contains("\"author\""));
    assertTrue(content.contains("\"color\""));*/
  }
}
