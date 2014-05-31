package es.wander.chesstime;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuScreen implements Screen, InputProcessor{
	private SpriteBatch batch;
	private Texture backgroundTexture;
	private Sprite playButton;
	private Sprite optionsButton;
	
	@Override
	public void show() {

		batch = new SpriteBatch();
		
		backgroundTexture = new Texture("menuBackground.png");
		playButton = new Sprite(new Texture("play.png"));
		optionsButton = new Sprite(new Texture("options.png"));
		
		Gdx.input.setInputProcessor(this);		
	}
	
	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0.078f, 0.588f, 0.784f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(backgroundTexture,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		
		playButton.draw(batch);
		playButton.setPosition(Gdx.graphics.getWidth()/4.25f, Gdx.graphics.getHeight()/4);
		
		optionsButton.draw(batch);
		optionsButton.setPosition(Gdx.graphics.getWidth()/4.25f, playButton.getY()-playButton.getHeight()-10);
		batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}


	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		if ( playButton.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY) )
		 {
			 playButton.setScale(1.1f);
		 }
		if ( optionsButton.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY) )
		 {
			optionsButton.setScale(1.1f);
		 }
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		
	 if ( playButton.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY) )
	 {
		 playButton.setScale(1);
		 ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
		 batch.dispose();
	 }
	 
	 if ( optionsButton.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY) )
	 {
		 optionsButton.setScale(1f);
	 }
	
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
