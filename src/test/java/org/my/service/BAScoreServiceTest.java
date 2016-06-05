package org.my.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.my.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class BAScoreServiceTest {

    @Autowired
    private BAScoreService bawlingScoreService;

    @Test
    public void testBAExampleTest() throws Exception {
        int[] score = bawlingScoreService.runBAWithCheck(
                new int[]{1, 4, 4, 5, 6, 4, 5, 5, 10, 0, 0, 1, 7, 3, 6, 4, 10, 0, 2, 8, 6}
        );
        assertArrayEquals(new int[]{5, 14, 29, 49, 60, 61, 77, 97, 117, 133}, score);
    }

    @Test
    public void testBAAllStrikesTest() throws Exception {
        int[] score = bawlingScoreService.runBAWithCheck(
                new int[]{10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 10, 10}
        );
        assertArrayEquals(new int[]{30, 60, 90, 120, 150, 180, 210, 240, 270, 300}, score);
    }

    @Test
    public void testBAAllStrikesExceptBonusShotTest() throws Exception {
        int[] score = bawlingScoreService.runBAWithCheck(
                new int[]{10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 10, 3}
        );
        Arrays.stream(score).boxed().forEach(el -> System.out.print(el + " "));
        assertArrayEquals(new int[]{30, 60, 90, 120, 150, 180, 210, 240, 263, 286}, score);
    }

    @Test
    public void testBAAllSparesTest() throws Exception {
        int[] score = bawlingScoreService.runBAWithCheck(
                new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5}
        );
        Arrays.stream(score).boxed().forEach(el -> System.out.print(el + " "));
        assertArrayEquals(new int[]{15, 30, 45, 60, 75, 90, 105, 120, 135, 150}, score);
    }

    @Test
    public void testBANoStrikesNoSparesTest() throws Exception {
        int[] score = bawlingScoreService.runBAWithCheck(
                new int[]{1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 0}
        );
        Arrays.stream(score).boxed().forEach(el -> System.out.print(el + " "));
        assertArrayEquals(new int[]{3, 6, 9, 12, 15, 18, 21, 24, 27, 30}, score);
    }

    @Test
    public void testBA1StrikeTest() throws Exception {
        int[] score = bawlingScoreService.runBAWithCheck(
                new int[]{1, 2, 10, 0, 5, 2, 7, 1, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 0}
        );
        Arrays.stream(score).boxed().forEach(el -> System.out.print(el + " "));
        assertArrayEquals(new int[]{3, 20, 27, 35, 38, 41, 44, 47, 50, 53}, score);
    }

    @Test
    public void testBA3StrikesTest() throws Exception {
        int[] score = bawlingScoreService.runBAWithCheck(
                new int[]{1, 2, 10, 0, 10, 0, 10, 0, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 0}
        );
        Arrays.stream(score).boxed().forEach(el -> System.out.print(el + " "));
        assertArrayEquals(new int[]{3, 33, 54, 67, 70, 73, 76, 79, 82, 85}, score);
    }

    @Test
    public void testBA1SpareTest() throws Exception {
        int[] score = bawlingScoreService.runBAWithCheck(
                new int[]{1, 2, 3, 7, 5, 2, 7, 1, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 0}
        );
        Arrays.stream(score).boxed().forEach(el -> System.out.print(el + " "));
        assertArrayEquals(new int[]{3, 18, 25, 33, 36, 39, 42, 45, 48, 51}, score);
    }

    @Test
    public void testBAHalfFramesTest() throws Exception {
        int[] score = bawlingScoreService.runBAWithCheck(
                new int[]{1, 2, 10, 0, 10, 0, 10, 0, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}
        );
        Arrays.stream(score).boxed().forEach(el -> System.out.print(el + " "));
        assertArrayEquals(new int[]{3, 33, 54, 0, 0, 0, 0, 0, 0, 0}, score);
    }

    @Test
    public void testBANoKnocksTest() throws Exception {
        int[] score = bawlingScoreService.runBAWithCheck(
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        );
        Arrays.stream(score).boxed().forEach(el -> System.out.print(el + " "));
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, score);
    }

    @Test
    public void testBANoBonusShotTest() throws Exception {
        int[] score = bawlingScoreService.runBAWithCheck(
                new int[]{5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 10, -1}
        );
        Arrays.stream(score).boxed().forEach(el -> System.out.print(el + " "));
        assertArrayEquals(new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 0}, score);
    }

    @Test(expected = BAException.class)
    public void testBAWrongFrameSumTest() throws Exception {
        bawlingScoreService.runBAWithCheck(
                new int[]{5, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        );
    }

    @Test(expected = BAException.class)
    public void testBAWrongShotValueTest() throws Exception {
        bawlingScoreService.runBAWithCheck(
                new int[]{12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        );
    }

    @Test(expected = BAException.class)
    public void testBAWrongLastFrameSumTest() throws Exception {
        bawlingScoreService.runBAWithCheck(
                new int[]{5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 11, 3}
        );
    }

    @Test(expected = BAException.class)
    public void testBAWrongBonusShotValueTest() throws Exception {
        bawlingScoreService.runBAWithCheck(
                new int[]{5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 10, 11}
        );
    }

    @Test(expected = BAException.class)
    public void testBAWrongInputArraySizeTest() throws Exception {
        bawlingScoreService.runBAWithCheck(
                new int[]{5, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        );
    }
}