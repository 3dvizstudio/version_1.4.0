package main;

import processing.core.PApplet;
import util.Color;

public class View {
	private PApplet p5;
	public View(PApplet p5) { this.p5 = p5; }
	public void render() {
	/*	if ((App.PSYS != null) && (App.PSYS.getPhysics().particles != null)) {
			if (App.SHOW_PARTICLES) drawVerletParticles();
			if (App.SHOW_INFO) drawVerletStats();
		}
		if ((App.PSYS != null) && (App.PSYS.getPhysics().springs != null)) {
			if (App.SHOW_SPRINGS) drawVerletSprings();
		}
		if ((App.PSYS != null)) {
			if (App.SHOW_SPRINGS) drawVerletSprings();
		}
		if ((App.SHOW_ATTRACTORS) && (App.PSYS.getAttractors() != null)) {
			drawAttractors();
		}
		if ((App.FSYS != null) && (FSys.nodes != null)) {
			if (App.SHOW_NODES) drawNodes();
			if (App.SHOW_INFO) drawNodeStats();
		}*/
	}
	private void drawAttractors() {
/*		p5.stroke(0xffffff00);
		for (VerletParticle2D a :PSys.getPhysics(),particles) {
			p5.line(a.x - 3, a.y - 3, a.x + 3, a.y + 3);
			p5.line(a.x - 3, a.y + 3, a.x + 3, a.y - 3);
			p5.ellipse(a.x, a.y, a.getWeight() * 2, a.getWeight() * 2);
		} p5.noStroke();*/
	}

	private void drawNodeStats() {
		p5.pushMatrix();
		p5.translate(520, 45);
		p5.fill(Color.NODE_TXT);
		p5.text("id", 0, 0);
		p5.text("name", 50, 0);
		p5.text("n size", 100, 0);
		p5.text("n rad", 150, 0);
		p5.text("v wght", 200, 0);
		p5.text("b rad", 250, 0);
		p5.text("b str", 300, 0);
		for (FSys.Node n : FSys.nodes) {
			p5.translate(0, 10);
			p5.text(n.id, 0, 0);
			p5.text(n.name, 50, 0);
			p5.text((int) n.size + "sq.m", 100, 0);
			p5.text((int) n.radius + "m", 150, 0);
			p5.text(n.getVerlet().getWeight(), 200, 0);
			p5.text(n.getBehavior().getRadius(), 250, 0);
			p5.text(n.getBehavior().getStrength(), 300, 0);
		} p5.noFill();
		p5.popMatrix();
	}
	//			p5.fill(0xffcc0000);			p5.text(n.id, 0, 0);			p5.text(n.name, n.verlet.x, 0);			p5.text((int) n.size + "m²", 60, 0);			p5.noFill();//		"	"p5.text("[ weight: " + n.verlet.getWeight() + " ] [ area: " + n.getVerlet().getWeight() + " ] [ rad: " + App.DF3.format(n.getRadius()) + " ]", 310, p5.height - 4);""
	private void drawVerletParticles() {
//		p5.fill(Color.PHYS_PTCL);
//		for (VerletParticle2D p : App.PSYS.getPhysics().particles) {
//			p5.ellipse(p.x, p.y, 4, 4);
//		} p5.noFill();
	}
	private void drawVerletSprings() {
//		p5.stroke(Color.PHYS_SPR);
//		for (VerletSpring2D s : App.PSYS.getPhysics().springs) { p5.line(s.a.x, s.a.y, s.b.x, s.b.y); }
//		p5.noStroke();
	}
	private void drawNodes() {
		for (FSys.Node n : FSys.nodes) {
//			if (n.getVerlet().isLocked()) {p5.stroke(0xff444444); }
//			else { p5.stroke(n.getColor(), 100, 100); }
			p5.stroke(0xffffffff);
			p5.ellipse(n.getPos().x, n.getPos().y, n.radius, n.radius);
//			p5.ellipse(n.getPos().x+10, n.getPos().y, n.radius * App.SCALE, n.radius * App.SCALE);
			p5.stroke(0xff666666);
			p5.ellipse(n.getPos().x, n.getPos().y, n.getBehavior().getRadius(), n.getBehavior().getRadius());
			p5.stroke(0xff333333);
			p5.ellipse(n.getPos().x, n.getPos().y, n.getVerlet().getWeight(), n.getVerlet().getWeight());
					p5.noStroke();
		} if (Edit.activeNode != null) {
			FSys.Node n = Edit.activeNode;
//			p5.stroke(Color.RED);
//			p5.ellipse(n.getPos().x, n.getPos().y, n.radius, 30);
		}
		for (FSys.Node n : Edit.selectedNodes) {
			p5.stroke(0xffffffff);
//			p5.ellipse(n.getPos().x, n.getPos().y, 20, 20);
			p5.noStroke();
		}
	}
	private void drawEditMode() {
		if ((Edit.edit_mode) && (App.P5.mousePressed)) {
			App.P5.stroke(Color.RED);
			App.P5.ellipse(App.MOUSE.x, App.MOUSE.y, 10, 10);
		}
	}
}

//			if (p == Edit.activeNode.getVerlet()) p5.ellipse(p.x, p.y, 8, 8);			else

 /*if ((App.SHOW_ATTRACTORS) && (PSys.attractors != null)) {
				p5.stroke(Color.PHYS_ATTR);
				for (AttractionBehavior2D a : PSys.attractors) {
					Vec2D n = a.getAttractor();
					if (a == Edit.selectedAttractor) p5.stroke(100);
					else p5.stroke(Color.PHYS_ATTR);
					p5.line(n.x - 3, n.y - 3, n.x + 3, n.y + 3);
					p5.line(n.x - 3, n.y + 3, n.x + 3, n.y - 3);
					p5.ellipse(n.x, n.y, a.getRadius() * 2, a.getRadius() * 2);
				} p5.noStroke();
			}*/
//if (App.SHOW_MINDIST) {p5.stroke(PHYS_SPR);for (VerletSpring2D s : minDistSprings) { p5.line(s.a.x, s.a.y, s.b.x, s.b.y); }p5.noStroke();}
