import ant.Boid;
import ant.DNA;
import nest.Nest;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import utils.gui.IProcessingApp;
import utils.gui.SubPlot;

import java.util.ArrayList;

public class AntColonySimulator implements IProcessingApp {

    private Nest n;
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0,0,1,1};
    private SubPlot plt;
    private float[] maxSpeed = {4, 4};
    private PVector target;
    private ArrayList<Boid> ants;

    @Override
    public void settings(PApplet p) {

    }

    @Override
    public void setup(PApplet p) {
        PImage ant = p.loadImage("assets/ant.png");
        PImage nest = p.loadImage("assets/nest.png");
        plt = new SubPlot(window, viewport, p.width, p.height);
        n = new Nest(new PVector(), 1, 1f, nest, ant, p, plt);
        ants = n.getAnts();
        target = new PVector();
    }

    @Override
    public void draw(PApplet p, float dt) {
        p.background(0);
        n.display(p, plt);

        for (Boid ant : ants) {
            PVector f = ant.seek(target);
            ant.applyForce(f);
            ant.move(dt);

            ant.display(p, plt);
        }
    }

    @Override
    public void mousePressed(PApplet p) {
        double[] ww = plt.getWorldCoord(p.mouseX, p.mouseY);
        target.x = (float)ww[0];
        target.y = (float)ww[1];
    }

    @Override
    public void keyPressed(PApplet p) {

    }
}
