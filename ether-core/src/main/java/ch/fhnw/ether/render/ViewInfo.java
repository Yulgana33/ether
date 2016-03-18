/*
 * Copyright (c) 2013 - 2016 Stefan Muller Arisona, Simon Schubiger
 * Copyright (c) 2013 - 2016 FHNW & ETH Zurich
 * All rights reserved.
 *
 * Contributions by: Filip Schramka, Samuel von Stachelski
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *  Neither the name of FHNW / ETH Zurich nor the names of its contributors may
 *   be used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package ch.fhnw.ether.render;

import java.util.Map;
import java.util.function.Supplier;

import com.jogamp.opengl.GL3;

import ch.fhnw.ether.render.gl.FloatUniformBuffer;
import ch.fhnw.ether.render.variable.builtin.ViewUniformBlock;
import ch.fhnw.ether.scene.attribute.IAttribute;
import ch.fhnw.ether.scene.camera.IViewCameraState;

public final class ViewInfo {
	private final FloatUniformBuffer uniforms = new FloatUniformBuffer(ViewUniformBlock.BLOCK_SIZE, 3);
	private IViewCameraState vcs;

	public void update(GL3 gl, IViewCameraState vcs) {
		ViewUniformBlock.loadUniforms(gl, uniforms, vcs);
		this.vcs = vcs;
	}
	
	public IViewCameraState getViewCameraState() {
		return vcs;
	}
	
	public void setCameraSpace(GL3 gl) {
		uniforms.bind(gl, 0);
	}

	public void setOrthoDeviceSpace(GL3 gl) {
		uniforms.bind(gl, 1);
	}

	public void setOrthoScreenSpace(GL3 gl) {
		uniforms.bind(gl, 2);
	}

	public void getAttributes(Map<IAttribute, Supplier<?>> globals) {
		globals.put(ViewUniformBlock.ATTRIBUTE, uniforms::getBindingPoint);
	}
}
