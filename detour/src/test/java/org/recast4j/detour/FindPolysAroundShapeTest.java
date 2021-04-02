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

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

public class FindPolysAroundShapeTest extends AbstractDetourTest {

    long[][] refs = {
            { 281474976710696L, 281474976710695L, 281474976710694L, 281474976710691L, 281474976710697L,
                    281474976710693L, 281474976710692L, 281474976710703L, 281474976710706L, 281474976710699L,
                    281474976710705L, 281474976710698L, 281474976710700L, 281474976710704L },
            { 281474976710773L, 281474976710769L, 281474976710772L, 281474976710768L, 281474976710771L,
                    281474976710754L, 281474976710755L, 281474976710753L, 281474976710751L, 281474976710756L,
                    281474976710749L },
            { 281474976710680L, 281474976710679L, 281474976710684L, 281474976710683L, 281474976710688L,
                    281474976710678L, 281474976710676L, 281474976710687L, 281474976710690L, 281474976710686L,
                    281474976710689L, 281474976710685L, 281474976710697L, 281474976710695L, 281474976710694L,
                    281474976710691L, 281474976710696L, 281474976710693L, 281474976710692L, 281474976710703L,
                    281474976710706L, 281474976710699L, 281474976710705L, 281474976710700L, 281474976710704L },
            { 281474976710753L, 281474976710748L, 281474976710752L, 281474976710731L },
            { 281474976710733L, 281474976710735L, 281474976710736L, 281474976710742L, 281474976710734L,
                    281474976710739L, 281474976710738L, 281474976710740L, 281474976710746L, 281474976710743L,
                    281474976710745L, 281474976710741L, 281474976710747L, 281474976710737L, 281474976710732L,
                    281474976710728L, 281474976710724L, 281474976710744L, 281474976710725L, 281474976710717L,
                    281474976710729L, 281474976710726L, 281474976710721L, 281474976710719L, 281474976710731L,
                    281474976710720L, 281474976710752L, 281474976710748L, 281474976710753L, 281474976710755L,
                    281474976710756L, 281474976710750L, 281474976710749L, 281474976710754L, 281474976710751L,
                    281474976710768L, 281474976710772L, 281474976710773L, 281474976710771L, 281474976710769L } };
    long[][] parentsRefs = {
            { 0L, 281474976710696L, 281474976710695L, 281474976710695L, 281474976710695L, 281474976710695L,
                    281474976710693L, 281474976710694L, 281474976710703L, 281474976710706L, 281474976710706L,
                    281474976710705L, 281474976710705L, 281474976710705L },
            { 0L, 281474976710773L, 281474976710773L, 281474976710772L, 281474976710772L, 281474976710768L,
                    281474976710754L, 281474976710755L, 281474976710755L, 281474976710753L, 281474976710756L },
            { 0L, 281474976710680L, 281474976710680L, 281474976710680L, 281474976710684L, 281474976710679L,
                    281474976710678L, 281474976710688L, 281474976710687L, 281474976710687L, 281474976710687L,
                    281474976710687L, 281474976710686L, 281474976710697L, 281474976710695L, 281474976710695L,
                    281474976710695L, 281474976710695L, 281474976710693L, 281474976710694L, 281474976710703L,
                    281474976710706L, 281474976710706L, 281474976710705L, 281474976710705L },
            { 0L, 281474976710753L, 281474976710748L, 281474976710752L },
            { 0L, 281474976710733L, 281474976710733L, 281474976710735L, 281474976710736L, 281474976710736L,
                    281474976710736L, 281474976710742L, 281474976710740L, 281474976710746L, 281474976710746L,
                    281474976710746L, 281474976710746L, 281474976710738L, 281474976710738L, 281474976710737L,
                    281474976710728L, 281474976710745L, 281474976710724L, 281474976710724L, 281474976710717L,
                    281474976710717L, 281474976710717L, 281474976710729L, 281474976710729L, 281474976710721L,
                    281474976710731L, 281474976710752L, 281474976710748L, 281474976710753L, 281474976710753L,
                    281474976710753L, 281474976710756L, 281474976710755L, 281474976710755L, 281474976710754L,
                    281474976710768L, 281474976710772L, 281474976710772L, 281474976710773L } };
    float[][] costs = {
            { 0.000000f, 16.188787f, 22.561579f, 19.950766f, 19.519329f, 21.906523f, 22.806520f, 23.311579f, 25.124035f,
                    28.454576f, 26.084503f, 36.438854f, 30.526634f, 31.942192f },
            { 0.000000f, 16.618738f, 12.136283f, 20.387646f, 17.343250f, 22.037645f, 22.787645f, 27.178831f, 26.501472f,
                    31.691311f, 33.176235f },
            { 0.000000f, 36.657764f, 35.197689f, 37.484924f, 37.755524f, 37.132103f, 37.582104f, 38.816185f, 52.426109f,
                    55.945839f, 51.882935f, 44.879601f, 57.745838f, 59.402641f, 65.063034f, 64.934372f, 62.733185f,
                    62.756744f, 63.656742f, 65.813034f, 67.625488f, 70.956032f, 68.585960f, 73.028091f, 74.443649f },
            { 0.000000f, 2.097958f, 3.158618f, 4.658618f },
            { 0.000000f, 20.495766f, 21.352942f, 21.999096f, 25.531757f, 28.758514f, 30.264732f, 23.499096f, 24.670631f,
                    33.166218f, 35.651184f, 34.371792f, 30.012215f, 33.886887f, 33.855347f, 34.643524f, 36.300327f,
                    38.203144f, 40.339203f, 40.203213f, 47.254810f, 50.043945f, 49.054485f, 49.804810f, 49.204811f,
                    52.813477f, 51.004814f, 52.504814f, 53.565475f, 62.748611f, 61.504147f, 57.915474f, 62.989071f,
                    67.139801f, 66.507599f, 67.889801f, 69.539803f, 77.791168f, 75.186256f, 83.111412f } };

    @Test
    public void testFindPolysAroundShape() {
        QueryFilter filter = new DefaultQueryFilter();
        for (int i = 0; i < startRefs.length; i++) {
            long startRef = startRefs[i];
            float[] startPos = startPoss[i];
            Result<FindPolysAroundResult> polys = query.findPolysAroundShape(startRef,
                    getQueryPoly(startPos, endPoss[i]), 4, filter);
            Assert.assertEquals(refs[i].length, polys.result.getRefs().size());
            for (int v = 0; v < refs[i].length; v++) {
                boolean found = false;
                for (int w = 0; w < refs[i].length; w++) {
                    if (refs[i][v] == polys.result.getRefs().get(w).longValue()) {
                        Assert.assertEquals(parentsRefs[i][v], polys.result.getParentRefs().get(w).longValue());
                        Assert.assertEquals(costs[i][v], polys.result.getCosts().get(w).floatValue(), 0.01f);
                        found = true;
                    }
                }
                assertTrue("Ref not found " + refs[i][v], found);
            }
        }

    }

    private float[] getQueryPoly(float[] m_spos, float[] m_epos) {

        float nx = (m_epos[2] - m_spos[2]) * 0.25f;
        float nz = -(m_epos[0] - m_spos[0]) * 0.25f;
        float agentHeight = 2.0f;

        float[] m_queryPoly = new float[12];
        m_queryPoly[0] = m_spos[0] + nx * 1.2f;
        m_queryPoly[1] = m_spos[1] + agentHeight / 2;
        m_queryPoly[2] = m_spos[2] + nz * 1.2f;

        m_queryPoly[3] = m_spos[0] - nx * 1.3f;
        m_queryPoly[4] = m_spos[1] + agentHeight / 2;
        m_queryPoly[5] = m_spos[2] - nz * 1.3f;

        m_queryPoly[6] = m_epos[0] - nx * 0.8f;
        m_queryPoly[7] = m_epos[1] + agentHeight / 2;
        m_queryPoly[8] = m_epos[2] - nz * 0.8f;

        m_queryPoly[9] = m_epos[0] + nx;
        m_queryPoly[10] = m_epos[1] + agentHeight / 2;
        m_queryPoly[11] = m_epos[2] + nz;
        return m_queryPoly;
    }

}
