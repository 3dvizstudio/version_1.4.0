package main;

import processing.core.PApplet;
import processing.core.PGraphics;
import toxi.geom.*;
import toxi.geom.mesh2d.Voronoi;
import toxi.physics2d.VerletParticle2D;
import toxi.processing.ToxiclibsSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static util.Color.*;

public class VSys {
	//	protected PApplet p5;
	private PolygonClipper2D clipper;
	private ArrayList<Vec2D> cellSites = new ArrayList<>();
	private Voronoi voronoi = new Voronoi();
	private ArrayList<Polygon2D> cells = new ArrayList<>();
	private HashMap<Polygon2D, Integer> cellmap = new HashMap<>();
	private HashMap<Vec2D, Integer> sitemap = new HashMap<>();

	public VSys(PApplet $p5, Rect bounds) {
//		this.p5 = $p5;
		clipper = new SutherlandHodgemanClipper(bounds);
	}

	public Voronoi getVoronoi() { return voronoi; }
	public void setVoronoi(Voronoi v) { voronoi = v; }
	public void addCell(Vec2D v) { getCellSites().add(v); }
	public void addSite(Vec2D v, Integer i) {getSitemap().put(v, i);}
	public void setCells(ArrayList<Polygon2D> cells) { this.cells = cells; }
	public void setCellmap(HashMap<Polygon2D, Integer> cellmap) { this.cellmap = cellmap; }
	public void setClipper(PolygonClipper2D clipper) { this.clipper = clipper; }
	public void setCellSites(ArrayList<Vec2D> cellSites) { this.cellSites = cellSites; }
	public void setSitemap(HashMap<Vec2D, Integer> sitemap) { this.sitemap = sitemap; }
	public PolygonClipper2D getClipper() { return clipper; }
	public ArrayList<Vec2D> getCellSites() { return cellSites; }
	public ArrayList<Polygon2D> getCells() { return cells; }
	public HashMap<Vec2D, Integer> getSitemap() { return sitemap; }
	public HashMap<Polygon2D, Integer> getCellmap() { return cellmap; }

	public void update(ArrayList<VerletParticle2D> sites, ToxiclibsSupport gfx) {
		if (App.UPDATE_VORONOI) {
			setVoronoi(new Voronoi());
			getVoronoi().addPoints(sites);
//			getVoronoi().addPoints(App.PSYS.getParticles());
		} if (App.SHOW_VORONOI && getVoronoi() != null) {
			setCells(new ArrayList<Polygon2D>());
			setCellmap(new HashMap<Polygon2D, Integer>());
			for (Polygon2D poly : getVoronoi().getRegions()) {
				poly = getClipper().clipPolygon(poly);
				for (Vec2D v : getCellSites()) {
					if (poly.containsPoint(v)) { getCells().add(poly); }
				}
			} drawPoly(gfx);
		}
	}

	private void drawPoly(ToxiclibsSupport gfx) {
		PGraphics pg = gfx.getGraphics();

		String drawMode = App.DRAWMODE;
		switch (drawMode) {
			case "none": break;
			case "verts":
				pg.stroke(VOR_VERTS);
				for (Polygon2D poly : getCells()) {
					for (Vec2D vec : poly.vertices) {
						pg.ellipse(vec.x, vec.y, 4, 4);
					}
				}
				pg.noStroke();
				break;
			case "bezier":
				pg.fill(VOR_VOIDS);
				pg.stroke(VOR_CELLS);
				for (Polygon2D poly : getCells()) {
					//	pg.stroke(cellmap.get(poly),100,100);
					List<Vec2D> vec = poly.vertices;
					int count = vec.size();
					pg.beginShape();
					pg.vertex((vec.get(count - 1).x + vec.get(0).x) / 2, (vec.get(count - 1).y + vec.get(0).y) / 2);
					for (int i = 0; i < count; i++) {
						pg.bezierVertex(vec.get(i).x, vec.get(i).y,
								vec.get(i).x, vec.get(i).y,
								(vec.get((i + 1) % count).x + vec.get(i).x) / 2,
								(vec.get((i + 1) % count).y + vec.get(i).y) / 2);
					} pg.endShape(PApplet.CLOSE);
				}
				pg.noStroke();
				pg.noFill();
				break;
			case "poly":
				pg.stroke(VOR_CELLS);
				for (Polygon2D poly : getCells()) { gfx.polygon2D(poly); }
				pg.noStroke();
				break;
			case "info":
				pg.fill(VOR_TXT);
				for (Polygon2D poly : getCells()) {
					pg.text(poly.getNumVertices() + "." + getCells().indexOf(poly), poly.getCentroid().x, poly.getCentroid().y);
				}
				pg.noFill();
				break;
		}
	}
}