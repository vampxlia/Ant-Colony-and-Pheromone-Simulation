package ant.behaviours.implementations;

import ant.Boid;
import ant.behaviours.Behaviour;
import processing.core.PVector;
import utils.SceneObject;

public class Seek extends Behaviour {
    public Seek(float weight) {
        super(weight);
    }

    @Override
    public PVector getDesiredVelocity(Boid me){
        SceneObject target = me.eye.getTarget();
        return PVector.sub(target.getPos(), me.getPos());
    }
}
