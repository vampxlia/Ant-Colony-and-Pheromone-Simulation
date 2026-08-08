package nest;

import ant.Ant;
import ant.AntState;
import ant.boid.behaviours.implementations.FollowPheromone;
import ant.boid.behaviours.implementations.Seek;
import ant.boid.behaviours.implementations.Wander;
import food.Food;
import pheromone.Pheromones;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;
import utils.SceneObject;
import utils.gui.SubPlot;

import java.util.ArrayList;
import java.util.Arrays;

public class Nest implements SceneObject {
    private final ArrayList<Ant> ants;
    private final PImage nestImage;
    private final PImage antImage;
    private final PImage antFoodImage;
    private final PApplet p;
    private final SubPlot plt;
    private final PVector pos;
    private final float radius;
    private final float intensity;
    private float pheromoneTimer = 0f;
    private final Pheromones pheromones;
    private int totalFoodFound = 0;
    private int totalFoodReturned = 0;
    private int deaths = 0;
    private int risings = 0;
    private int foodInNest = 0;
    private final int nAnts;
    public Nest(PVector pos, int nAnts, float radius, PImage nestImage, PImage antImage, PImage antFoodImage, PApplet p, SubPlot plt, Pheromones pheromones){
        this.pos = pos;
        this.nestImage = nestImage;
        this.antImage = antImage;
        this.antFoodImage = antFoodImage;
        this.radius = radius;
        this.p = p;
        this.plt = plt;
        ants = new ArrayList<>();
        spawnAnt(nAnts);
        this.nAnts = nAnts;
        this.intensity = 2f;
        this.pheromones = pheromones;
    }

    public float getRadius(){
        return radius;
    }

    public int getDeaths(){
        return deaths;
    }

    public float getIntensity(AntState state){
        if (state == AntState.RETURN) return intensity;
        else return 0f;
    }

    public void countTotalSwitchedAnts(){
        int born = 0;
        boolean foodReturned = totalFoodReturned % 3 == 0;
        for(Ant ant : ants){
            if(ant.foodFound){
                totalFoodFound++;
                ant.foodFound = false;
            }
            else if(ant.foodReturned){
                totalFoodReturned++;
                foodInNest++;
                ant.foodReturned = false;
                if (foodInNest > 3 && ants.size() <= nAnts){
                    foodInNest -= 3;
                    born++;
                }
            }
        }
        spawnAnt(born);
        risings += born;
    }

    public int getRisings(){
        return risings;
    }

    public int getTotalFoodFound(){
        return totalFoodFound;
    }
    public int getTotalFoodReturned(){
        return totalFoodReturned;
    }

    public void display(PApplet p, SubPlot plt, float dt, Food[] food, Pheromones pheromones){

        pheromoneTimer += dt;
        for (int i = 0; i < ants.size(); i++){
            if (ants.get(i).getIntensity(ants.get(i).state) <= 0){ //se intensidade chegar a 0, formiga respawna no formigueiro
                ants.remove(ants.get(i));
                //spawnAnt(1);
                deaths++;
            }
        }
        this.applyEyes(food);

        float pheromoneWindow = 0.5f;
        for (Ant ant : ants) {
            ant.applyBehavious(dt);
            ant.display(p, plt);
            if(pheromoneTimer >= pheromoneWindow) {
                ant.dropPheromone(pheromones);
            }
        }
        if(pheromoneTimer >= pheromoneWindow) {
            pheromoneTimer = 0f;
        }

        float[] rr = plt.getVectorCoord(radius, radius);
        float[] pp = plt.getPixelCoord(pos.x, pos.y);
        p.imageMode(PConstants.CENTER);
        p.image(nestImage, pp[0],pp[1], rr[0], rr[1]);

    }

    private void applyEyes(Food[] food){
        for (Ant ant : ants) {
            ArrayList<SceneObject> allTrackingBodies;
            if(ant.getState() == AntState.RETURN){
                allTrackingBodies = new ArrayList<>();
                allTrackingBodies.add(this);
            }
            else{
                allTrackingBodies = new ArrayList<>(Arrays.asList(food));
            }
            ant.updateStateAndEye(allTrackingBodies);
        }
    }

    public float getAvgIntensity(){
        float average = 0f;
        for(Ant ant : ants){
            if(ant.getState() == AntState.RETURN){
                average =  average + ant.getIntensity(AntState.RETURN);
            }
            else{
                average =  average + ant.getIntensity(AntState.SEARCH);
            }
        }
        return average/ants.size();
    }

    public void spawnAnt(int nAnts){
        for (int i = 0; i < nAnts; i++){
            Ant ant = new Ant(this.pos, new PVector(), 1F, antImage, antFoodImage, p, plt, this);
            ant.addBehaviour(new Seek(1f));   // só food/nest
            ant.addBehaviour(new FollowPheromone(0.3f, pheromones));
            ant.addBehaviour(new Wander(0.3f));

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
