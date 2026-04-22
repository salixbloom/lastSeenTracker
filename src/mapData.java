import java.util.stream.IntStream;

//TODO JavaDoc idiot

public class mapData {
    private final int numOfRows;
    private final int numOfCols;

    private static final int NORTH = -1;
    private static final int SOUTH = 1;
    private static final int WEST = -1;
    private static final int EAST = 1;

    private final mapDataPoint[][] theMap;

    public mapData(final int theNumOfRows, final int theNumOfCols) {
        numOfRows = theNumOfRows;
        numOfCols = theNumOfCols;
        theMap = new mapDataPoint[theNumOfRows][theNumOfCols];
        IntStream.range(0, numOfRows).forEach(i ->
            IntStream.range(0, numOfCols).forEach(j ->
                theMap[i][j] = new mapDataPoint()
            )
        );
    }

    public void tick() {
        IntStream.range(0, numOfRows).forEach(row ->
            IntStream.range(0, numOfCols).forEach(col ->
                spreadPoint(theMap[row][col], row, col)
            )
        );
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
        mapDataPoint point = theMap[theRow][theCol];
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
