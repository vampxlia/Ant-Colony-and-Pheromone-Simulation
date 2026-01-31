import ant.Ant;
import ant.AntState;
import ant.boid.Boid;
import ant.boid.Eye;
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
    private Pheromones pheromones;
    private MusicSystem music;



    @Override
    public void settings(PApplet p) {

    }

    @Override
    public void setup(PApplet p) {
        PImage antImage = p.loadImage("assets/ant.png");
        PImage antFoodImage = p.loadImage("assets/ant_with_food.png");
        PImage nestImage = p.loadImage("assets/nest.png");
        PImage foodImage = p.loadImage("assets/food.png");
        PVector foodPosition = new PVector(3, 3);
        PVector nestPosition = new PVector(-3, -3);
        plt = new SubPlot(window, viewport, p.width, p.height);
        pheromones = new Pheromones(p, plt, 100, 100, 2, 1);
        nest = new Nest(nestPosition, 20, 1f, nestImage, antImage, antFoodImage, p, plt, pheromones);
        food = new Food(foodPosition,1f, foodImage, p, plt);
        music = new MusicSystem();


    }

    @Override
    public void draw(PApplet p, float dt) {
        p.background(0);
        pheromones.update();
        pheromones.display(p);
        nest.display(p, plt, dt, food, pheromones);
        music.update(dt, nest.getAnts());
        food.display(p, plt);


    }

    @Override
    public void mousePressed(PApplet p) {

    }

    @Override
    public void keyPressed(PApplet p) {

    }
}
