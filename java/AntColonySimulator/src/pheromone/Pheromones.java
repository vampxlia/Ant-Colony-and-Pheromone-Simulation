package pheromone;

public class Pheromones {
    private int[][] pheromones;
    private double decayRate;
    public Pheromones(int xSize, int ySize, double decayRate){
        this.pheromones = new int[xSize][ySize];
        this.decayRate = decayRate;
    }

    public void dropPheromones(int x, int y){
        this.pheromones[x][y] = 100;
    }

    public void update(){
        for (int i = 0; i < pheromones.length; i++){
            for (int j = 0; j < pheromones[0].length; j++){
                pheromones[i][j] = (int) (pheromones[i][j] * decayRate);
            }
        }
    }

    public int[][] getPheromones() {
        return pheromones;
    }
}
