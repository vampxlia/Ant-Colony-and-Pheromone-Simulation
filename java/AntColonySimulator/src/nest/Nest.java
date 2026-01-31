package nest;

import ant.Ant;
import ant.AntState;
import ant.boid.behaviours.implementations.FollowPheromone;
import ant.boid.behaviours.implementations.Seek;
import ant.boid.behaviours.implementations.Wander;
import pheromone.Pheromones;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import utils.SceneObject;
import utils.gui.SubPlot;

import java.util.ArrayList;

public class Nest implements SceneObject {
    private ArrayList<Ant> ants;
    private final PImage nestImage;
    private final PImage antImage;
    private final PApplet p;
    private final SubPlot plt;
    private final PVector pos;
    private final float radius;
    private final float intensity;
    private float pheromoneWindow = 0.5f;
    private float pheromoneTimer = 0f;
    private Pheromones pheromones;
    public Nest(PVector pos, int nAnts, float radius, PImage nestImage, PImage antImage, PApplet p, SubPlot plt, Pheromones pheromones){
        this.pos = pos;
        this.nestImage = nestImage;
        this.antImage = antImage;
        this.radius = radius;
        this.p = p;
        this.plt = plt;
        ants = new ArrayList<Ant>();
        spawnAnt(nAnts);
        this.intensity = 2f;
        this.pheromones = pheromones;
    }

    public float getRadius(){
        return radius;
    }

    public float getIntensity(AntState state){
        if (state == AntState.RETURN) return intensity;
        else return 0f;
    }

    public void display(PApplet p, SubPlot plt, float dt, Pheromones pheromones){

        pheromoneTimer += dt;
        for (Ant ant : ants) {
            ant.applyBehavious(dt);
            ant.display(p, plt);
            if(pheromoneTimer >= pheromoneWindow) {
                ant.dropPheromone(pheromones);
            }
        }
        if(pheromoneTimer >= pheromoneWindow) {
            pheromoneTimer = 0f;
        }

        float[] rr = plt.getVectorCoord(radius, radius);
        float[] pp = plt.getPixelCoord(pos.x, pos.y);
        p.imageMode(p.CENTER);
        p.image(nestImage, pp[0],pp[1], rr[0], rr[1]);

    }

    public void spawnAnt(int nAnts){
        for (int i = 0; i < nAnts; i++){
            Ant ant = new Ant(this.pos, new PVector(), 1F, antImage, p, plt);
            ant.addBehaviour(new Wander(1f));
            ant.addBehaviour(new FollowPheromone(2.5f, pheromones));
            ant.addBehaviour(new Seek(2f));   // só food/nest

            ants.add(ant);
        }
    }

    public ArrayList<Ant> getAnts(){
        return this.ants;
    }

    @Override
    public PVector getPos() {
        return this.pos;
    }
}
