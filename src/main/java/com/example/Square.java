package com.example;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.*;


//Please read the following class carefully! It represents a single chess board square and is what you'll be using
//to represent the chessboard.
@SuppressWarnings("serial")
public class Square extends JComponent {
	//a reference back to the board that stores this square.
    private Board b;
    
    //true for white, false for black.
    private final boolean color;
    
    //if there's a piece on the square this stores it. If there isn't this stores null.
    private Piece occupyingPiece;
    
    //True means to display the piece. This property will be switched to false when we are dragging a piece around while choosing our next move.
    private boolean dispPiece;
    
    //the coordinates of the square.
    private int row;
    private int col;
    
    
    public Square(Board b, boolean isWhite, int row, int col) {
        
        this.b = b;
        this.color = isWhite;
        this.dispPiece = true;
        this.row = row;
        this.col = col;
        this.setBorder(BorderFactory.createEmptyBorder());
    }
    
    public boolean getColor() {
        return this.color;
    }
    
    public Piece getOccupyingPiece() {
        return occupyingPiece;
    }
    
    public boolean isOccupied() {
        return (this.occupyingPiece != null);
    }
    
    public int getRow() {
        return this.row;
    }
    
    public int getCol() {
        return this.col;
    }
    
    //precondition: none
    //postcondition: the display property is set. true means the piece will be drawn. false means the piece will not show up (but is still contained by the square)
    public void setDisplay(boolean v) {
        this.dispPiece = v;
    }
    
    //precondition: none
    //postcondition: the given piece p is placed into this square. p could be null.
    public void put(Piece p) {
        this.occupyingPiece = p;
    }
    
    //precondition: none
    //postcondition: returns the piece in this Square. Returns null if the square is empty.
    public Piece removePiece() {
        Piece p = this.occupyingPiece;
        this.occupyingPiece = null;
        return p;
    }

    //precondition: g is a non-null valid Graphics object.
    //postcondition: either a white or black square is drawn onto the screen. 
    //Note*: if you want to mess with the color scheme of the board edit the g.setColor method calls bellow to use the colors of your choice.
    public void draw(Graphics g) {
        
        // super.paintComponent(g);
        
        //change this to alter the color scheme.
        if (this.color) {
            g.setColor(new Color(221,192,127));
        } else {
            g.setColor(new Color(101,67,33));
        }

        //this part highlights the square you're trying to move a piece from.
        if(!dispPiece){
            setBorder(BorderFactory.createLineBorder(Color.blue));
        }
     
        //please note that these values are not set here, but rather we are using a layout manager to help us place these evenly
        //for more information on how this works come back next year for programming 3! (or look it up yourself)
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        
        //if there's a piece at this location and we are not currently trying to drag it, draw it onto the screen.
        if(occupyingPiece != null && dispPiece) {
            occupyingPiece.draw(g, this);
        }
    }
    
    
}
