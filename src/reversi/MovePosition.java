package reversi;

/**
 * MovePosition
 * Utilisée pour contenir un mouvement possible
 */
public class MovePosition {
	private int row;
	private int column;
	
	public MovePosition(int row, int column) {
		this.setRow(row);
		this.setColumn(column);
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	@Override
	public String toString() {
		return "[ " + row + ", " + column + " ]";
	}
}
