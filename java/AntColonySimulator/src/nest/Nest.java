package nest;

import ant.Ant;
import ant.boid.Boid;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import utils.SceneObject;
import utils.gui.SubPlot;

import java.util.ArrayList;

public class Nest implements SceneObject {
    private ArrayList<Ant> ants;
    private PImage nestImage;
    private PImage antImage;
    private PApplet p;
    private SubPlot plt;
    private PVector pos;
    private float radius;
    private float intensity;
    public Nest(PVector pos, int nAnts, float radius, PImage nestImage, PImage antImage, PApplet p, SubPlot plt){
        this.pos = pos;
        this.nestImage = nestImage;
        this.antImage = antImage;
        this.radius = radius;
        this.p = p;
        this.plt = plt;
        ants = new ArrayList<Ant>();
        spawnAnt(nAnts);
        this.intensity = 1f;
    }

    public float getRadius(){
        return radius;
    }

    public float getIntensity(){
        return intensity;
    }

    public void display(PApplet p, SubPlot plt){
        float[] rr = plt.getVectorCoord(radius, radius);
        float[] pp = plt.getPixelCoord(pos.x, pos.y);
        p.imageMode(p.CENTER);
        p.image(nestImage, pp[0],pp[1], rr[0], rr[1]);

        //display associated ants
        for (Boid ant : ants) {
            //TODO comportamento das formigas
        }
    }

    public void spawnAnt(int nAnts){
        for (int i = 0; i < nAnts; i++){
            Ant ant = new Ant(this.pos, new PVector(), 1F, antImage, p, plt);
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
