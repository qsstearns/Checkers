/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers_final_project;

/**
 *
 * @author Quintin Stearns
 */
public class Pieces {
    String piece;   
    Boolean isKing = false;
    
    public void updatePiece(int tPiece){
        switch(tPiece){
            case 1: piece = "R";
                break;
            
            case 2: piece = "B";
                break;
                
            case 3: piece = " ";
                break;
        }
    }
    
    
    public void updateKing(int tKing){
        switch(tKing){
            case 1: piece = "KR";
                    isKing = true;
                break;
                
            case 2: piece = "KB";
                    isKing = true;
                break;
        }
    }
    
    
    public Boolean getKing(){
        return isKing;
    }
    
    
    public String getPiece(){
            return piece;
    }
    
}
