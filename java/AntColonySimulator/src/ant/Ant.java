package ant;

import ant.boid.Boid;
import ant.boid.Eye;
import food.Food;
import nest.Nest;
import pheromone.Pheromone;
import pheromone.Pheromones;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import utils.SceneObject;
import utils.gui.SubPlot;

import java.util.ArrayList;

public class Ant extends Boid {
    public AntState state;
    private float intensity;
    private PImage img;
    private final PImage antImage;
    private final PImage foodImage;
    private final Nest nest;
    public boolean foodFound = false;
    public boolean foodReturned = false;
    public Ant(PVector pos, PVector vel, float mass, PImage antImage, PImage foodImage, PApplet p, SubPlot plt, Nest nest) {
        super(pos, vel, mass, p, plt);
        this.antImage = antImage;
        this.foodImage = foodImage;
        this.img = antImage;
        this.state = AntState.SEARCH;
        this.intensity = 1f;
        this.nest = nest;
    }
    public AntState getState() {
        return state;
    }
    public SceneObject getTarget(){
        return this.eye.getTarget();
    }

    public void updateStateAndEye(Food food, ArrayList<SceneObject> targets) {
        super.eye = new Eye(this, targets);
        if (this.state == AntState.SEARCH) {
            if (PVector.dist(this.pos, food.getPos()) < food.getRadius()) {
                this.state = AntState.RETURN;
                this.img = foodImage;
                this.vel.rotate((float) Math.PI); //Quando bate no sítio desejado, vira ao contrário para facilitar retorno
                intensity = 1f;
                foodFound = true;
            }
        }
        if (this.state == AntState.RETURN) {
            if (PVector.dist(this.pos, nest.getPos()) < nest.getRadius()) {
                this.state = AntState.SEARCH;
                this.img = antImage;
                this.vel.rotate((float) Math.PI);
                intensity = 1f;
                foodReturned = true;
            }
        }
    }

    public void dropPheromone(Pheromones pheromoneGrid){
        //if(this.pos.x > 0 && this.pos.y > 0 && this.pos.x < 1000 && this.pos.y < 1000) {
            Pheromone pheromone = (Pheromone) pheromoneGrid.world2Cell(this.pos.x, this.pos.y);
            if (this.state == AntState.SEARCH) pheromone.newSearchPheromone(intensity);
            if (this.state == AntState.RETURN) pheromone.newReturnPheromone(intensity);
        //}
        if(intensity > 0f){
            intensity -= 0.02f;
        }
    }

    @Override
    public float getIntensity(AntState state) {
        if (state == this.state){
            return intensity;
        }
        return 0;
    }



    @Override
    public void display(PApplet p, SubPlot plt){

        float[] rr = plt.getVectorCoord(radius, radius);
        float[] pp = plt.getPixelCoord(pos.x, pos.y);

        p.pushMatrix();
        p.translate(pp[0], pp[1]);
        p.rotate(vel.heading());
        p.imageMode(p.CENTER);
        p.image(img, 0,0, rr[0], rr[1]);
        p.popMatrix();
    }
}
