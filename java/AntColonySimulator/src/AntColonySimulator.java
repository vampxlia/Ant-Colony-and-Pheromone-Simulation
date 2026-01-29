import ant.Boid;
import ant.DNA;
import ant.Eye;
import ant.behaviours.implementations.Seek;
import ant.physics.Body;
import nest.Nest;
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
    private ArrayList<Boid> ants;
    private ArrayList<SceneObject> allTrackingBodies;

    @Override
    public void settings(PApplet p) {

    }

    @Override
    public void setup(PApplet p) {
        PImage antImage = p.loadImage("assets/ant.png");
        PImage nestImage = p.loadImage("assets/nest.png");
        plt = new SubPlot(window, viewport, p.width, p.height);
        nest = new Nest(new PVector(), 1, 1f, nestImage, antImage, p, plt);
        ants = nest.getAnts();

        target = new Body(new PVector(), new PVector(), 1f, 0.2f, p.color(255,0,0));
        allTrackingBodies = new ArrayList<>();
        allTrackingBodies.add(target);

        for (Boid ant : ants) {
            ant.addBehaviour(new Seek(1f));
            Eye eye = new Eye(ant, allTrackingBodies);
            ant.setEye(eye);
        }
    }

    @Override
    public void draw(PApplet p, float dt) {
        p.background(0);
        nest.display(p, plt);

        for (Boid ant : ants) {
            ant.applyBehavious(dt);
            ant.display(p, plt);
        }
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
