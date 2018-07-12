package org.yourorghere;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

public class GLRenderer implements GLEventListener {

    //class vector untuk memudah vektor-isasi
    class vector {

        float x;
        float y;
        float z;

        public vector(float startX, float startY, float startZ) {
            x = startX;
            y = startY;
            z = startZ;
        }

        void vectorRotation(vector reference, float angle) {
            vector temp = reference;
            float magnitude = (float) Math.sqrt(Math.pow(temp.x,
                    2) + Math.pow(temp.y, 2) + Math.pow(temp.z, 2));
            temp.x = temp.x / magnitude;
            temp.y
                    = temp.y / magnitude;
            temp.z = temp.z / magnitude;
            float dot_product
                    = (x * temp.x) + (y * temp.y) + (z * temp.z);
            float cross_product_x = (y * temp.z) - (temp.z * z);
            float cross_product_y = -((x * temp.z) - (z * temp.x));
            float cross_product_z = (x * temp.y) - (y * temp.x);
            float last_factor_rodrigues = (float) (1
                    - Math.cos(Math.toRadians(angle % 360)));

            x = (float) ((x * Math.cos(Math.toRadians(angle % 360)))
                    + (cross_product_x * Math.sin(Math.toRadians(angle % 360)))
                    + (dot_product * last_factor_rodrigues * x));

            y = (float) ((this.y * Math.cos(Math.toRadians(angle % 360)))
                    + (cross_product_y * Math.sin(Math.toRadians(angle % 360)))
                    + (dot_product * last_factor_rodrigues * y));

            z = (float) ((z * Math.cos(Math.toRadians(angle % 360)))
                    + (cross_product_z * Math.sin(Math.toRadians(angle % 360)))
                    + (dot_product * last_factor_rodrigues * z));

        }

    }

    vector depanBelakang = new vector(0f, 0f, -1f);//deklarasi awal vektor untuk maju & mundur
    vector samping = new vector(1f, 0f, 0f);//deklarasi awalvektor untuk gerakan ke kanan & kiri 
    vector vertikal = new vector(0f, 1f, 0f);//deklarasi awalvetor untuk gerakan naik & turun

    static float Cx = 0, Cy = 2.5f, Cz = 0;
    static float Lx = 0, Ly = 2.5f, Lz = -20f;

    static float angle_depanBelakang = 0f;
    static float angle_depanBelakang2 = 0f;

    static float angle_samping = 0f;
    static float angle_samping2 = 0f;

    static float angle_vertikal = 0f;
    static float angle_vertikal2 = 0f;

    static float silinderAngle = 90f;

    boolean ori = true, silinder = false, kamera = false;


    /*
     ini adalah metod untuk melakukan pergerakan.
     magnitude adalah besarnya gerakan sedangkan direction
     digunakan untuk menentukan arah.
     gunakan -1 untuk arah berlawanan dengan vektor awal
     */
    private static void vectorMovement(vector toMove, float magnitude,
            float direction) {
        float speedX = toMove.x * magnitude * direction;
        float speedY = toMove.y * magnitude * direction;
        float speedZ = toMove.z * magnitude * direction;

        Cx += speedX;
        Cy += speedY;
        Cz += speedZ;

        Lx += speedX;
        Ly += speedY;
        Lz += speedZ;
        
        System.out.println(Cx + " " + Cy + " " + Cz);
    }

    private void cameraRotation(vector reference, double angle) {
        float M = (float) (Math.sqrt(Math.pow(reference.x, 2)
                + Math.pow(reference.y, 2) + Math.pow(reference.z, 2)));//magnitud

        float Up_x1 = reference.x / M; //melakukan
        float Up_y1 = reference.y / M; //normalisasi
        float Up_z1 = reference.z / M; //vektor patokan

        float VLx = Lx - Cx;
        float VLy = Ly - Cy;
        float VLz = Lz
                - Cz;
        float dot_product = (VLx * Up_x1) + (VLy * Up_y1) + (VLz * Up_z1);
        float cross_product_x = (Up_y1 * VLz) - (VLy * Up_z1);
        float cross_product_y = -((Up_x1 * VLz) - (Up_z1 * VLx));
        float cross_product_z = (Up_x1 * VLy) - (Up_y1 * VLx);

        float last_factor_rodriques = (float) (1
                - Math.cos(Math.toRadians(angle % 360)));

        float Lx1 = (float) ((VLx * Math.cos(Math.toRadians(angle % 360)))
                + (cross_product_x * Math.sin(Math.toRadians(angle % 360)))
                + (dot_product * last_factor_rodriques * VLx));

        float Ly1 = (float) ((VLy * Math.cos(Math.toRadians(angle % 360)))
                + (cross_product_y * Math.sin(Math.toRadians(angle % 360)))
                + (dot_product * last_factor_rodriques * VLy));

        float Lz1 = (float) ((VLz * Math.cos(Math.toRadians(angle % 360)))
                + (cross_product_z * Math.sin(Math.toRadians(angle % 360)))
                + (dot_product * last_factor_rodriques * VLz));

        Lx = Lx1 + Cx;
        Ly = Ly1 + Cy;
        Lz = Lz1 + Cz;
    }

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));
        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: "
                + gl.getClass().getName());
        // Enable VSync
        gl.setSwapInterval(1);
        float ambient[] = {1.0f, 1.0f, 1.0f, 1.0f};
        float diffuse[] = {1.0f, 1.0f, 1.0f, 1.0f};
        float position[] = {1.0f, 1.0f, 1.0f, 0.0f};
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, ambient, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position, 0);
        gl.glEnable(GL.GL_LIGHT0);
        gl.glEnable(GL.GL_LIGHTING);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glClearColor(0f, 0f, 1.0f, 1.0f);
        gl.glShadeModel(GL.GL_SMOOTH);
 //gl.glShadeModel(GL.GL_SMOOTH); // try setting this to

    }

    public void reshape(GLAutoDrawable drawable, int x, int y,
            int width, int height) {
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
    float direction = 5;

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
            Logger.getLogger(Grafisuas.class.getName()).log(Level.SEVERE, null, ex);
        }
        GL gl = drawable.getGL();
        GLU glu = new GLU();
        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT
                | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();
        // Move the "drawing cursor" around
        glu.gluLookAt(Cx, Cy, Cz,
                Lx, Ly, Lz,
                vertikal.x, vertikal.y, vertikal.z);

        // Move the "drawing cursor" around
        gl.glTranslatef(-1.5f, 0.0f, -16.0f);

        gl.glRotatef(-90, 1.0f, 0.0f, 0.0f);

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
        //drawHorn(gl, glu, q);
        // eyes
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        Objek.drawEyes(gl, glu, q);
        gl.glColor3f(0.0f, 1.0f, 0.0f);

        // Draw arms
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslatef(BODY_RADIUS + LIMB_RADIUS + 0.1f, 0.0f, BODY_LENGTH - LIMB_RADIUS);
        gl.glRotatef(angle, 1, 0, 0);
        Objek.drawLimb(gl, glu, q, -ARM_SWING);
        angle += direction;
        if (angle >= 25 || angle <= -25) {
            direction = -direction;
        }

        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslatef(-(BODY_RADIUS + LIMB_RADIUS + 0.11f), 0.0f, BODY_LENGTH - LIMB_RADIUS);
        gl.glRotatef(-angle, 1, 0, 0);
        Objek.drawLimb(gl, glu, q, ARM_SWING);
        angle += direction;
        if (angle >= 25 || angle <= -25) {
            direction = direction;
        }
        gl.glPopMatrix();
        // Draw legs
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslatef(BODY_RADIUS - LIMB_RADIUS * 2.0f, 0.0f, 0.2f);

        gl.glRotatef(angle, 1, 0, 0); //kaki
        Objek.drawLimb(gl, glu, q, ARM_SWING);
        angle += direction;
        if (angle >= 25 || angle <= -25) {
            direction = -direction;
        }

        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslatef(-(BODY_RADIUS - LIMB_RADIUS * 2.0f), 0.0f, 0.2f);

        gl.glRotatef(-angle, 1, 0, 0); //kaki
        Objek.drawLimb(gl, glu, q, -ARM_SWING);
        angle += direction;
        if (angle >= 25 || angle <= -25) {
            direction = direction;
        }

        gl.glPopMatrix();

        // delete the quadric
        glu.gluDeleteQuadric(q);

        // Flush all drawing operations to the graphics card
        gl.glFlush();

        if (kamera) {
            Key_Pressed(74);
        }

        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    void Key_Pressed(int keyCode) {

        //huruf W
        if (keyCode == 87) {
            vectorMovement(depanBelakang, 2f, 1f);
        } //huruf S
        else if (keyCode == 83) {
            vectorMovement(depanBelakang, 2f, -1f);
        } //huruf A
        else if (keyCode == 68) {
            vectorMovement(samping, 2f, 1f);
        } //huruf D
        else if (keyCode == 65) {
            vectorMovement(samping, 2f, -1f);
        } //panah atas
        else if (keyCode == 38) {
            vectorMovement(vertikal, 2f, 1f);
        } //panah bawah
        else if (keyCode == 40) {
            vectorMovement(vertikal, 2f, -1f);
        } //tombol spasi
        else if (keyCode == 32) {
            if (silinder) {
                silinder = false;
            } else {
                silinder = true;
            }
        } //tombol enter
        else if (keyCode == 10) {
            if (kamera) {
                kamera = false;
            } else {
                kamera = true;
            }
        } //huruf J
        else if (keyCode == 74) {
            angle_vertikal += 15f;

            samping.vectorRotation(vertikal, angle_vertikal - angle_vertikal2);
            depanBelakang.vectorRotation(vertikal,
                    angle_vertikal - angle_vertikal2);
            cameraRotation(vertikal, angle_vertikal - angle_vertikal2);
            angle_vertikal2 = angle_vertikal;
        } //huruf L
        else if (keyCode == 76) {
            angle_vertikal -= 15f;

            samping.vectorRotation(vertikal, angle_vertikal - angle_vertikal2);
            depanBelakang.vectorRotation(vertikal,
                    angle_vertikal - angle_vertikal2);
            cameraRotation(vertikal, angle_vertikal - angle_vertikal2);
            angle_vertikal2 = angle_vertikal;
        } //huruf I
        else if (keyCode == 73) {
            angle_samping -= 15f;

            //vertikal.vectorRotation(samping, angle_sampingangle_samping2);
            depanBelakang.vectorRotation(samping, angle_samping - angle_samping2);
            cameraRotation(samping, angle_samping - angle_samping2);
            angle_samping2 = angle_samping;
        } //huruf K
        else if (keyCode == 75) {
            angle_samping += 15f;

            //vertikal.vectorRotation(samping, angle_sampingangle_samping2);
            depanBelakang.vectorRotation(samping, angle_samping - angle_samping2);
            cameraRotation(samping, angle_samping - angle_samping2);
            angle_samping2 = angle_samping;
        } //panah kanan
        else if (keyCode == 39) {
            angle_depanBelakang -= 15f;

            samping.vectorRotation(depanBelakang,
                    angle_depanBelakang - angle_depanBelakang2);
            vertikal.vectorRotation(depanBelakang,
                    angle_depanBelakang - angle_depanBelakang2);
            //cameraRotation(vertikal, angle_sampingangle_samping2);
            angle_depanBelakang2 = angle_depanBelakang;
        } //panah kiri
        else if (keyCode == 37) {
            angle_depanBelakang += 15f;

            samping.vectorRotation(depanBelakang,
                    angle_depanBelakang - angle_depanBelakang2);
            vertikal.vectorRotation(depanBelakang,
                    angle_depanBelakang - angle_depanBelakang2);
            //cameraRotation(vertikal, angle_sampingangle_samping2);
            angle_depanBelakang2 = angle_depanBelakang;
        }
    }

}
