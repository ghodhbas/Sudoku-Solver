import java.io.*;
import java.util.*;

/*
 * 
 */
public class Sudoku {

    private int[][] input = new int[9][9]; // numbers in the Sudoku board

    /** the constructor, read the input, call the solver, and print the output */
    public Sudoku(String[] args) {      
        try (BufferedReader br = new BufferedReader(new FileReader("Sudoku.in"))) {

            // read the input file 
            for (int i = 0; i < 9; i++) {
                // read a line and use the space symbol to split it into parts
                String[] inLine = br.readLine().split(" "); 

                // save the number to the 2d-array
                for (int j = 0; j < 9; j++) {
                    input[i][j] = Integer.parseInt(inLine[j]);
                }
            }

            // call solvePuzzle method to solve the problem
            int[][] solution = solvePuzzle(input);
             
            if (solution == null) { // the input had no solution
                System.out.println("No solution found."); 
                return;
            }
            
            // print the result
            String result = "Solution: \n";
            
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    result += solution[i][j] + " ";
                }
                result += "\n"; // end each row with a new line symbol
            }

            PrintWriter writer = new PrintWriter(new File("solution.out"));
            writer.print(result);
            writer.close(); 

        } catch (Exception e) {
            e.printStackTrace();
        } 

    }

    // solve the given puzzle, result a soultion if there is one;
    // result null if there is no solution to the given puzzle
    private int[][] solvePuzzle(int[][] puzzle) {
        
        // create a new puzzle and copy all numbers from the old puzzle to the new puzzle
        /** your code here */
        
        //create a 2D array to store a copy of the puzzle
        int[][] puzzleCopy = new int[9][9];
        
        //copy the puzzle into the copy
        for (int i = 0; i < 9; i++) {
             for (int j = 0; j < 9; j++) {
                   puzzleCopy[i][j] = puzzle[i][j];
             }
        }
        
        
        // find row and col number of the first 0-entry in the old puzzle
        /** your code here */
        
        boolean stop=false; // a boolean variable to track whether the puzzle contains a 0-entry
        int row=0; //a variable to save the position of the row
        int col=0; //a variable to save the position of the column
        
        //go through the copy puzzle to look for any 0-entry value
        for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                  //if there's a 0-entry value break the loops and save the position of this variable
                  if( puzzleCopy[i][j] ==0){
                      stop = true;
                      row = i;
                      col = j;
                      break;
                    }
                }
                if(stop) break;
            }
        
        // if there is no 0-entry left in puzzle, the puzzle is already solved, just return puzzle
        /** your code here */
        
        if(!stop) return puzzleCopy; //if stop is false == the whole table has been searched and didn't stop == no 0-entry values
        
        
        // call the numbersUsed method to compute a boolean array that tells us whether each number 
        // appears in the same row, same column, or the same 3x3 cube as the above 0-entry
        /** your code here */
        
        //create an array to save which values has been used
        boolean[] used = numbersUsed(puzzleCopy, row, col);
        
        // for every number i from 1 to 9
        //    if i-th elment of the boolean array computed above is false
        //      replace the 0-entry in the new puzzle by number i 
        //      recursively call solvePuzzle to solve the new puzzle
        //      if solvePuzzle returns a non-null solution to the new puzzle, return it (if not, the loop continues) 
        /** your code here */
        
        //search the used number array to look for any values that can be used
        for(int i=1; i<=9;i++){
            //if you find one
            if( !used[i]){
                //insert it into the pizzle
                puzzleCopy[row][col]= i;
                //try to solve the rest of the puzzle recursively and save it into a 2D Array
                int[][]solution = solvePuzzle(puzzleCopy);
                
                //if the solution is null continue solvingwith other values of the used number array
                if( solution == null) {
                    continue;
                }else{
                    //if the solution is not null (an actual array) show the solution by returning it
                    return solution;
                }
            }
        }
        
        // all iterations of the loop end without finding a solution, then there is no solution, return null 
        return null; 
    }
    

    // Given a puzzle, the row and col number of an entry, find what numbers appear 
    // in the same row, same column, or the same 3x3 cube of the puzzle as this entry. 
    // Return a boolean array of size 10, the n-th element of the array is true if and only if
    // the nunmber n appears in the same row, same column, or the same 3x3 cube of the puzzle
    private boolean[] numbersUsed(int[][] puzzle, int row, int col) {
        // create a boolean array to record whether each number appears
        // in the same row, same column, or the same 3x3 cube
        boolean[] used = new boolean[10];

        
        
        // for each cell in the same row
        //   get int value in the cell
        //   set used[value] to true
        /** your code here */
        
        //go through the different columns in the same row
        for(int j=0;j<9;j++){
            used[puzzle[row][j]]=true; //set the values found to true in the numbers used array
        }
        
        // for each cell in the same column
        //   get int value in the cell
        //   set used[value] to true
        /** your code here */
        
         //go through the different rows in the same column
        for(int i=0;i<9;i++){
            used[puzzle[i][col]]=true; //set the values found to true in the numbers used array
        }
        
        
        // find which 3x3 cube the given entry belongs to
        /** your code here */
        
        int iStart = (row/3)*3; //save the start row of the 3x3cube
        int iEnd = iStart+3; //save the end row of the 3x3cube
        
        int jStart = (col/3)*3; //save the start column of the 3x3cube
        int jEnd = jStart+3; //save the end column of the 3x3cube
        
        // for each cell in this 3x3 cube 
        //   get int value in the cell
        //   set used[value] to true
        /** your code here */
        //go through each ceall in the 3x3 cube
        for(int i= iStart; i<iEnd;i++){
            for(int j= jStart ; j< jEnd;  j++){
                used[puzzle [i][j]]=true; //set the values found to true in the numbers used array
            }
        }
        
        // uncomment the following line to print the contents in the array used
        //System.out.println("____________________________");
        //System.out.println(Arrays.toString(used));
      
        return used;
    }

    
    /** the main method */
    public static void main(String[] args) {
        new Sudoku(args);
    }

}