import ant.Ant;
import ant.AntState;
import ant.boid.Boid;
import ant.boid.Eye;
import ant.boid.behaviours.implementations.Brake;
import ant.boid.behaviours.implementations.Seek;
import ant.boid.behaviours.implementations.Wander;
import ant.boid.physics.Body;
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
    private ArrayList<SceneObject> searchTrackingBodies;
    private ArrayList<SceneObject> returnTrackingBodies;
    private Pheromones pheromones;
    private float pheromoneTimer = 0f;
    private float pheromoneWindow = 0.5f;
    private int distanceNestFood = 10;

    @Override
    public void settings(PApplet p) {

    }

    @Override
    public void setup(PApplet p) {
        PImage antImage = p.loadImage("assets/ant.png");
        PImage nestImage = p.loadImage("assets/nest.png");
        PImage foodImage = p.loadImage("assets/food.png");
        PVector foodPosition = new PVector(-5, -5);
        PVector nestPosition = new PVector(5, 5);
        plt = new SubPlot(window, viewport, p.width, p.height);
        nest = new Nest(nestPosition, 20, 1f, nestImage, antImage, p, plt);
        food = new Food(foodPosition,1f, foodImage, p, plt);
        ants = nest.getAnts();

        pheromones = new Pheromones(p, plt, 100, 100, 2, 1);
        searchTrackingBodies = new ArrayList<>();
        searchTrackingBodies.add(food);
        returnTrackingBodies = new ArrayList<>();
        returnTrackingBodies.add(nest);

        for (Boid ant : ants) {
            ant.addBehaviour(new Wander(1f));
            ant.addBehaviour(new Brake(1f));
            ant.addBehaviour(new Seek(1f));

            Eye eye = new Eye(ant, searchTrackingBodies);
            ant.setEye(eye);
        }
    }

    @Override
    public void draw(PApplet p, float dt) {
        p.background(0);
        pheromones.update();
        pheromoneTimer += dt;
        for (Ant ant : ants) {
            ant.applyBehavious(dt);
            ant.display(p, plt);
            if(pheromoneTimer >= pheromoneWindow) {
                ant.dropPheromone(pheromones);
                if(ant.getState() == AntState.SEARCH){
                    returnTrackingBodies.add(pheromones.pixel2Cell(ant.getPos().x,ant.getPos().y));
                } else if(ant.getState() == AntState.RETURN){
                    searchTrackingBodies.add(pheromones.pixel2Cell(ant.getPos().x,ant.getPos().y));
                }
            }

            target = ant.getTarget();
            if(target != null && PVector.dist(target.getPos(), ant.getPos()) <= target.getRadius()){
                ant.switchState();
                if(ant.getState() == AntState.SEARCH){
                    Eye eye = new Eye(ant, searchTrackingBodies);
                    ant.setEye(eye);
                }
                else if(ant.getState() == AntState.RETURN){
                    Eye eye = new Eye(ant, returnTrackingBodies);
                    ant.setEye(eye);
                }
            }
        }
        if(pheromoneTimer >= pheromoneWindow) {
            pheromoneTimer = 0f;
        }
        nest.display(p, plt);
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
