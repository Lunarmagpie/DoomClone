package states;

public abstract class StateABC {
    
    public abstract void tick(double delta);

    public abstract void render(double delta);
    
}
