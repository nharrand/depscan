package se.kth.castor;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.Test;
import spoon.Launcher;
import spoon.MavenLauncher;
import spoon.reflect.CtModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class LibraryTest {
    @Test
    public void testInit() throws IOException, XmlPullParserException, DependencyResolutionRequiredException {
        File pom = new File("/home/nharrand/Documents/jitsi/jitsi-videobridge/pom.xml");
        //File pom = new File("/home/nharrand/Documents/jitsi/helloworld/pom.xml");
        Launcher launcher = new Launcher();
        //MavenLauncher launcher = new MavenLauncher(pom.getParentFile().getAbsolutePath(), MavenLauncher.SOURCE_TYPE.APP_SOURCE);
        launcher.getEnvironment().setAutoImports(true);
        launcher.addInputResource("/home/nharrand/Documents/jitsi/jitsi-videobridge/src");
        //launcher.getEnvironment().setNoClasspath(true);
        launcher.buildModel();
        CtModel model = launcher.getModel();
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model2 = reader.read(new FileInputStream(pom));
        org.apache.maven.project.MavenProject p = new MavenProject(model2);
        List<String> ds = p.getRuntimeClasspathElements();
        List<String> ds2 = p.getCompileClasspathElements();
        Library lib = new Library("org.json.simple");
        lib.init(model);
        System.out.println("coucou");
    }

}