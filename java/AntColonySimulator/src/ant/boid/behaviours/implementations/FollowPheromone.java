package ant.boid.behaviours.implementations;

import ant.Ant;
import ant.AntState;
import ant.boid.Boid;
import ant.boid.behaviours.Behaviour;
import pheromone.Pheromones;
import processing.core.PVector;

public class FollowPheromone extends Behaviour {

	private Pheromones pheromones;

	public FollowPheromone(float weight, Pheromones pheromones) {
		super(weight);
		this.pheromones = pheromones;
	}

	@Override
	public PVector getDesiredVelocity(Boid me) {

		if (!(me instanceof Ant)) return new PVector(0,0);

		Ant ant = (Ant) me;

		// se ainda não há sistema de feromonas, não interfere
		if (pheromones == null) {
			return new PVector(0, 0);
		}

		PVector force = pheromones.getSteeringForce(
				ant.getPos(),
				ant.getState()
		);

		// sem gradiente → não corrige trajetória
		if (force.magSq() == 0) {
			return new PVector(0,0);
		}

		// desired velocity
		force.normalize();
		force.mult(ant.dna.maxSpeed);

		// steering = desired - current velocity
		return PVector.sub(force, ant.getVel());
	}
}


