import ant.Ant;

import javax.sound.midi.*;
import java.util.*;

public class MusicSystem {

    private MidiChannel[] channels;

    private final int[] scale = {0, 2, 4, 5, 7, 9, 11}; // escala maior
    private boolean useScale = true;

    private float timer = 0f;

    private final Random rng = new Random();

    public MusicSystem() {
        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            channels = synth.getChannels();

            int[] instruments = {
                    0, 11, 24, 40, 48, 56, 64, 73, 88, 98
            };
            for (int i = 0; i < instruments.length; i++) {
                channels[i].programChange(instruments[i]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(float dt, List<Ant> ants) {
        timer += dt;
        float interval = 0.25f;
        if (timer >= interval) {
            timer = 0f;
            playFromAnts(ants);
        }
    }

    private void playFromAnts(List<Ant> ants) {
        if (ants.size() < 2) return;
        for (Ant ant : ants) {
            playAnt(ant);
        }
    }

    private void playAnt(Ant ant) {

        float x = ant.getPos().x;
        float y = ant.getPos().y;

        // converter mundo → ecrã (aproximado)
        float px = (x + 10f) / 20f * 1000f;
        float py = (y + 10f) / 20f * 1000f;

        // instrumento
        int instrumentIndex = constrain((int)(px / 100f));

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

    private int constrain(int v) {
        return Math.clamp(v, 0, 9);
    }

    public void setUseScale(boolean v) {
        useScale = v;
    }
}

