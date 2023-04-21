public class Game {
    private static Game game;

    private Game() {

    }

    public static Game getInstance() {
        if (game == null)
            game = new Game();
        return game;
    }
}
