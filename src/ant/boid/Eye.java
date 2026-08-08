package ant.boid;

import processing.core.PVector;
import utils.SceneObject;

import java.util.ArrayList;

public class Eye {
    private final ArrayList<SceneObject> bodies;
    private final Boid me;
    private SceneObject target;

    public Eye(Boid me, ArrayList<SceneObject> bodies){
        this.me = me;
        this.bodies = bodies;
    }

    public void look(){
        target = null;
        float bestDist = Float.MAX_VALUE;

        for(SceneObject obj : bodies){
            float d = PVector.dist(me.getPos(), obj.getPos());
            if(d < me.dna.visionDistance && d < bestDist){
                bestDist = d;
                target = obj;
            }
        }
    }

    public SceneObject getTarget(){
        return target;
    }
}

