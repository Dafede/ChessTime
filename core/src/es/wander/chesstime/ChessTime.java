package es.wander.chesstime;

import com.badlogic.gdx.Game;

public class ChessTime extends Game {
	@Override
	public void create() {
		// TODO Auto-generated method stub
		
		setScreen(new SplashScreen());
		//setScreen(new LobbyScreen());
		//setScreen(new GameScreenNewEdition());
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		super.pause();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		super.resume();
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		super.resize(width, height);
	}
}
