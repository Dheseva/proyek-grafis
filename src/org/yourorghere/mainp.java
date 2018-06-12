package org.yourorghere;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;
import static org.yourorghere.Objek.drawHorn;


public class mainp implements GLEventListener {

    public static void main(String[] args) {
        Frame frame = new Frame("Grafis");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new mainp());
        frame.add(canvas);
        frame.setSize(640, 480);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!

            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    float angle = 0;
    float direction = 1;

    // static dimensions    
    private static final float BODY_LENGTH = 3.0f;
    private static final float BODY_RADIUS = 1.5f;
    private static final float LIMB_LENGTH = 1.7f;
    private static final float LIMB_RADIUS = 0.4f;
    private static final float HORN_LENGTH = 0.6f;
    private static final float HORN_RADIUS = 0.1f;

    // drawing precision
    private static final int SLICES = 30;
    private static final int STACKS = 30;

    // animation    
    private static float ARM_SWING = 0.0f;
    private static float DELTA_ARM_SWING = 5.0f;
    private static float MAX_ARM_SWING = 50.0f;

    private static float TALK_SWING = 0.0f;
    private static float MAX_TALK_SWING = 5.0f;
    private static float DELTA_TALK_SWING = 2.0f;

    public void display(GLAutoDrawable drawable) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(mainp.class.getName()).log(Level.SEVERE, null, ex);
        }

        GL gl = drawable.getGL();

        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        // Move the "drawing cursor" around
        gl.glTranslatef(-1.5f, 0.0f, -16.0f);

        gl.glRotatef(-90, 1.0f, 0.0f, 0.0f);

        GLU glu = new GLU();
        GLUquadric q = glu.gluNewQuadric();
        glu.gluQuadricDrawStyle(q, GLU.GLU_FILL);

        //glu.gluQuadricDrawStyle(q, GLU.GLU_SILHOUETTE);
        glu.gluQuadricOrientation(q, GLU.GLU_OUTSIDE);
        glu.gluQuadricNormals(q, GLU.GLU_SMOOTH);

        // set the material
        gl.glColor3f(0.0f, 1.0f, 0.0f);

        //---------------------------
        // draw body
        gl.glPushMatrix();
        glu.gluCylinder(q, BODY_RADIUS, BODY_RADIUS, BODY_LENGTH, SLICES, STACKS);
        glu.gluDisk(q, 0.0f, BODY_RADIUS, SLICES, STACKS);
        gl.glTranslatef(0.0f, 0.0f, BODY_LENGTH);
        glu.gluDisk(q, 0.0f, BODY_RADIUS, SLICES, STACKS);

        // draw head top 
        // talk
        gl.glRotatef(TALK_SWING, 1.0f, 0.0f, 0.0f);
        gl.glTranslatef(0.0f, 0.0f, 0.07f + 0.05f * TALK_SWING);
        glu.gluDisk(q, 0.0f, BODY_RADIUS, SLICES, STACKS);

        // halfsphere
        double[] cutplane = new double[]{0.0f, 0.0f, 1.0f, 0.0f};
        gl.glClipPlane(GL.GL_CLIP_PLANE2, cutplane, 0);
        gl.glEnable(GL.GL_CLIP_PLANE2);

        glu.gluSphere(q, BODY_RADIUS, SLICES, STACKS);

        gl.glDisable(GL.GL_CLIP_PLANE2);

        //horn
        drawHorn(gl, glu, q);

        // eyes
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        Objek.drawEyes(gl, glu, q);
        gl.glColor3f(0.0f, 1.0f, 0.0f);

        // Draw arms
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslatef(BODY_RADIUS + LIMB_RADIUS + 0.1f, 0.0f, BODY_LENGTH - LIMB_RADIUS);
        gl.glRotatef(10, 1.0f, 0.0f, 0.0f);
        Objek.drawLimb(gl, glu, q, -ARM_SWING);

        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslatef(-(BODY_RADIUS + LIMB_RADIUS + 0.1f), 0.0f, BODY_LENGTH - LIMB_RADIUS);
        gl.glRotatef(40, 1.0f, 0.0f, 0.0f);
        Objek.drawLimb(gl, glu, q, ARM_SWING);

        // Draw legs
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslatef(BODY_RADIUS - LIMB_RADIUS * 2.0f, 0.0f, 0.2f);

        Objek.drawLimb(gl, glu, q, ARM_SWING);

        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslatef(-(BODY_RADIUS - LIMB_RADIUS * 2.0f), 0.0f, 0.2f);

        gl.glRotatef(-angle, 1, 0, 0);
        Objek.drawLimb(gl, glu, q, -ARM_SWING);
        angle += direction;
        if (angle >= 45 || angle <= -45) {
            direction = -direction;
        }

        gl.glPopMatrix();

        // delete the quadric
        glu.gluDeleteQuadric(q);

        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}
