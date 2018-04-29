package com.senither.shakespear;

public class Main {

    private static final int ROUNDS = 100;

    public static void main(String[] args) {
        String fullSonnet = "To be, or not to be, that is the question:\n" +
                "Whether 'tis nobler in the mind to suffer\n" +
                "The slings and arrows of outrageous fortune,\n" +
                "Or to take arms against a sea of troubles\n" +
                "And by opposing end them. To die—to sleep,\n" +
                "No more; and by a sleep to say we end\n" +
                "The heart-ache and the thousand natural shocks\n" +
                "That flesh is heir to: 'tis a consummation\n" +
                "Devoutly to be wish'd. To die, to sleep;\n" +
                "To sleep, perchance to dream—ay, there's the rub:\n" +
                "For in that sleep of death what dreams may come,\n" +
                "When we have shuffled off this mortal coil,\n" +
                "Must give us pause—there's the respect\n" +
                "That makes calamity of so long life.\n" +
                "For who would bear the whips and scorns of time,\n" +
                "Th'oppressor's wrong, the proud man's contumely,\n" +
                "The pangs of dispriz'd love, the law's delay,\n" +
                "The insolence of office, and the spurns\n" +
                "That patient merit of th'unworthy takes,\n" +
                "When he himself might his quietus make\n" +
                "With a bare bodkin? Who would fardels bear,\n" +
                "To grunt and sweat under a weary life,\n" +
                "But that the dread of something after death,\n" +
                "The undiscovere'd country, from whose bourn\n" +
                "No traveller returns, puzzles the will,\n" +
                "And makes us rather bear those ills we have\n" +
                "Than fly to others that we know not of?\n" +
                "Thus conscience does make cowards of us all,\n" +
                "And thus the native hue of resolution\n" +
                "Is sicklied o'er with the pale cast of thought,\n" +
                "And enterprises of great pitch and moment\n" +
                "With this regard their currents turn awry\n" +
                "And lose the name of action.";

        String startingPoint = "To b";
        StringBuilder sonnet = new StringBuilder(startingPoint);
        for (int i = startingPoint.length(); i < fullSonnet.length(); i++) {
            sonnet.append(fullSonnet.charAt(i));
            run(sonnet.toString());
        }
    }

    public static void run(String sonnet) {
        CharacterBuilder builder = new CharacterBuilder(sonnet);

        System.out.println("Running generic algorithm to try and learn to write the following sentence:\n");
        System.out.println(sonnet);
        System.out.println("\nRunning populations...");

        int generations = 0;
        int jump = 10;
        int next = jump - 1;
        long start = System.currentTimeMillis();
        long milliseconds = start;
        for (int i = 0; i < ROUNDS; i++) {
            if (i % jump == 0 || i == 0) {
                System.out.print("Running population #" + i + " - #" + (i + jump));
            }

            Population population = new Population(sonnet, builder, Settings.MUTATION_RATE, Settings.POPULATION_MAX);
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
        long total = System.currentTimeMillis() - start;

        System.out.println("Done!\n");
        System.out.println("It took an average of " + (generations / ROUNDS) + " generations to get to the end result.");
        System.out.println("Total time taken was " + (total) + " ms (An average of " + (total / ROUNDS) + " ms each population)\n");
    }
}
