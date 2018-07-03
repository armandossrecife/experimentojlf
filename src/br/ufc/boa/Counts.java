package br.ufc.boa;

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
import java.io.*;
import java.text.*;
import java.util.*;

/**
 * Generates Figure 2, Section 4.2.
 *
 * @author Robert Dyer (rdyer@iastate.edu)
 */
public class Counts {
	private final static HashMap<String, Long> values = new HashMap<>();
	private final static NumberFormat numFormat = NumberFormat.getInstance();

	public static void main(String[] args) throws IOException {
		getCounts();

	    numFormat.setMaximumFractionDigits(2);

		System.out.println("% $Id: counts.java,v 1.2 2013/09/15 19:49:52 rdyer Exp $");
		System.out.println("% DO NOT EDIT - This file automatically generated by section4-2/counts.java");
		System.out.println("\\begin{figure}[ht]");
		System.out.println("\\centering");
		System.out.println("\\rowcolors{2}{white}{gray!10}");
		System.out.println("\\begin{tabular}[t]{|c|r|}");
		System.out.println("\\hline");
		System.out.println("\\textbf{Metric} & \\multicolumn{1}{c|}{\\textbf{Count}} \\\\");
		System.out.println("\\hline");

		printEntry("All Projects", "AllProjects", false);
		printEntry("Java Projects", "JavaProjects", false);
		printEntry("Studied Projects", "StudiedProjects", true);

		System.out.println("\\hline");
		System.out.println("\\hline");

		printEntry("Repositories", "Repositories", false);
		printEntry("Revisions", "Revisions", true);

		System.out.println("\\hline");
		System.out.println("\\hline");

		printEntry("Files", "Files", false);
		printEntry("File Snapshots", "FileSnapshots", false);

		System.out.println("\\hline");
		System.out.println("\\hline");

		printEntry("Java Files", "JavaFiles", true);
		printEntry("Java File Snapshots", "JavaFileSnapshots", true);

		System.out.println("\\hline");
		System.out.println("\\hline");

		printEntry("AST Nodes", "AstNodes", true);

		System.out.println("\\hline");
		System.out.println("\\end{tabular}");
		System.out.println("\\caption{Metrics for the SourceForge-based dataset in Boa.}");
		System.out.println("\\label{tab:counts}");
		System.out.println("\\end{figure}");
	}

	private static void printEntry(final String title, final String key, final boolean bold) {
		System.out.print(title + " & ");
		if (bold)
			System.out.print("\\textbf{" + numFormat.format(values.get(key)) + "}");
		else
			System.out.print("\\emph{" + numFormat.format(values.get(key)) + "}");
		System.out.println("\\\\");
	}

	private static void getCounts() throws IOException {
		final DataInputStream in = new DataInputStream(new FileInputStream("boa-job69110-output.txt"));
		final BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String strLine;

		while ((strLine = br.readLine()) != null)
			values.put (strLine.substring(0, strLine.indexOf("[")), Long.parseLong(strLine.substring(strLine.indexOf(" = ") + 3)));

		in.close();
	}
}