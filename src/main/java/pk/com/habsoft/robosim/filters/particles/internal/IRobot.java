package pk.com.habsoft.robosim.filters.particles.internal;

import pk.com.habsoft.robosim.filters.particles.RobotType;

public interface IRobot extends Cloneable, SimulationObject {

	double MAX_STEERING_ANGLE = Math.PI / 4.0;

	IRobot clone() throws CloneNotSupportedException;

	public double getForward_noise();

	public double getLength();

	public double getOrientation();

	public RobotType getRobot_type();

	public double getSense_noise();

	public double getSteering_drift();

	public double getSteering_noise();

	public double getX();

	public double getY();

	public double measurement_prob(double[] measurements);

	public void move(double steering, double speed);

	public void move(double[] motions);

	public void random();

	public double[] sense(boolean addNoise);

	public void setBoundedVision(boolean boundedVision);

	public void setCheckBoundaries(boolean checkBoundaries);

	public void setLaserAngle(int laserAngle);

	public void setLaserRange(int laserRange);

	public void setLocation(double x, double y, double orientation);

	public void setLocation(double[] measurements);

	public void setNoise(double sense_noise, double steering_noise, double forward_noise);

	public void setOrientation(double orientation);

	public void setSteering_drift(double steering_drift);

	public void setX(double x);

	public void setY(double y);

	public void update(IRobot obj);

}
