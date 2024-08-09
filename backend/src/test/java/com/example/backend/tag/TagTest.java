package com.example.backend.tag;

import com.example.backend.client.timekeeper.dto.TimekeeperUserDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class TagTest {
    @Test
    void shouldReturnSverige() {
        Tag tag1 = new Tag();
        tag1.setName("TestTag");
        Tag tag2 = new Tag();
        tag2.setName("Sverige");
        TimekeeperUserDto tkUserTest = new TimekeeperUserDto(
                "Test Name",
                "Test Last Name",
                "email@test.mk",
                null,
                List.of(tag1, tag2),
                null,
                true,
                "test client",
                "test pt",
                true
        );
        String expectedResult = "Sverige";
        String actualResult = Tag.extractCountryTagFromTkUser(tkUserTest);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturnNorge() {
        Tag tag1 = new Tag();
        tag1.setName("TestTag");
        Tag tag2 = new Tag();
        tag2.setName("Norge");
        TimekeeperUserDto tkUserTest = new TimekeeperUserDto(
                "Test Name",
                "Test Last Name",
                "email@test.mk",
                null,
                List.of(tag1, tag2),
                null,
                true,
                "test client",
                "test pt",
                true
        );
        String expectedResult = "Norge";
        String actualResult = Tag.extractCountryTagFromTkUser(tkUserTest);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void shouldReturnSverigeForEmptyList() {
        TimekeeperUserDto tkUserTest = new TimekeeperUserDto(
                "Test Name",
                "Test Last Name",
                "email@test.mk",
                null,
                new ArrayList<>(),
                null,
                true,
                "test client",
                "test pt",
                true
        );
        String expectedResult = "Sverige";
        String actualResult = Tag.extractCountryTagFromTkUser(tkUserTest);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturnSverigeForListWithoutCountry() {
        Tag tag1 = new Tag();
        tag1.setName("TestTag1");
        Tag tag2 = new Tag();
        tag2.setName("TestTag2");
        TimekeeperUserDto tkUserTest = new TimekeeperUserDto(
                "Test Name",
                "Test Last Name",
                "email@test.mk",
                null,
                List.of(tag1, tag2),
                null,
                true,
                "test client",
                "test pt",
                true
        );
        String expectedResult = "Sverige";
        String actualResult = Tag.extractCountryTagFromTkUser(tkUserTest);
        assertEquals(expectedResult, actualResult);
    }
}