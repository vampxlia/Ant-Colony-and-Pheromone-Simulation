package pheromone;

import food.Food;
import nest.Nest;
import pheromone.ca.CellularAutomata;
import processing.core.PApplet;
import utils.SceneObject;
import utils.gui.SubPlot;

import java.util.ArrayList;

public class Pheromones extends CellularAutomata{
    public Pheromones(PApplet p, SubPlot plt, int nrows, int ncols, int nStates, int radiusNeigh) {
        super(p, plt, nrows, ncols, nStates, radiusNeigh);
    }

    public void update(){
        for (int i=0;i<nrows;i++) {
            for (int j=0;j<ncols;j++) {
                Pheromone pheromone = (Pheromone) cells[i][j];
                pheromone.decay(0.001f);
            }
        }
    }

    @Override
    protected void createCells(){
        for (int i=0;i<nrows;i++) {
            for (int j=0;j<ncols;j++) {
                cells[i][j] = new Pheromone(this, i,j);
            }
        }
        setMooreNeighbours();
    }

    @Override
    public Pheromone pixel2Cell(float x, float y) {
        int row = (int)((y-ymin)/cellHeight);
        int col = (int)((x-xmin)/cellWidth);
        if(row>= nrows) row = nrows - 1;
        if(col>= ncols) col = ncols - 1;
        return (Pheromone) cells[row][col];
    }

    public ArrayList<SceneObject> getActivePheromones(float threshold, Food food, Nest nest){
        ArrayList<SceneObject> searchPheromonesList = new ArrayList<>();
        searchPheromonesList.add(food);
        searchPheromonesList.add(nest);
        for (int i=0;i<nrows;i++) {
            for (int j=0;j<ncols;j++) {
                Pheromone pheromone = (Pheromone) cells[i][j];
                if (pheromone.returnIntensity > threshold || pheromone.searchIntensity > threshold) searchPheromonesList.add(pheromone);
            }
        }
        return  searchPheromonesList;
    }
}
