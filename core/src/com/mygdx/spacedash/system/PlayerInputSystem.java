package com.mygdx.spacedash.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.spacedash.component.MovementComponent;
import com.mygdx.spacedash.component.PlayerComponent;
import com.mygdx.spacedash.component.tools.Mappers;
import com.mygdx.spacedash.screen.GameScreen;

public class PlayerInputSystem extends EntitySystem {

    private static final Family FAMILY_PLAYER= Family.all(
            PlayerComponent.class,
            MovementComponent.class
    ).get();

    private Entity player;
    private float timer;
    private GameScreen game;

    public PlayerInputSystem(GameScreen game){
        this.game=game;
    }

    @Override
    public void addedToEngine (Engine engine) {
        player = engine.getEntitiesFor(FAMILY_PLAYER).get(0);
        timer=0;
    }

    @Override
    public void update(float deltaTime) {
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            MovementComponent movement = Mappers.MOVEMENT.get(player);
            movement.velocitX+= movement.velocityTemp*(float)Math.cos(movement.angle_rad);
            movement.velocitY+= movement.velocityTemp*(float)Math.sin(movement.angle_rad);
            timer=0;
            if(game.isGameOver()){
                game.Restart();
            }
        }else if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            if(timer>0.2){
                MovementComponent movement = Mappers.MOVEMENT.get(player);
                movement.velocitX+= deltaTime * 10 *  movement.velocityTemp*(float)Math.cos(movement.angle_rad);
                movement.velocitY+= deltaTime * 10 * movement.velocityTemp*(float)Math.sin(movement.angle_rad);
            }else {
                timer+=deltaTime;
            }
        }
        if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)){
            game.Restart();
        }
    }
}
