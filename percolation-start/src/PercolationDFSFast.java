
public class PercolationDFSFast extends PercolationDFS{
	//Doruk Doganay dsd20
	public PercolationDFSFast(int n) {
		super(n);
		// TODO Auto-generated constructor stub
	}
	//check to see if in bounds, if yes, check if open
	@Override
	public boolean isOpen(int row, int col) {
	    if (! inBounds(row,col)) {
	      throw new IndexOutOfBoundsException("no!");
	    }else{
	    return super.isOpen(row, col);
	}
	}
	//check to see if in bounds, if yes, check if full
	@Override
	public boolean isFull(int row, int col) {
	    if (! inBounds(row,col)) {
	    	throw new IndexOutOfBoundsException("no!");
	    }else{
	    return super.isFull(row, col);
	}
	}
	//check to see if in bounds, if in bounds see if open but no return
	@Override
	public void open(int row, int col) {
	    if (! inBounds(row,col)) {
	    	throw new IndexOutOfBoundsException("no!");
	    }else{
	     super.open(row, col);
	}
	}
	
	@Override
	//check to see if a box is open or not, then do dfs on the surrounding ones
	// if the surrounding boxes are in bounds
	public void updateOnOpen(int row, int col) {
		
		//check to see if the box is on bounds
		if(!inBounds(row,col)){
			throw new IndexOutOfBoundsException("no!");
		}
		
		//do dfs on the neighbors
		if (inBounds(row, col) && isFull(row, col)){
			
			dfs(row-1,col);
			dfs(row+1,col);
			dfs(row,col-1);
			dfs(row, col+1);
			
	}
		// check upper, if in bounds and if full, dfs on neighbors
		if (inBounds(row, col+1) && isFull(row, col+1)){
		
			myGrid[row][col] = FULL;
			dfs(row-1,col);
			dfs(row+1,col);
			dfs(row,col-1);
			
			
	}
		//check right, bounds and full? then dfs neighbors
		if (inBounds(row+1, col) && isFull(row+1, col)){
		
			myGrid[row][col] = FULL;
			dfs(row-1,col);
			
			dfs(row,col-1);
			dfs(row, col+1);
			
		}
		//check bottom, and do dfs on the neighbors, like we did above
		if (inBounds(row, col-1) && isFull(row, col-1)){
		
			myGrid[row][col] = FULL;
			dfs(row-1,col);
			dfs(row+1,col);
			
			dfs(row, col+1);
			
		}
		//check left and dfs neighbors, as we did above
		if (inBounds(row-1, col) && isFull(row-1, col)){
		
			myGrid[row][col] = FULL;
			
			dfs(row+1,col);
			dfs(row,col-1);
			dfs(row, col+1);
			
		}
	}

}
