package ant;

import ant.behaviours.Behaviour;
import ant.physics.Body;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import utils.gui.SubPlot;

import java.util.ArrayList;

public class Boid extends Body {

	private SubPlot plt;
	private PImage img;
	private ArrayList<Behaviour> behaviours;
	public DNA dna;
	public Boid(PVector pos, PVector vel, float mass, PImage img, PApplet p, SubPlot plt) {
		super(pos, vel, mass, 0.5f, p.color(255));
		dna = new DNA();
		behaviours = new ArrayList<>();
		this.plt = plt;
		this.img = img;
		setShape(p, plt);
	}

	public void setShape(PApplet p, SubPlot plt){
		//
		//p.imageMode(p.CENTER);
		//p.image(img, 0, 0, );
	}

	public void addBehaviour(Behaviour behaviour){
		behaviours.add(behaviour);
	}

	public void removeBehaviour(Behaviour behaviour){
		behaviours.remove(behaviour);
	}


	@Override
	public void display(PApplet p, SubPlot plt){
		float[] rr = plt.getVectorCoord(radius, radius);
		float[] pp = plt.getPixelCoord(pos.x, pos.y);
		p.translate(pp[0], pp[1]);
		p.rotate(vel.heading());
		p.imageMode(p.CENTER);
		p.image(img, 0,0, rr[0], rr[1]);
	}

	public PVector seek(PVector target){
		PVector vector_direction = PVector.sub(target, pos);
		vector_direction.normalize().mult(dna.maxSpeed);
		return PVector.sub(vector_direction, vel);
	}

}
