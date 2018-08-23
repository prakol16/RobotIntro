/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1683.robot;

import org.usfirst.frc.team1683.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1683.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import motor.TalonSRX;
import sensors.QuadEncoder;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	public static final boolean LEFT_REVERSE = false;
	public static final boolean RIGHT_REVERSE = true;
	
	public static Gyro gyro;
	
	public static TalonSRX grabberLeft, grabberRight, elevatorTalon,
		leftETalonSRX, rightETalonSRX;
	
	public static DriveTrain drive;
	
	private static final double INCHES_PER_PULSE = 1; // configure
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		gyro = new AnalogGyro(HWR.GYRO);
		
		grabberLeft = new TalonSRX(HWR.GRABBER_LEFT, false);
		grabberRight = new TalonSRX(HWR.GRABBER_RIGHT, false);
		elevatorTalon = new TalonSRX(HWR.ELEVATOR_MAIN, false);
		elevatorTalon.setEncoder(new QuadEncoder(elevatorTalon, 0.00112, true));
		leftETalonSRX = new TalonSRX(HWR.LEFT_DRIVE_TRAIN_FRONT, LEFT_REVERSE);
		rightETalonSRX = new TalonSRX(HWR.RIGHT_DRIVE_TRAIN_FRONT, RIGHT_REVERSE);
		leftETalonSRX.setEncoder(new QuadEncoder(leftETalonSRX, INCHES_PER_PULSE, true));
		rightETalonSRX.setEncoder(new QuadEncoder(rightETalonSRX, INCHES_PER_PULSE, true));
		
		TalonSRX leftFollow1 = new TalonSRX(HWR.LEFT_DRIVE_TRAIN_MIDDLE, LEFT_REVERSE),
				 leftFollow2 = new TalonSRX(HWR.LEFT_DRIVE_TRAIN_BACK, LEFT_REVERSE),
				 rightFollow1 = new TalonSRX(HWR.RIGHT_DRIVE_TRAIN_MIDDLE, RIGHT_REVERSE),
				 rightFollow2 = new TalonSRX(HWR.RIGHT_DRIVE_TRAIN_BACK, RIGHT_REVERSE);

		leftETalonSRX.setupCurrentLimiting();
		rightETalonSRX.setupCurrentLimiting();
		leftFollow1.setupCurrentLimiting();
		leftFollow2.setupCurrentLimiting();
		rightFollow1.setupCurrentLimiting();
		rightFollow2.setupCurrentLimiting();
		
		drive = new TankDrive(leftETalonSRX, rightETalonSRX, gyro);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
	}

	/**
	 * Called once at the beginning of autonomous
	 */
	@Override
	public void autonomousInit() {
		
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		// Before we used Command.runAllCommands(); This is the wpilib equivalent
		Scheduler.getInstance().run();
		
		// debug
		SmartDashboard.putNumber("Gyro angle", gyro.getAngle());
		SmartDashboard.putNumber("Left Encoder Value", drive.getLeftEncoder().getDistance());
		SmartDashboard.putNumber("Right Encoder Value", drive.getRightEncoder().getDistance());
	}

	@Override
	public void teleopInit() {
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		

		// debug
		SmartDashboard.putNumber("Gyro angle", gyro.getAngle());
		SmartDashboard.putNumber("Left Encoder Value", drive.getLeftEncoder().getDistance());
		SmartDashboard.putNumber("Right Encoder Value", drive.getRightEncoder().getDistance());
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
