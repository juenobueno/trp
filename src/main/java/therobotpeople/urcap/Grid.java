package therobotpeople.urcap;

import java.awt.Point;

public class Grid {

	//The placement position is based on the top left corner
	//I remove all grids from top left to top right
	
	//When placing, check all the values between top left and top right
	public int x_length;
	public int y_length;
	public int resolution;
	public int offset;
	public int buffer;
	
	public int[][] val;

	
	public Grid(int width, int height) {
		this.x_length = width;
		this.y_length = height;
		
		this.val = new int[width][height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				this.val[i][j] = 0;
			}
		}
		
	}
	
	public boolean set_Object(Point start, int width, int height) {
		if(!check_empty(start,width,height)) {
			return false;
		}
		
		for(int i = start.x; i < start.x+width; i++) {
			for(int j = start.y; j < start.y+height; j++) {
				this.val[i][j] = 1;
			}
		}
		
		return true;
	}
	
	public void clear(Point start, int width, int height) {
		for(int i = start.x; i < start.x+width; i++) {
			for(int j = start.y; j < start.y+height; j++) {
				this.val[i][j] = 0;
			}
		}
	}
	
	public void set(Point start, int width, int height) {
		for(int i = start.x; i < start.x+width; i++) {
			for(int j = start.y; j < start.y+height; j++) {
				this.val[i][j] = 1;
			}
		}
	}
	
	public boolean check_empty(Point start, int width, int height) {
		
		//System.out.println("Empty Check");
		//System.out.println("Starting point is: "+start);
		
		for(int i = start.x; i < start.x+width-1; i++) {
			for(int j = start.y; j < start.y+height-1; j++) {
				if(this.val[i][j] != 0) {
					
					//System.out.println("Failed at: i"+i+",j"+j);
					
					return false;
				}
			}
		}
		return true;
	}
	
	public void print() {
		for(int i = 0; i < this.y_length; i++) {
			for(int j = 0; j < this.x_length; j++) {
				System.out.print(this.val[j][i]);
			}
			System.out.println("");;
		}
	}
	
	public int left_limit(Point start,int width, int height) {

		if(!check_empty(start,width,height)) {
			return 0;
		}
		int count = -1;
		int i = start.x;
		while(i>0) {
			for(int j = start.y; j < start.y+height;j++) {
				if(this.val[i][j] != 0) {
					return count;
				}
			}
			count++;
			i--;
		}
		count++;
		return count;
	}
	
	public int right_limit(Point start,int width, int height) {

		if(!check_empty(start,width,height)) {
			return 0;
		}
		int count = -1;
		int i = start.x+width;
		while(i<this.x_length) {
			for(int j = start.y; j < start.y+height;j++) {
				if(this.val[i][j] != 0) {
					return count;
				}
			}
			count++;
			i++;
		}
		count++;
		return count;
	}
	
	public int bottom_limit(Point start,int width, int height) {

		if(!check_empty(start,width,height)) {
			return 0;
		}
		int count = -1;
		int i = start.y+height;
		while(i<this.y_length) {
			for(int j = start.x; j < start.x+width;j++) {
				if(this.val[j][i] != 0) {
					return count;
				}
			}
			count++;
			i++;
		}
		count++;
		return count;
	}
	public int top_limit(Point start,int width, int height) {

		if(!check_empty(start,width,height)) {
			return 0;
		}
		int count = -1;
		int i = start.y;
		while(i>0) {
			for(int j = start.x; j < start.x+width;j++) {
				if(this.val[j][i] != 0) {
					return count;
				}
			}
			count++;
			i--;
		}
		count++;
		return count;
	}
	
}
