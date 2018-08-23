package org.usfirst.frc.team1683.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import motor.TalonSRX;
import sensors.Encoder;

public abstract class DriveTrain extends Subsystem {

	public abstract void set(double speed);
	
	public abstract void set(double leftSpeed, double rightSpeed);

	public abstract void stop();

	public abstract void coast();

	public abstract void resetEncoders();

	public abstract Encoder getLeftEncoder();

	public abstract Encoder getRightEncoder();

	public abstract TalonSRX getLeft();

	public abstract TalonSRX getRight();

	public abstract Gyro getGyro();

	/**
	 * @param right
	 *            True if should turn right (clockwise), false if left
	 * @param speed
	 *            Speed of motors
	 */
	public abstract void turnInPlace(boolean right, double speed);

	public abstract void setLeft(double speed);

	public abstract void setRight(double speed);
	
	public abstract double[] getSpeed();
	
	public abstract void enableBrownoutProtection();
	
	public abstract void disableBrownoutProtection();
}
