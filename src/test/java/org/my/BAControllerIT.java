package org.my;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class BAControllerIT {

    @Value("${local.server.port}")
    private int port;

    private RestTemplate template;
    private UriComponentsBuilder builder;

    @Before
    public void setUp() throws Exception {
        builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/bowling/score");
        template = new TestRestTemplate();
    }

    @Test
    public void getScores() throws Exception {
        Integer[] knockedPins = new Integer[]{1, 4, 4, 5, 6, 4, 5, 5, 10, 0, 0, 1, 7, 3, 6, 4, 10, 0, 2, 8, 6};
        Integer[] scoreExpected = new Integer[]{5, 14, 29, 49, 60, 61, 77, 97, 117, 133};
        ResponseEntity<Integer[]> response = template.postForEntity(
                builder.build().toUri(), knockedPins, Integer[].class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(scoreExpected, response.getBody());
    }

    @Test
    public void getPage() throws Exception {

        ResponseEntity<String> response = template.getForEntity(
                UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/bowling").build().toUri(),
                String.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertArrayEquals(scoreExpected, response.getBody());
    }
}