package ant.boid.physics;

import processing.core.PVector;
import utils.SceneObject;

public abstract class Move implements SceneObject {
	protected PVector pos;
	protected PVector vel;
	protected PVector acc;
	protected float mass;
	private static double G = 6.67e-11;
	
	protected Move(PVector pos, PVector vel, float mass) {
		this.pos = pos.copy();
		this.vel = vel;
		this.mass = mass;
		acc = new PVector();
	}

	public void applyForce(PVector force) {
		acc.add(PVector.div(force, mass));
	}
	
	public PVector attraction(Move m) {
		PVector r = PVector.sub(pos,  m.pos);
		float dist = r.mag();
		float strength = (float) (G*mass*m.mass/ Math.pow(dist, 2));
		return r.normalize().mult(strength);
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

		//if (pos.x < xmin) pos.x = xmax;
		//else if (pos.x > xmax) pos.x = xmin;

		//if (pos.y < ymin) pos.y = ymax;
		//else if (pos.y > ymax) pos.y = ymin;
	}


	public void setPos(PVector pos) {
		this.pos = pos;
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
