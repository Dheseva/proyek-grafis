package org.yourorghere;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

public class Bola {

    static void Bola1(GL gl) {
        float BODY_RADIUS = 0.5f;
        int SLICES = 30;
        int STACKS = 30;
        GLU glu = new GLU();
        GLUquadric q = glu.gluNewQuadric();
        glu.gluSphere(q, BODY_RADIUS, SLICES, STACKS);
    }

//    static void Bola2(GL gl) {
//        float BODY_RADIUS = 0.3f;
//        int SLICES = 30;
//        int STACKS = 30;
//        GLU glu = new GLU();
//        GLUquadric q = glu.gluNewQuadric();
//        glu.gluSphere(q, BODY_RADIUS, SLICES, STACKS);
//    }

    static void Kerucut(GL gl) {
        float BODY_LENGTH = 2f;
        float BODY_RADIUS = 0f;
        float BODY_RADIUS2 = 1f;
        int SLICES = 30;
        int STACKS = 30;
        GLU glu = new GLU();
        GLUquadric q = glu.gluNewQuadric();
        glu.gluCylinder(q, BODY_RADIUS2, BODY_RADIUS, BODY_LENGTH, SLICES, STACKS);
        glu.gluDisk(q, 0.0f, BODY_RADIUS2, SLICES, STACKS); //lingkaran untuk tutup atas
        gl.glTranslatef(0.0f, 0.0f, BODY_LENGTH);
        glu.gluDisk(q, 0.0f, BODY_RADIUS, SLICES, STACKS); //lingkaran untuk tutup bawah
    }

    static void Tabung(GL gl) {
        {

            float BODY_RADIUS = 0.5f;
            float BODY_LENGTH = 0.5f * 5;
            int SLICES = 50;
            int STACKS = 60;
            GLU glu = new GLU();
            GLUquadric q = glu.gluNewQuadric();
            glu.gluCylinder(q, BODY_RADIUS, BODY_RADIUS, BODY_LENGTH, SLICES, STACKS);
            glu.gluDisk(q, 0.0f, BODY_RADIUS, SLICES, STACKS); //lingkaran untuk tutup atas
            gl.glTranslatef(0f, 0.0f, BODY_LENGTH);
            glu.gluDisk(q, 0f, BODY_RADIUS, SLICES, STACKS); //lingkaran untuk tutup bawah
        }
    }

    static void Tabung1(GL gl) {
        {

            float BODY_RADIUS = 0.5f * 2;
            float BODY_LENGTH = 0.5f * 5;
            int SLICES = 50;
            int STACKS = 60;
            GLU glu = new GLU();
            GLUquadric q = glu.gluNewQuadric();
            glu.gluCylinder(q, BODY_RADIUS, BODY_RADIUS, BODY_LENGTH, SLICES, STACKS);
            glu.gluDisk(q, 0.0f, BODY_RADIUS, SLICES, STACKS); //lingkaran untuk tutup atas
            gl.glTranslatef(0f, 0.0f, BODY_LENGTH);
            glu.gluDisk(q, 0f, BODY_RADIUS, SLICES, STACKS); //lingkaran untuk tutup bawah
        }
    }

    static void Tabung2(GL gl) {//foot
        {

            float BODY_RADIUS = 0.3f;
            float BODY_LENGTH = 0.5f * 5;
            int SLICES = 50;
            int STACKS = 60;
            GLU glu = new GLU();
            GLUquadric q = glu.gluNewQuadric();
            glu.gluCylinder(q, BODY_RADIUS, BODY_RADIUS, BODY_LENGTH, SLICES, STACKS);
            glu.gluDisk(q, 0.0f, BODY_RADIUS, SLICES, STACKS); //lingkaran untuk tutup atas
            gl.glTranslatef(0f, 0.0f, BODY_LENGTH);
            glu.gluDisk(q, 0f, BODY_RADIUS, SLICES, STACKS); //lingkaran untuk tutup bawah
        }
    }

    static void Tabung3(GL gl) {//right hand
        {
            float BODY_RADIUS = 0.3f;
            float BODY_LENGTH = 1.5f;
            int SLICES = 50;
            int STACKS = 60;
            GLU glu = new GLU();
            GLUquadric q = glu.gluNewQuadric();
            glu.gluCylinder(q, BODY_RADIUS, BODY_RADIUS, BODY_LENGTH, SLICES, STACKS);
            glu.gluDisk(q, 0.0f, BODY_RADIUS, SLICES, STACKS); //lingkaran untuk tutup atas
            gl.glTranslatef(0f, 0.0f, BODY_LENGTH);
            glu.gluDisk(q, 0f, BODY_RADIUS, SLICES, STACKS); //lingkaran untuk tutup bawah
        }
    }

    static void Tabung4(GL gl) {
        {

            float BODY_RADIUS = 0.3f;
            float BODY_LENGTH = 0.1f;
            int SLICES = 50;
            int STACKS = 60;
            GLU glu = new GLU();
            GLUquadric q = glu.gluNewQuadric();
            glu.gluCylinder(q, BODY_RADIUS, BODY_RADIUS, BODY_LENGTH, SLICES, STACKS);
            glu.gluDisk(q, 0.0f, BODY_RADIUS, SLICES, STACKS); //lingkaran untuk tutup atas
            gl.glTranslatef(0f, 0.0f, BODY_LENGTH);
            glu.gluDisk(q, 0f, BODY_RADIUS, SLICES, STACKS); //lingkaran untuk tutup bawah
        }
    }

    static void Setengah(GL gl) {//head
        double clip_plane1[] = {0.0, 0.0, -1.0, 1};
        gl.glClipPlane(GL.GL_CLIP_PLANE1, clip_plane1, 0);
        gl.glEnable(GL.GL_CLIP_PLANE1);
// drawing a sphere
        GLU glu = new GLU();
        GLUquadric qd = glu.gluNewQuadric();
        glu.gluSphere(qd, 1.0f, 10, 10);
        glu.gluDisk(qd, 1f, 0f, 10, 10);
        glu.gluDeleteQuadric(qd);
        gl.glDisable(GL.GL_CLIP_PLANE1);
    }
}
