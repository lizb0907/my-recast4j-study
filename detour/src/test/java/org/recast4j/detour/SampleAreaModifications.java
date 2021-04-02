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

import org.recast4j.recast.AreaModification;

public class SampleAreaModifications {

    public static int SAMPLE_POLYAREA_TYPE_MASK = 0x07;
    public static int SAMPLE_POLYAREA_TYPE_GROUND = 0x1;
    public static int SAMPLE_POLYAREA_TYPE_WATER = 0x2;
    public static int SAMPLE_POLYAREA_TYPE_ROAD = 0x3;
    public static int SAMPLE_POLYAREA_TYPE_DOOR = 0x4;
    public static int SAMPLE_POLYAREA_TYPE_GRASS = 0x5;
    public static int SAMPLE_POLYAREA_TYPE_JUMP = 0x6;

    public static AreaModification SAMPLE_AREAMOD_GROUND = new AreaModification(SAMPLE_POLYAREA_TYPE_GROUND,
            SAMPLE_POLYAREA_TYPE_MASK);
    public static AreaModification SAMPLE_AREAMOD_WATER = new AreaModification(SAMPLE_POLYAREA_TYPE_WATER,
            SAMPLE_POLYAREA_TYPE_MASK);
    public static AreaModification SAMPLE_AREAMOD_ROAD = new AreaModification(SAMPLE_POLYAREA_TYPE_ROAD,
            SAMPLE_POLYAREA_TYPE_MASK);
    public static AreaModification SAMPLE_AREAMOD_GRASS = new AreaModification(SAMPLE_POLYAREA_TYPE_GRASS,
            SAMPLE_POLYAREA_TYPE_MASK);
    public static AreaModification SAMPLE_AREAMOD_DOOR = new AreaModification(SAMPLE_POLYAREA_TYPE_DOOR,
            SAMPLE_POLYAREA_TYPE_DOOR);
    public static AreaModification SAMPLE_AREAMOD_JUMP = new AreaModification(SAMPLE_POLYAREA_TYPE_JUMP,
            SAMPLE_POLYAREA_TYPE_JUMP);

    public static final int SAMPLE_POLYFLAGS_WALK = 0x01; // Ability to walk (ground, grass, road)
    public static final int SAMPLE_POLYFLAGS_SWIM = 0x02; // Ability to swim (water).
    public static final int SAMPLE_POLYFLAGS_DOOR = 0x04; // Ability to move through doors.
    public static final int SAMPLE_POLYFLAGS_JUMP = 0x08; // Ability to jump.
    public static final int SAMPLE_POLYFLAGS_DISABLED = 0x10; // Disabled polygon
    public static final int SAMPLE_POLYFLAGS_ALL = 0xffff; // All abilities.
}
