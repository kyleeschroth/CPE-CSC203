import java.util.List;
import java.util.ArrayList;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntBiFunction;
import java.util.Collections; 
import java.lang.Math; 

public class AStarPathingStrategy implements PathingStrategy{

	private DebugGrid grid; 

	@Override
	public void setDebugGrid(DebugGrid grid){
		this.grid = grid; 
	}

	@Override
	public List<Point> computePath(final Point start, final Point end,
                            Predicate<Point> canPassThrough,
                            Function<Point, List<Point>> potentialNeighbors,
                            ToIntBiFunction<Point, Point> stepsFromTo){

		ArrayList<Point> result = new ArrayList<>();

		ArrayList<Point> closed = new ArrayList<>();

		ArrayList<Point> open = new ArrayList<>(); 
		open.add(start); 

		Map<Point, Point> cameFrom = new HashMap<>(); 

		Map<Point, Double> gScore = new HashMap<>(); 
		gScore.put(start, 0.0); 

		Map<Point, Double> fScore = new HashMap<>(); 
		int ya = stepsFromTo.applyAsInt(start, end); 
		fScore.put(start, costEstimate(start, end, ya)); 

		while (!open.isEmpty()){
			Point current = Collections.min(open, Comparator.comparing(p -> fScore.getOrDefault(p, Double.MAX_VALUE))); 
			if (current == end){
				return reconstructPath(cameFrom, current); 
			}
			open.remove(current); 
			closed.add(current); 

			for (Point neighbor : potentialNeighbors.apply(current)){
				if (closed.contains(neighbor)){
					continue; 
				}

				double tentativeGScore = gScore.get(current) + stepsFromTo.applyAsInt(current, neighbor);

				if (!open.contains(neighbor)){
					open.add(neighbor);
				} 
				else if (tentativeGScore <= gScore.getOrDefault(neighbor, Double.MAX_VALUE)){
					continue; 
				}

				cameFrom.put(neighbor, current);
				gScore.put(neighbor, tentativeGScore); 
				int steps = stepsFromTo.applyAsInt(current, neighbor); 
				fScore.put(neighbor, gScore.get(neighbor) + costEstimate(neighbor, end, steps)); 
			}
			if (DebugGrid.ENABLED) paintDebug(open, closed);
			
		}
		return result; 
	}

	public List<Point> reconstructPath(Map<Point, Point> cameFrom, Point current){
		LinkedList<Point> totalPath = new LinkedList<Point>();  
		totalPath.add(current); 
		while (cameFrom.containsKey(current)){
			current = cameFrom.get(current); 
			totalPath.addFirst(current); 
		}
		totalPath.removeFirst(); 
		return totalPath; 
	}

	protected double costEstimate(Point start, Point end, int est){
		return est * 1.05; 
	}

	private void paintDebug(final Iterable<Point> open, final Iterable<Point> closed) {
        if (grid == null) return;
        	for (Point p : open) {
            	grid.setGridValue(p, DebugGrid.OPEN_SET_TILE);
        	}
        	for (Point p : closed) {
            	grid.setGridValue(p, DebugGrid.CLOSED_SET_TILE);
        	}
        grid.showFrame();
    }
	/*
	public Double lowestFScore(Map<Point, Double> fScore){
		Double min = fScore.get(0); 
		for (int i = 0; i <= fScore.size(); i++){
			if (fScore.get(i) < min){
				min = fScore.get(i);
			}
		}
	}
	*/

}