package ant;

import ant.behaviours.Behaviour;
import ant.physics.Body;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;
import simple_subplot.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class Boid extends Body {

	private PShape shape;
	protected Eye eye;
	protected List<Behaviour> behaviours;
	public float phiWander;
	protected float maxSpeed;
	private double[] window;
	private float sumWeights;
	protected Boid(PVector pos, float mass, float radius, int color, PApplet p, SubPlot plt) {
		super(pos, new PVector(), mass, radius, color);
		this.maxSpeed = dna.maxSpeed;
		behaviours = new ArrayList<Behaviour>();
		window = plt.getWindow();
		setShape(p, plt);
	}

	public List<Behaviour> getBehaviours() {
		return behaviours;
	}
	
	public void setEye(Eye eye) {
		this.eye = eye;
	}
	
	public Eye getEye() {
		return eye;
	}
	
	public void setShape(PApplet p, SubPlot plt) {
		float[] rr = plt.getVectorCoord(radius, radius);
		shape = p.createShape();
		shape.beginShape();
		shape.noStroke();
		shape.fill(color);
		shape.vertex(-rr[0], rr[0]/2);
		shape.vertex(rr[0], 0);
		shape.vertex(-rr[0], -rr[0]/2);
		shape.vertex(-rr[0]/2, 0);
		shape.endShape(PConstants.CLOSE);
	}
	
	private void updateSumWeights() {
		sumWeights = 0;
		for (Behaviour beh : behaviours) {
			sumWeights += beh.getWeight();
		}
	}
	
	public void addBehaviour(Behaviour behaviour) {
		behaviours.add(behaviour);
		updateSumWeights();
	}
	
	public void removeBehaviour(Behaviour behaviour) {
		if (behaviours.contains(behaviour)) {
			behaviours.remove(behaviour);
		}
	}
	
	public void applyBehaviour(int i, float dt) {
		if (eye != null) eye.look();
		Behaviour behaviour = behaviours.get(i);
		PVector vd = behaviour.getDesiredVelocity(this);
		move(dt, vd);
	}
	
	
	public void applyBehaviours(float dt) {
		if (eye != null) eye.look();
		
		PVector vd = new PVector();
		for (Behaviour behaviour: behaviours) {
			PVector vdd = behaviour.getDesiredVelocity(this);
			vdd.mult(behaviour.getWeight()/sumWeights);
			vd.add(vdd);
		}
		move(dt,vd);
	}
	
	private void move(float dt, PVector vd) {
		vd.normalize().mult(this.maxSpeed);
		PVector fs = PVector.sub(vd, vel);
		applyForce(fs.limit(dna.maxForce));
		super.move(dt);
		if(pos.x < window[0]) {
			pos.x += (float) (window[1] - window[0]);
		}
		if(pos.y < window[2]) {
			pos.y += (float) (window[3] - window[2]);
		}
		if(pos.x >= window[1]) {
			pos.x -= (float) (window[1] - window[0]);
		}
		if(pos.y >= window[3]) {
			pos.y -= (float) (window[3] - window[2]);
		}
	}
	
	public void display(PApplet p, SubPlot plt) {
		p.pushMatrix();
		float[] pp = plt.getPixelCoord(pos.x, pos.y);
		float[] vv = plt.getVectorCoord(vel.x, vel.y);
		PVector vaux = new PVector(vv[0], vv[1]);
		p.translate(pp[0], pp[1]);
		p.rotate(-vaux.heading());
		p.shape(shape);
		p.popMatrix();
	}

}
