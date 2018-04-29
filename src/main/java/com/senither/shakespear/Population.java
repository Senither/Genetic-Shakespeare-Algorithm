package com.senither.shakespear;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {

    // Random object to generate random values
    private final Random random = new Random();
    // The target that should be attempted to be reached
    private final String target;
    // The mutation rate, defaults to 10%
    private final float mutationRate;
    // The array of DNAs for our populations array
    private final DNA[] populations;
    // The list of mating pools, used before generating the next generation
    private final List<DNA> matingPool;
    // The number of generations the population has gone through
    private int generations;
    // The highest fitness for the current generation
    private double maxFitness;
    // Determine if the population has finished or not
    private boolean finished;
    // The best result with the highest fitness
    private String best;

    public Population(String target, CharacterBuilder builder, float mutationRate, int populationMax) {
        this.target = target;
        this.mutationRate = mutationRate;

        finished = false;
        generations = 0;
        best = "";

        populations = new DNA[populationMax];
        matingPool = new ArrayList<>();

        for (int i = 0; i < populations.length; i++) {
            populations[i] = new DNA(this.target.length(), builder);
        }

        this.calcFitness();
    }

    public void naturalSelection() {
        matingPool.clear();

        maxFitness = 0.0D;
        for (int i = 0; i < populations.length; i++) {
            if (populations[i].getFitness() > maxFitness) {
                maxFitness = populations[i].getFitness();
            }
        }

        // Based on fitness, each member will get added to the mating pool a certain number of times,
        // a higher fitness = more entries to mating pool = more likely to be picked as a parent
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

            DNA child = partnerA.crossover(partnerB, target);

            populations[i] = child;
        }
        generations++;
    }

    public void calcFitness() {
        for (DNA dna : populations) {
            dna.calcFitness(target);
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public void evaluate() {
        double worldRecord = 0.0D;
        int index = 0;
        for (int i = 0; i < populations.length; i++) {
            if (populations[i].getFitness() > worldRecord) {
                index = i;
                worldRecord = populations[i].getFitness();
            }
        }

        best = populations[index].getPhrase();
        if (worldRecord == 1.0F) {
            finished = true;
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
