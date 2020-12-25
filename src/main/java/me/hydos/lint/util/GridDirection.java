package me.hydos.lint.util;

public enum GridDirection {
	UP(0, 0, 1, false),
	RIGHT(1, 1, 0, true),
	DOWN(2, 0, -1, false),
	LEFT(3, -1, 0, true);

	private GridDirection(int id, int xOff, int zOff, boolean horizontal) {
		this.id = id;
		this.xOff = xOff;
		this.zOff = zOff;
		this.horizontal = horizontal;
	}

	public final int id;
	public final int xOff, zOff;
	public final boolean horizontal;

	public GridDirection reverse() {
		switch (this) {
			case UP:
				return DOWN;
			case DOWN:
				return UP;
			case LEFT:
				return RIGHT;
			case RIGHT:
				return LEFT;
			default:
				return null;
		}
	}

	public static final GridDirection[] BY_ID = new GridDirection[4];

	static {
		for (GridDirection d : GridDirection.values()) {
			BY_ID[d.id] = d;
		}
	}
}