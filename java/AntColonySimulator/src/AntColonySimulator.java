import ant.Boid;
import ant.DNABoid;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import utils.gui.IProcessingApp;
import utils.gui.SubPlot;

public class AntColonySimulator implements IProcessingApp {

    private Boid b;
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0,0,1,1};
    private SubPlot plt;
    private DNABoid dna;
    private float[] maxSpeed = {4, 4};
    private PVector target;
    private PImage ant;

    @Override
    public void settings(PApplet p) {

    }

    @Override
    public void setup(PApplet p) {
        PImage ant = p.loadImage("assets/ant.png");
        plt = new SubPlot(window, viewport, p.width, p.height);
        dna = new DNABoid(maxSpeed);
        b = new Boid(new PVector(), new PVector(), 1F, ant, dna, p, plt);
        target = new PVector();
    }

    @Override
    public void draw(PApplet p, float dt) {
        p.background(0);
        PVector f = b.seek(target);
        b.applyForce(f);
        b.move(dt);

        b.display(p, plt);
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
