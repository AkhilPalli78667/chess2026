// AKHIL
//  Snake piece
// Description: The Snake moves in a unique horizontal zig-zag pattern. 
// It slithers across the board by alternating rows while moving left or right,
// but it cannot jump over other pieces.  




package com.example;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//you will need to implement two functions in this file.

public class Snake extends Piece{
    
    public Snake(boolean isWhite, String img_file) {
        super(isWhite, img_file);
    }
    
    

    
    
    
    
   







    // TO BE IMPLEMENTED!
    //return a list of every square that is "controlled" by this piece. A square is controlled
    //if the piece capture into it legally.
        // NEW HELPER: Defines the specific zig-zag track across the middle
   // NEW HELPER: Casts a horizontal zig-zag path
    private void addHorizontalZigZag(Square[][] board, ArrayList<Square> list, int startRow, int startCol, int colStep, int initialRowStep) {
        int currRow = startRow;
        int currCol = startCol;
        int rowStep = initialRowStep; // Tells us whether the first 'zig' is up or down

        while (true) {
            int nextCol = currCol + colStep; // Moving horizontally (y)
            int nextRow = currRow + rowStep; // moving vertically (x)

            // 1. Stop if we hit the edge of the board
            if (nextRow < 0 || nextRow >= 8 || nextCol < 0 || nextCol >= 8) {
                break; 
            }

            
            Square target = board[nextRow][nextCol];
            list.add(target);

          
            if (target.isOccupied()) break;
          
            currRow = nextRow;
            currCol = nextCol;
            rowStep = -rowStep; // Flips +1 to -1, or -1 to +1
        }
    }

    
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        ArrayList<Square> controlled = new ArrayList<>();
        
        
        int r = start.getRow(); 
        int c = start.getCol(); 

       

        // Path 1: Move Right (col +1), first step Up (row -1)
        addHorizontalZigZag(board, controlled, r, c, 1, -1);
        
        // Path 2: Move Right (col +1), first step Down (row +1)
        addHorizontalZigZag(board, controlled, r, c, 1, 1);
        
        // Path 3: Move Left (col -1), first step Up (row -1)
        addHorizontalZigZag(board, controlled, r, c, -1, -1);
        
        // Path 4: Move Left (col -1), first step Down (row +1)
        addHorizontalZigZag(board, controlled, r, c, -1, 1);

        return controlled;
    }

    

    //TO BE IMPLEMENTED!
    //implement the move function here
    //it's up to you how the piece moves, but at the very least the rules should be logical and it should never move off the board!
    //returns an arraylist of squares which are legal to move to
    //please note that your piece must have some sort of logic. Just being able to move to every square on the board is not
    //going to score any points.
    public ArrayList<Square> getLegalMoves(Board b, Square start){
        ArrayList<Square> legalMoves = new ArrayList<>();
    
        ArrayList<Square> controlled = getControlledSquares(b.getSquareArray(), start);

        for (Square s : controlled) {
        
            if (!s.isOccupied() || s.getOccupyingPiece().getColor() != getColor() ) {
                legalMoves.add(s);
            }
        }
        return legalMoves;
    }
    @Override
    public String toString() {
         return "A " + (getColor() ? "white" : "black") + " Snake";
    }



}