package states;

public abstract class State {
    
    public abstract void tick(double delta);

    public abstract void render(double delta);
    
}
