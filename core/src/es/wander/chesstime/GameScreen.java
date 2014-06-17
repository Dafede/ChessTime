package es.wander.chesstime;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen, InputProcessor{
	
	private SpriteBatch batch;
	private Texture tableChessTexture;
	
	private Sprite tableHighlight;

	private float Wi=Gdx.graphics.getWidth();
	private float Hi=Gdx.graphics.getHeight();
	//private int W=Gdx.graphics.getWidth();
	private int H=Gdx.graphics.getHeight();
	
	/*
	 * buscar una relacion buena para el 8, 15 que se adapte segun la resolucion de la pantalla
	 */
	private int PADDINGPIECEBORDER = (int) (8 * (Wi/Hi));
	private int PADDINGPIECEINTER = (int) (15 * (Wi/Hi));
	
	
	private int sizePiece = (Gdx.graphics.getWidth()/8)-10;
	
	Sprite pawnW1;	Sprite pawnW2;	Sprite pawnW3;	Sprite pawnW4;	Sprite pawnW5;	Sprite pawnW6;	Sprite pawnW7;	Sprite pawnW8;	Sprite rookW1;	Sprite rookW2;	Sprite queenW;	Sprite knightW1;	Sprite knightW2;	Sprite bishopW1;	Sprite bishopW2;	Sprite kingW;
	
	Sprite pawnB1;	Sprite pawnB2;	Sprite pawnB3;	Sprite pawnB4;	Sprite pawnB5;	Sprite pawnB6;	Sprite pawnB7;	Sprite pawnB8;	Sprite rookB1;	Sprite rookB2;	Sprite queenB;	Sprite knightB1;	Sprite knightB2;	Sprite bishopB1;	Sprite bishopB2;	Sprite kingB;
	
	@Override
	public void show() {
		
		batch = new SpriteBatch();

		tableChessTexture = new Texture("chessTable2.png");		
		tableHighlight = new Sprite(new Texture("highlight.png"));
		tableHighlight.setSize(sizePiece,sizePiece);
		tableHighlight.setPosition(getPositionX(0), getPositionY(0));
		tableHighlight.setAlpha(0);
		
		initializeBlack();
		initializeWhite();
		
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0.078f, 0.588f, 0.784f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		batch.draw(tableChessTexture,0,Gdx.graphics.getHeight()-Gdx.graphics.getWidth(),Gdx.graphics.getWidth(),Gdx.graphics.getWidth());
		
		drawBlack();
		drawWhite();
		
		tableHighlight.draw(batch);
		
		batch.end();
	}
	
	public void initializeBlack(){
		
		pawnB1 = new Sprite(new Texture("pawnB.png"));
		pawnB1.setSize(sizePiece,sizePiece);
		pawnB1.setPosition(getPositionX(0), getPositionY(1));
		
		pawnB2 = new Sprite(new Texture("pawnB.png"));
		pawnB2.setSize(sizePiece,sizePiece);
		pawnB2.setPosition(getPositionX(1), getPositionY(1));
		
		pawnB3 = new Sprite(new Texture("pawnB.png"));
		pawnB3.setSize(sizePiece,sizePiece);
		pawnB3.setPosition(getPositionX(2), getPositionY(1));

		pawnB4 = new Sprite(new Texture("pawnB.png"));
		pawnB4.setSize(sizePiece,sizePiece);
		pawnB4.setPosition(getPositionX(3), getPositionY(1));
		
		pawnB5 = new Sprite(new Texture("pawnB.png"));
		pawnB5.setSize(sizePiece,sizePiece);
		pawnB5.setPosition(getPositionX(4), getPositionY(1));
		
		pawnB6 = new Sprite(new Texture("pawnB.png"));
		pawnB6.setSize(sizePiece,sizePiece);
		pawnB6.setPosition(getPositionX(5), getPositionY(1));
		
		pawnB7 = new Sprite(new Texture("pawnB.png"));
		pawnB7.setSize(sizePiece,sizePiece);
		pawnB7.setPosition(getPositionX(6), getPositionY(1));
		
		pawnB8 = new Sprite(new Texture("pawnB.png"));
		pawnB8.setSize(sizePiece,sizePiece);
		pawnB8.setPosition(getPositionX(7), getPositionY(1));
		
		rookB1 = new Sprite(new Texture("rookB.png"));
		rookB1.setSize(sizePiece,sizePiece);
		rookB1.setPosition(getPositionX(0), getPositionY(0));
		
		rookB2 = new Sprite(new Texture("rookB.png"));
		rookB2.setSize(sizePiece,sizePiece);
		rookB2.setPosition(getPositionX(7), getPositionY(0));
		
		knightB1 = new Sprite(new Texture("knightB.png"));
		knightB1.setSize(sizePiece,sizePiece);
		knightB1.setPosition(getPositionX(1), getPositionY(0));
		
		knightB2 = new Sprite(new Texture("knightB.png"));
		knightB2.setSize(sizePiece,sizePiece);
		knightB2.setPosition(getPositionX(6), getPositionY(0));
		
		bishopB1 = new Sprite(new Texture("bishopB.png"));
		bishopB1.setSize(sizePiece,sizePiece);
		bishopB1.setPosition(getPositionX(2), getPositionY(0));
		
		bishopB2 = new Sprite(new Texture("bishopB.png"));
		bishopB2.setSize(sizePiece,sizePiece);
		bishopB2.setPosition(getPositionX(5), getPositionY(0));
		
		queenB = new Sprite(new Texture("queenB.png"));
		queenB.setSize(sizePiece,sizePiece);
		queenB.setPosition(getPositionX(4), getPositionY(0));
		
		kingB = new Sprite(new Texture("kingB.png"));
		kingB.setSize(sizePiece,sizePiece);
		kingB.setPosition(getPositionX(3), getPositionY(0));
		
	}
	
	public void initializeWhite(){

		pawnW1 = new Sprite(new Texture("pawnW.png"));
		pawnW1.setSize(sizePiece,sizePiece);
		pawnW1.setPosition(getPositionX(0), getPositionY(6));
		
		pawnW2 = new Sprite(new Texture("pawnW.png"));
		pawnW2.setSize(sizePiece,sizePiece);
		pawnW2.setPosition(getPositionX(1), getPositionY(6));
		
		pawnW3 = new Sprite(new Texture("pawnW.png"));
		pawnW3.setSize(sizePiece,sizePiece);
		pawnW3.setPosition(getPositionX(2), getPositionY(6));

		pawnW4 = new Sprite(new Texture("pawnW.png"));
		pawnW4.setSize(sizePiece,sizePiece);
		pawnW4.setPosition(getPositionX(3), getPositionY(6));
		
		pawnW5 = new Sprite(new Texture("pawnW.png"));
		pawnW5.setSize(sizePiece,sizePiece);
		pawnW5.setPosition(getPositionX(4), getPositionY(6));
		
		pawnW6 = new Sprite(new Texture("pawnW.png"));
		pawnW6.setSize(sizePiece,sizePiece);
		pawnW6.setPosition(getPositionX(5), getPositionY(6));
		
		pawnW7 = new Sprite(new Texture("pawnW.png"));
		pawnW7.setSize(sizePiece,sizePiece);
		pawnW7.setPosition(getPositionX(6), getPositionY(6));
		
		pawnW8 = new Sprite(new Texture("pawnW.png"));
		pawnW8.setSize(sizePiece,sizePiece);
		pawnW8.setPosition(getPositionX(7), getPositionY(6));
		
		rookW1 = new Sprite(new Texture("rookW.png"));
		rookW1.setSize(sizePiece,sizePiece);
		rookW1.setPosition(getPositionX(0), getPositionY(7));
		
		rookW2 = new Sprite(new Texture("rookW.png"));
		rookW2.setSize(sizePiece,sizePiece);
		rookW2.setPosition(getPositionX(7), getPositionY(7));
		
		knightW1 = new Sprite(new Texture("knightW.png"));
		knightW1.setSize(sizePiece,sizePiece);
		knightW1.setPosition(getPositionX(1), getPositionY(7));
		
		knightW2 = new Sprite(new Texture("knightW.png"));
		knightW2.setSize(sizePiece,sizePiece);
		knightW2.setPosition(getPositionX(6), getPositionY(7));
		
		bishopW1 = new Sprite(new Texture("bishopW.png"));
		bishopW1.setSize(sizePiece,sizePiece);
		bishopW1.setPosition(getPositionX(2), getPositionY(7));
		
		bishopW2 = new Sprite(new Texture("bishopW.png"));
		bishopW2.setSize(sizePiece,sizePiece);
		bishopW2.setPosition(getPositionX(5), getPositionY(7));
		
		queenW = new Sprite(new Texture("queenW.png"));
		queenW.setSize(sizePiece,sizePiece);
		queenW.setPosition(getPositionX(4), getPositionY(7));
		
		kingW = new Sprite(new Texture("kingW.png"));
		kingW.setSize(sizePiece,sizePiece);
		kingW.setPosition(getPositionX(3), getPositionY(7));
		
	}
	
	public int getPositionX(int x){
		
		switch (x) {
		
			case 0:  return PADDINGPIECEBORDER;
        
			case 1:  return PADDINGPIECEBORDER+(PADDINGPIECEINTER*1)+(sizePiece*1);
			
			case 2:  return PADDINGPIECEBORDER+(PADDINGPIECEINTER*2)+(sizePiece*2);
			
			case 3:  return PADDINGPIECEBORDER+(PADDINGPIECEINTER*3)+(sizePiece*3);
			
			case 4:  return PADDINGPIECEBORDER+(PADDINGPIECEINTER*4)+(sizePiece*4);
			
			case 5:  return PADDINGPIECEBORDER+(PADDINGPIECEINTER*5)+(sizePiece*5);
			
			case 6:  return PADDINGPIECEBORDER+(PADDINGPIECEINTER*6)+(sizePiece*6);
			
			case 7:  return PADDINGPIECEBORDER+(PADDINGPIECEINTER*7)+(sizePiece*7);
			
			default: return -1;
       
		}
	}
	
	public int getPositionY(int y){
		
		switch (y) {
		
			case 0:  return H-PADDINGPIECEBORDER-sizePiece;
        
			case 1:  return H-PADDINGPIECEBORDER-(PADDINGPIECEINTER*1)-(sizePiece*2);
			
			case 2:  return H-PADDINGPIECEBORDER-(PADDINGPIECEINTER*2)-(sizePiece*3);
			
			case 3:  return H-PADDINGPIECEBORDER-(PADDINGPIECEINTER*3)-(sizePiece*4);
			
			case 4:  return H-PADDINGPIECEBORDER-(PADDINGPIECEINTER*4)-(sizePiece*5);
			
			case 5:  return H-PADDINGPIECEBORDER-(PADDINGPIECEINTER*5)-(sizePiece*6);
			
			case 6:  return H-PADDINGPIECEBORDER-(PADDINGPIECEINTER*6)-(sizePiece*7);
			
			case 7:  return H-PADDINGPIECEBORDER-(PADDINGPIECEINTER*7)-(sizePiece*8);
			
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
public void setHighlightToSprite(Sprite sprite){
	 tableHighlight.setX(sprite.getX());
	 tableHighlight.setY(sprite.getY());
	 tableHighlight.setAlpha(0.3f);
	 batch.begin();
	 tableHighlight.draw(batch);
	 sprite.draw(batch);
	 batch.end();
}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		setHighlightToTouchedCell(screenX,screenY);
		if ( pawnW1.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY) )
		 {
			setHighlightToSprite(pawnW1);	
		 }
		if ( pawnW2.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY) )
		 {	
			setHighlightToSprite(pawnW2);
		 }
		return false;
	}
	
	public void setHighlightToTouchedCell(int x, int y){
		if ( pawnW1.getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y) )
		 {
			setHighlightToSprite(pawnW1);	
		 }
		if ( pawnW2.getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y) )
		 {	
			setHighlightToSprite(pawnW2);
		 }
		if ( pawnW3.getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y) )
		 {
			setHighlightToSprite(pawnW3);	
		 }
		if ( pawnW4.getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y) )
		 {	
			setHighlightToSprite(pawnW4);
		 }
		if ( pawnW5.getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y) )
		 {
			setHighlightToSprite(pawnW5);	
		 }
		if ( pawnW6.getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y) )
		 {	
			setHighlightToSprite(pawnW6);
		 }
		if ( pawnW7.getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y) )
		 {
			setHighlightToSprite(pawnW7);	
		 }
		if ( pawnW8.getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y) )
		 {	
			setHighlightToSprite(pawnW8);
		 }
		
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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
