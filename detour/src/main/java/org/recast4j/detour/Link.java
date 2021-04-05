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
 * Defines a link between polygons.
 *
 * 定义多边行之间的链接
 *
 * @note This structure is rarely if ever used by the end user.
 * @see MeshTile
 */
public class Link {

    /**
     * Neighbour reference. (The neighbor that is linked to.)
     *
     * 相邻的索引
     */
    public long ref;

    /**
     * Index of the next link
     *
     * 下一个链接索引
     */
    public int next;

    /**
     * Index of the polygon edge that owns this link.
     *
     * 拥有此链接的多边形边的索引
     */
    public int edge;

    /**
     * If a boundary link, defines on which side the link is.
     *
     * 如果是边界链接，定义链接在哪一侧
     */
    int side;

    /**
     * If a boundary link, defines the minimum sub-edge area.
     *
     * 定义最小子边缘区域
     */
    int bmin;

    /**
     * If a boundary link, defines the maximum sub-edge area.
     *
     * 定义最大子边缘区域
     */
    int bmax;

}
