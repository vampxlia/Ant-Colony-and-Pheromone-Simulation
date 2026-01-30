package ant.boid;

import processing.core.PVector;
import utils.SceneObject;

import java.util.ArrayList;

public class Eye {
    private ArrayList<SceneObject> allTrackingBodies;
    private ArrayList<SceneObject> farSight;
    private ArrayList<SceneObject> nearSight;
    private Boid me;
    private SceneObject target;

    public Eye(Boid me, ArrayList<SceneObject> allTrackingBodies){
        this.me = me;
        this.allTrackingBodies = allTrackingBodies;
        target = allTrackingBodies.get(0);
    }

    public SceneObject getTarget() {
        return target;
    }

    public void look(){
        farSight = new ArrayList<>();
        nearSight = new ArrayList<>();
        for (SceneObject object : allTrackingBodies){
            if(farSight(object.getPos())) farSight.add(object);
            if(nearSight(object.getPos())) nearSight.add(object);
        }
    }

    public ArrayList<SceneObject> getFarSight() {
        return farSight;
    }

    public ArrayList<SceneObject> getNearSight() {
        return nearSight;
    }

    private boolean inSight(PVector t, float maxDistance, float maxAngle){
        PVector r = PVector.sub(t, me.getPos());
        float d = r.mag();
        float angle = PVector.angleBetween(r, me.getVel());
        return ((d > 0) && (d < maxDistance) && (angle < maxAngle));
    }

    private boolean farSight(PVector t){
        return inSight(t, me.dna.visionDistance, me.dna.visionAngle);
    }

    private boolean nearSight(PVector t){
        return inSight(t, me.dna.visionSafeDistance, (float) Math.PI);
    }

}
