package se.kth.castor;

import java.io.File;
import java.io.IOException;

public class CommonDep {
	public static void main(String[] args) throws IOException {
		File repos = new File("/home/nharrand/Documents/java-maven-repo/repos");
		DependencyLib lib = DependencyLib.build(repos.listFiles());
		lib.print();
	}
}
