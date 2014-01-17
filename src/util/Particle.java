package util;

import processing.core.PGraphics;
import toxi.geom.Vec2D;
import toxi.physics2d.VerletParticle2D;
import toxi.processing.ToxiclibsSupport;

public class Particle extends VerletParticle2D {

String name;
int id;
float size;
int color;

public Particle(Vec2D pos, float weight, String name, int id, float size) {
	super(pos, weight);
	this.name=name;
	this.id=id;
	this.size=size;
}
public void draw(ToxiclibsSupport gfx){
	PGraphics pg = gfx.getGraphics();
	pg.fill(0xffffff00);
	pg.text(name,x,y);
}
}
