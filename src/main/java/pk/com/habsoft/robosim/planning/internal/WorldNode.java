package pk.com.habsoft.robosim.planning.internal;

import java.util.PriorityQueue;

/*
 * This class is used to represent a single node while searching
 * the world
 */
public class WorldNode implements Comparable<WorldNode> {

	private static int instances = 0;

	public static int getInstances() {
		return instances;
	}

	public static void main(String[] args) {
		PriorityQueue<WorldNode> q = new PriorityQueue<WorldNode>();
		for (int i = 0; i < 5; i++) {
			WorldNode s = new WorldNode(i, i);
			s.setHeuristic(5 - i);
			s.setDepth(i * 2);
			q.add(s);
			System.out.println("Adding " + s);
		}
		while (!q.isEmpty()) {
			System.out.println("Poll " + q.poll());
		}
	}

	/**
	 * x,y location of this node in the worlds
	 */
	private int xLoc, yLoc;
	/**
	 * Heuristic value to be used for algorithm.
	 */
	private double heuristic = 0;

	private int depth = 0;

	// private int cost = 0;
	/**
	 * By following which action this node is populated. To trace back the path.
	 */
	private int action = -1;

	public WorldNode(int xLoc, int yLoc) {
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		instances++;
	}

	public WorldNode(WorldNode node) {
		setxLoc(node.getxLoc());
		setyLoc(node.getyLoc());
		setDepth(node.getDepth());
		setAction(node.getAction());
	}

	@Override
	public int compareTo(WorldNode that) {
		int returnValue = 0;
		// f = g + h

		if (this.getCost() == that.getCost()) {
			returnValue = 0;
		} else if (this.getCost() < that.getCost()) {
			returnValue = -1;
		} else {
			returnValue = 1;
		}
		return returnValue;
	}

	@Override
	public boolean equals(Object obj) {
		WorldNode that = (WorldNode) obj;
		return (this.xLoc == that.xLoc && this.yLoc == that.yLoc);
	}

	public int getAction() {
		return action;
	}

	public double getCost() {
		return this.depth + this.heuristic;
	}

	public int getDepth() {
		return depth;
	}

	public double getHeuristic() {
		return heuristic;
	}

	public int getxLoc() {
		return xLoc;
	}

	public int getyLoc() {
		return yLoc;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public void setHeuristic(double heuristic) {
		this.heuristic = heuristic;
	}

	public void setxLoc(int xLoc) {
		this.xLoc = xLoc;
	}

	public void setyLoc(int yLoc) {
		this.yLoc = yLoc;
	}

	@Override
	public String toString() {
		return "WorldState [xLoc=" + xLoc + ", yLoc=" + yLoc + ", heuristic=" + heuristic + ", depth=" + depth + ", Cost=" + getCost()
				+ ", action=" + action + "]";
	}
}
