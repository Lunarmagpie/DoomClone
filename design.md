# Design
### Game Loop
The GameLoop object controls the flow of the program. It will handle running the render function and tick function for all the states. On the tick is when the game will process information & render the screen. The reason for this is that we can keep a steady 60 frames per second with this method.

### States
A state is a "state" of the program. For example the title screen and game loop are their own states.

### Levels
Levels will be 2d stages that are rendered in 3d by the Stage state. The game will only have 1 level.

### Player
The player is going to work like a character is a 2d game. The player will only have an X & Y. The coordinate system we are using for the map data and player position is Cartesian coordinates

### Entities
Entities are anything that are rendered as 2d sprites & not walls (ex. Enemies and pickups). Entities will only have an X & Y coordnate like the player. They will be rendered as an image on the player's screen, and will always face the player.

## File Structure

```python
src
꜖ core
  ꜖ GameLoop.java
  ꜖ Ray.java
  ꜖ Render2D.java
  ꜖ Render3D.java
  ꜖ RenderMap.java
꜖ entities
  ꜖ EnemyABC.java
  ꜖ EntityABC.java
  ꜖ ItemABC.java
꜖ stages
  ꜖ LevelABC.java
  ꜖ Stage1.java
꜖ states
  ꜖ StateABC.java
  ꜖ TitleScreen.java
  ꜖ Level.java
꜖ player
  ꜖ Player.java
  ꜖ Weapon.java
꜖ Main.java
```
