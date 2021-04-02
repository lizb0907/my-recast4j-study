/*
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
package org.recast4j.demo.ui;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_HIDDEN;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_MIDDLE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;
import static org.lwjgl.glfw.GLFW.glfwSetCharCallback;
import static org.lwjgl.glfw.GLFW.glfwSetClipboardString;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.nglfwGetClipboardString;
import static org.lwjgl.nuklear.Nuklear.NK_ANTI_ALIASING_ON;
import static org.lwjgl.nuklear.Nuklear.NK_BUTTON_LEFT;
import static org.lwjgl.nuklear.Nuklear.NK_BUTTON_MIDDLE;
import static org.lwjgl.nuklear.Nuklear.NK_BUTTON_RIGHT;
import static org.lwjgl.nuklear.Nuklear.nk_init;
import static org.lwjgl.nuklear.Nuklear.nk_input_begin;
import static org.lwjgl.nuklear.Nuklear.nk_input_button;
import static org.lwjgl.nuklear.Nuklear.nk_input_end;
import static org.lwjgl.nuklear.Nuklear.nk_input_motion;
import static org.lwjgl.nuklear.Nuklear.nk_input_scroll;
import static org.lwjgl.nuklear.Nuklear.nk_input_unicode;
import static org.lwjgl.nuklear.Nuklear.nk_rgb;
import static org.lwjgl.nuklear.Nuklear.nnk_strlen;
import static org.lwjgl.nuklear.Nuklear.nnk_textedit_paste;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memAddress;
import static org.lwjgl.system.MemoryUtil.memCopy;
import static org.lwjgl.system.MemoryUtil.nmemAlloc;
import static org.lwjgl.system.MemoryUtil.nmemFree;

import java.nio.ByteBuffer;

import org.lwjgl.nuklear.NkAllocator;
import org.lwjgl.nuklear.NkColor;
import org.lwjgl.nuklear.NkContext;
import org.lwjgl.nuklear.NkMouse;
import org.lwjgl.nuklear.NkVec2;
import org.lwjgl.system.MemoryStack;

public class NuklearUI {

    final NkAllocator allocator;
    final NkContext ctx = NkContext.create();
    final NkColor background;
    final NkColor white;
    private final NuklearUIModule[] modules;
    private final NuklearGL glContext;
    private boolean mouseOverUI;

    public NuklearUI(long window, Mouse mouse, NuklearUIModule... modules) {
        allocator = NkAllocator.create();
        allocator.alloc((handle, old, size) -> {
            long mem = nmemAlloc(size);
            if (mem == NULL) {
                throw new OutOfMemoryError();
            }
            return mem;

        });
        allocator.mfree((handle, ptr) -> nmemFree(ptr));
        background = NkColor.create();
        nk_rgb(28, 48, 62, background);
        white = NkColor.create();
        nk_rgb(255, 255, 255, white);
        nk_init(ctx, allocator, null);
        setupMouse(mouse);
        setupClipboard(window);
        glfwSetCharCallback(window, (w, codepoint) -> nk_input_unicode(ctx, codepoint));
        glContext = new NuklearGL(this);
        this.modules = modules;
    }

    private void setupMouse(Mouse mouse) {
        mouse.addListener(new MouseListener() {

            @Override
            public void scroll(double xoffset, double yoffset) {
                if (mouseOverUI) {
                    try (MemoryStack stack = stackPush()) {
                        NkVec2 scroll = NkVec2.mallocStack(stack).x((float) xoffset).y((float) yoffset);
                        nk_input_scroll(ctx, scroll);
                    }
                }
            }

            @Override
            public void button(int button, int mods, boolean down) {
                try (MemoryStack stack = stackPush()) {
                    int nkButton;
                    switch (button) {
                    case GLFW_MOUSE_BUTTON_RIGHT:
                        nkButton = NK_BUTTON_RIGHT;
                        break;
                    case GLFW_MOUSE_BUTTON_MIDDLE:
                        nkButton = NK_BUTTON_MIDDLE;
                        break;
                    default:
                        nkButton = NK_BUTTON_LEFT;
                    }
                    nk_input_button(ctx, nkButton, (int) mouse.getX(), (int) mouse.getY(), down);
                }
            }

            @Override
            public void position(double x, double y) {
                nk_input_motion(ctx, (int) x, (int) y);
            }
        });
    }

    private void setupClipboard(long window) {
        ctx.clip().copy((handle, text, len) -> {
            if (len == 0) {
                return;
            }

            try (MemoryStack stack = stackPush()) {
                ByteBuffer str = stack.malloc(len + 1);
                memCopy(text, memAddress(str), len);
                str.put(len, (byte) 0);
                glfwSetClipboardString(window, str);
            }
        });
        ctx.clip().paste((handle, edit) -> {
            long text = nglfwGetClipboardString(window);
            if (text != NULL) {
                nnk_textedit_paste(edit, text, nnk_strlen(text));
            }
        });
    }

    public void inputBegin() {
        nk_input_begin(ctx);
    }

    public void inputEnd(long win) {
        NkMouse mouse = ctx.input().mouse();
        if (mouse.grab()) {
            glfwSetInputMode(win, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
        } else if (mouse.grabbed()) {
            float prevX = mouse.prev().x();
            float prevY = mouse.prev().y();
            glfwSetCursorPos(win, prevX, prevY);
            mouse.pos().x(prevX);
            mouse.pos().y(prevY);
        } else if (mouse.ungrab()) {
            glfwSetInputMode(win, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        }
        nk_input_end(ctx);
    }

    public boolean layout(long win, int x, int y, int width, int height, int mouseX, int mouseY) {
        mouseOverUI = false;
        for (NuklearUIModule m : modules) {
            mouseOverUI = m.layout(ctx, x, y, width, height, mouseX, mouseY) | mouseOverUI;
        }
        return mouseOverUI;
    }

    public void render(long win) {
        glContext.render(win, NK_ANTI_ALIASING_ON);
    }

}
