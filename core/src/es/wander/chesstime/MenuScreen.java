package es.wander.chesstime;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class MenuScreen implements Screen, InputProcessor {
	
	private SpriteBatch batch;
	
	private Texture backgroundTexture;

	private Sprite loginButton;
	
	private Sprite registerButton;
	
	BitmapFont font;
	
	TextField userTagTextField;

	private Stage stage;
	
	@Override
	public void show() {
		
		stage = new Stage();

		batch = new SpriteBatch();

		backgroundTexture = new Texture("menuBackground.png");

		InputMultiplexer multiplexer = new InputMultiplexer();
		
		multiplexer.addProcessor(stage);
		
		multiplexer.addProcessor(this);
		
		Gdx.input.setInputProcessor(multiplexer);

		loginButton = new Sprite(new Texture("loginButton.png"));
		
		registerButton = new Sprite(new Texture("registerButton.png"));

		loginButton.setPosition(Gdx.graphics.getWidth() / 4.25f,Gdx.graphics.getHeight() / 4);
		
		registerButton.setPosition(Gdx.graphics.getWidth() / 4.25f,	loginButton.getY() - loginButton.getHeight() - 10);
		
		font = new BitmapFont();
		
		TextFieldStyle userTagTextFieldStyle = new TextFieldStyle();
		userTagTextFieldStyle.font = font;
		userTagTextFieldStyle.fontColor = Color.BLACK;
	
		
		userTagTextFieldStyle.background = new SpriteDrawable(new Sprite(new Texture("textinput.png")));

		userTagTextField = new TextField("", userTagTextFieldStyle);
		
		userTagTextField.setText("  Insert Username");
		
		userTagTextField.setTextFieldListener(new TextFieldListener() {
			public void keyTyped (TextField textField, char key) {
				if (key == '\n') textField.getOnscreenKeyboard().show(false);
			}
		});
		
		userTagTextField.setPosition(50, 300);
		
		stage.addActor(userTagTextField);

	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0.078f, 0.588f, 0.784f, 1);
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		loginButton.draw(batch);
		
		registerButton.draw(batch);
		
		batch.end();
		
		//stage.draw();

	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		if (loginButton.getBoundingRectangle().contains(screenX,Gdx.graphics.getHeight() - screenY)) {
			loginButton.setScale(1.1f);
			
		}
		if (registerButton.getBoundingRectangle().contains(screenX,	Gdx.graphics.getHeight() - screenY)) {
			registerButton.setScale(1.1f);
		}

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub

		if (loginButton.getBoundingRectangle().contains(screenX,Gdx.graphics.getHeight() - screenY)	) {
			
			loginButton.setScale(1);
			
			Timer.schedule(new Task(){
			    @Override
			    public void run() {
			    	dispose();
			    	((Game)Gdx.app.getApplicationListener()).setScreen(new LoginScreen());
			    }
			}, 0.05f);	
			
		}

		if (registerButton.getBoundingRectangle().contains(screenX,	Gdx.graphics.getHeight() - screenY)	) {
			registerButton.setScale(1f);
		}

		return false;
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
		batch.dispose();
		backgroundTexture.dispose();
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
