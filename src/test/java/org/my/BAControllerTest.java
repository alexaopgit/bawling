package org.my;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.text.StringContainsInOrder.stringContainsInOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class BAControllerTest {

    private MockMvc mvc;

    /**
     * JSON serializer (jackson)
     */
    protected ObjectMapper mapper = new ObjectMapper();

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        mvc = webAppContextSetup(webApplicationContext).dispatchOptions(true).build();
    }

    @Test
    public void testGetScore() throws Exception {
        int[] knockedPins = new int[]{1, 4, 4, 5, 6, 4, 5, 5, 10, 0, 0, 1, 7, 3, 6, 4, 10, 0, 2, 8, 6};
        int[] score = new int[]{5, 14, 29, 49, 60, 61, 77, 97, 117, 133};
        mvc.perform(
                post("/bowling/score")
                        .content(mapper.writeValueAsString(knockedPins))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(stringContainsInOrder(
                        Arrays.stream(score).boxed().map(String::valueOf).collect(Collectors.toList())
                )));
    }

    @Test
    public void testGetScoreWithError() throws Exception {
        int[] knockedPins = new int[]{1, 4, 4, 8, 6, 4, 5, 5, 10, 0, 0, 1, 7, 3, 6, 4, 10, 0, 2, 8, 6};
        mvc.perform(
                post("/bowling/score")
                        .content(mapper.writeValueAsString(knockedPins))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Sum for frame 2 has value bigger then 10"));
    }
}
