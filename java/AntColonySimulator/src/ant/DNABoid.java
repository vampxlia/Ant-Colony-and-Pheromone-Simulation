package ant;

import utils.PSControl;

public class DNABoid {
    private float[] maxSpeed;

    public DNABoid(float[] maxSpeed){
        this.maxSpeed = maxSpeed;
    }

    public float getRndMaxSpeed() {
        return PSControl.getRnd(maxSpeed[0], maxSpeed[1]);
    }
}
