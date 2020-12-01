package swingy.utils;

public class ConsolePrinter {
    private final StringBuilder stringBuilder;

    private ConsolePrinter() {
        this.stringBuilder = new StringBuilder();
    }

    public static ConsolePrinter getPrinter() {
        ConsolePrinter consolePrinter = new ConsolePrinter();
        consolePrinter.stringBuilder.append("Race: \n");
        return consolePrinter;
    }

    public ConsolePrinter setNumber(int number) {
        this.stringBuilder.append(number)
                .append(" - to ");
        return this;
    }

    public ConsolePrinter setMessage(String message) {
        this.stringBuilder.append(message)
                .append("\n");
        return this;
    }

    public void printMessage() {
        System.out.println(this.stringBuilder.toString());
    }
}
