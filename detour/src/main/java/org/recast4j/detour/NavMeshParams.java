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
 * Configuration parameters used to define multi-tile navigation meshes. The values are used to allocate space during
 * the initialization of a navigation mesh.
 *
 * 定义多tile导航网格的配置参数
 *
 * @see NavMesh
 */
public class NavMeshParams {

    /**
     * The world space origin of the navigation mesh's tile space. [(x, y, z)]
     *
     * tile导航网格世界空间起始点
     */
    public final float[] orig = new float[3];

    /**
     * The width of each tile. (Along the x-axis.)
     *
     * 每个tile的宽度
     */
    public float tileWidth;

    /**
     * The height of each tile. (Along the z-axis.)
     *
     * 每个tile的高度
     */
    public float tileHeight;

    /**
     * The maximum number of tiles the navigation mesh can contain.
     *
     * 导航网格能够容纳的最大tile数量
     */
    public int maxTiles;

    /**
     * The maximum number of polygons each tile can contain.
     *
     * 每个tile包含的多变形最大数量
     */
    public int maxPolys;
}
