import ant.Ant;
import ant.boid.Boid;
import ant.boid.Eye;
import ant.boid.behaviours.implementations.Wander;
import ant.boid.physics.Body;
import nest.Nest;
import pheromone.Pheromones;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import utils.SceneObject;
import utils.gui.IProcessingApp;
import utils.gui.SubPlot;

import java.util.ArrayList;

public class AntColonySimulator implements IProcessingApp {

    private Nest nest;
    private final double[] window = {-10, 10, -10, 10};
    private final float[] viewport = {0,0,1,1};
    private SubPlot plt;
    private Body target;
    private ArrayList<Ant> ants;
    private ArrayList<SceneObject> allTrackingBodies;
    private Pheromones pheromones;

    @Override
    public void settings(PApplet p) {

    }

    @Override
    public void setup(PApplet p) {
        PImage antImage = p.loadImage("assets/ant.png");
        PImage nestImage = p.loadImage("assets/nest.png");
        plt = new SubPlot(window, viewport, p.width, p.height);
        nest = new Nest(new PVector(), 3, 1f, nestImage, antImage, p, plt);
        ants = nest.getAnts();

        pheromones = new Pheromones(p, plt, 50, 50, 2, 1);
        target = new Body(new PVector(), new PVector(), 1f, 0.2f, p.color(255,0,0));
        allTrackingBodies = new ArrayList<>();
        allTrackingBodies.add(target);

        for (Boid ant : ants) {
            ant.addBehaviour(new Wander(1f));
            Eye eye = new Eye(ant, allTrackingBodies);
            ant.setEye(eye);
        }
    }

    @Override
    public void draw(PApplet p, float dt) {
        p.background(0);
        pheromones.update();
        for (Ant ant : ants) {
            ant.applyBehavious(dt);
            ant.display(p, plt);
            ant.dropPheromone(pheromones);
        }
        nest.display(p, plt);
        pheromones.display(p);

    }

    @Override
    public void mousePressed(PApplet p) {
        double[] ww = plt.getWorldCoord(p.mouseX, p.mouseY);
        target.setPos(new PVector((float)ww[0], (float)ww[1]));
    }

    @Override
    public void keyPressed(PApplet p) {

    }
}
