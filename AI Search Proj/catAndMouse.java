import java.util.ArrayList;

public class catAndMouse {

    public static void main(String args[]) {
        
        //Later, we will also allow us to input the size of the board,
        //the start and goal of the mouse, and the cat positions.
        //For now, we will keep it simple and use a 2 by 2 board until we get the search
        //algorithms right.

        Board b = new Board();
        UninformedMouse mouse = new UninformedMouse(b);

        ArrayList path;

        b.print();
        path = mouse.depthFirstSearch(0, 3);
        mouse.printPath(path);

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

    }

}

//AI Mouse Algorithms***************************************************************************
class Mouse {

    Board b;
    Boolean[] visited;

    Mouse(Board b) {

        this.b = b;
        visited = new Boolean[2 * 2];

        for(int i = 0; i < 2 * 2; i++) {
            visited[i] = false;
        }

    }

    Boolean isCat(int position) {

        for(int i = 0; i < b.cat.size(); i++)
            if(position == (int)b.cat.get(i))
                return true;

        return false;
    }

}

class UninformedMouse extends Mouse {

    UninformedMouse(Board b) {

        super(b);

    }

    ArrayList depthFirstSearch(int current, int goal) {

        ArrayList path = null;

        visited[current] = true;
        ArrayList neighbors = b.moves[current];

        for(int i = 0; i < neighbors.size(); i++)
            if((int)neighbors.get(i) != goal && !visited[(int)neighbors.get(i)] && !isCat((int)neighbors.get(i)))
                path = depthFirstSearch((int)neighbors.get(i), goal);
            else if ((int)neighbors.get(i) == goal) {
                path = new ArrayList<Integer>();
                path.add(goal);
                break;
            }

        if(path == null) {
            path = new ArrayList<Integer>();
            path.add(-1);
        }

        path.add(0, current);

        return path;
    }

    void printPath(ArrayList path) {

        System.out.print("Mouse: ");
        for(int i = 0; i < path.size() - 1; i++)
            System.out.print(path.get(i) + " -> ");
        System.out.print(path.get(path.size() - 1) + "\n");

        System.out.print('\n');
        if((int)path.get(path.size() - 1) == -1)
            System.out.println("The mouse could not make it to the end alive!");
        else
            System.out.println("The mouse made it to the goal safely.");

    }

}

class InformedMouse extends Mouse {

    InformedMouse(Board b) {

        super(b);

    }
    
    
    for(int i = 0; i < 2; i++)
        for(int j = 0; j < 2; j++) {
        	if(heuristics[2 * i + j] != isCat)
        	{
        		heuristics[2 * i + j] = new ArrayList<Integer>();
        		heuristics[2 * i + j] = heuristic(i, j, 1, 1);
        	}
        }
    
    ArrayList aStarSearch(int current, int goal)
    {
    	ArrayList path = null;

        visited[current] = true;
        
        ArrayList neighbors = b.moves[current];
        
        int j = 0;
        int lowest = 99;
        for(int i = 0; i < neighbors.size(); i++)
        {
        	if(lowest > (int)neighbor.get[i] && != goal && != visited[(int)neighbors.get(i)]) 
        	{
        		j = i;
        		int lowest = (int)neighbor.get[i];
        	}
        	else if((int)neighbor.get[i] == goal)
            {
            	path = new ArrayList<Integer>();
                path.add(goal);
                break;
            }
            path = aStarSearch((int)neighbor.get[j], goal);
        }
        
        if(path == null) {
            path = new ArrayList<Integer>();
            path.add(-1);
        }

        path.add(0, current);

        return path;
    }
    
    int heuristic(int currentX, int currentY, int goalX, int goalY)
    {    	
    	int dx = abs(currentX - goalX);
    	int dy = abs(currentY - goalY);
    	int h = dx + dy;
    	return h;
    }
    
    
    
    
    
}