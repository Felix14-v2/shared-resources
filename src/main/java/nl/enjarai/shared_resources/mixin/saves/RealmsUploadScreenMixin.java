package nl.enjarai.shared_resources.mixin.saves;

import net.minecraft.client.realms.gui.screen.RealmsUploadScreen;
import nl.enjarai.shared_resources.api.DirectoryResourceHelper;
import nl.enjarai.shared_resources.registry.DirectoryResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.io.File;

@Mixin(RealmsUploadScreen.class)
public abstract class RealmsUploadScreenMixin {
    @ModifyVariable(
            method = "upload()V",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "java/io/File.<init>(Ljava/lang/String;Ljava/lang/String;)V"
            ),
            argsOnly = true
    )
    private File changePath(File original) {
        var newPath = DirectoryResourceHelper.getPathFor(DirectoryResources.SAVES);

        if (newPath != null) {
            return newPath.toFile();
        }

        return original;
    }
}
