package main;

import processing.core.PGraphics;
import toxi.geom.Vec2D;
import toxi.physics2d.VerletParticle2D;
import toxi.physics2d.behaviors.AttractionBehavior2D;
import toxi.processing.ToxiclibsSupport;
import util.Color;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.HashMap;

@XmlRootElement(name = "flowgraph")
public class FSys {
	@XmlElement(name = "node")
	protected static ArrayList<Node> nodes = new ArrayList<>();
	@XmlElement(name = "rel")
	protected static ArrayList<Relation> relations = new ArrayList<>();
	@XmlTransient
	private HashMap<Integer, Node> nodeIndex = new HashMap<>();
	@XmlTransient
	private HashMap<Integer, ArrayList<Node>> relationIndex = new HashMap<>();

	public void build() {
		for (Node n : nodes) { nodeIndex.put(n.id, n); }
		for (Relation r : relations) {
			ArrayList<Node> nlist = relationIndex.get(r.from);
			if (nlist == null) {
				nlist = new ArrayList<>();
				relationIndex.put(r.from, nlist);
			} nlist.add(nodeIndex.get(r.to));
		}
	}
	public void update(App app) {
		if ((App.UPDATE_PHYSVAL) && (App.SCALE != 0)) {
			for (Node n : nodes) n.update();
			for (Relation r : relations) {
				Node na = nodeIndex.get(r.from);
				Node nb = nodeIndex.get(r.to);
				float l = (((na.getRadius() + nb.getRadius()) * App.SCALE) / 2) * App.SPR_SCALE;
				app.getPSYS().getPhysics().getSpring(na.getVerlet(), nb.getVerlet()).setRestLength(l);
			}
		}
	}
	public void clear(){
		nodes.clear();
		relations.clear();
		nodeIndex.clear();
		relationIndex.clear();
	}
	public void draw(ToxiclibsSupport gfx) {
		drawInfo(gfx);
		drawNodes(gfx);
	}
	private void drawInfo(ToxiclibsSupport gfx) {
		PGraphics pg = gfx.getGraphics();
		pg.pushMatrix();
		pg.translate(520, 45);
		pg.fill(Color.NODE_TXT);
		pg.text("id", 0, 0);
		pg.text("name", 50, 0);
		pg.text("n size", 100, 0);
		pg.text("n rad", 150, 0);
		pg.text("v wght", 200, 0);
		pg.text("b rad", 250, 0);
		pg.text("b str", 300, 0);
		for (Node n : nodes) {
			pg.translate(0, 10);
			pg.text(n.id, 0, 0);
			pg.text(n.name, 50, 0);
			pg.text((int) n.size + "sq.m", 100, 0);
			pg.text((int) n.radius + "m", 150, 0);
			pg.text(n.getVerlet().getWeight(), 200, 0);
			pg.text(n.getBehavior().getRadius(), 250, 0);
			pg.text(n.getBehavior().getStrength(), 300, 0);
		} pg.noFill();
		pg.popMatrix();
	}
	private void drawNodes(ToxiclibsSupport gfx) {
		PGraphics pg = gfx.getGraphics();
		pg.pushMatrix();
		for (Node n : nodes) {
			pg.stroke(0xffffffff);
			pg.ellipse(n.getPos().x, n.getPos().y, n.radius, n.radius);
			pg.stroke(0xff666666);
			pg.ellipse(n.getPos().x, n.getPos().y, n.getBehavior().getRadius(), n.getBehavior().getRadius());
			pg.stroke(0xff333333);
			pg.ellipse(n.getPos().x, n.getPos().y, n.getVerlet().getWeight(), n.getVerlet().getWeight());
			pg.noStroke();
			n.draw(gfx);
		}
		pg.popMatrix();
	}

	public final FSys.Node getNodeForID(int id) { return nodeIndex.get(id); }
	public final ArrayList<Node> getRelForID(int id) { return relationIndex.get(id); }
	public HashMap<Integer, Node> getNodeIndex() { return nodeIndex; }
	public void setRelations(ArrayList<Relation> relations) {FSys.relations = relations;}
	public void setNodes(ArrayList<Node> nodes) {FSys.nodes = nodes;}
	public void addNode(Node n) {nodes.add(n);}
	public void addRelation(Relation r) {relations.add(r);}

	@XmlRootElement(name = "node")
	public static class Node {
		@XmlAttribute
		public String name;
		@XmlAttribute
		public int id = 0;
		@XmlAttribute
		public float size = 50;
		@XmlAttribute
		public float x = 0;
		@XmlAttribute
		public float y = 0;
		@XmlTransient
		public int color = 180;
		@XmlTransient
		public float radius = (float) (Math.sqrt(size / Math.PI) * App.NODE_SCALE);
		@XmlTransient
		public VerletParticle2D verlet = new VerletParticle2D(x, y);
		@XmlTransient
		public AttractionBehavior2D behavior = new AttractionBehavior2D(verlet, radius, -1.2f);

		public AttractionBehavior2D getBehavior() {return behavior;}
		public VerletParticle2D getVerlet() {return verlet;}
		public Vec2D getPos() {return verlet.copy();}
		public float getRadius() { return radius; }
		public int getColor() {return color;}
		public final String toString() {return Integer.toString(id);}

		public void setId(int id) {this.id = id;}
		public void setSize(float size) {this.size = size;}
		public void setName(String name) {this.name = name;}
		public void setColor(int color) {this.color = color;}
		public void setPos(Vec2D pos) {this.verlet.set(pos);}

		public void update() {
			if (App.NODE_SCALE != 0) { this.radius = (float) (Math.sqrt(size / Math.PI) * App.NODE_SCALE);}
			this.behavior.setRadius(((radius * App.SCALE) + App.NODE_PAD));
			if (App.NODE_STR != 0) this.behavior.setStrength(App.NODE_STR);
		}
		public void draw(ToxiclibsSupport gfx) {
			PGraphics pg = gfx.getGraphics();
			pg.stroke(color, 100, 100);
			pg.ellipse(verlet.x, verlet.y, radius * App.SCALE, radius * App.SCALE);
			pg.stroke(Color.NODE_F);
			pg.ellipse(verlet.x, verlet.y, radius, radius);
			pg.noStroke();
		}
	}

	@XmlRootElement(name = "rel")
	public static class Relation {
		@XmlAttribute
		public int from;
		@XmlAttribute
		public int to;
		public void setTo(int to) {this.to = to;}
		public void setFrom(int from) {this.from = from;}
	}
}
