package checkers_final_project;
import java.util.Scanner;
/**
 *
 * @author Quintin Stearns and Alexander Smith
 */
public class Board {
    Scanner in = new Scanner(System.in);
    private final Pieces[][] pieces = new Pieces[8][8];
    private boolean isValid = false;
    private int totRedPieces = 16,
                totBlackPieces = 16;
    
    //--------------------------------------------------------------------------
    //The constructor initializes the starting pieces for both sides of the
    //board, R for red pieces, B for black pieces, and " " for blank spots where
    //there are not yet pieces
    //--------------------------------------------------------------------------
    public Board(){
        
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                pieces[r][c] = new Pieces();
                if(r < 2)
                    pieces[r][c].initializePiece(1); // Red Piece Initialization
                else if(r > 5) 
                    pieces[r][c].initializePiece(2); // Black Piece Initialization
                else 
                    pieces[r][c].initializePiece(3); // Blank Spot Initialization
            } //End of Col. Loop
        } //End of Row Loop
        
    } //End of Board Constructor
    
    //--------------------------------------------------------------------------
    //playGame prints out the board and also prompts players to move pieces,
    //alternating turns and playing until the total number of pieces on the one
    //side reaches 0
    //--------------------------------------------------------------------------
    public void playGame(){
        int current_x,
                current_y,
                next_x,
                next_y,
                one = 1;
        
        while(totRedPieces != 0 || totBlackPieces != 0){
            printOutBoard();
            
            while(!isValid){
                //Player 1 input
                System.out.println("Player 1, it is your turn!");
                System.out.println("What is the row and col. of the piece you want to move? Please type in the row first, hit enter, and then the col.");
                current_x = in.nextInt() - one;
                current_y = in.nextInt() - one;

                System.out.println("Where would you like to move that piece? Please type in the row first, hit enter, and then the col.");
                next_x = in.nextInt() - one;
                next_y = in.nextInt() - one;
                //Moves piece if it is a valid move
                movePiece(current_x, current_y, next_x, next_y); 
            }isValid=false;
            
            printOutBoard();
            
            while(!isValid){ 
                //Player 2 input
                System.out.println("Player 2, it is your turn!");
                System.out.println("What is the row and col. of the piece you want to move? Please type in the row first, hit enter, and then the col.");
                current_x = in.nextInt() - one;
                current_y = in.nextInt() - one;

                System.out.println("Where would you like to move that piece? Please type in the row first, hit enter, and then the col.");
                next_x = in.nextInt() - one;
                next_y = in.nextInt() - one;

                //Moves piece if it is a valid move
                movePiece(current_x, current_y, next_x, next_y);
            }isValid=false;
            
        } //End of Game Loop     
    } //End of playGame
    
    
    //--------------------------------------------------------------------------
    //printOutBoard prints out an 8x8 board containing the checkers pieces and 
    //also prints out 8x8 numbering to better help find the pieces
    //--------------------------------------------------------------------------
    private void printOutBoard(){
        System.out.println("    1   2   3   4   5   6   7   8");
        for(int r = 0; r < 8; r++){
            System.out.println("   --------------------------------");
            System.out.print(r + 1 + " ");
            for(int c = 0; c < 8; c++){
                System.out.print("|");
                
                //Prints out red pieces
                if(pieces[r][c].getPiece().equals("R"))
                    System.out.print(" " + pieces[r][c].getPiece() + " ");
                
                //Prints out black pieces
                if(pieces[r][c].getPiece().equals("B"))
                    System.out.print(" " + pieces[r][c].getPiece() + " ");
                
                //Prints out blank spaces
                if(pieces[r][c].getPiece().equals(" "))
                    System.out.print(" " + pieces[r][c].getPiece() + " ");
                
            } //End of Col. Loop
            System.out.print("|\n");
        } //End of Row Loop
        System.out.println("   --------------------------------\n");
    } //End of printOutBoard
    
    
    //--------------------------------------------------------------------------
    //movePiece takes in 4 ints, 2 being the piece desired to be moved x and y,
    //and also 2 ints which specify, x and y, where the piece is trying to be
    //moved to
    //--------------------------------------------------------------------------
    private void movePiece(int current_x, int current_y, int next_x, int next_y){
        
        //First checks for the color of the piece(Red),
        //Then checks if it is a valid move or a valid jump
        if(pieces[current_x][current_y].getPiece().equals("R")
                && (validMove(current_x, current_y, next_x, next_y) || validJump(current_x, current_y, next_x, next_y))){
            pieces[next_x][next_y].updatePiece(1);
            pieces[current_x][current_y].updatePiece(3);
            isValid=true;
        }
        
        //First checks for the color of the piece(Black),
        //Then checks if it is a valid move or a valid jump
        else if(pieces[current_x][current_y].getPiece().equals("B")
                && (validMove(current_x, current_y, next_x, next_y) || validJump(current_x, current_y, next_x, next_y))){
            pieces[next_x][next_y].updatePiece(2);
            pieces[current_x][current_y].updatePiece(3);
            isValid=true;
        }
        
        //A blank space was selected to be moved
        else if(pieces[current_x][current_y].getPiece().equals(" "))
            System.out.println("There is not a piece there to be moved.\n");
        
        //An invalid move was made, not diagonal
        else if(!validMove(current_x, current_y, next_x, next_y))
            System.out.println("Moves must be made diagonally and one spot away.\n");
        
        //An invalid jump was made
        else if(!validJump(current_x, current_y, next_x, next_y))
            System.out.println("An invalid jump was made.\n");
        
        //A piece was in a space to be moved to
        else if(occupiedSpace(next_x, next_y))
            System.out.println("There is a piece occupying that position!\n");   
    } //End of movePiece
    

    //--------------------------------------------------------------------------
    //movePiece takes in 4 ints, 2 being the piece desired to be moved x and y,
    //and also 2 ints which specify, x and y, where the piece is trying to be
    //moved to and checks:
    //What color and piece is trying to be moved,
    //If the move is one diagonal spot away,
    //And if there is already a piece there
    //--------------------------------------------------------------------------
    private Boolean validMove(int current_x, int current_y, int next_x, int next_y){

        //Checks if the x position of the move is 1 spot to the right or left
        if(current_x - next_x == -1 || current_x - next_x == 1 && current_x - 1 > 0 && current_x + 1 < 9){
            
            //First checks if the piece to be moved is a red piece
            //Then checks if the y position of the move is 1 spot down, for red
            //Lastly checks to see if the position to be moved to is occupied
            if(pieces[current_x][current_y].getPiece().equals("R") 
                    && (current_y - next_y == -1 || current_y - next_y == 1) 
                    && !occupiedSpace(next_x, next_y))
                return true;
            
            //First checks if the piece to be moved is a black piece
            //Then checks if the y position of the move is 1 spot up, for black
            //Lastly checks to see if the position to be moved to is occupied
            else if(pieces[current_x][current_y].getPiece().equals("B") 
                    && (current_y - next_y == -1 || current_y - next_y == 1) 
                    && !occupiedSpace(next_x, next_y))
                return true;
            
            //Otherwise returns false, the Y position is not 1 spot up/down or 
            //a piece currently occupies the spot
            else
                return false;
        }
        
        //Otherwise returns false, the X position is not 1 spot left/right or 
        //a piece currently occupies the spot 
        else
            return false;
        
    } //End of validMove
    
    
    //--------------------------------------------------------------------------
    //validJump takes in 4 ints, 2 being the piece desired to be moved x and y,
    //and also 2 ints which specify, x and y, where the piece is trying to be
    //moved to. 
    //First where the piece will be jumping to, 
    //then checks what piece is going to be moved, 
    //then checks that the piece to be jumped is an opponents piece, 
    //and then checks to make sure the position to be jumped to is unoccupied
    //--------------------------------------------------------------------------
    private Boolean validJump(int current_x, int current_y, int next_x, int next_y){
        
        //Checks if the x position of the jump is 2 spots to the right or left
        if((current_x - next_x == -2 || current_x - next_x == 2) && current_x - 2 > 0 && current_x + 2 < 9){

            //First checks if the piece to be moved is a red piece
            //Then checks if the piece to be jumped, left diagonal, is an opponents piece
            //Lastly checks to see if the position to be moved to is occupied
            if(pieces[current_x][current_y].getPiece().equals("R")
                    && (pieces[current_x - 1][current_y + 1].getPiece().equals("B"))
                    && !occupiedSpace(next_x, next_y)){
                pieces[current_x - 1][current_y + 1].updatePiece(3);
                totBlackPieces--;
                return true;
            }
            
            //First checks if the piece to be moved is a red piece
            //Then checks if the piece to be jumped, right diagonal, is an opponents piece
            //Lastly checks to see if the position to be moved to is occupied
            else if(pieces[current_x][current_y].getPiece().equals("R")
                    && (pieces[current_x + 1][current_y + 1].getPiece().equals("B"))
                    && !occupiedSpace(next_x, next_y)){
                pieces[current_x + 1][current_y + 1].updatePiece(3);
                totBlackPieces--;
                return true;
            }
            
            //First checks if the piece to be moved is a black piece
            //Then checks if the piece to be jumped, left diagonal, is an opponents piece
            //Lastly checks to see if the position to be moved to is occupied
            else if(pieces[current_x][current_y].getPiece().equals("B")
                    && (pieces[current_x - 1][current_y - 1].getPiece().equals("R"))
                    && !occupiedSpace(next_x, next_y)){
                pieces[current_x - 1][current_y - 1].updatePiece(3);
                totRedPieces--;
                return true;
            }
            
            //First checks if the piece to be moved is a black piece
            //Then checks if the piece to be jumped, left diagonal, is an opponents piece
            //Lastly checks to see if the position to be moved to is occupied
            else if(pieces[current_x][current_y].getPiece().equals("B")
                    && (pieces[current_x + 1][current_y - 1].getPiece().equals("R"))
                    && !occupiedSpace(next_x, next_y)){
                pieces[current_x + 1][current_y - 1].updatePiece(3);
                totRedPieces--;
                return true;
            }
            
            //Otherwise returns false, the Y position is not 2 spots up/down or 
            //a piece currently occupies the spot
            else
                return false;    
        }
        
        //Otherwise returns false, the X position is not 2 spots up/down or 
        //a piece currently occupies the spot
            else
                return false;
        
    } //End of validJump
    
    
    //--------------------------------------------------------------------------
    //occupiedSpace checks the position to be moved to, and if it contains a " "
    //the position is a valid space to move to, otherwise it contains a piece and
    //is not valid
    //--------------------------------------------------------------------------
    private Boolean occupiedSpace(int next_x, int next_y){
        
        //If the position to be moved to contains a " " then the space is blank 
        //and is able to be moved to
        if(pieces[next_x][next_y].getPiece().equals(" "))
            return false;
        
        //Otherwise the position is filled with a piece and cannot be moved onto
        else
            return true;
    } //End of occupiedSpace
    
} //End of Board Class
