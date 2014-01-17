package main;

import toxi.geom.Vec2D;
import toxi.physics2d.VerletParticle2D;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;

public class Edit {

	public static FSys.Node activeNode;
	public static VerletParticle2D activeParticle;
	public static ArrayList<FSys.Node> selectedNodes = new ArrayList<>();
	public static boolean edit_mode = true;
	ArrayList<FSys.Node> nodes = new ArrayList<>();
	ArrayList<FSys.Relation> relations = new ArrayList<>();
	private App app;

	public Edit(App app) { this.app = app; }
	public void display() {
		for (FSys.Node n : nodes) {
			App.P5.ellipse(n.getVerlet().x, n.getVerlet().y, 100, 100);
		}
	}
	public void mousePressed() {
//		if (nodes != null) { selectNode(App.MOUSE); }
	}

	public void mouseDragged() {
		if (activeNode != null) activeNode.getVerlet().set(App.MOUSE);
		if (activeParticle != null) activeParticle.set(App.MOUSE);
	}

	public void mouseReleased() {
		if (activeNode != null) { activeNode.getVerlet().unlock(); activeNode = null; }
		if (activeParticle != null) { activeParticle.unlock(); activeParticle = null; }
	}

	public void keyPressed() {
		switch (app.key) {
			case '1':
				App.DRAWMODE = "none";
				break;
			case '2':
				App.DRAWMODE = "verts";
				break;
			case '3':
				App.DRAWMODE = "bezier";
				break;
			case '4':
				App.DRAWMODE = "poly";
				break;
			case '5':
				App.DRAWMODE = "info";
				break;
			case '6':
				App.DRAWMODE = "debug";
				break;
			case 'a':
				createNode(App.OBJ_NAME, App.MOUSE, App.OBJ_SIZE, (int) App.OBJ_COLOR, 10);
				break;
			case 'f':
//				App.PSYS.addAttractor(App.MOUSE);
				break;
			case 'h':
//				App.PSYS.setParticleLock(App.MOUSE);
				break;
			case 'x':
				if (activeNode != null) activeNode.setSize(App.OBJ_SIZE);
				break;
			case 'p':
				marshal();
				break;
		}
	}
/*	private void selectNode(Vec2D v) {
		Circle c = new Circle(v, 30);
		activeNode = null;
		activeParticle = null;
		for (FSys.Node p : nodes) {
			if (c.containsPoint(p.verlet)) {
				activeNode = p;
				activeNode.getVerlet().lock();
				if (App.P5.keyPressed && App.P5.key == 'z') { selectedNodes.add(p); }
				else {selectedNodes.clear();}
				break;
			}
		}
		for (VerletParticle2D p : App.PSYS.getPhysics().particles) {
			if (c.containsPoint(p)) {
				activeParticle = p;
				p.lock();
				break;
			}
		}
	}*/

	public void createNode(String name, Vec2D pos, float size, int color, int id) {
		FSys.Node newNode = new FSys.Node();
		newNode.setId(id);
		newNode.setName(name);
		newNode.setSize(size);
		newNode.setPos(pos);
		newNode.setColor(color);
		newNode.getVerlet().setWeight(size);
//		App.PSYS.addParticle(newNode.getVerlet(), newNode.getBehavior());
		nodes.add(newNode);
		marshal();
	}
	public void marshal() {
		FSys flowgraph = new FSys();
		flowgraph.setNodes(nodes);
		flowgraph.setRelations(relations);
		try {
			JAXBContext context = JAXBContext.newInstance(FSys.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(flowgraph, System.out);
			m.marshal(flowgraph, new File(App.xmlFilePath));
		} catch (JAXBException e) { System.out.println("error parsing xml: "); e.printStackTrace(); System.exit(1); }
	}
	void addRandom(int cnt) {
/*		for (int i = 0; i < cnt; i++) {
			Vec2D v = new Vec2D(App.PSYS.getBounds().getRandomPoint());
			App.PSYS.addAttractor(v);
		}*/
	}

	void addPerim(int res) {
/*		for (int i = 0; i < App.PSYS.getBounds().height; i += res) {
			Vec2D vl = new Vec2D(App.PSYS.getBounds().getLeft() + 20, i + App.PSYS.getBounds().getTop());
			Vec2D vr = new Vec2D(App.PSYS.getBounds().getRight() - 20, i + App.PSYS.getBounds().getTop());
			App.PSYS.addAttractor(vl);
			App.PSYS.addAttractor(vr);
		}
		for (int j = 0; j < App.PSYS.getBounds().width; j += res) {
			Vec2D vt = new Vec2D(j + App.PSYS.getBounds().getLeft(), App.PSYS.getBounds().getTop() + 20);
			Vec2D vb = new Vec2D(j + App.PSYS.getBounds().getLeft(), App.PSYS.getBounds().getBottom() - 20);
			App.PSYS.addAttractor(vt);
			App.PSYS.addAttractor(vb);
		}*/
	}
}
