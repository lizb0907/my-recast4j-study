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

import java.util.ArrayList;
import java.util.List;

/**
 * Defines a navigation mesh tile.
 *
 * 基于tile划分的N个邻接凸多边形集合（多个多边形）
 *
 * <p>
 *     mesh tile 和 solo区别:
 *     1.mesh tile:
 *        是由一块块tile组成(瓷砖)，每一块是一个正方形的区域，对应一个Tile，把这些Tile对应的mesh组合起来，得到最终的mesh.
 *     2.solo mesh:
 *        是直接生成一个大的mesh
 * </p>
 *
 */
public class MeshTile {

    final int index;

    /**
     * Counter describing modifications to the tile
     *
     * 计数器，描述对tile的修改
     */
    int salt;

    /**
     * The tile data
     *
     * 数据
     */
    public MeshData data;

    /**
     * The tile links
     *
     * tile的链接列表
     */
    public final List<Link> links = new ArrayList<>();

    /**
     * Index to the next free link.
     *
     * 下一个空闲链接索引（指示实体未链接到任何内容的值）
     */
    int linksFreeList = NavMesh.DT_NULL_LINK;


    /** Tile flags. (See: #dtTileFlags) */
    int flags;

    /**
     * The next free tile, or the next tile in the spatial grid.
     *
     * 下一个空闲的tile，或者下一个空间网格tile
     */
    MeshTile next;

    public MeshTile(int index) {
        this.index = index;
    }

}
