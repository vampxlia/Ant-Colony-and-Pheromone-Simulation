package ant.boid.physics;

import processing.core.PVector;
import utils.SceneObject;

public abstract class Move implements SceneObject {
	protected PVector pos;
	protected PVector vel;
	protected PVector acc;
	protected float mass;

    protected Move(PVector pos, PVector vel, float mass) {
		this.pos = pos.copy();
		this.vel = vel;
		this.mass = mass;
		acc = new PVector();
	}

	public void applyForce(PVector force) {
		acc.add(PVector.div(force, mass));
	}

	public void move(float dt) {
		vel.add(acc.mult(dt));
		wrapAround();
		pos.add(PVector.mult(vel,  dt));
		acc.mult(0);
	}

	private void wrapAround() {

		float xmin = -10;
		float xmax =  10;
		float ymin = -10;
		float ymax =  10;
		if (pos.x < xmin || pos.y < ymin || pos.x > xmax || pos.y > ymax) this.vel.rotate(180); //rodar a dar 'bounce' das paredes
	}

	@Override
	public PVector getPos() {
		return pos;
	}
	public void setVel(PVector vel) {
		this.vel = vel;
	}
	
	public PVector getVel() {
		return vel;
	}
}
