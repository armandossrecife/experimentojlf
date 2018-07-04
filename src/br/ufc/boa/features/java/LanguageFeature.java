package br.ufc.boa.features.java;

/*
 * Copyright 2013-2014 Iowa State University. All rights reserved.
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
 * THIS SOFTWARE IS PROVIDED BY IOWA STATE UNIVERSITY ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL IOWA STATE UNIVERSITY OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are those
 * of the authors and should not be interpreted as representing official policies,
 * either expressed or implied, of Iowa State University.
 */

import java.util.HashMap;

public class LanguageFeature {
	// JLS2
	public static final String Feature_Assert = "Assert";
	// JLS3
	public static final String Feature_EnhancedFor = "EnhancedFor";
	public static final String Feature_Enums = "Enums";
	public static final String Super_Feature_Generics = "Generics";
	public static final String Feature_GenDefField = "GenDefField";
	public static final String Feature_GenDefMethod = "GenDefMethod";
	public static final String Feature_GenDefType = "GenDefType";
	public static final String Feature_GenExtends = "GenExtends";
	public static final String Feature_GenSuper = "GenSuper";
	public static final String Feature_GenWildcard = "GenWildcard";
	public static final String Feature_Varargs = "Varargs";
	public static final String Super_Feature_Annotation = "Annotation";
	public static final String Feature_AnnotDefine = "AnnotDefine";
	public static final String Feature_AnnotUse = "AnnotUse";
	// JLS4
	public static final String Super_Feature_Literal = "Literal";
	public static final String Feature_BinaryLit = "BinaryLit";
	public static final String Feature_UnderscoreLit = "UnderscoreLit";
	public static final String Feature_Diamond = "Diamond";
	public static final String Feature_SafeVarargs = "SafeVarargs";
	public static final String Super_Feature_Try_Catch = "TryCatch";
	public static final String Feature_TryResources = "TryResources";
	public static final String Feature_MultiCatch = "MultiCatch";
	// JSL
	public static final String Version_Feature_JSL2 = "JLS2";
	public static final String Version_Feature_JSL3 = "JLS3";
	public static final String Version_Feature_JSL4 = "JLS4";
	// map
	public static HashMap<String, String> superFeatures = new HashMap<String, String>();
	public static HashMap<String, String> jlsFeatures = new HashMap<String, String>();
	
	static {
		superFeatures.put(Feature_AnnotDefine, Super_Feature_Annotation);
		superFeatures.put(Feature_AnnotUse, Super_Feature_Annotation);
		
		superFeatures.put(Feature_GenDefField, Super_Feature_Generics);
		superFeatures.put(Feature_GenDefMethod, Super_Feature_Generics);
		superFeatures.put(Feature_GenDefType, Super_Feature_Generics);
		superFeatures.put(Feature_GenExtends, Super_Feature_Generics);
		superFeatures.put(Feature_GenSuper, Super_Feature_Generics);
		superFeatures.put(Feature_GenWildcard, Super_Feature_Generics);
		
		superFeatures.put(Feature_MultiCatch, Super_Feature_Try_Catch);
		superFeatures.put(Feature_TryResources, Super_Feature_Try_Catch);
		
		superFeatures.put(Feature_UnderscoreLit, Super_Feature_Literal);
		superFeatures.put(Feature_BinaryLit, Super_Feature_Literal);
		
		jlsFeatures.put(Feature_Assert, Version_Feature_JSL2);
		
		jlsFeatures.put(Feature_AnnotDefine, Version_Feature_JSL3);
		jlsFeatures.put(Feature_AnnotUse, Version_Feature_JSL3);
		jlsFeatures.put(Feature_EnhancedFor, Version_Feature_JSL3);
		jlsFeatures.put(Feature_Enums, Version_Feature_JSL3);
		jlsFeatures.put(Feature_GenDefField, Version_Feature_JSL3);
		jlsFeatures.put(Feature_GenDefMethod, Version_Feature_JSL3);
		jlsFeatures.put(Feature_GenDefType, Version_Feature_JSL3);
		jlsFeatures.put(Feature_GenExtends, Version_Feature_JSL3);
		jlsFeatures.put(Feature_GenSuper, Version_Feature_JSL3);
		jlsFeatures.put(Feature_GenWildcard, Version_Feature_JSL3);
		jlsFeatures.put(Feature_Varargs, Version_Feature_JSL3);
		
		jlsFeatures.put(Feature_BinaryLit, Version_Feature_JSL4);
		jlsFeatures.put(Feature_Diamond, Version_Feature_JSL4);
		jlsFeatures.put(Feature_MultiCatch, Version_Feature_JSL4);
		jlsFeatures.put(Feature_SafeVarargs, Version_Feature_JSL4);
		jlsFeatures.put(Feature_TryResources, Version_Feature_JSL4);
		jlsFeatures.put(Feature_UnderscoreLit, Version_Feature_JSL4);
	}
	
	public static String getSuperFeature(String feature) {
		if (superFeatures.containsKey(feature))
			return superFeatures.get(feature);
		return feature;
	}
	
	public static String getJlsVersion(String feature) {
		if (jlsFeatures.containsKey(feature))
			return jlsFeatures.get(feature);
		System.err.println("Feature not in any JLS version!!!");
		System.exit(0);
		return null;
	}
}