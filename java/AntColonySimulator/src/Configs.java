public class Configs {
    private final float aspectRatio = (float) 16/9;
    //aspect ratio é fixo para mander as células quadradas
    //assim é só definida a largura, e o tamanho das células é calculado automáticamente em relação à área disponível e ao n células desejado
    private final int xGrid = 500;
    private final int yGrid = (int) (xGrid / aspectRatio);
    private final int xWindow = 1920;
    private final int yWindow = (int) (xWindow / aspectRatio);
    private final int cellSize = xWindow/xGrid;
    private final byte[] returnPheromoneColor = {(byte) 255, 0, 0};
    private final byte[] explorePheromoneColor = {0, 0, (byte) 255};
    private final double pheromoneDecayRate = 0.1; //taxa de decaimento de feromonas (entre 0 e 1)

    //Getters
    public float getAspectRatio() {
        return aspectRatio;
    }
    public int getCellSize() {
        return cellSize;
    }
    public int getxGrid() {
        return xGrid;
    }
    public int getyGrid() {
        return yGrid;
    }
    public int getxWindow() {
        return xWindow;
    }
    public int getyWindow() {
        return yWindow;
    }
    public byte[] getExplorePheromoneColor() {
        return explorePheromoneColor;
    }
    public byte[] getReturnPheromoneColor() {
        return returnPheromoneColor;
    }
    public double getPheromoneDecayRate() {
        return pheromoneDecayRate;
    }
}
