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

import java.util.List;

import org.recast4j.demo.builder.SampleAreaModifications;
import org.recast4j.demo.geom.DemoInputGeomProvider;
import org.recast4j.demo.geom.DemoOffMeshConnection;
import org.recast4j.demo.sample.Sample;
import org.recast4j.demo.settings.SettingsUI;
import org.recast4j.detour.NavMesh;
import org.recast4j.detour.NavMeshQuery;
import org.recast4j.recast.ConvexVolume;
import org.recast4j.recast.RecastBuilder.RecastBuilderResult;

public class NavMeshRenderer {

    private final RecastDebugDraw debugDraw;

    private final int navMeshDrawFlags = RecastDebugDraw.DRAWNAVMESH_OFFMESHCONS
            | RecastDebugDraw.DRAWNAVMESH_CLOSEDLIST;

    public NavMeshRenderer(RecastDebugDraw debugDraw) {
        this.debugDraw = debugDraw;
    }

    public RecastDebugDraw getDebugDraw() {
        return debugDraw;
    }

    public void render(Sample sample) {
        if (sample == null) {
            return;
        }
        NavMeshQuery navQuery = sample.getNavMeshQuery();
        DemoInputGeomProvider geom = sample.getInputGeom();
        List<RecastBuilderResult> rcBuilderResults = sample.getRecastResults();
        NavMesh navMesh = sample.getNavMesh();
        SettingsUI settingsUI = sample.getSettingsUI();
        debugDraw.fog(true);
        debugDraw.depthMask(true);
        DrawMode drawMode = settingsUI.getDrawMode();

        float texScale = 1.0f / (settingsUI.getCellSize() * 10.0f);
        float m_agentMaxSlope = settingsUI.getAgentMaxSlope();

        if (drawMode != DrawMode.DRAWMODE_NAVMESH_TRANS) {
            // Draw mesh
            debugDraw.debugDrawTriMeshSlope(geom.vertices, geom.faces, geom.normals, m_agentMaxSlope, texScale);
            drawOffMeshConnections(geom, false);
        }

        debugDraw.fog(false);
        debugDraw.depthMask(false);
        // Draw bounds
        float[] bmin = geom.getMeshBoundsMin();
        float[] bmax = geom.getMeshBoundsMax();
        debugDraw.debugDrawBoxWire(bmin[0], bmin[1], bmin[2], bmax[0], bmax[1], bmax[2],
                DebugDraw.duRGBA(255, 255, 255, 128), 1.0f);
        debugDraw.begin(DebugDrawPrimitives.POINTS, 5.0f);
        debugDraw.vertex(bmin[0], bmin[1], bmin[2], DebugDraw.duRGBA(255, 255, 255, 128));
        debugDraw.end();

        if (navMesh != null && navQuery != null
                && (drawMode == DrawMode.DRAWMODE_NAVMESH || drawMode == DrawMode.DRAWMODE_NAVMESH_TRANS
                        || drawMode == DrawMode.DRAWMODE_NAVMESH_BVTREE || drawMode == DrawMode.DRAWMODE_NAVMESH_NODES
                        || drawMode == DrawMode.DRAWMODE_NAVMESH_INVIS
                        || drawMode == DrawMode.DRAWMODE_NAVMESH_PORTALS)) {
            if (drawMode != DrawMode.DRAWMODE_NAVMESH_INVIS) {
                debugDraw.debugDrawNavMeshWithClosedList(navMesh, navQuery, navMeshDrawFlags);
            }
            if (drawMode == DrawMode.DRAWMODE_NAVMESH_BVTREE) {
                debugDraw.debugDrawNavMeshBVTree(navMesh);
            }
            if (drawMode == DrawMode.DRAWMODE_NAVMESH_PORTALS) {
                debugDraw.debugDrawNavMeshPortals(navMesh);
            }
            if (drawMode == DrawMode.DRAWMODE_NAVMESH_NODES) {
                debugDraw.debugDrawNavMeshNodes(navQuery);
                debugDraw.debugDrawNavMeshPolysWithFlags(navMesh, SampleAreaModifications.SAMPLE_POLYFLAGS_DISABLED,
                        DebugDraw.duRGBA(0, 0, 0, 128));
            }
        }

        debugDraw.depthMask(true);

        for (RecastBuilderResult rcBuilderResult : rcBuilderResults) {
            if (rcBuilderResult.getCompactHeightfield() != null && drawMode == DrawMode.DRAWMODE_COMPACT) {
                debugDraw.debugDrawCompactHeightfieldSolid(rcBuilderResult.getCompactHeightfield());
            }
            if (rcBuilderResult.getCompactHeightfield() != null && drawMode == DrawMode.DRAWMODE_COMPACT_DISTANCE) {
                debugDraw.debugDrawCompactHeightfieldDistance(rcBuilderResult.getCompactHeightfield());
            }
            if (rcBuilderResult.getCompactHeightfield() != null && drawMode == DrawMode.DRAWMODE_COMPACT_REGIONS) {
                debugDraw.debugDrawCompactHeightfieldRegions(rcBuilderResult.getCompactHeightfield());
            }
            if (rcBuilderResult.getSolidHeightfield() != null && drawMode == DrawMode.DRAWMODE_VOXELS) {
                debugDraw.fog(true);
                debugDraw.debugDrawHeightfieldSolid(rcBuilderResult.getSolidHeightfield());
                debugDraw.fog(false);
            }
            if (rcBuilderResult.getSolidHeightfield() != null && drawMode == DrawMode.DRAWMODE_VOXELS_WALKABLE) {
                debugDraw.fog(true);
                debugDraw.debugDrawHeightfieldWalkable(rcBuilderResult.getSolidHeightfield());
                debugDraw.fog(false);
            }
            if (rcBuilderResult.getContourSet() != null && drawMode == DrawMode.DRAWMODE_RAW_CONTOURS) {
                debugDraw.depthMask(false);
                debugDraw.debugDrawRawContours(rcBuilderResult.getContourSet(), 1f);
                debugDraw.depthMask(true);
            }
            if (rcBuilderResult.getContourSet() != null && drawMode == DrawMode.DRAWMODE_BOTH_CONTOURS) {
                debugDraw.depthMask(false);
                debugDraw.debugDrawRawContours(rcBuilderResult.getContourSet(), 0.5f);
                debugDraw.debugDrawContours(rcBuilderResult.getContourSet());
                debugDraw.depthMask(true);
            }
            if (rcBuilderResult.getContourSet() != null && drawMode == DrawMode.DRAWMODE_CONTOURS) {
                debugDraw.depthMask(false);
                debugDraw.debugDrawContours(rcBuilderResult.getContourSet());
                debugDraw.depthMask(true);
            }
            if (rcBuilderResult.getCompactHeightfield() != null && drawMode == DrawMode.DRAWMODE_REGION_CONNECTIONS) {
                debugDraw.debugDrawCompactHeightfieldRegions(rcBuilderResult.getCompactHeightfield());
                debugDraw.depthMask(false);
                if (rcBuilderResult.getContourSet() != null) {
                    debugDraw.debugDrawRegionConnections(rcBuilderResult.getContourSet());
                }
                debugDraw.depthMask(true);
            }
            if (rcBuilderResult.getMesh() != null && drawMode == DrawMode.DRAWMODE_POLYMESH) {
                debugDraw.depthMask(false);
                debugDraw.debugDrawPolyMesh(rcBuilderResult.getMesh());
                debugDraw.depthMask(true);
            }
            if (rcBuilderResult.getMeshDetail() != null && drawMode == DrawMode.DRAWMODE_POLYMESH_DETAIL) {
                debugDraw.depthMask(false);
                debugDraw.debugDrawPolyMeshDetail(rcBuilderResult.getMeshDetail());
                debugDraw.depthMask(true);
            }
        }

        drawConvexVolumes(geom);
    }

    public void drawOffMeshConnections(DemoInputGeomProvider geom, boolean hilight) {
        int conColor = DebugDraw.duRGBA(192, 0, 128, 192);
        int baseColor = DebugDraw.duRGBA(0, 0, 0, 64);
        debugDraw.depthMask(false);

        debugDraw.begin(DebugDrawPrimitives.LINES, 2.0f);
        for (DemoOffMeshConnection con : geom.getOffMeshConnections()) {

            float[] v = con.verts;
            debugDraw.vertex(v[0], v[1], v[2], baseColor);
            debugDraw.vertex(v[0], v[1] + 0.2f, v[2], baseColor);

            debugDraw.vertex(v[3], v[4], v[5], baseColor);
            debugDraw.vertex(v[3], v[4] + 0.2f, v[5], baseColor);

            debugDraw.appendCircle(v[0], v[1] + 0.1f, v[2], con.radius, baseColor);
            debugDraw.appendCircle(v[3], v[4] + 0.1f, v[5], con.radius, baseColor);

            if (hilight) {
                debugDraw.appendArc(v[0], v[1], v[2], v[3], v[4], v[5], 0.25f, con.bidir ? 0.6f : 0.0f, 0.6f, conColor);
            }
        }
        debugDraw.end();

        debugDraw.depthMask(true);
    }

    void drawConvexVolumes(DemoInputGeomProvider geom) {
        debugDraw.depthMask(false);

        debugDraw.begin(DebugDrawPrimitives.TRIS);

        for (int i = 0; i < geom.convexVolumes().size(); ++i) {
            ConvexVolume vol = geom.convexVolumes().get(i);
            int col = DebugDraw.duTransCol(DebugDraw.areaToCol(vol.areaMod.getMaskedValue()), 32);
            for (int j = 0, k = vol.verts.length - 3; j < vol.verts.length; k = j, j += 3) {
                float[] va = new float[] { vol.verts[k], vol.verts[k + 1], vol.verts[k + 2] };
                float[] vb = new float[] { vol.verts[j], vol.verts[j + 1], vol.verts[j + 2] };

                debugDraw.vertex(vol.verts[0], vol.hmax, vol.verts[2], col);
                debugDraw.vertex(vb[0], vol.hmax, vb[2], col);
                debugDraw.vertex(va[0], vol.hmax, va[2], col);

                debugDraw.vertex(va[0], vol.hmin, va[2], DebugDraw.duDarkenCol(col));
                debugDraw.vertex(va[0], vol.hmax, va[2], col);
                debugDraw.vertex(vb[0], vol.hmax, vb[2], col);

                debugDraw.vertex(va[0], vol.hmin, va[2], DebugDraw.duDarkenCol(col));
                debugDraw.vertex(vb[0], vol.hmax, vb[2], col);
                debugDraw.vertex(vb[0], vol.hmin, vb[2], DebugDraw.duDarkenCol(col));
            }
        }

        debugDraw.end();

        debugDraw.begin(DebugDrawPrimitives.LINES, 2.0f);
        for (int i = 0; i < geom.convexVolumes().size(); ++i) {
            ConvexVolume vol = geom.convexVolumes().get(i);
            int col = DebugDraw.duTransCol(DebugDraw.areaToCol(vol.areaMod.getMaskedValue()), 220);
            for (int j = 0, k = vol.verts.length - 3; j < vol.verts.length; k = j, j += 3) {
                float[] va = new float[] { vol.verts[k], vol.verts[k + 1], vol.verts[k + 2] };
                float[] vb = new float[] { vol.verts[j], vol.verts[j + 1], vol.verts[j + 2] };
                debugDraw.vertex(va[0], vol.hmin, va[2], DebugDraw.duDarkenCol(col));
                debugDraw.vertex(vb[0], vol.hmin, vb[2], DebugDraw.duDarkenCol(col));
                debugDraw.vertex(va[0], vol.hmax, va[2], col);
                debugDraw.vertex(vb[0], vol.hmax, vb[2], col);
                debugDraw.vertex(va[0], vol.hmin, va[2], DebugDraw.duDarkenCol(col));
                debugDraw.vertex(va[0], vol.hmax, va[2], col);
            }
        }
        debugDraw.end();

        debugDraw.begin(DebugDrawPrimitives.POINTS, 3.0f);
        for (int i = 0; i < geom.convexVolumes().size(); ++i) {
            ConvexVolume vol = geom.convexVolumes().get(i);
            int col = DebugDraw
                    .duDarkenCol(DebugDraw.duTransCol(DebugDraw.areaToCol(vol.areaMod.getMaskedValue()), 220));
            for (int j = 0; j < vol.verts.length; j += 3) {
                debugDraw.vertex(vol.verts[j + 0], vol.verts[j + 1] + 0.1f, vol.verts[j + 2], col);
                debugDraw.vertex(vol.verts[j + 0], vol.hmin, vol.verts[j + 2], col);
                debugDraw.vertex(vol.verts[j + 0], vol.hmax, vol.verts[j + 2], col);
            }
        }
        debugDraw.end();

        debugDraw.depthMask(true);
    }

}
