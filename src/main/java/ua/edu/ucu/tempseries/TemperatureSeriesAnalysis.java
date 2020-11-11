package ua.edu.ucu.tempseries;

import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    static int MIN_TEMPERATURE = -273;

    private double[] temperatureArray;
    private int temperatureArrayCapacity;
    private int currentTemperaturesNumber;

    TemperatureSeriesAnalysis() {
        temperatureArray = new double[1];
        temperatureArrayCapacity = 1;
        currentTemperaturesNumber = 0;
    }

    TemperatureSeriesAnalysis(double[] temperatureSeries) {
        this();
        addTemps(temperatureSeries);
    }

    double average() {
        if (currentTemperaturesNumber == 0) {
            throw new IllegalArgumentException();
        }

        double averageTemp = 0;
        for (int i = 0; i < currentTemperaturesNumber; i++) {
            averageTemp += temperatureArray[i];
        }
        averageTemp /= currentTemperaturesNumber;
        return averageTemp;
    }


    double deviation() {
        double deviation = 0;
        double average = average();

        for (int i = 0; i < currentTemperaturesNumber; i++) {
            deviation += Math.pow(Math.abs(temperatureArray[i] - average), 2);
        }

        deviation /= currentTemperaturesNumber;
        return deviation;
    }

    double min() {
        if (currentTemperaturesNumber == 0) {
            throw new IllegalArgumentException();
        }

        double min = temperatureArray[0];
        for (int i = 1; i < currentTemperaturesNumber; i++) {
            if (temperatureArray[i] < min) {
                min = temperatureArray[i];
            }
        }
        return min;
    }

    double max() {
        if (currentTemperaturesNumber == 0) {
            throw new IllegalArgumentException();
        }

        double max = temperatureArray[0];
        for (int i = 1; i < currentTemperaturesNumber; i++) {
            if (temperatureArray[i] > max) {
                max = temperatureArray[i];
            }
        }
        return max;
    }

    double findTempClosestToZero() {
        return findTempClosestToValue(0.0);
    }

    double findTempClosestToValue(double tempValue) {
        if (currentTemperaturesNumber == 0) {
            throw new IllegalArgumentException();
        }

        double currentClosest = temperatureArray[0];
        double dist = Math.abs(currentClosest - tempValue);

        for (int i = 1; i < currentTemperaturesNumber; i++) {
            if (dist > Math.abs(temperatureArray[i] - tempValue)) {
                currentClosest = temperatureArray[i];
                dist = Math.abs(currentClosest - tempValue);
            } else if (Math.abs(currentClosest) == Math.abs(temperatureArray[i]) && temperatureArray[i] > currentClosest) {
                currentClosest = temperatureArray[i];
                dist = Math.abs(currentClosest - tempValue);
            }
        }
        return currentClosest;
    }


    double[] findTempsLessThan(double tempValue) {
        TemperatureSeriesAnalysis smaller = new TemperatureSeriesAnalysis();

        for (int i = 0; i < currentTemperaturesNumber; i++) {
            if (temperatureArray[i] < tempValue) {
                smaller.addOneTemp(temperatureArray[i]);
            }
        }
        double[] res = new double[smaller.currentTemperaturesNumber];
        System.arraycopy(smaller.temperatureArray, 0, res, 0, res.length);
        return res;
    }

    double[] findTempsGreaterThan(double tempValue) {
        TemperatureSeriesAnalysis bigger = new TemperatureSeriesAnalysis();

        for (int i = 0; i < currentTemperaturesNumber; i++) {
            if (temperatureArray[i] > tempValue) {
                bigger.addOneTemp(temperatureArray[i]);
            }
        }
        double[] res = new double[bigger.currentTemperaturesNumber];
        System.arraycopy(bigger.temperatureArray, 0, res, 0, res.length);
        return res;
    }

    private void addOneTemp(double temp) {
        if (temperatureArrayCapacity == currentTemperaturesNumber) {
            double[] newTempsArr = new double[temperatureArrayCapacity * 2];
            System.arraycopy(temperatureArray, 0, newTempsArr, 0, currentTemperaturesNumber);
            temperatureArrayCapacity *= 2;
            temperatureArray = newTempsArr;
        }
        temperatureArray[currentTemperaturesNumber] = temp;
        currentTemperaturesNumber++;
    }

    TempSummaryStatistics summaryStatistics() {
        return new TempSummaryStatistics(
                average(),
                deviation(),
                min(),
                max());
    }


    int addTemps(double... temps) {
        for (double temp : temps) {
            if (temp < MIN_TEMPERATURE) {
                throw new InputMismatchException();
            }
        }

        for (double temp : temps) {
            addOneTemp(temp);
        }
        return currentTemperaturesNumber;
    }
}