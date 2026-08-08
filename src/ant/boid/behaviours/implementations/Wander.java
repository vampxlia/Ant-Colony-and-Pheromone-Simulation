package ant.boid.behaviours.implementations;

import ant.boid.Boid;
import ant.boid.behaviours.Behaviour;
import processing.core.PVector;

public class Wander extends Behaviour {

	public Wander(float weight) {
		super(weight);
	}
	
	@Override
	public PVector getDesiredVelocity(Boid me) {
		PVector center = me.getPos().copy();
		center.add(me.getVel().mult(me.dna.deltaTWander));
		PVector target = new PVector(me.dna.radiusWander * (float)Math.cos(me.phiWander), me.dna.radiusWander * (float)Math.sin(me.phiWander));
		target.add(center);
		me.phiWander+= (float) (2*(Math.random()-0.5*me.dna.deltaPhiWander));
		return PVector.sub(target, me.getPos());
	}
	
}
