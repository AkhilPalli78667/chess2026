package com.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.io.File;
import java.util.ArrayList;


import java.net.URL;
import java.util.ArrayList;


import javax.imageio.ImageIO;
import javax.swing.*;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
 

//You will be implmenting a part of a function and a whole function in this document. Please follow the directions for the 
//suggested order of completion that should make testing easier.
@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
    // Resource location constants for piece images
    public static final String PICTURE_PATH = "/src/main/java/com/example/Pictures/";
    private static final String RESOURCES_WBISHOP_PNG = PICTURE_PATH + "wbishop.png";
    private static final String RESOURCES_BBISHOP_PNG = PICTURE_PATH + "bbishop.png";
    private static final String RESOURCES_WKNIGHT_PNG = PICTURE_PATH + "wknight.png";
    private static final String RESOURCES_BKNIGHT_PNG = PICTURE_PATH + "bknight.png";
    private static final String RESOURCES_WROOK_PNG = PICTURE_PATH + "wrook.png";
    private static final String RESOURCES_BROOK_PNG = PICTURE_PATH + "brook.png";
    private static final String RESOURCES_WKING_PNG = PICTURE_PATH + "wking.png";
    private static final String RESOURCES_BKING_PNG = PICTURE_PATH + "bking.png";
    private static final String RESOURCES_BQUEEN_PNG = PICTURE_PATH + "bqueen.png";
    private static final String RESOURCES_WQUEEN_PNG = PICTURE_PATH + "wqueen.png";
    private static final String RESOURCES_WPAWN_PNG = PICTURE_PATH + "wpawn.png";
    private static final String RESOURCES_BPAWN_PNG = PICTURE_PATH + "bpawn.png";

    //constant used to keep track of where the piece should be drawn when the user is dragging it
    private static final int PIECE_OFFSET = 24;

    // Logical and graphical representations of board
    private final Square[][] board;
    private final GameWindow g;

    // contains true if it's white's turn.
    private boolean whiteTurn;

    // if the player is currently dragging a piece this variable contains it.
    Piece currPiece;
    //the square your piece came from when the user tries to move it.
    private Square fromMoveSquare;

    //the square your piece tries to go to when the user tries to move it.
    private Square endSquare;

    // used to keep track of the x/y coordinates of the mouse.

    //used to keep track of the x/y coordinates of the mouse.

    private int currX;
    private int currY;

    public Board(GameWindow g) {
        this.g = g;
        board = new Square[8][8];
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);


        // TO BE IMPLEMENTED FIRST

        // for (.....)
        // populate the board with squares here. Note that the board is composed of 64
        // squares alternating from white to black.
        //IMPORTANT**** : please note for each square you create you HAVE to do "this.add(<your new square here>)" 
        //the reason this is required has to do with how visual components are rendered, so if you neglect to do this
        //you will not see any of your squares show up on the board!
        // Where's the "add" method? Stay tuned for next unit where we discover where it is and why we can do this action.

        //TO BE IMPLEMENTED FIRST
     
      for (int x = 0; x<8; x++){
        for (int y = 0; y < 8; y++){
            boolean isWhite = (x+y)%2 == 0;
            Square bord = new Square (this,isWhite,x,y );
            board[x][y] = bord;
            this.add(bord);
        }
      }  
//        	populate the board with squares here. Note that the board is composed of 64 squares alternating from 
//        	white to black.

        
        initializePieces();

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;

    }

    // set up the board such that the black pieces are on one side and the white
    // pieces are on the other.
    // since we only have one kind of piece for now you need only set the same
    // number of pieces on either side.
    // it's up to you how you wish to arrange your pieces.
    void initializePieces() {


        board[0][0].put(new Piece(true, RESOURCES_WKING_PNG));

    	
    	board[0][0].put(new Piece(true, RESOURCES_WKING_PNG));

        //board[0][2].put(new Piece (true, resources));

        


    }

    public Square[][] getSquareArray() {
        return this.board;
    }

    public boolean getTurn() {
        return whiteTurn;
    }

    public void setCurrPiece(Piece p) {
        this.currPiece = p;
    }

    public Piece getCurrPiece() {
        return this.currPiece;
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col].draw(g);

     Image backgroundImage = null; 
     URL imageUrl = null;
     if (currPiece != null) {
      imageUrl = getClass().getResource("/src/main/java/com/example/"+currPiece.getImage());
     }

     if (imageUrl != null) {
            // This is the cleanest way to get an AWT Image object from a URL
            backgroundImage = Toolkit.getDefaultToolkit().createImage(imageUrl);
        } else {
           // System.err.println("Image resource not found. Check path: /src/main/java/com/example/Pictures/");
        }
    

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = board[x][y];
                if(sq == fromMoveSquare)
                	 sq.setBorder(BorderFactory.createLineBorder(Color.blue));
                sq.paintComponent(g);
                //System.out.println("Painting square at " + x + ", " + y);   
                

            }
        }

        if (currPiece != null) {
            if (currPiece.getColor() == whiteTurn) {
                final Image img = currPiece.getImage();
                g.drawImage(img, currX, currY, null);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();

        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (sq.isOccupied() && sq.getOccupyingPiece().getColor() == whiteTurn) {
            currPiece = sq.getOccupyingPiece();
            fromMoveSquare = sq;



            for (Square s : currPiece.getLegalMoves(this,fromMoveSquare)){
                

                s.setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.red));



            }
            if (currPiece.getColor() != whiteTurn)
                return;

            sq.setDisplay(false);
        }
        repaint();
    }

    // TO BE IMPLEMENTED!
    // should move the piece to the desired location only if this is a legal move.
    // use the pieces "legal move" function to determine if this move is legal, then
    // complete it by moving the new piece to it's new board location.
    @Override
    public void mouseReleased(MouseEvent e) {

        endSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        // using currPiece
        if(fromMoveSquare!= null){
            fromMoveSquare.setDisplay(true);
        }

        Square endSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));
        
        //using currPiece
          if (currPiece != null && fromMoveSquare != null && currPiece.getColor() == whiteTurn) {
        
        // 2. Check the zig-zag rules you defined in getLegalMoves
        ArrayList<Square> legalMoves = currPiece.getLegalMoves(this, fromMoveSquare);
        
        if (legalMoves != null && legalMoves.contains(endSquare)) {
            // 3. Move the piece: put it on the new square, remove from old
            endSquare.put(currPiece);      
            fromMoveSquare.put(null);      
            
            // 4. Change the turn
            whiteTurn = !whiteTurn;
        }
    }

    // 5. Clear highlights and reset the 'hovering' visual
    for (Square[] row : board) {
        for (Square s : row) {
            s.setBorder(null);
        }
    }
   
    if (fromMoveSquare != null) {
        fromMoveSquare.setDisplay(true); // Show the piece again on the board
    }
    
   
    
    

       
        fromMoveSquare.setDisplay(true);

        currPiece = null;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - PIECE_OFFSET;
        currY = e.getY() - PIECE_OFFSET;

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
