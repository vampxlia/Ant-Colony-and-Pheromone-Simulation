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
	public Boid(PVector pos, PVector vel, float mass, PImage img, DNA dna, PApplet p, SubPlot plt) {
		super(pos, vel, mass, 0.5f, p.color(255));
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
		vector_direction.normalize().mult(10);
		return PVector.sub(vector_direction, vel);

	}

}
