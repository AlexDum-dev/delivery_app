package algorithm.tsp;

public class RunTSP {
	public static void main(String[] args) {
		TSP tsp = new TSP1();
		for (int nbVertices = 8; nbVertices <= 16; nbVertices += 2){
			System.out.println("Graphs with "+nbVertices+" vertices:");
			
			
			int[][] costs = new int[nbVertices][nbVertices];
			for (int i = 0; i<costs[0].length; i++) {
				for (int j = 0; j<costs[i].length; j++) {
					costs[i][j] = (int) (Math.random()*10);
					if (i==j) {
						costs[i][j] = -1;
					}
				}
			}
			Graph g = new CompleteGraph(5);
			long startTime = System.currentTimeMillis();
			tsp.searchSolution(20000, g);
			System.out.print("Solution of cost "+tsp.getSolutionCost()+" found in "
					+(System.currentTimeMillis() - startTime)+"ms : ");
			for (int i=0; i<nbVertices; i++)
				System.out.print(tsp.getSolution(i)+" ");
			System.out.println("0");
		}
	}

}
