import java.util.Random;

//Class responsible for every new Graph
public class Graph {

    public int[][] G; //Adjacency Matrix of Graph
    public int N; //Number of Nodes
    public int M; //Number of Edges
    public int MAOrder[]; //MA Order
    public Random rand;
    public int x = -1;  //x and y are used to store the last two entries in MA order
    public int y = -1;
    public int lambdaxy; //min cut between x and y
    public int lambda;	//min cut for graph
    boolean disconnected = false;
    
    //constructor used to initialize graph with the input size
    Graph(int N)
    {

		rand = new Random();
    	this.N = N;
    	this.M = 0;
    	G = new int[N][N];
    	MAOrder = new int[N];
    	for(int i=0;i<N;i++){
    		for(int j=0;j<N;j++){
    			G[i][j] = 0;
    		}
    	}
    }
    
    //This function is used to construct the graph with M random edges
    public void construct(int M)
    {
    	this.M = M;
    	for(int i=0;i<M;i++){
    		int p = rand.nextInt(N);
    		int q = rand.nextInt(N);
    		while(p == q){
    			q = rand.nextInt(N);
    		}
    		G[p][q]++; 
    		G[q][p]++;  
    	}  	
    	
    }
    
    //This function is used to print the adjacency matrix
    public void print(){
    	for(int i=0;i<N;i++){
    		for(int j=0;j<N;j++){
    			System.out.print(G[i][j]+ ",");
    		}
    		System.out.println();
    	}
    	System.out.println();
    }
    
    //this function just checks if an array contains an integer
    public static boolean contains(int [] arr, int c,int len){
    	boolean flag = false;
    	for(int i=0;i<len;i++)
    	{
    		if(arr[i] == c){
    			flag = true;
    		}
    	}
    	
    	return flag;
    	
    }
    
    //This function is used to find the MA order
    public void findMAOrder()
    {
    	int k = 0;
    	MAOrder[k] = rand.nextInt(N);k++;
    	while(k<N)
    	{
	    	int max = -1;
	    	int sum = 0;    	
	    	for(int i =0; i<N; i++){
	    		int total = 0;
	    		if(!contains(MAOrder,i,k))
	    		{ 
	    			for(int j = 0;j<k;j++)
	    			{
	    				//System.out.println(MAOrder[j]);
	    				total += G[i][MAOrder[j]];	    				
	    			}
	    		}
	    		if (total>sum)
	    		{
	    			max = i;
	    			sum = total;
	    		}
	    	}
	    	if(max==-1){
	    		disconnected = true;
	    		break;
	    	}
	    	MAOrder[k] = max;k++;
    	}
	    if(!disconnected){	
	    	/*for(int i=0;i<N;i++){
	    		System.out.print(MAOrder[i]+ " ");
	    	}*/
	    	x = MAOrder[N-2];
	    	y = MAOrder[N-1];
	    	lambdaxy =  0;
	    	for(int q=0;q<N;q++){
	    		lambdaxy = lambdaxy + G[y][q];
	    	}
	    }
	    else{
	    	lambdaxy = 0;
	    	lambda = 0;
	    }
    	//System.out.println(lambdaxy);
    }
    
    //this function is used to implement the Algorithm for getting the value of lambda recursively
    public int getLambda(){
    	
    	if(N>2){
			findMAOrder();
			Graph Gxy = getGxy();
			if(!disconnected){
				this.lambda = Math.min(lambdaxy, Gxy.getLambda());
			}
			else{
				lambda = 0;
			}
			return lambda;
    	}
    	else
    	{
    		return G[0][1];
    	}
		
    }
    
    //This function gets the graph my merging x and y nodes
    public Graph getGxy(){
    	if(!disconnected){
		Graph Gnew = new Graph(N-1);
		int [][]Gr = new int[N][N];
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				Gr[i][j] = G[i][j];
			}
		}
		for(int i=0;i<N;i++){
			if(i!=x){
				Gr[x][i] =  Gr[x][i] + Gr[y][i] ;
				Gr[i][x] =  Gr[i][x] + Gr[i][y] ;
			}
		}
		int p=0,q=0;
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				if((i!=y)&&(j!=y)){
					
					Gnew.G[p][q++]= Gr[i][j];
				}
			}
			if(i!=y){p++;q=0;}
		}
    	return Gnew;
    	}
    	else
    		return null;
    }
}
