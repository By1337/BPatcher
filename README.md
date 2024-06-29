### Description

BPatcher is a tool designed to simplify bytecode modification for servers. With BPatcher, you can easily apply patches to your Minecraft server.

### Installation and Usage

To use BPatcher, you need to add `javaagent` to your server's startup command:

```shell
java -javaagent:BPatcher-1.0.jar -jar Server.jar
```

Upon first launch, BPatcher will create a `patches` folder. You can move your patches to this folder, and they will be applied the next time the server starts.

### Creating Patches

1. Create a new project and add the following snippet to your `pom.xml`:

    ```xml
    <project>
        <repositories>
            <repository>
                <id>by1337-repo</id>
                <url>https://repo.by1337.space/repository/maven-releases/</url>
            </repository>
        </repositories>
        <dependencies>
            <dependency>
                <groupId>org.by1337.bpatcher</groupId>
                <artifactId>BPatcher</artifactId>
                <version>1.0</version>
                <scope>provided</scope>
            </dependency>
        </dependencies>
    </project>
    ```

2. Create a class that implements the `Patcher` interface.

3. Compile the project and move it to the `patches` folder.

### Patch Example

```java
import org.by1337.bpatcher.patcher.api.Patcher;
import org.by1337.bpatcher.util.ByteCodeBuilder;
import org.by1337.bpatcher.util.BytecodeHelper;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class ExamplePatch implements Patcher {
   @Override
   public void apply(ClassNode classNode) {
      MethodNode methodNode = BytecodeHelper.getMethod(classNode, "main");
      AbstractInsnNode first = methodNode.instructions.getFirst();

      ByteCodeBuilder builder = new ByteCodeBuilder();
      builder.getstatic("java/lang/System", "out", "Ljava/io/PrintStream;");
      builder.ldc("Example patch!");
      builder.invokevirtual("java/io/PrintStream", "println", "(Ljava/lang/String;)V");

      methodNode.instructions.insertBefore(first, builder.getSource());
   }

   @Override
   public String targetClass() {
      return "org/bukkit/craftbukkit/Main";
   }
}
```

This example shows how to create a patch that inserts a print statement into the `main` method of the `org/bukkit/craftbukkit/Main` class. This patch will print "Example patch!" to the console when the method is executed.