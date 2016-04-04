package pk.com.habsoft.robosim.filters.particles;

public enum RobotType {
	PARTICLE(0, "Particle"), ROBOT(1, "Robot"), GHOST(2, "Ghost");
	int type;

	String name;

	RobotType(int type, String name) {
		this.type = type;
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
