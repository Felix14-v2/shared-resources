package nl.enjarai.shared_resources.mixin.saves;

import net.minecraft.client.MinecraftClient;
import nl.enjarai.shared_resources.api.DirectoryResourceHelper;
import nl.enjarai.shared_resources.registry.DirectoryResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.nio.file.Path;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Redirect(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/nio/file/Path;resolve(Ljava/lang/String;)Ljava/nio/file/Path;"
            )
    )
    private Path changePath(Path rootDir, String defaultPath) {
        var newPath = DirectoryResourceHelper.getPathFor(DirectoryResources.SAVES);

        if (newPath != null) {
            return newPath;
        }

        return rootDir.resolve(defaultPath);
    }
}
