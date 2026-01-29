package nest;

import ant.Boid;
import ant.DNA;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import utils.SceneObject;
import utils.gui.SubPlot;

import java.util.ArrayList;

public class Nest implements SceneObject {
    private ArrayList<Boid> ants;
    private PImage nestImage;
    private PImage antImage;
    private PApplet p;
    private SubPlot plt;
    private PVector pos;
    private float radius;
    public Nest(PVector pos, int nAnts, float radius, PImage nestImage, PImage antImage, PApplet p, SubPlot plt){
        this.pos = pos;
        this.nestImage = nestImage;
        this.antImage = antImage;
        this.radius = radius;
        this.p = p;
        this.plt = plt;
        ants = new ArrayList<Boid>();
        spawnAnt(nAnts);
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
            Boid b = new Boid(this.pos, new PVector(), 1F, antImage, p, plt);
            ants.add(b);
        }
    }

    public ArrayList<Boid> getAnts(){
        return this.ants;
    }

    @Override
    public PVector getPos() {
        return this.pos;
    }
}
