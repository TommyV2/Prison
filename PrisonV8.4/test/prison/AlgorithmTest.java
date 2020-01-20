package prison;

import org.junit.Test;
import static org.junit.Assert.*;

public class AlgorithmTest {

    @Test
    public void testPrizonShapeParameter() {
        System.out.println("prizonShapeParameter");
        int X1 = 100;
        int H1 = 100;
        int X2 = 50;
        int H2 = 50;
        int cellWidth = 20;
        int cellHeight = 20;
        Algorithm instance = new Algorithm(1, 1, 1, 1, 1, 1);
        int expResult = 1;
        int result = instance.prizonShapeParameter(X1, H1, X2, H2, cellWidth, cellHeight);
        assertEquals(expResult, result);

    }

    @Test
    public void testPrizonShapeParameterFail() {
        System.out.println("prizonShapeParameter");
        int X1 = 100;
        int H1 = 100;
        int X2 = 150;
        int H2 = 150;
        int cellWidth = 20;
        int cellHeight = 20;
        Algorithm instance = new Algorithm(1, 1, 1, 1, 1, 1);
        int expResult = 0;
        int result = instance.prizonShapeParameter(X1, H1, X2, H2, cellWidth, cellHeight);
        assertEquals(expResult, result);

    }

    @Test
    public void testCellShapeParamater() {
        System.out.println("cellShapeParamater");
        int cellWidth = 100;
        int cellHeight = 100;
        int minCellWidth = 100;
        int minCellHeight = 100;
        Algorithm instance = new Algorithm(1, 1, 1, 1, 1, 1);
        int expResult = 1;
        int result = instance.cellShapeParamater(cellWidth, cellHeight, minCellWidth, minCellHeight);
        assertEquals(expResult, result);

    }

    @Test
    public void testCellShapeParamaterFail() {
        System.out.println("cellShapeParamater");
        int cellWidth = 100;
        int cellHeight = 100;
        int minCellWidth = 101;
        int minCellHeight = 100;
        Algorithm instance = new Algorithm(1, 1, 1, 1, 1, 1);
        int expResult = -50;
        int result = instance.cellShapeParamater(cellWidth, cellHeight, minCellWidth, minCellHeight);
        assertEquals(expResult, result);

    }

    @Test
    public void testCellQuantityParameter() {
        System.out.println("cellQuantityParameter");
        int maxCellQuantity = 100;
        int cellQuantity = 50;
        Algorithm instance = new Algorithm(1, 1, 1, 1, 1, 1);
        double expResult = 0.5;
        double result = instance.cellQuantityParameter(maxCellQuantity, cellQuantity);
        assertEquals(expResult, result, 0.0);

    }

    @Test
    public void testCellQuantityParameterManyCells() {
        System.out.println("cellQuantityParameter");
        int maxCellQuantity = 100;
        int cellQuantity = 101;
        Algorithm instance = new Algorithm(1, 1, 1, 1, 1, 1);
        double expResult = 1;
        double result = instance.cellQuantityParameter(maxCellQuantity, cellQuantity);
        assertEquals(expResult, result, 0.0);

    }

    @Test
    public void testBedQuantityParameter() {
        System.out.println("bedQuantityParameter");
        int maxBedQuantity = 5;
        int bedQuantity = 2;
        Algorithm instance = new Algorithm(1, 1, 1, 1, 1, 1);
        double expResult = 0.4;
        double result = instance.bedQuantityParameter(maxBedQuantity, bedQuantity);
        assertEquals(expResult, result, 0.0);

    }

    @Test
    public void testBedQuantityParameterMaxIsZero() {
        System.out.println("bedQuantityParameter");
        int maxBedQuantity = 0;
        int bedQuantity = 2;
        Algorithm instance = new Algorithm(1, 1, 1, 1, 1, 1);
        double expResult = -50;
        double result = instance.bedQuantityParameter(maxBedQuantity, bedQuantity);
        assertEquals(expResult, result, 0.0);

    }

    @Test
    public void testCameraQuantityParameter() {
        System.out.println("cameraQuantityParameter");
        int optimalCameraQuantity = 5;
        int cameraQuantity = 5;
        Algorithm instance = new Algorithm(1, 1, 1, 1, 1, 1);
        double expResult = 1;
        double result = instance.cameraQuantityParameter(optimalCameraQuantity, cameraQuantity);
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testBudgetParameter() {
        System.out.println("budgetParameter");
        int budget = 1000;
        int maxCellQuantity = 2;
        int maxBedQuantity = 2;
        int cameraQuantity = 2;
        int optimalCameraQuantity = 2;
        Algorithm instance = new Algorithm(1, 1, 1, 1, 1, 1);
        double expResult = 0.16;
        double result = instance.budgetParameter(budget, maxCellQuantity, maxBedQuantity, cameraQuantity, optimalCameraQuantity);
        assertEquals(expResult, result, 0.0);

    }

    @Test
    public void testDensityParameter() {
        System.out.println("densityParameter");
        int X1 = 100;
        int H1 = 100;
        int X2 = 0;
        int H2 = 0;
        int cellWidth = 10;
        int cellHeight = 10;
        int cellQuantity = 5;
        int maxCellQuantity = 5;
        Algorithm instance = new Algorithm(1, 1, 1, 1, 1, 1);
        double expResult = 0.05;
        double result = instance.densityParameter(X1, H1, X2, H2, cellWidth, cellHeight, cellQuantity, maxCellQuantity);
        assertEquals(expResult, result, 0.0);

    }

    @Test
    public void testDensityParameterTooManyCells() {
        System.out.println("densityParameter");
        int X1 = 100;
        int H1 = 100;
        int X2 = 0;
        int H2 = 0;
        int cellWidth = 10;
        int cellHeight = 10;
        int cellQuantity = 500;
        int maxCellQuantity = 5;
        Algorithm instance = new Algorithm(1, 1, 1, 1, 1, 1);
        double expResult = 0.05;
        double result = instance.densityParameter(X1, H1, X2, H2, cellWidth, cellHeight, cellQuantity, maxCellQuantity);
        assertEquals(expResult, result, 0.0);

    }

    @Test
    public void testMaxCellQuantity() {
        System.out.println("maxCellQuantity");
        int X1 = 100;
        int H1 = 100;
        int X2 = 20;
        int H2 = 20;
        int cellWidth = 20;
        int cellHeight = 20;
        Algorithm instance = new Algorithm(1, 1, 1, 1, 1, 1);
        int expResult = 8;
        int result = instance.maxCellQuantity(X1, H1, X2, H2, cellWidth, cellHeight);
        assertEquals(expResult, result);

    }

    @Test
    public void testMaxCellQuantityTooBigCells() {
        System.out.println("maxCellQuantity");
        int X1 = 100;
        int H1 = 100;
        int X2 = 20;
        int H2 = 20;
        int cellWidth = 200;
        int cellHeight = 200;
        Algorithm instance = new Algorithm(1, 1, 1, 1, 1, 1);
        int expResult = 0;
        int result = instance.maxCellQuantity(X1, H1, X2, H2, cellWidth, cellHeight);
        assertEquals(expResult, result);

    }

    @Test
    public void testMaxCellQuantityTooSmallCells() {
        System.out.println("maxCellQuantity");
        int X1 = 100;
        int H1 = 100;
        int X2 = 20;
        int H2 = 20;
        int cellWidth = 0;
        int cellHeight = 0;
        Algorithm instance = new Algorithm(1, 1, 1, 1, 1, 1);
        int expResult = 0;
        int result = instance.maxCellQuantity(X1, H1, X2, H2, cellWidth, cellHeight);
        assertEquals(expResult, result);

    }

    @Test
    public void testMaxBedQuantity() {
        System.out.println("maxBedQuantity");
        int maxCellQuantity = 10;
        int cellQuantity = 5;
        int cellWidth = 40;
        int cellHeight = 40;
        Algorithm instance = new Algorithm(1, 1, 1, 1, 1, 1);
        int expResult = 0;
        int result = instance.maxBedQuantity(maxCellQuantity, cellQuantity, cellWidth, cellHeight);
        assertEquals(expResult, result);

    }

    @Test
    public void testMaxBedRowsInCell() {
        System.out.println("maxBedRowsInCell");
        Algorithm instance = new Algorithm(1, 1, 1, 1, 1, 1);
        int expResult = 0;
        int result = instance.maxBedRowsInCell();
        assertEquals(expResult, result);

    }

    @Test
    public void testOptimalCameraQuantity() {
        System.out.println("optimalCameraQuantity");
        int X1 = 100;
        int H1 = 100;
        int X2 = 20;
        int H2 = 20;
        int maxCellQuantity = 0;
        int cellWidth = 0;
        int cellHeight = 0;
        int camerasPerCell = 0;
        Algorithm instance = new Algorithm(1, 1, 1, 1, 1, 1);
        int expResult = 12;
        int result = instance.optimalCameraQuantity(X1, H1, X2, H2, maxCellQuantity, cellWidth, cellHeight, camerasPerCell);
        assertEquals(expResult, result);

    }

    @Test
    public void testCamerasPerCell() {
        System.out.println("camerasPerCell");
        int cellWidth = 100;
        int cellHeight = 100;
        Algorithm instance = new Algorithm(1, 1, 1, 1, 1, 1);
        int expResult = 2;
        int result = instance.camerasPerCell(cellWidth, cellHeight);
        assertEquals(expResult, result);

    }

}
