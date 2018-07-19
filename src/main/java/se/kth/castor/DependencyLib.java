package se.kth.castor;

import java.io.*;
import java.util.*;

public class DependencyLib {
	public Map<String,List<Dependency>> lib = new HashMap<>();

	public static DependencyLib build(File[] projects) throws IOException {
		boolean includeTest = false;
		DependencyLib lib = new DependencyLib();
		for(File project : projects) {
			if (project.isDirectory()) {
				File dependencyList = new File(project, "dependency.list");

				BufferedReader output = new BufferedReader(new InputStreamReader(new FileInputStream(dependencyList)));
				String line;

				while ((line = output.readLine()) != null) {
					line = line.replace(" ", "");
					String[] infos = line.split(":");
					Dependency d = new Dependency();
					d.groupID = infos[0];
					d.artifactID = infos[1];
					d.packaging = infos[2];
					d.versionID = infos[3];
					d.scope = infos[4];
					d.includingProject = project.getName();

					String key = d.groupID + ":" + d.artifactID;

					if(includeTest || !d.scope.equals("test")) {
						List<Dependency> list;
						if (!lib.lib.containsKey(key)) list = new ArrayList<>();
						else list = lib.lib.get(key);
						list.add(d);
						lib.lib.put(key, list);
					}
				}
			}
		}
		return lib;
	}

	public void print() {
		List<Map.Entry<String, List<Dependency>>> list =
				new LinkedList<Map.Entry<String, List<Dependency>>>(lib.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, List<Dependency>>>() {
			public int compare(Map.Entry<String, List<Dependency>> o1,
			                   Map.Entry<String, List<Dependency>> o2) {
				return (o1.getValue().size() - o2.getValue().size());
			}
		});
		for (Map.Entry<String, List<Dependency>> dep : list) {
			System.out.println(dep.getKey() + ": ");
			for (Dependency d: lib.get(dep.getKey())) {
				System.out.println("\t" + d.includingProject + " -> " + d.versionID + ":" + d.scope);
			}
		}
	}


	public static class Dependency {
		String groupID;
		String artifactID;
		String versionID;
		String packaging;
		String scope;
		String includingProject;

		public Dependency() {}
	}

}
