import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

//TODO JavaDoc idiot

public class mapData {
    private final int numOfRows;
    private final int numOfCols;

    private static final int NORTH = -1;
    private static final int SOUTH = 1;
    private static final int WEST = -1;
    private static final int EAST = 1;

    private final mapDataPoint[][] myMap;

    protected BufferedImage renderedMap;

    public mapData(final int theNumOfRows, final int theNumOfCols) {
        numOfRows = theNumOfRows;
        numOfCols = theNumOfCols;
        myMap = new mapDataPoint[theNumOfRows][theNumOfCols];
        renderedMap = new BufferedImage(numOfRows, numOfCols, BufferedImage.TYPE_INT_ARGB);
        IntStream.range(0, numOfRows).forEach(i ->
            IntStream.range(0, numOfCols).forEach(j ->
                myMap[i][j] = new mapDataPoint()
            )
        );
    }

    public void tick() {
        IntStream.range(0, numOfRows).forEach(row ->
            IntStream.range(0, numOfCols).forEach(col -> {
                spreadPoint(myMap[row][col], row, col);
                updateImage(row, col);
            })
        );
    }

    private void updateImage(final int theRow, final int theCol) {
        for (mapDataPoint[] row : myMap) {
            for (mapDataPoint point : row) {
                double v = point.fillAmount;
                int gray = (int)(v * 255);
                int rgb = (gray << 16) | (gray << 8) | gray;

                renderedMap.setRGB(theRow, theCol, rgb);
            }
        }
    }

    private void spreadPoint(final mapDataPoint thePoint, final int theRow, final int theCol) {
        if (thePoint.fillAmount == 1.0) {
            // do some logic that spreads the point to its neighbors up and down and left and right
            if (theRow + NORTH >= 0) {
                increasePoint(theRow + NORTH, theCol);
            }
            if (theRow + SOUTH <= numOfRows - 1) {
                increasePoint(theRow + SOUTH, theCol);
            }
            if (theCol + WEST >= 0) {
                increasePoint(theRow, theCol + WEST);
            }
            if (theCol + EAST <= numOfCols - 1) {
                increasePoint(theRow, theCol + EAST);
            }
        }
    }

    private void increasePoint(final int theRow, final int theCol) {
        mapDataPoint point = myMap[theRow][theCol];
        point.fillAmount += 0.1;
        if (point.fillAmount > 0.9) {
            point.fillAmount = 1.0;
        }
    }

    private static class mapDataPoint {
        protected double fillAmount;
        protected boolean isFillable = true;
    }
}
