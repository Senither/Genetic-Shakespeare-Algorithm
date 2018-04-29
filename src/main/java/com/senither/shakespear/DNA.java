package com.senither.shakespear;

import java.util.Random;

public class DNA {

    private final Random random = new Random();
    private final char[] genes;
    private double fitness;

    public DNA(int size) {
        genes = new char[size];
        fitness = 0;

        for (int i = 0; i < genes.length; i++) {
            genes[i] = CharacterComparator.getRandom();
        }
    }

    public void calcFitness(String target) {
        int score = 0;
        for (int i = 0; i < genes.length; i++) {
            if (genes[i] == target.charAt(i)) {
                score++;
            }
        }
        fitness = Math.pow((score + 0.01D) / target.length(), 4);
    }

    public double getFitness() {
        return fitness;
    }

    public DNA crossover(DNA partner) {
        // A new child
        DNA child = new DNA(this.genes.length);

        int midpoint = random.nextInt(genes.length); // Pick a midpoint

        // Half from one, half from the other
        for (int i = 0; i < genes.length; i++) {
            if (i > midpoint) {
                child.genes[i] = genes[i];
            } else {
                child.genes[i] = partner.genes[i];
            }
        }
        return child;
    }

    public void mutate(float mutationRate) {
        for (int i = 0; i < genes.length; i++) {
            if (random.nextInt(1) < mutationRate) {
                this.genes[i] = CharacterComparator.getRandom();
            }
        }
    }

    public String getPhrase() {
        return new String(genes);
    }
}
