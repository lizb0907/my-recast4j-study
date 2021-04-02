/*
Copyright (c) 2009-2010 Mikko Mononen memon@inside.org
recast4j copyright (c) 2015-2019 Piotr Piastucki piotr@jtilia.org

This software is provided 'as-is', without any express or implied
warranty.  In no event will the authors be held liable for any damages
arising from the use of this software.
Permission is granted to anyone to use this software for any purpose,
including commercial applications, and to alter it and redistribute it
freely, subject to the following restrictions:
1. The origin of this software must not be misrepresented; you must not
 claim that you wrote the original software. If you use this software
 in a product, an acknowledgment in the product documentation would be
 appreciated but is not required.
2. Altered source versions must be plainly marked as such, and must not be
 misrepresented as being the original software.
3. This notice may not be removed or altered from any source distribution.
*/
package org.recast4j.demo.draw;

public class DebugDraw {

    private final GLCheckerTexture g_tex = new GLCheckerTexture();
    private final OpenGLDraw openGlDraw = new ModernOpenGLDraw();

    public void begin(DebugDrawPrimitives prim) {
        begin(prim, 1f);
    }

    public void debugDrawCylinderWire(float minx, float miny, float minz, float maxx, float maxy, float maxz, int col,
            float lineWidth) {
        begin(DebugDrawPrimitives.LINES, lineWidth);
        appendCylinderWire(minx, miny, minz, maxx, maxy, maxz, col);
        end();
    }

    private final int CYLINDER_NUM_SEG = 16;
    private final float[] cylinderDir = new float[CYLINDER_NUM_SEG * 2];
    private boolean cylinderInit = false;

    private void initCylinder() {
        if (!cylinderInit) {
            cylinderInit = true;
            for (int i = 0; i < CYLINDER_NUM_SEG; ++i) {
                float a = (float) (i * Math.PI * 2 / CYLINDER_NUM_SEG);
                cylinderDir[i * 2] = (float) Math.cos(a);
                cylinderDir[i * 2 + 1] = (float) Math.sin(a);
            }
        }
    }

    void appendCylinderWire(float minx, float miny, float minz, float maxx, float maxy, float maxz, int col) {

        initCylinder();

        float cx = (maxx + minx) / 2;
        float cz = (maxz + minz) / 2;
        float rx = (maxx - minx) / 2;
        float rz = (maxz - minz) / 2;

        for (int i = 0, j = CYLINDER_NUM_SEG - 1; i < CYLINDER_NUM_SEG; j = i++) {
            vertex(cx + cylinderDir[j * 2 + 0] * rx, miny, cz + cylinderDir[j * 2 + 1] * rz, col);
            vertex(cx + cylinderDir[i * 2 + 0] * rx, miny, cz + cylinderDir[i * 2 + 1] * rz, col);
            vertex(cx + cylinderDir[j * 2 + 0] * rx, maxy, cz + cylinderDir[j * 2 + 1] * rz, col);
            vertex(cx + cylinderDir[i * 2 + 0] * rx, maxy, cz + cylinderDir[i * 2 + 1] * rz, col);
        }
        for (int i = 0; i < CYLINDER_NUM_SEG; i += CYLINDER_NUM_SEG / 4) {
            vertex(cx + cylinderDir[i * 2 + 0] * rx, miny, cz + cylinderDir[i * 2 + 1] * rz, col);
            vertex(cx + cylinderDir[i * 2 + 0] * rx, maxy, cz + cylinderDir[i * 2 + 1] * rz, col);
        }
    }

    public void debugDrawBoxWire(float minx, float miny, float minz, float maxx, float maxy, float maxz, int col,
            float lineWidth) {

        begin(DebugDrawPrimitives.LINES, lineWidth);
        appendBoxWire(minx, miny, minz, maxx, maxy, maxz, col);
        end();
    }

    void appendBoxWire(float minx, float miny, float minz, float maxx, float maxy, float maxz, int col) {
        // Top
        vertex(minx, miny, minz, col);
        vertex(maxx, miny, minz, col);
        vertex(maxx, miny, minz, col);
        vertex(maxx, miny, maxz, col);
        vertex(maxx, miny, maxz, col);
        vertex(minx, miny, maxz, col);
        vertex(minx, miny, maxz, col);
        vertex(minx, miny, minz, col);

        // bottom
        vertex(minx, maxy, minz, col);
        vertex(maxx, maxy, minz, col);
        vertex(maxx, maxy, minz, col);
        vertex(maxx, maxy, maxz, col);
        vertex(maxx, maxy, maxz, col);
        vertex(minx, maxy, maxz, col);
        vertex(minx, maxy, maxz, col);
        vertex(minx, maxy, minz, col);

        // Sides
        vertex(minx, miny, minz, col);
        vertex(minx, maxy, minz, col);
        vertex(maxx, miny, minz, col);
        vertex(maxx, maxy, minz, col);
        vertex(maxx, miny, maxz, col);
        vertex(maxx, maxy, maxz, col);
        vertex(minx, miny, maxz, col);
        vertex(minx, maxy, maxz, col);
    }

    void appendBox(float minx, float miny, float minz, float maxx, float maxy, float maxz, int[] fcol) {
        float[][] verts = { { minx, miny, minz }, { maxx, miny, minz }, { maxx, miny, maxz }, { minx, miny, maxz },
                { minx, maxy, minz }, { maxx, maxy, minz }, { maxx, maxy, maxz }, { minx, maxy, maxz } };
        int[] inds = { 7, 6, 5, 4, 0, 1, 2, 3, 1, 5, 6, 2, 3, 7, 4, 0, 2, 6, 7, 3, 0, 4, 5, 1, };

        int in = 0;
        for (int i = 0; i < 6; ++i) {
            vertex(verts[inds[in]], fcol[i]);
            in++;
            vertex(verts[inds[in]], fcol[i]);
            in++;
            vertex(verts[inds[in]], fcol[i]);
            in++;
            vertex(verts[inds[in]], fcol[i]);
            in++;
        }
    }

    public void debugDrawArc(float x0, float y0, float z0, float x1, float y1, float z1, float h, float as0, float as1,
            int col, float lineWidth) {
        begin(DebugDrawPrimitives.LINES, lineWidth);
        appendArc(x0, y0, z0, x1, y1, z1, h, as0, as1, col);
        end();
    }

    public void begin(DebugDrawPrimitives prim, float size) {
        getOpenGlDraw().begin(prim, size);
    }

    void vertex(float[] pos, int color) {
        getOpenGlDraw().vertex(pos, color);
    }

    public void vertex(float x, float y, float z, int color) {
        getOpenGlDraw().vertex(x, y, z, color);
    }

    void vertex(float[] pos, int color, float[] uv) {
        getOpenGlDraw().vertex(pos, color, uv);
    }

    void vertex(float x, float y, float z, int color, float u, float v) {
        getOpenGlDraw().vertex(x, y, z, color, u, v);
    }

    public void end() {
        getOpenGlDraw().end();
    }

    public void debugDrawCircle(float x, float y, float z, float r, int col, float lineWidth) {
        begin(DebugDrawPrimitives.LINES, lineWidth);
        appendCircle(x, y, z, r, col);
        end();
    }

    private boolean circleInit = false;
    private final static int CIRCLE_NUM_SEG = 40;
    private final float[] circeDir = new float[CIRCLE_NUM_SEG * 2];

    public void appendCircle(float x, float y, float z, float r, int col) {
        if (!circleInit) {
            circleInit = true;
            for (int i = 0; i < CIRCLE_NUM_SEG; ++i) {
                float a = (float) (i * Math.PI * 2 / CIRCLE_NUM_SEG);
                circeDir[i * 2] = (float) Math.cos(a);
                circeDir[i * 2 + 1] = (float) Math.sin(a);
            }
        }
        for (int i = 0, j = CIRCLE_NUM_SEG - 1; i < CIRCLE_NUM_SEG; j = i++) {
            vertex(x + circeDir[j * 2 + 0] * r, y, z + circeDir[j * 2 + 1] * r, col);
            vertex(x + circeDir[i * 2 + 0] * r, y, z + circeDir[i * 2 + 1] * r, col);
        }
    }

    private static final int NUM_ARC_PTS = 8;
    private static final float PAD = 0.05f;
    private static final float ARC_PTS_SCALE = (1.0f - PAD * 2) / NUM_ARC_PTS;

    public void appendArc(float x0, float y0, float z0, float x1, float y1, float z1, float h, float as0, float as1,
            int col) {
        float dx = x1 - x0;
        float dy = y1 - y0;
        float dz = z1 - z0;
        float len = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
        float[] prev = new float[3];
        evalArc(x0, y0, z0, dx, dy, dz, len * h, PAD, prev);
        for (int i = 1; i <= NUM_ARC_PTS; ++i) {
            float u = PAD + i * ARC_PTS_SCALE;
            float[] pt = new float[3];
            evalArc(x0, y0, z0, dx, dy, dz, len * h, u, pt);
            vertex(prev[0], prev[1], prev[2], col);
            vertex(pt[0], pt[1], pt[2], col);
            prev[0] = pt[0];
            prev[1] = pt[1];
            prev[2] = pt[2];
        }

        // End arrows
        if (as0 > 0.001f) {
            float[] p = new float[3], q = new float[3];
            evalArc(x0, y0, z0, dx, dy, dz, len * h, PAD, p);
            evalArc(x0, y0, z0, dx, dy, dz, len * h, PAD + 0.05f, q);
            appendArrowHead(p, q, as0, col);
        }

        if (as1 > 0.001f) {
            float[] p = new float[3], q = new float[3];
            evalArc(x0, y0, z0, dx, dy, dz, len * h, 1 - PAD, p);
            evalArc(x0, y0, z0, dx, dy, dz, len * h, 1 - (PAD + 0.05f), q);
            appendArrowHead(p, q, as1, col);
        }
    }

    private void evalArc(float x0, float y0, float z0, float dx, float dy, float dz, float h, float u, float[] res) {
        res[0] = x0 + dx * u;
        res[1] = y0 + dy * u + h * (1 - (u * 2 - 1) * (u * 2 - 1));
        res[2] = z0 + dz * u;
    }

    public void debugDrawCross(float x, float y, float z, float size, int col, float lineWidth) {
        begin(DebugDrawPrimitives.LINES, lineWidth);
        appendCross(x, y, z, size, col);
        end();
    }

    private void appendCross(float x, float y, float z, float s, int col) {
        vertex(x - s, y, z, col);
        vertex(x + s, y, z, col);
        vertex(x, y - s, z, col);
        vertex(x, y + s, z, col);
        vertex(x, y, z - s, col);
        vertex(x, y, z + s, col);
    }

    public void debugDrawBox(float minx, float miny, float minz, float maxx, float maxy, float maxz, int[] fcol) {
        begin(DebugDrawPrimitives.QUADS);
        appendBox(minx, miny, minz, maxx, maxy, maxz, fcol);
        end();
    }

    public void debugDrawCylinder(float minx, float miny, float minz, float maxx, float maxy, float maxz, int col) {
        begin(DebugDrawPrimitives.TRIS);
        appendCylinder(minx, miny, minz, maxx, maxy, maxz, col);
        end();
    }

    void appendCylinder(float minx, float miny, float minz, float maxx, float maxy, float maxz, int col) {
        initCylinder();

        int col2 = duMultCol(col, 160);

        float cx = (maxx + minx) / 2;
        float cz = (maxz + minz) / 2;
        float rx = (maxx - minx) / 2;
        float rz = (maxz - minz) / 2;

        for (int i = 2; i < CYLINDER_NUM_SEG; ++i) {
            int a = 0, b = i - 1, c = i;
            vertex(cx + cylinderDir[a * 2 + 0] * rx, miny, cz + cylinderDir[a * 2 + 1] * rz, col2);
            vertex(cx + cylinderDir[b * 2 + 0] * rx, miny, cz + cylinderDir[b * 2 + 1] * rz, col2);
            vertex(cx + cylinderDir[c * 2 + 0] * rx, miny, cz + cylinderDir[c * 2 + 1] * rz, col2);
        }
        for (int i = 2; i < CYLINDER_NUM_SEG; ++i) {
            int a = 0, b = i, c = i - 1;
            vertex(cx + cylinderDir[a * 2 + 0] * rx, maxy, cz + cylinderDir[a * 2 + 1] * rz, col);
            vertex(cx + cylinderDir[b * 2 + 0] * rx, maxy, cz + cylinderDir[b * 2 + 1] * rz, col);
            vertex(cx + cylinderDir[c * 2 + 0] * rx, maxy, cz + cylinderDir[c * 2 + 1] * rz, col);
        }
        for (int i = 0, j = CYLINDER_NUM_SEG - 1; i < CYLINDER_NUM_SEG; j = i++) {
            vertex(cx + cylinderDir[i * 2 + 0] * rx, miny, cz + cylinderDir[i * 2 + 1] * rz, col2);
            vertex(cx + cylinderDir[j * 2 + 0] * rx, miny, cz + cylinderDir[j * 2 + 1] * rz, col2);
            vertex(cx + cylinderDir[j * 2 + 0] * rx, maxy, cz + cylinderDir[j * 2 + 1] * rz, col);

            vertex(cx + cylinderDir[i * 2 + 0] * rx, miny, cz + cylinderDir[i * 2 + 1] * rz, col2);
            vertex(cx + cylinderDir[j * 2 + 0] * rx, maxy, cz + cylinderDir[j * 2 + 1] * rz, col);
            vertex(cx + cylinderDir[i * 2 + 0] * rx, maxy, cz + cylinderDir[i * 2 + 1] * rz, col);
        }
    }

    public void debugDrawArrow(float x0, float y0, float z0, float x1, float y1, float z1, float as0, float as1,
            int col, float lineWidth) {

        begin(DebugDrawPrimitives.LINES, lineWidth);
        appendArrow(x0, y0, z0, x1, y1, z1, as0, as1, col);
        end();
    }

    public void appendArrow(float x0, float y0, float z0, float x1, float y1, float z1, float as0, float as1, int col) {

        vertex(x0, y0, z0, col);
        vertex(x1, y1, z1, col);

        // End arrows
        float[] p = new float[] { x0, y0, z0 };
        float[] q = new float[] { x1, y1, z1 };
        if (as0 > 0.001f)
            appendArrowHead(p, q, as0, col);
        if (as1 > 0.001f)
            appendArrowHead(q, p, as1, col);
    }

    void appendArrowHead(float[] p, float[] q, float s, int col) {
        float eps = 0.001f;
        if (vdistSqr(p, q) < eps * eps) {
            return;
        }
        float[] ax = new float[3], ay = { 0, 1, 0 }, az = new float[3];
        vsub(az, q, p);
        vnormalize(az);
        vcross(ax, ay, az);
        vcross(ay, az, ax);
        vnormalize(ay);

        vertex(p, col);
        // vertex(p[0]+az[0]*s+ay[0]*s/2, p[1]+az[1]*s+ay[1]*s/2, p[2]+az[2]*s+ay[2]*s/2, col);
        vertex(p[0] + az[0] * s + ax[0] * s / 3, p[1] + az[1] * s + ax[1] * s / 3, p[2] + az[2] * s + ax[2] * s / 3,
                col);

        vertex(p, col);
        // vertex(p[0]+az[0]*s-ay[0]*s/2, p[1]+az[1]*s-ay[1]*s/2, p[2]+az[2]*s-ay[2]*s/2, col);
        vertex(p[0] + az[0] * s - ax[0] * s / 3, p[1] + az[1] * s - ax[1] * s / 3, p[2] + az[2] * s - ax[2] * s / 3,
                col);

    }

    void vcross(float[] dest, float[] v1, float[] v2) {
        dest[0] = v1[1] * v2[2] - v1[2] * v2[1];
        dest[1] = v1[2] * v2[0] - v1[0] * v2[2];
        dest[2] = v1[0] * v2[1] - v1[1] * v2[0];
    }

    void vnormalize(float[] v) {
        float d = (float) (1.0f / Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]));
        v[0] *= d;
        v[1] *= d;
        v[2] *= d;
    }

    void vsub(float[] dest, float[] v1, float[] v2) {
        dest[0] = v1[0] - v2[0];
        dest[1] = v1[1] - v2[1];
        dest[2] = v1[2] - v2[2];
    }

    float vdistSqr(float[] v1, float[] v2) {
        float x = v1[0] - v2[0];
        float y = v1[1] - v2[1];
        float z = v1[2] - v2[2];
        return x * x + y * y + z * z;
    }

    public static int areaToCol(int area) {
        if (area == 0) {
            return duRGBA(0, 192, 255, 255);
        } else {
            return duIntToCol(area, 255);
        }
    }

    /*
    public static int areaToCol(int area) {
        switch (area) {
        // Ground (0) : light blue
        case SampleAreaModifications.SAMPLE_POLYAREA_TYPE_GROUND:
            return duRGBA(0, 192, 255, 255);
        // Water : blue
        case SampleAreaModifications.SAMPLE_POLYAREA_TYPE_WATER:
            return duRGBA(0, 0, 255, 255);
        // Road : brown
        case SampleAreaModifications.SAMPLE_POLYAREA_TYPE_ROAD:
            return duRGBA(50, 20, 12, 255);
        // Door : cyan
        case SampleAreaModifications.SAMPLE_POLYAREA_TYPE_DOOR:
            return duRGBA(0, 255, 255, 255);
        // Grass : green
        case SampleAreaModifications.SAMPLE_POLYAREA_TYPE_GRASS:
            return duRGBA(0, 255, 0, 255);
        // Jump : yellow
        case SampleAreaModifications.SAMPLE_POLYAREA_TYPE_JUMP:
            return duRGBA(255, 255, 0, 255);
        // Unexpected : red
        default:
            return duRGBA(255, 0, 0, 255);
        }
    }
    */
    public static int duRGBA(int r, int g, int b, int a) {
        return (r) | (g << 8) | (b << 16) | (a << 24);
    }

    public static int duLerpCol(int ca, int cb, int u) {
        int ra = ca & 0xff;
        int ga = (ca >> 8) & 0xff;
        int ba = (ca >> 16) & 0xff;
        int aa = (ca >> 24) & 0xff;
        int rb = cb & 0xff;
        int gb = (cb >> 8) & 0xff;
        int bb = (cb >> 16) & 0xff;
        int ab = (cb >> 24) & 0xff;

        int r = (ra * (255 - u) + rb * u) / 255;
        int g = (ga * (255 - u) + gb * u) / 255;
        int b = (ba * (255 - u) + bb * u) / 255;
        int a = (aa * (255 - u) + ab * u) / 255;
        return duRGBA(r, g, b, a);
    }

    public static int bit(int a, int b) {
        return (a & (1 << b)) >>> b;
    }

    public static int duIntToCol(int i, int a) {
        int r = bit(i, 1) + bit(i, 3) * 2 + 1;
        int g = bit(i, 2) + bit(i, 4) * 2 + 1;
        int b = bit(i, 0) + bit(i, 5) * 2 + 1;
        return duRGBA(r * 63, g * 63, b * 63, a);
    }

    public static void duCalcBoxColors(int[] colors, int colTop, int colSide) {
        colors[0] = duMultCol(colTop, 250);
        colors[1] = duMultCol(colSide, 140);
        colors[2] = duMultCol(colSide, 165);
        colors[3] = duMultCol(colSide, 217);
        colors[4] = duMultCol(colSide, 165);
        colors[5] = duMultCol(colSide, 217);
    }

    public static int duMultCol(int col, int d) {
        int r = col & 0xff;
        int g = (col >> 8) & 0xff;
        int b = (col >> 16) & 0xff;
        int a = (col >> 24) & 0xff;
        return duRGBA((r * d) >> 8, (g * d) >> 8, (b * d) >> 8, a);
    }

    public static int duTransCol(int c, int a) {
        return (a << 24) | (c & 0x00ffffff);
    }

    public static int duDarkenCol(int col) {
        return ((col >> 1) & 0x007f7f7f) | (col & 0xff000000);
    }

    public void fog(float start, float end) {
        getOpenGlDraw().fog(start, end);
    }

    public void fog(boolean state) {
        getOpenGlDraw().fog(state);
    }

    public void depthMask(boolean state) {
        getOpenGlDraw().depthMask(state);
    }

    void texture(boolean state) {
        getOpenGlDraw().texture(g_tex, state);
    }

    public void init(float fogDistance) {
        getOpenGlDraw().init();
    }

    public void clear() {
        getOpenGlDraw().clear();
    }

    public float[] projectionMatrix(float fovy, float aspect, float near, float far) {
        float[] projectionMatrix = new float[16];
        GLU.glhPerspectivef2(projectionMatrix, fovy, aspect, near, far);
        getOpenGlDraw().projectionMatrix(projectionMatrix);
        return projectionMatrix;
    }

    public float[] viewMatrix(float[] cameraPos, float[] cameraEulers) {
        float[] rx = GLU.build_4x4_rotation_matrix(cameraEulers[0], 1, 0, 0);
        float[] ry = GLU.build_4x4_rotation_matrix(cameraEulers[1], 0, 1, 0);
        float[] r = GLU.mul(rx, ry);
        float[] t = new float[16];
        t[0] = t[5] = t[10] = t[15] = 1;
        t[12] = -cameraPos[0];
        t[13] = -cameraPos[1];
        t[14] = -cameraPos[2];
        float[] viewMatrix = GLU.mul(r, t);
        getOpenGlDraw().viewMatrix(viewMatrix);
        return viewMatrix;
    }

    private OpenGLDraw getOpenGlDraw() {
        return openGlDraw;
    }
}
