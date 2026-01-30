package ant;

import ant.boid.Boid;
import pheromone.Pheromone;
import pheromone.Pheromones;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import utils.gui.SubPlot;

public class Ant extends Boid {
    private AntState state;
    public Ant(PVector pos, PVector vel, float mass, PImage img, PApplet p, SubPlot plt) {
        super(pos, vel, mass, img, p, plt);
        this.state = AntState.SEARCH;
    }

    public void switchState(){
        if (this.state == AntState.RETURN) this.state = AntState.SEARCH;
        if (this.state == AntState.SEARCH) this.state = AntState.RETURN;
    }

    public void dropPheromone(Pheromones pheromoneGrid){
        Pheromone pheromone = (Pheromone) pheromoneGrid.world2Cell(this.pos.x, this.pos.y);
        if (this.state == AntState.SEARCH) pheromone.newSearchPheromone();
        if (this.state == AntState.RETURN) pheromone.newReturnPheromone();
    }
}
