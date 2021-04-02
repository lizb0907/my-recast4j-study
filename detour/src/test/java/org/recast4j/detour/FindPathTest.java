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

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class FindPathTest extends AbstractDetourTest {

    private final Status[] statuses = { Status.SUCCSESS, Status.PARTIAL_RESULT, Status.SUCCSESS, Status.SUCCSESS,
            Status.SUCCSESS };
    private final long[][] results = {
            { 281474976710696L, 281474976710695L, 281474976710694L, 281474976710703L, 281474976710706L,
                    281474976710705L, 281474976710702L, 281474976710701L, 281474976710714L, 281474976710713L,
                    281474976710712L, 281474976710727L, 281474976710730L, 281474976710717L, 281474976710721L },
            { 281474976710773L, 281474976710772L, 281474976710768L, 281474976710754L, 281474976710755L,
                    281474976710753L, 281474976710748L, 281474976710752L, 281474976710731L, 281474976710729L,
                    281474976710717L, 281474976710724L, 281474976710728L, 281474976710737L, 281474976710738L,
                    281474976710736L, 281474976710733L, 281474976710735L, 281474976710742L, 281474976710740L,
                    281474976710746L, 281474976710745L, 281474976710744L },
            { 281474976710680L, 281474976710684L, 281474976710688L, 281474976710687L, 281474976710686L,
                    281474976710697L, 281474976710695L, 281474976710694L, 281474976710703L, 281474976710706L,
                    281474976710705L, 281474976710702L, 281474976710701L, 281474976710714L, 281474976710713L,
                    281474976710712L, 281474976710727L, 281474976710730L, 281474976710717L, 281474976710729L,
                    281474976710731L, 281474976710752L, 281474976710748L, 281474976710753L, 281474976710755L,
                    281474976710754L, 281474976710768L, 281474976710772L, 281474976710773L, 281474976710770L,
                    281474976710757L, 281474976710761L, 281474976710758L },
            { 281474976710753L, 281474976710748L, 281474976710752L, 281474976710731L },
            { 281474976710733L, 281474976710736L, 281474976710738L, 281474976710737L, 281474976710728L,
                    281474976710724L, 281474976710717L, 281474976710729L, 281474976710731L, 281474976710752L,
                    281474976710748L, 281474976710753L, 281474976710755L, 281474976710754L, 281474976710768L,
                    281474976710772L } };

    private final StraightPathItem[][] straightPaths = {
            { new StraightPathItem(new float[] { 22.606520f, 10.197294f, -45.918674f }, 1, 281474976710696L),
                    new StraightPathItem(new float[] { 3.484785f, 10.197294f, -34.241272f }, 0, 281474976710713L),
                    new StraightPathItem(new float[] { 1.984785f, 10.197294f, -31.241272f }, 0, 281474976710712L),
                    new StraightPathItem(new float[] { 1.984785f, 10.197294f, -29.741272f }, 0, 281474976710727L),
                    new StraightPathItem(new float[] { 2.584784f, 10.197294f, -27.941273f }, 0, 281474976710730L),
                    new StraightPathItem(new float[] { 6.457663f, 10.197294f, -18.334061f }, 2, 0L) },

            { new StraightPathItem(new float[] { 22.331268f, 10.197294f, -1.040187f }, 1, 281474976710773L),
                    new StraightPathItem(new float[] { 9.784786f, 10.197294f, -2.141273f }, 0, 281474976710755L),
                    new StraightPathItem(new float[] { 7.984783f, 10.197294f, -2.441269f }, 0, 281474976710753L),
                    new StraightPathItem(new float[] { 1.984785f, 10.197294f, -8.441269f }, 0, 281474976710752L),
                    new StraightPathItem(new float[] { -4.315216f, 10.197294f, -15.341270f }, 0, 281474976710724L),
                    new StraightPathItem(new float[] { -8.215216f, 10.197294f, -17.441269f }, 0, 281474976710728L),
                    new StraightPathItem(new float[] { -10.015216f, 10.197294f, -17.741272f }, 0, 281474976710738L),
                    new StraightPathItem(new float[] { -11.815216f, 9.997294f, -17.441269f }, 0, 281474976710736L),
                    new StraightPathItem(new float[] { -17.815216f, 5.197294f, -11.441269f }, 0, 281474976710735L),
                    new StraightPathItem(new float[] { -17.815216f, 5.197294f, -8.441269f }, 0, 281474976710746L),
                    new StraightPathItem(new float[] { -11.815216f, 0.197294f, 3.008419f }, 2, 0L) },

            { new StraightPathItem(new float[] { 18.694363f, 15.803535f, -73.090416f }, 1, 281474976710680L),
                    new StraightPathItem(new float[] { 17.584785f, 10.197294f, -49.841274f }, 0, 281474976710697L),
                    new StraightPathItem(new float[] { 17.284786f, 10.197294f, -48.041275f }, 0, 281474976710695L),
                    new StraightPathItem(new float[] { 16.084785f, 10.197294f, -45.341274f }, 0, 281474976710694L),
                    new StraightPathItem(new float[] { 3.484785f, 10.197294f, -34.241272f }, 0, 281474976710713L),
                    new StraightPathItem(new float[] { 1.984785f, 10.197294f, -31.241272f }, 0, 281474976710712L),
                    new StraightPathItem(new float[] { 1.984785f, 10.197294f, -8.441269f }, 0, 281474976710753L),
                    new StraightPathItem(new float[] { 7.984783f, 10.197294f, -2.441269f }, 0, 281474976710755L),
                    new StraightPathItem(new float[] { 9.784786f, 10.197294f, -2.141273f }, 0, 281474976710768L),
                    new StraightPathItem(new float[] { 38.423977f, 10.197294f, -0.116067f }, 2, 0L) },

            { new StraightPathItem(new float[] { 0.745335f, 10.197294f, -5.940050f }, 1, 281474976710753L),
                    new StraightPathItem(new float[] { 0.863553f, 10.197294f, -10.310320f }, 2, 0L) },

            { new StraightPathItem(new float[] { -20.651257f, 5.904126f, -13.712508f }, 1, 281474976710733L),
                    new StraightPathItem(new float[] { -11.815216f, 9.997294f, -17.441269f }, 0, 281474976710738L),
                    new StraightPathItem(new float[] { -10.015216f, 10.197294f, -17.741272f }, 0, 281474976710728L),
                    new StraightPathItem(new float[] { -8.215216f, 10.197294f, -17.441269f }, 0, 281474976710724L),
                    new StraightPathItem(new float[] { -4.315216f, 10.197294f, -15.341270f }, 0, 281474976710729L),
                    new StraightPathItem(new float[] { 1.984785f, 10.197294f, -8.441269f }, 0, 281474976710753L),
                    new StraightPathItem(new float[] { 7.984783f, 10.197294f, -2.441269f }, 0, 281474976710755L),
                    new StraightPathItem(new float[] { 18.784092f, 10.197294f, 3.054368f }, 2, 0L) } };

    @Test
    public void testFindPath() {
        QueryFilter filter = new DefaultQueryFilter();
        for (int i = 0; i < startRefs.length; i++) {
            long startRef = startRefs[i];
            long endRef = endRefs[i];
            float[] startPos = startPoss[i];
            float[] endPos = endPoss[i];
            Result<List<Long>> path = query.findPath(startRef, endRef, startPos, endPos, filter);
            Assert.assertEquals(statuses[i], path.status);
            Assert.assertEquals(results[i].length, path.result.size());
            for (int j = 0; j < results[i].length; j++) {
                Assert.assertEquals(results[i][j], path.result.get(j).longValue());
            }
        }
    }

    @Test
    public void testFindPathSliced() {
        QueryFilter filter = new DefaultQueryFilter();
        for (int i = 0; i < startRefs.length; i++) {
            long startRef = startRefs[i];
            long endRef = endRefs[i];
            float[] startPos = startPoss[i];
            float[] endPos = endPoss[i];
            query.initSlicedFindPath(startRef, endRef, startPos, endPos, filter, NavMeshQuery.DT_FINDPATH_ANY_ANGLE);
            Status status = Status.IN_PROGRESS;
            while (status == Status.IN_PROGRESS) {
                Result<Integer> res = query.updateSlicedFindPath(10);
                status = res.status;
            }
            Result<List<Long>> path = query.finalizeSlicedFindPath();
            Assert.assertEquals(statuses[i], path.status);
            Assert.assertEquals(results[i].length, path.result.size());
            for (int j = 0; j < results[i].length; j++) {
                Assert.assertEquals(results[i][j], path.result.get(j).longValue());
            }

        }
    }

    @Test
    public void testFindPathStraight() {
        QueryFilter filter = new DefaultQueryFilter();
        for (int i = 0; i < straightPaths.length; i++) {// startRefs.length; i++) {
            long startRef = startRefs[i];
            long endRef = endRefs[i];
            float[] startPos = startPoss[i];
            float[] endPos = endPoss[i];
            Result<List<Long>> path = query.findPath(startRef, endRef, startPos, endPos, filter);
            Result<List<StraightPathItem>> result = query.findStraightPath(startPos, endPos, path.result,
                    Integer.MAX_VALUE, 0);
            List<StraightPathItem> straightPath = result.result;
            Assert.assertEquals(straightPaths[i].length, straightPath.size());
            for (int j = 0; j < straightPaths[i].length; j++) {
                Assert.assertEquals(straightPaths[i][j].ref, straightPath.get(j).ref);
                for (int v = 0; v < 3; v++) {
                    Assert.assertEquals(straightPaths[i][j].pos[v], straightPath.get(j).pos[v], 0.01f);
                }
                Assert.assertEquals(straightPaths[i][j].flags, straightPath.get(j).flags);
            }
        }
    }

}
