package pk.com.habsoft.robosim.planning.algos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import pk.com.habsoft.robosim.planning.internal.DiscreteWorld;
import pk.com.habsoft.robosim.planning.internal.Path;
import pk.com.habsoft.robosim.planning.internal.PathNode;
import pk.com.habsoft.robosim.planning.internal.WorldNode;

public class DFSAlgorithm extends Algorithm {

	// PriorityQueue<WorldState> q = new PriorityQueue<WorldState>();
	LinkedList<WorldNode> stack = new LinkedList<WorldNode>();
	List<WorldNode> closed = new ArrayList<WorldNode>();
	private boolean allowDiagonalMotion;

	int collision_cost = 5000;
	double sProb = 0.7;
	double fProb = (1 - sProb) / 2;

	public DFSAlgorithm(DiscreteWorld world, boolean diagonalMotion) {
		this.world = world;
		this.allowDiagonalMotion = diagonalMotion;
		path = new Path();

		policy = new int[world.getRows()][world.getColumns()];
		expand = new double[world.getRows()][world.getColumns()];
		solve();
	}

	@Override
	public int getBlockedSize() {
		return blocked;
	}

	@Override
	public double[][] getExpand() {
		return expand;
	}

	@Override
	public int getExploredSize() {
		return exploredSize;
	}

	@Override
	public double[][] getHeuristic() {
		return new double[world.getRows()][world.getColumns()];
	}

	@Override
	public Path getPath() {
		return path;
	}

	@Override
	public int getPathSize() {
		return pathSize;
	}

	@Override
	public int[][] getPolicy() {
		return policy;
	}

	@Override
	public String getResult() {
		return result;
	}

	@Override
	public int getTotalInstances() {
		return instances;
	}

	@Override
	public int getTotalSize() {
		return exploredSize + unExploredSize + blocked;
	}

	@Override
	public int getUnExploredSize() {
		return unExploredSize;
	}

	@Override
	void solve() {
		for (int i = 0; i < world.getRows(); i++) {
			for (int j = 0; j < world.getColumns(); j++) {
				expand[i][j] = -1;
				if (world.isHidden(i, j)) {
					policy[i][j] = HIDDEN;
				} else if (world.isOpen(i, j)) {
					policy[i][j] = NOT_EXPLORED;
				} else {
					policy[i][j] = BLOCK;
					blocked++;
				}
			}
		}
		// Cost of each node
		int cost = 1;
		// w.setStartNode(0, 0);
		// w.setGoalNode(world[0].length, world.length);

		boolean solve = false;
		boolean resign = false;
		WorldNode start = world.getTempStart();
		stack.push(start);
		closed.add(start);
		while (!solve && !resign) {
			if (stack.isEmpty()) {
				resign = true;
			} else {
				WorldNode current = stack.pop();
				int x = current.getxLoc();
				int y = current.getyLoc();
				int g = current.getDepth();
				if (world.isHidden(x, y)) {
					policy[x][y] = HIDDEN;
				} else {
					expand[x][y] = exploredSize++;
					policy[x][y] = EXPLORED;
				}
				if (world.isGoal(current)) {
					solve = true;
					result = "Goal Found";
				} else {
					int incr = allowDiagonalMotion ? 1 : 2;
					for (int i = 0; i < deltas.length; i = i + incr) {
						int x2 = x + deltas[i][0];
						int y2 = y + deltas[i][1];
						// if path is open
						// double v2 = cost;
						// v2 += sProb * get_value(x, y, i);
						// v2 += fProb * get_value(x, y, Util.modulus(i - 1,
						// d.length, true));
						// v2 += fProb * get_value(x, y, Util.modulus(i + 1,
						// d.length, true));
						if (world.isOpen(x2, y2)) {
							int g2 = g + cost;
							WorldNode toExplore = new WorldNode(x2, y2);
							instances++;
							if (!closed.contains(toExplore)) {
								toExplore.setDepth(g2);
								toExplore.setAction(i);
								stack.push(toExplore);
								closed.add(toExplore);
							}
						}
					}
				}
			}
		}
		unExploredSize = world.getRows() * world.getColumns() - exploredSize - blocked;

		WorldNode last = new WorldNode(world.getStart().getxLoc(), world.getStart().getyLoc());
		if (solve) {
			last = new WorldNode(closed.get(closed.indexOf(world.getGoal())));
			path.add(new PathNode(last.getxLoc(), last.getyLoc()));

		}

		String[][] temp = new String[policy.length][policy[0].length];
		if (last != null) {
			while (last.getAction() != -1) {
				pathSize++;
				int x2 = last.getxLoc() - deltas[last.getAction()][0];
				int y2 = last.getyLoc() - deltas[last.getAction()][1];
				policy[x2][y2] = last.getAction();
				temp[x2][y2] = DELTA_NAMES[last.getAction()];
				last.setxLoc(x2);
				last.setyLoc(y2);
				last = closed.get(closed.indexOf(last));
				path.add(new PathNode(last.getxLoc(), last.getyLoc()));

			}
			if (pathSize == 1) {
				pathSize = 0;
			}
		}
		// for (String[] strings : temp) {
		// System.out.println(Arrays.toString(strings));
		// }
		// for (int[] strings : expand) {
		// System.out.println(Arrays.toString(strings));
		// }
		// System.out.println(world.printWorld());

	}
}
