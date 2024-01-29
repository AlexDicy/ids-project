package it.unicam.cs.ids.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class MunicipalityTest {

    @Test
    void shouldCreateMunicipality() {
        List<Coordinate> perimeter = new ArrayList<>();
        perimeter.add(new Coordinate(10, 10));
        perimeter.add(new Coordinate(30, 100));
        perimeter.add(new Coordinate(20, 140));
        perimeter.add(new Coordinate(120, 170));
        perimeter.add(new Coordinate(120, 10));
        Assertions.assertDoesNotThrow(() -> new Municipality("", "TestMunicipality", perimeter));
    }

    @Test
    void shouldNotCreateMunicipality() {
        List<Coordinate> p2 = new ArrayList<>();
        p2.add(new Coordinate(10, 10));
        p2.add(new Coordinate(30, 100)); // less than 3 coordinates
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Municipality("", "TestMunicipality", p2));
    }
}
