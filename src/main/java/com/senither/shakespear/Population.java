package com.senither.shakespear;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {

    // Stuff
    private final String target;
    private final float mutationRate;
    private final DNA[] populations;
    private final List<DNA> matingPool;
    // Thing
    private final float perfectScore = 1.0F;
    private final Random random = new Random();
    // Test
    private int generations;
    private double maxFitness;
    private boolean finished;
    private String best;

    public Population(String target, float mutationRate, int populationMax) {
        this.target = target;
        this.mutationRate = mutationRate;

        this.finished = false;
        this.generations = 0;
        this.best = "";

        this.populations = new DNA[populationMax];
        this.matingPool = new ArrayList<>();

        for (int i = 0; i < this.populations.length; i++) {
            this.populations[i] = new DNA(this.target.length());
        }

        this.calcFitness();
    }

    public void naturalSelection() {
        matingPool.clear();

        maxFitness = 0.0D;
        for (int i = 0; i < populations.length; i++) {
            if (this.populations[i].getFitness() > maxFitness) {
                maxFitness = this.populations[i].getFitness();
            }
        }

        // Based on fitness, each member will get added to the mating pool a certain number of times
        // a higher fitness = more entries to mating pool = more likely to be picked as a parent
        // a lower fitness = fewer entries to mating pool = less likely to be picked as a parent
        for (DNA dna : populations) {
            double fitness = (dna.getFitness() / (maxFitness - 0));
            double n = Math.floor(fitness * 100); // Arbitrary multiplier, we can also use monte carlo method
            for (int j = 0; j < n; j++) { // and pick two random numbers
                matingPool.add(dna);
            }
        }
    }

    public void generate() {
        // Refill the populations with children from the mating pool
        for (int i = 0; i < populations.length; i++) {
            int a = random.nextInt(matingPool.size());
            int b = random.nextInt(matingPool.size());
            DNA partnerA = matingPool.get(a);
            DNA partnerB = matingPool.get(b);

            DNA child = partnerA.crossover(partnerB);

            child.mutate(mutationRate);
            this.populations[i] = child;
        }
        this.generations++;
    }

    public void calcFitness() {
        for (DNA aPopulation : populations) {
            aPopulation.calcFitness(target);
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public void evaluate() {
        double worldRecord = 0.0D;
        int index = 0;
        for (int i = 0; i < populations.length; i++) {
            if (this.populations[i].getFitness() > worldRecord) {
                index = i;
                worldRecord = this.populations[i].getFitness();
            }
        }

        this.best = this.populations[index].getPhrase();
        if (worldRecord == perfectScore) {
            this.finished = true;
        }
    }

    public int getGenerations() {
        return generations;
    }

    public String getBest() {
        return best;
    }

    public double getMaxFitness() {
        return maxFitness;
    }
}
