import ant.Ant;
import ant.boid.Boid;
import ant.boid.Eye;
import ant.boid.behaviours.implementations.Brake;
import ant.boid.behaviours.implementations.Seek;
import ant.boid.behaviours.implementations.Wander;
import food.Food;
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
    private Food food;
    private final double[] window = {-10, 10, -10, 10};
    private final float[] viewport = {0,0,1,1};
    private SubPlot plt;
    private SceneObject target;
    private ArrayList<Ant> ants;
    private ArrayList<SceneObject> allTrackingBodies;
    private ArrayList<SceneObject> returnTrackingBodies;
    private Pheromones pheromones;
    private int distanceNestFood = 10;
    private MusicSystem music;



    @Override
    public void settings(PApplet p) {

    }

    @Override
    public void setup(PApplet p) {
        PImage antImage = p.loadImage("assets/ant.png");
        PImage nestImage = p.loadImage("assets/nest.png");
        PImage foodImage = p.loadImage("assets/food.png");
        PVector foodPosition = new PVector(2, 2);
        PVector nestPosition = new PVector(-2, -2);
        plt = new SubPlot(window, viewport, p.width, p.height);
        nest = new Nest(nestPosition, 20, 1f, nestImage, antImage, p, plt);
        food = new Food(foodPosition,1f, foodImage, p, plt);
        ants = nest.getAnts();

        pheromones = new Pheromones(p, plt, 100, 100, 2, 1);
        allTrackingBodies = new ArrayList<>();
        allTrackingBodies.add(food);
        allTrackingBodies.add(nest);

        for (Boid ant : ants) {
            Eye eye = new Eye(ant, allTrackingBodies);
            ant.setEye(eye);
        }

        music = new MusicSystem();


    }

    @Override
    public void draw(PApplet p, float dt) {
        p.background(0);
        pheromones.update();

        music.update(dt, ants);

        //atualizar listas para seguir
        allTrackingBodies = pheromones.getActivePheromones(0f, food, nest);

        for (Ant ant : ants) {
            ant.updateStateAndEye(nest, food, allTrackingBodies);
        }

        nest.display(p, plt, dt, pheromones);
        food.display(p, plt);
        pheromones.display(p);

    }

    @Override
    public void mousePressed(PApplet p) {
        //double[] ww = plt.getWorldCoord(p.mouseX, p.mouseY);
        //target.setPos(new PVector((float)ww[0], (float)ww[1]));
    }

    @Override
    public void keyPressed(PApplet p) {

    }
}
