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
package org.recast4j.detour.crowd.debug;

import static org.recast4j.detour.DetourCommon.clamp;

public class ObstacleAvoidanceDebugData {
    int m_nsamples;
    int m_maxSamples;
    float[] m_vel;
    float[] m_ssize;
    float[] m_pen;
    float[] m_vpen;
    float[] m_vcpen;
    float[] m_spen;
    float[] m_tpen;

    public ObstacleAvoidanceDebugData(int maxSamples) {
        m_maxSamples = maxSamples;
        m_vel = new float[3 * m_maxSamples];
        m_pen = new float[m_maxSamples];
        m_ssize = new float[m_maxSamples];
        m_vpen = new float[m_maxSamples];
        m_vcpen = new float[m_maxSamples];
        m_spen = new float[m_maxSamples];
        m_tpen = new float[m_maxSamples];
    }

    public void reset() {
        m_nsamples = 0;
    }

    void normalizeArray(float[] arr, int n) {
        // Normalize penaly range.
        float minPen = Float.MAX_VALUE;
        float maxPen = -Float.MAX_VALUE;
        for (int i = 0; i < n; ++i) {
            minPen = Math.min(minPen, arr[i]);
            maxPen = Math.max(maxPen, arr[i]);
        }
        float penRange = maxPen - minPen;
        float s = penRange > 0.001f ? (1.0f / penRange) : 1;
        for (int i = 0; i < n; ++i)
            arr[i] = clamp((arr[i] - minPen) * s, 0.0f, 1.0f);
    }

    public void normalizeSamples() {
        normalizeArray(m_pen, m_nsamples);
        normalizeArray(m_vpen, m_nsamples);
        normalizeArray(m_vcpen, m_nsamples);
        normalizeArray(m_spen, m_nsamples);
        normalizeArray(m_tpen, m_nsamples);
    }

    public void addSample(float[] vel, float ssize, float pen, float vpen, float vcpen, float spen, float tpen) {
        if (m_nsamples >= m_maxSamples)
            return;
        m_vel[m_nsamples * 3] = vel[0];
        m_vel[m_nsamples * 3 + 1] = vel[1];
        m_vel[m_nsamples * 3 + 2] = vel[2];
        m_ssize[m_nsamples] = ssize;
        m_pen[m_nsamples] = pen;
        m_vpen[m_nsamples] = vpen;
        m_vcpen[m_nsamples] = vcpen;
        m_spen[m_nsamples] = spen;
        m_tpen[m_nsamples] = tpen;
        m_nsamples++;
    }

    public int getSampleCount() {
        return m_nsamples;
    }

    public float[] getSampleVelocity(int i) {
        float[] vel = new float[3];
        vel[0] = m_vel[i * 3];
        vel[1] = m_vel[i * 3 + 1];
        vel[2] = m_vel[i * 3 + 2];
        return vel;
    }

    public float getSampleSize(int i) {
        return m_ssize[i];
    }

    public float getSamplePenalty(int i) {
        return m_pen[i];
    }

    public float getSampleDesiredVelocityPenalty(int i) {
        return m_vpen[i];
    }

    public float getSampleCurrentVelocityPenalty(int i) {
        return m_vcpen[i];
    }

    public float getSamplePreferredSidePenalty(int i) {
        return m_spen[i];
    }

    public float getSampleCollisionTimePenalty(int i) {
        return m_tpen[i];
    }
}