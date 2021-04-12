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

public class Node {

    static int DT_NODE_OPEN = 0x01;
    static int DT_NODE_CLOSED = 0x02;
    /** parent of the node is not adjacent. Found using raycast. */
    static int DT_NODE_PARENT_DETACHED = 0x04;

    public final int index;

    /**
     * Position of the node
     *
     * node的代表点位置信息
     *
     * 多边形寻路，计算距离的时候，是以代表点代替多边形的
     *
     * 例如:
     *    相邻多边形之间公共边的中点，或是起始点
     */
    public float[] pos = new float[3];

    /**
     * Cost from previous node to current node.
     * ps:相当于g值
     */
    float cost;

    /**
     * Cost up to the node
     *
     * ps:路径经过这个Node到终点需要花费的总开销，为cost+启发距离
     *    也就是F值 (f = g + h)
     */
    float total;

    /**
     * Index to parent node
     *
     * 父亲节点索引
     */
    public int pidx;

    /**
     * extra state information. A polyRef can have multiple nodes with different extra info. see DT_MAX_STATES_PER_NODE
     *
     * 额外状态信息。一个poly引用可以具有多个具有不同附加信息的节点
     *
     * 例如：当前节点是跨tile节点
     */
    int state;

    /**
     * Node flags. A combination of dtNodeFlags
     *
     * 这个Node的状态，0=未遍历过，1=已在openList中，2=已在closeList中
     */
    int flags;

    /**
     * Polygon ref the node corresponds to.
     *
     * 节点对应的多边形索引
     */
    long id;

    public Node(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Node [id=" + id + "]";
    }

}
