package com.senither.shakespear;

public class Main {

    public static void main(String[] args) {
        String sonnet = "To be or not to be.";

        Population population = new Population(sonnet, Settings.MUTATION_RATE, Settings.POPULATION_MAX);

        while (!population.isFinished()) {
            population.naturalSelection();
            population.generate();
            population.calcFitness();

            if (population.getGenerations() % 25 == 0) {
                System.out.println(
                        population.getGenerations() + " : " + population.getMaxFitness() + " : " + population.getBest()
                );
            }

            population.evaluate();

            if (population.getGenerations() > 50000) {
                System.out.println("Exiting population loop, took too long!");
                break;
            }
        }

        System.out.println("Done!\nGenerations: " + population.getGenerations() + "\nResult: " + population.getBest() + "\nExcepted Result: " + sonnet);
    }
}
