import java.util.ArrayList;

public class catAndMouse {

    public static void main(String args[]) {
        
        //Later, we will also allow us to input the size of the board,
        //the start and goal of the mouse, and the cat positions.
        //For now, we will keep it simple and use a 2 by 2 board until we get the search
        //algorithms right.

        Board b = new Board();

        b.print();

    }

}

//Cat and Mouse Board Setup**********************************************************************
class Board {

    int[][] position;
    ArrayList[] moves;
    
    ArrayList cat = new ArrayList<Integer>();
  
    Board() {

        position = new int[2][2];
        moves = new ArrayList[2 * 2];

        for(int i = 0; i < 2; i++)
            for(int j = 0; j < 2; j++)
                position[i][j] = 2 * i + j;

        for(int i = 0; i < 2; i++)
            for(int j = 0; j < 2; j++) {
                moves[2 * i + j] = new ArrayList<Integer>();
                try { moves[2 * i + j].add(position[i][j - 1]); } catch(Exception e) {};
                try { moves[2 * i + j].add(position[i][j + 1]); } catch(Exception e) {};
                try { moves[2 * i + j].add(position[i - 1][j]); } catch(Exception e) {};
                try { moves[2 * i + j].add(position[i + 1][j]); } catch(Exception e) {};
            }

        cat.add(2);                     //This is just temporary. It sets position 2 as a cat for now.
        
    }

    void print() {
        
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++)
                System.out.print(position[i][j] + "   ");
            System.out.print('\n');
        }

        System.out.print("Cats: ");
        for(int i = 0; i < cat.size(); i++)
            System.out.print(cat.get(i) + "     ");
        System.out.print('\n');

        System.out.print("Mouse: ");
        System.out.print('\n');

    }

}

//AI Mouse Algorithms***************************************************************************
class Mouse {

    Board b;

    int start, goal;

    Mouse(Board b, int start, int goal) {
        this.b = b;
        this.start = start;
        this.goal = goal;
    }

}

class UninformedMouse extends Mouse {

    UninformedMouse(Board b, int start, int goal) {
        super(b, start, goal);
    }

    

}

class InformedMouse extends Mouse {

    InformedMouse(Board b, int start, int goal) {
        super(b, start, goal);
    }



}