package es.wander.chesstime;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class GameScreenNewEdition implements Screen, InputProcessor{
    
    SpriteBatch batch;
	private Texture backgroundTexture;
    //private int W=Gdx.graphics.getWidth();
    private int H=Gdx.graphics.getHeight();
    private int sizeCell = Gdx.graphics.getWidth()/8;
    private int reduxPiece = 10; //Esta es la unica variable que se introduce a mano, habria que calcularla en proporcion, segun el tipo de dispositivo
    private int FIRSTPADDING = 5; //Esta tambien
    private int sizePiece = sizeCell-reduxPiece;
    private Sprite chessCell[][] = new Sprite[8][8];//matriz con los sprites blancos o negros (nunca se toca despues de inicializarlo)
    
    private Sprite tableHighlight;
    
    private Sprite whereGoChess[][] = new Sprite [8][8];//matriz con los sprites (puntos verdes) de donde puedo ir cuando toco una ficha por defecto a alpha=0.0
    
    private boolean whereGoChessBool[][] = new boolean [8][8];//matriz que nos indica si hay un punto verde activo (alpha =0.4) o si no lo hay (alpha=0.0)
    
    int numberPieceActive = -1; //pieza al inicio no hay ninguna pero durante el ejemplo siempre hay alguna activa, si no tocas una tocas otra (no se puede cancelar...por ahora)
    
    private int chessPosition[][] = new int[8][8]; // matriz de las posiciones de las fichas , cada ficha tiene un int ID que indica que ficha es
    
    //MATRIZ DE POSICIONES POR SPRITE???????????? //private Sprite
    
    Sprite pawnW1;	Sprite pawnW2;	Sprite pawnW3;	Sprite pawnW4;	Sprite pawnW5;	Sprite pawnW6;	Sprite pawnW7;	Sprite pawnW8;	Sprite rookW1;	Sprite rookW2;	Sprite queenW;	Sprite knightW1;	Sprite knightW2;	Sprite bishopW1;	Sprite bishopW2;	Sprite kingW;
    Sprite pawnB1;	Sprite pawnB2;	Sprite pawnB3;	Sprite pawnB4;	Sprite pawnB5;	Sprite pawnB6;	Sprite pawnB7;	Sprite pawnB8;	Sprite rookB1;	Sprite rookB2;	Sprite queenB;	Sprite knightB1;	Sprite knightB2;	Sprite bishopB1;	Sprite bishopB2;	Sprite kingB;
    
    private boolean turnPlayer=false; // Variable que indica en que turno estamos: FALSE = WHITE ; TRUE = BLACK
    
    Sprite sendButton;
    int originX=-1, destinyY=-1,originY=-1,destinyX=-1;
    
    String resultChallengeTurn="";
    String inProcess="";
    boolean notFileExists=false;
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // POR IMPLEMENTAR:
    
    // EL CAMINO DEJADO POR EL ANTERIOR MOVIMIENTO DEL RIVAL
    
    // CAPTURA A MOVIMIENTO PASADO
    
    // ENROQUE
    
    // JAQUES (EL JUGADOR NO PUEDE MOVER OTRA FICHAS QUE NO SEA PARA EVITAR EL MATE)
    
    // AL EMPEZAR, SI HACES CLICK EN BLANCAS Y LUEGO DOS CLICKS EN NEGRAS, PUEDES MOVER NEGRAS SIN HABER MOVIDO BLANCAS    
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public void show() {
    	backgroundTexture = new Texture("menuBackground.png");
        batch = new SpriteBatch();
        
      //ver cual es el turno de la partida
        try {
			 resultChallengeTurn = sendGet_getChallengeTurn(actualUser1(),actualUser2());
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        load();
    
		if (notFileExists) {
			// cambiar esto por la carga de una partida guardada, si no, dejarlo
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					chessPosition[i][j] = 0;
				}
			}
		}
		
        // tengo que actualizar la juagada 
		//if(resultChallengeTurn.equals(UserSession.User)){		}
		
        initializeBoard();
        spritesBlack();
        spritesWhite();
        
		if (notFileExists) {
			initializeBlack();
			initializeWhite();
		}else{ 
			drawChess();
		}
		
		
        tableHighlight = new Sprite(new Texture("highlight.png"));
        tableHighlight.setSize(sizeCell,sizeCell);
        tableHighlight.setPosition(getPositionX(0), getPositionY(0));
        tableHighlight.setAlpha(0);
        
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                whereGoChess[i][j] = new Sprite(new Texture("whereHighlight.png"));
                whereGoChess[i][j].setSize(sizeCell,sizeCell);
                whereGoChess[i][j].setPosition(getPositionX(i), getPositionY(j));
                whereGoChess[i][j].setAlpha(0);
                whereGoChessBool[i][j]=false;
            }
        }
        
        Gdx.input.setInputProcessor(this);
        
        sendButton = new Sprite(new Texture("sendButton.png"));
        sendButton.setPosition(Gdx.graphics.getWidth()/2-sendButton.getWidth()/2, (Gdx.graphics.getHeight()-sizeCell*8) - (Gdx.graphics.getHeight()-sizeCell*8)/2 -sendButton.getHeight()/2 );

    }
    
    public String actualUser1(){
    	if(UserSession.game1User1!=null || UserSession.game1User1!="")return UserSession.game1User1;
		//UserSession.game1User2="";
		if(UserSession.game2User1!=null || UserSession.game2User1!="")return UserSession.game2User1;
		//UserSession.game2User2="";
		if(UserSession.game3User1!=null || UserSession.game3User1!="")return UserSession.game3User1;
		//UserSession.game3User2="";
		return "";
    	
    }
    public String actualUser2(){
    	
    	//UserSession.game1User1="";
		if(UserSession.game1User2!=null || UserSession.game1User2!="")return UserSession.game1User2;
		//UserSession.game2User1="";
		if(UserSession.game2User2!=null || UserSession.game2User2!="")return UserSession.game2User2;
		//UserSession.game3User1="";
		if(UserSession.game3User2!=null || UserSession.game3User2!="")return UserSession.game3User2;
		
		return "";
    	
    }
    
    @Override
    public void render(float delta) {
        
        Gdx.gl.glClearColor(0.078f, 0.588f, 0.784f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        drawBoard();
        tableHighlight.draw(batch);
        drawBlack();
        drawWhite();
        
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                whereGoChess[i][j].draw(batch);
            }
        }
        sendButton.draw(batch);
        batch.end();
        
    }
    
    public void initializeBoard(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                
                if(i%2==0)
                {
                    if(j%2==0)
                    {
                        chessCell[i][j] = new Sprite(new Texture("whiteCell.png"));
                        chessCell[i][j].setSize(sizeCell,sizeCell);
                        chessCell[i][j].setPosition(getPositionX(i), getPositionY(j));
                    }
                    else
                    {
                        chessCell[i][j] = new Sprite(new Texture("blackCell.png"));
                        chessCell[i][j].setSize(sizeCell,sizeCell);
                        chessCell[i][j].setPosition(getPositionX(i), getPositionY(j));
                    }
                }
                else
                {
                    if(j%2==0)
                    {
                        chessCell[i][j] = new Sprite(new Texture("blackCell.png"));
                        chessCell[i][j].setSize(sizeCell,sizeCell);
                        chessCell[i][j].setPosition(getPositionX(i), getPositionY(j));
                    }
                    else
                    {
                        chessCell[i][j] = new Sprite(new Texture("whiteCell.png"));
                        chessCell[i][j].setSize(sizeCell,sizeCell);
                        chessCell[i][j].setPosition(getPositionX(i), getPositionY(j));
                    }
                }
                
            }
        }
    }
    
    public void drawBoard(){
        
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                chessCell[i][j].draw(batch);
            }
        }
    }
    
    //POSICIONES DE LAS CELDAS, SOLO UTILIZADO ESTE METODO PARA DIBUJAR EL TABLERO
    public int getPositionX(int x){
    	return sizeCell*x;
    }
    
    //POSICIONES DE LAS CELDAS, SOLO UTILIZADO ESTE METODO PARA DIBUJAR EL TABLERO
    public int getPositionY(int y){
        switch (y) {
            case 0:  return H-sizeCell;
            case 1:  return H-sizeCell*2;
            case 2:  return H-sizeCell*3;
            case 3:  return H-sizeCell*4;
            case 4:  return H-sizeCell*5;
            case 5:  return H-sizeCell*6;
            case 6:  return H-sizeCell*7;
            case 7:  return H-sizeCell*8;
            default: return -1;
        }
    }
    
    //POSICIONES DE LAS FICHAS SOLO UTILIZADAS AL INICIO
    public int getPositionXPiece(int x){
        switch (x) {
            case 0:  return FIRSTPADDING;
            case 1:  return sizeCell+(reduxPiece/2);
            case 2:  return sizeCell*2+(reduxPiece/2);
            case 3:  return sizeCell*3+(reduxPiece/2);
            case 4:  return sizeCell*4+(reduxPiece/2);
            case 5:  return sizeCell*5+(reduxPiece/2);
            case 6:  return sizeCell*6+(reduxPiece/2);
            case 7:  return sizeCell*7+(reduxPiece/2);
            default: return -1;
        }
    }
    
    //POSICIONES DE LAS FICHAS SOLO UTILIZADAS AL INICIO
    public int getPositionYPiece(int y){
        switch (y) {
            case 0:  return H-sizeCell+(reduxPiece/2);
            case 1:  return H-sizeCell*2+(reduxPiece/2);
            case 2:  return H-sizeCell*3+(reduxPiece/2);
            case 3:  return H-sizeCell*4+(reduxPiece/2);
            case 4:  return H-sizeCell*5+(reduxPiece/2);
            case 5:  return H-sizeCell*6+(reduxPiece/2);
            case 6:  return H-sizeCell*7+(reduxPiece/2);
            case 7:  return H-sizeCell*8+(reduxPiece/2);
            default: return -1;
        }
    }
    
    public void drawBlack(){
        pawnB1.draw(batch);
        pawnB2.draw(batch);
        pawnB3.draw(batch);
        pawnB4.draw(batch);
        pawnB5.draw(batch);
        pawnB6.draw(batch);
        pawnB7.draw(batch);
        pawnB8.draw(batch);
        rookB1.draw(batch);
        rookB2.draw(batch);
        queenB.draw(batch);
        knightB1.draw(batch);
        knightB2.draw(batch);
        bishopB1.draw(batch);
        bishopB2.draw(batch);
        kingB.draw(batch);
    }
    
    public void drawWhite(){
        pawnW1.draw(batch);
        pawnW2.draw(batch);
        pawnW3.draw(batch);
        pawnW4.draw(batch);
        pawnW5.draw(batch);
        pawnW6.draw(batch);
        pawnW7.draw(batch);
        pawnW8.draw(batch);
        rookW1.draw(batch);
        rookW2.draw(batch);
        knightW1.draw(batch);
        knightW2.draw(batch);
        queenW.draw(batch);
        bishopW1.draw(batch);
        bishopW2.draw(batch);
        kingW.draw(batch);
    }
    
    public void drawChess(){
    	
    	 pawnW1.setPosition(getPositionXPiece(getPositionXByNumberPiece(1)), getPositionYPiece(getPositionYByNumberPiece(1)));
         pawnW2.setPosition(getPositionXPiece(getPositionXByNumberPiece(2)), getPositionYPiece(getPositionYByNumberPiece(2)));
         pawnW3.setPosition(getPositionXPiece(getPositionXByNumberPiece(3)), getPositionYPiece(getPositionYByNumberPiece(3)));
         pawnW4.setPosition(getPositionXPiece(getPositionXByNumberPiece(4)), getPositionYPiece(getPositionYByNumberPiece(4)));
         pawnW5.setPosition(getPositionXPiece(getPositionXByNumberPiece(5)), getPositionYPiece(getPositionYByNumberPiece(5)));
         pawnW6.setPosition(getPositionXPiece(getPositionXByNumberPiece(6)), getPositionYPiece(getPositionYByNumberPiece(6)));
         pawnW7.setPosition(getPositionXPiece(getPositionXByNumberPiece(7)), getPositionYPiece(getPositionYByNumberPiece(7)));
         pawnW8.setPosition(getPositionXPiece(getPositionXByNumberPiece(8)), getPositionYPiece(getPositionYByNumberPiece(8)));
         rookW1.setPosition(getPositionXPiece(getPositionXByNumberPiece(9)), getPositionYPiece(getPositionYByNumberPiece(9)));
         rookW2.setPosition(getPositionXPiece(getPositionXByNumberPiece(10)), getPositionYPiece(getPositionYByNumberPiece(10)));
         knightW1.setPosition(getPositionXPiece(getPositionXByNumberPiece(11)), getPositionYPiece(getPositionYByNumberPiece(11)));
         knightW2.setPosition(getPositionXPiece(getPositionXByNumberPiece(12)), getPositionYPiece(getPositionYByNumberPiece(12)));
         bishopW1.setPosition(getPositionXPiece(getPositionXByNumberPiece(13)), getPositionYPiece(getPositionYByNumberPiece(13)));
         bishopW2.setPosition(getPositionXPiece(getPositionXByNumberPiece(14)), getPositionYPiece(getPositionYByNumberPiece(14)));
         queenW.setPosition(getPositionXPiece(getPositionXByNumberPiece(15)), getPositionYPiece(getPositionYByNumberPiece(15)));
         kingW.setPosition(getPositionXPiece(getPositionXByNumberPiece(16)), getPositionYPiece(getPositionYByNumberPiece(16)));
         
         
         pawnB1.setPosition(getPositionXPiece(getPositionXByNumberPiece(17)), getPositionYPiece(getPositionYByNumberPiece(17)));
         pawnB2.setPosition(getPositionXPiece(getPositionXByNumberPiece(18)), getPositionYPiece(getPositionYByNumberPiece(18)));
         pawnB3.setPosition(getPositionXPiece(getPositionXByNumberPiece(19)), getPositionYPiece(getPositionYByNumberPiece(19)));
         pawnB4.setPosition(getPositionXPiece(getPositionXByNumberPiece(20)), getPositionYPiece(getPositionYByNumberPiece(20)));
         pawnB5.setPosition(getPositionXPiece(getPositionXByNumberPiece(21)), getPositionYPiece(getPositionYByNumberPiece(21)));
         pawnB6.setPosition(getPositionXPiece(getPositionXByNumberPiece(22)), getPositionYPiece(getPositionYByNumberPiece(22)));
         pawnB7.setPosition(getPositionXPiece(getPositionXByNumberPiece(23)), getPositionYPiece(getPositionYByNumberPiece(23)));
         pawnB8.setPosition(getPositionXPiece(getPositionXByNumberPiece(24)), getPositionYPiece(getPositionYByNumberPiece(24)));
         rookB1.setPosition(getPositionXPiece(getPositionXByNumberPiece(25)), getPositionYPiece(getPositionYByNumberPiece(25)));
         rookB2.setPosition(getPositionXPiece(getPositionXByNumberPiece(26)), getPositionYPiece(getPositionYByNumberPiece(26)));
         knightB1.setPosition(getPositionXPiece(getPositionXByNumberPiece(27)), getPositionYPiece(getPositionYByNumberPiece(27)));
         knightB2.setPosition(getPositionXPiece(getPositionXByNumberPiece(28)), getPositionYPiece(getPositionYByNumberPiece(28)));
         bishopB1.setPosition(getPositionXPiece(getPositionXByNumberPiece(29)), getPositionYPiece(getPositionYByNumberPiece(29)));
         bishopB2.setPosition(getPositionXPiece(getPositionXByNumberPiece(30)), getPositionYPiece(getPositionYByNumberPiece(30)));
         queenB.setPosition(getPositionXPiece(getPositionXByNumberPiece(31)), getPositionYPiece(getPositionYByNumberPiece(31)));
         kingB.setPosition(getPositionXPiece(getPositionXByNumberPiece(32)), getPositionYPiece(getPositionYByNumberPiece(32)));
     
    }
    
    public void spritesBlack(){
    	pawnB1 = new Sprite(new Texture("pawnB.png"));
        pawnB1.setSize(sizePiece,sizePiece);
        pawnB2 = new Sprite(new Texture("pawnB.png"));
        pawnB2.setSize(sizePiece,sizePiece);
        pawnB3 = new Sprite(new Texture("pawnB.png"));
        pawnB3.setSize(sizePiece,sizePiece);
        pawnB4 = new Sprite(new Texture("pawnB.png"));
        pawnB4.setSize(sizePiece,sizePiece);
        pawnB5 = new Sprite(new Texture("pawnB.png"));
        pawnB5.setSize(sizePiece,sizePiece);
        pawnB6 = new Sprite(new Texture("pawnB.png"));
        pawnB6.setSize(sizePiece,sizePiece);
        pawnB7 = new Sprite(new Texture("pawnB.png"));
        pawnB7.setSize(sizePiece,sizePiece);
        pawnB8 = new Sprite(new Texture("pawnB.png"));
        pawnB8.setSize(sizePiece,sizePiece);
        rookB1 = new Sprite(new Texture("rookB.png"));
        rookB1.setSize(sizePiece,sizePiece);
        rookB2 = new Sprite(new Texture("rookB.png"));
        rookB2.setSize(sizePiece,sizePiece);
        knightB1 = new Sprite(new Texture("knightB.png"));
        knightB1.setSize(sizePiece,sizePiece);
        knightB2 = new Sprite(new Texture("knightB.png"));
        knightB2.setSize(sizePiece,sizePiece);
        bishopB1 = new Sprite(new Texture("bishopB.png"));
        bishopB1.setSize(sizePiece,sizePiece);
        bishopB2 = new Sprite(new Texture("bishopB.png"));
        bishopB2.setSize(sizePiece,sizePiece);
        queenB = new Sprite(new Texture("queenB.png"));
        queenB.setSize(sizePiece,sizePiece);
        kingB = new Sprite(new Texture("kingB.png"));
        kingB.setSize(sizePiece,sizePiece);
    }
    public void spritesWhite(){
    	pawnW1 = new Sprite(new Texture("pawnW.png"));
        pawnW1.setSize(sizePiece,sizePiece);
 
        
        pawnW2 = new Sprite(new Texture("pawnW.png"));
        pawnW2.setSize(sizePiece,sizePiece);

        
        pawnW3 = new Sprite(new Texture("pawnW.png"));
        pawnW3.setSize(sizePiece,sizePiece);

        
        pawnW4 = new Sprite(new Texture("pawnW.png"));
        pawnW4.setSize(sizePiece,sizePiece);

        
        pawnW5 = new Sprite(new Texture("pawnW.png"));
        pawnW5.setSize(sizePiece,sizePiece);

        pawnW6 = new Sprite(new Texture("pawnW.png"));
        pawnW6.setSize(sizePiece,sizePiece);

        
        pawnW7 = new Sprite(new Texture("pawnW.png"));
        pawnW7.setSize(sizePiece,sizePiece);

        
        pawnW8 = new Sprite(new Texture("pawnW.png"));
        pawnW8.setSize(sizePiece,sizePiece);
 
        
        rookW1 = new Sprite(new Texture("rookW.png"));
        rookW1.setSize(sizePiece,sizePiece);

        rookW2 = new Sprite(new Texture("rookW.png"));
        rookW2.setSize(sizePiece,sizePiece);

        
        knightW1 = new Sprite(new Texture("knightW.png"));
        knightW1.setSize(sizePiece,sizePiece);

        
        knightW2 = new Sprite(new Texture("knightW.png"));
        knightW2.setSize(sizePiece,sizePiece);

        
        bishopW1 = new Sprite(new Texture("bishopW.png"));
        bishopW1.setSize(sizePiece,sizePiece);
  
        
        bishopW2 = new Sprite(new Texture("bishopW.png"));
        bishopW2.setSize(sizePiece,sizePiece);

        
        queenW = new Sprite(new Texture("queenW.png"));
        queenW.setSize(sizePiece,sizePiece);
  
        
        kingW = new Sprite(new Texture("kingW.png"));
        kingW.setSize(sizePiece,sizePiece);

    }
    public void initializeBlack(){
        
       
        pawnB1.setPosition(getPositionXPiece(0), getPositionYPiece(1));
        chessPosition[0][1]=17;
        
      
        pawnB2.setPosition(getPositionXPiece(1), getPositionYPiece(1));
        chessPosition[1][1]=18;
        

        pawnB3.setPosition(getPositionXPiece(2), getPositionYPiece(1));
        chessPosition[2][1]=19;

        pawnB4.setPosition(getPositionXPiece(3), getPositionYPiece(1));
        chessPosition[3][1]=20;

        pawnB5.setPosition(getPositionXPiece(4), getPositionYPiece(1));
        chessPosition[4][1]=21;
        

        pawnB6.setPosition(getPositionXPiece(5), getPositionYPiece(1));
        chessPosition[5][1]=22;
 
        pawnB7.setPosition(getPositionXPiece(6), getPositionYPiece(1));
        chessPosition[6][1]=23;
        
  
        pawnB8.setPosition(getPositionXPiece(7), getPositionYPiece(1));
        chessPosition[7][1]=24;
    
        rookB1.setPosition(getPositionXPiece(0), getPositionYPiece(0));
        chessPosition[0][0]=25;

        rookB2.setPosition(getPositionXPiece(7), getPositionYPiece(0));
        chessPosition[7][0]=26;

        knightB1.setPosition(getPositionXPiece(1), getPositionYPiece(0));
        chessPosition[1][0]=27;
    
        knightB2.setPosition(getPositionXPiece(6), getPositionYPiece(0));
        chessPosition[6][0]=28;

        bishopB1.setPosition(getPositionXPiece(2), getPositionYPiece(0));
        chessPosition[2][0]=29;

        bishopB2.setPosition(getPositionXPiece(5), getPositionYPiece(0));
        chessPosition[5][0]=30;

        queenB.setPosition(getPositionXPiece(4), getPositionYPiece(0));
        chessPosition[4][0]=31;
        

        kingB.setPosition(getPositionXPiece(3), getPositionYPiece(0));
        chessPosition[3][0]=32;
        
        
    }
    public void initializeWhite(){

        pawnW1.setPosition(getPositionXPiece(0), getPositionYPiece(6));
        chessPosition[0][6]=1;
        
       
        pawnW2.setPosition(getPositionXPiece(1), getPositionYPiece(6));
        chessPosition[1][6]=2;
        
       
        pawnW3.setPosition(getPositionXPiece(2), getPositionYPiece(6));
        chessPosition[2][6]=3;
  
        pawnW4.setPosition(getPositionXPiece(3), getPositionYPiece(6));
        chessPosition[3][6]=4;

        pawnW5.setPosition(getPositionXPiece(4), getPositionYPiece(6));
        chessPosition[4][6]=5;
        
        pawnW6.setPosition(getPositionXPiece(5), getPositionYPiece(6));
        chessPosition[5][6]=6;

        pawnW7.setPosition(getPositionXPiece(6), getPositionYPiece(6));
        chessPosition[6][6]=7;

        pawnW8.setPosition(getPositionXPiece(7), getPositionYPiece(6));
        chessPosition[7][6]=8;
        
        rookW1.setPosition(getPositionXPiece(0), getPositionYPiece(7));
        chessPosition[0][7]=9;
        
        rookW2.setPosition(getPositionXPiece(7), getPositionYPiece(7));
        chessPosition[7][7]=10;

        knightW1.setPosition(getPositionXPiece(1), getPositionYPiece(7));
        chessPosition[1][7]=11;
    
        knightW2.setPosition(getPositionXPiece(6), getPositionYPiece(7));
        chessPosition[6][7]=12;
        
        bishopW1.setPosition(getPositionXPiece(2), getPositionYPiece(7));
        chessPosition[2][7]=13;
        
        bishopW2.setPosition(getPositionXPiece(5), getPositionYPiece(7));
        chessPosition[5][7]=14;
       
        queenW.setPosition(getPositionXPiece(4), getPositionYPiece(7));
        chessPosition[4][7]=15;
        
        kingW.setPosition(getPositionXPiece(3), getPositionYPiece(7));
        chessPosition[3][7]=16;
        
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
    
    public int getPositionXByNumberPiece(int p){
        
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                if(chessPosition[i][j]==p)return i;
            }
            
        }
        return -1;
    }
    
    public int getPositionYByNumberPiece(int p){
        
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                if(chessPosition[i][j]==p)return j;
            }
            
        }
        return -1;
    }
    
    /*
    * SI QUEREMOS CANCELAR EL MOVIMIENTO SE PUEDE VOVLER A PULSAR SOBRE LA PIEZA
    * EN ESE CASO HAY QUE PONER LA PIEZA ACTIVA == 0
    */
    public void setHighlightToSprite(Sprite sprite,int numberPiece){
        
        tableHighlight.setPosition(getPositionX(getPositionXByNumberPiece(numberPiece)),
        getPositionY(getPositionYByNumberPiece(numberPiece)));
        tableHighlight.setAlpha(0.3f);
        
    }
    
    public boolean isActivewhereGoChessBool(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(whereGoChessBool[i][j]==true)return true;
            }
        }
        return false;
    }
    
    @Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		// Cuando toco la pantalla se dan varios casos:
		// 1
		// que haya tocado una pieza
		// 2
		// que haya tocad una posicion a la que mover una pieza

		if (resultChallengeTurn.equals(UserSession.User)) {
			if (!whoPieceTouched(screenX, screenY)
					&& isActivewhereGoChessBool())
				whoMoveTouched(screenX, screenY);

			if (sendButton.getBoundingRectangle().contains(screenX,
					Gdx.graphics.getHeight() - screenY)) {
				sendButton.setScale(1.1f);
			}

		}
		return false;

	}
    public void pritnTableroi(){
    	 for(int i=0;i<8;i++){
             for(int j=0;j<8;j++){
                 System.out.print(chessPosition[j][i]);
             }
             System.out.println();
         }
    }
    //REVISAR ESTE METODO; SALTA ERROR DE VEZ EN CUANDO (SOLO EN VERSION MOBIL)
    public void whoMoveTouched(int x, int y){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(whereGoChess[i][j].getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y) && whereGoChessBool[i][j]==true ){
                	//MUEVE FICHA a x E y
                	
                	originX=getPositionXByNumberPiece(numberPieceActive);
                    originY=getPositionYByNumberPiece(numberPieceActive);
                    chessPosition[getPositionXByNumberPiece(numberPieceActive)][getPositionYByNumberPiece(numberPieceActive)]=0;
                    
                    if(chessPosition[i][j]!=0){
                    	//
                    	// ARREGLAR ESTO PARA MEJOR .... DIOX // es cuando alguien coje la pieza de su oponente
                    	//
                    	getSpriteById(chessPosition[i][j]).setPosition(-50, -50);
                    }
                    
                    
                    destinyX=i;
                    destinyY=j;
                    getSpriteById(numberPieceActive).setPosition(getPositionXPiece(i), getPositionYPiece(j));
                    chessPosition[i][j]=numberPieceActive;
                    resetWhereGoHighlightAndBool();
                    tableHighlight.setAlpha(0);
                    //pritnTableroi();
                }
            }
        }
        numberPieceActive=-1;
        
        if(turnPlayer == false) turnPlayer=true;
        else turnPlayer=false;
        }
    
    public Sprite getSpriteById(int id){
        switch (id) {
            case 1:  return pawnW1;
            case 2:  return pawnW2;
            case 3:  return pawnW3;
            case 4:  return pawnW4;
            case 5:  return pawnW5;
            case 6:  return pawnW6;
            case 7:  return pawnW7;
            case 8:  return pawnW8;
            
            case 9:  return rookW1;
            case 10:  return rookW2;
            case 11:  return knightW1;
            case 12:  return knightW2;
            case 13:  return bishopW1;
            case 14:  return bishopW2;
            case 15:  return queenW;
            case 16:  return kingW;
            
            case 17:  return pawnB1;
            case 18:  return pawnB2;
            case 19:  return pawnB3;
            case 20:  return pawnB4;
            case 21:  return pawnB5;
            case 22:  return pawnB6;
            case 23:  return pawnB7;
            case 24:  return pawnB8;
            
            case 25:  return rookB1;
            case 26:  return rookB2;
            case 27:  return knightB1;
            case 28:  return knightB2;
            case 29:  return bishopB1;
            case 30:  return bishopB2;
            case 31:  return queenB;
            case 32:  return kingB;
            
            default: return pawnW1;
        }
    }
    
    public void resetWhereGoHighlightAndBool(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                whereGoChess[i][j].setAlpha(0);
                whereGoChessBool[i][j]=false;
            }
        }
    }
    
    public void resetPieceHighlight(){
        tableHighlight.setAlpha(0);
    }
    
    public void setHighlightwhereCanGo(int numberPiece){
        
        if(numberPiece>=1 && numberPiece<=8){//PAWN WHITE
            resetWhereGoHighlightAndBool();
            int y=getPositionYByNumberPiece(numberPiece);
            int x=getPositionXByNumberPiece(numberPiece);
            
            if((y-1)>=0 && chessPosition[x][y-1]==0){
                whereGoChess[x][y-1].setAlpha(0.4f);
                whereGoChessBool[x][y-1]=true;
                if((y-1)>=0 && y==6 && chessPosition[x][y-2]==0){whereGoChess[x][y-2].setAlpha(0.4f);whereGoChessBool[x][y-2]=true;}
                
                //Movimientos de capturas a las fichas negras
                if((x-1)>=0){
                    if(chessPosition[x-1][y-1]>=17){
                        whereGoChess[x-1][y-1].setAlpha(0.4f);
                        whereGoChessBool[x-1][y-1]=true;
                    }
                }
                if((x+1)<8){
                    if(chessPosition[x+1][y-1]>=17){
                        whereGoChess[x+1][y-1].setAlpha(0.4f);
                        whereGoChessBool[x+1][y-1]=true;
                    }
                }
                
            }
            
        }
        if(numberPiece>=17 && numberPiece <=24){//PAWN BLACK
            resetWhereGoHighlightAndBool();
            int y=getPositionYByNumberPiece(numberPiece);
            int x=getPositionXByNumberPiece(numberPiece);
            
            if((y+1)<8 && chessPosition[x][y+1]==0){
                whereGoChess[x][y+1].setAlpha(0.4f);whereGoChessBool[x][y+1]=true;
                if((y+1)<8 && y==1 && chessPosition[x][y+2]==0){whereGoChess[x][y+2].setAlpha(0.4f);whereGoChessBool[x][y+2]=true;}
            }
            
            
            //Movimientos de capturas a las fichas blancas
            
            if((x-1)>=0){
                if(chessPosition[x-1][y+1]<=16 && chessPosition[x-1][y+1]>0){
                    whereGoChess[x-1][y+1].setAlpha(0.4f);
                    whereGoChessBool[x-1][y+1]=true;
                }
            }
            
            if((x+1)<8){
                if(chessPosition[x+1][y+1]<=16 && chessPosition[x+1][y+1]>0){
                    whereGoChess[x+1][y+1].setAlpha(0.4f);
                    whereGoChessBool[x+1][y+1]=true;
                }
            }
        }
        
        if(numberPiece==9 || numberPiece==10){//ROOK WHITE
            resetWhereGoHighlightAndBool();
            int y=getPositionYByNumberPiece(numberPiece);
            int x=getPositionXByNumberPiece(numberPiece);
            for(int i=x+1;i<8;i++){
                
                if(chessPosition[i][y]!=0){
                    if(chessPosition[i][y]>=17){
                        whereGoChess[i][y].setAlpha(0.4f);
                        whereGoChessBool[i][y]=true;
                        break;
                    }
                    break;
                }
                else{
                    whereGoChess[i][y].setAlpha(0.4f);
                    whereGoChessBool[i][y]=true;
                }
                
            }
            
            for(int i=x-1;i>=0;i--){
                if (chessPosition[i][y]!=0){
                    if(chessPosition[i][y]>=17){
                        whereGoChess[i][y].setAlpha(0.4f);
                        whereGoChessBool[i][y]=true;
                        break;
                    }
                    break;
                }
                else{
                    whereGoChess[i][y].setAlpha(0.4f);
                    whereGoChessBool[i][y]=true;
                }
            }
            for(int i=y-1;i>=0;i--){
                if (chessPosition[x][i]!=0){
                    if(chessPosition[x][i]>=17){
                        whereGoChess[x][i].setAlpha(0.4f);
                        whereGoChessBool[x][i]=true;
                        break;
                    }
                    break;
                }
                else{
                    whereGoChess[x][i].setAlpha(0.4f);
                    whereGoChessBool[x][i]=true;
                }
            }
            for(int i=y+1;i<8;i++){
                if (chessPosition[x][i]!=0){
                    if(chessPosition[x][i]>=17){
                        whereGoChess[x][i].setAlpha(0.4f);
                        whereGoChessBool[x][i]=true;
                        break;
                    }
                    break;
                }
                else{
                    whereGoChess[x][i].setAlpha(0.4f);
                    whereGoChessBool[x][i]=true;
                }
            }
        }
        if(numberPiece==25 || numberPiece==26){//ROOK BLACK
            resetWhereGoHighlightAndBool();
            int y=getPositionYByNumberPiece(numberPiece);
            int x=getPositionXByNumberPiece(numberPiece);
            int i=0;
            for( i=x+1;i<8;i++){
                if (chessPosition[i][y]!=0){
                    if(chessPosition[i][y]<=16 && chessPosition[i][y]>0){
                        whereGoChess[i][y].setAlpha(0.4f);
                        whereGoChessBool[i][y]=true;
                        break;
                    }
                    break;
                }
                else{
                    whereGoChess[i][y].setAlpha(0.4f);
                    whereGoChessBool[i][y]=true;
                }
            }
            for( i=x-1;i>=0;i--){
                if (chessPosition[i][y]!=0){
                    if(chessPosition[i][y]<=16 && chessPosition[i][y]>0){
                        whereGoChess[i][y].setAlpha(0.4f);
                        whereGoChessBool[i][y]=true;
                        break;
                    }
                    break;
                }
                else{
                    whereGoChess[i][y].setAlpha(0.4f);
                    whereGoChessBool[i][y]=true;
                }
            }
            for( i=y-1;i>=0;i--){
                if (chessPosition[x][i]!=0){
                    if(chessPosition[x][i]<=16 && chessPosition[i][y]>0){
                        whereGoChess[x][i].setAlpha(0.4f);
                        whereGoChessBool[x][i]=true;
                        break;
                    }
                    break;
                }
                else{
                    whereGoChess[x][i].setAlpha(0.4f);
                    whereGoChessBool[x][i]=true;
                }
            }
            for( i=y+1;i<8;i++){
                if (chessPosition[x][i]!=0){
                    if(chessPosition[x][i]<=16 && chessPosition[i][y]>0){
                        whereGoChess[x][i].setAlpha(0.4f);
                        whereGoChessBool[x][i]=true;
                        break;
                    }
                    break;
                }
                else{
                    whereGoChess[x][i].setAlpha(0.4f);
                    whereGoChessBool[x][i]=true;
                }
            }
        }
        if(numberPiece==11 || numberPiece==12){//KNIGHT WHITE
            resetWhereGoHighlightAndBool();
            int y=getPositionYByNumberPiece(numberPiece);
            int x=getPositionXByNumberPiece(numberPiece);
            
            if((x+2)<8 && (y-1)>=0 && (chessPosition[x+2][y-1]==0 || chessPosition[x+2][y-1]>=17)){whereGoChess[x+2][y-1].setAlpha(0.4f);whereGoChessBool[x+2][y-1]=true;}
            if((y-2)>=0 && (x+1)<8 && (chessPosition[x+1][y-2]==0 || chessPosition[x+1][y-2]>=17)){whereGoChess[x+1][y-2].setAlpha(0.4f);whereGoChessBool[x+1][y-2]=true;}
            if((x-1)>=0 && (y-2)>=0 && (chessPosition[x-1][y-2]==0 || chessPosition[x-1][y-2]>=17)){whereGoChess[x-1][y-2].setAlpha(0.4f);whereGoChessBool[x-1][y-2]=true;}
            if((y-1)>=0 && (x-2)>=0 && (chessPosition[x-2][y-1]==0 || chessPosition[x-2][y-1]>=17)){whereGoChess[x-2][y-1].setAlpha(0.4f);whereGoChessBool[x-2][y-1]=true;}
            
            if((x-2)>=0 && (y+1)<8 && (chessPosition[x-2][y+1]==0 || chessPosition[x-2][y+1]>=17)){whereGoChess[x-2][y+1].setAlpha(0.4f);whereGoChessBool[x-2][y+1]=true;}
            if((y+2)<8 && (x-1)>=0 && (chessPosition[x-1][y+2]==0 || chessPosition[x-1][y+2]>=17)){whereGoChess[x-1][y+2].setAlpha(0.4f);whereGoChessBool[x-1][y+2]=true;}
            if((x+1)<8 && (y+2)<8 && (chessPosition[x+1][y+2]==0 || chessPosition[x+1][y+2]>=17)){whereGoChess[x+1][y+2].setAlpha(0.4f);whereGoChessBool[x+1][y+2]=true;}
            if((y+1)<8 && (x+2)<8 && (chessPosition[x+2][y+1]==0 || chessPosition[x+2][y+1]>=17)){whereGoChess[x+2][y+1].setAlpha(0.4f);whereGoChessBool[x+2][y+1]=true;}
        }
        if(numberPiece==27 || numberPiece==28){//KNIGHT BLACK
            resetWhereGoHighlightAndBool();
            int y=getPositionYByNumberPiece(numberPiece);
            int x=getPositionXByNumberPiece(numberPiece);
            if((x+2)<8 && (y-1)>=0 && (chessPosition[x+2][y-1]==0 || (chessPosition[x+2][y-1]<=16 && chessPosition[x+2][y-1]>0))){whereGoChess[x+2][y-1].setAlpha(0.4f);whereGoChessBool[x+2][y-1]=true;}
            if((y-2)>=0 && (x+1)<8 && (chessPosition[x+1][y-2]==0 || (chessPosition[x+1][y-2]<=16 && chessPosition[x+1][y-2]>0))){whereGoChess[x+1][y-2].setAlpha(0.4f);whereGoChessBool[x+1][y-2]=true;}
            if((x-1)>=0 && (y-2)>=0 && (chessPosition[x-1][y-2]==0 || (chessPosition[x-1][y-2]<=16 && chessPosition[x-1][y-2]>0))){whereGoChess[x-1][y-2].setAlpha(0.4f);whereGoChessBool[x-1][y-2]=true;}
            if((y-1)>=0 && (x-2)>=0 && (chessPosition[x-2][y-1]==0 || (chessPosition[x-2][y-1]<=16 && chessPosition[x-2][y-1]>0))){whereGoChess[x-2][y-1].setAlpha(0.4f);whereGoChessBool[x-2][y-1]=true;}
            
            if((x-2)>=0 && (y+1)<8 && (chessPosition[x-2][y+1]==0 || (chessPosition[x-2][y+1]<=16 && chessPosition[x-2][y+1]>0))){whereGoChess[x-2][y+1].setAlpha(0.4f);whereGoChessBool[x-2][y+1]=true;}
            if((y+2)<8 && (x-1)>=0 && (chessPosition[x-1][y+2]==0 || (chessPosition[x-1][y+2]<=16 && chessPosition[x-1][y+2]>0))){whereGoChess[x-1][y+2].setAlpha(0.4f);whereGoChessBool[x-1][y+2]=true;}
            if((x+1)<8 && (y+2)<8 && (chessPosition[x+1][y+2]==0 || (chessPosition[x+1][y+2]<=16 && chessPosition[x+1][y+2]>0))){whereGoChess[x+1][y+2].setAlpha(0.4f);whereGoChessBool[x+1][y+2]=true;}
            if((y+1)<8 && (x+2)<8 && (chessPosition[x+2][y+1]==0 || (chessPosition[x+2][y+1]<=16 && chessPosition[x+2][y+1]>0))){whereGoChess[x+2][y+1].setAlpha(0.4f);whereGoChessBool[x+2][y+1]=true;}
        }
        if(numberPiece==13 || numberPiece==14){//BISHOP WHITE
            resetWhereGoHighlightAndBool();
            int y=getPositionYByNumberPiece(numberPiece);
            int x=getPositionXByNumberPiece(numberPiece);
            int i=0, j=0;
            for( i=x+1, j=y-1; i<8 && j>=0; i++,j--){
                if (chessPosition[i][j]!=0){ 
                	if(chessPosition[i][j]>=17){
                        whereGoChess[i][j].setAlpha(0.4f);
                        whereGoChessBool[i][j]=true;
                        break;
                    }                	
                	break;
                }
                else{
                whereGoChess[i][j].setAlpha(0.4f);
                whereGoChessBool[i][j]=true;
                }
            }
            for( i=x-1, j=y-1; i>=0 && j>=0; i--,j--){
                if (chessPosition[i][j]!=0){
                	if(chessPosition[i][j]>=17){
                        whereGoChess[i][j].setAlpha(0.4f);
                        whereGoChessBool[i][j]=true;
                        break;
                    }   
                	break;
                }
                else{
                whereGoChess[i][j].setAlpha(0.4f);
                whereGoChessBool[i][j]=true;
                }
            }
            for( i=x+1, j=y+1; i<8 && j<8; i++,j++){
                if (chessPosition[i][j]!=0){
                	if(chessPosition[i][j]>=17){
                        whereGoChess[i][j].setAlpha(0.4f);
                        whereGoChessBool[i][j]=true;
                        break;
                    }   
                	break;
                }
                else{
                whereGoChess[i][j].setAlpha(0.4f);
                whereGoChessBool[i][j]=true;
                }
            }
            for( i=x-1, j=y+1; i>=0 && j<8; i--,j++){
                if (chessPosition[i][j]!=0){
                	if(chessPosition[i][j]>=17){
                        whereGoChess[i][j].setAlpha(0.4f);
                        whereGoChessBool[i][j]=true;
                        break;
                    }   
                	break;
                }
                else{
                whereGoChess[i][j].setAlpha(0.4f);
                whereGoChessBool[i][j]=true;
                }
            }
            
        }
        if(numberPiece==29 || numberPiece==30){//BISHOP BLACK
            resetWhereGoHighlightAndBool();
            int y=getPositionYByNumberPiece(numberPiece);
            int x=getPositionXByNumberPiece(numberPiece);
            int i=0, j=0;
            for( i=x+1, j=y-1; i<8 && j>=0; i++,j--){
                if (chessPosition[i][j]!=0){
                	if(chessPosition[i][j]<=16 && chessPosition[i][j]>0){
                        whereGoChess[i][j].setAlpha(0.4f);
                        whereGoChessBool[i][j]=true;
                        break;
                    }
                	break;
                }
                else{
                whereGoChess[i][j].setAlpha(0.4f);
                whereGoChessBool[i][j]=true;
                }
            }
            for( i=x-1, j=y-1; i>=0 && j>=0; i--,j--){
                if (chessPosition[i][j]!=0){
                	if(chessPosition[i][j]<=16 && chessPosition[i][j]>0){
                        whereGoChess[i][j].setAlpha(0.4f);
                        whereGoChessBool[i][j]=true;
                        break;
                    }
                	break;
                }
                else{
                whereGoChess[i][j].setAlpha(0.4f);
                whereGoChessBool[i][j]=true;
                }
            }
            for( i=x+1, j=y+1; i<8 && j<8; i++,j++){
                if (chessPosition[i][j]!=0){
                	if(chessPosition[i][j]<=16 && chessPosition[i][j]>0){
                        whereGoChess[i][j].setAlpha(0.4f);
                        whereGoChessBool[i][j]=true;
                        break;
                    }
                	break;
                }
                else{
                whereGoChess[i][j].setAlpha(0.4f);
                whereGoChessBool[i][j]=true;
                }
            }
            for( i=x-1, j=y+1; i>=0 && j<8; i--,j++){
                if (chessPosition[i][j]!=0){
                	if(chessPosition[i][j]<=16 && chessPosition[i][j]>0){
                        whereGoChess[i][j].setAlpha(0.4f);
                        whereGoChessBool[i][j]=true;
                        break;
                    }
                	break;
                }
                else{
                whereGoChess[i][j].setAlpha(0.4f);
                whereGoChessBool[i][j]=true;
                }
            }
        }
        if(numberPiece==15){//QUEEN WHITE
            resetWhereGoHighlightAndBool();
            int y=getPositionYByNumberPiece(numberPiece);
            int x=getPositionXByNumberPiece(numberPiece);
            int i=0, j=0;
            for( i=x+1;i<8;i++){
                if (chessPosition[i][y]!=0){
                	 if(chessPosition[i][y]>=17){
                         whereGoChess[i][y].setAlpha(0.4f);
                         whereGoChessBool[i][y]=true;
                         break;
                     }
                	break;
                }
                else{
                whereGoChess[i][y].setAlpha(0.4f);
                whereGoChessBool[i][y]=true;
                }
            }
            for( i=x-1;i>=0;i--){
                if (chessPosition[i][y]!=0){
                	 if(chessPosition[i][y]>=17){
                         whereGoChess[i][y].setAlpha(0.4f);
                         whereGoChessBool[i][y]=true;
                         break;
                     }
                	break;
                }
                else{
                whereGoChess[i][y].setAlpha(0.4f);
                whereGoChessBool[i][y]=true;
                }
            }
            for( i=y-1;i>=0;i--){
                if (chessPosition[x][i]!=0){
                	 if(chessPosition[x][i]>=17){
                         whereGoChess[x][i].setAlpha(0.4f);
                         whereGoChessBool[x][i]=true;
                         break;
                     }
                	break;
                }
                else{
                whereGoChess[x][i].setAlpha(0.4f);
                whereGoChessBool[x][i]=true;
                }
            }
            for( i=y+1;i<8;i++){
                if (chessPosition[x][i]!=0){
                	 if(chessPosition[x][i]>=17){
                         whereGoChess[x][i].setAlpha(0.4f);
                         whereGoChessBool[x][i]=true;
                         break;
                     }
                	break;
                }
                else{
                whereGoChess[x][i].setAlpha(0.4f);
                whereGoChessBool[x][i]=true;
                }
            }
            for( i=x+1, j=y-1; i<8 && j>=0; i++,j--){
                if (chessPosition[i][j]!=0){
                	if(chessPosition[i][j]>=17){
                        whereGoChess[i][j].setAlpha(0.4f);
                        whereGoChessBool[i][j]=true;
                        break;
                    }
                	break;
                }else{
                whereGoChess[i][j].setAlpha(0.4f);
                whereGoChessBool[i][j]=true;
                }
            }
            for( i=x-1, j=y-1; i>=0 && j>=0; i--,j--){
                if (chessPosition[i][j]!=0){
                	if(chessPosition[i][j]>=17){
                        whereGoChess[i][j].setAlpha(0.4f);
                        whereGoChessBool[i][j]=true;
                        break;
                    }
                	break;
                }
                else{
                whereGoChess[i][j].setAlpha(0.4f);
                whereGoChessBool[i][j]=true;
                }
            }
            for( i=x+1, j=y+1; i<8 && j<8; i++,j++){
                if (chessPosition[i][j]!=0){
                	if(chessPosition[i][j]>=17){
                        whereGoChess[i][j].setAlpha(0.4f);
                        whereGoChessBool[i][j]=true;
                        break;
                    }
                	break;
                }else{
                whereGoChess[i][j].setAlpha(0.4f);
                whereGoChessBool[i][j]=true;
                }
            }
            for( i=x-1, j=y+1; i>=0 && j<8; i--,j++){
                if (chessPosition[i][j]!=0){
                	if(chessPosition[i][j]>=17){
                        whereGoChess[i][j].setAlpha(0.4f);
                        whereGoChessBool[i][j]=true;
                        break;
                    }
                	break;
                }
                else{
                whereGoChess[i][j].setAlpha(0.4f);
                whereGoChessBool[i][j]=true;
                }
            }
        }
        
        if(numberPiece==31){//QUEEN BLACK
            resetWhereGoHighlightAndBool();
            int y=getPositionYByNumberPiece(numberPiece);
            int x=getPositionXByNumberPiece(numberPiece);
            int i=0, j=0;
            for( i=x+1;i<8;i++){
                if (chessPosition[i][y]!=0){
                	if(chessPosition[i][y]<=16 && chessPosition[i][y]>0){
                        whereGoChess[i][y].setAlpha(0.4f);
                        whereGoChessBool[i][y]=true;
                        break;
                    }
                	break;
                }
                else{
                whereGoChess[i][y].setAlpha(0.4f);
                whereGoChessBool[i][y]=true;
                }
            }
            for( i=x-1;i>=0;i--){
                if (chessPosition[i][y]!=0){
                	if(chessPosition[i][y]<=16 && chessPosition[i][y]>0){
                        whereGoChess[i][y].setAlpha(0.4f);
                        whereGoChessBool[i][y]=true;
                        break;
                    }
                	break;
                }
                else{
                whereGoChess[i][y].setAlpha(0.4f);
                whereGoChessBool[i][y]=true;
                }
            }
            for( i=y-1;i>=0;i--){
                if (chessPosition[x][i]!=0){
                	if(chessPosition[x][i]<=16 && chessPosition[x][i]>0){
                        whereGoChess[x][i].setAlpha(0.4f);
                        whereGoChessBool[x][i]=true;
                        break;
                    }
                	break;
                }else{
                whereGoChess[x][i].setAlpha(0.4f);
                whereGoChessBool[x][i]=true;
                }
            }
            for( i=y+1;i<8;i++){
                if (chessPosition[x][i]!=0){
                	if(chessPosition[x][i]<=16 && chessPosition[x][i]>0){
                        whereGoChess[x][i].setAlpha(0.4f);
                        whereGoChessBool[x][i]=true;
                        break;
                    }
                	break;
                }
                else{
                whereGoChess[x][i].setAlpha(0.4f);
                whereGoChessBool[x][i]=true;
                }
            }
            for( i=x+1, j=y-1; i<8 && j>=0; i++,j--){
                if (chessPosition[i][j]!=0){
                	if(chessPosition[i][j]<=16 && chessPosition[i][j]>0){
                        whereGoChess[i][j].setAlpha(0.4f);
                        whereGoChessBool[i][j]=true;
                        break;
                    }
                	break;
                }else{
                whereGoChess[i][j].setAlpha(0.4f);
                whereGoChessBool[i][j]=true;
                }
            }
            for( i=x-1, j=y-1; i>=0 && j>=0; i--,j--){
                if (chessPosition[i][j]!=0){
                	if(chessPosition[i][j]<=16 && chessPosition[i][j]>0){
                        whereGoChess[i][j].setAlpha(0.4f);
                        whereGoChessBool[i][j]=true;
                        break;
                    }
                	break;
                }else{
                whereGoChess[i][j].setAlpha(0.4f);
                whereGoChessBool[i][j]=true;
                }
            }
            for( i=x+1, j=y+1; i<8 && j<8; i++,j++){
                if (chessPosition[i][j]!=0){
                	if(chessPosition[i][j]<=16 && chessPosition[i][j]>0){
                        whereGoChess[i][j].setAlpha(0.4f);
                        whereGoChessBool[i][j]=true;
                        break;
                    }
                	break;
                }else{
                whereGoChess[i][j].setAlpha(0.4f);
                whereGoChessBool[i][j]=true;
                }
            }
            for( i=x-1, j=y+1; i>=0 && j<8; i--,j++){
                if (chessPosition[i][j]!=0){
                	if(chessPosition[i][j]<=16 && chessPosition[i][j]>0){
                        whereGoChess[i][j].setAlpha(0.4f);
                        whereGoChessBool[i][j]=true;
                        break;
                    }
                	break;
                }else{
                whereGoChess[i][j].setAlpha(0.4f);
                whereGoChessBool[i][j]=true;
                }
            }
        }
        if(numberPiece==16){//KING WHITE
            resetWhereGoHighlightAndBool();
            int y=getPositionYByNumberPiece(numberPiece);
            int x=getPositionXByNumberPiece(numberPiece);
            if((x+1)<8 && (chessPosition[x+1][y]==0 || chessPosition[x+1][y]>=17)){whereGoChess[x+1][y].setAlpha(0.4f);whereGoChessBool[x+1][y]=true;}
            if((y+1)<8 && (chessPosition[x][y+1]==0 || chessPosition[x][y+1]>=17)){whereGoChess[x][y+1].setAlpha(0.4f);whereGoChessBool[x][y+1]=true;}
            if((x-1)>=0 && (chessPosition[x-1][y]==0 || chessPosition[x-1][y]>=17)){whereGoChess[x-1][y].setAlpha(0.4f);whereGoChessBool[x-1][y]=true;}
            if((y-1)>=0 && (chessPosition[x][y-1]==0 || chessPosition[x][y-1]>=17)){whereGoChess[x][y-1].setAlpha(0.4f);whereGoChessBool[x][y-1]=true;}
            
            if((x+1)<8 && (y+1)<8 && (chessPosition[x+1][y+1]==0 || chessPosition[x+1][y+1]>=17)){whereGoChess[x+1][y+1].setAlpha(0.4f);whereGoChessBool[x+1][y+1]=true;}
            if((y+1)<8 && (x-1)>=0 && (chessPosition[x-1][y+1]==0 || chessPosition[x-1][y+1]>=17)){whereGoChess[x-1][y+1].setAlpha(0.4f);whereGoChessBool[x-1][y+1]=true;}
            if((x+1)<8 && (y-1)>=0 && (chessPosition[x+1][y-1]==0 || chessPosition[x+1][y-1]>=17)){whereGoChess[x+1][y-1].setAlpha(0.4f);whereGoChessBool[x+1][y-1]=true;}
            if((y-1)>=0 && (x-1)>=0 && (chessPosition[x-1][y-1]==0 || chessPosition[x-1][y-1]>=17)){whereGoChess[x-1][y-1].setAlpha(0.4f);whereGoChessBool[x-1][y-1]=true;}
            
        }
        if(numberPiece==32){//KING BLACK
            resetWhereGoHighlightAndBool();
            int y=getPositionYByNumberPiece(numberPiece);
            int x=getPositionXByNumberPiece(numberPiece);
            if((x+1)<8 && (chessPosition[x+1][y]==0 || (chessPosition[x+1][y]<=16 && chessPosition[x+1][y]>0))){whereGoChess[x+1][y].setAlpha(0.4f);whereGoChessBool[x+1][y]=true;}
            if((y+1)<8 && (chessPosition[x][y+1]==0 || (chessPosition[x][y+1]<=16 && chessPosition[x][y+1]>0))){whereGoChess[x][y+1].setAlpha(0.4f);whereGoChessBool[x][y+1]=true;}
            if((x-1)>=0 && (chessPosition[x-1][y]==0 || (chessPosition[x-1][y]<=16 && chessPosition[x-1][y]>0))){whereGoChess[x-1][y].setAlpha(0.4f);whereGoChessBool[x-1][y]=true;}
            if((y-1)>=0 && (chessPosition[x][y-1]==0 || (chessPosition[x][y-1]<=16 && chessPosition[x][y-1]>0))){whereGoChess[x][y-1].setAlpha(0.4f);whereGoChessBool[x][y-1]=true;}
            
            if((x+1)<8 && (y+1)<8 && (chessPosition[x+1][y+1]==0 || (chessPosition[x+1][y+1]<=16 && chessPosition[x+1][y+1]>0))){whereGoChess[x+1][y+1].setAlpha(0.4f);whereGoChessBool[x+1][y+1]=true;}
            if((y+1)<8 && (x-1)>=0 && (chessPosition[x-1][y+1]==0 || (chessPosition[x-1][y+1]<=16 && chessPosition[x-1][y+1]>0))){whereGoChess[x-1][y+1].setAlpha(0.4f);whereGoChessBool[x-1][y+1]=true;}
            if((x+1)<8 && (y-1)>=0 && (chessPosition[x+1][y-1]==0 || (chessPosition[x+1][y-1]<=16 && chessPosition[x+1][y-1]>0))){whereGoChess[x+1][y-1].setAlpha(0.4f);whereGoChessBool[x+1][y-1]=true;}
            if((y-1)>=0 && (x-1)>=0 && (chessPosition[x-1][y-1]==0 || (chessPosition[x-1][y-1]<=16 && chessPosition[x-1][y-1]>0))){whereGoChess[x-1][y-1].setAlpha(0.4f);whereGoChessBool[x-1][y-1]=true;}
        }
    }
    
    public boolean whoPieceTouched(int x, int y){
        //NEGRAS
        if(turnPlayer==true){
            if ( pawnB1.getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y) )
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=17){
                    numberPieceActive=17;
                    setHighlightToSprite(pawnB1,17);
                    setHighlightwhereCanGo(17);
                    return true;
                }
            }
            if ( pawnB2.getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y) )
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=18){
                    numberPieceActive=18;
                    setHighlightToSprite(pawnB2,18);
                    setHighlightwhereCanGo(18);
                    return true;
                }
            }
            if ( pawnB3.getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y) )
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=19){
                    numberPieceActive=19;
                    setHighlightToSprite(pawnB3,19);
                    setHighlightwhereCanGo(19);
                    return true;
                }
            }
            if ( pawnB4.getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y) )
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=20){
                    numberPieceActive=20;
                    setHighlightToSprite(pawnB4,20);
                    setHighlightwhereCanGo(20);
                    return true;
                }
            }
            if ( pawnB5.getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y) )
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=21){
                    numberPieceActive=21;
                    setHighlightToSprite(pawnB5,21);
                    setHighlightwhereCanGo(21);
                    return true;
                }
            }
            if ( pawnB6.getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y) )
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=22){
                    numberPieceActive=22;
                    setHighlightToSprite(pawnB6,22);
                    setHighlightwhereCanGo(22);
                    return true;
                }
            }
            if ( pawnB7.getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y) )
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=23){
                    numberPieceActive=23;
                    setHighlightToSprite(pawnB7,23);
                    setHighlightwhereCanGo(23);
                    return true;
                }
            }
            if ( pawnB8.getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y) )
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=24){
                    numberPieceActive=24;
                    setHighlightToSprite(pawnB8,24);
                    setHighlightwhereCanGo(24);
                    return true;
                }
            }
            
            if(rookB1.getBoundingRectangle().contains(x,Gdx.graphics.getHeight() - y))
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=25){
                    numberPieceActive=25;
                    setHighlightToSprite(rookB1,25);
                    setHighlightwhereCanGo(25);
                    return true;
                }
            }
            if(rookB2.getBoundingRectangle().contains(x,Gdx.graphics.getHeight() - y))
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=26){
                    numberPieceActive=26;
                    setHighlightToSprite(rookB2,26);
                    setHighlightwhereCanGo(26);
                    return true;
                }
            }
            if(knightB1.getBoundingRectangle().contains(x,Gdx.graphics.getHeight() - y))
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=27){
                    numberPieceActive=27;
                    setHighlightToSprite(knightB1,27);
                    setHighlightwhereCanGo(27);
                    return true;
                }
            }
            if(knightB2.getBoundingRectangle().contains(x,Gdx.graphics.getHeight() - y))
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=28){
                    numberPieceActive=28;
                    setHighlightToSprite(knightB2,28);
                    setHighlightwhereCanGo(28);
                    return true;
                }
            }
            if(bishopB1.getBoundingRectangle().contains(x,Gdx.graphics.getHeight() - y))
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=29){
                    numberPieceActive=29;
                    setHighlightToSprite(bishopB1,29);
                    setHighlightwhereCanGo(29);
                    return true;
                }
            }
            
            if(bishopB2.getBoundingRectangle().contains(x,Gdx.graphics.getHeight() - y))
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=30){
                    numberPieceActive=30;
                    setHighlightToSprite(bishopB2,30);
                    setHighlightwhereCanGo(30);
                    return true;
                }
            }
            if(queenB.getBoundingRectangle().contains(x,Gdx.graphics.getHeight() - y))
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=31){
                    numberPieceActive=31;
                    setHighlightToSprite(queenB,31);
                    setHighlightwhereCanGo(31);
                    return true;
                }
            }
            if(kingB.getBoundingRectangle().contains(x,Gdx.graphics.getHeight() - y))
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=32){
                    numberPieceActive=32;
                    setHighlightToSprite(kingB,32);
                    setHighlightwhereCanGo(32);
                    return true;
                }
            }
            
            
        }
        
        //BLANCAS
        if(turnPlayer==false){
            if ( pawnW1.getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y) )
            {
                
                resetWhereGoHighlightAndBool();
                
                resetPieceHighlight();
                
                if(numberPieceActive!=1){
                    
                    numberPieceActive=1;
                    
                    setHighlightToSprite(pawnW1,1);
                    
                    setHighlightwhereCanGo(1);
                    
                    return true;
                }
                
            }
            if ( pawnW2.getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y) )
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=2){
                    numberPieceActive=2;
                    setHighlightToSprite(pawnW2,2);
                    setHighlightwhereCanGo(2);
                    return true;
                }
            }
            if ( pawnW3.getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y) )
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=3){
                    numberPieceActive=3;
                    setHighlightToSprite(pawnW3,3);
                    setHighlightwhereCanGo(3);
                    return true;
                }
            }
            if ( pawnW4.getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y) )
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=4){
                    numberPieceActive=4;
                    setHighlightToSprite(pawnW4,4);
                    setHighlightwhereCanGo(4);
                    return true;
                }
            }
            if ( pawnW5.getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y) )
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=5){
                    numberPieceActive=5;
                    setHighlightToSprite(pawnW5,5);
                    setHighlightwhereCanGo(5);
                    return true;
                }
            }
            if ( pawnW6.getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y) )
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=6){
                    numberPieceActive=6;
                    setHighlightToSprite(pawnW6,6);
                    setHighlightwhereCanGo(6);
                    return true;
                }
            }
            if ( pawnW7.getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y) )
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=7){
                    numberPieceActive=7;
                    setHighlightToSprite(pawnW7,7);
                    setHighlightwhereCanGo(7);
                    return true;
                }
            }
            if ( pawnW8.getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y) )
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=8){
                    numberPieceActive=8;
                    setHighlightToSprite(pawnW8,8);
                    setHighlightwhereCanGo(8);
                    return true;
                }
            }
            if(rookW1.getBoundingRectangle().contains(x,Gdx.graphics.getHeight() - y))
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=9){
                    numberPieceActive=9;
                    setHighlightToSprite(rookB1,9);
                    setHighlightwhereCanGo(9);
                    return true;
                }
            }
            if(rookW2.getBoundingRectangle().contains(x,Gdx.graphics.getHeight() - y))
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=10){
                    numberPieceActive=10;
                    setHighlightToSprite(rookW2,10);
                    setHighlightwhereCanGo(10);
                    
                    return true;
                }
            }
            if(knightW1.getBoundingRectangle().contains(x,Gdx.graphics.getHeight() - y))
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=11){
                    numberPieceActive=11;
                    setHighlightToSprite(knightW1,11);
                    setHighlightwhereCanGo(11);
                    return true;
                }
            }
            if(knightW2.getBoundingRectangle().contains(x,Gdx.graphics.getHeight() - y))
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=12){
                    numberPieceActive=12;
                    setHighlightToSprite(knightW2,12);
                    setHighlightwhereCanGo(12);
                    return true;
                }
            }
            if(bishopW1.getBoundingRectangle().contains(x,Gdx.graphics.getHeight() - y))
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=13){
                    numberPieceActive=13;
                    setHighlightToSprite(bishopW1,13);
                    setHighlightwhereCanGo(13);
                    return true;
                }
            }
            if(bishopW2.getBoundingRectangle().contains(x,Gdx.graphics.getHeight() - y))
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=14){
                    numberPieceActive=14;
                    setHighlightToSprite(bishopW2,14);
                    setHighlightwhereCanGo(14);
                    return true;
                }
            }
            if(queenW.getBoundingRectangle().contains(x,Gdx.graphics.getHeight() - y))
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=15){
                    numberPieceActive=15;
                    setHighlightToSprite(queenW,15);
                    setHighlightwhereCanGo(15);
                    return true;
                }
            }
            if(kingW.getBoundingRectangle().contains(x,Gdx.graphics.getHeight() - y))
            {
                resetWhereGoHighlightAndBool();
                resetPieceHighlight();
                if(numberPieceActive!=16){
                    numberPieceActive=16;
                    setHighlightToSprite(kingW,16);
                    setHighlightwhereCanGo(16);
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    	//si es mi turno, juego, si no, no puedo hacer nada(deshabilitar el boton sen)
    	//System.out.println(UserSession.User);
    	//System.out.println(resultChallengeTurn);
    	if(resultChallengeTurn.equals(UserSession.User)){ 
    		
    	if ( sendButton.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY) )
		 {
    		sendButton.setScale(1);
    		
    		//obtenemos si la partida esta en proceso (inProgress)
    		try {
				inProcess = sendGet_isInProcess(actualUser1(),actualUser2());
			} catch (Exception e) {
				e.printStackTrace();
			}
    		//en este caso es que tengo que realizar la primera jugada (es el que a creado la partida quien esta jugando la 1º jugada)
    		if(inProcess.equals("0") && resultChallengeTurn.equals(UserSession.User)){
    			//no guarda por que espero a que el otro acepte la partida
    			
    		}
    		else{
    			save();	
    		}
    		//si es la primera jugada del USUARIO RETADO tenemos que aceptarla si juega
    		if(resultChallengeTurn.equals(UserSession.User) && inProcess.equals("0") && actualUser2().equals(UserSession.User)){
    			//seteamos inProcess = 1 para aceptar la partida
    			try {
					sendGet_setInProcessOne(actualUser1(),actualUser2());
				} catch (Exception e) {
					e.printStackTrace();
				}
    			//guardamos la partida
    			save();
    		}
    		else{
    			if(resultChallengeTurn.equals(UserSession.User))
    			save();
    		}
    		
    		//enviamos jugada
    		try {
    			sendGet_play(actualUser1(),actualUser2(),originX,originY,destinyX, destinyY);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}	
    			
    		UserSession.game1User1="";
    		UserSession.game1User2="";
    		UserSession.game2User1="";
    		UserSession.game2User2="";
    		UserSession.game3User1="";
    		UserSession.game3User2="";
    		
    		Timer.schedule(new Task() {
				@Override
				public void run() {
					((Game)Gdx.app.getApplicationListener()).setScreen(new LobbyScreen());
				}
			}, 2);
    		
		 }
    	}
        return false;
    } 
    
	private void save() {
		UserSession save;
		FileHandle file = Gdx.files.local("bin/" + actualUser1() + "vs"	+ actualUser2());
		if (file.exists()) {
			Json json = new Json();
			save = json.fromJson(UserSession.class, file);
		} else {
			save = new UserSession();
		}
		save.setChess(chessPosition);
		Json json = new Json();
		file.writeString(json.prettyPrint(save), false);
	}

	private void load() {
		UserSession load;
		FileHandle file = Gdx.files.local("bin/" + actualUser1() + "vs"	+ actualUser2());
		if (file.exists()) {
			Json json = new Json();
			load = json.fromJson(UserSession.class, file);
		} else {
			load = new UserSession();
			notFileExists = true;
		}
		chessPosition = load.getChess();
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
    
    private String sendGet_getChallengeTurn(String user1, String user2) throws Exception {
		String url = "http://84.123.125.224/chesstime/getChallengeTurn.php?user1="+user1+"&user2="+user2;
		String response="";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection(); 
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		response = in.readLine();
		in.close();
		return response;
	}
    private String sendGet_isInProcess(String user1, String user2) throws Exception {
  		String url = "http://84.123.125.224/chesstime/isInProcess.php?user1="+user1+"&user2="+user2;
  		String response="";
  		URL obj = new URL(url);
  		HttpURLConnection con = (HttpURLConnection) obj.openConnection(); 
  		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
  		response = in.readLine();
  		in.close();
  		return response;
  	}
    private String sendGet_play(String user1, String user2, int originX,int originY, int destinyX, int destinyY) throws Exception {
    	String url = "http://84.123.125.224/chesstime/play.php?user1="+ user1 + "&user2=" + user2 + "&originX=" + originX + "&originY="+ originY + "&destinyX=" + destinyX + "&destinyY=" + destinyY + "&turn=" + getOtherUser();
		String response = "";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		response = in.readLine();
		in.close();
		return response;
	}
    private String sendGet_setInProcessOne(String user1, String user2) throws Exception {
  		String url = "http://84.123.125.224/chesstime/setInProcessOne.php?user1="+user1+"&user2="+user2;
  		String response="";
  		URL obj = new URL(url);
  		HttpURLConnection con = (HttpURLConnection) obj.openConnection(); 
  		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
  		response = in.readLine();
  		in.close();
  		return response;
  	}
    
    public String getOtherUser(){
    	if(UserSession.User.equals(actualUser1())) return actualUser2();
    	else return actualUser1();
    }
    
}
