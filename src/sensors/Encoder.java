package sensors;

public interface Encoder {

	public double getDistance();
	
	public double getSpeed();

	public void reset();
}
