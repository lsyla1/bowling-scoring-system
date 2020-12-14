package com.jobsity.bowling.service;

import com.jobsity.bowling.exception.BowlingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public class BowlingSystemServiceIntegrationTest {

    @Autowired
    private BowlingSystemService bowlingSystemService;

    @Test
    void testDefaultGame () throws URISyntaxException, IOException, BowlingException {
        String filePath = "game1.txt";
        String result = bowlingSystemService.processGame(Paths.get(ClassLoader.getSystemResource(filePath).toURI()).toString());

        String expected = "Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10\n" +
                "Jeff\n" +
                "Pinfalls\t\tX\t7\t/\t9\t0\t\tX\t0\t8\t8\t/\tF\t6\t\tX\t\tX\tX\t8\t1\n" +
                "Score\t\t20\t\t39\t\t48\t\t66\t\t74\t\t84\t\t90\t\t120\t\t148\t\t167\n" +
                "John\n" +
                "Pinfalls\t3\t/\t6\t3\t\tX\t8\t1\t\tX\t\tX\t9\t0\t7\t/\t4\t4\tX\t9\t0\n" +
                "Score\t\t16\t\t25\t\t44\t\t53\t\t82\t\t101\t\t110\t\t124\t\t132\t\t151\n";
        assertEquals(result, expected);
    }

    @Test
    void testPerfectGame () throws URISyntaxException, IOException, BowlingException {
        String filePath = "game2.txt";
        String result = bowlingSystemService.processGame(Paths.get(ClassLoader.getSystemResource(filePath).toURI()).toString());

        String expected = "Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10\n" +
                "Carl\n" +
                "Pinfalls\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\tX\tX\tX\n" +
                "Score\t\t30\t\t60\t\t90\t\t120\t\t150\t\t180\t\t210\t\t240\t\t270\t\t300\n";
        assertEquals(result, expected);
    }

    @Test
    void testZeroGame () throws URISyntaxException, IOException, BowlingException {
        String filePath = "game3.txt";
        String result = bowlingSystemService.processGame(Paths.get(ClassLoader.getSystemResource(filePath).toURI()).toString());

        String expected = "Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10\n" +
                "Carl\n" +
                "Pinfalls\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\n" +
                "Score\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\n";
        assertEquals(result, expected);
    }

    @Test
    void testFoulGame () throws URISyntaxException, IOException, BowlingException {
        String filePath = "game4.txt";
        String result = bowlingSystemService.processGame(Paths.get(ClassLoader.getSystemResource(filePath).toURI()).toString());

        String expected = "Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10\n" +
                "Andres\n" +
                "Pinfalls\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\n" +
                "Score\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\n";
        assertEquals(result, expected);
    }

    @Test
    void testTwoPlayersGame () throws URISyntaxException, IOException, BowlingException {
        String filePath = "game5.txt";
        String result = bowlingSystemService.processGame(Paths.get(ClassLoader.getSystemResource(filePath).toURI()).toString());

        String expected = "Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10\n" +
                "Andres\n" +
                "Pinfalls\t\tX\t7\t/\t9\t0\t\tX\t0\t8\t8\t/\tF\t6\t\tX\t\tX\tX\t8\t1\n" +
                "Score\t\t20\t\t39\t\t48\t\t66\t\t74\t\t84\t\t90\t\t120\t\t148\t\t167\n" +
                "Jenn\n" +
                "Pinfalls\t3\t5\t\tX\t7\t/\tF\t5\t\tX\t4\t/\t3\t2\t\tX\t5\t2\t4\t4\n" +
                "Score\t\t8\t\t28\t\t38\t\t43\t\t63\t\t76\t\t81\t\t98\t\t105\t\t113\n";
        assertEquals(result, expected);
    }

    @Test
    void testThreePlayersGame () throws URISyntaxException, IOException, BowlingException {
        String filePath = "game6.txt";
        String result = bowlingSystemService.processGame(Paths.get(ClassLoader.getSystemResource(filePath).toURI()).toString());

        String expected = "Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10\n" +
                "Andres\n" +
                "Pinfalls\t\tX\t3\t/\tF\t5\t0\t9\t\tX\t\tX\t2\t7\t\tX\t\tX\t0\t5\n" +
                "Score\t\t20\t\t30\t\t35\t\t44\t\t66\t\t85\t\t94\t\t114\t\t129\t\t134\n" +
                "Jenn\n" +
                "Pinfalls\t3\t3\t8\t1\t\tX\t5\t/\t2\t4\t\tX\tF\t1\t3\t/\t\tX\tX\t7\t3\n" +
                "Score\t\t6\t\t15\t\t35\t\t47\t\t53\t\t64\t\t65\t\t85\t\t112\t\t132\n" +
                "Michael\n" +
                "Pinfalls\t\tX\t2\t5\tF\t8\t\tX\t\tX\t4\t2\t0\t9\t0\t5\tF\tF\t2\t/\t2\n" +
                "Score\t\t17\t\t24\t\t32\t\t56\t\t72\t\t78\t\t87\t\t92\t\t92\t\t104\n";
        assertEquals(result, expected);
    }
}
