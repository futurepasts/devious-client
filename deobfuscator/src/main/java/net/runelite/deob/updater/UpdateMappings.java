/*
 * Copyright (c) 2016-2017, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.deob.updater;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.runelite.asm.Annotation;
import net.runelite.asm.ClassFile;
import net.runelite.asm.ClassGroup;
import net.runelite.asm.Field;
import net.runelite.asm.Method;
import net.runelite.asm.Type;
import net.runelite.deob.deobfuscators.mapping.AnnotationIntegrityChecker;
import net.runelite.deob.deobfuscators.mapping.AnnotationMapper;
import net.runelite.deob.deobfuscators.mapping.Mapper;
import net.runelite.deob.deobfuscators.mapping.ParallelExecutorMapping;
import net.runelite.deob.deobfuscators.transformers.BufferRenameTransformer;
import net.runelite.deob.deobfuscators.transformers.GraphicsObjectTransformer;
import net.runelite.deob.deobfuscators.transformers.ScriptOpcodesTransformer;
import net.runelite.deob.deobfuscators.transformers.BadEnumConstructorTransformer;
import net.runelite.deob.util.JarUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateMappings
{
	private static final Logger logger = LoggerFactory.getLogger(UpdateMappings.class);

	private final ClassGroup group1, group2;

	public UpdateMappings(ClassGroup group1, ClassGroup group2)
	{
		this.group1 = group1;
		this.group2 = group2;
	}

	public void update()
	{
		Mapper mapper = new Mapper(group1, group2);
		mapper.run();
		ParallelExecutorMapping mapping = mapper.getMapping();

		new BufferRenameTransformer(mapping).transform(group2);

		Field targetFillModeField = (Field) mapping.getMap().entrySet().stream().filter(e -> e.getKey().toString().equals("static LFillMode; FillMode.SOLID")).map(Map.Entry::getValue).findFirst().get();
		mapping.map(null, group1.findClass("FillMode"), targetFillModeField.getClassFile());

		ClassFile rasterizer2D = group1.findClass("Rasterizer2D");
		ClassFile targetRasterizer2D = (ClassFile) mapping.getMap().get(rasterizer2D);
		List<Method> missingRasterizer2DMethods = rasterizer2D.getMethods().stream().filter(m -> !m.getName().equals("<clinit>") && !m.getName().equals("<init>") && !mapping.getMap().containsKey(m)).collect(Collectors.toList());
		List<Method> missingTargetRasterizer2DMethods = targetRasterizer2D.getMethods().stream().filter(m -> !m.getName().equals("<clinit>") && !m.getName().equals("<init>") && !mapping.getMap().containsValue(m)).collect(Collectors.toList());
		if (missingRasterizer2DMethods.size() == missingTargetRasterizer2DMethods.size())
		{
			for (int i = 0; i < missingRasterizer2DMethods.size(); i ++)
			{
				mapping.map(null, missingRasterizer2DMethods.get(i), missingTargetRasterizer2DMethods.get(i));
			}
		}

		AnnotationMapper amapper = new AnnotationMapper(group1, group2, mapping);
		amapper.run();

		AnnotationIntegrityChecker aic = new AnnotationIntegrityChecker(group1, group2, mapping);
		aic.run();

		int errors = aic.getErrors();

		if (errors > 0)
		{
			logger.warn("Errors in annotation checker, exiting");
			System.exit(-1);
		}

		AnnotationRenamer an = new AnnotationRenamer(group2);
		an.run();

		ParameterRenamer pr = new ParameterRenamer(group1, group2, mapping);
		pr.run();

		AnnotationAdder ad = new AnnotationAdder(group2);
		ad.run();

		new ScriptOpcodesTransformer().transform(group2);
		new GraphicsObjectTransformer().transform(group2);

		for (ClassFile cf : group2)
		{
			Map<Type, Annotation> annotations = cf.getAnnotations();
			annotations.keySet()
				.stream()
				.filter(k -> !k.toString().startsWith("Lnet/runelite/"))
				.collect(Collectors.toList())
				.forEach(annotations::remove);
		}

		new BadEnumConstructorTransformer().transform(group2);
	}

	public void save(File out) throws IOException
	{
		JarUtil.save(group2, out);
	}

	public static void main(String[] args) throws IOException
	{
		if (args.length < 3)
		{
			System.exit(-1);
		}

		UpdateMappings u = new UpdateMappings(
			JarUtil.load(new File(args[0])),
			JarUtil.load(new File(args[1]))
		);
		u.update();
		u.save(new File(args[2]));
	}
}
