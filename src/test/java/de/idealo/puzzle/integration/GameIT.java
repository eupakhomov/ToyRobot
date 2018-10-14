package de.idealo.puzzle.integration;

import de.idealo.puzzle.business.GameService;
import de.idealo.puzzle.domain.Direction;
import de.idealo.puzzle.domain.Report;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for main paths.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class GameIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Test
    public void createGame() throws Exception {
        String id = UUID.randomUUID().toString();

        given(gameService.createGame())
                .willReturn(id);

        mockMvc.perform(post("/api/games")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\": \"" + id + "\"}"))
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void invokeAction() throws Exception {
        String id = UUID.randomUUID().toString();

        mockMvc.perform(post("/api/games/" + id + "/robot/actions/move/invoke")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"resultType\":\"void\"}"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void invokeActionReport() throws Exception {
        String id = UUID.randomUUID().toString();
        Report report = new Report(0, 0, Direction.NORTH);
        given(gameService.getReport(id))
                .willReturn(report);

        mockMvc.perform(post("/api/games/" + id + "/robot/actions/report/invoke")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"resultType\":\"object\",\"result\":{\"x\":0,\"y\":0,\"facing\":\"NORTH\"}}"))
                .andDo(MockMvcResultHandlers.print());
    }
}
