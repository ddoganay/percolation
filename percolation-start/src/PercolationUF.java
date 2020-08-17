
public class PercolationUF implements IPercolate{
	//Doruk Doganay dsd20
	boolean[][] myGrid;
	
	IUnionFind myFinder;
	
	int open = 0;
	
	private final int VTOP;
	private final int VBOTTOM;
	//create a PercolationUF class object, initializw variables and grid etc.
	public PercolationUF(int n, IUnionFind x) {
		myGrid= new boolean[n][n];
		x.initialize(n*n+2);
		myFinder = x;
		VTOP = n*n;
		VBOTTOM = n*n+1;
	}
	//check to see if in bounds, copied from PercolationDFS
	protected boolean inBounds(int row, int col) {
		if (row < 0 || row >= myGrid.length) return false;
		if (col < 0 || col >= myGrid[0].length) return false;
		return true;
	}
	//check if opened before
	@Override
	public void open(int row, int col) {
		if (!inBounds(row,col)){ 
			throw new IndexOutOfBoundsException();
		}
		else {
			if (myGrid[row][col] != false)
				return;
			open += 1;
			myGrid[row][col] = true;
			updateOnOpen(row,col);
		}
	}
	//check if in bounds, and if the box is open
	@Override
	public boolean isOpen(int row, int col) {
	    if (! inBounds(row,col)) {
	      throw new IndexOutOfBoundsException("no!");
	    }else{
	    return myGrid[row][col];
	}
	}
	//check if in bounds and if the box is full
	@Override
	public boolean isFull(int row, int col) {
		if (! inBounds(row,col)) {
		      throw new IndexOutOfBoundsException("no!");
		    }else{
		    return myFinder.connected(VTOP,getIndex(row, col));
		    }
	}
	//check to see if the system percolates, bottom and top rows connect?
	@Override
	public boolean percolates() {
		return myFinder.connected(VTOP,VBOTTOM);
	}
	//we counted number of opens above, this method just returns their number
	@Override
	public int numberOfOpenSites() {
		return open;
	}
	
	//helper method to get the value (count) of the box
	public int getIndex(int row, int col) {
		int count = 0;
		count = myGrid.length*row + col;
		return count;
	}
	//check cases (boxes) and update the system accordingly
	public void updateOnOpen(int row, int col) {
		
		if(row == 0){
			myFinder.union(VTOP, getIndex(row,col));
		}
		if(col+1 < myGrid.length && myGrid[row][col + 1]){
			myFinder.union(getIndex(row,col), getIndex(row, col+1));
		}
		if(col-1 >= 0 && myGrid[row][col - 1]){
			myFinder.union(getIndex(row,col), getIndex(row, col-1));
		}
		if(row+1<myGrid.length && myGrid[row+1][col]){
			myFinder.union(getIndex(row,col), getIndex(row+1, col));
		}
		if(row >0 && myGrid[row -1][col]){
			myFinder.union(getIndex(row,col), getIndex(row-1, col));
		}

		if(row+1==myGrid.length){
			myFinder.union(getIndex(row,col), VBOTTOM);
		}
	}


}