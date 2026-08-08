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
        SceneObject target = me.eye.getTarget();
        if(target == null) return null;

        PVector desired = PVector.sub(target.getPos(), me.getPos());
        PVector steering = PVector.sub(desired, me.getVel());
        return steering;
    }

}
