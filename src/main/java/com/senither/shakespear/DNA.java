package com.senither.shakespear;

public class DNA {

    private final CharacterBuilder builder;
    private final char[] genes;
    private float fitness;

    public DNA(int size, CharacterBuilder builder) {
        this.builder = builder;
        genes = new char[size];
        for (int i = 0; i < genes.length; i++) {
            genes[i] = builder.getRandom();
        }
    }

    public void calcFitness(String target) {
        int score = 0;
        for (int i = 0; i < genes.length; i++) {
            if (genes[i] == target.charAt(i)) {
                score++;
            }
        }
        fitness = (float) score / (float) target.length();
        fitness = fitness * fitness * fitness * fitness; // fitness^4
    }

    public double getFitness() {
        return fitness;
    }

    public DNA crossover(DNA partner, String target) {
        // A new child
        DNA child = new DNA(this.genes.length, builder);

        for (int i = 0; i < genes.length; i++) {
            if (genes[i] == target.charAt(i)) {
                child.genes[i] = genes[i];
            } else if (partner.genes[i] == target.charAt(i)) {
                child.genes[i] = partner.genes[i];
            } else {
                child.genes[i] = builder.getRandom();
            }
        }

        return child;
    }

    public String getPhrase() {
        return new String(genes);
    }
}
