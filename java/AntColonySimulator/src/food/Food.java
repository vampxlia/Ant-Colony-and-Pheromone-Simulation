package food;

import ant.Ant;
import ant.boid.Boid;
import ant.boid.physics.Body;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import utils.SceneObject;
import utils.gui.SubPlot;

public class Food implements SceneObject {
    private Body body;
    private PImage foodImage;
    private PApplet p;
    private SubPlot plt;
    private PVector pos;
    private float radius;
    private float intensity;
    public Food(PVector pos, float radius, PImage foodImage, PApplet p, SubPlot plt){
        this.pos = pos;
        this.foodImage = foodImage;
        this.radius = radius;
        this.p = p;
        this.plt = plt;
        this.intensity = 1f;
    }
    public PVector getPos() {
        return pos;
    }
    public float getIntensity(){
        return intensity;
    }
    public float getRadius(){
        return radius;
    }
    public void display(PApplet p, SubPlot plt){
        float[] rr = plt.getVectorCoord(radius, radius);
        float[] pp = plt.getPixelCoord(pos.x, pos.y);
        p.imageMode(p.CENTER);
        p.image(foodImage, pp[0],pp[1], rr[0], rr[1]);
    }
}