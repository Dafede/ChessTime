package es.wander.chesstime;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuScreen implements Screen, InputProcessor, TextInputListener{
	private SpriteBatch batch;
	private Texture backgroundTexture;
	
	private Sprite playButton;
	private Sprite optionsButton;
	
	private Sprite loginButton;
	private Sprite registerButton;
	
	private boolean playAndOption = true;
	private boolean loginAndRegister = false;
	private boolean loginMenu = false;
	private boolean registerMenu = false;
	
	private Sprite textInput1;
	private Sprite textInput2;
	private BitmapFont usernameLabel;
	private BitmapFont passwordLabel;


	
	
	
	public void setMenusFalse(){
		playAndOption=false;
		loginAndRegister=false;
		loginMenu=false;
		registerMenu=false;
	}
	@Override
	public void show() {

		batch = new SpriteBatch();
		
		backgroundTexture = new Texture("menuBackground.png");
		
		playButton = new Sprite(new Texture("play.png"));
		optionsButton = new Sprite(new Texture("options.png"));
		
		loginButton = new Sprite(new Texture("loginButton.png"));
		registerButton = new Sprite(new Texture("registerButton.png"));
		
		usernameLabel = new BitmapFont();
		passwordLabel = new BitmapFont();
		textInput1 = new Sprite(new Texture("textinput.png"));
		textInput2 = new Sprite(new Texture("textinput.png"));
		
		playButton.setPosition(Gdx.graphics.getWidth()/4.25f, Gdx.graphics.getHeight()/4);
		optionsButton.setPosition(Gdx.graphics.getWidth()/4.25f, playButton.getY()-playButton.getHeight()-10);
		
		Gdx.input.setInputProcessor(this);	
	}
	
	public void setPositionLoginAndRegister(){
		loginButton.setPosition(Gdx.graphics.getWidth()/4.25f, Gdx.graphics.getHeight()/4);
		registerButton.setPosition(Gdx.graphics.getWidth()/4.25f, loginButton.getY()-loginButton.getHeight()-10);
	}
	
	public void setPositionTextsInputs(){
		textInput1.setPosition(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/4);
		textInput2.setPosition(Gdx.graphics.getWidth()/3, textInput1.getY()-textInput1.getHeight()-10);
		
			
	}
	
	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0.078f, 0.588f, 0.784f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(backgroundTexture,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		
		
		if(playAndOption){
			playButton.draw(batch);
			optionsButton.draw(batch);
		}

		if(loginAndRegister){
			loginButton.draw(batch);
			registerButton.draw(batch);
		}
		
		if(loginMenu){
			textInput1.draw(batch);
			textInput2.draw(batch);
		}
		
		if(registerMenu){

		}
		
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

	public void moveButtonsPlayAndOptions(){
		playButton.setPosition(-100, -100);
		optionsButton.setPosition(-100, -100);
	}
	
	public  void moveButtonsLoginAndRegister(){
		loginButton.setPosition(-100, -100);
		registerButton.setPosition(-100, -100);
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
		if ( loginButton.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY) )
		 {
			loginAndRegister=false;
			loginButton.setScale(1.1f);
		 }
		if ( registerButton.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY) )
		 {
			registerButton.setScale(1.1f);
		 }
		
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		
	 if ( playButton.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY) )
	 {
		 playButton.setScale(1);
		 moveButtonsPlayAndOptions();
		 setPositionLoginAndRegister();
		 setMenusFalse();
		 loginAndRegister=true;
		 
		 //((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
		 //batch.dispose();
		 //Gdx.input.getTextInput(this, "Dialog Title", "Initial Textfield Value");
	 }
	 
	 if ( optionsButton.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY) )
	 {
		 optionsButton.setScale(1f);
	 }
	 
	 
	 if ( loginButton.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY) &&  !loginAndRegister )
	 {
		 loginButton.setScale(1f);
		 moveButtonsLoginAndRegister();
		 setMenusFalse(); 
		 loginMenu=true;
	 }
	 
	 if ( registerButton.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY) &&  !loginAndRegister )
	 {
		 registerButton.setScale(1f);
		 setMenusFalse();
		 registerMenu=true;
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

	@Override
	public void input(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void canceled() {
		// TODO Auto-generated method stub
		
	}

}
