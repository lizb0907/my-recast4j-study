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
package org.recast4j.recast.geom;

import java.util.List;

import org.recast4j.recast.geom.ChunkyTriMesh.ChunkyTriMeshNode;

public class TriMesh {

    private final float[] vertices;
    private final int[] faces;
    private final ChunkyTriMesh chunkyTriMesh;

    public TriMesh(float[] vertices, int[] faces) {
        this.vertices = vertices;
        this.faces = faces;
        chunkyTriMesh = new ChunkyTriMesh(vertices, faces, faces.length / 3, 256);
    }

    public int[] getTris() {
        return faces;
    }

    public float[] getVerts() {
        return vertices;
    }

    public List<ChunkyTriMeshNode> getChunksOverlappingRect(float[] bmin, float[] bmax) {
        return chunkyTriMesh.getChunksOverlappingRect(bmin, bmax);
    }

}
