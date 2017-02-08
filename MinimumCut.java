/*
 * ATN PROJECT 2
 * Title - Implementation of Nagamochi-Ibaraki Algorithm
 * Author - Pulkit Khemka
 * */


public class MinimumCut {
	static int m =40; //starting no of edges
	static int n =22; //number of nodes
	public static void main(String[] args) {
		
		
		//calculating value of Min cut and Critical Edges for different values of M
		while(m<=400)
		{
			Graph g = new Graph(n);
			g.construct(m);
			//g.print();
			System.out.println(" Edges - " +m +","+" Min cut - " +g.getLambda()+","+" Critical Edges - " +getc(g)+","+" Average Degree - " +(2*m/22));
			m = m+5;
			
			
		}		
		
	}
	
	//This function calculates the number of critical nodes
	public static int getc(Graph g){
		int[][] CE = new int[n][n];
		int C =0;
		Graph Gnew = new Graph(n);
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				Gnew.G[i][j] = g.G[i][j];
				CE[i][j]= 0;
			}
		}
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				if(Gnew.G[i][j]>0){
					Gnew.G[i][j]--;
					Gnew.G[j][i]--;
					if(g.getLambda()>Gnew.getLambda()){
						CE[i][j]=g.G[i][j];
						C= C + g.G[i][j];
					}
					Gnew.G[i][j]++;
					Gnew.G[j][i]++;
				}
			}
		}
		/*for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				System.out.print(CE[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();*/
		return C/2;		
	}
}
