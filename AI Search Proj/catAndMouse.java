import java.util.Scanner;
import java.util.ArrayList;

public class catAndMouse {

    public static void main(String args[]) {
        
        //Later, we will also allow us to input the size of the board,
        //the start and goal of the mouse, and the cat positions.
        //For now, we will keep it simple and use a 2 by 2 board until we get the search
        //algorithms right.

        Scanner reader = new Scanner(System.in);
        int length;
        System.out.print("Enter the length of the board: ");
        length = reader.nextInt();
        System.out.print('\n');
        Board b = new Board(length);
        b.print();

        int start, goal;
        System.out.print("Enter the mouse starting position: ");
        start = reader.nextInt();
        System.out.print("Enter the goal position: ");
        goal = reader.nextInt();


        int cat;
        System.out.println("Enter all positions with cats or -1 to stop.");
        System.out.print("Cat: ");
        cat = reader.nextInt();
        while(cat != -1) {
            b.setCat(cat);
            System.out.print("Cat: ");
            cat = reader.nextInt();
        }
        System.out.print('\n');
        reader.close();

        UninformedMouse mouse = new UninformedMouse(b);
        ArrayList path;

        b.print();
        System.out.print("Cat: ");
        for(int i = 0; i < b.cat.size(); i++)
            System.out.print(b.cat.get(i) + "     ");
        System.out.print('\n');

        path = mouse.depthFirstSearch(start, goal);
        mouse.printPath(path);

    }

}

//Cat and Mouse Board Setup**********************************************************************
class Board {

    int length;

    int[][] position;
    ArrayList[] moves;
    
    ArrayList cat = new ArrayList<Integer>();
  
    Board(int length) {

        this.length = length;

        position = new int[length][length];
        moves = new ArrayList[length * length];

        for(int i = 0; i < length; i++)
            for(int j = 0; j < length; j++)
                position[i][j] = length * i + j;

        for(int i = 0; i < length; i++)
            for(int j = 0; j < length; j++) {
                moves[length * i + j] = new ArrayList<Integer>();
                try { moves[length * i + j].add(position[i][j - 1]); } catch(Exception e) {};
                try { moves[length * i + j].add(position[i][j + 1]); } catch(Exception e) {};
                try { moves[length * i + j].add(position[i - 1][j]); } catch(Exception e) {};
                try { moves[length * i + j].add(position[i + 1][j]); } catch(Exception e) {};
            }
        
    }

    void setCat(int position) {

        cat.add(position);

    }

    void print() {
        
        for(int i = 0; i < length; i++) {
            for(int j = 0; j < length; j++)
                System.out.print(position[i][j] + "   ");
            System.out.print('\n');
        }

    }

}

//AI Mouse Algorithms***************************************************************************
class Mouse {

    Board b;
    Boolean[] visited;

    Mouse(Board b) {

        this.b = b;
        visited = new Boolean[b.length * b.length];

        for(int i = 0; i < b.length * b.length; i++) {
            visited[i] = false;
        }

    }

    Boolean isCat(int position) {

        for(int i = 0; i < b.cat.size(); i++)
            if(position == (int)b.cat.get(i))
                return true;

        return false;
    }

    void printPath(ArrayList path) {

        if(path == null)
            System.out.println("The mouse could not make it to the end alive!");
        else {
            System.out.print("Mouse: ");
            for(int i = 0; i < path.size() - 1; i++)
                System.out.print(path.get(i) + " -> ");
            System.out.print(path.get(path.size() - 1) + "\n");

            System.out.print('\n');
            System.out.println("The mouse made it to the goal safely.");
        }
        System.out.print('\n');

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

        for(int i = 0; i < neighbors.size(); i++) {
            int neighbor = (int)neighbors.get(i);

            if(neighbor != goal && !visited[neighbor] && !isCat(neighbor))
                path = depthFirstSearch(neighbor, goal);
            else if (neighbor == goal) {
                path = new ArrayList<Integer>();
                path.add(goal);
            }
            if(path != null)
                break;
        }

        if(path != null)    
            path.add(0, current);

        return path;
    }

}

class InformedMouse extends Mouse {

    InformedMouse(Board b) {

        super(b);

    }
    
    ArrayList aStarSearch(int start, int goal) {
        
        //Initialize Variables***************************************************
        int[] h = new int[b.length * b.length];

        {
            int goalX, goalY;
            goalX = goal % b.length;
            goalY = goal / b.length;

            initialize(goalX, goalY, h);
        }

        ArrayList path;
        int[] g = new int[b.length * b.length];
        int[] parent = new int[b.length * b.length];
        ArrayList openList = new ArrayList<Integer>();
        ArrayList closedList = new ArrayList<Integer>();

        Boolean found = false;                  //True if goal has been found, false otherwise
        Integer current = start;
        closedList.add(current);
        g[start] = 0;

        //A* Loop******************************************************************
        do {
            int f = 0;
            Integer highestFNode;

            for(int i = 0; i < b.moves[current].size(); i++) {
                Integer node = b.moves[current].get(i);

                if (!closedList.contains(node)) {
                    if(isCat((int)node))
                        closedList.add(node);
                    else {
                        parent[(int)node] = current;
                        openList.add(node);
                    }
                }
            }

            for(int j = 0; j < openList.size(); j++) {
                int item = (int)openList.get(j);

                if(item == goal) {
                    found = true;
                    break;
                }
                else {
                    g[item] = g[parent[item]] + 1;
                
                    if(g[item] + h[item] > f) {
                        f = g[item] + h[item];
                        highestFNode = openList.get(j);
                    }
                }
            }

            if(!found) {
                closedList.add(highestFNode);
                openList.remove(highestFNode);
                current = highestFNode;
            }
        } while (!found && !isOutOfMoves(closedList));        //Bug: stop A* too soon!

        if (found) {
            path = tracePathBack(start, goal, parent);
        }
        else {
            path = null;
        }
        
        return path;
    }

    void initialize(int goalX, int goalY, int[] h) {

        for(int i = 0; i < b.length; i++)
            for(int j = 0; j < b.length; j++) {
                int position = b.length * i + j;

                if(isCat(position))
                    h[position] = 0;
                else
                    h[position] = abs(goalX - j) + abs(goalY - i);      //Manhattan heuristic formula
            }

    }

    Boolean isOutOfMoves(ArrayList closedList) {

        for(int i = 0; i < closedList.size(); i++) {
            Integer closedItem = closedList.get(i);

            if(!isCat((int)closedItem))
                for(int j = 0; j < b.moves[(int)closedItem].size(); j++) {
                    Integer move = b.moves[(int)closedItem].get(j);

                    if(!closedList.contains(move))
                        return false;
                }
        }

        return true;
    }

    ArrayList tracePathBack(int start, int goal, int[] parent) {

        ArrayList path = new ArrayList<Integer>();
        int p;

        path.add(goal);
        p = parent[goal];

        while(p != start) {
            path.add(0, p);
            p = parent[p];
        }
        
        path.add(0, start);

        return path;
    }
    
}