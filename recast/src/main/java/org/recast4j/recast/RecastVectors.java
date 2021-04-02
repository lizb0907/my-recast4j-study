/*
Copyright (c) 2009-2010 Mikko Mononen memon@inside.org
recast4j Copyright (c) 2015-2019 Piotr Piastucki piotr@jtilia.org

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
package org.recast4j.recast;

public class RecastVectors {

    public static void min(float[] a, float[] b, int i) {
        a[0] = Math.min(a[0], b[i + 0]);
        a[1] = Math.min(a[1], b[i + 1]);
        a[2] = Math.min(a[2], b[i + 2]);
    }

    public static void max(float[] a, float[] b, int i) {
        a[0] = Math.max(a[0], b[i + 0]);
        a[1] = Math.max(a[1], b[i + 1]);
        a[2] = Math.max(a[2], b[i + 2]);
    }

    public static void copy(float[] out, float[] in, int i) {
        copy(out, 0, in, i);
    }

    public static void copy(float[] out, float[] in) {
        copy(out, 0, in, 0);
    }

    public static void copy(float[] out, int n, float[] in, int m) {
        out[n] = in[m];
        out[n + 1] = in[m + 1];
        out[n + 2] = in[m + 2];
    }

    public static void add(float[] e0, float[] a, float[] verts, int i) {
        e0[0] = a[0] + verts[i];
        e0[1] = a[1] + verts[i + 1];
        e0[2] = a[2] + verts[i + 2];
    }

    public static void sub(float[] e0, float[] verts, int i, int j) {
        e0[0] = verts[i] - verts[j];
        e0[1] = verts[i + 1] - verts[j + 1];
        e0[2] = verts[i + 2] - verts[j + 2];
    }

    public static void sub(float[] e0, float[] i, float[] verts, int j) {
        e0[0] = i[0] - verts[j];
        e0[1] = i[1] - verts[j + 1];
        e0[2] = i[2] - verts[j + 2];
    }

    public static void cross(float[] dest, float[] v1, float[] v2) {
        dest[0] = v1[1] * v2[2] - v1[2] * v2[1];
        dest[1] = v1[2] * v2[0] - v1[0] * v2[2];
        dest[2] = v1[0] * v2[1] - v1[1] * v2[0];
    }

    public static void normalize(float[] v) {
        float d = (float) (1.0f / Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]));
        v[0] *= d;
        v[1] *= d;
        v[2] *= d;
    }

}