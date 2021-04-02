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
package org.recast4j.detour;

/**
 * Defines a polyogn within a dtMeshTile object.
 *
 * 多边形(简称Poly)
 */
public class Poly {

    /**
     * 多边行索引
     */
    public final int index;

    /**
     * The polygon is a standard convex polygon that is part of the surface of the mesh.
     *
     * 标准的凸多边形标识，它是网格表面的一部分
     */
    public static final int DT_POLYTYPE_GROUND = 0;

    /**
     * The polygon is an off-mesh connection consisting of two vertices.
     *
     *由两个顶点组成的非网格多边形
     */
    public static final int DT_POLYTYPE_OFFMESH_CONNECTION = 1;

    /**
     * Index to first link in linked list. (Or #DT_NULL_LINK if there is no link.)
     *
     * 连接链表中第一个索引
     */
    public int firstLink;

    /**
     *  The indices of the polygon's vertices. The actual vertices are located in MeshTile::verts.
     *
     *  多边形顶点的索引。 顶点实际存储在MeshTile::verts
     */
    public final int[] verts;

    /**
     * Packed data representing neighbor polygons references and flags for each edge.
     *
     * 表示每个边的相邻多边形参考和标志的打包数据
     */
    public final int[] neis;

    /**
     * The user defined polygon flags.
     *
     * 用户定义的多边形标志
     */
    public int flags;

    /**
     * The number of vertices in the polygon.
     *
     * 多边形顶点数量
     */
    public int vertCount;

    /**
     * The bit packed area id and polygon type.
     *
     * @note Use the structure's set and get methods to access this value.
     *
     * 位压缩区域和多边形类型
     *
     * 注意：使用使用结构的set和get方法访问此值
     */
    public int areaAndtype;

    public Poly(int index, int maxVertsPerPoly) {
        this.index = index;
        firstLink = NavMesh.DT_NULL_LINK;
        verts = new int[maxVertsPerPoly];
        neis = new int[maxVertsPerPoly];
    }

    /** Sets the user defined area id. [Limit: < #DT_MAX_AREAS] */
    public void setArea(int a) {
        areaAndtype = (areaAndtype & 0xc0) | (a & 0x3f);
    }

    /** Sets the polygon type. (See: #dtPolyTypes.) */
    public void setType(int t) {
        areaAndtype = (areaAndtype & 0x3f) | (t << 6);
    }

    /** Gets the user defined area id. */
    public int getArea() {
        return areaAndtype & 0x3f;
    }

    /** Gets the polygon type. (See: #dtPolyTypes) */
    public int getType() {
        return areaAndtype >> 6;
    }

};
