package ant;

import ant.boid.Boid;
import ant.boid.Eye;
import ant.boid.physics.Body;
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
    public Ant(PVector pos, PVector vel, float mass, PImage img, PApplet p, SubPlot plt) {
        super(pos, vel, mass, img, p, plt);
        this.state = AntState.SEARCH;
    }
    public AntState getState() {
        return state;
    }
    public SceneObject getTarget(){
        return this.eye.getTarget();
    }

    public void updateStateAndEye(Nest nest, Food food, ArrayList<SceneObject> targets) {
        super.eye = new Eye(this, targets);
        if (this.state == AntState.SEARCH) {
            if (PVector.dist(this.pos, food.getPos()) < food.getRadius()) {
                this.state = AntState.RETURN;
                this.vel = new PVector();
            }
        }
        if (this.state == AntState.RETURN) {
            if (PVector.dist(this.pos, nest.getPos()) < nest.getRadius()) {
                this.state = AntState.SEARCH;
                this.vel = new PVector();
            }
        }
    }

    public void dropPheromone(Pheromones pheromoneGrid){
        Pheromone pheromone = (Pheromone) pheromoneGrid.world2Cell(this.pos.x, this.pos.y);
        if (this.state == AntState.SEARCH) pheromone.newSearchPheromone();
        if (this.state == AntState.RETURN) pheromone.newReturnPheromone();
    }
}
