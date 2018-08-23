package motor;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.interfaces.Gyro;
import sensors.Encoder;

/*
 * Motor control (talonSRX)
 */
public class TalonSRX extends com.ctre.phoenix.motorcontrol.can.TalonSRX implements Motor {

	private Encoder encoder;
	Gyro gyro;
	public static final int CURRENT_LIMIT = 41;
	public static final int CURRENT_LIMIT_THRESHOLD = 41;
	public static final int LIMIT_TIMEOUT = 200; //ms

	private TalonSRX brownoutFollower = null;
	private boolean brownout = false;

	/**
	 * Constructor for a TalonSRX motor
	 *
	 * @param channel
	 *            The port where the TalonSRX is plugged in.
	 * @param reversed
	 *            If the TalonSRX should invert the signal.
	 */
	public TalonSRX(int channel, boolean reversed) {
		super(channel);
		super.setInverted(reversed);
	}

	/**
	 * Constructor
	 *
	 * @param channel
	 *            The port where the TalonSRX is plugged in.
	 * @param reversed
	 *            If the TalonSRX should invert the signal.
	 * @param encoder
	 *            Encoder to attach to this TalonSRX.
	 */
	public TalonSRX(int channel, boolean reversed, Encoder encoder) {
		super(channel);
		super.setInverted(reversed);

		this.encoder = encoder;
	}

	/**
	 * Set the speed of the TalonSRX.
	 *
	 * @param speed
	 *            -- Speed from 0 to 1 (or negative for backwards)
	 */
	@Override
	public void set(double speed) {
		super.set(ControlMode.PercentOutput, speed);
	}
	
	@Override
	public void brake() {
		this.set(0);
		super.setNeutralMode(NeutralMode.Brake);
	}

	@Override
	public void coast() {
		this.set(0);
		super.setNeutralMode(NeutralMode.Coast);
	}

	@Override
	public boolean hasEncoder() {
		return !(encoder == null);
	}

	@Override
	public Encoder getEncoder() {
		return encoder;
	}

	public void setEncoder(Encoder encoder) {
		this.encoder = encoder;
	}

	// TODO: make sure this works.
	@Override
	public int getChannel() {
		return super.getDeviceID();
	}

	@Override
	public boolean isReversed() {
		return super.getInverted();
	}

	@Override
	public double getPercentSpeed() {
		return super.getMotorOutputPercent();
	}
	
	@Override
	public double getSpeed() {
		if (!hasEncoder())
			return 0;
		return encoder.getSpeed();
	}
	
	public double getError() {
		return super.getClosedLoopError(0);
	}

	@Override
	public void stop() {
		set(0);
	}
	
	public void follow(TalonSRX other) {
		other.brownoutFollower = this;
		this.set(ControlMode.Follower, other.getChannel());
	}


	public void setupCurrentLimiting() {

		this.configContinuousCurrentLimit(CURRENT_LIMIT, 0);
		this.configPeakCurrentLimit(CURRENT_LIMIT_THRESHOLD, 0);
		this.configPeakCurrentDuration(LIMIT_TIMEOUT, 0);
		this.enableCurrentLimit(true);
	}

	public void disableCurrentLimiting() {
	    this.enableCurrentLimit(false);
    }

    public void enableBrownoutProtection() {
		if (brownoutFollower != null) {
			brownoutFollower.coast();
		}
		brownout = true;
	}

	public void disableBrownoutProtection() {
		if (brownoutFollower != null && brownout) {
			brownoutFollower.setNeutralMode(NeutralMode.Brake);
			brownoutFollower.set(ControlMode.Follower, getChannel());
		}
		brownout = false;
	}

}
