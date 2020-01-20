package prisonGUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Stroke;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import prison.Algorithm;
import java.text.DecimalFormat;

public class PrisonGeneratorPanel extends JPanel {

    private final int WIDTH = 1000;
    private final int HEIGHT = 700;
    private final int buttonWidth = 100;
    private final int buttonHeight = 30;
    private final int drawingScreenXY = 50;
    private final int drawingScreenWidth = 600;
    private final int drawingScreenHeight = 600;
    private final int drawingStart = drawingScreenXY + 30;
    private final int bedWidth = 10;
    private final int bedHeight = 20;
    private final int bedPillowSize = 6;
    private final int doorWidth = 10;
    private final int windowWidth = 10;
    private final int evacuationRouteWidth = 10;
    private final int corridorEvacuationRouteWidth = 20;
    private final int cameraRange = 50;
    private final int cameraSize = 7;
    private final int wcSize = 10;
    private final int exitDoorWidth = corridorEvacuationRouteWidth - 10;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    private final Color wallColor = Color.BLACK;
    private final Color backgroundColor = UIManager.getColor("Panel.background");
    private final Color bedColor = Color.LIGHT_GRAY;
    private final Color doorColor = Color.YELLOW;
    private final Color windowColor = Color.CYAN;
    private final Color cameraColor = Color.RED;
    private final Color wcColor = Color.PINK;
    private final Color securityRoomWallColor = Color.BLUE;
    private final Color exitDoorColor = Color.GREEN;
    private final Color textLabelGREEN = new Color(0, 153, 76);

    private final Stroke wallStroke;
    private final Stroke basicStroke;
    private final Stroke prisonShapeStroke;
    private final Stroke windowStroke;
    private final int basicStrokeInt = 1;
    private final int wallStrokeInt = 4;

    private int X1 = 450;
    private int H1 = 440;
    private int X2 = 220;
    private int H2 = 200;
    private int cellQty = 12;
    private int bedQty = 140;
    private int cameraQty = 50;
    private int cellWidth = 80;
    private int cellHeight = 100;
    private int popNumber;
    private double theFittestScore;
    private double moneySpent;

    private int maxColumnsOfBeds1Side;
    private int maxRowsOfBeds;
    private int maxBedsInCell;
    private int maxCellsX1Wall;
    private int maxCellsH1Wall;
    private int maxCellsX2Wall;
    private int maxCellsH2Wall;
    private int camerasInCell;
    private int remainingCameras;
    private int remainingBeds;
    private int remainingCells;
    private int maxCamerasX1Wall;
    private int maxCamerasH1Wall;
    private int maxCamerasX2Wall;
    private int maxCamerasH2Wall;
    private int theClosestCell;
    private int theFurthestCell;
    private int cellsH2 = 0;

    private Timer displayTimer;
    private int displayDelay = 0;
    private JSlider slider;
    private Algorithm a1;
    private boolean isRunning = false;
    private boolean wasStopped = false;
    private boolean wasStarted = false;
    private JLabel fitnessLabel;
    private JLabel moneyLabel;
    private JLabel popNumberLabel;
    private JLabel theClosestLabel;
    private JLabel theFurthestLabel;

    public PrisonGeneratorPanel(int popMax, int budget, int popSize, double crossingRate, double mutationRate, double acceptedQuality) {

        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new GridBagLayout());
        setLayout(null);
        wallStroke = new BasicStroke(wallStrokeInt);
        basicStroke = new BasicStroke(basicStrokeInt);
        prisonShapeStroke = new BasicStroke(8);
        windowStroke = new BasicStroke(8);
        a1 = new Algorithm(popMax, budget, popSize, crossingRate, mutationRate, acceptedQuality);
        displayTimer = new Timer(displayDelay, actionEvent -> {
            a1.runAlgorithm();
            popNumber = a1.getPopNumber();
            theFittestScore = a1.getTheFittestScore();
            X1 = a1.getX1();
            H1 = a1.getH1();
            X2 = a1.getX2();
            H2 = a1.getH2();
            cellWidth = a1.getCellWidth();
            cellHeight = a1.getCellHeight();
            cellQty = a1.getCellQuantity();
            bedQty = a1.getBedQuantity();
            cameraQty = a1.getCameraQuantity();
            moneySpent = a1.getMoneySpent();
            theClosestCell = a1.getCellWidth();
            theFurthestCell = a1.getH1() + a1.getX1() - 2 * a1.getCellWidth();
            calculateParams();
            repaint();
        });

        slider = new JSlider(0, 5, 5);
        slider.setBounds(680, 550, 300, 50);
        slider.addChangeListener(event -> {
            JSlider source = (JSlider) event.getSource();
            if (!source.getValueIsAdjusting()) {
                adjustSpeed((int) source.getValue());
            }
        });
        slider.setMajorTickSpacing(1);
        slider.setPaintLabels(true);
        add(slider);

        fitnessLabel = new JLabel("Fitness: " + df2.format(10 * theFittestScore) + "%");
        moneyLabel = new JLabel("Wydane pieniadze: " + moneySpent + "$");
        theClosestLabel = new JLabel("Najblizsza cela: " + theClosestCell / 10 + "[m]");
        theFurthestLabel = new JLabel("Najdalsza cela: " + theFurthestCell / 10 + "[m]");
        fitnessLabel.setBounds(700, 200, 200, 30);
        moneyLabel.setBounds(700, 250, 200, 30);
        theClosestLabel.setBounds(700, 300, 200, 30);
        theFurthestLabel.setBounds(700, 350, 200, 30);
        popNumberLabel = new JLabel("Numer populacji: " + popNumber);
        popNumberLabel.setBounds(50, 650, 200, 30);
        add(popNumberLabel);
        add(fitnessLabel);
        add(moneyLabel);
        add(theClosestLabel);
        add(theFurthestLabel);

        createButton("Settings", WIDTH - buttonWidth, 0, buttonWidth, buttonHeight, event -> {
            displayTimer.stop();
            setVisible(false);
            displaySettingsPanel();
        });

        createButton("START", 700, 600, buttonWidth, buttonHeight, event -> {
            if (!isRunning && !wasStopped) {
                startAlgorithm();
                isRunning = true;
                wasStarted = true;
            } else if (!isRunning && wasStopped) {
                displayTimer.start();
            }
        });

        createButton("STOP", 840, 600, buttonWidth, buttonHeight, event -> {
            if (wasStarted) {
                displayTimer.stop();
                isRunning = false;
                wasStopped = true;
            }
        });
    }

    public void adjustSpeed(int sliderValue) {
        switch (sliderValue) {
            case 5:
                displayDelay = 0;
                displayTimer.setDelay(displayDelay);
                System.out.println("displayDelay = " + displayDelay);
                break;
            case 4:
                displayDelay = 1;
                displayTimer.setDelay(displayDelay);
                System.out.println("displayDelay = " + displayDelay);
                break;
            case 3:
                displayDelay = 15;
                displayTimer.setDelay(displayDelay);
                System.out.println("displayDelay = " + displayDelay);
                break;
            case 2:
                displayDelay = 250;
                displayTimer.setDelay(displayDelay);
                System.out.println("displayDelay = " + displayDelay);
                break;
            case 1:
                displayDelay = 700;
                displayTimer.setDelay(displayDelay);
                System.out.println("displayDelay = " + displayDelay);
                break;
            case 0:
                displayDelay = 1500;
                displayTimer.setDelay(displayDelay);
                System.out.println("displayDelay = " + displayDelay);
                break;
        }
    }

    public void calculateParams() {
        maxColumnsOfBeds1Side = maxBedColumnsOnOneSideInCell();
        maxRowsOfBeds = maxBedRowsInCell();
        maxBedsInCell = (maxColumnsOfBeds1Side * 2) * maxRowsOfBeds;
        maxCellsX1Wall = maxCellsX1Wall();
        maxCellsH1Wall = maxCellsH1Wall();
        maxCellsX2Wall = maxCellsX2Wall();
        maxCellsH2Wall = maxCellsH2Wall();
        camerasInCell = camerasInCell();
        remainingCameras = cameraQty;
        remainingBeds = bedQty;
        remainingCells = cellQty;
        maxCamerasX1Wall = camerasX1Wall();
        maxCamerasH1Wall = camerasH1Wall();
        maxCamerasX2Wall = camerasX2Wall();
        maxCamerasH2Wall = camerasH2Wall();
    }

    public void startAlgorithm() {
        a1.runPreAlgorithm();
        displayTimer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        paintDrawingScreen(g2);
        paintPrisonShape(g2);
        paintCells(g2);
        paintCorridorCameras(g2);
        paintExitDoor(g2);
        updateStrings();
    }

    public void updateStrings() {
        if (theFittestScore < 8) {
            fitnessLabel.setForeground(Color.RED);
        } else {
            fitnessLabel.setForeground(textLabelGREEN);
        }
        fitnessLabel.setText("Fitness: " + df2.format(10 * theFittestScore) + "%");

        if (moneySpent > a1.getBudget()) {
            moneyLabel.setForeground(Color.RED);
        } else {
            moneyLabel.setForeground(textLabelGREEN);
        }
        moneyLabel.setText("Wydane pieniadze: " + moneySpent + "$");
        theClosestLabel.setText("Najblizsza cela: " + theClosestCell / 10 + "[m]");
        theFurthestLabel.setText("Najdalsza cela: " + theFurthestCell / 10 + "[m]");
        popNumberLabel.setText("Numer populacji: " + popNumber);
    }

    public void paintDrawingScreen(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.setStroke(basicStroke);
        g2.drawRect(drawingScreenXY, drawingScreenXY, drawingScreenWidth, drawingScreenHeight);
    }

    public void paintPrisonShape(Graphics2D g2) {

        g2.setStroke(prisonShapeStroke);
        g2.setColor(wallColor);
        g2.drawRect(drawingStart, drawingStart, X1, H1);
        g2.setColor(backgroundColor);
        g2.drawRect(drawingStart + X1 - X2, drawingStart, X2, H2);
        g2.setColor(wallColor);
        g2.drawLine(drawingStart + X1 - X2, drawingStart, drawingStart + X1 - X2, drawingStart + H2);
        g2.drawLine(drawingStart + X1 - X2, drawingStart + H2, drawingStart + X1, drawingStart + H2);
    }

    public void paintSecurityRoom(Graphics2D g2, int roomX, int roomY) {
        g2.setStroke(wallStroke);
        g2.setColor(securityRoomWallColor);
        g2.drawRect(roomX, roomY, cellHeight, cellWidth);

        paintWindow(g2, roomX, roomY + cellWidth / 2 - windowWidth / 2,
                roomX, roomY + cellWidth / 2 + windowWidth / 2);
        paintDoor(g2, roomX + cellHeight, roomY + cellWidth / 2 - doorWidth / 2,
                roomX + cellHeight, roomY + cellWidth / 2 + doorWidth / 2);
        paintWC(g2, roomX + wallStrokeInt / 2, roomY + cellWidth / 2 - windowWidth / 2);
    }

    public void paintExitDoor(Graphics2D g2) {
        g2.setStroke(prisonShapeStroke);
        g2.setColor(exitDoorColor);
        g2.drawLine(drawingStart + X1, drawingStart + H1 - cellHeight - wallStrokeInt,
                drawingStart + X1, drawingStart + H1 - cellHeight - exitDoorWidth - wallStrokeInt);
    }

    public void paintWC(Graphics2D g2, int wcX, int wcY) {
        g2.setColor(wcColor);
        g2.setStroke(basicStroke);
        g2.fillRect(wcX, wcY, wcSize, wcSize);
    }

    public void paintCamera(Graphics2D g2, int camX, int camY) {
        g2.setColor(cameraColor);
        g2.setStroke(basicStroke);
        g2.fillOval(camX, camY, cameraSize, cameraSize);
    }

    public void paintCorridorCameras(Graphics2D g2) {
        int h1CamerasToPrint = remainingCameras >= maxCamerasH1Wall ? maxCamerasH1Wall : remainingCameras;
        for (int camerasH1Count = 0; camerasH1Count < h1CamerasToPrint; camerasH1Count++) {
            paintCamera(g2, drawingStart + cellHeight, drawingStart + (camerasH1Count * cameraRange * 2));
            remainingCameras--;
        }

        int x1CamerasToPrint = remainingCameras >= maxCamerasX1Wall ? maxCamerasX1Wall : remainingCameras;
        for (int camerasX1Count = 0; camerasX1Count < x1CamerasToPrint; camerasX1Count++) {
            paintCamera(g2, drawingStart + cellHeight + (camerasX1Count * cellWidth), drawingStart + H1 - cellHeight - (wallStrokeInt * 2));
            remainingCameras--;
        }

        int x2CamerasToPrint = remainingCameras >= maxCamerasX2Wall ? maxCamerasX2Wall : remainingCameras;
        for (int camerasX2Count = 0; camerasX2Count < x2CamerasToPrint; camerasX2Count++) {
            paintCamera(g2, drawingStart + X1 - cameraRange - (camerasX2Count * cameraRange * 2), drawingStart + H2 + cellHeight);
            remainingCameras--;
        }

        int h2CamerasToPrint = remainingCameras >= maxCamerasH2Wall ? maxCamerasH2Wall : remainingCameras;
        for (int camerasH2Count = 0; camerasH2Count < h2CamerasToPrint; camerasH2Count++) {
            paintCamera(g2, drawingStart + X1 - X2 - cellHeight - (wallStrokeInt * 2),
                    drawingStart + cameraRange + (camerasH2Count * cameraRange * 2));
            remainingCameras--;
        }
    }

    public void paintCells(Graphics2D g2) {

        paintSecurityRoom(g2, drawingStart, drawingStart);

        for (int cellH1Count = 0; cellH1Count < maxCellsH1Wall && remainingCells > 0; cellH1Count++) {
            int bedsToPrint = remainingBeds >= maxBedsInCell ? maxBedsInCell : remainingBeds;
            int camerasToPrint = remainingCameras >= 2 ? camerasInCell : remainingCameras;

            paintCellHorizontal(g2, drawingStart, drawingStart + cellWidth + (cellH1Count * cellWidth),
                    false, bedsToPrint, camerasToPrint);
            remainingBeds -= bedsToPrint;
            remainingCameras -= camerasToPrint;
            remainingCells--;
        }

        for (int cellX1Count = 0; cellX1Count < maxCellsX1Wall && remainingCells > 0; cellX1Count++) {
            int bedsToPrint = remainingBeds >= maxBedsInCell ? maxBedsInCell : remainingBeds;
            int camerasToPrint = remainingCameras >= 2 ? camerasInCell : remainingCameras;

            paintCellVertical(g2, drawingStart + cellHeight + (cellX1Count * cellWidth),
                    drawingStart + H1 - cellHeight, false, bedsToPrint, camerasToPrint);
            remainingBeds -= bedsToPrint;
            remainingCameras -= camerasToPrint;
            remainingCells--;
        }

        for (int cellX2Count = 0; cellX2Count < maxCellsX2Wall && remainingCells > 0; cellX2Count++) {
            int bedsToPrint = remainingBeds >= maxBedsInCell ? maxBedsInCell : remainingBeds;
            int camerasToPrint = remainingCameras >= 2 ? camerasInCell : remainingCameras;

            paintCellVertical(g2, drawingStart + X1 - cellWidth - (cellX2Count * cellWidth),
                    drawingStart + H2, true, bedsToPrint, camerasToPrint);
            remainingBeds -= bedsToPrint;
            remainingCameras -= camerasToPrint;
            remainingCells--;
        }

        for (int cellH2Count = 0; cellH2Count < maxCellsH2Wall && remainingCells > 0; cellH2Count++) {
            int bedsToPrint = remainingBeds >= maxBedsInCell ? maxBedsInCell : remainingBeds;
            int camerasToPrint = remainingCameras >= 2 ? camerasInCell : remainingCameras;

            paintCellHorizontal(g2, drawingStart + X1 - X2 - cellHeight, drawingStart + (cellH2Count * cellWidth),
                    true, bedsToPrint, camerasToPrint);
            remainingBeds -= bedsToPrint;
            remainingCameras -= camerasToPrint;
            remainingCells--;
            cellsH2 = cellH2Count;
        }

    }

    public void paintCellVertical(Graphics2D g2, int cellX, int cellY, boolean isReversed, int bedCount, int cameraCount) {
        g2.setStroke(wallStroke);
        g2.setColor(wallColor);
        g2.drawRect(cellX, cellY, cellWidth, cellHeight);

        if (isReversed) {
            paintWindow(g2, cellX + cellWidth / 2 - windowWidth / 2, cellY,
                    cellX + cellWidth / 2 + windowWidth / 2, cellY);
            paintDoor(g2, cellX + cellWidth / 2 - doorWidth / 2, cellY + cellHeight,
                    cellX + cellWidth / 2 + doorWidth / 2, cellY + cellHeight);
            paintWC(g2, cellX + (cellWidth / 2 - windowWidth / 2), cellY + (wallStrokeInt / 2));
        } else {
            paintDoor(g2, cellX + cellWidth / 2 - doorWidth / 2, cellY,
                    cellX + cellWidth / 2 + doorWidth / 2, cellY);
            paintWindow(g2, cellX + cellWidth / 2 - windowWidth / 2, cellY + cellHeight,
                    cellX + cellWidth / 2 + windowWidth / 2, cellY + cellHeight);
            paintWC(g2, cellX + (cellWidth / 2 - windowWidth / 2), cellY + cellHeight - (wallStrokeInt / 2) - wcSize);
        }

        int bedPrinted = 0;
        for (int column = 0; column < maxColumnsOfBeds1Side; column++) {
            for (int row = 0; row < maxRowsOfBeds && bedPrinted < bedCount; row++) {
                paintBedVertical(g2, cellX + (wallStrokeInt / 2) + (column * (bedWidth + evacuationRouteWidth)),
                        cellY + (wallStrokeInt / 2) + (row * (bedHeight + evacuationRouteWidth)));
                bedPrinted++;
            }
        }
        for (int column = 0; column < maxColumnsOfBeds1Side; column++) {
            for (int row = 0; row < maxRowsOfBeds && bedPrinted < bedCount; row++) {
                paintBedVertical(g2, cellX + ((cellWidth + evacuationRouteWidth + wallStrokeInt) / 2)
                        + (column * (bedWidth + evacuationRouteWidth)),
                        cellY + (wallStrokeInt / 2) + (row * (bedHeight + evacuationRouteWidth)));
                bedPrinted++;
            }
        }
        if (cameraCount == 2) {
            paintCamera(g2, cellX + wallStrokeInt, cellY);
            paintCamera(g2, cellX + cellWidth - 3 * wallStrokeInt, cellY + cellHeight - 2 * wallStrokeInt);
        } else if (cameraCount == 1) {
            paintCamera(g2, cellX + wallStrokeInt, cellY);
        }
    }

    public void paintCellHorizontal(Graphics2D g2, int cellX, int cellY, boolean isReversed, int bedCount, int cameraCount) {
        g2.setStroke(wallStroke);
        g2.setColor(wallColor);
        g2.drawRect(cellX, cellY, cellHeight, cellWidth);

        if (isReversed) {
            paintDoor(g2, cellX, cellY + cellWidth / 2 - doorWidth / 2,
                    cellX, cellY + cellWidth / 2 + doorWidth / 2);
            paintWindow(g2, cellX + cellHeight, cellY + cellWidth / 2 - windowWidth / 2,
                    cellX + cellHeight, cellY + cellWidth / 2 + windowWidth / 2);
            paintWC(g2, cellX + cellHeight - wallStrokeInt / 2 - wcSize, cellY + cellWidth / 2 - windowWidth / 2);
        } else {
            paintWindow(g2, cellX, cellY + cellWidth / 2 - windowWidth / 2,
                    cellX, cellY + cellWidth / 2 + windowWidth / 2);
            paintDoor(g2, cellX + cellHeight, cellY + cellWidth / 2 - doorWidth / 2,
                    cellX + cellHeight, cellY + cellWidth / 2 + doorWidth / 2);
            paintWC(g2, cellX + wallStrokeInt / 2, cellY + cellWidth / 2 - windowWidth / 2);
        }

        int bedPrinted = 0;
        for (int column = 0; column < maxColumnsOfBeds1Side; column++) {
            for (int row = 0; row < maxRowsOfBeds && bedPrinted < bedCount; row++) {
                paintBedHorizontal(g2, cellX + (wallStrokeInt / 2) + (row * (bedHeight + evacuationRouteWidth)),
                        cellY + (wallStrokeInt / 2) + (column * (bedWidth + evacuationRouteWidth)));
                bedPrinted++;
            }
        }
        for (int column = 0; column < maxColumnsOfBeds1Side; column++) {
            for (int row = 0; row < maxRowsOfBeds && bedPrinted < bedCount; row++) {
                paintBedHorizontal(g2, cellX + (wallStrokeInt / 2) + (row * (bedHeight + evacuationRouteWidth)),
                        cellY + ((cellWidth + evacuationRouteWidth + wallStrokeInt) / 2)
                        + (column * (bedWidth + evacuationRouteWidth)));
                bedPrinted++;
            }
        }
        if (cameraCount == 2) {
            paintCamera(g2, cellX + wallStrokeInt, cellY);
            paintCamera(g2, cellX + cellHeight - 3 * wallStrokeInt, cellY + cellWidth - 2 * wallStrokeInt);
        } else if (cameraCount == 1) {
            paintCamera(g2, cellX + wallStrokeInt, cellY);
        }
    }

    public void paintWindow(Graphics2D g2, int winX1, int winY1, int winX2, int winY2) {
        g2.setColor(windowColor);
        g2.setStroke(windowStroke);
        g2.drawLine(winX1, winY1, winX2, winY2);
    }

    public void paintDoor(Graphics2D g2, int doorX1, int doorY1, int doorX2, int doorY2) {
        g2.setColor(doorColor);
        g2.setStroke(wallStroke);
        g2.drawLine(doorX1, doorY1, doorX2, doorY2);
    }

    public void paintBedVertical(Graphics2D g2, int bedX, int bedY) {
        g2.setStroke(basicStroke);
        g2.setColor(Color.BLACK);
        g2.drawRect(bedX, bedY, bedWidth, bedHeight);
        g2.setColor(bedColor);
        g2.fillRect(bedX + basicStrokeInt, bedY + bedPillowSize + basicStrokeInt,
                bedWidth - basicStrokeInt, bedHeight - basicStrokeInt - bedPillowSize);
    }

    public void paintBedHorizontal(Graphics2D g2, int bedX, int bedY) {
        g2.setStroke(basicStroke);
        g2.setColor(Color.BLACK);
        g2.drawRect(bedX, bedY, bedHeight, bedWidth);
        g2.setColor(bedColor);
        g2.fillRect(bedX + basicStrokeInt, bedY + basicStrokeInt,
                bedHeight - basicStrokeInt - bedPillowSize, bedWidth - basicStrokeInt);
    }

    public int camerasInCell() {
        double diagonal = Math.sqrt((cellWidth * cellWidth) + (cellHeight * cellHeight));
        return diagonal > cameraRange ? 2 : 1;
    }

    public int camerasH1Wall() {
        if (cellQty >= maxCellsH1Wall) {
            return ((maxCellsH1Wall * cellWidth) / (cameraRange * 2)) + 1;
        } else {
            return ((cellQty * cellWidth) / (cameraRange * 2)) + 1;
        }
    }

    public int camerasX1Wall() {
        if (cellQty >= maxCellsH1Wall + maxCellsX1Wall) {
            return ((maxCellsX1Wall * cellWidth) / (cameraRange * 2)) + 1;
        } else {
            int cellsX1Wall = cellQty - maxCellsH1Wall;
            if (cellsX1Wall < 0) {
                return 0;
            }
            return ((cellsX1Wall * cellWidth) / (cameraRange * 2)) + 1;
        }
    }

    public int camerasX2Wall() {
        if (cellQty >= maxCellsH1Wall + maxCellsX1Wall + maxCellsX2Wall) {
            return (((maxCellsX2Wall * cellWidth) - cameraRange) / (cameraRange * 2))
                    + (cameraRange > (maxCellsX2Wall * cellWidth) ? 0 : 1);
        } else {
            int cellsX2Wall = cellQty - maxCellsH1Wall - maxCellsX1Wall;
            if (cellsX2Wall < 0) {
                return 0;
            }
            return (((cellsX2Wall * cellWidth) - cameraRange) / (cameraRange * 2))
                    + (cameraRange > (cellsX2Wall * cellWidth) ? 0 : 1);
        }
    }

    public int camerasH2Wall() {
        if (cellQty >= maxCellsH1Wall + maxCellsX1Wall + maxCellsX2Wall + maxCellsH2Wall) {
            return (((maxCellsH2Wall * cellWidth) - cameraRange) / (cameraRange * 2))
                    + (cameraRange > (maxCellsH2Wall * cellWidth) ? 0 : 1);
        } else {
            int cellsH2Wall = cellQty - maxCellsH1Wall - maxCellsX1Wall - maxCellsX2Wall;
            if (cellsH2Wall < 0) {
                return 0;
            }
            return (((cellsH2Wall * cellWidth) - cameraRange) / (cameraRange * 2))
                    + (cameraRange > (cellsH2Wall * cellWidth) ? 0 : 1);
        }
    }

    public int maxCellsH1Wall() {
        if (X1 - X2 < cellHeight + corridorEvacuationRouteWidth) {
            return 0;
        } else {
            return ((H1 - cellHeight) / cellWidth) - 1;
        }
    }

    public int maxCellsX1Wall() {
        if (H1 - H2 < cellHeight + corridorEvacuationRouteWidth) {
            return 0;
        } else {
            return (X1 - cellHeight) / cellWidth;
        }
    }

    public int maxCellsX2Wall() {
        if (H1 - H2 < (cellHeight * 2) + corridorEvacuationRouteWidth) {
            return 0;
        } else {
            return (X2 / cellWidth);
        }
    }

    public int maxCellsH2Wall() {
        if (X1 - X2 < (cellHeight * 2) + corridorEvacuationRouteWidth) {
            return 0;
        } else {
            return (H2 / cellWidth);
        }
    }

    public int maxBedColumnsOnOneSideInCell() {
        int maxColumns = 0;
        int totalWidth = 0;
        if ((((cellWidth - evacuationRouteWidth) / 2) - (wallStrokeInt / 2)) >= bedWidth) {
            maxColumns++;
            totalWidth += (bedWidth + (wallStrokeInt / 2));
        }
        while (true) {
            if ((totalWidth + evacuationRouteWidth + bedWidth) <= ((cellWidth - evacuationRouteWidth - wallStrokeInt) / 2)) {
                maxColumns++;
                totalWidth += evacuationRouteWidth + bedWidth;
            } else {
                break;
            }
        }
        return maxColumns;
    }

    public int maxBedRowsInCell() {
        int maxRows = 0;
        int totalHeight = 0;
        if (cellHeight - wallStrokeInt >= bedHeight) {
            maxRows++;
            totalHeight += (bedHeight + wallStrokeInt);
        }
        while (true) {
            if ((totalHeight + evacuationRouteWidth + bedHeight) <= cellHeight) {
                maxRows++;
                totalHeight += evacuationRouteWidth + bedHeight;
            } else {
                break;
            }
        }
        return maxRows;
    }

    private void createButton(String title, int x, int y, int width, int height, ActionListener listener) {
        JButton button = new JButton(title);
        button.setBounds(x, y, width, height);
        button.addActionListener(listener);
        add(button);
    }

    public void displaySettingsPanel() {

        setVisible(false);
        EventQueue.invokeLater(()
                -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            SettingsPanel settings = new SettingsPanel();
            frame.add(settings);
            settings.requestFocus();
            frame.pack();
        });

    }
}
