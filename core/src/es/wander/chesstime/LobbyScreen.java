package es.wander.chesstime;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class LobbyScreen implements Screen, InputProcessor, TextInputListener {

	private SpriteBatch batch;

	private Texture backgroundTexture;
	private BitmapFont searchLabel;
	private Sprite textInputSearch;
	private BitmapFont challengeLabel;
	private Sprite challengePlayer;
	private Sprite boxChallenges;
	boolean insertUsername=false;
	String usernameText="";
	boolean usernameTextBool=false;
	BitmapFont usernameLabel;
	Sprite black;
	BitmapFont challengeSent;
	boolean MODALbool=false;
	
	String usersChallengedMe="";
	
	Sprite exampleChallenge;
	Sprite exampleChallengeButton;
	BitmapFont exampleChallengeText;
	
	Sprite exampleChallenge2;
	Sprite exampleChallengeButton2;
	BitmapFont exampleChallengeText2;
	
	Sprite exampleChallenge3;
	Sprite exampleChallengeButton3;
	BitmapFont exampleChallengeText3;
	
	Sprite NexampleChallenge;
	Sprite NexampleChallengeButton;
	BitmapFont NexampleChallengeText;
	
	Sprite NexampleChallenge2;
	Sprite NexampleChallengeButton2;
	BitmapFont NexampleChallengeText2;
	
	Sprite NexampleChallenge3;
	Sprite NexampleChallengeButton3;
	BitmapFont NexampleChallengeText3;
	
	Sprite exampleDeleteButton;
	Sprite exampleDeleteButton2;
	Sprite exampleDeleteButton3;
	
	Sprite NexampleDeleteButton;
	Sprite NexampleDeleteButton2;
	Sprite NexampleDeleteButton3;
	
	boolean exampleChallengeBool=false;
	
	Sprite refreshButton;
	
	Sprite exampleProcess;
	Sprite exampleProcess2;
	Sprite exampleProcess3;
	
	String usersGamesInProcess="";
	
	String textMODAL="";
	int challengesSended=0;
	
	float heightFile;
	
	boolean userInProcess1=false;
	boolean userInProcess2=false;
	boolean userInProcess3=false;
	
	String textUserInProgress1="";
	String textUserInProgress2="";
	String textUserInProgress3="";
	
	boolean userInProcessToMe1=false;
	boolean userInProcessToMe2=false;
	boolean userInProcessToMe3=false;
	
	String textUserInProgressToMe1="";
	String textUserInProgressToMe2="";
	String textUserInProgressToMe3="";
	
	boolean breakifs2=true;
	boolean breakifs3=true;
	
	
	float E1X, E1Y, E1XB, E1YB,E2X, E2Y, E2XB, E2YB,E3X, E3Y, E3XB, E3YB,E1XDB,E1YDB,E2XDB,E2YDB,E3XDB,E3YDB;
	
	//contiene los jugadores de las 3 partidas posibles en proceso
	// [0]vs[1]  [2]vs[3] ... siendo [user1] vs [user2]
	String[] usersChallengeInProcess = new String[6];
	
	//contiene los jugadores de  3  o menos partidas posibles que nos han retado
		// [0]vs[1]  [2]vs[3] ... siendo [user1] vs [user2]
		String[] usersChallengeToMe = new String[6];
	
	@Override
	public void show() {
		
		batch = new SpriteBatch();

		Gdx.input.setInputProcessor(this);

		backgroundTexture = new Texture("menuBackground.png");

		textInputSearch = new Sprite(new Texture("textinput.png"));
		textInputSearch.setPosition(Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 20 - textInputSearch.getHeight() * 2);
		textInputSearch.setSize(Gdx.graphics.getWidth() - ((Gdx.graphics.getWidth() / 15) * 2),	textInputSearch.getHeight());
		
		challengePlayer = new Sprite(new Texture("challengePlayer.png"));
		challengePlayer.setPosition(textInputSearch.getX() + ((textInputSearch.getWidth() - challengePlayer.getWidth()) / 2), Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 20 - textInputSearch.getHeight() * 4);

		searchLabel = new BitmapFont();

		challengeLabel = new BitmapFont();
		
		usernameLabel = new BitmapFont();
		usernameLabel.setColor(Color.BLACK);
		
		black = new Sprite(new Texture("black.png"));
		black.setPosition(0,0);
		black.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		black.setAlpha(0.7f);
		
		challengeSent = new BitmapFont();
		challengeSent.setColor(Color.WHITE);
		challengeSent.scale(1f);
	
		boxChallenges = new Sprite(new Texture("textinputBig.png"));
		boxChallenges.setPosition(Gdx.graphics.getWidth() / 15, 20);
		boxChallenges.setSize(textInputSearch.getWidth(), Gdx.graphics.getHeight()/2);	
		
		refreshButton = new Sprite(new Texture("refreshButton.png"));
		refreshButton.setSize(refreshButton.getWidth()/2, refreshButton.getHeight()/2);
		refreshButton.setPosition(boxChallenges.getWidth()-refreshButton.getWidth(),boxChallenges.getY()+boxChallenges.getHeight()+5);
		
		// Cargamos maximo 3 partidas...
		setBooleans();
		loadGames();

		loadFile1();
		loadFile2();
		loadFile3();
		
		//volvemos a posicionar y redimensionar ya que lo hacemos en funcion de las filas anteriormente cargadas
		boxChallenges.setSize(textInputSearch.getWidth(), heightFile*3);	
		boxChallenges.setPosition(exampleChallenge3.getX(), exampleChallenge3.getY());
		
		exampleProcess =new Sprite(new Texture("inProcess.png"));
		exampleProcess.setPosition(boxChallenges.getX(),boxChallenges.getY()+ boxChallenges.getHeight()- exampleChallenge.getHeight());
		exampleProcess.setSize(boxChallenges.getWidth(),exampleChallenge.getHeight());
	
		exampleProcess2 =new Sprite(new Texture("inProcess.png"));
		exampleProcess2.setPosition(exampleChallenge.getX(),exampleChallenge.getY()-exampleChallenge2.getHeight());
		exampleProcess2.setSize(boxChallenges.getWidth(),exampleChallenge2.getHeight());

		exampleProcess3 =new Sprite(new Texture("inProcess.png"));
		exampleProcess3.setPosition(exampleChallenge2.getX(),exampleChallenge2.getY()-exampleChallenge3.getHeight());
		exampleProcess3.setSize(boxChallenges.getWidth(),exampleChallenge3.getHeight());	

		
//////////no tienen posicion por que se añaden dinamicamente en el render///////////
		NexampleChallenge = new Sprite(new Texture("challengeFile.png"));
		NexampleChallenge.setSize(boxChallenges.getWidth(),exampleChallenge.getHeight());
		NexampleChallengeButton = new Sprite(new Texture("playButton.png"));
		NexampleChallengeButton.setSize(NexampleChallengeButton.getWidth()/1.5f, NexampleChallengeButton.getHeight()/1.5f);
		NexampleDeleteButton = new Sprite(new Texture("deleteButton.png"));
		NexampleDeleteButton.setSize(NexampleDeleteButton.getWidth()/1.5f, NexampleDeleteButton.getHeight()/1.5f);
		
		
		NexampleChallengeText = new BitmapFont();
		NexampleChallengeText.setColor(Color.WHITE);
		NexampleChallengeText.setScale(0.9f);
///////		
		NexampleChallenge2 = new Sprite(new Texture("challengeFile.png"));
		NexampleChallenge2.setSize(boxChallenges.getWidth(),exampleChallenge.getHeight());		
		NexampleChallengeButton2 = new Sprite(new Texture("playButton.png"));
		NexampleChallengeButton2.setSize(NexampleChallengeButton2.getWidth()/1.5f, NexampleChallengeButton2.getHeight()/1.5f);
		NexampleDeleteButton2 = new Sprite(new Texture("deleteButton.png"));
		NexampleDeleteButton2.setSize(NexampleDeleteButton2.getWidth()/1.5f, NexampleDeleteButton2.getHeight()/1.5f);
		
		NexampleChallengeText2 = new BitmapFont();
		NexampleChallengeText2.setColor(Color.WHITE);
		NexampleChallengeText2.setScale(0.9f);
///////
		NexampleChallenge3 = new Sprite(new Texture("challengeFile.png"));	
		NexampleChallenge3.setSize(boxChallenges.getWidth(),exampleChallenge.getHeight());		
		NexampleChallengeButton3 = new Sprite(new Texture("playButton.png"));
		NexampleChallengeButton3.setSize(NexampleChallengeButton3.getWidth()/1.5f, NexampleChallengeButton3.getHeight()/1.5f);
		NexampleDeleteButton3 = new Sprite(new Texture("deleteButton.png"));
		NexampleDeleteButton3.setSize(NexampleDeleteButton3.getWidth()/1.5f, NexampleDeleteButton3.getHeight()/1.5f);
		
		NexampleChallengeText3 = new BitmapFont();
		NexampleChallengeText3.setColor(Color.WHITE);
		NexampleChallengeText3.setScale(0.9f);

	}	
	
	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0.078f, 0.588f, 0.784f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();	
		batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		textInputSearch.draw(batch);
		challengePlayer.draw(batch);
		searchLabel.draw(batch, "Challenging Users", Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 20);
		challengeLabel.draw(batch, "Challenges", Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 2.5f);
		boxChallenges.draw(batch);
		
		if(usernameTextBool){
			usernameLabel.draw(batch, usernameText,Gdx.graphics.getWidth() / 15 + 25, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 20 - textInputSearch.getHeight() * 2 +25);
		}
		
		refreshButton.draw(batch);
		

		if(userInProcess1){
			exampleChallenge.draw(batch);
			exampleProcess.draw(batch);
			exampleChallengeText.draw(batch,textUserInProgress1,exampleChallenge.getX()+10,exampleChallenge.getY()+exampleChallenge.getHeight()/2 +5);
			exampleChallengeButton.draw(batch);
			exampleDeleteButton.draw(batch);
		}
		if(userInProcess2){
			exampleChallenge2.draw(batch);
			exampleProcess2.draw(batch);
			exampleChallengeText2.draw(batch,textUserInProgress2,exampleChallenge2.getX()+10,exampleChallenge2.getY()+exampleChallenge2.getHeight()/2 +5);
			exampleChallengeButton2.draw(batch);
			exampleDeleteButton2.draw(batch);
		}
		if(userInProcess3){
			exampleChallenge3.draw(batch);
			exampleProcess3.draw(batch);
			exampleChallengeText3.draw(batch,textUserInProgress3,exampleChallenge3.getX()+10,exampleChallenge3.getY()+exampleChallenge3.getHeight()/2 +5);
			exampleChallengeButton3.draw(batch);
			exampleDeleteButton3.draw(batch);
		}
		
		//////aki dibujamos los retos que no estan en proceso, es decir los que me han mandado
		////// tengo que posicionarlos de manera dinamica segun el numero de los que hayan en proceso
		if(!userInProcess1){
			breakifs2=false;
			breakifs3=false;
			if(userInProcessToMe1){
				NexampleChallenge.setPosition(E1X,E1Y);
				NexampleChallengeButton.setPosition(E1XB,E1YB);
				NexampleDeleteButton.setPosition(E1XDB, E1YDB);
				NexampleChallenge.draw(batch);
				NexampleChallengeText.draw(batch,textUserInProgressToMe1,NexampleChallenge.getX()+10,NexampleChallenge.getY()+NexampleChallenge.getHeight()/2 +5);
				NexampleChallengeButton.draw(batch);
				NexampleDeleteButton.draw(batch);
			}
			if(userInProcessToMe2){
				NexampleChallenge2.setPosition(E2X,E2Y);
				NexampleChallengeButton2.setPosition(E2XB,E2YB);
				NexampleDeleteButton2.setPosition(E2XDB, E2YDB);
				NexampleChallenge2.draw(batch);
				NexampleChallengeText2.draw(batch,textUserInProgressToMe2,NexampleChallenge2.getX()+10,NexampleChallenge2.getY()+NexampleChallenge2.getHeight()/2 +5);
				NexampleChallengeButton2.draw(batch);
				NexampleDeleteButton2.draw(batch);
			}
			if(userInProcessToMe3){
				NexampleChallenge3.setPosition(E3X,E3Y);
				NexampleChallengeButton3.setPosition(E3XB,E3YB);
				NexampleDeleteButton3.setPosition(E3XDB, E3YDB);
				NexampleChallenge3.draw(batch);
				NexampleChallengeText3.draw(batch,textUserInProgressToMe3,NexampleChallenge3.getX()+10,NexampleChallenge3.getY()+NexampleChallenge3.getHeight()/2 +5);
				NexampleChallengeButton3.draw(batch);
				NexampleDeleteButton3.draw(batch);
			}
		}
		if(!userInProcess2 && breakifs2){
			breakifs3=false;
			if(userInProcessToMe1){
				NexampleChallenge.setPosition(E2X, E2Y);
				NexampleChallengeButton.setPosition(E2XB, E2YB);
				NexampleDeleteButton.setPosition(E2XDB, E2YDB);
				NexampleChallenge.draw(batch);
				NexampleChallengeText.draw(batch,textUserInProgressToMe1,NexampleChallenge.getX()+10,NexampleChallenge.getY()+NexampleChallenge.getHeight()/2 +5);
				NexampleChallengeButton.draw(batch);		
				NexampleDeleteButton.draw(batch);
			}
			if(userInProcessToMe2){
				NexampleChallenge2.setPosition(E3X, E3Y);
				NexampleChallengeButton2.setPosition(E3XB, E3YB);
				NexampleDeleteButton2.setPosition(E3XDB, E3YDB);
				NexampleChallenge2.draw(batch);
				NexampleChallengeText2.draw(batch,textUserInProgressToMe2,NexampleChallenge2.getX()+10,NexampleChallenge2.getY()+NexampleChallenge2.getHeight()/2 +5);
				NexampleChallengeButton2.draw(batch);
				NexampleDeleteButton2.draw(batch);
			}
		}
		if(!userInProcess3 && breakifs3){
			if(userInProcessToMe1){
				NexampleChallenge.setPosition(E3X, E3Y);
				NexampleChallengeButton.setPosition(E3XB, E3YB);
				NexampleDeleteButton.setPosition(E3XDB, E3YDB);
				NexampleChallenge.draw(batch);
				NexampleChallengeText.draw(batch,textUserInProgressToMe1,NexampleChallenge.getX()+10,NexampleChallenge.getY()+NexampleChallenge.getHeight()/2 +5);
				NexampleChallengeButton.draw(batch);
				NexampleDeleteButton.draw(batch);
			}
		}
		
		//Este if SIEMPRE al final
		if(MODALbool){
			black.draw(batch);
			challengeSent.draw(batch, textMODAL, Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/2);
		}

		batch.end();
		
	}


	
	public void loadGames() {
		//Primero cargaremos las partidas en curso, si no, las partidas que estan por comenzar.
		try {
			usersGamesInProcess = sendGet_getChallengesInProcess(UserSession.User);
			parseUsersGameInProcess(usersGamesInProcess);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (usersChallengeInProcess[0] != null) {
			userInProcess1 = true;
			textUserInProgress1 = usersChallengeInProcess[0] + " VS "+ usersChallengeInProcess[1];
		}
		
		if (usersChallengeInProcess[2] != null) {
			userInProcess2 = true;
			textUserInProgress2 = usersChallengeInProcess[2] + " VS "+ usersChallengeInProcess[3];
		}
		
		if (usersChallengeInProcess[4] != null) {
			userInProcess3 = true;
			textUserInProgress3 = usersChallengeInProcess[4] + " VS "+ usersChallengeInProcess[5];
		}

		//Cargaremos las partidas por comenzar. Los ultimos retos que nos han mandado, o que yo he generado.

		// Si los games in progress anteriores son menores que 3 carga las ofertas restantes hasta 3
		if (userInProcess3 == false) {
			try {
				usersChallengedMe = sendGet_getChallenges(UserSession.User);
				parseUsersGameInProcess1(usersChallengedMe);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (usersChallengeToMe[0] != null) {
				userInProcessToMe1 = true;
				textUserInProgressToMe1 = usersChallengeToMe[0] + " VS "+ usersChallengeToMe[1];
			}
			if (usersChallengeToMe[2] != null) {
				userInProcessToMe2 = true;
				textUserInProgressToMe2 = usersChallengeToMe[2] + " VS "+ usersChallengeToMe[3];
			}
			if (usersChallengeToMe[4] != null) {
				userInProcessToMe3 = true;
				textUserInProgressToMe3 = usersChallengeToMe[4] + " VS "+ usersChallengeToMe[5];
			}
		}
	}
	
	public void setBooleans() {
		
		userInProcess3 = false;
		userInProcess2 = false;
		userInProcess1 = false;
		userInProcessToMe1 = false;
		userInProcessToMe2 = false;
		userInProcessToMe3 = false;
	}
	public void setBreakIfs(){
		breakifs2 = true;
		breakifs3 = true;
	}
	public void resetArrays() {
		for (int i = 0; i < usersChallengeInProcess.length; i++) {
			usersChallengeInProcess[i] = null;
		}
		for (int i = 0; i < usersChallengeToMe.length; i++) {
			usersChallengeToMe[i] = null;
		}
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		if ( challengePlayer.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY) )
		 {
			challengePlayer.setScale(1.1f);
		 }
		if ( exampleChallengeButton.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY) )
		 {
			exampleChallengeButton.setScale(1.1f);
		 }
		if ( refreshButton.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY) )
		 {
			refreshButton.setScale(1.1f);
		 }
		
		// PLAY BUTONS
		if (exampleChallengeButton.getBoundingRectangle().contains(screenX,
				Gdx.graphics.getHeight() - screenY)) {
			exampleChallengeButton.setScale(1.1f);
		}
		if (exampleChallengeButton2.getBoundingRectangle().contains(screenX,
				Gdx.graphics.getHeight() - screenY)) {
			exampleChallengeButton2.setScale(1.1f);
		}
		if (exampleChallengeButton3.getBoundingRectangle().contains(screenX,
				Gdx.graphics.getHeight() - screenY)) {
			exampleChallengeButton3.setScale(1.1f);
		}
		if (NexampleChallengeButton.getBoundingRectangle().contains(screenX,
				Gdx.graphics.getHeight() - screenY)) {
			NexampleChallengeButton.setScale(1.1f);
		}
		if (NexampleChallengeButton2.getBoundingRectangle().contains(screenX,
				Gdx.graphics.getHeight() - screenY)) {
			NexampleChallengeButton2.setScale(1.1f);
		}
		if (NexampleChallengeButton3.getBoundingRectangle().contains(screenX,
				Gdx.graphics.getHeight() - screenY)) {
			NexampleChallengeButton3.setScale(1.1f);
		}

		// DELETE BUTTONS
		if (exampleDeleteButton.getBoundingRectangle().contains(screenX,
				Gdx.graphics.getHeight() - screenY)) {
			exampleDeleteButton.setScale(1.1f);
		}
		if (exampleDeleteButton2.getBoundingRectangle().contains(screenX,
				Gdx.graphics.getHeight() - screenY)) {
			exampleDeleteButton2.setScale(1.1f);
		}
		if (exampleDeleteButton3.getBoundingRectangle().contains(screenX,
				Gdx.graphics.getHeight() - screenY)) {
			exampleDeleteButton3.setScale(1.1f);
		}
		if (NexampleDeleteButton.getBoundingRectangle().contains(screenX,
				Gdx.graphics.getHeight() - screenY)) {
			NexampleDeleteButton.setScale(1.1f);
		}
		if (NexampleDeleteButton2.getBoundingRectangle().contains(screenX,
				Gdx.graphics.getHeight() - screenY)) {
			NexampleDeleteButton2.setScale(1.1f);
		}
		if (NexampleDeleteButton3.getBoundingRectangle().contains(screenX,
				Gdx.graphics.getHeight() - screenY)) {
			NexampleDeleteButton3.setScale(1.1f);
		}
		return false;
	}
	public void refresh(){
		setBooleans();
		// setBreakIfs();
		resetArrays();

		Timer.schedule(new Task() {
			@Override
			public void run() {
				//loadGames() necesita haber resetado antessetBooleans() y resetArrays() 
				loadGames(); 
				// tengo que tener cuidado con cualquier cosa que se use en
				// el render() y que ser resetee, en este caso es el reseteo
				// de variables boolea, no puedo ponerlas antes por que el
				// metodo render sigue dibujando (no poner en
				// "mirar mas  arriba el metodo setBreakIfs();")
				setBreakIfs();
			}
		}, 0.2f);
		}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		if (refreshButton.getBoundingRectangle().contains(screenX,
				Gdx.graphics.getHeight() - screenY)) {
			refreshButton.setScale(1);	
			refresh();	
		}
		
		if (challengePlayer.getBoundingRectangle().contains(screenX,Gdx.graphics.getHeight() - screenY)) {
			
			challengePlayer.setScale(1);
			
			if (usernameText == "") {
				
				textMODAL = "Insert a username";
				Timer.schedule(new Task() {
					@Override
					public void run() {
						MODALbool = false;
					}
				}, 2);
				MODALbool = true;
				
				
			} else {
				// si el usuario al que quiere retar no existe, comunicarlo al jugador
				String responseExists = "";
				try {
					responseExists = sendGet_isUserExists(usernameText);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (responseExists.equals("0")) {
					
					textMODAL = "User not exists";
					Timer.schedule(new Task() {
						@Override
						public void run() {
							MODALbool = false;
						}
					}, 2);
					MODALbool = true;
					
				} else {
					
					try {
						sendGet_challengeUser(UserSession.User, usernameText,-1,-1,-1,-1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					refresh();
					
					textMODAL = "Move! Your turn";
					Timer.schedule(new Task() {
						@Override
						public void run() {
							MODALbool = false;
						}
					}, 2);
					MODALbool = true;
				}

			}
			usernameTextBool=false;
		}

		if (textInputSearch.getBoundingRectangle().contains(screenX,
				Gdx.graphics.getHeight() - screenY)) {
			Gdx.input.getTextInput(this, "Insert Player Username to Challenge",
					"");
			insertUsername = true;
		}
		if (refreshButton.getBoundingRectangle().contains(screenX,
				Gdx.graphics.getHeight() - screenY)) {
			refreshButton.setScale(1);
		}
		
		//PLAY BUTTONS
		if (exampleChallengeButton.getBoundingRectangle().contains(screenX,Gdx.graphics.getHeight() - screenY)) {
			exampleChallengeButton.setScale(1);
			
			UserSession.game1User1=usersChallengeInProcess[0];
			UserSession.game1User2=usersChallengeInProcess[1];
			
			((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreenNewEdition());
		}
		if (exampleChallengeButton2.getBoundingRectangle().contains(screenX,Gdx.graphics.getHeight() - screenY)) {
			exampleChallengeButton2.setScale(1);
			
			UserSession.game2User1=usersChallengeInProcess[2];
			UserSession.game2User2=usersChallengeInProcess[3];			
			
			((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreenNewEdition());
		}
		if (exampleChallengeButton3.getBoundingRectangle().contains(screenX,Gdx.graphics.getHeight() - screenY)) {
			exampleChallengeButton3.setScale(1);
			
			UserSession.game3User1=usersChallengeInProcess[4];
			UserSession.game3User2=usersChallengeInProcess[5];
			
			((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreenNewEdition());
		}
		if (NexampleChallengeButton.getBoundingRectangle().contains(screenX,Gdx.graphics.getHeight() - screenY)) {
			NexampleChallengeButton.setScale(1);
			/*
			System.out.println(usersChallengeToMe[0]+ " - " +usersChallengeToMe[1]);
			System.out.println(usersChallengeToMe[2]+ " - " +usersChallengeToMe[3]);
			System.out.println(usersChallengeToMe[4]+ " - " +usersChallengeToMe[5]);
			*/
			UserSession.game1User1=usersChallengeToMe[0];
			UserSession.game1User2=usersChallengeToMe[1];
			
			((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreenNewEdition());
		}
		if (NexampleChallengeButton2.getBoundingRectangle().contains(screenX,Gdx.graphics.getHeight() - screenY)) {
			NexampleChallengeButton2.setScale(1);
			
			UserSession.game2User1=usersChallengeToMe[2];
			UserSession.game2User2=usersChallengeToMe[3];
			
			((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreenNewEdition());
		}
		if (NexampleChallengeButton3.getBoundingRectangle().contains(screenX,Gdx.graphics.getHeight() - screenY)) {
			NexampleChallengeButton3.setScale(1);
			
			UserSession.game3User1=usersChallengeToMe[4];
			UserSession.game3User2=usersChallengeToMe[5];
			
			
			((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreenNewEdition());
		}

		// DELETE BUTTONS
		if (exampleDeleteButton.getBoundingRectangle().contains(screenX,Gdx.graphics.getHeight() - screenY)) {
			exampleDeleteButton.setScale(1);
			try {
				sendGet_deleteChallenge(usersChallengeInProcess[0],usersChallengeInProcess[1]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Timer.schedule(new Task() {
				@Override
				public void run() {
					
					refresh();
				}
			}, 2);
		}
		if (exampleDeleteButton2.getBoundingRectangle().contains(screenX,Gdx.graphics.getHeight() - screenY)) {
			exampleDeleteButton2.setScale(1);
			try {
				sendGet_deleteChallenge(usersChallengeInProcess[2],usersChallengeInProcess[3]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Timer.schedule(new Task() {
				@Override
				public void run() {
					
					refresh();
				}
			}, 2);
		}
		if (exampleDeleteButton3.getBoundingRectangle().contains(screenX,Gdx.graphics.getHeight() - screenY)) {
			exampleDeleteButton3.setScale(1);
			try {
				sendGet_deleteChallenge(usersChallengeInProcess[4],usersChallengeInProcess[5]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Timer.schedule(new Task() {
				@Override
				public void run() {
					
					refresh();
				}
			}, 2);
		}
		if (NexampleDeleteButton.getBoundingRectangle().contains(screenX,Gdx.graphics.getHeight() - screenY)) {
			NexampleDeleteButton.setScale(1);		
			try {
				sendGet_deleteChallenge(usersChallengeToMe[0],usersChallengeToMe[1]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Timer.schedule(new Task() {
				@Override
				public void run() {
					
					refresh();
				}
			}, 2);
		}
		if (NexampleDeleteButton2.getBoundingRectangle().contains(screenX,Gdx.graphics.getHeight() - screenY)) {
			NexampleDeleteButton2.setScale(1);
			try {
				sendGet_deleteChallenge(usersChallengeToMe[2],usersChallengeToMe[3]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Timer.schedule(new Task() {
				@Override
				public void run() {
					
					refresh();
				}
			}, 2);
		}
		if (NexampleDeleteButton3.getBoundingRectangle().contains(screenX,Gdx.graphics.getHeight() - screenY)) {
			NexampleDeleteButton3.setScale(1);
			try {
				sendGet_deleteChallenge(usersChallengeToMe[4],usersChallengeToMe[5]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Timer.schedule(new Task() {
				@Override
				public void run() {
					
					refresh();
				}
			}, 2);
		}	

		return false;
	}

	////////////////// START GET REQUESTS ////////////////////////////////////
	private String sendGet_isUserExists(String username) throws Exception {
		String url = "http://84.123.125.224/chesstime/isUserExists.php?username="+username;
		String response="";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection(); 
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		response = in.readLine();
		in.close();
		return response;
	}
	private String sendGet_getChallenges(String username) throws Exception {
		String url = "http://84.123.125.224/chesstime/getChallenges.php?username="+username;
		String response="";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection(); 
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		response = in.readLine();
		in.close();
		return response;
	}
	private String sendGet_getChallengesInProcess(String username)throws Exception {
		String url = "http://84.123.125.224/chesstime/getChallengesInProcess.php?username="+ username;		
		String response = "";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		response = in.readLine();
		in.close();
		return response;
	}
	private String sendGet_challengeUser(String me, String other, int originX,int originY, int destinyX, int destinyY) throws Exception {
		String url = "http://84.123.125.224/chesstime/challengeUser.php?me="+ me + "&other=" + other + "&originX=" + originX + "&originY="+ originY + "&destinyX=" + destinyX + "&destinyY=" + destinyY;
		String response = "";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		response = in.readLine();
		in.close();
		return response;
	}
	private String sendGet_deleteChallenge(String user1, String user2) throws Exception {
		String url = "http://84.123.125.224/chesstime/deleteChallenge.php?user1="+ user1 + "&user2=" + user2;
		String response = "";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		response = in.readLine();
		in.close();
		return response;
	}
	////////////////// END  GET REQUESTS /////////////////////////////////////

	public void parseUsersGameInProcess1(String usersChallengedMe){		
		String substringUsers = "";
		int x;
		if (usersChallengedMe != null) {

			x = usersChallengedMe.indexOf("-");
			if (x != -1) {
				usersChallengeToMe[0] = usersChallengedMe
						.substring(0, x);
				substringUsers = usersChallengedMe.substring(x + 1);
			}
			x = substringUsers.indexOf("#");
			if (x != -1) {
				usersChallengeToMe[1] = substringUsers.substring(0, x);
				substringUsers = substringUsers.substring(x + 1);
			}
			x = substringUsers.indexOf("-");
			if (x != -1) {
				usersChallengeToMe[2] = substringUsers.substring(0, x);
				substringUsers = substringUsers.substring(x + 1);
			}
			x = substringUsers.indexOf("#");
			if (x != -1) {
				usersChallengeToMe[3] = substringUsers.substring(0, x);
				substringUsers = substringUsers.substring(x + 1);
			}
			x = substringUsers.indexOf("-");
			if (x != -1) {
				usersChallengeToMe[4] = substringUsers.substring(0, x);
				substringUsers = substringUsers.substring(x + 1);
			}
			x = substringUsers.indexOf("#");
			if (x != -1) {
				usersChallengeToMe[5] = substringUsers.substring(0, x);
				substringUsers = substringUsers.substring(x + 1);
			}
		}
	}
	
	public void parseUsersGameInProcess(String usersGamesInProcess) {

		String substringUsers = "";
		int x;
		if (usersGamesInProcess != null) {

			x = usersGamesInProcess.indexOf("-");
			if (x != -1) {
				usersChallengeInProcess[0] = usersGamesInProcess
						.substring(0, x);
				substringUsers = usersGamesInProcess.substring(x + 1);
			}
			x = substringUsers.indexOf("#");
			if (x != -1) {
				usersChallengeInProcess[1] = substringUsers.substring(0, x);
				substringUsers = substringUsers.substring(x + 1);
			}
			x = substringUsers.indexOf("-");
			if (x != -1) {
				usersChallengeInProcess[2] = substringUsers.substring(0, x);
				substringUsers = substringUsers.substring(x + 1);
			}
			x = substringUsers.indexOf("#");
			if (x != -1) {
				usersChallengeInProcess[3] = substringUsers.substring(0, x);
				substringUsers = substringUsers.substring(x + 1);
			}
			x = substringUsers.indexOf("-");
			if (x != -1) {
				usersChallengeInProcess[4] = substringUsers.substring(0, x);
				substringUsers = substringUsers.substring(x + 1);
			}
			x = substringUsers.indexOf("#");
			if (x != -1) {
				usersChallengeInProcess[5] = substringUsers.substring(0, x);
				substringUsers = substringUsers.substring(x + 1);
			}
		}

	}
	
	@Override
	public void input(String text) {
		
		if(insertUsername){
			usernameText=text;
			usernameTextBool=true;
			insertUsername=false;
		}
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
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}
	public void loadFile1(){
		exampleChallenge = new Sprite(new Texture("challengeFile.png"));
		exampleChallenge.setPosition(boxChallenges.getX(),boxChallenges.getY()+ boxChallenges.getHeight()- exampleChallenge.getHeight());
		exampleChallenge.setSize(boxChallenges.getWidth(),exampleChallenge.getHeight());
		
		exampleChallengeButton = new Sprite(new Texture("playButton.png"));
		exampleChallengeButton.setSize(exampleChallengeButton.getWidth()/1.5f, exampleChallengeButton.getHeight()/1.5f);
		exampleChallengeButton.setPosition(exampleChallenge.getWidth()-exampleChallengeButton.getWidth()*2+10 ,exampleChallenge.getY()+ exampleChallenge.getHeight()/2 - exampleChallengeButton.getHeight()/2 );
		
		exampleDeleteButton = new Sprite(new Texture("deleteButton.png"));
		exampleDeleteButton.setSize(exampleDeleteButton.getWidth()/1.5f,exampleDeleteButton.getHeight()/1.5f);
		exampleDeleteButton.setPosition(exampleChallenge.getWidth()-exampleDeleteButton.getWidth()+10, exampleChallenge.getY()+ exampleChallenge.getHeight()/2 - exampleDeleteButton.getHeight()/2);
		
		E1X=exampleChallenge.getX();
		E1Y=exampleChallenge.getY();
		E1XB=exampleChallengeButton.getX();
		E1YB=exampleChallengeButton.getY();
		
		E1XDB=exampleDeleteButton.getX();
		E1YDB=exampleDeleteButton.getY();
		
		exampleChallengeText = new BitmapFont();
		exampleChallengeText.setColor(Color.WHITE);
		exampleChallengeText.setScale(0.9f);
		
		heightFile = exampleChallenge.getHeight();
		}
	public void loadFile2(){
		exampleChallenge2 = new Sprite(new Texture("challengeFile.png"));
		exampleChallenge2.setPosition(exampleChallenge.getX(),exampleChallenge.getY()-exampleChallenge2.getHeight());
		exampleChallenge2.setSize(boxChallenges.getWidth(),exampleChallenge2.getHeight());
		
		exampleChallengeButton2 = new Sprite(new Texture("playButton.png"));
		exampleChallengeButton2.setSize(exampleChallengeButton2.getWidth()/1.5f, exampleChallengeButton2.getHeight()/1.5f);
		exampleChallengeButton2.setPosition(exampleChallenge2.getWidth()-exampleChallengeButton2.getWidth()*2+10 ,exampleChallenge2.getY()+ exampleChallenge2.getHeight()/2 - exampleChallengeButton2.getHeight()/2 );
		
		exampleDeleteButton2 = new Sprite(new Texture("deleteButton.png"));
		exampleDeleteButton2.setSize(exampleDeleteButton2.getWidth()/1.5f,exampleDeleteButton2.getHeight()/1.5f);
		exampleDeleteButton2.setPosition(exampleChallenge2.getWidth()-exampleDeleteButton2.getWidth()+10, exampleChallenge2.getY()+ exampleChallenge2.getHeight()/2 - exampleDeleteButton2.getHeight()/2);
		
		E2X=exampleChallenge2.getX();
		E2Y=exampleChallenge2.getY();
		E2XB=exampleChallengeButton2.getX();
		E2YB=exampleChallengeButton2.getY();
		
		E2XDB=exampleDeleteButton2.getX();
		E2YDB=exampleDeleteButton2.getY();
		
		exampleChallengeText2 = new BitmapFont();
		exampleChallengeText2.setColor(Color.WHITE);
		exampleChallengeText2.setScale(0.9f);
		}
	public void loadFile3(){
		exampleChallenge3 = new Sprite(new Texture("challengeFile.png"));
		exampleChallenge3.setPosition(exampleChallenge2.getX(),exampleChallenge2.getY()-exampleChallenge3.getHeight());
		exampleChallenge3.setSize(boxChallenges.getWidth(),exampleChallenge3.getHeight());
		
		exampleChallengeButton3 = new Sprite(new Texture("playButton.png"));
		exampleChallengeButton3.setSize(exampleChallengeButton3.getWidth()/1.5f, exampleChallengeButton3.getHeight()/1.5f);
		exampleChallengeButton3.setPosition(exampleChallenge3.getWidth()-exampleChallengeButton3.getWidth()*2+10 ,exampleChallenge3.getY()+ exampleChallenge3.getHeight()/2 - exampleChallengeButton3.getHeight()/2 );
		
		exampleDeleteButton3 = new Sprite(new Texture("deleteButton.png"));
		exampleDeleteButton3.setSize(exampleDeleteButton3.getWidth()/1.5f,exampleDeleteButton3.getHeight()/1.5f);
		exampleDeleteButton3.setPosition(exampleChallenge3.getWidth()-exampleDeleteButton3.getWidth()+10, exampleChallenge3.getY()+ exampleChallenge3.getHeight()/2 - exampleDeleteButton3.getHeight()/2);
		
		E3X=exampleChallenge3.getX();
		E3Y=exampleChallenge3.getY();
		E3XB=exampleChallengeButton3.getX();
		E3YB=exampleChallengeButton3.getY();
		
		E3XDB=exampleDeleteButton3.getX();
		E3YDB=exampleDeleteButton3.getY();
		
		exampleChallengeText3 = new BitmapFont();
		exampleChallengeText3.setColor(Color.WHITE);
		exampleChallengeText3.setScale(0.9f);
		}
}
