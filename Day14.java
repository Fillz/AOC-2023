import java.util.ArrayList;
import java.util.HashMap;

public class Day14 {
	public static void main(String[] args) {
		new Day14();
	}

	enum Direction {
		NORTH,
		SOUTH,
		EAST,
		WEST
	}

	public Day14() {
		ArrayList<String> input = ReadInput.read("res/input14.txt");
		char[][] map = new char[input.get(0).length()][input.size()];

		for (int y = 0; y < input.size(); y++) {
			for (int x = 0; x < input.get(0).length(); x++) {
				map[x][y] = input.get(y).charAt(x);
			}
		}
		char[][] map2 = copyCharArray(map);

		partOne(map);
		partTwo(map2);
	}

	private void partOne(char[][] map) {
		tiltPlatform(map, Direction.NORTH);
		System.out.println(calculateLoad(map));
	}

	private void partTwo(char[][] map) {
		HashMap<String, Integer> cache = new HashMap<String, Integer>();

		int cycleStart = -1;
		int cycleEnd = -1;
		for (int i = 1; i <= 1000000000; i++) {
			tiltPlatform(map, Direction.NORTH);
			tiltPlatform(map, Direction.WEST);
			tiltPlatform(map, Direction.SOUTH);
			tiltPlatform(map, Direction.EAST);

			if (cache.get(getMapAsString(map)) != null) {
				cycleEnd = i;
				cycleStart = cache.get(getMapAsString(map));
				break;
			}
			cache.put(getMapAsString(map), i);
		}

		int amountToRun = (1000000000 - cycleStart) % (cycleEnd - cycleStart);

		for (int i = 1; i <= amountToRun; i++) {
			tiltPlatform(map, Direction.NORTH);
			tiltPlatform(map, Direction.WEST);
			tiltPlatform(map, Direction.SOUTH);
			tiltPlatform(map, Direction.EAST);
		}
		System.out.println(calculateLoad(map));
	}

	private String getMapAsString(char[][] map) {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < map[0].length; y++) {
			for (int x = 0; x < map.length; x++) {
				sb.append(map[x][y]);
			}
		}
		return sb.toString();
	}

	private int calculateLoad(char[][] map) {
		int res = 0;
		for (int y = 0; y < map[0].length; y++) {
			for (int x = 0; x < map.length; x++) {
				if (map[x][y] == 'O')
					res += map[0].length - y;
			}
		}
		return res;
	}

	private void tiltPlatform(char[][] map, Direction direction) {
		if (direction == Direction.NORTH) {
			for (int y = 0; y < map[0].length; y++) {
				for (int x = 0; x < map.length; x++) {
					if (map[x][y] == 'O')
						moveRock(x, y, Direction.NORTH, map);
				}
			}
		} else if (direction == Direction.SOUTH) {
			for (int y = map[0].length - 1; y >= 0; y--) {
				for (int x = 0; x < map.length; x++) {
					if (map[x][y] == 'O')
						moveRock(x, y, Direction.SOUTH, map);
				}
			}
		} else if (direction == Direction.WEST) {
			for (int x = 0; x < map.length; x++) {
				for (int y = 0; y < map[0].length; y++) {
					if (map[x][y] == 'O')
						moveRock(x, y, Direction.WEST, map);
				}
			}
		} else {
			for (int x = map.length - 1; x >= 0; x--) {
				for (int y = 0; y < map[0].length; y++) {
					if (map[x][y] == 'O')
						moveRock(x, y, Direction.EAST, map);
				}
			}
		}
	}

	private void moveRock(int x, int y, Direction direction, char[][] map) {
		int xDir;
		int yDir;
		if (direction == Direction.NORTH) {
			xDir = 0;
			yDir = -1;
		} else if (direction == Direction.SOUTH) {
			xDir = 0;
			yDir = 1;
		} else if (direction == Direction.WEST) {
			xDir = -1;
			yDir = 0;
		} else {
			xDir = 1;
			yDir = 0;
		}
		int newX = x;
		int newY = y;
		while (true) {
			int xStep = newX + xDir;
			int yStep = newY + yDir;
			if (xStep < 0 || xStep >= map.length || yStep < 0 || yStep >= map[0].length || map[xStep][yStep] == '#'
					|| map[xStep][yStep] == 'O')
				break;
			newX = xStep;
			newY = yStep;
		}

		if (newX == x && newY == y)
			return;

		map[newX][newY] = map[x][y];
		map[x][y] = '.';
	}

	private char[][] copyCharArray(char[][] map) {
		char[][] newMap = new char[map.length][map[0].length];
		for (int y = 0; y < map[0].length; y++) {
			for (int x = 0; x < map.length; x++) {
				newMap[x][y] = map[x][y];
			}
		}
		return newMap;
	}
}
