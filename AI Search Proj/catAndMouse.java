public class catAndMouse {

    public static void main(String args[]) {
        
        //Later, we will also allow us to input the size of the board,
        //the start and goal of the mouse, and the cat positions.
        //For now, we will keep it simple and use a 2 by 2 board until we get the search
        //algorithms right.

        Board b = new Board();

        b.position[1][0].hasACat = true;    //This is just temporary. It sets position 2 as a cat for now.
        b.print();

    }

}

class Pos {

    Boolean hasACat = false;
    
    int label;
    int[] move = new int[4];

    Pos(int label) {

        this.label = label;

    }

    void setMoves(int move0, int move1, int move2, int move3) {

        move[0] = move0;
        move[1] = move1;
        move[2] = move2;
        move[3] = move3;

    }
    void setMoves(int move0, int move1, int move2) {

        move[0] = move0;
        move[1] = move1;
        move[2] = move2;
        move[3] = -1;

    }
    void setMoves(int move0, int move1) {

        move[0] = move0;
        move[1] = move1;
        move[2] = -1;
        move[3] = -1;

    }
    void setMoves(int move0) {

        move[0] = move0;
        move[1] = -1;
        move[2] = -1;
        move[3] = -1;

    }

}

class Board {

    Pos[][] position;
    Pos start, goal;
    
    Board() {

        position = new Pos[2][2];

        for(int i = 0; i < 2; i++)
            for(int j = 0; j < 2; j++)
                position[i][j] = new Pos(2 * i + j);

        for(int i = 0; i < 2; i++)
            for(int j = 0; j < 2; j++)
                if(position[i][j] == position[0][0])
                    position[i][j].setMoves(position[i][j + 1].label, position[i + 1][j].label);
                else if(position[i][j] == position[0][1])
                    position[i][j].setMoves(position[i][j - 1].label, position[i + 1][j].label);
                else if(position[i][j] == position[1][0])
                    position[i][j].setMoves(position[i][j + 1].label, position[i - 1][j].label);
                else if(position[i][j] == position[1][1])
                    position[i][j].setMoves(position[i][j - 1].label, position[i - 1][j].label);
        
        start = position[0][0];
        goal = position[1][1];

    }

    void print() {
        
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++)
                System.out.print(position[i][j].label + "   ");
            System.out.print('\n');
        }

        System.out.print("Cats: ");
        for(int i = 0; i < 2; i++)
            for(int j = 0; j < 2; j++)
                if(position[i][j].hasACat)
                    System.out.print(position[i][j].label + "   ");
        System.out.print('\n');

        System.out.print("Mouse: ");
        System.out.print('\n');

    }

}

class UninformedMouse {

    

}

class InformedMouse {



}