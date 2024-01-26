package it.unicam.cs.ids.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ConcreteContestTest {

    private Contest contest;

    @BeforeEach
    void setUp() {
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 86400000); // Adding 1 day to start date
        contest = new ConcreteContest("1", "Test Contest", "Test Description", startDate, endDate, "123");
    }

    @Test
    void shouldGetId() {
        assertEquals("1", contest.getId());
    }

    @Test
    void shouldGetStartDate() {
        assertNotNull(contest.getStartDate());
    }

    @Test
    void shouldGetEndDate() {
        assertNotNull(contest.getEndDate());
    }

    @Test
    void shouldGetAllowedUsersInitiallyNull() {
        assertEquals(0, contest.getAllowedUsers().size());
    }

    @Test
    void shouldSetAllowedUsers() {
        List<String> users = Arrays.asList("user1", "user2");
        contest.setAllowedUsers(users);
        assertEquals(users, contest.getAllowedUsers());
    }

    @Test
    void shouldSetWinners() {
        List<String> winners = Arrays.asList("winner1", "winner2");
        contest.setWinners(winners);
        assertEquals(winners, contest.getWinners());
    }

    @Test
    void shouldGetDetails() {
        assertEquals(contest, contest.getDetails());
    }

    @Test
    void shouldGetAnimatorId() {
        assertEquals("123", contest.getAnimatorId());
    }
}
