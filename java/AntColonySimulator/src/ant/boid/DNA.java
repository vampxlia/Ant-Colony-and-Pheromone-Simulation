package ant.boid;

public class DNA {
    public float maxSpeed;
    public float maxForce;
    public float visionDistance;
    public float visionSafeDistance;
    public float visionAngle;

    public float deltaTWander;
    public float radiusWander;
    public float deltaPhiWander;

    public DNA(){
        //basics
        this.maxSpeed = random(4, 4);
        this.maxForce = random(2, 2);
        visionDistance = random(5, 5);
        visionSafeDistance = 0.25f * visionDistance;
        visionAngle = (float) (Math.PI);

        //wander
        radiusWander = random(3f,3f);
        deltaTWander = random(1f, 1f);
        deltaPhiWander = (float)Math.PI/4;
    }

    public static float random(float min, float max) {
        return (float) (min + (max - min)*Math.random());
    }
}
