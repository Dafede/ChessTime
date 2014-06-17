package es.wander.chesstime;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class SplashScreen implements Screen{
	SpriteBatch batch;
	Texture logoTexture;

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.533f, 0.533f, 0.533f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(logoTexture, (Gdx.graphics.getWidth() - logoTexture.getWidth()) / 2, (Gdx.graphics.getHeight() - logoTexture.getHeight()) / 2);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		batch = new SpriteBatch();
		logoTexture = new Texture("logo.png");
		float delay = 0.1f; // seconds

		Timer.schedule(new Task(){
		    @Override
		    public void run() {
		    	((Game)Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
		    	batch.dispose();
		    }
		}, delay);
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

}
