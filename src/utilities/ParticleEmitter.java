package utilities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import utilities.Particle;

public class ParticleEmitter {
	
	private CopyOnWriteArrayList<Particle> particles = new CopyOnWriteArrayList<>();
	private int numMaxParticles;
	private int particlePositionOffset;
	private int currentIndex;
	
	
	public ParticleEmitter(int numMaxParticles, int particlePositionOffset){
		this.numMaxParticles = numMaxParticles;
		this.particlePositionOffset = particlePositionOffset;
		this.currentIndex = 0;
	}
	
	public void emittParticle(int x, int y) {
		Color randomColor = new Color((int) (200 + Math.random() * 55),
				(int) (128 + Math.random() * 127), 0);
		
		if (this.particles.size() > this.currentIndex && this.particles.get(this.currentIndex) != null){
			this.particles.remove(this.currentIndex);
			this.particles.add(this.currentIndex, new Particle(x, y, this.particlePositionOffset,
					randomColor));
		} else {
			this.particles.add(new Particle(x, y, this.particlePositionOffset,
					randomColor));
		}
		
		this.currentIndex++;
		if (this.currentIndex >= this.numMaxParticles) {
			this.currentIndex = 0;
		}
	}
	
	public synchronized void drawParticles(Graphics2D g){
		for (final Iterator iterator = particles.iterator(); iterator.hasNext(); ) {
			((Particle)iterator.next()).drawParticle(g);
		}
	}

}
