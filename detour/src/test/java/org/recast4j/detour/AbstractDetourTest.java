/*
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
package org.recast4j.detour;

import org.junit.Before;

public abstract class AbstractDetourTest {

    protected final long[] startRefs = { 281474976710696L, 281474976710773L, 281474976710680L, 281474976710753L,
            281474976710733L };

    protected final long[] endRefs = { 281474976710721L, 281474976710767L, 281474976710758L, 281474976710731L,
            281474976710772L };

    protected final float[][] startPoss = { { 22.60652f, 10.197294f, -45.918674f },
            { 22.331268f, 10.197294f, -1.0401875f }, { 18.694363f, 15.803535f, -73.090416f },
            { 0.7453353f, 10.197294f, -5.94005f }, { -20.651257f, 5.904126f, -13.712508f } };

    protected final float[][] endPoss = { { 6.4576626f, 10.197294f, -18.33406f },
            { -5.8023443f, 0.19729415f, 3.008419f }, { 38.423977f, 10.197294f, -0.116066754f },
            { 0.8635526f, 10.197294f, -10.31032f }, { 18.784092f, 10.197294f, 3.0543678f } };

    protected NavMeshQuery query;
    protected NavMesh navmesh;

    @Before
    public void setUp() {
        navmesh = createNavMesh();
        query = new NavMeshQuery(navmesh);
    }

    protected NavMesh createNavMesh() {
        return new NavMesh(new RecastTestMeshBuilder().getMeshData(), 6, 0);
    }

}
