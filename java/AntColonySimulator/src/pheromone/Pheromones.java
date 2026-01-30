package pheromone;

import pheromone.ca.Cell;
import pheromone.ca.CellularAutomata;
import processing.core.PApplet;
import utils.SceneObject;
import utils.gui.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class Pheromones extends CellularAutomata{
    public Pheromones(PApplet p, SubPlot plt, int nrows, int ncols, int nStates, int radiusNeigh) {
        super(p, plt, nrows, ncols, nStates, radiusNeigh);
    }

    public void update(){
        for (int i=0;i<nrows;i++) {
            for (int j=0;j<ncols;j++) {
                Pheromone pheromone = (Pheromone) cells[i][j];
                pheromone.decay(0.005f);
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

    public ArrayList<SceneObject> getSearchPheromones (float threshold){
        ArrayList<SceneObject> searchPheromonesList = new ArrayList<>();
        for (int i=0;i<nrows;i++) {
            for (int j=0;j<ncols;j++) {
                Pheromone pheromone = (Pheromone) cells[i][j];
                if (pheromone.searchIntensity > threshold) searchPheromonesList.add(pheromone);
            }
        }
        return  searchPheromonesList;
    }
    public ArrayList<SceneObject> getReturnPheromones (float threshold){
        ArrayList<SceneObject> returnPheromonesList = new ArrayList<>();
        for (int i=0;i<nrows;i++) {
            for (int j=0;j<ncols;j++) {
                Pheromone pheromone = (Pheromone) cells[i][j];
                if (pheromone.returnIntensity > threshold) returnPheromonesList.add(pheromone);
            }
        }
        return  returnPheromonesList;
    }



}
