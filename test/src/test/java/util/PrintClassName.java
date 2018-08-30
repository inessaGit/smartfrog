package util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class PrintClassName {

	public List<Class> getClassOfPackage(String packageName) throws ClassNotFoundException, IOException {

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;

		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		ArrayList<Class> classes = new ArrayList<Class>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes;
	}

	void printClassNames(List<Class>list){

		for (Class c: list){
			//System.out.println("Package: "+c.getPackage()+"\nClass: "+c.getSimpleName()+"\nFull Identifier: "+c.getName());
			System.out.println("<class name= \""+c.getName()+"\"/>");


		}
	}
	void printClassNamesWithTestTag(List<Class>list){

		System.out.println("=======================================\n");
		for (Class c: list){
			System.out.println("<test name=\""+c.getName()+"\">\n"+" <classes>\n<class name= \""+c.getName()+"\"/></classes></test>");
		}
	}
	private static List<Class> findClasses(File directory, String packageName)
			throws ClassNotFoundException {
		List<Class> classes = new ArrayList<Class>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file,
						packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName
						+ '.'
						+ file.getName().substring(0,
								file.getName().length() - 6)));
			}
		}
		return classes;
	}


}

class TestPrintClassName{
	public static void main(String[] args) throws ClassNotFoundException,
	IOException {

		PrintClassName print = new PrintClassName();
		List<Class> list =print.getClassOfPackage("pages");

		print.printClassNames(list);
		
		print.printClassNamesWithTestTag(list);

	}

}