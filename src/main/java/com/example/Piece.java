package com.example;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//you will need to implement two functions in this file.
public class Piece {
    private final boolean color;
    private BufferedImage img;
    
    public Piece(boolean isWhite, String img_file) {
        this.color = isWhite;
         
        try {
            if (this.img == null) {
                this.img = ImageIO.read(new File(System.getProperty("user.dir")+img_file));
            }
          } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
          }
    }
    
    

    
    public boolean getColor() {
        return color;
    }
    
    public Image getImage() {
        return img;
    }
    
    //precondition: g and currentSquare must be on-null valid objects.
    //postcondition: the image stored in the img property of this object is drawn to the screen.
    public void draw(Graphics g, Square currentSquare) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();
        


        g.drawImage(this.img, x, y, null);
    }
    
    
    // TO BE IMPLEMENTED!
    //return a list of every square that is "controlled" by this piece. A square is controlled
    //if the piece capture into it legally.
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
     
      ArrayList<Square> controlled = new ArrayList<>();
        int x = start.getX();
        int y = start.getY();
         // Path 1: Zig-zagging "Up" (decreasing Y)
    addZigZagPath(board, controlled, x, y, -1, true); 
    // Path 2: Zig-zagging "Down" (increasing Y)
    addZigZagPath(board, controlled, x, y, 1, true);

    return controlled;


    }


    private void addZigZagPath(Square[][] board, ArrayList<Square> list, int x, int y, int stepY, boolean startRight) {
    int currX = x;
    int currY = y;
    boolean moveRight = startRight;

    // Continue the zig-zag until it hits the board edge or another piece
    while (true) {
        int nextX = currX + (moveRight ? 1 : -1);
        int nextY = currY + stepY;

        // Boundary check
        if (nextX < 0 || nextX >= 8 || nextY < 0 || nextY >= 8) break;

        Square target = board[nextX][nextY];
        list.add(target);

        // If the square is occupied, the snake stops (it controls the square but can't pass through)
        if (target.isOccupied()) break;

        // Move the snake to the new square and flip the zig-zag direction.
        currX = nextX;
        currY = nextY;
        moveRight = !moveRight;
    }
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
        // Legal if empty OR if occupied by an enemy
             if (!s.isOccupied() || s.getOccupyingPiece().getColor() != this.color) {
            legalMoves.add(s);
            }
        }
        return legalMoves;
    }
}
