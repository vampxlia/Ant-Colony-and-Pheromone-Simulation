package utils;

import ant.AntState;
import processing.core.PVector;

public interface SceneObject {
    public PVector getPos();
    public float getIntensity(AntState state);
    public float getRadius();
}
