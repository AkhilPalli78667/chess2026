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
        // NEW HELPER: Defines the specific zig-zag track across the middle
   // NEW HELPER: Casts a horizontal zig-zag path
    private void addHorizontalZigZag(Square[][] board, ArrayList<Square> list, int startRow, int startCol, int colStep, int initialRowStep) {
        int currRow = startRow;
        int currCol = startCol;
        int rowStep = initialRowStep; // Tells us whether the first 'zig' is up or down

        while (true) {
            int nextCol = currCol + colStep; // Moving horizontally (y)
            int nextRow = currRow + rowStep; // Oscillating vertically (x)

            // 1. Stop if we hit the edge of the board
            if (nextRow < 0 || nextRow >= 8 || nextCol < 0 || nextCol >= 8) {
                break; 
            }

            // 2. Add the square to our list of controlled squares
            // Since your x=row and y=col, we use board[row][col]
            Square target = board[nextRow][nextCol];
            list.add(target);

            // 3. JUMPING LOGIC
            // If you want the piece to STOP when it hits another piece, leave this line uncommented.
            // If you want it to JUMP over pieces, DELETE or COMMENT OUT this next line!
            if (target.isOccupied()) break;

            // 4. Update position and flip the vertical direction to create the "zag"
            currRow = nextRow;
            currCol = nextCol;
            rowStep = -rowStep; // Flips +1 to -1, or -1 to +1
        }
    }

    
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        ArrayList<Square> controlled = new ArrayList<>();
        
        // Using your coordinate system: getRow() is actually your 'x', getCol() is your 'y'
        int r = start.getRow(); 
        int c = start.getCol(); 

        // We cast 4 different zig-zag paths so the piece can move in any horizontal direction

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
        // This calls the "Belt" logic we just wrote above
        ArrayList<Square> controlled = getControlledSquares(b.getSquareArray(), start);

        for (Square s : controlled) {
            // Logic: You can land if it's empty OR if it's an enemy.
            if (!s.isOccupied() || s.getOccupyingPiece().getColor() != this.color) {
                legalMoves.add(s);
            }
        }
        return legalMoves;
    }
}