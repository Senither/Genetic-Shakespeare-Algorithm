package com.senither.shakespear;

public class Main {

    private static final int ROUNDS = 1000;

    public static void main(String[] args) {
        String sonnet = "To be or not to be.";

        System.out.println("Running generic algorithm to try and learn to write the following sentence:\n");
        System.out.println("\t" + sonnet);
        System.out.println("\nRunning populations...");

        int generations = 0;
        int jump = 100;
        int next = jump - 1;
        long milliseconds = System.currentTimeMillis();
        for (int i = 0; i < ROUNDS; i++) {
            if (i % jump == 0 || i == 0) {
                System.out.print("Running population #" + i + " - #" + (i + jump));
            }

            Population population = new Population(sonnet, Settings.MUTATION_RATE, Settings.POPULATION_MAX);
            while (!population.isFinished()) {
                population.naturalSelection();
                population.generate();
                population.calcFitness();

                population.evaluate();
            }
            generations += population.getGenerations();

            if (i == next) {
                next += jump;
                long time = (System.currentTimeMillis() - milliseconds);
                System.out.println(" | Task took " + time + " ms (~" + (time / jump) + " ms each)");
                milliseconds = System.currentTimeMillis();
            }
        }
        System.out.println("Done!\n");
        System.out.println("It took an average of " + (generations / ROUNDS) + " generations to get to the end result");
    }
}
