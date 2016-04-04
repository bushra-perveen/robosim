package pk.com.habsoft.robosim.planning.algos;

import java.util.Arrays;

import pk.com.habsoft.robosim.utils.RoboMathUtils;

public class TestDPPP {

	public static void main(String[] args) {
		TestDPPP d = new TestDPPP();
		d.search();
	}

	int[][] grid = { { 1, 1, 1, 0, 0, 0 }, { 1, 1, 1, 0, 1, 0 }, { 0, 0, 0, 0, 0, 0 }, { 1, 1, 1, 0, 1, 1 }, { 1, 1, 1, 0, 1, 1 } };

	int[] goal = { 2, 0 };
	int[] init = { 4, 3, 0 };

	int[][] forward = { { -1, 0 }, // go up
			{ 0, -1 }, // go left
			{ 1, 0 }, // go down
			{ 0, 1 } };// go right
	// String[] forward_names = { "up", "left", "down", "right" };
	// char[] forward_names = { 'U', 'L', 'D', 'R' };

	int[] cost = { 1, 1, 15 };
	int[] action = { -1, 0, 1 };
	char[] action_names = { 'R', '#', 'L' };
	int g = -100;

	void search() {

		int[][][] value = new int[4][grid.length][grid[0].length];
		int[][][] policy3D = new int[4][grid.length][grid[0].length];
		// int[][] policy2D = new int[grid.length][grid[0].length];
		int[][] policy = new int[grid.length][grid[0].length];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < grid.length; j++) {
				for (int k = 0; k < grid[j].length; k++) {
					value[i][j][k] = 9999;
					policy3D[i][j][k] = 0;
					// policy2D[j][k] = -2;
					policy[j][k] = Algorithm.NOT_EXPLORED;
				}
			}
		}
		boolean change = true;
		while (change) {
			change = false;
			for (int x = 0; x < grid.length; x++) {
				for (int y = 0; y < grid[x].length; y++) {
					for (int j = 0; j < forward.length; j++) {
						if (goal[0] == x && goal[1] == y) {
							if (value[j][x][y] > 0) {
								value[j][x][y] = 0;
								policy3D[j][x][y] = g;
								change = true;
							}
						} else if (grid[x][y] == 0) {
							for (int i = 0; i < action.length; i++) {
								int o2 = RoboMathUtils.modulus((j + action[i]), 4, false);
								int x2 = x + forward[o2][0];
								int y2 = y + forward[o2][1];
								if (x2 >= 0 && x2 < grid.length && y2 >= 0 && y2 < grid[0].length && grid[x2][y2] == 0) {
									int v2 = value[o2][x2][y2] + cost[i];
									if (v2 < value[j][x][y]) {
										change = true;
										value[j][x][y] = v2;
										policy3D[j][x][y] = i;

									}
								}
							}
						}
					}
				}
			}
		}

		int x = init[0];
		int y = init[1];
		int orientation = init[2];
		int o2 = orientation;

		// policy2D[x][y] = policy[orientation][x][y];
		// pol[x][y] = forward_names[orientation];
		while (policy3D[orientation][x][y] != g) {
			// if (action_names[policy[orientation][x][y]] == '#') {
			// o2 = orientation;
			// } else if (action_names[policy[orientation][x][y]] == 'R') {
			// o2 = Util.modulus((orientation - 1), 4, false);
			// } else if (action_names[policy[orientation][x][y]] == 'L') {
			// o2 = Util.modulus((orientation + 1), 4, false);
			// }
			o2 = RoboMathUtils.modulus((orientation + action[policy3D[orientation][x][y]]), 4, false);
			policy[x][y] = o2;
			x = x + forward[o2][0];
			y = y + forward[o2][1];
			orientation = o2;
			// if (policy[orientation][x][y]<4 && policy[orientation][x][y]>=0)
			// {
			// policy2D[x][y] = action[policy[orientation][x][y]];
			// }
		}

		// for (int[] arr : policy2D) {
		// System.out.println(Arrays.toString(arr));
		// }
		System.out.println("-------------");
		for (int[] arr : policy) {
			System.out.println(Arrays.toString(arr));
		}
	}
}
