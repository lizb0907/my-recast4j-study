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

import org.junit.Assert;
import org.junit.Test;

public class GetPolyWallSegmentsTest extends AbstractDetourTest {

    float[][] vertices = {
            { 22.084785f, 10.197294f, -48.341274f, 22.684784f, 10.197294f, -44.141273f, 22.684784f, 10.197294f,
                    -44.141273f, 23.884785f, 10.197294f, -48.041275f, 23.884785f, 10.197294f, -48.041275f, 22.084785f,
                    10.197294f, -48.341274f },
            { 27.784786f, 10.197294f, 4.158730f, 28.384785f, 10.197294f, 2.358727f, 28.384785f, 10.197294f, 2.358727f,
                    28.384785f, 10.197294f, -2.141273f, 28.384785f, 10.197294f, -2.141273f, 27.784786f, 10.197294f,
                    -2.741272f, 27.784786f, 10.197294f, -2.741272f, 19.684784f, 10.197294f, -4.241272f, 19.684784f,
                    10.197294f, -4.241272f, 19.684784f, 10.197294f, 4.158730f, 19.684784f, 10.197294f, 4.158730f,
                    27.784786f, 10.197294f, 4.158730f },
            { 22.384785f, 14.997294f, -71.741272f, 19.084785f, 16.597294f, -74.741272f, 19.084785f, 16.597294f,
                    -74.741272f, 18.184784f, 15.997294f, -73.541275f, 18.184784f, 15.997294f, -73.541275f, 17.884785f,
                    14.997294f, -72.341278f, 17.884785f, 14.997294f, -72.341278f, 17.584785f, 14.997294f, -70.841278f,
                    17.584785f, 14.997294f, -70.841278f, 22.084785f, 14.997294f, -70.541275f, 22.084785f, 14.997294f,
                    -70.541275f, 22.384785f, 14.997294f, -71.741272f },
            { 4.684784f, 10.197294f, -6.941269f, 1.984785f, 10.197294f, -8.441269f, 1.984785f, 10.197294f, -8.441269f,
                    -4.015217f, 10.197294f, -6.941269f, -4.015217f, 10.197294f, -6.941269f, -1.615215f, 10.197294f,
                    -1.541275f, -1.615215f, 10.197294f, -1.541275f, 1.384785f, 10.197294f, 1.458725f, 1.384785f,
                    10.197294f, 1.458725f, 7.984783f, 10.197294f, -2.441269f, 7.984783f, 10.197294f, -2.441269f,
                    4.684784f, 10.197294f, -6.941269f },
            { -22.315216f, 6.597294f, -17.141273f, -23.815216f, 5.397294f, -13.841270f, -23.815216f, 5.397294f,
                    -13.841270f, -24.115217f, 4.997294f, -12.041275f, -24.115217f, 4.997294f, -12.041275f, -22.315216f,
                    4.997294f, -11.441269f, -22.315216f, 4.997294f, -11.441269f, -17.815216f, 5.197294f, -11.441269f,
                    -17.815216f, 5.197294f, -11.441269f, -22.315216f, 6.597294f, -17.141273f } };
    long[][] refs = { { 281474976710695L, 0L, 0L },
            { 0L, 281474976710770L, 0L, 281474976710769L, 281474976710772L, 0L },
            { 281474976710683L, 281474976710674L, 0L, 281474976710679L, 281474976710684L, 0L },
            { 281474976710750L, 281474976710748L, 0L, 0L, 281474976710755L, 281474976710756L },
            { 0L, 0L, 0L, 281474976710735L, 281474976710736L } };

    @Test
    public void testFindDistanceToWall() {
        QueryFilter filter = new DefaultQueryFilter();
        for (int i = 0; i < startRefs.length; i++) {
            Result<GetPolyWallSegmentsResult> result = query.getPolyWallSegments(startRefs[i], true, filter);
            GetPolyWallSegmentsResult segments = result.result;
            Assert.assertEquals(vertices[i].length, segments.getSegmentVerts().size() * 6);
            Assert.assertEquals(refs[i].length, segments.getSegmentRefs().size());
            for (int v = 0; v < vertices[i].length / 6; v++) {
                for (int n = 0; n < 6; n++) {
                    Assert.assertEquals(vertices[i][v * 6 + n], segments.getSegmentVerts().get(v)[n], 0.001f);
                }
            }
            for (int v = 0; v < refs[i].length; v++) {
                Assert.assertEquals(refs[i][v], segments.getSegmentRefs().get(v).longValue());
            }
        }

    }
}
