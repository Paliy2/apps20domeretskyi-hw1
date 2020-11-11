package ua.edu.ucu.tempseries;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.InputMismatchException;

public class TemperatureSeriesAnalysisTest {

    private TemperatureSeriesAnalysis emptyTempSeries;
    private TemperatureSeriesAnalysis repeatedElementsSeries;
    private TemperatureSeriesAnalysis tempSeries;

    @Before
    public void setUp() {
        repeatedElementsSeries = new TemperatureSeriesAnalysis(new double[]{37.7, 37.7, 37.7, 37.7});
        emptyTempSeries = new TemperatureSeriesAnalysis();
        tempSeries = new TemperatureSeriesAnalysis(new double[]{3.0, 1.0, -1.0, 13.0, 335.0, -100.0});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTempSummaryStatisticsEmptySeries() {
        emptyTempSeries.summaryStatistics();
    }

    @Test(expected = InputMismatchException.class)
    public void testAddLowTemps() {
        tempSeries.addTemps(-274);
        tempSeries.addTemps(-3000);
    }

    @Test
    public void testAddTemperatureSeries() {
        assertEquals(tempSeries.addTemps(1.0, 0.0, 2.0), 9);
    }

    @Test
    public void testGreaterThanEmptyArray() {
        double[] expected = {};
        assertArrayEquals(expected, emptyTempSeries.findTempsGreaterThan(2.0), 0.000001);
    }

    @Test
    public void testGreaterThanSameElements() {
        double[] expected = {};
        double[] actualResult = repeatedElementsSeries.findTempsGreaterThan(38.0);

        assertArrayEquals(expected, actualResult, 0.0001);
    }

    @Test
    public void testLessThanEmptyArray() {
        double[] expected = {};
        double[] actualResult = emptyTempSeries.findTempsLessThan(1.0);

        assertArrayEquals(expected, actualResult, 0.000001);
    }

    @Test
    public void testLessThanSameElements() {
        double[] expected = {};
        double[] actualResult = repeatedElementsSeries.findTempsLessThan(1.0);

        assertArrayEquals(expected, actualResult, 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeviationEmptySeries() {
        emptyTempSeries.deviation();
    }

    @Test
    public void testDeviationSameElementsArray() {
        assertEquals(0, repeatedElementsSeries.deviation(), 0.000001);
    }

    @Test
    public void testDeviationArray() {
        assertEquals(18650.8055, tempSeries.deviation(), 0.0001);
    }

    @Test
    public void testClosestToValueOneClosestElement() {
        assertEquals(13.0, tempSeries.findTempClosestToValue(10.0), 0.0);
    }

    @Test
    public void testClosestToValueTwoOppositeElements() {
        double expected = 1.0;
        double actualResult = tempSeries.findTempClosestToValue(0.0);

        assertEquals(expected, actualResult, 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testClosestToValueEmptyArray() {
        emptyTempSeries.findTempClosestToValue(1.0);
    }

    @Test
    public void testClosestToZero() {
        assertEquals(1.0, tempSeries.findTempClosestToZero(), 0.0);
    }

    @Test
    public void testClosestToZeroAbsElements() {
        assertEquals(1.0, tempSeries.findTempClosestToZero(), 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testClosestToZeroEmptyArray() {
        emptyTempSeries.findTempClosestToZero();
    }

    @Test
    public void testMinValueNormal() {
        assertEquals(-100.0, tempSeries.min(), 0.0);
    }

    @Test
    public void testMinValueSameElementsArray() {
        assertEquals(37.7, repeatedElementsSeries.min(), 0.0);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinValueEmptyArray() {
        emptyTempSeries.min();
    }

    @Test
    public void testMaxValueNormalArray() {
        assertEquals(335.0, tempSeries.max(), 0.0);
        assertEquals(37.7, repeatedElementsSeries.max(), 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaxValueEmptyArray() {
        emptyTempSeries.max();
    }

    @Test
    public void testSameAverage() {
        assertEquals(37.7, repeatedElementsSeries.average(), 0.0001);
    }

    @Test
    public void testAverageWithOneElementArray() {
        // setup input data and expected result
        double[] temperatureSeries = {-1.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expected = -1.0;

        // call tested method
        double actualResult = seriesAnalysis.average();

        // compare expected result with actual result
        assertEquals(expected, actualResult, 0.00001);
    }

    @Ignore
    @Test
    public void testAverage() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expected = 1.0;

        double actualResult = seriesAnalysis.average();

        assertEquals(expected, actualResult, 0.00001);
    }
}