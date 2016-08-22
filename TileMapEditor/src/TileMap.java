
public class TileMap {
	int [][]	tileIds;
	public int[][] getTileIds(){
		return tileIds;
	}
	public TileMap(int height, int width){
		tileIds=new int[height][width];
	}
	public void setTileId(int row, int column, int id){
		tileIds[row][column]=id;
	}
	public int getTileId(int row, int column){
		return tileIds[row][column];
	}
	public void setAllIds(int[][] ids){
		tileIds=ids;
	}
	
}
