package es.wander.chesstime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
	
	private boolean finalLogin=false;
	
	private boolean finalMenu=false;
	
	private Sprite textInput1;
	private Sprite textInput2;
	private BitmapFont usernameLabel;
	private BitmapFont passwordLabel;
	
	private Sprite okButton;
	private Sprite cancelButton;
	
	private BitmapFont insertedUsername;
	private BitmapFont insertedPassword;
	
	
	private String usernameText="";
	private boolean usernameTextBool=false;
	
	private String passwordText="";
	private boolean passwordTextBool=false;

	private boolean insertUsername=false;
	private boolean insertPassword=false;
	
	
	
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
		
		okButton = new Sprite(new Texture("okButton.png"));
		cancelButton = new Sprite(new Texture("cancelButton.png"));
		
		insertedPassword = new BitmapFont();
		insertedUsername = new BitmapFont();
		
		
		
		
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
		
		okButton.setPosition(Gdx.graphics.getWidth()/15, textInput2.getY()-50);
		cancelButton.setPosition(Gdx.graphics.getWidth()/3+textInput2.getWidth()-cancelButton.getWidth(),textInput2.getY()-50);
		
			
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

		if(loginAndRegister || finalLogin){
			loginButton.draw(batch);
			registerButton.draw(batch);
		}
		
		if(loginMenu || finalMenu){
			textInput1.draw(batch);
			textInput2.draw(batch);
			usernameLabel.draw(batch, "Username: ",Gdx.graphics.getWidth()/15,  Gdx.graphics.getHeight()/4 + textInput1.getHeight()/1.5f +2);
			passwordLabel.draw(batch, "Password: ",Gdx.graphics.getWidth()/15, Gdx.graphics.getHeight()/4  + textInput1.getHeight()/1.5f -45);
		
			if(usernameTextBool){
				insertedUsername.setColor(Color.BLACK);
				insertedUsername.draw(batch, usernameText, Gdx.graphics.getWidth()/2 - 30,  Gdx.graphics.getHeight()/4 +textInput1.getHeight()/1.5f +2);
			}
			if(passwordTextBool){
				insertedPassword.setColor(Color.BLACK);
				insertedPassword.draw(batch, passwordText, Gdx.graphics.getWidth()/2 - 30, Gdx.graphics.getHeight()/4  + textInput1.getHeight()/1.5f -45);
			}
			
			okButton.draw(batch);
			cancelButton.draw(batch);
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
	
	public void moveButtonsLoginAndRegister(){
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
			finalLogin=true;
			loginButton.setScale(1.1f);
			loginAndRegister=false;
		 }
		if ( registerButton.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY) )
		 {
			registerButton.setScale(1.1f);
		 }
		if ( textInput1.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY) )
		 {
			finalMenu=true;
			loginMenu=false;
		 }
		if ( textInput2.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY) )
		 {
			loginMenu=false;
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

	 }
	 
	 if ( optionsButton.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY) )
	 {
		 optionsButton.setScale(1f);
	 }
	 
	 
	 if ( loginButton.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY) &&  !loginAndRegister )
	 {
		 finalLogin=false;
		 loginButton.setScale(1);
		 moveButtonsLoginAndRegister();
		 setPositionTextsInputs();
		 setMenusFalse(); 
		 loginMenu=true;
	 }
	 
	 if ( registerButton.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY) &&  !loginAndRegister )
	 {
		 registerButton.setScale(1f);
		 setMenusFalse();
		 registerMenu=true;
	 }
	 
	 if(textInput1.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY) &&  !loginMenu){
		 finalMenu=false;
		 insertUsername=true;
		 Gdx.input.getTextInput(this, "Insert Username", "");
		 loginMenu=true;
	 }
	 
	 if(textInput2.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY) &&  !loginMenu){
		 finalMenu=false;
		 insertPassword=true;
		 Gdx.input.getTextInput(this, "Insert Password", "");
		 loginMenu=true;
	 }
	 if(okButton.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY)){
		 batch.dispose();
		 
		 try {
			sendGet(usernameText,passwordText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 //((Game)Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
	 }
	
		return false;
	}
	
	private void sendGet(String username, String password) throws IOException{
		//http://84.123.125.224/chesstime/login.php?username=asd&password=asddd
		String url = "http://84.123.125.224/chesstime/login.php?username="+username+"&password="+password;
 
		URL obj = new URL(url);
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection(); 
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		
		System.out.println(response.toString());
		
 
	}
	
	
	@Override
	public void input(String text) {
		

		if(insertUsername){
			usernameText=text;
			usernameTextBool=true;
			insertUsername=false;
		}
		
		if(insertPassword){
			passwordText=text;
			passwordTextBool=true;
			insertPassword=false;
		}
		
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
	public void canceled() {
		// TODO Auto-generated method stub
		
	}

}
