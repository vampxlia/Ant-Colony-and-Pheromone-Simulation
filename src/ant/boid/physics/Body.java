package ant.boid.physics;

import ant.AntState;
import processing.core.PApplet;
import processing.core.PVector;
import utils.gui.SubPlot;

public class Body extends Move {
	protected int color;
	protected float radius;
	public Body(PVector pos, PVector vel, float mass, float radius, int color) {
		super(pos, vel, mass);
		this.color = color;
		this.radius = radius;
	}

	@Override
	public float getIntensity(AntState state) {
		return 0;
	}

	public float getRadius(){
		return radius;
	}

	
	public void display(PApplet p, SubPlot plt) {
		p.pushStyle();
		float[] pp = plt.getPixelCoord(pos.x, pos.y);
		float[] r = plt.getVectorCoord(radius, radius);
		p.noStroke();
		p.fill(color);
		p.circle(pp[0], pp[1], r[0]);
		p.popStyle();
	}
}
