package ant.boid;

import ant.boid.physics.Body;
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

    public SceneObject getTarget(){
        return target;
    }

    public SceneObject getBestTarget() {
        /*
        farSight.sort((a, b) -> {return
                (int) ((40 * (b.getIntensity(me.state) - a.getIntensity(me.state))) +
                        60 * ((1.0f - PVector.dist(me.getPos(), a.getPos()) / me.dna.visionDistance) - 1.0f - PVector.dist(me.getPos(), b.getPos()) / me.dna.visionDistance));
            //eu não faço a minima idea se esta conta está feita de forma certa, mas we ball.
            //queria fazer de forma elegante com funções lambda a ver se o arnaldo gosta mais de nós
        });
        if (!farSight.isEmpty() && !(farSight.get(0).getIntensity(me.state) == 0)){
            return farSight.get(0);
        } else return null;
        */

        SceneObject best = target;
        float bestScore = -Float.MAX_VALUE;
        if(!farSight.isEmpty() && !(farSight.get(0).getIntensity(me.state) == 0)) {
            for (SceneObject obj : farSight) {

                float dist = PVector.dist(me.getPos(), obj.getPos());
                float normDist = 1.0f - dist / me.dna.visionDistance;
                normDist = Math.max(0, normDist);

                float normIntensity = obj.getIntensity(me.state);
                // assumindo intensity ∈ [0,1]

                float score = 0.6f * normDist + 0.4f * normIntensity;
                if (score > bestScore) {
                    bestScore = score;
                    best = obj;
                }
            }
            target = best;
        } else {
            target = null;
        }
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
