package ant.boid.behaviours.implementations;

import ant.boid.Boid;
import ant.boid.behaviours.Behaviour;
import processing.core.PVector;
import utils.SceneObject;

public class Seek extends Behaviour {
    public Seek(float weight) {
        super(weight);
    }

    @Override
    public PVector getDesiredVelocity(Boid me){
        SceneObject target = me.eye.getBestTarget();
        if(target != null) return PVector.sub(target.getPos(), me.getPos());
        else return new PVector();
    }
}
