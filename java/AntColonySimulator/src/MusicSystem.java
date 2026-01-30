import ant.Ant;

import javax.sound.midi.*;
import java.util.*;

public class MusicSystem {

    private Synthesizer synth;
    private MidiChannel[] channels;

    private int[] instruments = {
            0, 11, 24, 40, 48, 56, 64, 73, 88, 98
    };

    private int[] scale = {0, 2, 4, 5, 7, 9, 11}; // escala maior
    private boolean useScale = true;

    private float timer = 0f;
    private float interval = 0.25f;

    private Random rng = new Random();

    public MusicSystem() {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channels = synth.getChannels();

            for (int i = 0; i < instruments.length; i++) {
                channels[i].programChange(instruments[i]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(float dt, List<Ant> ants) {
        timer += dt;
        if (timer >= interval) {
            timer = 0f;
            playFromAnts(ants);
        }
    }

    private void playFromAnts(List<Ant> ants) {
        if (ants.size() < 2) return;

        Ant a1 = ants.get(rng.nextInt(ants.size()));
        Ant a2 = ants.get(rng.nextInt(ants.size()));

        playAnt(a1);
        playAnt(a2);
    }

    private void playAnt(Ant ant) {

        float x = ant.getPos().x;
        float y = ant.getPos().y;

        // converter mundo → ecrã (aproximado)
        float px = (x + 10f) / 20f * 1000f;
        float py = (y + 10f) / 20f * 1000f;

        // instrumento
        int instrumentIndex = constrain((int)(px / 100f), 0, 9);

        // oitava (5 em loop)
        int octave = ((int)(py / 200f)) % 5;

        int baseNote = 36 + octave * 12; // C2 → C7

        int note;
        if (useScale) {
            int degree = rng.nextInt(scale.length);
            note = baseNote + scale[degree];
        } else {
            note = baseNote + rng.nextInt(12);
        }

        int velocity = 60 + rng.nextInt(40);

        MidiChannel ch = channels[instrumentIndex];

        ch.noteOn(note, velocity);

        new Timer().schedule(new TimerTask() {
            public void run() {
                ch.noteOff(note);
            }
        }, 300);
    }

    private int constrain(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }

    public void setUseScale(boolean v) {
        useScale = v;
    }
}

