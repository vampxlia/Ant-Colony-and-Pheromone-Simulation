package food;

import ant.AntState;
import ant.boid.physics.Body;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import utils.SceneObject;
import utils.gui.SubPlot;

public class Food implements SceneObject {
    private Body body;
    private final PImage foodImage;
    private PApplet p;
    private SubPlot plt;
    private final PVector pos;
    private final float radius;
    private final float intensity;
    public Food(PVector pos, float radius, PImage foodImage, PApplet p, SubPlot plt){
        this.pos = pos;
        this.foodImage = foodImage;
        this.radius = radius;
        this.p = p;
        this.plt = plt;
        this.intensity = 2f;
    }
    public PVector getPos() {
        return pos;
    }

    @Override
    public float getIntensity(AntState state) {
        if (state == AntState.SEARCH) return intensity;
        else return 0f;
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