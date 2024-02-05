package it.unicam.cs.ids.manager;

import it.unicam.cs.ids.UtilClass;
import it.unicam.cs.ids.model.Coordinate;
import it.unicam.cs.ids.model.content.Content;
import it.unicam.cs.ids.model.content.POI;
import it.unicam.cs.ids.model.contest.GenericContest;
import it.unicam.cs.ids.model.contest.Contest;
import it.unicam.cs.ids.model.contest.POIContest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContestManagerTest {

    private ContestManager contestManager;

    @BeforeEach
    void setUp() {
        contestManager = new ContestManager();
    }

    @Test
    void shouldSubmitContest() {
        contestManager.submit(contest);

        assertEquals(1, contestManager.contestList.size());
        assertEquals(contest, contestManager.get("1"));
    }

    @Test
    void shouldGetCompletedContests() {
        Date currentDate = new Date();
        Date yesterday = new Date(currentDate.getTime() - 86400000);
        Date halfDayAgo = new Date(currentDate.getTime() - (86400000 / 2));
        Date tomorrow = new Date(currentDate.getTime() + 86400000);

        Contest contest1 = new GenericContest("1", "Test Contest 1", "Description", yesterday, halfDayAgo, "user1");
        Contest contest2 = new GenericContest("2", "Test Contest 2", "Description", yesterday, halfDayAgo, "user1");
        Contest contest3 = new GenericContest("3", "Test Contest 3", "Description", yesterday, tomorrow, "user2");
        contestManager.submit(contest1);
        contestManager.submit(contest2);
        contestManager.submit(contest3);

        assertEquals(2, contestManager.getAnimatorCompletedContests("user1").size());
        assertEquals(0, contestManager.getAnimatorCompletedContests("user2").size());
    }

    @Test
    void shouldGetContentByContest() {
        Date currentDate = new Date();
        Date yesterday = new Date(currentDate.getTime() - 86400000);
        Date halfDayAgo = new Date(currentDate.getTime() - (86400000 / 2));

        POI poi1 = POIManagerTest.createPOI("POI_1", "POI_1", "First POI", "", true, halfDayAgo, new Coordinate(88, 180));
        UtilClass.getPOIManager().submit(poi1);

        ImageManager imageManager = UtilClass.getImageManager();
        imageManager.submit(ImageManagerTest.createImage("ID_1", "Image_1", "First Image", "Author_1", halfDayAgo, true, "POI_1"));
        imageManager.submit(ImageManagerTest.createImage("ID_2", "Image_2", "Second Image", "Author_1", halfDayAgo, false, "POI_1"));
        imageManager.submit(ImageManagerTest.createImage("ID_3", "Image_3", "Third Image", "Author_1", halfDayAgo, true, "POI_1"));
        imageManager.submit(ImageManagerTest.createImage("ID_4", "Image_4", "Fourth Image", "Author_1", halfDayAgo, false, "POI_2"));

        Contest contentContest = new POIContest("1", "POI Contest", "Description", yesterday, currentDate, "user1", "POI_1");
        contestManager.submit(contentContest);

        List<Content> contents = contestManager.getContentByContest("1", yesterday, currentDate);

        assertEquals(2, contents.size());
        assertEquals("ID_1", contents.get(0).getId());
        assertEquals("ID_3", contents.get(1).getId());
    }

    @Test
    void shouldSetContestWinners() {
        Contest contest = new GenericContest("1", "Test Contest", "Description", new Date(), new Date(), "user1");
        contestManager.submit(contest);

        List<String> winners = Arrays.asList("winner1", "winner2");
        contestManager.setContestWinners("1", winners);

        assertEquals(winners, contest.getWinners());
    }
}
