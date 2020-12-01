package swingy.mvc.views;

public interface InterfaceView {

    void pickHero() throws Exception;

    void draw();

    void viewRepaint();

    void scrollPositionManager();

    void updateData();

    void printTextToLog(String text);

    boolean askYesOrNoQuestion(String message);

    String getCurrentViewType();

    void close();
}