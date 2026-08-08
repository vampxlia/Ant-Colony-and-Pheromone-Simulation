import food.Food;
import nest.Nest;
import pheromone.Pheromones;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import utils.gui.IProcessingApp;
import utils.gui.SubPlot;

public class AntColonySimulator implements IProcessingApp {
    private Nest nest;
    private Food food;
    private final double[] window = {-10, 10, -10, 10};
    private final float[] viewport = {0,0,1,1};
    private SubPlot plt;
    private Pheromones pheromones;
    private MusicSystem music;
    private float timeSinceStart;
    private boolean printed;


    @Override
    public void settings(PApplet p) {

    }

    @Override
    public void setup(PApplet p) {
        PImage antImage = p.loadImage("assets/ant.png");
        PImage antFoodImage = p.loadImage("assets/ant_with_food.png");
        PImage nestImage = p.loadImage("assets/nest.png");
        PImage foodImage = p.loadImage("assets/food.png");
        PVector foodPosition = new PVector(3.5f, 3.5f);
        //PVector foodPosition2 = new PVector(-3, 3);
        PVector nestPosition = new PVector(-3.5f, -3.5f);
        plt = new SubPlot(window, viewport, p.width, p.height);
        pheromones = new Pheromones(p, plt, 100, 100, 2, 1);
        nest = new Nest(nestPosition, 200, 1f, nestImage, antImage, antFoodImage, p, plt, pheromones);
        food = new Food(foodPosition,1f, foodImage, p, plt);
        //food2 = new Food(foodPosition2,1f, foodImage, p, plt);
        music = new MusicSystem();
        timeSinceStart = 0;
        printed = false;
    }

    @Override
    public void draw(PApplet p, float dt) {
        p.background(0);
        pheromones.update();
        pheromones.display(p);
        Food[] foodList = new Food[1];
        foodList[0] = food;
        //foodList[1] = food2;
        nest.display(p, plt, dt, foodList, pheromones);
        music.update(dt, nest.getAnts());
        food.display(p, plt);
        //food2.display(p, plt);
        nest.countTotalSwitchedAnts();
        timeSinceStart += dt;
        if(timeSinceStart >= 60 && !printed){
            System.out.println("Average Ant Intensity: " + nest.getAvgIntensity());
            System.out.println("Total food delivered to nest: " + nest.getTotalFoodReturned());
            System.out.println("Total food gathered: " + nest.getTotalFoodFound());
            System.out.println("Ant Deaths: " + nest.getDeaths());
            System.out.println("Ant Births: " + nest.getRisings());
            printed = true;
        }


    }

    @Override
    public void mousePressed(PApplet p) {

    }

    @Override
    public void keyPressed(PApplet p) {

    }
}
