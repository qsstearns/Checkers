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
    //there are not yet pieces.
    //--------------------------------------------------------------------------
    public Board(){
        
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                pieces[r][c] = new Pieces();
                if(r < 2)
                    pieces[r][c].updatePiece(1); // Red Piece Initialization
                else if(r > 5) 
                    pieces[r][c].updatePiece(2); // Black Piece Initialization
                else 
                    pieces[r][c].updatePiece(3); // Blank Spot Initialization
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
                next_move,
                one = 1;
        
        System.out.println("Player 1 you are the R pieces, Player 2 you are the B pieces\n");
        
        while(totRedPieces != 0 || totBlackPieces != 0){
            
            while(!isValid){
                printOutBoard();
                //Player 1 input
                System.out.println("Player 1, it is your turn!");
                System.out.println("What is the row and col. of the piece you want to move? Please type in the row first, hit enter, and then the col.");
                current_x = in.nextInt() - one;
                current_y = in.nextInt() - one;
                
                if(pieces[current_x][current_y].getPiece().equals("R") || pieces[current_x][current_y].getPiece().equals("KR")){
                    userAssistance(current_x, current_y);
                    System.out.println("Which number would you like to move to? ");
                    next_move = in.nextInt();
                    
                    movePiece(current_x, current_y, next_move);
                }
                
                clearUserAssistance();
                next_move = 0;
                checkKing();
            }isValid=false; //End of isValid while loop
            
            printOutBoard();
            
            while(!isValid){ 
                //Player 2 input
                System.out.println("Player 2, it is your turn!");
                System.out.println("What is the row and col. of the piece you want to move? Please type in the row first, hit enter, and then the col.");
                current_x = in.nextInt() - one;
                current_y = in.nextInt() - one;

                if(pieces[current_x][current_y].getPiece().equals("B") || pieces[current_x][current_y].getPiece().equals("KB")){
                    userAssistance(current_x, current_y);
                    System.out.println("Which number would you like to move to? ");
                    next_move = in.nextInt();
                    
                    movePiece(current_x, current_y, next_move);
                }
                
                
                clearUserAssistance();
                next_move = 0;
                checkKing();
            }isValid=false; //End of isValid while loop
            
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
                if(pieces[r][c].getPiece().equals("R") || pieces[r][c].getPiece().equals("KR"))
                    System.out.print(" " + pieces[r][c].getPiece() + " ");
                
                //Prints out black pieces
                if(pieces[r][c].getPiece().equals("B") || pieces[r][c].getPiece().equals("KB"))
                    System.out.print(" " + pieces[r][c].getPiece() + " ");
                
                //Prints out blank spaces
                if(pieces[r][c].getPiece().equals(" "))
                    System.out.print(" " + pieces[r][c].getPiece() + " ");
                
                //Prints out numbers for userAssistance
                if(pieces[r][c].getPiece().equals("1") || pieces[r][c].getPiece().equals("2")
                        || pieces[r][c].getPiece().equals("3") || pieces[r][c].getPiece().equals("4"))
                    System.out.print(" " + pieces[r][c].getPiece() + " ");
                
            } //End of Col. Loop
            System.out.print("|\n");
        } //End of Row Loop
        System.out.println("   --------------------------------\n");
    } //End of printOutBoard
    
    //--------------------------------------------------------------------------
    //clearUserASsistance looks over the entire board and clears any numbers that
    //are on the board as result of userAssistance method (1,2,3,4)
    //--------------------------------------------------------------------------
    private void clearUserAssistance(){
        
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                
                if(!pieces[r][c].getPiece().equals("R") && !pieces[r][c].getPiece().equals("B")
                    && !pieces[r][c].getPiece().equals("KR") && !pieces[r][c].getPiece().equals("KB"))
                    pieces[r][c].updatePiece(3);
            }
        }
    }
    
    //--------------------------------------------------------------------------
    //movePiece takes in 4 ints, 2 being the piece desired to be moved x and y,
    //and also 2 ints which specify, x and y, where the piece is trying to be
    //moved to
    //--------------------------------------------------------------------------
    private void movePiece(int current_x, int current_y, int next_move){
        //First checks for the color of the piece(Red),
        //Then checks if it is a valid move or a valid jump
        if(!pieces[current_x][current_y].getPiece().equals("B") && !pieces[current_x][current_y].getPiece().equals(" ")){
            
            if((current_y + 1 < 8 && current_x + 1 < 8) && (pieces[current_x + 1][current_y + 1].getPiece().equals(Integer.toString(next_move))) ){
                if(pieces[current_x][current_y].getKing())
                    moveKing(current_x, current_y, current_x + 1, current_y + 1);
                else
                    pieces[current_x + 1][current_y + 1].updatePiece(1);
                pieces[current_x][current_y].updatePiece(3);
                
                clearUserAssistance();
                
                isValid=true;
            }
            
            else if((current_y - 1 >= 0 && current_x + 1 < 8) && pieces[current_x + 1][current_y - 1].getPiece().equals(Integer.toString(next_move))){
                if(pieces[current_x][current_y].getKing())
                    moveKing(current_x, current_y, current_x + 1, current_y - 1);
                else
                    pieces[current_x + 1][current_y - 1].updatePiece(1);
                pieces[current_x][current_y].updatePiece(3);
                
                clearUserAssistance();
                
                isValid=true;
            }
            
            else if((current_y + 2 < 8 && current_x + 2 < 8) && (pieces[current_x + 2][current_y + 2].getPiece().equals(Integer.toString(next_move))) ){
                if(pieces[current_x][current_y].getKing())
                    moveKing(current_x, current_y, current_x + 2, current_y + 2);
                else
                    pieces[current_x + 2][current_y + 2].updatePiece(1);
                pieces[current_x][current_y].updatePiece(3);
                pieces[current_x + 1][current_y + 1].updatePiece(3);
                totBlackPieces--;
                
                clearUserAssistance();
                
                isValid=true;
            }
            
            else if((current_y - 2 >= 0 && current_x + 2 < 8) && pieces[current_x + 2][current_y - 2].getPiece().equals(Integer.toString(next_move))){
                if(pieces[current_x][current_y].getKing())
                    moveKing(current_x, current_y, current_x + 2, current_y - 2);
                else
                    pieces[current_x + 2][current_y - 2].updatePiece(1);
                pieces[current_x][current_y].updatePiece(3);
                pieces[current_x + 1][current_y - 1].updatePiece(3);
                totBlackPieces--;
                
                clearUserAssistance();
                
                isValid=true;
            }

        }
        
        //First checks for the color of the piece(Black),
        //Then checks if it is a valid move or a valid jump
        if(!pieces[current_x][current_y].getPiece().equals("R") && !pieces[current_x][current_y].getPiece().equals(" ")){

            if((current_y + 1 < 8 && current_x - 1 < 8) && pieces[current_x - 1][current_y + 1].getPiece().equals(Integer.toString(next_move))){
                if(pieces[current_x][current_y].getKing())
                    moveKing(current_x, current_y, current_x - 1, current_y + 1);
                else
                    pieces[current_x - 1][current_y + 1].updatePiece(2);
                pieces[current_x][current_y].updatePiece(3);
                
                clearUserAssistance();
                
                isValid=true;
                return;
            }
            
            else if((current_y - 1 >= 0 && current_x - 1 < 8) && pieces[current_x - 1][current_y - 1].getPiece().equals(Integer.toString(next_move))){
                if(pieces[current_x][current_y].getKing())
                    moveKing(current_x, current_y, current_x - 1, current_y - 1);
                else
                    pieces[current_x - 1][current_y - 1].updatePiece(2);
                pieces[current_x][current_y].updatePiece(3);
                
                clearUserAssistance();
                
                isValid=true;
            }
            
            else if((current_y + 2 < 8 && current_x - 2 < 8) && pieces[current_x - 2][current_y + 2].getPiece().equals(Integer.toString(next_move))){
                if(pieces[current_x][current_y].getKing())
                    moveKing(current_x, current_y, current_x - 2, current_y + 2);
                else
                    pieces[current_x - 2][current_y + 2].updatePiece(2);
                pieces[current_x][current_y].updatePiece(3);
                pieces[current_x - 1][current_y + 1].updatePiece(3);
                totRedPieces--;
                
                clearUserAssistance();
                
                isValid=true;
                return;
            }
            
            else if((current_y - 2 >= 0 && current_x - 2 < 8) && pieces[current_x - 2][current_y - 2].getPiece().equals(Integer.toString(next_move))){
                if(pieces[current_x][current_y].getKing())
                    moveKing(current_x, current_y, current_x - 2, current_y - 2);
                else
                    pieces[current_x - 2][current_y - 2].updatePiece(2);
                pieces[current_x][current_y].updatePiece(3);
                pieces[current_x - 1][current_y - 1].updatePiece(3);
                totRedPieces--;
                
                clearUserAssistance();
                
                isValid=true;
            }
        }

    } //End of movePiece
    
    
    //--------------------------------------------------------------------------
    //moveKing checks to see whether a piece is a red king or a black king and then
    //changes the position to be moved to accordingly
    //--------------------------------------------------------------------------
    private void moveKing(int current_x, int current_y, int next_x, int next_y){
        if(pieces[current_x][current_y].getPiece().equals("KR"))
            pieces[next_x][next_y].updateKing(1);
        else
            pieces[next_x][next_y].updateKing(2);
    }
    

    //--------------------------------------------------------------------------
    //validMove takes in 4 ints, 2 being the piece desired to be moved x and y,
    //and also 2 ints which specify, x and y, where the piece is trying to be
    //moved to and checks:
    //What color and piece is trying to be moved,
    //If the move is one diagonal spot away,
    //And if there is already a piece there
    //--------------------------------------------------------------------------
    private Boolean validMove(int current_x, int current_y, int next_x, int next_y){

        //Checks if the x position of the move is 1 spot to the right or left
        if((current_x - next_x == -1 || current_x - next_x == 1) && (next_x >= 0 && next_x < 8) && (next_y >= 0 && next_y < 8)){

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
            
            else if(pieces[current_x][current_y].getKing()
                    && (current_y - next_y == -1 || current_y - next_y == 1 || current_y - next_y == -1 || current_y - next_y == 1)
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
        if((current_x - next_x == -2 || current_x - next_x == 2) && (next_x >= 0 && next_x < 8) && (next_y >= 0 && next_y < 8)){

            //First checks if the piece to be moved is a red piece
            //Then checks if the piece to be jumped, left diagonal, is an opponents piece
            //Lastly checks to see if the position to be moved to is occupied
            if((next_y >= 0 && next_y < 8 && next_x >= 0 && next_x < 8) && !pieces[current_x][current_y].getPiece().equals("B") && !pieces[current_x][current_y].getPiece().equals(" ")
                    && (pieces[current_x + 1][current_y - 1].getPiece().equals("B"))
                    && !occupiedSpace(next_x, next_y)){
                return true;
            }
            
            //First checks if the piece to be moved is a red piece
            //Then checks if the piece to be jumped, right diagonal, is an opponents piece
            //Lastly checks to see if the position to be moved to is occupied
            else if((next_y >= 0 && next_y < 8 && next_x >= 0 && next_x < 8) && !pieces[current_x][current_y].getPiece().equals("B") && !pieces[current_x][current_y].getPiece().equals(" ")
                    && (pieces[current_x + 1][current_y + 1].getPiece().equals("B"))
                    && !occupiedSpace(next_x, next_y)){
                return true;
            }
            
            //First checks if the piece to be moved is a black piece
            //Then checks if the piece to be jumped, left diagonal, is an opponents piece
            //Lastly checks to see if the position to be moved to is occupied
            else if((next_y >= 0 && next_y < 8 && next_x >= 0 && next_x < 8) && !pieces[current_x][current_y].getPiece().equals("R") && !pieces[current_x][current_y].getPiece().equals(" ")
                    && (pieces[current_x - 1][current_y - 1].getPiece().equals("R"))
                    && !occupiedSpace(next_x, next_y)){
                return true;
            }
            
            //First checks if the piece to be moved is a black piece
            //Then checks if the piece to be jumped, right diagonal, is an opponents piece
            //Lastly checks to see if the position to be moved to is occupied
            else if((next_y >= 0 && next_y < 8 && next_x >= 0 && next_x < 8) && !pieces[current_x][current_y].getPiece().equals("R") && !pieces[current_x][current_y].getPiece().equals(" ")
                    && (pieces[current_x - 1][current_y + 1].getPiece().equals("R"))
                    && !occupiedSpace(next_x, next_y)){
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
        if(next_y >= 0 && next_y < 8 && next_x >= 0 && next_y < 8){
            if(!pieces[next_x][next_y].getPiece().equals("R") && !pieces[next_x][next_y].getPiece().equals("B"))
                return false;
            
            //Otherwise the position is filled with a piece and cannot be moved onto
            else
                return true;
        }
        
        else
            return true;
    } //End of occupiedSpace
    
    
    //--------------------------------------------------------------------------
    //checkKing checks all valid spaces that could change a regular piece to a king, 
    //a red piece has to occupy any space in [7][0-7] to be turned into a king,
    //a black piece has to occupy any space in [0][0-7] to be turned into a king.
    //--------------------------------------------------------------------------
    private void checkKing(){
        
        //checks all columns [0-7]
        for(int c = 0; c < 8; c++){
            
            //Checks [0][c] for any black piece, and updates the piece if there is one
            if(pieces[0][c].getPiece().equals("B"))
                pieces[0][c].updateKing(2);
            
            //Checks [7][c] for any red piece, and updates the piece if there is one
            else if(pieces[7][c].getPiece().equals("R"))
                pieces[7][c].updateKing(1);
        } //End of col. for loop 
    } //End of checkKing
    
    
    private void userAssistance(int current_x, int current_y){
        int validMoveCounter = 0;
        
        //First checks if the piece to be moved is a red piece
        //Then checks if the spot 1 move down and 1 move to the right
        //If true puts in a number counter to make the movement of a piece easier
        //for the player
        if(!pieces[current_x][current_y].getPiece().equals("B") && !pieces[current_x][current_y].getPiece().equals(" ")
            && validMove(current_x, current_y, current_x + 1, current_y + 1)){
                validMoveCounter++;
                pieces[current_x + 1][current_y + 1].updateMove(validMoveCounter);
        }
        
        else if(!pieces[current_x][current_y].getPiece().equals("B") && !pieces[current_x][current_y].getPiece().equals(" ")
            && validJump(current_x, current_y, current_x + 2, current_y + 2)){
                validMoveCounter++;
                pieces[current_x + 2][current_y + 2].updateMove(validMoveCounter);
        }
        
        //First checks if the piece to be moved is a red piece
        //Then checks if the he spot 1 move down and 1 move to the left
        //If true puts in a number counter to make the movement of a piece easier
        //for the player
        if(!pieces[current_x][current_y].getPiece().equals("B") && !pieces[current_x][current_y].getPiece().equals(" ") 
            && validMove(current_x, current_y, current_x + 1, current_y - 1)){
                validMoveCounter++;
                pieces[current_x + 1][current_y - 1].updateMove(validMoveCounter);
        }
        
        else if(!pieces[current_x][current_y].getPiece().equals("B") && !pieces[current_x][current_y].getPiece().equals(" ")
            && validJump(current_x, current_y, current_x + 2, current_y - 2)){
                validMoveCounter++;
                pieces[current_x + 2][current_y - 2].updateMove(validMoveCounter);
        }
        
        //First checks if the piece to be moved is a black piece
        //Then checks if the spot 1 move up and 1 move to the right
        //If true puts in a number counter to make the movement of a piece easier
        //for the player
        if(!pieces[current_x][current_y].getPiece().equals("R") && !pieces[current_x][current_y].getPiece().equals(" ")
            && validMove(current_x, current_y, current_x - 1, current_y + 1)){
                validMoveCounter++;
                pieces[current_x - 1][current_y + 1].updateMove(validMoveCounter);
        }
        
        else if(!pieces[current_x][current_y].getPiece().equals("R") && !pieces[current_x][current_y].getPiece().equals(" ")
            && validJump(current_x, current_y, current_x - 2, current_y + 2)){
                validMoveCounter++;
                pieces[current_x - 2][current_y + 2].updateMove(validMoveCounter);
        }
        
        //First checks if the piece to be moved is a black piece
        //Then checks if the he spot 1 move up and 1 move to the left
        //If true puts in a number counter to make the movement of a piece easier
        //for the player
        if(!pieces[current_x][current_y].getPiece().equals("R") && !pieces[current_x][current_y].getPiece().equals(" ")
            && validMove(current_x, current_y, current_x - 1, current_y - 1)){
                validMoveCounter++;
                pieces[current_x - 1][current_y - 1].updateMove(validMoveCounter);
        }

        else if(!pieces[current_x][current_y].getPiece().equals("R") && !pieces[current_x][current_y].getPiece().equals(" ")
            && validJump(current_x, current_y, current_x - 2, current_y - 2)){
                validMoveCounter++;
                pieces[current_x - 2][current_y - 2].updateMove(validMoveCounter);
        }
        
        validMoveCounter = 0;
        
        printOutBoard();
    } //End of userAssistance 
    
} //End of Board Class
