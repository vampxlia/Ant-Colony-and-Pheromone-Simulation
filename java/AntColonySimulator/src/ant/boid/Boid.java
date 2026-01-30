package ant.boid;

import ant.AntState;
import ant.boid.behaviours.Behaviour;
import ant.boid.physics.Body;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import utils.gui.SubPlot;

import java.util.ArrayList;

public class Boid extends Body {

	public AntState state;
	private SubPlot plt;
	private PImage img;
	private ArrayList<Behaviour> behaviours;
	public DNA dna;
	public Eye eye;
	public float phiWander;
	public Boid(PVector pos, PVector vel, float mass, PImage img, PApplet p, SubPlot plt) {
		super(pos, vel, mass, 0.2f, p.color(255));
		dna = new DNA();
		behaviours = new ArrayList<>();
		this.plt = plt;
		this.img = img.copy();
		setShape(p, plt);
	}

	public void setEye(Eye eye){
		this.eye = eye;
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

	public void applyBehavious(float dt){
		eye.look();

		PVector vd = new PVector();
		for (Behaviour behaviour: behaviours){
			PVector vdd = behaviour.getDesiredVelocity(this);
			if (vdd != null){
				vdd.mult(behaviour.getWeight());
				vd.add(vdd);
				break;
			}
		}
		move(dt, vd);
	}

	private void move(float dt, PVector vd){
		vd.normalize().mult(dna.maxSpeed);
		PVector fs = PVector.sub(vd, vel);
		applyForce(fs.limit(dna.maxSpeed));
		super.move(dt);
	}

	@Override
	public void display(PApplet p, SubPlot plt){
		//TODO imagens não estão a representar corretamente a posição por razões que me iludem
		//por agora deixei um círculo branco só para dar para ver algo
		//remover super quando estiver fixed
		super.display(p, plt);

		//float[] rr = plt.getVectorCoord(radius, radius);
		//float[] pp = plt.getPixelCoord(pos.x, pos.y);
		//p.translate(pp[0], pp[1]);
		//p.rotate(vel.heading());
		//p.imageMode(p.CENTER);
		//p.image(img, 0,0, rr[0], rr[1]);
	}
}
