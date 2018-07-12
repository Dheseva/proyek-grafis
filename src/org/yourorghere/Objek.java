/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yourorghere;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

/**
 *
 * @author WAHYU
 */
public class Objek {

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

    static void drawLimb(GL gl, GLU glu, GLUquadric q, float swing) {
        // arm or leg
        float amb[] = {1f, 0f, 0f, 1};
        float diff[] = {0.41f, 0.41f, 0.41f, 1};
        float spec[] = {0.11f, 0.11f, 0.11f, 1};
        float shine = 200;
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,
                GL.GL_AMBIENT, amb, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,
                GL.GL_DIFFUSE, diff, 0);

        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,
                GL.GL_SPECULAR, spec, 0);
        gl.glMaterialf(GL.GL_FRONT_AND_BACK,
                GL.GL_SHININESS, shine);
        gl.glPushMatrix();
        gl.glRotatef(swing, 1.0f, 0.0f, 0.0f);
        glu.gluSphere(q, LIMB_RADIUS, SLICES, STACKS);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glRotatef(180.0f + swing, 1.0f, 0.0f, 0.0f);
        glu.gluCylinder(q, LIMB_RADIUS, LIMB_RADIUS, LIMB_LENGTH, SLICES, STACKS);
        gl.glTranslated(0.0f, 0.0f, LIMB_LENGTH);
        glu.gluSphere(q, LIMB_RADIUS, SLICES, STACKS);
        gl.glPopMatrix();
    }

    static void drawEyes(GL gl, GLU glu, GLUquadric q) {
        float amb[] = {0f, 0f, 1f, 1};
        float diff[] = {0.41f, 0.41f, 0.41f, 1};
        float spec[] = {0.11f, 0.11f, 0.11f, 1};
        float shine = 200;
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,
                GL.GL_AMBIENT, amb, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,
                GL.GL_DIFFUSE, diff, 0);

        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,
                GL.GL_SPECULAR, spec, 0);
        gl.glMaterialf(GL.GL_FRONT_AND_BACK,
                GL.GL_SHININESS, shine);
        gl.glPushMatrix();
        gl.glRotatef(-20.0f, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(70.0f, 0.0f, 1.0f, 1.0f);
        gl.glTranslated(0.0f, 0.0f, BODY_RADIUS - 0.8f * LIMB_RADIUS);
        glu.gluSphere(q, LIMB_RADIUS - 0.2f, SLICES, STACKS);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glRotatef(-20.0f, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(-70.0f, 0.0f, 1.0f, 1.0f);
        gl.glTranslated(0.0f, 0.0f, BODY_RADIUS - 0.5f * LIMB_RADIUS);
        glu.gluSphere(q, LIMB_RADIUS - 0.2f, SLICES, STACKS);
        gl.glPopMatrix();
        gl.glPopMatrix();
    }

    static void drawHorn(GL gl, GLU glu, GLUquadric q) {
        float amb[] = {0f, 1f, 0f, 1};
        float diff[] = {0.41f, 0.41f, 0.41f, 1};
        float spec[] = {0.11f, 0.11f, 0.11f, 1};
        float shine = 200;
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,
                GL.GL_AMBIENT, amb, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,
                GL.GL_DIFFUSE, diff, 0);

        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,
                GL.GL_SPECULAR, spec, 0);
        gl.glMaterialf(GL.GL_FRONT_AND_BACK,
                GL.GL_SHININESS, shine);
        gl.glPushMatrix();
        gl.glRotatef(10.0f, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(30.0f, 0.0f, 1.0f, 1.0f);
        gl.glTranslatef(0.0f, 0.0f, BODY_RADIUS - 0.1f);
        glu.gluCylinder(q, HORN_RADIUS, HORN_RADIUS, HORN_LENGTH, SLICES, STACKS);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glRotatef(10.0f, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(-30.0f, 0.0f, 1.0f, 1.0f);
        gl.glTranslatef(0.0f, 0.0f, BODY_RADIUS - 0.1f);
        glu.gluCylinder(q, HORN_RADIUS, HORN_RADIUS, HORN_LENGTH, SLICES, STACKS);
        gl.glPopMatrix();
    }
}
