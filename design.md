# Design
## StateMachine
The StateMachine object controls the flow of the program. It will handle running the render function and tick function for all the states.

## States
A state is a "state" of the program. For example the title screen and game loop are their own states.

## Levels
Levels will be 2d stages that are rendered in 3d by the Stage state.

## Player
The player is going to work like a character is a 2d game. The player will only have an X & Y.

## Entities
Entities are anything that isn't part of the walls. ex Enemies and pickups. Entities will only have an X & Y coordnate like the player. They will be rendered as an image on the player's screen,