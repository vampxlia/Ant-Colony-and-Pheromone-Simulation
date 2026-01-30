package ant.boid.behaviours;

import ant.boid.Boid;
import processing.core.PVector;

public interface IBehaviour {
	public PVector getDesiredVelocity(Boid me);
	public void setWeight(float weight);
	public float getWeight();
}
