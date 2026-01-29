package ant;

public class DNA {
    public float maxSpeed;
    public float maxForce;
    public float visionDistance;
    public float visionSafeDistance;
    public float visionAngle;

    public DNA(){
        this.maxSpeed = random(3, 5);
        this.maxForce = random(4, 7);
        visionDistance = random(2, 4);
        visionSafeDistance = 0.25f * visionDistance;
        visionAngle = (float) (Math.PI) / 2;
    }

    public static float random(float min, float max) {
        return (float) (min + (max - min)*Math.random());
    }
}
