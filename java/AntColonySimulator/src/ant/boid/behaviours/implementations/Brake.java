package ant.boid.behaviours.implementations;
import ant.boid.Boid;
import ant.boid.behaviours.Behaviour;
import processing.core.PVector;

public class Brake extends Behaviour {

	public Brake(float weight) {
		super(weight);
	}
	
	public PVector getDesiredVelocity(Boid me) {
		//return me.getVel().copy().mult(-1);
		return new PVector(0,0);
	}

}
