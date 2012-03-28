package org.yourorghere;

import com.sun.opengl.util.Screenshot;
import com.sun.opengl.util.j2d.TextRenderer;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.math.*;



/**
 * GLRenderer.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class GLRenderer implements GLEventListener {

   
    int i = 0;
    boolean check = false;
    private float angle=90.0f;// Başlangıç açısı
    private float tangle=90.0f;
    private float cx=0.0f; // Başlangıç koordinatları Orta nokta koordinatı olarak atanır
    private float cy=0.0f;
    private float tx=0.0f;
    private float ty=0.0f;
    private float nx,ny;
    int cek=0;
    int aci=0;
    TextRenderer renderer;
    
    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
//        System.err.println("INIT GL IS: " + gl.getClass().getName());
        
        renderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 36));
        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(0.98f, 0.625f, 0.12f, 0.0f); //turuncu
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!
        
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
      
        gl.glOrtho(-2.0, 7.0, -2.0, 2.0, -1.0, 1.0);
      
    }
    
    
    
    void arkaplanciz(GL gl){
    	
        for( float x=(float) -0.75; x<=0.7;x= x+(float)0.15){
        	for(float y=(float) -0.75; y<=0.7;y= y+(float)0.15){
        		gl.glBegin(GL.GL_LINES);
        		gl.glColor3f(1, 1, 1);
        		gl.glVertex2f(x,y);
		        gl.glVertex2f(x,y+(float)0.15);
		        gl.glVertex2f(x,y+(float)0.15);
		        gl.glVertex2f(x+(float)0.15,y+(float)0.15);
		        gl.glVertex2f(x+(float)0.15,y+(float)0.15);
		        gl.glVertex2f(x+(float)0.15,y);
		        gl.glVertex2f(x+(float)0.15 ,y );
		        gl.glVertex2f(x,y);
		        
		        gl.glEnd();
		        gl.glFlush();
       }
        
        
        }
     
    
    }
    void ilerle(GL gl){        
    	int m=0;
    	check = false; 
    	if ((int)angle % 2 == 1) m=2; // açıya göre göre uzunluğun belirlenmesi
    	if ((int)angle % 2 == 0) m=1; // 
    	nx = cx+(float)((Math.pow(m,0.5)*0.15)*Math.sin((-angle+90)*Math.PI/180)); // açıya göre yeni koordinatların hesabı
    	ny = cy+(float)((Math.pow(m,0.5)*0.15)*Math.cos((-angle+90)*Math.PI/180));
    	if (nx <= 0.75 & nx >=-0.8 & ny <= 0.75 & ny >=-0.8 ){ // sınır dışına çıkılırsa check true olur.
    	gl.glBegin(GL.GL_LINES);
    	
    	gl.glColor3d(1, 0, 0); // kırmızı
        gl.glVertex2f(cx, cy);
        gl.glVertex2f(nx, ny);
        gl.glEnd();
        cx = nx;
        cy = ny;

    	}
    	else {
    		check = true;
    	}
    	 }
    

    public void display(GLAutoDrawable drawable) {
      
       
        GL gl = drawable.getGL();
        if (i==0){ //ekranı temizleme
        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_PROJECTION);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
      
        // Move the "drawing cursor" around
        gl.glLineWidth(1.9f);
        arkaplanciz(gl);
        aciciz(gl);
        }
        if (i!=0 && cek==0 && aci==0) { // ekran görüntüsü alırken veya açı değiştiğin çizim yapılmaması için
        	ilerle(gl);
        
        }
        i++;
        if (aci==1) // Açı değerini ekrana yazdırma
        {
        	aciciz(gl);
        	aci = 0;
        }
      
        if (cek==1){ //ekran görüntüsü alma
        	ekran();
        	System.out.println("girdi");
       
       }
      
    }
    public void kaydet(){
    	
    	tangle = angle;
    	tx = cx;
    	ty = cy;	
    }
    public void sifirla(){
    	
    	cx = 0;
    	cy = 0;
    	angle = 90.0f;    	
    }
    public void hatirla(){
    	
    	angle = tangle;
    	cx = tx;
    	cy = ty;
    }
    public void kucuktur(){
    	
    	angle+=45;
    	angle = angle % 360;
    	aci = 1;
    	 }
    public void buyuktur(){
    	
    	angle-=45;
    	if (angle < 0) { // Açının eksi değer almaması için
    		angle= 360+angle;
    	}
    	angle = angle % 360;
    	aci = 1;
     }
    void aciciz(GL gl){
    	gl.glBegin(GL.GL_POLYGON); // Açı değerini temizle
    	gl.glColor4f(0.98f, 0.625f, 0.12f, 0.0f); //turuncu
    	gl.glVertex2f(-0.78f,-0.8f);
    	gl.glVertex2f(0.2f,-0.8f);
    	gl.glVertex2f(-0.78f,-1.0f);
    	gl.glVertex2f(0.2f,-1.0f);
    	gl.glEnd();
    	renderer.beginRendering(640,480); // Açı değerini ekrana yazdır
    	renderer.setColor(1.0f, 1.0f, 1.0f, 1.f); //beyaz
        renderer.draw("Aci : " + angle,10,10);
        renderer.endRendering();

    }
    void ekran(){
      BufferedImage tScreenshot = Screenshot.readToBufferedImage(0,0, 640, 480, false); 
        File tScreenCaptureImageFile = new File("joglpng.jpg"); 
        try {
            ImageIO.write(tScreenshot, "jpg", tScreenCaptureImageFile);
        } catch (IOException ex) {
            Logger.getLogger(GLRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
        cek=0;
 
 }
    void gir (){ // ekran görüntüsü almak için
    	cek=1;
 
 }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}
