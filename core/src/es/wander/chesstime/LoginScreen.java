package es.wander.chesstime;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class LoginScreen implements Screen, InputProcessor, TextInputListener {

	private SpriteBatch batch;
	
	private Texture backgroundTexture;
	
	private Sprite textInput1;
	
	private Sprite textInput2;
	
	private BitmapFont usernameLabel;
	
	private BitmapFont passwordLabel;
	
	private Sprite okButton;
	
	private Sprite cancelButton;
	
	private BitmapFont insertedUsername;
	
	private BitmapFont insertedPassword;
	
	String usernameText ="";
	
	String passwordText="";
	
	boolean insertUsername=false;
	
	boolean insertPassword=false;
	
	String resultGet="";
	
	boolean challengeSentBool=false;
	
	Sprite black;
	
	BitmapFont modalMessage;
	
	String modalMessageString="";
	
	@Override
	public void show() {
		
		batch = new SpriteBatch();
		
		backgroundTexture = new Texture("menuBackground.png");

		usernameLabel = new BitmapFont();
		
		passwordLabel = new BitmapFont();
		
		textInput1 = new Sprite(new Texture("textinput.png"));
		
		textInput2 = new Sprite(new Texture("textinput.png"));
		
		okButton = new Sprite(new Texture("okButton.png"));
		
		cancelButton = new Sprite(new Texture("cancelButton.png"));
		
		insertedPassword = new BitmapFont();
		
		insertedUsername = new BitmapFont();
		
		textInput1.setPosition(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/4);
		
		textInput2.setPosition(Gdx.graphics.getWidth()/3, textInput1.getY()-textInput1.getHeight()-10);
		
		okButton.setPosition(Gdx.graphics.getWidth()/15, textInput2.getY()-50);
		
		cancelButton.setPosition(Gdx.graphics.getWidth()/3+textInput2.getWidth()-cancelButton.getWidth(),textInput2.getY()-50);
		
		black = new Sprite(new Texture("black.png"));
		
		black.setPosition(0,0);

		black.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		
		black.setAlpha(0.7f);
		
		modalMessage = new BitmapFont();
		
		modalMessage.setColor(Color.WHITE);
		
		Gdx.input.setInputProcessor(this);
		
	}
	
	@Override
	public void render(float delta) {
		
		batch.begin();
		
		batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		textInput1.draw(batch);
		
		textInput2.draw(batch);
		
		usernameLabel.draw(batch, "Username: ",Gdx.graphics.getWidth()/15,  Gdx.graphics.getHeight()/4 + textInput1.getHeight()/1.5f +2);
		
		passwordLabel.draw(batch, "Password: ",Gdx.graphics.getWidth()/15, Gdx.graphics.getHeight()/4  + textInput1.getHeight()/1.5f -45);
	
		insertedUsername.setColor(Color.BLACK);
		
		insertedUsername.draw(batch, usernameText, Gdx.graphics.getWidth()/2 - 30,  Gdx.graphics.getHeight()/4 +textInput1.getHeight()/1.5f +2);
		
		insertedPassword.setColor(Color.BLACK);
		
		insertedPassword.draw(batch, passwordText, Gdx.graphics.getWidth()/2 - 30, Gdx.graphics.getHeight()/4  + textInput1.getHeight()/1.5f -45);
		
		okButton.draw(batch);
		
		cancelButton.draw(batch);
		
		if(challengeSentBool){
			
			black.draw(batch);
			
			modalMessage.draw(batch, modalMessageString, Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/2);
		}
		
		batch.end();
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		if(okButton.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY)){
			
			 okButton.setScale(1.1f);
			 
		}
		
		if(cancelButton.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY)){
			
			 cancelButton.setScale(1.1f);
			 
		}
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		 if(textInput1.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY)){
			 
			 insertUsername=true;
			 
			 Gdx.input.getTextInput(this, "Insert Username", "");
			
		 }
		 
		 if(textInput2.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY)){
			
			 insertPassword=true;
			 
			 Gdx.input.getTextInput(this, "Insert Password", "");
			
		 }
		 if(cancelButton.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY)){
			 
			 cancelButton.setScale(1);
				
				Timer.schedule(new Task(){
				    @Override
				    public void run() {
				    	
				    	dispose();
				    	
				    	((Game)Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
				    }
				}, 0.05f);
			 
		}
		 if(okButton.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY)){
			
			okButton.setScale(1);

			Timer.schedule(new Task() {
				@Override
				public void run() {

					if (checkLoginRequest())
						((Game) Gdx.app.getApplicationListener()).setScreen(new LobbyScreen());
				}
			}, 0.05f);

		}

		
		return false;
	}
	
	public void modalMessage(String message){
		modalMessageString=message;
		Timer.schedule(new Task(){
			@Override
			public void run() {
				challengeSentBool=false;
			}
		}, 1.5f);
				
		challengeSentBool=true;
	}
	
	public boolean checkLoginRequest() {
		if (usernameText.compareTo("") == 0){
			modalMessage("Enter username");
			return false;}
		else if (passwordText.compareTo("") == 0){
			modalMessage("Enter password");
			return false;}

		try {
			resultGet = sendGet(usernameText, passwordText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(resultGet.compareTo("0")==0){
			modalMessage("Username o password incorrectos");
			return false;
		}
		
		if (resultGet.compareTo("1")==0){
			UserSession.User=usernameText;
			UserSession.Password=passwordText;
			return true;
		}
		
		return false;
	}
	
	@Override
	public void input(String text) {
		
		if (insertUsername) {
			
			usernameText = text;
			
			insertUsername=false;
			
		}

		if (insertPassword) {
			
			passwordText = text;
			
			insertPassword=false;
			
		}
		
	}
	
	private String sendGet(String username, String password) throws Exception {
		//http://84.123.125.224/chesstime/login.php?username=asd&password=asddd
		String url = "http://chesstime.net46.net/login.php?username="+username+"&password="+password;
		//http://chesstime.net46.net/getChallengeTurn.php?user1=david&user2=ernesto
		String response="";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection(); 
		//int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'GET' request to URL : " + url);
		//System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		response = in.readLine();
		//String inputLine;
		//StringBuffer response = new StringBuffer();
		//while ((inputLine = in.readLine()) != null) {
		//	response.append(inputLine);
		//}
		in.close();
		//System.out.println(response.toString());
		return response;
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		backgroundTexture.dispose();
		usernameLabel.dispose();
		passwordLabel.dispose();
		insertedPassword.dispose();
		insertedUsername.dispose();
	}

	@Override
	public void canceled() {
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

}
